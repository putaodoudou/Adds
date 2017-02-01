package com.adds.userInterface.applicationUI;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.adds.R;
import com.adds.application.DSApplication;


/**
 * @author Rolbin
 */
public class DSSecureLockScreen extends Activity {

    private String mUserPassword = "android";
    private TextView mStatusText;
    private EditText mPasswordInputField;
    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ds_secure_lock_layout);
        initializeViews();
        mPasswordInputField.setFocusable(true);
        setTextWatchListeners();

    }

    private void initializeViews() {
        mStatusText = (TextView) findViewById(R.id.status_view);
        mPasswordInputField = (EditText) findViewById(R.id.pwd_input_field);
    }

    private void setTextWatchListeners() {
        mPasswordInputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String userTypedPassword = mPasswordInputField.getText().toString();
                if (userTypedPassword.equals(null) || userTypedPassword.isEmpty()) {
                    return;
                } else if (userTypedPassword.length() != mUserPassword.length()) {
                    return;
                }

                if (userTypedPassword.equals(mUserPassword)) {
                    mStatusText.setTextColor(Color.GREEN);
                    mStatusText.setText("Pin matched");
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    }, 3000);
                } else {
                    mStatusText.setTextColor(Color.RED);
                    mStatusText.setText("Wrong PIN. Keypad Locked");
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPasswordInputField.setText("");
                            mStatusText.setText("");
                            DSApplication.getInstance().setIsUnLocked(false);
                        }
                    }, 3000);
                }
            }
        });
    }
}
