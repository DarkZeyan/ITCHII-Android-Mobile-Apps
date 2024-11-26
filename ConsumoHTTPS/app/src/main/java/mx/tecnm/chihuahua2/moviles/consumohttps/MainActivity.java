package mx.tecnm.chihuahua2.moviles.consumohttps;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mx.tecnm.chihuahua2.moviles.consumohttps.Cultivo;
import mx.tecnm.chihuahua2.moviles.consumohttps.CultivoAdapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

    private String url_gramineas = "https://moviles.zeyan.dev/gramineas.php";

    private RecyclerView recyclerView;
    private DividerItemDecoration dividerItemDecoration;
    private LinearLayoutManager linearLayoutManager;
    private List<Cultivo> cultivoList;
    private RecyclerView.Adapter adapter;


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

        // Inicializar los elementos de la interfaz de usuario

        recyclerView =  findViewById(R.id.main_list);
        cultivoList = new ArrayList<>();

        adapter = new CultivoAdapter(getApplicationContext(), cultivoList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        fetchData();

    }

    private void fetchData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url_gramineas, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Cultivo cultivo = new Cultivo();
                        cultivo.setId_graminea(jsonObject.getInt("id_graminea"));
                        cultivo.setNombre_comun(jsonObject.getString("nombre_comun"));
                        cultivo.setNombre_cientifico(jsonObject.getString("nombre_cientifico"));
                        cultivo.setDescripcion(jsonObject.getString("descripcion"));
                        cultivo.setImagen(jsonObject.getString("imagen"));
                        cultivoList.add(cultivo);
                        Toast.makeText(getApplicationContext(), cultivo.getNombre_comun(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

        Toast.makeText(getApplicationContext(), cultivoList.toString(), Toast.LENGTH_SHORT).show();



    }
}