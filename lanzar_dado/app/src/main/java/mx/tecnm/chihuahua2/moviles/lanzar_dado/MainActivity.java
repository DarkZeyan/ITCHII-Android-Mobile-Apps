package mx.tecnm.chihuahua2.moviles.lanzar_dado;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //Declaracion de objetos Java

    ImageView iv_dado;
    Button btn_lanzar_dado;
    TextView tv_resultado, textView_porcentajeDado1, textView_porcentajeDado2;
    TextView textView_progresoDado1, textView_progresoDado2;
    ProgressBar progressBarDado1, progressBarDado2;
    int[] ladosDado = new int [6];
    int totalDadosLanzados = 0;
    //Cambiar imagen al dado correspondiente al valor del numero

    //Metodo para desplegar resultados
    private void displayResults(){
        // Obtener los recursos de String.

        String dadoPorcentaje1, dadoPorcentaje2, dado1Resultado, dado2Resultado;
        float porcentajeDado1, porcentajeDado2;


        dado1Resultado = getResources().getString(R.string.textView_dado1);
        textView_progresoDado1.setText(dado1Resultado+ " "+ladosDado[0]);
        porcentajeDado1 = (float) ( ladosDado[0] * 100 / totalDadosLanzados);
        progressBarDado1.setProgress((int)(porcentajeDado1));

        dado2Resultado = getResources().getString(R.string.textView_dado2);
        porcentajeDado2 = (float) (ladosDado[1]*100/totalDadosLanzados);
        textView_progresoDado2.setText(dado2Resultado+" "+ladosDado[1]);
        progressBarDado2.setProgress((int)(porcentajeDado2));




    }

    private void processResult(int resultado){
        totalDadosLanzados++;
        switch(resultado){
            case 1:
                iv_dado.setImageResource(R.drawable.dado1);
                ladosDado[resultado-1]++;
                break;
            case 2:
                iv_dado.setImageResource(R.drawable.dado2);
                ladosDado[resultado-1]++;
                break;
            case 3:
                iv_dado.setImageResource(R.drawable.dado3);
                ladosDado[resultado-1]++;
                break;
            case 4:
                iv_dado.setImageResource(R.drawable.dado4);
                ladosDado[resultado-1]++;
                break;
            case 5:
                iv_dado.setImageResource(R.drawable.dado5);
                ladosDado[resultado-1]++;
                break;
            default:
                iv_dado.setImageResource(R.drawable.dado6);
                ladosDado[resultado-1]++;
                break;
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
        //Vinculacion de los objetos de Java a los componentes de UI de Android XML.

        iv_dado = findViewById(R.id.imageView);
        btn_lanzar_dado = findViewById(R.id.button);
        tv_resultado = findViewById(R.id.textView_Resultado);
        textView_progresoDado1 = findViewById(R.id.textView_progresoDado1);
        textView_progresoDado2 = findViewById(R.id.textView_progresoDado2);
        textView_porcentajeDado1 = findViewById(R.id.textView_porcentajeDado1);
        textView_porcentajeDado2 = findViewById(R.id.textView_porcentajeDado2);
        progressBarDado1 = findViewById(R.id.progressBarDado1);
        progressBarDado2 = findViewById(R.id.progressBarDado2);
        //Se agregan EventListeners para el boton de lanzar

        btn_lanzar_dado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Generamos un numero aleatorio para el dado entre 1 y 6
                Random randomObject = new Random();
                int randomNumber = randomObject.nextInt(6)+1;

                // Detectar el numero generado y mostrar el resultado.

                Toast.makeText(MainActivity.this, "Numero: "+randomNumber, Toast.LENGTH_LONG).show();
                processResult(randomNumber);
                displayResults();
                String resultText = "El resultado es "+randomNumber;
                tv_resultado.setText(resultText);


            }
        });

    }
}