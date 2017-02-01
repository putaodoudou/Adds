package com.adds.userInterface.applicationUI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.adds.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class DSIntroPage extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(false);
        addSlide(AppIntroFragment.newInstance("SMART, SECURE & SUPERB", "The worlds first super smart secure data storing application", R.drawable.ic_smart,
                ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.black), ContextCompat.getColor(this, R.color.black)));
        addSlide(AppIntroFragment.newInstance("SECURE DATA STORING", "Stores data in your phone itself, no need to wory about unknown servers", R.drawable.ic_storage,
                ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.black), ContextCompat.getColor(this, R.color.black)));
        addSlide(AppIntroFragment.newInstance("COMPLETE DATA PROTECTION", "All your data is 256-bit AES encrypted and securely stored", R.drawable.ic_secure,
                ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.black), ContextCompat.getColor(this, R.color.black)));
        addSlide(AppIntroFragment.newInstance("YOU ARE ALL SET TO GO", "Press done and never look back, It's all good", R.drawable.ic_all_done,
                ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.black), ContextCompat.getColor(this, R.color.black)));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#ffffff"));
        setSeparatorColor(Color.parseColor("#000000"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setColorSkipButton(ContextCompat.getColor(this, R.color.black));
        setColorTransitionsEnabled(true);
        setProgressButtonEnabled(true);
        setProgressIndicator();
        setNextArrowColor(ContextCompat.getColor(this, R.color.black));
        setColorDoneText(ContextCompat.getColor(this, R.color.black));

    }

    @Override
    public void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        callDashBoardActivity();
    }

    @Override
    public void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        callDashBoardActivity();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }

    private void callDashBoardActivity() {
        Intent dashBoardIntent = new Intent(this, DSDashBoard.class);
        startActivity(dashBoardIntent);
    }
}

