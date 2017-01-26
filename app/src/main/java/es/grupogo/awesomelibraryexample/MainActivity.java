package es.grupogo.awesomelibraryexample;

import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import es.grupogo.awesomelibrary.AwesomeFunction;
import es.grupogo.awesomelibrary.BadgeView;
import es.grupogo.awesomelibrary.LocationManager;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AwesomeFunction
        int dp = 20;
        int pixels = AwesomeFunction.dpToPx(this, dp);

        //Badgeview
        BadgeView badgeView = (BadgeView) findViewById(R.id.badgeview);
        badgeView.setBadgeText("Text");
        badgeView.setTextColor(Color.WHITE);
        badgeView.setBadgeTextSize(18);
        badgeView.setBadgeColor(Color.BLUE);

        locationManager = new LocationManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.startLocationUpdates(new LocationManager.OnLocationChangeListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("latitude", String.valueOf(location.getLatitude()));
                Log.i("longitude", String.valueOf(location.getLongitude()));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.stopLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==LocationManager.REQ_LOCATION_PERMISSION) {
            new LocationManager(this).startLocationUpdates(new LocationManager.OnLocationChangeListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.i("latitude", String.valueOf(location.getLatitude()));
                    Log.i("longitude", String.valueOf(location.getLongitude()));
                }
            });
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
