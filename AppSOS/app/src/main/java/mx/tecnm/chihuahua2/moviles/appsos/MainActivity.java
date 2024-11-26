package mx.tecnm.chihuahua2.moviles.appsos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView_contador;
    private Button button_sos;
    private GPSReceiver gpsReceiver;
    private LocationManager locationManager;
    double lat = 0.0, lon = 0.0;
    int contador = 0;
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


        textView_contador = findViewById(R.id.textView_contador);
        button_sos = findViewById(R.id.button_sos);
        textView_contador.setText(getString(R.string.textView_contador)+contador);

        gpsReceiver = new GPSReceiver();
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        try{
            if(ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
            ){
             // Solicitar el permiso
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.SEND_SMS},
                        1
                );
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1.0F, gpsReceiver);
        }catch(SecurityException e){
            Toast.makeText(this, "Error de permisos de ubicación", Toast.LENGTH_SHORT).show();
        }

        button_sos.setOnClickListener(v -> {

            SmsManager smsManager = SmsManager.getDefault();
            String phoneNumber = "6143513346";
            String messageBody = "Mi ubicación actual es: Latitud: " + Double.toString(lat) + ", Longitud: " + Double.toString(lon);

            try{
                smsManager.sendTextMessage(phoneNumber, null, messageBody, null, null);
                Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                contador++;
                textView_contador.setText(getString(R.string.textView_contador)+contador);
            }catch(Exception e){
                Log.e("Error", e.getMessage());
                Toast.makeText(this, "Error al enviar el mensaje", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public class GPSReceiver implements LocationListener{
        @Override
        public void onLocationChanged(@NonNull android.location.Location location) {
            if(location != null){
             lat = location.getLatitude();
             lon = location.getLongitude();
                Toast.makeText(MainActivity.this, "Listo para enviar", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "No se puede enviar la ubicación aun", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onLocationChanged(@NonNull List<Location> locations){
            LocationListener.super.onLocationChanged(locations);
        }
        @Override
        public void onFlushComplete(int requestCode){
            LocationListener.super.onFlushComplete(requestCode);
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras){
            LocationListener.super.onStatusChanged(provider,status,extras);
        }
        @Override
        public void onProviderEnabled(@NonNull String provider){
            LocationListener.super.onProviderEnabled(provider);
            Toast.makeText(MainActivity.this,  "GPS habilitado", Toast.LENGTH_LONG).show();
        }
        @Override
        public void onProviderDisabled(@NonNull String provider){
            LocationListener.super.onProviderDisabled(provider);
            Toast.makeText(MainActivity.this,  "GPS deshabilitado", Toast.LENGTH_LONG).show();
        }
    }
}