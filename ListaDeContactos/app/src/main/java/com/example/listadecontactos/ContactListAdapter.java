package com.example.listadecontactos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactListAdapter extends ArrayAdapter<Contact>{
    private Context context;
    private SQLiteDatabase db; // Referencia a la base de datos de SQLite con los contactos
    private List<Contact> contacts; // Lista de contactos que se mostrarán en la lista
    private boolean isSearchMode = false; // Indica si estamos en modo de búsqueda
    public ContactListAdapter(Context context, SQLiteDatabase db) {
        super(context,0);   // Al procesarse directamente la base de datos con la lista, no hace falta mandar el layout
        this.context = context;
        this.db = db;
        this.contacts = new ArrayList<>();
        refreshList();
        }

    @Override
    public int getCount() {
        return contacts.size();
    }


    // Metodo para obtener un contacto en especifico
    @Override
    public Contact getItem(int position) {
        return contacts.get(position);
    }


    // Vista de la lista
    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull android.view.ViewGroup parent){
        if(!isSearchMode){
            refreshList();
        }
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_layout_contact_item, parent, false);
        }
        // Obtener el contacto en la posición actual
        Contact contact = getItem(position);

        TextView contactName = view.findViewById(R.id.contact_name);
        TextView contactPhone = view.findViewById(R.id.contact_phone);
        ImageView contactImage = view.findViewById(R.id.contact_icon);
        // Asignar el nombre y el teléfono al TextView correspondiente
        contactName.setText(contact.getFullName());
        contactPhone.setText(contact.getPhoneNumber());
        contactImage.setImageResource(R.drawable.ic_contact_foreground);
        //Agregarle background a la imagen
        contactImage.setBackgroundColor(R.color.ic_contact_background);

        return view;

    }
    // Metodo para buscar contactos que tengan el nombre parecido al ingresado
    public void searchContact(String query) {
        isSearchMode = true;
        // Validamos que el query no este vacio
        if(query == null || query.isEmpty()){
            // Si esta vacio, entonces se debe mostrar todos los contactos
            refreshList();
        }else{
            contacts.clear();
            clear();
            System.out.println("Query entrante: " + query);
            // Hacer la consulta de la base de datos, adicionalmente buscar por el apellido
            String sqliteQuery = "SELECT * FROM contacts WHERE name LIKE ? OR last_name LIKE ? OR phone LIKE ?";
            String[] args = {"%" + query + "%", "%" + query + "%"};
            System.out.println("Query a ejecutar: " + sqliteQuery);
            System.out.println("Args: " + Arrays.toString(args));
            Cursor cursor2 = db.rawQuery(sqliteQuery, args);

            // Verificar que existan contactos con ese nombre o apellido con el cursor.moveToFirst
            if(cursor2.moveToFirst()){
                // Hacemos uso de un do while para recorrer todos los registros que hagan el match
                // Hasta que ya no hayan mas disponibles

                do{
                    System.out.println("Cursor: " + cursor2);

                    // Asumiendo las columnas, _id, name, last_name, phone
                    int id = cursor2.getInt(cursor2.getColumnIndexOrThrow("_id"));
                    String name = cursor2.getString(cursor2.getColumnIndexOrThrow("name"));
                    String last_name = cursor2.getString(cursor2.getColumnIndexOrThrow("last_name"));
                    String phone = cursor2.getString(cursor2.getColumnIndexOrThrow("phone"));

                    // Agregar el contacto a la lista
                    contacts.add(new Contact(id, name, last_name, phone));

                }while(cursor2.moveToNext());
            }
            System.out.println("Contactos encontrados: " + cursor2.getCount());
            // Cerrar el cursor
            cursor2.close();
            // Notificar al adaptador que los datos han cambiado
            notifyDataSetChanged();
        }
    }

    private void refreshList() {
        System.out.println("Refreshing list");
        // Obtener todos los contactos de la base de datos
        Cursor cursor = db.rawQuery("SELECT * FROM contacts ORDER BY name ASC", null);
        // Limpiar la lista actual
        contacts.clear();
        // Verificar que existan contactos en la base de datos

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String last_name = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                contacts.add(new Contact(id, name, last_name, phone));
            } while(cursor.moveToNext());
        }
        // Cerrar el cursor
        cursor.close();
        // Notificar al adaptador que los datos han cambiado
        notifyDataSetChanged();
    }
    // Metodo para eliminar un contacto
    public void deleteContact(int position){

        isSearchMode = false;
        // Obtener el contacto en la posición actual
        Contact contact = getItem(position);
        // Eliminar el contacto de la base de datos
        contacts.remove(contact);
        db.delete("contacts", "_id = ?", new String[]{String.valueOf(contact.getId())});
        // Eliminar el contacto de la lista
        remove(contact);
        // Notificar al adaptador que los datos han cambiado
        notifyDataSetChanged();

    }
    // Metodo para editar un contacto

    public void editContact(int position, String newName, String newLastName, String newPhone){
        isSearchMode = false;
        // Obtener el contacto en la posición actual

        Contact contact = getItem(position);
        // Editar el contacto en la base de datos
        // Creamos un objeto ContentValues para almacenar los valores a actualizar
        ContentValues values = new ContentValues();
        values.put("name", newName);
        values.put("last_name", newLastName);
        values.put("phone", newPhone);
        // Actualizar el contacto en la base de datos
        contacts.set(position, new Contact(contact.getId(), newName, newLastName, newPhone));
        db.update("contacts", values, "_id = ?", new String[]{String.valueOf(contact.getId())});
        // Actualizar el nombre y el teléfono del contacto en la lista
        contact.setName(newName);
        contact.setLastName(newLastName);
        contact.setPhoneNumber(newPhone);
        // Notificar al adaptador que los datos han cambiado
        notifyDataSetChanged();
    }

    // Metodo para agregar un contacto
    public void addContact(String name, String last_name, String phone) {
        isSearchMode = false;
        // Insertar el contacto en la base de datos
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("last_name", last_name);
        values.put("phone", phone);

        long newRowId = db.insert("contacts", null, values);
        // Verificamos que la tabla exista y la insercion se haya realizado
        if (newRowId != -1) {
            // Asumimos que la insercion se realizo correctamente
            Contact newContact = new Contact((int) newRowId, name, last_name, phone);
            // Agregar el contacto a la lista
            contacts.add(newContact);
            // Notificar al adaptador que los datos han cambiado
            notifyDataSetChanged();
        } else {
            // Si la insercion no se realizo correctamente, entonces mostrar un mensaje de error
            Toast.makeText(context, "Error al agregar el contacto", Toast.LENGTH_SHORT).show();
        }
    }

}
