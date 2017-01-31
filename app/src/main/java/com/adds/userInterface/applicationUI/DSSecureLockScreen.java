package com.adds.userInterface.applicationUI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adds.R;
import com.adds.application.DSApplication;

/**
 * Created by 10745 on 1/30/2017.
 */

public class DSSecureLockScreen extends Activity implements OnClickListener {

    private static final int PIN_LENGTH = 4;
    private String mUserTypedPassword;
    private String mUserPassword = "android";

    private TextView mStatusText;

    private Button mKeypad0;
    private Button mKeypad1;
    private Button mKeypad2;
    private Button mKeypad3;
    private Button mKeypad4;
    private Button mKeypad5;
    private Button mKeypad6;
    private Button mKeypad7;
    private Button mKeypad8;
    private Button mKeypad9;
    private Button mKeypadExitBtn;
    private Button mKeypadDelBtn;
    private EditText mPasswordInputField;
    private ImageView mBackspace;

    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ds_secure_lock_layout);
        initializeViews();
        setListeners();
    }

    private void initializeViews() {
        mStatusText = (TextView) findViewById(R.id.statusview);
        mPasswordInputField = (EditText) findViewById(R.id.editText);

        //Keypad buttons
        mKeypad0 = (Button) findViewById(R.id.button0);
        mKeypad1 = (Button) findViewById(R.id.button1);
        mKeypad2 = (Button) findViewById(R.id.button2);
        mKeypad3 = (Button) findViewById(R.id.button3);
        mKeypad4 = (Button) findViewById(R.id.button4);
        mKeypad5 = (Button) findViewById(R.id.button5);
        mKeypad6 = (Button) findViewById(R.id.button6);
        mKeypad7 = (Button) findViewById(R.id.button7);
        mKeypad8 = (Button) findViewById(R.id.button8);
        mKeypad9 = (Button) findViewById(R.id.button9);
        mKeypadExitBtn = (Button) findViewById(R.id.buttonExit);
        mKeypadDelBtn = (Button) findViewById(R.id.buttonDeleteBack);
        mBackspace = (ImageView) findViewById(R.id.backSpace);
    }

    private void setListeners() {
        mKeypad0.setOnClickListener(this);
        mKeypad1.setOnClickListener(this);
        mKeypad2.setOnClickListener(this);
        mKeypad3.setOnClickListener(this);
        mKeypad4.setOnClickListener(this);
        mKeypad5.setOnClickListener(this);
        mKeypad6.setOnClickListener(this);
        mKeypad7.setOnClickListener(this);
        mKeypad8.setOnClickListener(this);
        mKeypad9.setOnClickListener(this);
        mKeypadExitBtn.setOnClickListener(this);
        mKeypadDelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonExit) {
            //Exit app
            Intent exitIntent = new Intent();
            exitIntent.setAction(Intent.ACTION_MAIN);
            exitIntent.addCategory(Intent.CATEGORY_HOME);
            startActivity(exitIntent);
            finish();
        } else if (view.getId() == R.id.buttonDeleteBack) {
            if (mUserTypedPassword.equals(mUserPassword)) {
                mStatusText.setTextColor(Color.GREEN);
                mStatusText.setText("Correct");
                DSApplication.getInstance().isLocked = false;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 3000);
            }
        } else {
            keyPadNumberPressed(view);
        }
    }

    private void keyPadNumberPressed(View view) {
        Button pressedButton = (Button) view;
        if (mPasswordInputField.getText().toString().length() > PIN_LENGTH) {
            return;
        }
        mUserTypedPassword = mUserTypedPassword + pressedButton.getText();
        //Update pin boxes
        mPasswordInputField.setText(mPasswordInputField.getText().toString());
//                mPasswordInputField.setSelection(mPasswordInputField.getText().toString().length());

        if (mUserTypedPassword.length() != PIN_LENGTH) {
            return;
        }
        //Check if entered PIN is correct
        if (mUserTypedPassword.equals(mUserPassword)) {
            mStatusText.setTextColor(Color.GREEN);
            mStatusText.setText("Correct");
            DSApplication.getInstance().isLocked = false;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
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
                    DSApplication.getInstance().isLocked = true;
                }
            }, 3000);
        }

    }
}
