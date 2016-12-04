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
        showStatusBar(false);
        addSlide(AppIntroFragment.newInstance("Super Smart", "The worlds first super smart data securing application", R.drawable.ic_skip_white, getResources().getColor(R.color.material_black)));
        addSlide(AppIntroFragment.newInstance("Secure Storing", "Stores data in your own phone itself", R.drawable.secure, getResources().getColor(R.color.material_blue_green)));
        addSlide(AppIntroFragment.newInstance("Complete Protection", "blalll blaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", R.drawable.ic_skip_white, getResources().getColor(R.color.material_violet_light)));
        addSlide(AppIntroFragment.newInstance("All Done", "Let :)", R.drawable.ic_skip_white, getResources().getColor(R.color.material_red_light)));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#ffffff"));

        // Hide Skip/Done button.
        showSkipButton(true);

        setProgressButtonEnabled(true);

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
        // Do something when the slide changes.
        setSlideOverAnimation();
        setBarColor(Color.parseColor("#212121"));
    }

    private void callDashBoardActivity() {
        Intent dashBoardIntent = new Intent(this, DSDashBoard.class);
        startActivity(dashBoardIntent);
    }
}

