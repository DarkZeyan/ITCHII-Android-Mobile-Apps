package com.example.listadecontactos;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewEditContactActivity extends AppCompatActivity {
    private Contact contact;
    private int position;
    private ContactListAdapter adapter;

    EditText editText_name, editText_last_name, editText_phone;
    Button button_eliminar, button_editar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_edit_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText_name = findViewById(R.id.editText_name);
        editText_last_name = findViewById(R.id.editTextText_last_name);
        editText_phone = findViewById(R.id.editText_phone);
        button_eliminar = findViewById(R.id.button_eliminar);
        button_editar = findViewById(R.id.button_editar);


        ContactSqliteHelper dbHelper = new ContactSqliteHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        position = getIntent().getIntExtra("position", -1);
        adapter = new ContactListAdapter(this, db);
        contact = adapter.getItem(position);

        editText_name.setText(contact.getName());
        editText_last_name.setText(contact.getLastName());
        editText_phone.setText(contact.getPhoneNumber());

        button_eliminar.setOnClickListener(v -> {

            // Soltar alerta para eliminar
            // Preguntando si realmente quiere eliminar el contacto

            new AlertDialog.Builder(this).setTitle("Eliminar contacto")
                    .setMessage("¿Estás seguro de que quieres eliminar este contacto?")
                    .setPositiveButton("Sí", (dialog, which) -> {

                        // Eliminar el contacto de la base de datos
                        adapter.deleteContact(position);
                        setResult(RESULT_OK);
                        Toast.makeText(this, getString(R.string.contact_deleted), Toast.LENGTH_SHORT).show();
                        // Volver a la actividad anterior
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        button_editar.setOnClickListener(v -> {
            // Actualizar el contacto en la base de datos
            adapter.editContact(position,
                    editText_name.getText().toString(),
                    editText_last_name.getText().toString(),
                    editText_phone.getText().toString());
            setResult(RESULT_OK);
            Toast.makeText(this, getString(R.string.contact_updated), Toast.LENGTH_SHORT).show();
            finish();
        });

    }
}