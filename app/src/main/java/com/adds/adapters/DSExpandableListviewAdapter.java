package com.adds.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adds.R;
import com.adds.modalClasses.DSBankAccModal;
import com.adds.modalClasses.DSCardModal;
import com.adds.modalClasses.DSChildModal;
import com.adds.modalClasses.DSHeaderModal;
import com.adds.modalClasses.DSLoginPasswordModal;
import com.adds.modalClasses.DSOthersModal;
import com.adds.userInterface.customViews.DSRoundedLetterView;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 */
public class DSExpandableListviewAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private DSChildModal mChildModal;
    private ArrayList<DSHeaderModal> mHeaderModal;

    public DSExpandableListviewAdapter(Context context, ArrayList<DSHeaderModal> parent, DSChildModal child) {
        this.mContext = context;
        this.mHeaderModal = parent;
        this.mChildModal = child;
    }

    private static int randomNumberGenerator(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    @Override
    public int getGroupCount() {
        if (mHeaderModal != null) {
            return mHeaderModal.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = 0;
        switch (groupPosition) {
            case 0:
                size = mChildModal.getmBankAccModal().size();
                break;
            case 1:
                size = mChildModal.getmCardModal().size();
                break;
            case 2:
                size = mChildModal.getmLoginPasswordModal().size();
                break;
            case 3:
                size = mChildModal.getmOthersModal().size();
                break;
            default:
                size = 0;
                break;
        }
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        switch (groupPosition) {
            case 0:
                return mChildModal.getmBankAccModal();
            case 1:
                return mChildModal.getmCardModal();
            case 2:
                return mChildModal.getmLoginPasswordModal();
            case 3:
                return mChildModal.getmOthersModal();
            default:
                return null;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        switch (groupPosition) {
            case 0:
                return mChildModal.getmBankAccModal().get(childPosition);
            case 1:
                return mChildModal.getmCardModal().get(childPosition);
            case 2:
                return mChildModal.getmLoginPasswordModal().get(childPosition);
            case 3:
                return mChildModal.getmOthersModal().get(childPosition);
            default:
                return null;
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ds_exp_lv_group_row, parent, false);
            initializeGroupView(convertView);
        }
        holder = (ViewHolder) convertView.getTag();

        DSHeaderModal data = mHeaderModal.get(groupPosition);
        holder.groupName.setText(data.getmHeader());


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ds_exp_lv_child_row, parent, false);
            initializeChildView(convertView);
        }
        holder = (ViewHolder) convertView.getTag();
        switch (groupPosition) {
            case 0:
                DSBankAccModal bankdata = mChildModal.getmBankAccModal().get(childPosition);
                holder.letterView.setBackgroundColor(getRandomColor());
                holder.childName.setText(bankdata.getmAccountName());
                holder.dataOne.setText(bankdata.getmBankName());
                holder.dataTwo.setText(bankdata.getmPassword());
                holder.dataThree.setText(bankdata.getmAccNo());
                holder.dataFour.setText(bankdata.getmIfscCode());
                holder.dataFive.setText(bankdata.getmRemarks());
                break;
            case 1:
                holder.letterView.setBackgroundColor(getRandomColor());
                DSCardModal cardData = mChildModal.getmCardModal().get(childPosition);
                holder.childName.setText(cardData.getmCardName());
                holder.dataOne.setText(String.valueOf(cardData.getmCardNo()));
                holder.dataTwo.setText(String.valueOf(cardData.getmCardPin()));
                holder.dataThree.setText(String.valueOf(cardData.getmCvvCode()));
                break;
            case 2:
                holder.letterView.setBackgroundColor(getRandomColor());
                DSLoginPasswordModal loginData = mChildModal.getmLoginPasswordModal().get(childPosition);
                holder.childName.setText(loginData.getmLoginName());
                holder.dataOne.setText(loginData.getmUserName());
                holder.dataTwo.setText(loginData.getmPassword());
                break;
            case 3:
                holder.letterView.setBackgroundColor(getRandomColor());
                DSOthersModal otherData = mChildModal.getmOthersModal().get(childPosition);
                holder.childName.setText(otherData.getmName());
                holder.dataOne.setText(otherData.getmData());
                break;
            default:
                break;
        }

        return convertView;
    }

    private void initializeGroupView(View view) {
        ViewHolder holder = new ViewHolder();
        holder.groupName = (TextView) view.findViewById(R.id.header_text);
        holder.groupIcon = (ImageView) view.findViewById(R.id.icon);

        view.setTag(holder);
    }

    private void initializeChildView(View view) {
        ViewHolder holder = new ViewHolder();
        holder.childName = (TextView) view.findViewById(R.id.name);
        holder.dataOne = (TextView) view.findViewById(R.id.data_one);
        holder.dataTwo = (TextView) view.findViewById(R.id.data_two);
        holder.dataThree = (TextView) view.findViewById(R.id.data_three);
        holder.dataFour = (TextView) view.findViewById(R.id.data_four);
        holder.dataFive = (TextView) view.findViewById(R.id.data_five);
        holder.letterView = (DSRoundedLetterView) view.findViewById(R.id.rlv_name_view);

        view.setTag(holder);
    }

    private int getRandomColor() {
        int[] colorArray = mContext.getResources().getIntArray(R.array.rounded_letter_colors);
        int randomNumber = randomNumberGenerator(0, colorArray.length - 1);
        return colorArray[randomNumber];
    }

    private class ViewHolder {
        TextView childName;
        TextView dataOne;
        TextView dataTwo;
        TextView dataThree;
        TextView dataFour;
        TextView dataFive;
        TextView groupName;
        ImageView groupIcon;
        DSRoundedLetterView letterView;
    }

}
