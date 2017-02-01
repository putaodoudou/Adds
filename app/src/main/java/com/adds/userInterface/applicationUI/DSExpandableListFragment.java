package com.adds.userInterface.applicationUI;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;

import com.adds.R;
import com.adds.adapters.DSExpandableListviewAdapter;
import com.adds.database.DSDataBaseHelper;
import com.adds.database.DSDatabaseFieldNames;
import com.adds.modalClasses.DSDisplayDataModal;

import java.util.ArrayList;

/**
 * Created by 10745 on 2/1/2017.
 */

public class DSExpandableListFragment extends Fragment {
    private View mMainView;
    private FrameLayout mFrameLayout;
    private ExpandableListView mExpListView;
    private DSExpandableListviewAdapter mExpandableListAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.ds_exp_lv_fragment, container, false);
        mFrameLayout = (FrameLayout) mMainView.findViewById(R.id.content_frame);
        mExpListView = (ExpandableListView) mMainView.findViewById(R.id.expandable_lv);
        setDashBoardListData();
        return mMainView;
    }

    private void setDashBoardListData() {
//        if (DSSharedPreferencUtils.getBooleanPref("SAVED_DATA", getActivity())) {
        //UI elements

        ArrayList<DSDisplayDataModal> headerModal = new ArrayList<>();
        DSDisplayDataModal modal1 = new DSDisplayDataModal();
        DSDisplayDataModal modal2 = new DSDisplayDataModal();
        DSDisplayDataModal modal3 = new DSDisplayDataModal();
        DSDisplayDataModal modal4 = new DSDisplayDataModal();
        headerModal.add(modal1);
        headerModal.add(modal2);
        headerModal.add(modal3);
        headerModal.add(modal4);
        modal1.setDisplayData("Bank account details");
        modal1.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_account_balance_black_48dp));
        modal2.setDisplayData("Credit and Debit card details ");
        modal2.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_credit_card_black_48dp));
        modal3.setDisplayData("Login credential details");
        modal3.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_account_circle_black_48dp));
        modal4.setDisplayData("Other secure datas");
        modal4.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_event_note_black_48dp));

        ArrayList<ArrayList<String>> childModal = new ArrayList<>();
        DSDataBaseHelper dataBaseHelper = new DSDataBaseHelper(getActivity());
        Cursor cursor = dataBaseHelper.selectAllBankName();
        ArrayList<String> dataModals1 = new ArrayList<>();
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    dataModals1.add(cursor.getString(cursor.getColumnIndex(DSDatabaseFieldNames.ACC_NAME)));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        childModal.add(dataModals1);

        Cursor cursor2 = dataBaseHelper.selectAllCardName();
        ArrayList<String> dataModals2 = new ArrayList<>();
        if (cursor2 != null && cursor2.getCount() != 0) {
            if (cursor2.moveToFirst()) {
                do {
                    dataModals2.add(cursor2.getString(cursor2.getColumnIndex(DSDatabaseFieldNames.CARD_NAME)));
                } while (cursor2.moveToNext());
            }
        }
        cursor2.close();
        childModal.add(dataModals2);

        Cursor cursor3 = dataBaseHelper.selectAllLoginName();
        ArrayList<String> dataModals3 = new ArrayList<>();
        if (cursor3 != null && cursor3.getCount() != 0) {
            if (cursor3.moveToFirst()) {
                do {
                    dataModals3.add(cursor3.getString(cursor3.getColumnIndex(DSDatabaseFieldNames.LOGIN_NAME)));
                } while (cursor3.moveToNext());
            }
        }
        cursor3.close();
        childModal.add(dataModals3);

        Cursor cursor4 = dataBaseHelper.selectAllOtherName();
        ArrayList<String> dataModals4 = new ArrayList<>();
        if (cursor4 != null && cursor4.getCount() != 0) {
            if (cursor4.moveToFirst()) {
                do {
                    dataModals4.add(cursor4.getString(cursor4.getColumnIndex(DSDatabaseFieldNames.OTHER_DATA_NAME)));
                } while (cursor4.moveToNext());
            }
        }
        cursor4.close();
        childModal.add(dataModals4);

        mExpandableListAdapter = new DSExpandableListviewAdapter(getActivity(), headerModal, childModal);
        mExpListView.setAdapter(mExpandableListAdapter);

        mExpListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (!parent.isGroupExpanded(groupPosition)) {
                    parent.expandGroup(groupPosition);
                } else {
                    parent.collapseGroup(groupPosition);
                }
                return true;
            }
        });

//        } else {
//            todo show fallback message
//        }
    }

}
