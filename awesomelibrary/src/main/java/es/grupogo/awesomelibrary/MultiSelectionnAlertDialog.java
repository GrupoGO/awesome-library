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

import java.util.Arrays;

/**
 * Created by Carlos Olmedo on 25/1/17.
 */

public class MultiSelectionnAlertDialog extends DialogFragment implements DialogInterface.OnClickListener, DialogInterface.OnMultiChoiceClickListener{

    private static final String PARAM_TITLE = "param_title";
    private static final String PARAM_POSITIVE = "param_positive";
    private static final String PARAM_NEGATIVE = "param_negative";
    private static final String PARAM_ITEMS = "param_items";
    private static final String PARAM_SELECTED_ITEMS = "param_selected_items";



    public interface CustomDialogCallback {

        void onDialogPositiveClick(DialogFragment dialog, boolean[] selectedItems);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    private String title, positiveButtonString, negativeButtonString;
    private CustomDialogCallback callback;
    private String[] items;
    private boolean[] selectedItems;


    public static MultiSelectionnAlertDialog newInstance(String title, String[] items, @Nullable boolean[] selectedItems, String positiveButtonString, String negativeButtonString) {

        Bundle args = new Bundle();
        args.putString(PARAM_TITLE, title);
        args.putString(PARAM_POSITIVE, positiveButtonString);
        args.putString(PARAM_NEGATIVE, negativeButtonString);
        args.putStringArray(PARAM_ITEMS, items);
        args.putBooleanArray(PARAM_SELECTED_ITEMS, selectedItems);

        MultiSelectionnAlertDialog fragment = new MultiSelectionnAlertDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args!=null) {

            title = args.getString(PARAM_TITLE);
            positiveButtonString = args.getString(PARAM_POSITIVE);
            negativeButtonString = args.getString(PARAM_NEGATIVE);
            items = args.getStringArray(PARAM_ITEMS);
            selectedItems = args.getBooleanArray(PARAM_SELECTED_ITEMS);

            if (items!=null && selectedItems==null) {
                selectedItems = new boolean[items.length];
                Arrays.fill(selectedItems, Boolean.FALSE);
            }
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

        if (positiveButtonString != null) {

            builder.setPositiveButton(positiveButtonString, this);
        }

        if (negativeButtonString != null) {

            builder.setNegativeButton(negativeButtonString, this);
        }

        if (items != null) {

            if (selectedItems!=null) {
                builder.setMultiChoiceItems(items, selectedItems, this);
            }
        }

        AlertDialog dialog = builder.create();

        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        switch (i) {
            case DialogInterface.BUTTON_POSITIVE:
                callback.onDialogPositiveClick(this, selectedItems);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                callback.onDialogNegativeClick(this);
                break;

        }
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int position, boolean selected) {
        selectedItems[position] = selected;
    }
}
