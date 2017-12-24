package com.moictab.yonunca.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import com.moictab.yonunca.R;

public class NameDialog extends DialogFragment {

    public static final String NAME = "NAME";
    public static final String DEFAULT = "DEFAULT";
    public static final String TAG = "NameDialog";

    private EditText etName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View rootView = getActivity().getLayoutInflater().inflate(R.layout.dialog_name, null);
        etName = rootView.findViewById(R.id.et_name);

        builder.setView(rootView).setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                preferences.edit().putString(NAME, etName.getText().toString()).apply();
            }
        });

        return builder.create();
    }
}
