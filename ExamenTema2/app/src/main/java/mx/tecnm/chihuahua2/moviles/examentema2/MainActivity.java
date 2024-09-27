package mx.tecnm.chihuahua2.moviles.examentema2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    int totalCalificaciones = 0;
    double promedioGrupo = 0.0;
    double mejorCalificacion = 0.0;
    double sumaCalificaciones = 0.0;

    Button button_capturar;
    EditText editText_calificacion;
    TextView textView_total_calificaciones;

    TextView textView_mejor_calificacion_resultado;
    TextView textView_promedio_grupo_resultado;


    @SuppressLint("DefaultLocale")
    private void capturarCalificacion() {

        if (editText_calificacion.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingresa una calificacion", Toast.LENGTH_SHORT).show();
            return;
        }
        double calificacion = Double.parseDouble(editText_calificacion.getText().toString());
        sumaCalificaciones += calificacion;
        totalCalificaciones++;
        promedioGrupo = sumaCalificaciones / totalCalificaciones;
        if (calificacion > mejorCalificacion) {
            mejorCalificacion = calificacion;
            textView_mejor_calificacion_resultado.setText(String.format("%.2f", mejorCalificacion));

        }
        textView_promedio_grupo_resultado.setText(String.format("%.2f", promedioGrupo));
        textView_total_calificaciones.setText("Cantidad de calificaciones capturadas: " + totalCalificaciones);

        editText_calificacion.setText("");

        // Recolorear el promedio si es mayor a 70 con green_approved y menor a 70 con red_disapproved
        if (promedioGrupo >= 70) {
            textView_promedio_grupo_resultado.setTextColor(getResources().getColor(R.color.green_approved));
        }else{
            textView_promedio_grupo_resultado.setTextColor(getResources().getColor(R.color.red_disapproved));
        }

        if(mejorCalificacion >= 70){
            textView_mejor_calificacion_resultado.setTextColor(getResources().getColor(R.color.green_approved));
        }else{
            textView_mejor_calificacion_resultado.setTextColor(getResources().getColor(R.color.red_disapproved));
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

        button_capturar = findViewById(R.id.button_capturar);
        editText_calificacion = findViewById(R.id.editText_calificacion);
        textView_total_calificaciones = findViewById(R.id.textView_total_calificaciones);
        textView_mejor_calificacion_resultado = findViewById(R.id.textView_mejor_calificacion_resultado);
        textView_promedio_grupo_resultado = findViewById(R.id.textView_promedio_grupo_resultado);

        button_capturar.setOnClickListener(v -> capturarCalificacion());



    }
}