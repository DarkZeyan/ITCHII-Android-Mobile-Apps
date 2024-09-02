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
    // Declaracion de objetos Java

    ImageView iv_dado;
    Button btn_lanzar_dado;
    TextView tv_resultado, textView_porcentajeDado1, textView_porcentajeDado2, textView_porcentajeDado3,
            textView_porcentajeDado4,
            textView_porcentajeDado5, textView_porcentajeDado6;
    TextView textView_progresoDado1, textView_progresoDado2, textView_progresoDado3, textView_progresoDado4,
            textView_progresoDado5,
            textView_progresoDado6;
    ProgressBar progressBarDado1, progressBarDado2, progressBarDado3, progressBarDado4, progressBarDado5,
            progressBarDado6;
    int[] ladosDado = new int[6];
    int totalDadosLanzados = 0;
    // Cambiar imagen al dado correspondiente al valor del numero

    // Metodo para desplegar resultados
    private void displayResults() {
        // Obtener los recursos de String.

        String txt_dadoPorcentaje1, txt_dadoPorcentaje2, txt_dadoPorcentaje3, txt_dadoPorcentaje4, txt_dadoPorcentaje5,
                txt_dadoPorcentaje6, dado1Resultado, dado2Resultado,
                dado3Resultado, dado4Resultado, dado5Resultado, dado6Resultado;
        float porcentajeDado1, porcentajeDado2, porcentajeDado3, porcentajeDado4, porcentajeDado5, porcentajeDado6;

        dado1Resultado = getResources().getString(R.string.textView_dado1);
        textView_progresoDado1.setText(dado1Resultado + " " + ladosDado[0]);
        txt_dadoPorcentaje1 = getResources().getString(R.string.textView_porcentaje_d1);
        porcentajeDado1 = (float) (ladosDado[0] * 100 / totalDadosLanzados);
        textView_porcentajeDado1.setText(porcentajeDado1 + txt_dadoPorcentaje1);

        progressBarDado1.setProgress((int) (porcentajeDado1));

        txt_dadoPorcentaje2 = getResources().getString(R.string.textView_porcentaje_d2);
        dado2Resultado = getResources().getString(R.string.textView_dado2);
        porcentajeDado2 = (float) (ladosDado[1] * 100 / totalDadosLanzados);
        textView_progresoDado2.setText(dado2Resultado + " " + ladosDado[1]);
        textView_porcentajeDado2.setText(porcentajeDado2 + txt_dadoPorcentaje2);
        progressBarDado2.setProgress((int) (porcentajeDado2));

        // Para caso 3

        txt_dadoPorcentaje3 = getResources().getString(R.string.textView_porcentaje_d3);
        dado3Resultado = getResources().getString(R.string.textView_dado3);
        porcentajeDado3 = (float) (ladosDado[2] * 100 / totalDadosLanzados);
        textView_progresoDado3.setText(dado3Resultado + " " + ladosDado[2]);
        textView_porcentajeDado3.setText(porcentajeDado3 + txt_dadoPorcentaje3);
        progressBarDado3.setProgress((int) (porcentajeDado3));

        // Caso 4
        txt_dadoPorcentaje4 = getResources().getString(R.string.textView_porcentaje_d4);
        dado4Resultado = getResources().getString(R.string.textView_dado4);
        porcentajeDado4 = (float) (ladosDado[3] * 100 / totalDadosLanzados);
        textView_progresoDado4.setText(dado4Resultado + " " + ladosDado[3]);
        textView_porcentajeDado4.setText(porcentajeDado4 + txt_dadoPorcentaje4);
        progressBarDado4.setProgress((int) (porcentajeDado4));

        // Caso 5
        txt_dadoPorcentaje5 = getResources().getString(R.string.textView_porcentaje_d5);
        dado5Resultado = getResources().getString(R.string.textView_dado5);
        porcentajeDado5 = (float) (ladosDado[4] * 100 / totalDadosLanzados);
        textView_progresoDado5.setText(dado5Resultado + " " + ladosDado[4]);
        textView_porcentajeDado5.setText(porcentajeDado5 + txt_dadoPorcentaje5);
        progressBarDado5.setProgress((int) (porcentajeDado5));

        // Caso 6
        txt_dadoPorcentaje6 = getResources().getString(R.string.textView_porcentaje_d6);
        dado6Resultado = getResources().getString(R.string.textView_dado6);
        porcentajeDado6 = (float) (ladosDado[5] * 100 / totalDadosLanzados);
        textView_progresoDado6.setText(dado6Resultado + " " + ladosDado[5]);
        textView_porcentajeDado6.setText(porcentajeDado6 + txt_dadoPorcentaje6);
        progressBarDado6.setProgress((int) (porcentajeDado6));

    }

    private void processResult(int resultado) {
        totalDadosLanzados++;
        switch (resultado) {
            case 1:
                iv_dado.setImageResource(R.drawable.dado1);
                ladosDado[resultado - 1]++;
                break;
            case 2:
                iv_dado.setImageResource(R.drawable.dado2);
                ladosDado[resultado - 1]++;
                break;
            case 3:
                iv_dado.setImageResource(R.drawable.dado3);
                ladosDado[resultado - 1]++;
                break;
            case 4:
                iv_dado.setImageResource(R.drawable.dado4);
                ladosDado[resultado - 1]++;
                break;
            case 5:
                iv_dado.setImageResource(R.drawable.dado5);
                ladosDado[resultado - 1]++;
                break;
            default:
                iv_dado.setImageResource(R.drawable.dado6);
                ladosDado[resultado - 1]++;
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
        // Vinculacion de los objetos de Java a los componentes de UI de Android XML.

        iv_dado = findViewById(R.id.imageView);
        btn_lanzar_dado = findViewById(R.id.button);
        tv_resultado = findViewById(R.id.textView_Resultado);
        textView_progresoDado1 = findViewById(R.id.textView_progresoDado1);
        textView_progresoDado2 = findViewById(R.id.textView_progresoDado2);
        textView_progresoDado3 = findViewById(R.id.textView_progresoDado3);
        textView_progresoDado4 = findViewById(R.id.textView_progresoDado4);
        textView_progresoDado5 = findViewById(R.id.textView_progresoDado5);
        textView_progresoDado6 = findViewById(R.id.textView_progresoDado6);
        textView_porcentajeDado1 = findViewById(R.id.textView_porcentajeDado1);
        textView_porcentajeDado2 = findViewById(R.id.textView_porcentajeDado2);
        textView_porcentajeDado3 = findViewById(R.id.textView_porcentajeDado3);
        textView_porcentajeDado4 = findViewById(R.id.textView_porcentajeDado4);
        textView_porcentajeDado5 = findViewById(R.id.textView_porcentajeDado5);
        textView_porcentajeDado6 = findViewById(R.id.textView_porcentajeDado6);
        progressBarDado1 = findViewById(R.id.progressBarDado1);
        progressBarDado2 = findViewById(R.id.progressBarDado2);
        progressBarDado3 = findViewById(R.id.progressBarDado3);
        progressBarDado4 = findViewById(R.id.progressBarDado4);
        progressBarDado5 = findViewById(R.id.progressBarDado5);
        progressBarDado6 = findViewById(R.id.progressBarDado6);

        // Se agregan EventListeners para el boton de lanzar

        btn_lanzar_dado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generamos un numero aleatorio para el dado entre 1 y 6
                Random randomObject = new Random();
                int randomNumber = randomObject.nextInt(6) + 1;

                // Detectar el numero generado y mostrar el resultado.

                Toast.makeText(MainActivity.this, "Numero: " + randomNumber, Toast.LENGTH_LONG).show();
                processResult(randomNumber);
                displayResults();
                String resultText = "El resultado de este lanzamiento es " + randomNumber + ", has lanzado "
                        + totalDadosLanzados + " dados.";
                tv_resultado.setText(resultText);

            }
        });

    }
}