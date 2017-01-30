package es.grupogo.awesomelibraryexample;

import android.app.Dialog;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

import es.grupogo.awesomelibrary.AwesomeFunction;
import es.grupogo.awesomelibrary.BadgeView;
import es.grupogo.awesomelibrary.dialogs.CustomAlertDialog;
import es.grupogo.awesomelibrary.dialogs.DatePickerDialogFragment;
import es.grupogo.awesomelibrary.LocationManager;
import es.grupogo.awesomelibrary.dialogs.MultiSelectionDialogFragment;
import es.grupogo.awesomelibrary.dialogs.SingleSelectionDialogFragment;
import es.grupogo.awesomelibrary.dialogs.TimePickerDialogFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        CustomAlertDialog.CustomDialogCallback,
        MultiSelectionDialogFragment.CustomDialogCallback,
        SingleSelectionDialogFragment.CustomDialogCallback,
        DatePickerDialogFragment.OnDateSetListener,
TimePickerDialogFragment.OnDateSetListener{

    private static final String TAG_DIALOG = "dialog";
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_1:
                CustomAlertDialog.newInstance("Titulo", "Mensaje", "OK", "Cancel", "Neutral").show(getSupportFragmentManager(), TAG_DIALOG);
                break;
            case R.id.button_2:
                CustomAlertDialog.newInstance("Titulo", new String[]{"Uno", "Dos", "Tres"}).show(getSupportFragmentManager(), TAG_DIALOG);
                break;
            case R.id.button_3:
                MultiSelectionDialogFragment.newInstance("Titulo", new String[]{"Uno", "Dos", "Tres"}, new boolean[]{false, true, true}, "Ok", "Cancel").show(getSupportFragmentManager(), TAG_DIALOG);
                break;
            case R.id.button_4:
                SingleSelectionDialogFragment.newInstance("Titulo", new String[]{"Uno", "Dos", "Tres"}, 0, "Ok", "Cancel").show(getSupportFragmentManager(), TAG_DIALOG);
                break;
            case R.id.button_5:
                DatePickerDialogFragment.newInstance().show(getSupportFragmentManager(), TAG_DIALOG);
                break;
            case R.id.button_6:
                TimePickerDialogFragment.newInstance().show(getSupportFragmentManager(), TAG_DIALOG);
                break;
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Toast.makeText(this, "Click positivo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, boolean[] selectedItems) {
        Toast.makeText(this, "Click selecccionados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int selectedItem) {
        Toast.makeText(this, "Click seleccionado " + selectedItem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "Click negativo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNeutralClick(DialogFragment dialog) {
        Toast.makeText(this, "Click neutral", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(DialogFragment dialog, int position) {
        Toast.makeText(this, "Item seleccionado " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDatePicked(Dialog dialog, Date date) {
        Toast.makeText(this, date.toString(), Toast.LENGTH_SHORT).show();
        Log.i("date dialog", date.toString());
    }

    @Override
    public void onTimeSet(Dialog dialog, int hour, int minutes) {

        Toast.makeText(this, String.format("%d horas %d minutos", hour, minutes), Toast.LENGTH_SHORT).show();
        Log.i("date dialog", String.format("%d horas %d minutos", hour, minutes));
    }
}
