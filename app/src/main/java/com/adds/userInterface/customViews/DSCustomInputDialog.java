package com.adds.userInterface.customViews;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.adds.R;
import com.adds.adapters.DSInputDialogAdapter;

import java.util.List;

/**
 * @author Rolbin
 */
public class DSCustomInputDialog extends DialogFragment {
    private ListView mListView;
    private DSInputDialogAdapter mAdapter;
    private List<String> mDataList;

    public static void setArguments() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.ds_custom_input_dialog);
        mAdapter = new DSInputDialogAdapter(getActivity(), mDataList);
        Button confirmButton = (Button) dialog.findViewById(R.id.confirm_btn);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return dialog;
    }

}
