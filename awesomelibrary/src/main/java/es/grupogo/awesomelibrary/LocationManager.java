package es.grupogo.awesomelibrary;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;



import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Carlos Olmedo on 26/1/17.
 */

public class LocationManager  {

    public interface OnLocationChangeListener {
        void onLocationChanged(Location location);
    }

    public static final int REQ_LOCATION_PERMISSION = 211;
    private GoogleApiClient mGoogleApiClient;
    private Activity activity;
    private OnLocationChangeListener listener;
    private LocationCallbacksManager locationCallbacksManager = new LocationCallbacksManager();

    public LocationManager(Activity activity) {

        this.activity = activity;

        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(locationCallbacksManager)
                .addOnConnectionFailedListener(locationCallbacksManager)
                .addApi(LocationServices.API)
                .build();


    }

    public void startLocationUpdates(OnLocationChangeListener listener) {
        this.listener = listener;
        if (mGoogleApiClient.isConnected()) {

            getLocationWithPermission(activity);
        } else {

            mGoogleApiClient.connect();
        }
    }

    public void stopLocationUpdates() {
        this.listener = null;
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, locationCallbacksManager);
        mGoogleApiClient.disconnect();
    }


    @SuppressWarnings("all")
    private void getLocation() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationCallbacksManager);

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            if (listener!=null) {
                listener.onLocationChanged(mLastLocation);
            }
        }
    }

    //Se necesita implementar el permission result en la activity
    private void getLocationWithPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(activity, permissions, REQ_LOCATION_PERMISSION);
        } else {
            getLocation();
        }
    }


    private class LocationCallbacksManager implements GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener,
            LocationListener {

        @Override
        public void onConnected(@Nullable Bundle bundle) {

            getLocationWithPermission(activity);
        }

        @Override
        public void onConnectionSuspended(int i) {

            Log.i("LocationManager", "Servicio caído. Reintentando...");
        }


        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Toast.makeText(activity, "No se pudo conectar", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLocationChanged(Location location) {

            if (location != null) {

                if (listener!=null) {
                    listener.onLocationChanged(location);
                }
            }
        }
    }


}
