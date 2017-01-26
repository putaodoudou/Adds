package com.adds.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adds.R;
import com.adds.modalClasses.DSDisplayDataModal;
import com.adds.userInterface.applicationUI.DSDashBoard;
import com.adds.userInterface.customViews.DSRoundedLetterView;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 */
public class DSExpandableListviewAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ArrayList<ArrayList<String>> mChildModal;
    private ArrayList<DSDisplayDataModal> mHeaderModal;

    public DSExpandableListviewAdapter(Context context, ArrayList<DSDisplayDataModal> parent, ArrayList<ArrayList<String>> child) {
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
        if (mChildModal.get(groupPosition) != null) {
            return mChildModal.get(groupPosition).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mChildModal.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildModal.get(groupPosition).get(childPosition);
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

        DSDisplayDataModal data = mHeaderModal.get(groupPosition);
        holder.groupName.setText(data.getData());

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ds_exp_lv_child_row, parent, false);
            initializeChildView(convertView);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.letterView.setBackgroundColor(getRandomColor());
        holder.childName.setText(mChildModal.get(groupPosition).get(childPosition));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uniqueName = mChildModal.get(groupPosition).get(childPosition);
                ((DSDashBoard) mContext).onChildClick(uniqueName, groupPosition + 1);
            }
        });

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
        TextView groupName;
        ImageView groupIcon;
        DSRoundedLetterView letterView;
    }

}
