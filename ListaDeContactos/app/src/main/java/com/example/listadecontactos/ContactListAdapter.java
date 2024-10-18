package com.example.listadecontactos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class ContactListAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private SQLiteDatabase db; // Referencia a la base de datos de SQLite con los contactos

    public ContactListAdapter(Context context, SQLiteDatabase db) {
        super(context,0);   // Al procesarse directamente la base de datos con la lista, no hace falta mandar el layout
        this.context = context;
        this.db = db;
        }

    @Override
    public int getCount() {
        // Obtener el número de contactos en la base de datos
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM contacts", null);
        int count = 0; // Se inicializa el conteo como 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0); // El primer valor del cursor es el número de contactos
        }
        cursor.close();
        return count;
    }

    // Metodo para obtener un contacto en especifico
    @Override
    public Contact getItem(int position) {
        // Query the database to get the contact at the specified position
        Cursor cursor = db.rawQuery("SELECT * FROM contacts LIMIT 1 OFFSET ?",
                new String[] { String.valueOf(position) });
        Contact contact = null;
        if (cursor.moveToFirst()) {
            // Assuming columns: _id, name, phone
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String last_name = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            contact = new Contact(id, name, last_name, phone); // Create Contact object
        }
        cursor.close();
        return contact;
    }


    // Vista de la lista
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull android.view.ViewGroup parent){

        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_layout_contact_item, parent, false);
        }
        // Obtener el contacto en la posición actual
        Contact contact = getItem(position);

        TextView contactName = view.findViewById(R.id.contact_name);
        TextView contactPhone = view.findViewById(R.id.contact_phone);
        // Asignar el nombre y el teléfono al TextView correspondiente
        contactName.setText(contact.getFullName());
        contactPhone.setText(contact.getPhoneNumber());

        return view;

    }
    // Metodo para buscar contactos que tengan el nombre parecido al ingresado
    public void searchContact(String query) {
        // Validamos que el query no este vacio
        if(query == null || query.isEmpty()){
            // Si esta vacio, entonces se debe mostrar todos los contactos
            refreshList();
        }else{
            // Hacer la consulta de la base de datos, adicionalmente buscar por el apellido
            String sqliteQuery = "SELECT * FROM contacts WHERE name LIKE ? OR last_name LIKE ?";
            String[] args = {"%" + query + "%", "%" + query + "%"};
            Cursor cursor = db.rawQuery(sqliteQuery, args);
            clear();
            // Verificar que existan contactos con ese nombre o apellido con el cursor.moveToFirst
            if(cursor.moveToFirst()){
                // Hacemos uso de un do while para recorrer todos los registros que hagan el match
                // Hasta que ya no hayan mas disponibles

                do{
                    // Asumiendo las columnas, _id, name, last_name, phone
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String last_name = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
                    String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));

                    // Agregar el contacto a la lista
                    add(new Contact(id, name, last_name, phone));

                }while(cursor.moveToNext());
            }
            // Cerrar el cursor
            cursor.close();
            // Notificar al adaptador que los datos han cambiado
            notifyDataSetChanged();
        }
    }

    private void refreshList() {

        // Obtener todos los contactos de la base de datos
        Cursor cursor = db.rawQuery("SELECT * FROM contacts", null);
        // Limpiar la lista actual
        clear();
        // Verificar que existan contactos en la base de datos

        if(cursor.moveToFirst()){

            do{
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String last_name = cursor.getString(cursor.getColumnIndexOrThrow("last_name"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                add(new Contact(id, name, last_name, phone));
            } while(cursor.moveToNext());
        }
        // Cerrar el cursor
        cursor.close();
        // Notificar al adaptador que los datos han cambiado
        notifyDataSetChanged();
    }
    // Metodo para eliminar un contacto
    public void deleteContact(int position){

        // Obtener el contacto en la posición actual
        Contact contact = getItem(position);
        // Eliminar el contacto de la base de datos
        db.delete("contacts", "_id = ?", new String[]{String.valueOf(contact.getId())});
        // Eliminar el contacto de la lista
        remove(contact);
        // Notificar al adaptador que los datos han cambiado
        notifyDataSetChanged();

    }
    // Metodo para editar un contacto

    public void editContact(int position, String newName, String newLastName, String newPhone){

        // Obtener el contacto en la posición actual
        Contact contact = getItem(position);
        // Editar el contacto en la base de datos
        // Creamos un objeto ContentValues para almacenar los valores a actualizar
        ContentValues values = new ContentValues();
        values.put("name", newName);
        values.put("last_name", newLastName);
        values.put("phone", newPhone);
        // Actualizar el contacto en la base de datos
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
            add(newContact);
            // Notificar al adaptador que los datos han cambiado
            notifyDataSetChanged();
        } else {
            // Si la insercion no se realizo correctamente, entonces mostrar un mensaje de error
            Toast.makeText(context, "Error al agregar el contacto", Toast.LENGTH_SHORT).show();
        }
    }

}
