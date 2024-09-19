package mx.tecnm.chihuahua2.moviles.internalstorage;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editText_producto;
    EditText editText_multiline_productos;
    Button button_guardarProducto;
    Button button_verListado;
    Button button_borrar;



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




    }
}