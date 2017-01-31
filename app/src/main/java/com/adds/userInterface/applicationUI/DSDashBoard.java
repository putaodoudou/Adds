package com.adds.userInterface.applicationUI;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;

import com.adds.Listeners.ExpandableListChildClickListener;
import com.adds.R;
import com.adds.adapters.DSExpandableListviewAdapter;
import com.adds.application.DSApplication;
import com.adds.authentication.DSPermissionsHelper;
import com.adds.authentication.DSPermissionsHelper.PermissionsCallback;
import com.adds.database.DSDataBaseHelper;
import com.adds.database.DSDatabaseFieldNames;
import com.adds.helpers.DSModalSelectionHelper;
import com.adds.modalClasses.DSBankAccModal;
import com.adds.modalClasses.DSCardModal;
import com.adds.modalClasses.DSDisplayDataModal;
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

public class DSDashBoard extends AppCompatActivity implements OnNavigationItemSelectedListener, PermissionsCallback, ExpandableListChildClickListener {

    private ExpandableListView mExpListView;
    private DSExpandableListviewAdapter mExpandableListAdapter;
    private DSPermissionsHelper mPermissionsHelper;

    private FloatingActionMenu mFabMenus;
    private FloatingActionButton mAddBankData;
    private FloatingActionButton mAddCardData;
    private FloatingActionButton mAddLoginData;
    private FloatingActionButton mAddOtherData;

    private Handler mUiHandler = new Handler();

    private int mSelectedMenu;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab1:
                    mSelectedMenu = 1;
                    callPopupMethod();
                    break;
                case R.id.fab2:
                    mSelectedMenu = 2;
                    callPopupMethod();
                    break;
                case R.id.fab3:
                    mSelectedMenu = 3;
                    callPopupMethod();
                    break;
                case R.id.fab4:
                    mSelectedMenu = 4;
                    callPopupMethod();
                    break;
            }
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
        if (DSApplication.getInstance().isLocked) {
            Intent lockScreenIntent = new Intent(this, DSSecureLockScreen.class);
            startActivity(lockScreenIntent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        DSApplication.getInstance().isLocked = true;
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

        initDrawerAndNavigation(toolbar);
        setDashBoardListData();
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

    private void setDashBoardListData() {
//        if (DSSharedPreferencUtils.getBooleanPref("SAVED_DATA", this)) {
        //UI elements
        mExpListView = (ExpandableListView) findViewById(R.id.expandable_lv);

        ArrayList<DSDisplayDataModal> headerModal = new ArrayList<>();
        DSDisplayDataModal modal1 = new DSDisplayDataModal();
        DSDisplayDataModal modal2 = new DSDisplayDataModal();
        DSDisplayDataModal modal3 = new DSDisplayDataModal();
        DSDisplayDataModal modal4 = new DSDisplayDataModal();
        headerModal.add(modal1);
        headerModal.add(modal2);
        headerModal.add(modal3);
        headerModal.add(modal4);
        modal1.setDisplayData("Bank account details");
        modal1.setDrawable(ContextCompat.getDrawable(this, R.drawable.ic_account_balance_black_48dp));
        modal2.setDisplayData("Credit and Debit card details ");
        modal2.setDrawable(ContextCompat.getDrawable(this, R.drawable.ic_credit_card_black_48dp));
        modal3.setDisplayData("Login credential details");
        modal3.setDrawable(ContextCompat.getDrawable(this, R.drawable.ic_account_circle_black_48dp));
        modal4.setDisplayData("Other secure datas");
        modal4.setDrawable(ContextCompat.getDrawable(this, R.drawable.ic_event_note_black_48dp));

        ArrayList<ArrayList<String>> childModal = new ArrayList<>();
        DSDataBaseHelper dataBaseHelper = new DSDataBaseHelper(this);
        Cursor cursor = dataBaseHelper.selectAllBankName();
        ArrayList<String> dataModals1 = new ArrayList<>();
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    dataModals1.add(cursor.getString(cursor.getColumnIndex(DSDatabaseFieldNames.ACC_NAME)));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        childModal.add(dataModals1);

        Cursor cursor2 = dataBaseHelper.selectAllCardName();
        ArrayList<String> dataModals2 = new ArrayList<>();
        if (cursor2 != null && cursor2.getCount() != 0) {
            if (cursor2.moveToFirst()) {
                do {
                    dataModals2.add(cursor2.getString(cursor2.getColumnIndex(DSDatabaseFieldNames.CARD_NAME)));
                } while (cursor2.moveToNext());
            }
        }
        cursor2.close();
        childModal.add(dataModals2);

        Cursor cursor3 = dataBaseHelper.selectAllLoginName();
        ArrayList<String> dataModals3 = new ArrayList<>();
        if (cursor3 != null && cursor3.getCount() != 0) {
            if (cursor3.moveToFirst()) {
                do {
                    dataModals3.add(cursor3.getString(cursor3.getColumnIndex(DSDatabaseFieldNames.LOGIN_NAME)));
                } while (cursor3.moveToNext());
            }
        }
        cursor3.close();
        childModal.add(dataModals3);

        Cursor cursor4 = dataBaseHelper.selectAllOtherName();
        ArrayList<String> dataModals4 = new ArrayList<>();
        if (cursor4 != null && cursor4.getCount() != 0) {
            if (cursor4.moveToFirst()) {
                do {
                    dataModals4.add(cursor4.getString(cursor4.getColumnIndex(DSDatabaseFieldNames.OTHER_DATA_NAME)));
                } while (cursor4.moveToNext());
            }
        }
        cursor4.close();
        childModal.add(dataModals4);

        mExpandableListAdapter = new DSExpandableListviewAdapter(this, headerModal, childModal);
        mExpListView.setAdapter(mExpandableListAdapter);

        mExpListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (!parent.isGroupExpanded(groupPosition)) {
                    parent.expandGroup(groupPosition);
                } else {
                    parent.collapseGroup(groupPosition);
                }
                return true;
            }
        });

//        } else {
//            todo show fallback message
//        }
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
            super.onBackPressed();
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
//            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
//            mExpListView.setVisibility(View.GONE);
//            frameLayout.setVisibility(View.VISIBLE);

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
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mExpListView.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
    }
}
