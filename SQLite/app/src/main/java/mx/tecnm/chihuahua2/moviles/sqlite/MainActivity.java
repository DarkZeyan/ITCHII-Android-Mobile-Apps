package mx.tecnm.chihuahua2.moviles.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Botones para avance de los registros
    ImageButton imageButton_first;
    ImageButton imageButton_previous;
    ImageButton imageButton_next;
    ImageButton imageButton_last;
    // Botones para CRUD
    ImageButton imageButton_insert;
    ImageButton imageButton_delete;
    ImageButton imageButton_update;
    ImageButton imageButton_search;
    // EditText para los campos
    EditText editText_id;
    EditText editText_name;
    EditText editText_description;

    private SQLiteHelper sqLiteHelper;
    private Cursor cursor;
    private SQLiteDatabase db;

    private void loadData() {
        sqLiteHelper = new SQLiteHelper(this, "gramineas.db", null, 1);
        db = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteHelper.getData();
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
        }
        db.close();
    }

    public void displayData() {
        if (!String.valueOf(cursor.getCount()).equals("0")) {
            editText_id.setText(cursor.getString(0));
            editText_name.setText(cursor.getString(1));
            editText_description.setText(cursor.getString(2));
        } else {
            // Limpiar campos si se borra el ultimo registro o el cursor esta vacio
            editText_id.setText("");
            editText_name.setText("");
            editText_description.setText("");
        }
    }

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

        // Vinculacion de objetos con los elementos de la vista

        imageButton_first = findViewById(R.id.imageButton_first);
        imageButton_previous = findViewById(R.id.imageButton_previous);
        imageButton_next = findViewById(R.id.imageButton_next);
        imageButton_last = findViewById(R.id.imageButton_last);
        imageButton_insert = findViewById(R.id.imageButton_insert);
        imageButton_delete = findViewById(R.id.imageButton_delete);
        imageButton_update = findViewById(R.id.imageButton_update);
        imageButton_search = findViewById(R.id.imageButton_search);

        editText_id = findViewById(R.id.editText_id);
        editText_name = findViewById(R.id.editText_name);
        editText_description = findViewById(R.id.editText_description);

        // Añadir funcionalidad a los botones

        loadData();
        // Enviar al registro 0
        if (!String.valueOf(cursor.getCount()).equals("0")) {
            displayData();
        }

        imageButton_insert.setOnClickListener(v -> {

            SQLiteHelper sqliteHelper = new SQLiteHelper(getApplicationContext(), "gramineas.db", null, 1);

            SQLiteDatabase db = sqliteHelper.getWritableDatabase();

            String id = editText_id.getText().toString();
            String name = editText_name.getText().toString();
            String description = editText_description.getText().toString();

            ContentValues values = new ContentValues();
            values.put("id_graminea", id);
            values.put("nombre", name);
            values.put("descripcion", description);

            db.insert("gramineas", null, values);
            db.close();

            Toast.makeText(getApplicationContext(), "Registro insertado", Toast.LENGTH_SHORT).show();
            // Actualizar la lista de registros del cursor
            loadData();
            displayData();

        });

        imageButton_delete.setOnClickListener(v -> {
            SQLiteHelper sqliteHelper = new SQLiteHelper(getApplicationContext(), "gramineas.db", null, 1);
            SQLiteDatabase db = sqliteHelper.getWritableDatabase();
            String id = editText_id.getText().toString();
            int deleted_registers = db.delete("gramineas", "id_graminea=?", new String[] { id });
            db.close();

            if (deleted_registers == 1) {
                Toast.makeText(getApplicationContext(), "Registro eliminado", Toast.LENGTH_SHORT).show();
                // Actualizar la lista de registros del cursor
                loadData();
                displayData();
            } else {
                Toast.makeText(getApplicationContext(), "Registro no encontrado", Toast.LENGTH_SHORT).show();
            }
        });

        // Boton de modificar

        imageButton_update.setOnClickListener(v -> {
            SQLiteHelper sqliteHelper = new SQLiteHelper(getApplicationContext(), "gramineas.db", null, 1);
            SQLiteDatabase db = sqliteHelper.getWritableDatabase();
            String id = editText_id.getText().toString();
            String name = editText_name.getText().toString();
            String description = editText_description.getText().toString();

            ContentValues values = new ContentValues();
            values.put("id_graminea", id);
            values.put("nombre", name);
            values.put("descripcion", description);

            int updated_registers = db.update("gramineas", values, "id_graminea=?", new String[] { id });
            db.close();

            if (updated_registers == 1) {
                Toast.makeText(getApplicationContext(), "Registro actualizado", Toast.LENGTH_SHORT).show();
                // Actualizar la lista de registros del cursor
                loadData();
                displayData();
            } else {
                Toast.makeText(getApplicationContext(), "Registro no encontrado", Toast.LENGTH_SHORT).show();
            }
        });

        // Boton de buscar
        imageButton_search.setOnClickListener(v -> {

            SQLiteHelper sqliteHelper = new SQLiteHelper(getApplicationContext(), "gramineas.db", null, 1);
            SQLiteDatabase db = sqliteHelper.getWritableDatabase();

            String id = editText_id.getText().toString();
            Cursor cursor = db.rawQuery("SELECT * FROM gramineas WHERE id_graminea = ?", new String[] { id });

            if (cursor.moveToFirst()) {
                editText_name.setText(cursor.getString(1));
                editText_description.setText(cursor.getString(2));

                // Obtener nueva posicion para el cursor

                Cursor previousCursor = db.rawQuery("SELECT count(*) FROM gramineas WHERE id_graminea < ?",
                        new String[] { id });

                previousCursor.moveToFirst();
                int new_position = Integer.parseInt(previousCursor.getString(0));
                cursor.moveToPosition(new_position);
            } else {
                Toast.makeText(getApplicationContext(), "Registro no encontrado", Toast.LENGTH_SHORT).show();
            }
            db.close();

        });

        // Botones de avance de registros

        // Primer registro
        imageButton_first.setOnClickListener(v -> {
            int position = 0;
            cursor.moveToPosition(position);
            displayData();
        });

        // Registro anterior
        imageButton_previous.setOnClickListener(v -> {
            int current_position = cursor.getPosition();
            if (current_position > 0) {
                cursor.moveToPosition(current_position - 1);
                displayData();
            } else {
                Toast.makeText(getApplicationContext(), "No hay registros anteriores", Toast.LENGTH_SHORT).show();
            }
        });

        // Siguiente registro
        imageButton_next.setOnClickListener(v -> {
            int current_position = cursor.getPosition();
            if (current_position < cursor.getCount() - 1) {
                cursor.moveToPosition(current_position + 1);
                displayData();
            } else {
                Toast.makeText(getApplicationContext(), "No hay registros siguientes", Toast.LENGTH_SHORT).show();
            }
        });

        // Ultimo registro
        imageButton_last.setOnClickListener(v -> {
            int position = cursor.getCount() - 1;
            cursor.moveToPosition(position);
            displayData();
        });
    }
}