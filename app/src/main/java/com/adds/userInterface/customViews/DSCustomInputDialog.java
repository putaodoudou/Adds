package com.adds.userInterface.customViews;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.adds.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rolbin
 */
public class DSCustomInputDialog extends DialogFragment {
    private ListView mListView;
    private DSInputDialogAdapter mAdapter;
    private List<String> mDataList;
    private int mDialogType;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.ds_custom_input_dialog);
        mListView = (ListView) dialog.findViewById(R.id.input_listview);

        mDataList = getArguments().getStringArrayList("");
        mAdapter = new DSInputDialogAdapter(getActivity(), mDataList, mDialogType);
        mListView.setAdapter(mAdapter);

        return dialog;
    }

    /**
     * @author Rolbin
     */
    private class DSInputDialogAdapter extends BaseAdapter {
        private Context mContext;
        private List<String> mFieldsList;
        private int mType;

        public DSInputDialogAdapter(Context context, List<String> list, int dialogType) {
            this.mContext = context;
            this.mFieldsList = list;
            this.mType = dialogType;
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

            if (position == mFieldsList.size()) {
                holder.confirmButton.setVisibility(View.VISIBLE);
            } else {
                holder.confirmButton.setVisibility(View.GONE);
            }

            holder.confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> dataList = new ArrayList<String>();
                    switch (mDialogType) {
                        case 6:
                            passListviewData(6);
                            break;
                        case 1:
                            passListviewData(6);
                            break;
                        case 2:
                            passListviewData(6);
                            break;
                        case 3:
                            passListviewData(6);
                            break;
                        default:
                            passListviewData(6);
                            break;
                    }

                }
            });

            return convertView;
        }

        private void passListviewData(int number) {
            List<String> dataList = new ArrayList<>();
            for (int count = 0; count < number; count++) {
                View view = getViewByPosition(count, mListView);
                ViewHolder holder = (ViewHolder) view.getTag();
                dataList.add(holder.inputField.getText().toString());
            }
        }

        private View getViewByPosition(int position, ListView listView) {
            final int firstListItemPosition = listView.getFirstVisiblePosition();
            final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

            if (position < firstListItemPosition || position > lastListItemPosition) {
                return listView.getAdapter().getView(position, null, listView);
            } else {
                final int childIndex = position - firstListItemPosition;
                return listView.getChildAt(childIndex);
            }
        }

        private void initializeView(View view) {
            ViewHolder holder = new ViewHolder();

            holder.fieldName = (TextView) view.findViewById(R.id.text_name);
            holder.inputField = (EditText) view.findViewById(R.id.input_field);
            holder.confirmButton = (Button) view.findViewById(R.id.confirm_btn);

            view.setTag(holder);
        }

        private class ViewHolder {
            private TextView fieldName;
            private EditText inputField;
            private Button confirmButton;
        }

    }

}
