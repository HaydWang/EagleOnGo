package com.cnh.android.eagleongo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.*;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.cnh.android.eagleongo.fragment.HomeFragment;
import com.cnh.android.eagleongo.fragment.SettingsFragment;
import com.cnh.android.eagleongo.fragment.UDWFragment;
import com.cnh.android.eagleongo.fragment.UserFragment;
import com.cnh.android.eagleongo.model.SingleUdwRecyclerViewAdapter;
import com.cnh.android.eagleongo.view.RecyclerItemTouchHelperCallback;
import com.cnh.android.eagleongo.view.SingleUdwViewHolder;
import com.cnh.pf.signal.Consumer;
import com.cnh.pf.signal.OnConnectionChangeListener;
import com.cnh.pf.signal.Producer;
import com.cnh.pf.signal.Signal;
import com.cnh.pf.signal.SignalUri;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.bottom_navigation_menu)
    BottomNavigationView mBottomNavigationView;

    private Fragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /* Looks FAB does not fit UI style here. Disable it for now.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int grid = 1;
                if (grid >= 3 )
                    grid = 1;
                else
                    grid++;
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(grid, OrientationHelper.VERTICAL));
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();
            }
        }); */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Init tabs and fragments
        mFragments = new Fragment[4];
        mFragments[0] = new HomeFragment();
        mFragments[1] = new UDWFragment();
        mFragments[2] = new SettingsFragment();
        mFragments[3] = new UserFragment();
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                return onTabItemSelected(item.getItemId());
            }
        });
        mBottomNavigationView.findViewById(R.id.action_udw).performClick();
    }

    private boolean onTabItemSelected(int id){
        Fragment fragment = null;
        switch (id){
            case R.id.action_home:
                fragment = mFragments[0];
                break;
            case R.id.action_udw:
                fragment = mFragments[1];
                break;
            case R.id.action_settings:
                //fragment = mFragments[2];
                // Just launch Vehicle app instaed of change fragment, until merge settings UI
                // into fragment
                try {
                    Intent i = new Intent();
                    i.setComponent(new ComponentName("com.cnh.pf.android.vehicle",
                            "com.cnh.pf.android.vehicle.VehicleActivity"));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this,
                            "Vehicle Setup App not found. ", Toast.LENGTH_LONG).show();
                }
                return false;
                //break;
        }
        if(fragment!=null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragments_container, fragment);
            transaction.commit();
        }
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
        getMenuInflater().inflate(R.menu.main, menu);
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
}
