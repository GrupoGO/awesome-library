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

public class SingleSelectionAlertDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private static final String PARAM_TITLE = "param_title";
    private static final String PARAM_POSITIVE = "param_positive";
    private static final String PARAM_NEGATIVE = "param_negative";
    private static final String PARAM_ITEMS = "param_items";
    private static final String PARAM_CHECKED_ITEM = "param_checked_item";


    public interface CustomDialogCallback {

        void onDialogPositiveClick(DialogFragment dialog, int selectedItem);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    private String title, positiveButtonString, negativeButtonString;
    private CustomDialogCallback callback;
    private String[] items;
    private int checkedItem = -1;


    public static SingleSelectionAlertDialog newInstance(String title, String[] items, int checkedItem, String positiveButtonString, String negativeButtonString) {

        Bundle args = new Bundle();
        args.putString(PARAM_TITLE, title);
        args.putString(PARAM_POSITIVE, positiveButtonString);
        args.putString(PARAM_NEGATIVE, negativeButtonString);
        args.putStringArray(PARAM_ITEMS, items);
        args.putInt(PARAM_CHECKED_ITEM, checkedItem);

        SingleSelectionAlertDialog fragment = new SingleSelectionAlertDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static SingleSelectionAlertDialog newInstance(String title, String[] items, String positiveButtonString, String negativeButtonString) {

        Bundle args = new Bundle();
        args.putString(PARAM_TITLE, title);
        args.putString(PARAM_POSITIVE, positiveButtonString);
        args.putString(PARAM_NEGATIVE, negativeButtonString);
        args.putStringArray(PARAM_ITEMS, items);
        args.putInt(PARAM_CHECKED_ITEM, -1);

        SingleSelectionAlertDialog fragment = new SingleSelectionAlertDialog();
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
            checkedItem = args.getInt(PARAM_CHECKED_ITEM);

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
             builder.setSingleChoiceItems(items, checkedItem, this);
        }

        AlertDialog dialog = builder.create();

        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        if (i>=0) {
            checkedItem = i;
            return;
        }

        switch (i) {
            case DialogInterface.BUTTON_POSITIVE:
                callback.onDialogPositiveClick(this, checkedItem);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                callback.onDialogNegativeClick(this);
                break;

        }
    }

}
