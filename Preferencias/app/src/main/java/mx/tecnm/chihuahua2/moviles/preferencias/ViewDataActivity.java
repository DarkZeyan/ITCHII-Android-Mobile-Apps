package mx.tecnm.chihuahua2.moviles.preferencias;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewDataActivity extends AppCompatActivity {

    TextView textView_name2;
    TextView textView_date;
    ImageView imageView_selection;
    Button button_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView_name2 = findViewById(R.id.textView_name2);
        textView_date = findViewById(R.id.textView_date);
        imageView_selection = findViewById(R.id.imageView_selection);

        SharedPreferences preferences = getSharedPreferences("preferencias", MODE_PRIVATE);
        textView_name2.setText("Nombre: "+preferences.getString("name", ""));
        textView_date.setText("Fecha de nacimiento: "+preferences.getString("date", ""));

        String figure = preferences.getString("topic", "");
        if(figure.equals("plant")){
            imageView_selection.setImageResource(R.drawable.planta);
        }
        if(figure.equals("animal")){
            imageView_selection.setImageResource(R.drawable.animal);
        }
        if(figure.equals("figure")) {
            imageView_selection.setImageResource(R.drawable.figura);
        }


        button_return = findViewById(R.id.button_return);
        button_return.setOnClickListener(v -> {
            finish();
        });
    }
}