package com.adds.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.adds.R;

import java.util.List;

/**
 * @author Rolbin
 */
public class DSInputDialogAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mFieldsList;

    public DSInputDialogAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mFieldsList = list;
    }

    @Override
    public int getCount() {
        if (mFieldsList != null) {
            return mFieldsList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mFieldsList != null) {
            return mFieldsList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.popup_row, parent, false);
            initializeView(convertView);
        }
        holder = (ViewHolder) convertView.getTag();

        holder.fieldName.setText(mFieldsList.get(position));
        holder.inputField.setHint(mFieldsList.get(position));

        return convertView;
    }

    private void initializeView(View view) {
        ViewHolder holder = new ViewHolder();

        holder.fieldName = (TextView) view.findViewById(R.id.text_name);
        holder.inputField = (EditText) view.findViewById(R.id.input_field);

        view.setTag(holder);
    }

    private class ViewHolder {
        private TextView fieldName;
        private EditText inputField;
    }
}
