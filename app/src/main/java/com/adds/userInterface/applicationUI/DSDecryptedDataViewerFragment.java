package com.adds.userInterface.applicationUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adds.R;
import com.adds.modalClasses.DSBankAccModal;
import com.adds.modalClasses.DSCardModal;
import com.adds.modalClasses.DSLoginPasswordModal;
import com.adds.modalClasses.DSOthersModal;
import com.adds.userInterface.uiHelper.DSUiUtils;

import java.util.ArrayList;

/**
 * Created by Rolbin on January 26 2017.
 */

public class DSDecryptedDataViewerFragment extends Fragment {
    private TextView labelOne;
    private TextView labelTwo;
    private TextView labelThree;
    private TextView labelFour;
    private TextView labelFive;
    private TextView labelSix;
    private TextView fieldOne;
    private TextView fieldTwo;
    private TextView fieldThree;
    private TextView fieldFour;
    private TextView fieldFive;
    private TextView fieldSix;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.decrypted_data_layout, container, false);
        initializeViews(mainView);
        ArrayList<String> labels = getArguments().getStringArrayList("labels");
        int type = getArguments().getInt("type");
        if (type == 1) {
            showBankDetails(labels);
        } else if (type == 2) {
            showCardDetails(labels);
            DSUiUtils.hideView(labelFive, labelSix, fieldFive, fieldSix);
        } else if (type == 3) {
            showLoginDetails(labels);
            DSUiUtils.hideView(labelSix, labelFive, labelFour, fieldSix, fieldFive, fieldFour);
        } else if (type == 4) {
            showOtherDetails(labels);
            DSUiUtils.hideView(labelSix, labelFive, labelFour, labelThree, fieldSix, fieldFive, fieldFour, fieldThree);
        }
        return mainView;
    }

    private void initializeViews(View mainView) {
        labelOne = (TextView) mainView.findViewById(R.id.label_1);
        labelTwo = (TextView) mainView.findViewById(R.id.label_2);
        labelThree = (TextView) mainView.findViewById(R.id.label_3);
        labelFour = (TextView) mainView.findViewById(R.id.label_4);
        labelFive = (TextView) mainView.findViewById(R.id.label_5);
        labelSix = (TextView) mainView.findViewById(R.id.label_6);
        fieldOne = (TextView) mainView.findViewById(R.id.field_1);
        fieldTwo = (TextView) mainView.findViewById(R.id.field_2);
        fieldThree = (TextView) mainView.findViewById(R.id.field_3);
        fieldFour = (TextView) mainView.findViewById(R.id.field_4);
        fieldFive = (TextView) mainView.findViewById(R.id.field_5);
        fieldSix = (TextView) mainView.findViewById(R.id.field_6);
    }

    private void showBankDetails(ArrayList<String> labels) {
        DSBankAccModal modal = (DSBankAccModal) getArguments().getSerializable("modal");
        labelOne.setText(labels.get(0));
        labelTwo.setText(labels.get(1));
        labelThree.setText(labels.get(2));
        labelFour.setText(labels.get(3));
        labelFive.setText(labels.get(4));
        labelSix.setText(labels.get(5));
        fieldOne.setText(modal.getmAccountName());
        fieldTwo.setText(modal.getmBankName());
        fieldThree.setText(modal.getmAccNo());
        fieldFour.setText(modal.getmPassword());
        fieldFive.setText(modal.getmIfscCode());
        fieldSix.setText(modal.getmRemarks());
    }

    private void showCardDetails(ArrayList<String> labels) {
        DSCardModal modal = (DSCardModal) getArguments().getSerializable("modal");
        labelOne.setText(labels.get(0));
        labelTwo.setText(labels.get(1));
        labelThree.setText(labels.get(2));
        labelFour.setText(labels.get(3));
        fieldOne.setText(modal.getmCardName());
        fieldTwo.setText(modal.getmCardNo());
        fieldThree.setText(modal.getmCardPin());
        fieldFour.setText(modal.getmCvvCode());
    }

    private void showLoginDetails(ArrayList<String> labels) {
        DSLoginPasswordModal modal = (DSLoginPasswordModal) getArguments().getSerializable("modal");
        labelOne.setText(labels.get(0));
        labelTwo.setText(labels.get(1));
        labelThree.setText(labels.get(2));
        fieldOne.setText(modal.getmLoginName());
        fieldTwo.setText(modal.getmUserName());
        fieldThree.setText(modal.getmPassword());
        fieldFour.setText(modal.getmPassword());
    }

    private void showOtherDetails(ArrayList<String> labels) {
        DSOthersModal modal = (DSOthersModal) getArguments().getSerializable("modal");
        labelOne.setText(labels.get(0));
        labelTwo.setText(labels.get(1));
        fieldOne.setText(modal.getmName());
        fieldTwo.setText(modal.getmData());
    }
}
