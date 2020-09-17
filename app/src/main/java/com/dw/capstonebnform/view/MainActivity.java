package com.dw.capstonebnform.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private NavController navController;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolBar;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupNavigation();
    }

    private void setupNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);

        drawerLayout = findViewById(R.id.drawer_layout);

        //AppBarConfiguration use the top level destination as root
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setOpenableLayout(drawerLayout)
                .build();


        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        navigationView = findViewById(R.id.navigationView_id);

        //lets controll navigation with configuration
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected: " + item);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);

        //Show where item is checkmarked on drawer
        item.setChecked(true);

        //Close drawer after clicked
        drawerLayout.closeDrawers();

        Context context = MainActivity.this;

//        int itemThatWasClickedid = item.getItemId();
//
//        switch (itemThatWasClickedid){
//            case R.id.menu_alerts_id:
//
//                Toast.makeText(context, "Aierts Clicked", Toast.LENGTH_LONG).show();
//
//                return true;
//
//            case R.id.menu_history_id:
//
//                Toast.makeText(context, "History Clicked", Toast.LENGTH_LONG).show();
//                return true;
//
//            case R.id.scan_menu_id:
//
//                Toast.makeText(context, "Scan Clicked", Toast.LENGTH_LONG).show();
//                NavigationUI.onNavDestinationSelected(item , navController);
//                return true;
//
//        }


        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }



    @Override
    public void onBackPressed() {
        //Handle the back button pressed on device
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //Handle the UP Arrow
        return NavigationUI.navigateUp(navController, appBarConfiguration);
    }
}