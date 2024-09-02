package mx.tecnm.chihuahua2.indice_de_masa_corporal;

import static mx.tecnm.chihuahua2.indice_de_masa_corporal.R.id.button_calcularIMC;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Agregar objeto Java
    EditText editText_peso;
    EditText editText_altura;
    Button button_calcularIMC;
    TextView textView_imc;
    TextView getTextView_imc_interpretacion;


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

        //Vinculación de objetos Java con elementos XML
        editText_peso = findViewById(R.id.editText_peso);
        editText_altura = findViewById(R.id.editText_altura);
        button_calcularIMC = findViewById(R.id.button_calcularIMC);
        textView_imc = findViewById(R.id.textView_imc);
        getTextView_imc_interpretacion =
                findViewById(R.id.textView_imc_interpretacion);

        //AGREGAR ESCUCHADOR CLICK DE EVENTO AL BOTO

        button_calcularIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float peso,altura, imc;
                //Recuperar peso y altura de la UI
                peso = Float.parseFloat(editText_peso.getText().toString());
                altura = Float.parseFloat(editText_altura.getText().toString());
                //Calcular IMC
                imc = peso / (altura * altura);
                //Mostrar el Resultado en la UI
                String resultadoIMC;
                resultadoIMC = getResources().getString(R.string.textView_resultado);
                textView_imc.setText(resultadoIMC + imc);


            }
        });
    }
}