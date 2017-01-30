package es.grupogo.awesomelibrary.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Carlos Olmedo on 25/1/17.
 */

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private static final String PARAM_DAY = "param_day";
    private static final String PARAM_MONTH = "param_month";
    private static final String PARAM_YEAR = "param_year";

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        if (callback!=null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            callback.onDatePicked(getDialog(), calendar.getTime());
        }
    }

    public interface OnDateSetListener {

        void onDatePicked(Dialog dialog, Date date);
    }

    private int day, month, year;
    private OnDateSetListener callback;



    public static DatePickerDialogFragment newInstance(int day, int month, int year) {
        Bundle args = new Bundle();
        args.putInt(PARAM_DAY, day);
        args.putInt(PARAM_MONTH, month);
        args.putInt(PARAM_YEAR, year);

        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static DatePickerDialogFragment newInstance() {

        Bundle args = new Bundle();

        DatePickerDialogFragment fragment = new DatePickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args!=null) {

           day = args.getInt(PARAM_DAY);
           month = args.getInt(PARAM_MONTH);
           year = args.getInt(PARAM_YEAR);
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

        if (year==0 && month==0 && day==0) {

            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);


        return dialog;
    }
}
