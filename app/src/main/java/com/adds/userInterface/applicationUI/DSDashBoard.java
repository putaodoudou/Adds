package com.adds.userInterface.applicationUI;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ExpandableListView;

import com.adds.R;
import com.adds.adapters.DSExpandableListviewAdapter;
import com.adds.authentication.DSPermissionsHelper;
import com.adds.authentication.DSPermissionsHelper.PermissionsCallback;
import com.adds.modalClasses.DSBankAccModal;
import com.adds.modalClasses.DSCardModal;
import com.adds.modalClasses.DSChildModal;
import com.adds.modalClasses.DSHeaderModal;
import com.adds.modalClasses.DSLoginPasswordModal;
import com.adds.modalClasses.DSOthersModal;
import com.adds.userInterface.customViews.DSCustomInputDialog;

import java.util.ArrayList;

public class DSDashBoard extends AppCompatActivity implements OnNavigationItemSelectedListener, PermissionsCallback {

    private ExpandableListView mExpListView;
    private DSExpandableListviewAdapter mExpandableListAdapter;
    private DSPermissionsHelper mPermissionsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPermissionsHelper = new DSPermissionsHelper(this, this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mPermissionsHelper.hasPermisson(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showInputPopup();
                } else {
                    mPermissionsHelper.requestPermissionActivity(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //UI elements
        mExpListView = (ExpandableListView) findViewById(R.id.expandable_lv);

        ArrayList<DSHeaderModal> headerModal = new ArrayList<>();
        DSHeaderModal modal1 = new DSHeaderModal();
        DSHeaderModal modal2 = new DSHeaderModal();
        DSHeaderModal modal3 = new DSHeaderModal();
        DSHeaderModal modal4 = new DSHeaderModal();
        headerModal.add(modal1);
        headerModal.add(modal2);
        headerModal.add(modal3);
        headerModal.add(modal4);
        modal1.setmHeader("one");
        modal2.setmHeader("two");
        modal3.setmHeader("three");
        modal4.setmHeader("four");

        DSBankAccModal bankAccModal = new DSBankAccModal("dasd", "Asdasd", "asddas", "asdsad", "asdasd");
        ArrayList<DSBankAccModal> list1 = new ArrayList<>();
        list1.add(bankAccModal);

        DSCardModal cardModal = new DSCardModal("dqwwwwasd", 12, 11, 121);
        ArrayList<DSCardModal> list2 = new ArrayList<>();
        list2.add(cardModal);

        DSLoginPasswordModal loginModal = new DSLoginPasswordModal("asdqwerr", "bnmk", "lol");
        ArrayList<DSLoginPasswordModal> list3 = new ArrayList<>();
        list3.add(loginModal);

        DSOthersModal othersModal = new DSOthersModal("wowww", "qwwww");
        ArrayList<DSOthersModal> list4 = new ArrayList<>();
        list4.add(othersModal);

        DSChildModal childModal = new DSChildModal(list1, list2, list3, list4);

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
    }

    private void showInputPopup() {
        DSCustomInputDialog inputDialog = new DSCustomInputDialog();
        Bundle bundle = new Bundle();
        ArrayList<String> list = new ArrayList<>();
        list.add("sadsd");
        list.add("sadsd");
        list.add("sadsd");
        list.add("sadsd");
        list.add("sadsd");
        bundle.putStringArrayList("data", list);
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
        showInputPopup();
    }

    @Override
    public void permissionDenied(String[] permissions) {

    }
}
