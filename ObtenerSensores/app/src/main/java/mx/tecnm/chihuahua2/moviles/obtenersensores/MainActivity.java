package mx.tecnm.chihuahua2.moviles.obtenersensores;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView_contador;
    int contador = 0;
    // Objeto para la gestion de sensores
    private SensorManager sensorManager;
    // Lista para el guardado del listado
    ListView listView_sensores;

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

        listView_sensores = findViewById(R.id.listView_sensores);
//        Para la identificacion de los sensores que estan disponibles en el dispositivo,
//        se debe obtener una referencia al servicio del sensor

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // Despues de la instanciacion del sensorManager se obtiene la lista de sensores disponibles
        List<Sensor> listaSensores = sensorManager.getSensorList(Sensor.TYPE_ALL);

        contador = listaSensores.size();
        textView_contador = findViewById(R.id.textView_contador);
        textView_contador.setText(getString(R.string.textView_contador)+String.valueOf(contador));


//        Se crea un adaptador, el cual es un mecanismo de Android que funciona como puente entre nuestros datos
        // y las vistas contenidas en el ListView (lista_sensores), puede ser tambien un GridView o un Spinner

        final ArrayAdapter<Sensor> adaptador = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,listaSensores);

        // Se le asignan los datos de la listview al adaptador
        listView_sensores.setAdapter(adaptador);



    }
}