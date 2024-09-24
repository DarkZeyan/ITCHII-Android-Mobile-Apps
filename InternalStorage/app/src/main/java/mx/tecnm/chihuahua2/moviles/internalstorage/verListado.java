package mx.tecnm.chihuahua2.moviles.internalstorage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class verListado extends AppCompatActivity {

    String[] listado;
    ListView listView_productos;

    FileInputStream inputStream;

    private void obtenerDatos(){



        try {

            // Verificar que el archivo exista

            if(!new File(getFilesDir(), "productos.txt").exists()){
               Toast.makeText(this, "El archivo no existe", Toast.LENGTH_SHORT).show();
               finish();
               return;
            }

            // Abrir archivo en modo lectura

            // Leer los datos del archivo

            inputStream = openFileInput("productos.txt");

            // Verificar que el archivo existe

            // Obtener los datos del archivo



            StringBuilder sb = new StringBuilder();
            int i;

            while((i = inputStream.read()) != -1) {
                sb.append((char) i);
            }

            // Una vez escrito el contenido del archivo, mostrar los productos en el multiline y cerrar el stream de entrada;
            inputStream.close();

            String details[] = sb.toString().split(" \n");

            listado = new String[details.length];

            System.arraycopy(details, 0, listado, 0, details.length);


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_listado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Vinculacion de objetos
        listView_productos = findViewById(R.id.listView_productos);

        // obtener los datos
        obtenerDatos();

        // Declarar e iniciar objeto de tipo List
        final List<String> listaProductos = new ArrayList<String>(Arrays.asList(listado));

        // Llenar el listView usando un adaptador de datos
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaProductos);

        // Asignar adapter al listview
        listView_productos.setAdapter(adapter);


    }
}