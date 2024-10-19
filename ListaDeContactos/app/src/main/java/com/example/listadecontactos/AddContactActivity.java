package com.example.listadecontactos;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddContactActivity extends AppCompatActivity {



    EditText editTextName;
    EditText editTextPhone;
    EditText editTextLastName;
    Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);

        // Aquí vuelves a obtener acceso a la base de datos
        ContactSqliteHelper dbHelper = new ContactSqliteHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Inicializa las vistas
        editTextName = findViewById(R.id.editText_name);
        editTextPhone = findViewById(R.id.editText_phone);
        editTextLastName = findViewById(R.id.editTextText_last_name);
        button_save = findViewById(R.id.button_guardar);

        button_save.setOnClickListener(view -> {
            String name = editTextName.getText().toString();
            String phone = editTextPhone.getText().toString();
            String lastName = editTextLastName.getText().toString();

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                ContactListAdapter adapter = new ContactListAdapter(this, db);
                adapter.addContact(name, lastName, phone);
                Toast.makeText(this, getString(R.string.contact_added), Toast.LENGTH_SHORT).show();

                // Devolvemos el resultado a MainActivity
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}