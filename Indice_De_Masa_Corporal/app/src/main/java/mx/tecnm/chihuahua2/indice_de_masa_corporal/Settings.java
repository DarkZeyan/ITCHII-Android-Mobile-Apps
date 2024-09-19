package mx.tecnm.chihuahua2.indice_de_masa_corporal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Settings extends AppCompatActivity {


    Button button_return;
    RadioButton radioButton_systemDefault;
    RadioButton radioButton_lightMode;
    RadioButton radioButton_darkMode;


    //Metodo para el manejo del cambio de temas

    private void switchTheme(int theme) {
        SharedPreferences.Editor editor = getSharedPreferences("ThemePrefs", MODE_PRIVATE).edit();
        editor.putInt("theme_mode", theme);
        editor.apply();

        // Aplicar el modo seleccionado
        AppCompatDelegate.setDefaultNightMode(theme);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        int themeMode = sharedPreferences.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(themeMode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Definicion para cambiar el tema de la app
        button_return = findViewById(R.id.button_return);
        radioButton_systemDefault = findViewById(R.id.radioButton_systemDefault);
        radioButton_lightMode = findViewById(R.id.radioButton_lightMode);
        radioButton_darkMode = findViewById(R.id.radioButton_darkMode);

        // Validar que boton ya tiene dicha configuracion seleccionada
        int currentTheme = AppCompatDelegate.getDefaultNightMode();
        if (currentTheme == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            radioButton_systemDefault.setChecked(true);
        } else if (currentTheme == AppCompatDelegate.MODE_NIGHT_YES) {
            radioButton_darkMode.setChecked(true);
        }
        else {
            radioButton_lightMode.setChecked(true);
        }

        //
        radioButton_systemDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                // Desactivar seleccion de los otros botones
                radioButton_lightMode.setChecked(false);
                radioButton_darkMode.setChecked(false);
            }
            });

        radioButton_lightMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTheme(AppCompatDelegate.MODE_NIGHT_NO);

                // Desactivar seleccion de los otros botones
                radioButton_darkMode.setChecked(false);
                radioButton_systemDefault.setChecked(false);
            }
            });

        radioButton_darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTheme(AppCompatDelegate.MODE_NIGHT_YES);

                // Desactivar seleccion de los otros botones
                radioButton_lightMode.setChecked(false);
                radioButton_systemDefault.setChecked(false);
            }
            });


        //Vinculación de objetos Java con elementos XML
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}