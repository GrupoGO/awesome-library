package es.grupogo.awesomelibrary.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Carlos Olmedo on 25/1/17.
 */

public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    public interface OnDateSetListener {

        void onTimeSet(Dialog dialog, int hour, int minutes);
    }

    private OnDateSetListener callback;


    public static TimePickerDialogFragment newInstance() {

        Bundle args = new Bundle();

        TimePickerDialogFragment fragment = new TimePickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args!=null) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Fragment targetFragment = getTargetFragment();
        if (targetFragment!=null) {
            try {
                callback = (OnDateSetListener) getTargetFragment();
            } catch (ClassCastException e) {
                e.printStackTrace();
                Log.e("myApp", "Implementa OnDateSetListener en el fragment " + targetFragment.toString());
            }
        } else {
            Log.e("myApp", "TargetFragment es null");
            try {
                callback = (OnDateSetListener) getActivity();
            } catch (ClassCastException e) {
                e.printStackTrace();
                Log.e("myApp", "Implementa OnDateSetListener en la activity " + getActivity().toString());
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        TimePickerDialog dialog = new TimePickerDialog(getActivity(), this, 0, 0, true);

        return dialog;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
        if (callback!=null) {
            callback.onTimeSet(getDialog(), hour, minutes);
        }
    }
}
