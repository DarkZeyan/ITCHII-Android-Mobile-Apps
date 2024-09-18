package mx.tecnm.chihuahua2.indice_de_masa_corporal;

import static mx.tecnm.chihuahua2.indice_de_masa_corporal.R.id.button_calcularIMC;
import static mx.tecnm.chihuahua2.indice_de_masa_corporal.R.id.textView_imc_interpretacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Agregar objeto Java
    EditText editText_peso;
    EditText editText_altura;
    Button button_calcularIMC;
    TextView textView_imc;
    TextView textView_imc_interpretacion;


    // Gestion del menu de opciones

    //Inflar el menu de opciones

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    // Escuchar los eventos de los elementos del menu
    // El argumento item es el elemento seleccionado del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.camara) {
            // Abrir la camara
            Toast.makeText(this, "Camara", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
        }
        else if (itemId == R.id.search) {
            // Abrir la busqueda
            Toast.makeText(this, "Busqueda", Toast.LENGTH_LONG).show();
        }
        else if (itemId == R.id.settings) {
            // Abrir la configuracion
            Toast.makeText(this, "Configuracion", Toast.LENGTH_LONG).show();
        }
        else if (itemId == R.id.about) {
            // Abrir la acerca de
            Toast.makeText(this, "Acerca de", Toast.LENGTH_LONG).show();

            // Llamar la actividad de About Me
            Intent intent = new Intent(this, AboutMe.class);
            startActivity(intent);
        }
        return true;
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


        //Creamos toolbar para vincularlo
        Toolbar toolbar;

        
        //Vinculación de objetos Java con elementos XML
        editText_peso = findViewById(R.id.editText_peso);
        editText_altura = findViewById(R.id.editText_altura);
        button_calcularIMC = findViewById(R.id.button_calcularIMC);
        textView_imc = findViewById(R.id.textView_imc);
        textView_imc_interpretacion =
        findViewById(R.id.textView_imc_interpretacion);
        toolbar = findViewById(R.id.toolbar);
        //AGREGAR ESCUCHADOR CLICK DE EVENTO AL BOTON

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        
        

        button_calcularIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float peso,altura, imc;
                //Validar que los campos no esten vacios
                if(editText_peso.getText().toString().isEmpty()){
                    editText_peso.setError(getResources().getString(R.string.editText_peso));
                    return;
                }

                // Obtener el fondo de la actividad para cambiarlo
                View main = findViewById(R.id.main);

                //Recuperar peso y altura de la UI
                peso = Float.parseFloat(editText_peso.getText().toString());
                altura = Float.parseFloat(editText_altura.getText().toString());
                //Calcular IMC
                imc = peso / (altura * altura);
                //Mostrar el Resultado en la UI
                String resultadoIMC;
                resultadoIMC = getResources().getString(R.string.textView_resultado);
                textView_imc.setText(resultadoIMC + " " + imc);

                //Obtener y mostrar interpretación IMC
                if(imc < 18.50){ //Peso bajo
                    textView_imc_interpretacion.setText(getResources().getString(R.string.peso_bajo));
                    main.setBackgroundColor(getResources().getColor(R.color.yellow));

                } else if (imc >= 18.50 && imc <= 24.99){ //Peso Normal)
                    textView_imc_interpretacion.setText(getResources().getString(R.string.peso_normal));
                    main.setBackgroundColor(getResources().getColor(R.color.green));
                }
                    else {
                        textView_imc_interpretacion.setText(getResources().getString(R.string.peso_alto));
                        main.setBackgroundColor(getResources().getColor(R.color.orange));
                }
            }
        });

    
    }
}