package com.adds.userInterface.applicationUI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.adds.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class DSIntroPage extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Super Smart", "The worlds first super smart data securing application", R.drawable.common_full_open_on_phone, getResources().getColor(R.color.material_black)));
        addSlide(AppIntroFragment.newInstance("Secure Storing", "Stores data in your own phone itself", R.drawable.common_full_open_on_phone, getResources().getColor(R.color.material_blue_green)));
        addSlide(AppIntroFragment.newInstance("Complete Protection", "blalll blaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", R.drawable.common_full_open_on_phone, getResources().getColor(R.color.material_violet_light)));
        addSlide(AppIntroFragment.newInstance("All Done", "Let :)", R.drawable.common_full_open_on_phone, getResources().getColor(R.color.material_red_light)));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
//        setVibrate(true);
//        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent dashBoardIntent = new Intent(this, DSDashBoard.class);
        startActivity(dashBoardIntent);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
        setSlideOverAnimation();
    }
}

