package mx.tecnm.chihuahua2.moviles.internalstorage;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;


public class MainActivity extends AppCompatActivity {

    EditText editText_producto;
    EditText editText_multiline_productos;
    Button button_guardarProducto;
    Button button_verListado;
    Button button_borrar;

    // declaracion de objetos para manipulacion de archivos
    FileOutputStream outputStream;
    FileInputStream inputStream;


    private void updateList(){

        //Abrir archivo en modo lectura

        try {

            // Verificar que el archivo existe
            File file = new File(getFilesDir(), "productos.txt");
            if (file.exists()) {
                inputStream = openFileInput("productos.txt");
                //Leer los datos del archivo para agregarlo al listado en el multiline

                StringBuilder sb = new StringBuilder();

                int i;

                while((i = inputStream.read()) != -1){
                    sb.append((char)i);
                }
                // Una vez escrito el contenido del archivo, mostrar los productos en el multiline y cerrar el stream de entrada;
                inputStream.close();

                editText_multiline_productos.setText(sb.toString());
            }else{
                // Soltar un Toast si el archivo no existe

                Toast.makeText(this, "El archivo no existe", Toast.LENGTH_SHORT).show();
            }



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

        //Vincular objetos con el código
        editText_producto = findViewById(R.id.editText_producto);
        editText_multiline_productos = findViewById(R.id.editText_multiline_productos);
        button_guardarProducto = findViewById(R.id.button_guardarProducto);
        button_verListado = findViewById(R.id.button_verListado);
        button_borrar = findViewById(R.id.button_borrar);


        //Darle funcionamiento al boton de guardado

        button_guardarProducto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Se obtiene el nombre del producto ingresado por el usuario
                        String producto = editText_producto.getText().toString();

                        //Se guarda en un archivo el producto al final del archivo


                        try {
                            outputStream = openFileOutput("productos.txt", Context.MODE_APPEND);

                            outputStream.write(producto.getBytes());
                            // Agregar salto de linea

                            outputStream.write(" \n".getBytes());


                            //Cerrar el archivo
                            outputStream.close();

                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        updateList();
                    }
                }
        );

        // Cargar el listado con el boton de ver listado

        button_verListado.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateList();
                    }

                }
        );

        // Borrar el listado con el boton de borrar

        button_borrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Borrar el contenido del archivo
                        File file = new File(getFilesDir(), "productos.txt");

                        if (file.exists()) {
                            // El archivo existe, intenta eliminarlo
                            boolean deleted = file.delete();

                            if (deleted) {
                                // Si se eliminó correctamente, actualizar la lista
                                editText_multiline_productos.setText(""); // Limpiar el multiline
                            } else {
                                // Si la eliminación falla, mostrar un mensaje (opcional)
                                Toast.makeText(MainActivity.this, "No se pudo eliminar el archivo", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Si el archivo no existe, mostrar un mensaje (opcional)
                            Toast.makeText(MainActivity.this, "El archivo no existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}