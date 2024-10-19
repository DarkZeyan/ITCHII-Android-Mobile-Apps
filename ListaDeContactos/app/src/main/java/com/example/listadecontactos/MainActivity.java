package com.example.listadecontactos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    ImageButton imageButton_add;
    public ContactListAdapter contactListAdapter;

    public ContactListAdapter getContactListAdapter(){
        return contactListAdapter;
    }

    ListView listView_contacts;

    SearchView searchView;
    TextView empty_list_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageButton_add = findViewById(R.id.imageButton_add);
        listView_contacts = findViewById(R.id.listView_contacts);
        searchView = findViewById(R.id.searchBar);
        empty_list_text = findViewById(R.id.textView_empty_list);
        // Cambiar el color del hint
        int searchTextId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = (TextView) searchView.findViewById(searchTextId);
        searchText.setHintTextColor(getResources().getColor(R.color.white)); //

        ContactSqliteHelper contactSqliteHelper = new ContactSqliteHelper(this);
        SQLiteDatabase db = contactSqliteHelper.getWritableDatabase();
        contactListAdapter = new ContactListAdapter(this, db);
        contactListAdapter.notifyDataSetChanged();
        listView_contacts.setAdapter(contactListAdapter);

        contactListAdapter.notifyDataSetChanged();

        listView_contacts.setEmptyView(empty_list_text);
        // Al tocar un elemento de la lista nos lleva a la vista de editar
        listView_contacts.setOnItemClickListener(
                (parent, view, position, id) -> {
                    Intent intent = new Intent(MainActivity.this, ViewEditContactActivity.class);
                    intent.putExtra("position",position);
                    startActivityForResult(intent, 1);
                }
        );

        imageButton_add.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddContactActivity.class);
            startActivityForResult(intent, 1);  // Inicia la actividad y espera un resultado
        });

        // Añadir funcionalidad de SearchBar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contactListAdapter.searchContact(query);
                // Creamos un nuevo hilo para actualizar la vista

                listView_contacts.post(new Runnable() {
                    @Override
                    public void run() {
                        listView_contacts.setAdapter(contactListAdapter);
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Actualizar la lista de contactos en tiempo real
                contactListAdapter.searchContact(newText);
                return true; // Return true to indicate that you've handled the change
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Validamos que el resultado sea correcto
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Actualizar la lista de contactos después de agregar uno nuevo
            contactListAdapter.notifyDataSetChanged();
            if (contactListAdapter.getCount() == 0) {
                empty_list_text.setVisibility(View.VISIBLE);
            } else {
                empty_list_text.setVisibility(View.GONE);
            }
        }
    }
}