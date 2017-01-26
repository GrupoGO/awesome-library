package es.grupogo.awesomelibrary;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by Carlos Olmedo on 25/1/17.
 */

public class CustomAlertDialog extends DialogFragment implements DialogInterface.OnClickListener{

    private static final String PARAM_TITLE = "param_title";
    private static final String PARAM_MESSAGE = "param_message";
    private static final String PARAM_POSITIVE = "param_positive";
    private static final String PARAM_NEGATIVE = "param_negative";
    private static final String PARAM_NEUTRAL = "param_neutral";
    private static final String PARAM_ITEMS = "param_items";

    public interface CustomDialogCallback {

        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
        void onDialogNeutralClick(DialogFragment dialog);
        void onItemSelected(DialogFragment dialog, int position);
    }

    private String title, message, positiveButton, negativeButton, neutralButton;
    private CustomDialogCallback callback;
    private String[] items;


    public static CustomAlertDialog newInstance(String title, String message, String positiveButton, @Nullable String negativeButton, @Nullable String neutralButton) {

        Bundle args = new Bundle();
        args.putString(PARAM_TITLE, title);
        args.putString(PARAM_MESSAGE, message);
        args.putString(PARAM_POSITIVE, positiveButton);
        args.putString(PARAM_NEGATIVE, negativeButton);
        args.putString(PARAM_NEUTRAL, neutralButton);

        CustomAlertDialog fragment = new CustomAlertDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static CustomAlertDialog newInstance(String title, String[] items) {

        Bundle args = new Bundle();
        args.putString(PARAM_TITLE, title);
        args.putStringArray(PARAM_ITEMS, items);


        CustomAlertDialog fragment = new CustomAlertDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args!=null) {

            title = args.getString(PARAM_TITLE);
            message = args.getString(PARAM_MESSAGE);
            positiveButton = args.getString(PARAM_POSITIVE);
            negativeButton = args.getString(PARAM_NEGATIVE);
            neutralButton = args.getString(PARAM_NEUTRAL);
            items = args.getStringArray(PARAM_ITEMS);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Fragment targetFragment = getTargetFragment();
        if (targetFragment!=null) {
            try {
                callback = (CustomDialogCallback) getTargetFragment();
            } catch (ClassCastException e) {
                e.printStackTrace();
                Log.e("myApp", "Implementa AlertDialogClickListener en el fragment " + targetFragment.toString());
            }
        } else {
            Log.e("myApp", "TargetFragment es null");
            try {
                callback = (CustomDialogCallback) getActivity();
            } catch (ClassCastException e) {
                e.printStackTrace();
                Log.e("myApp", "Implementa AlertDialogClickListener en la activity " + getActivity().toString());
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (title != null) {

            builder.setTitle(title);
        }

        if (message != null) {

            builder.setMessage(message);
        }

        if (positiveButton != null) {

            builder.setPositiveButton(positiveButton, this);
        }

        if (negativeButton != null) {

            builder.setNegativeButton(negativeButton, this);
        }

        if (neutralButton != null) {

            builder.setNeutralButton(neutralButton, this);
        }

        if (items != null) {

            builder.setItems(items, this);
        }

        AlertDialog dialog = builder.create();

        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        if (i>=0) {
            callback.onItemSelected(this, i);
            return;
        }

        switch (i) {
            case DialogInterface.BUTTON_POSITIVE:
                callback.onDialogPositiveClick(this);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                callback.onDialogNegativeClick(this);
                break;
            case DialogInterface.BUTTON_NEUTRAL:
                callback.onDialogNeutralClick(this);
                break;

        }
    }
}
