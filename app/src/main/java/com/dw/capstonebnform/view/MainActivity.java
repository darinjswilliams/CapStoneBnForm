package com.dw.capstonebnform.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dw.capstonebnform.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private FirebaseUser user;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mFirebaseAuth = FirebaseAuth.getInstance();


        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        setupNavigation();

//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    setupNavigation();
//                } else {
//
//                    List<AuthUI.IdpConfig> providers = Arrays.asList(
//                            new AuthUI.IdpConfig.EmailBuilder().build(),
//                            new AuthUI.IdpConfig.FacebookBuilder().build(),
//                            new AuthUI.IdpConfig.GoogleBuilder().build(),
//                            new AuthUI.IdpConfig.PhoneBuilder().build()
//
//                    );
//
//                    startActivityForResult(
//                            AuthUI.getInstance()
//                                    .createSignInIntentBuilder()
//                                    .setAvailableProviders(providers)
//                                    .setLogo(R.drawable.bnform_widget_icon)
//                                    .setIsSmartLockEnabled(false)
//                                    .setTheme(R.style.AppTheme)
//                                    .build(),
//                            RC_SIGN_IN);
//
//                }
//
//            }
//        };
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    private void setupNavigation() {


        drawerLayout = findViewById(R.id.drawer_layout);

        //AppBarConfiguration use the top level destination as root
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.lowAlertFragment)
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

        switch (item.getItemId()) {
            case R.id.loginFragment:
//                FirebaseAuth.getInstance().signOut();
//                finish();
                break;
        }


        //Close drawer after clicked
        drawerLayout.closeDrawers();

        Context context = MainActivity.this;

        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        //Handle the back button pressed on device
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
//        else {
//            if(user ==  null) finish();
//            super.onBackPressed();
//
//        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //Handle the UP Arrow
        return NavigationUI.navigateUp(navController, appBarConfiguration);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //remove listener
//        if(mAuthStateListener != null) {
//            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Attach the listener
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

}
