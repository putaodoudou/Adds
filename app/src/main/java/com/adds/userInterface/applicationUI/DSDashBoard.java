package com.adds.userInterface.applicationUI;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.adds.Listeners.DetailAddListener;
import com.adds.Listeners.ExpandableListChildClickListener;
import com.adds.R;
import com.adds.application.DSApplication;
import com.adds.authentication.DSPermissionsHelper;
import com.adds.authentication.DSPermissionsHelper.PermissionsCallback;
import com.adds.helpers.DSModalSelectionHelper;
import com.adds.modalClasses.DSBankAccModal;
import com.adds.modalClasses.DSCardModal;
import com.adds.modalClasses.DSLoginPasswordModal;
import com.adds.modalClasses.DSOthersModal;
import com.adds.userInterface.customViews.DSCustomInputDialog;
import com.adds.userInterface.customViews.DSFab.FloatingActionButton;
import com.adds.userInterface.customViews.DSFab.FloatingActionMenu;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class DSDashBoard extends AppCompatActivity implements OnNavigationItemSelectedListener, PermissionsCallback, ExpandableListChildClickListener,
        DetailAddListener {

    private DSPermissionsHelper mPermissionsHelper;

    private FloatingActionMenu mFabMenus;
    private FloatingActionButton mAddBankData;
    private FloatingActionButton mAddCardData;
    private FloatingActionButton mAddLoginData;
    private FloatingActionButton mAddOtherData;

    private FrameLayout mFrameLayout;

    private Handler mUiHandler = new Handler();

    private int mSelectedMenu;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab1:
                    mSelectedMenu = 1;
                    break;
                case R.id.fab2:
                    mSelectedMenu = 2;
                    break;
                case R.id.fab3:
                    mSelectedMenu = 3;
                    break;
                case R.id.fab4:
                    mSelectedMenu = 4;
                    break;
            }
            callPopupMethod();
        }
    };

    private void callPopupMethod() {
        if (checkRuntimePermission()) {
            showInputDataPopup(mSelectedMenu);
        } else {
            mPermissionsHelper.requestPermissionActivity(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!DSApplication.getInstance().isUnLocked()) {
            Intent lockScreenIntent = new Intent(this, DSSecureLockScreen.class);
            startActivityForResult(lockScreenIntent, 102);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                DSApplication.getInstance().setIsUnLocked(true);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        DSApplication.getInstance().setIsUnLocked(false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPermissionsHelper = new DSPermissionsHelper(this, this);

        mFabMenus = (FloatingActionMenu) findViewById(R.id.fab_menu);
        mAddBankData = (FloatingActionButton) findViewById(R.id.fab1);
        mAddCardData = (FloatingActionButton) findViewById(R.id.fab2);
        mAddLoginData = (FloatingActionButton) findViewById(R.id.fab3);
        mAddOtherData = (FloatingActionButton) findViewById(R.id.fab4);

        mFrameLayout = (FrameLayout) findViewById(R.id.content_frame);


        initDrawerAndNavigation(toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new DSExpandableListFragment(), null).commit();
    }

    private void initDrawerAndNavigation(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        initFab();

    }

    private void initFab() {
        mAddBankData.setOnClickListener(clickListener);
        mAddCardData.setOnClickListener(clickListener);
        mAddLoginData.setOnClickListener(clickListener);
        mAddOtherData.setOnClickListener(clickListener);

        int delay = 400;
        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFabMenus.showMenuButton(true);
            }
        }, delay);
    }

    private boolean checkRuntimePermission() {
        if (mPermissionsHelper.hasPermisson(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return true;
        }
        return false;
    }

    private void showInputDataPopup(int modal) {
        DSCustomInputDialog inputDialog = new DSCustomInputDialog();
        Bundle bundle = new Bundle();
        ArrayList<String> list = DSModalSelectionHelper.getInputModalData(modal);
        bundle.putStringArrayList("data", list);
        bundle.putInt("type", modal);
        inputDialog.setArguments(bundle);
        inputDialog.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getFragments().size() == 1) {

            } else {
                //show exit
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dsdash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, DSSecureLockScreen.class);
            startActivity(intent);
//            DSSecureLockScreen lockScreenFragment = new DSSecureLockScreen();
//            getSupportFragmentManager().beginTransaction().add(R.id.content_frame, lockScreenFragment, null).commit();
//            FrameLayout mFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
//            mExpListView.setVisibility(View.GONE);
//            mFrameLayout.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionsHelper.handleRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void permissionGranted(String[] permissions) {
        showInputDataPopup(mSelectedMenu);
    }

    @Override
    public void permissionDenied(String[] permissions) {

    }

    @Override
    public void onChildClick(String uniqueName, int groupPos) {
        ArrayList<String> labels = DSModalSelectionHelper.getInputModalData(groupPos);
        Bundle bundle = new Bundle();
        if (groupPos == 1) {
            try {
                DSBankAccModal modal = DSModalSelectionHelper.getDecryptedBankDetails(uniqueName, DSDashBoard.this);
                bundle.putSerializable("modal", modal);
            } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | BadPaddingException
                    | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException | IOException | UnrecoverableKeyException e) {
                e.printStackTrace();
            }
        } else if (groupPos == 2) {
            try {
                DSCardModal modal = DSModalSelectionHelper.getDecryptedCardDetails(uniqueName, DSDashBoard.this);
                bundle.putSerializable("modal", modal);
            } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | BadPaddingException
                    | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException | IOException | UnrecoverableKeyException e) {
                e.printStackTrace();
            }

        } else if (groupPos == 3) {
            try {
                DSLoginPasswordModal modal = DSModalSelectionHelper.getDecryptedLoginDetails(uniqueName, DSDashBoard.this);
                bundle.putSerializable("modal", modal);
            } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | BadPaddingException
                    | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException | IOException | UnrecoverableKeyException e) {
                e.printStackTrace();
            }
        } else if (groupPos == 4) {
            try {
                DSOthersModal modal = DSModalSelectionHelper.getDecryptedOtherDetails(uniqueName, DSDashBoard.this);
                bundle.putSerializable("modal", modal);
            } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | BadPaddingException
                    | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException | IOException | UnrecoverableKeyException e) {
                e.printStackTrace();
            }
        }

        bundle.putSerializable("labels", labels);
        bundle.putInt("type", groupPos);
        DSDecryptedDataViewerFragment fragment = new DSDecryptedDataViewerFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment, null).commit();
    }

    @Override
    public void onAddDetailSucces() {

    }

    @Override
    public void onAddDetailFailed() {

    }
}
