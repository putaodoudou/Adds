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
import android.widget.Toast;

import com.adds.R;
import com.adds.helpers.DSModalSelectionHelper;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author Rolbin
 */
public class DSCustomInputDialog extends DialogFragment {
    private ListView mListView;
    private DSInputDialogAdapter mAdapter;
    private ArrayList<String> mDataList;
    private Button confirmButton;
    private int mDialogType;
    private boolean isAllFieldsAreFilled = true;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.ds_custom_input_dialog);
        mListView = (ListView) dialog.findViewById(R.id.input_listview);
        confirmButton = (Button) dialog.findViewById(R.id.confirm_btn);

        mDataList = getArguments().getStringArrayList("data");
        mDialogType = getArguments().getInt("type");
        mAdapter = new DSInputDialogAdapter(getActivity(), mDataList);
        mListView.setAdapter(mAdapter);

        return dialog;
    }

    /**
     * @author Rolbin
     */
    private class DSInputDialogAdapter extends BaseAdapter {
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

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> dataList = getListviewData(mDataList.size());
                    if (!isAllFieldsAreFilled) {
                        //ShowPopup
                        Toast.makeText(mContext, "All fields are mandatory", Toast.LENGTH_LONG).show();
                        return;
                    }
                    long result = 0;
                    switch (mDialogType) {
                        case 1:
                            try {
                                result = DSModalSelectionHelper.encryptBankModalData(dataList, mContext);
                            } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | BadPaddingException
                                    | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException | IOException | UnrecoverableKeyException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                result = DSModalSelectionHelper.encryptCardData(dataList, mContext);
                            } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | BadPaddingException
                                    | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException | IOException | UnrecoverableKeyException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
                                result = DSModalSelectionHelper.encryptLoginData(dataList, mContext);
                            } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | BadPaddingException
                                    | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException | IOException | UnrecoverableKeyException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            try {
                                result = DSModalSelectionHelper.encryptOtherData(dataList, mContext);
                            } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | BadPaddingException
                                    | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException | IOException | UnrecoverableKeyException e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            break;
                    }

                    if (result == -1) {
                        //show error
                        Toast.makeText(mContext, "error occured", Toast.LENGTH_LONG).show();
                    } else {
                        dismiss();
                    }
                }
            });
            return convertView;
        }


        private List getListviewData(int number) {
            List<String> dataList = new ArrayList<>();
            for (int count = 0; count < number; count++) {
                View view = getViewByPosition(count, mListView);
                ViewHolder holder = (ViewHolder) view.getTag();
                String fieldData = holder.inputField.getText().toString();
                dataList.add(fieldData);
                if (fieldData == null || fieldData.isEmpty()) {
                    isAllFieldsAreFilled = false;
                }
            }
            return dataList;
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

            view.setTag(holder);
        }

        private class ViewHolder {
            private TextView fieldName;
            private EditText inputField;
        }

    }

}
