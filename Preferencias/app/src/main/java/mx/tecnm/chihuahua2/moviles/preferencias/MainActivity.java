package mx.tecnm.chihuahua2.moviles.preferencias;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editText_name;
    RadioButton radioButton_plant;
    RadioButton radioButton_animal;
    RadioButton radioButton_figure;
    RadioGroup radioGroup;
   CalendarView calendarView_birth_date;
    Button button_save;
    String selectedDate;
    Button button_view;
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

        editText_name = findViewById(R.id.editText_name);
        radioButton_plant = findViewById(R.id.radioButton_plant);
        radioButton_animal = findViewById(R.id.radioButton_animal);
        radioButton_figure = findViewById(R.id.radioButton_figure);;
        calendarView_birth_date = findViewById(R.id.calendarView_birth_date);
        button_save = findViewById(R.id.button_save);
        button_view = findViewById(R.id.button_view);

        button_save.setOnClickListener(v -> {
            // El metodo de getSharedPreferences() permite definir el nombre de un archivo para
            // guardar las preferencias, ademas el archivo se puede usar dentro de la aplicacion en cualquier activity.
            SharedPreferences preferences = getSharedPreferences("preferencias", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("name", editText_name.getText().toString());
            if(radioButton_plant.isChecked()){
                editor.putString("topic","plant");
            }
            if(radioButton_animal.isChecked()){
                editor.putString("topic","animal");
            }
            if(radioButton_figure.isChecked()) {
                editor.putString("topic", "figure");
            }
            editor.putString("date", selectedDate);

            // Guardar los cambios
            editor.commit();

            // Limpiar los campos
            editText_name.setText("");
            radioButton_plant.setChecked(false);
            radioButton_animal.setChecked(false);
            radioButton_figure.setChecked(false);

            Toast.makeText(this, "Preferencias guardadas", Toast.LENGTH_LONG).show();

        });


        button_view.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewDataActivity.class);
            startActivity(intent);
        });
        // Recuperar el dato de la fecha del calendario.
        calendarView_birth_date.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
        });

    }
}