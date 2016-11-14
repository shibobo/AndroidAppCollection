package com.shibobo.locationservicetest;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView locationTextView;
    private Button show_location;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationTextView=(TextView) findViewById(R.id.location);
        show_location=(Button) findViewById(R.id.show_location);
        show_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
                List<String> providerList=locationManager.getProviders(true);
                if (providerList.contains(LocationManager.GPS_PROVIDER)){
                    provider=LocationManager.GPS_PROVIDER;
                }else if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
                    provider=LocationManager.NETWORK_PROVIDER;
                }else{
                    Toast.makeText(MainActivity.this,"no provider found",Toast.LENGTH_SHORT).show();
                    return;
                }
                Location location=locationManager.getLastKnownLocation(provider);
                if (location!=null){
                    showLocation(location);
                }
                locationManager.requestLocationUpdates(provider,5000,1,locationListener);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager!=null){
            locationManager.removeUpdates(locationListener);
        }
    }
    LocationListener locationListener=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    public void showLocation(Location location){
        String currentLocation="Latitude is"+location.getLatitude()+
                               "\nLongitude is"+location.getLongitude();
        locationTextView.setText(currentLocation);
    }
}
