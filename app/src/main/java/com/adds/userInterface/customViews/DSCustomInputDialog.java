package com.adds.userInterface.customViews;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.adds.R;

/**
 * Created by Rolbin on November 13 2016.
 */
public class DSCustomInputDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.ds_custom_input_dialog);
        return dialog;
    }
}
