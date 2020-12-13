package com.dw.capstonebnform.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dw.capstonebnform.R;
import com.dw.capstonebnform.utils.Constants;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

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
//    private FirebaseUser user;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize firebase authetication
        mFirebaseAuth = FirebaseAuth.getInstance();



        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser  user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is signed in
                    Toast.makeText(MainActivity.this, "You have successfully signed In", Toast.LENGTH_SHORT).show();
                    setupNavigation();
                } else {
                    //user is signed out, so start sign in flow

                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                            new AuthUI.IdpConfig.PhoneBuilder().build()

                    );

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .setLogo(R.drawable.bnform_widget_icon)
                                    .setIsSmartLockEnabled(false)
                                    .setTheme(R.style.AppTheme)
                                    .build(),
                            RC_SIGN_IN);

                }

            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);


            if (resultCode == RESULT_OK) {

             setupNavigation();
                // ...
            } else if (resultCode == RESULT_CANCELED){
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                Toast.makeText(this, getResources().getString(R.string.login_cancel), Toast.LENGTH_SHORT).show();
                finish();

            } else {
                Log.i(TAG, "onActivityResult: fail to sign in");
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this,getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        }
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
            case R.id.loginOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;

            case R.id.newsForum:
                openWebPage(Constants.RECALL_NEWS);
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
        super.onBackPressed();

        //Handle the back button pressed on device
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            if (mAuthStateListener != null)
                finish();

        }
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
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Attach the listener
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void signOut(){
        AuthUI.getInstance().signOut(this);
    }

    private void openWebPage(String url){

        //Close drawer
//        drawerLayout.closeDrawer(GravityCompat.START);

        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
