package mx.tecnm.chihuahua2.moviles.permisosejecucion;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.Button;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity  implements  View.OnClickListener{

    private static final int PERMISSION_REQUEST_CODE = 200;
    private Button button_revisarPermisos;
    private Button button_solicitarPermisos;
    private View view;


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

        button_revisarPermisos = findViewById(R.id.button_revisarPermisos);
        button_revisarPermisos.setOnClickListener(this);
        button_solicitarPermisos = findViewById(R.id.button_solicitarPermisos);
        button_solicitarPermisos.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        view = v;
        int id = v.getId();

        if(id == R.id.button_revisarPermisos){
            if(revisarPermisos()){
                Snackbar.make(view, "Permisos concedidos previamente", Snackbar.LENGTH_LONG).show();
            }else{
                Snackbar.make(view, "Permisos denegados, debes solicitarlos", Snackbar.LENGTH_LONG).show();
            }
        } else if (id == R.id.button_solicitarPermisos) {
            if(!revisarPermisos()){
                solicitarPermisos();
            }else{
                Snackbar.make(view, "Permisos concedidos previamente", Snackbar.LENGTH_LONG).show();
            }

        }
    }

    private boolean revisarPermisos(){
            int resultLocation = checkSelfPermission(ACCESS_FINE_LOCATION);
            int resultCamera = checkSelfPermission(CAMERA);

            return resultLocation == PackageManager.PERMISSION_GRANTED && resultCamera == PackageManager.PERMISSION_GRANTED;
    }

    private void solicitarPermisos(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0){
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(locationAccepted && cameraAccepted){
                        Snackbar.make(view, "Permisos concedidos, ahora puedes acceder a la camara y la ubicacion", Snackbar.LENGTH_LONG).show();
                    }else{
                        Snackbar.make(view, "Permisos denegados. No puedes acceder a la camara y la ubicacion", Snackbar.LENGTH_LONG).show();

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)){
                                showMessageOKCancel("Necesitas conceder permisos para acceder a la camara y la ubicacion",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA}, PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener){
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }
}