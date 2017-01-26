package com.adds.userInterface.applicationUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adds.R;
import com.adds.modalClasses.DSBankAccModal;

import java.util.ArrayList;

/**
 * Created by Rolbin on January 26 2017.
 */

public class DSDecryptedDataViewerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.decrypted_data_layout, container, false);
        ArrayList<String> labels = (ArrayList<String>) getArguments().getStringArrayList("labels");
        int type = getArguments().getInt("type");
        DSBankAccModal modal = (DSBankAccModal) getArguments().getSerializable("modal");
        if (type == 1) {

        }
        return mainView;
    }
}
