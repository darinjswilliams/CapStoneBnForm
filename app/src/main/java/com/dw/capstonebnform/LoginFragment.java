package com.dw.capstonebnform;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dw.capstonebnform.databinding.FragmentLoginBinding;
import com.dw.capstonebnform.dto.User;
import com.dw.capstonebnform.viewModel.AuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class LoginFragment extends Fragment implements View.OnClickListener {


    private GoogleSignInClient mGogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private FragmentLoginBinding mfragmentLoginBinding;
    private static final String TAG = LoginFragment.class.getSimpleName();
    private AuthViewModel authViewModel;
    private LoginFragmentDirections.ActionLoginFragmentToLowAlertFragment mActionloginFragmentDirections;
    private NavController navController;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGogleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_container);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mfragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        // Inflate the layout for this fragment
        mfragmentLoginBinding.signInButton.setOnClickListener(this::onClick);

        initAuthViewModel();

        return mfragmentLoginBinding.getRoot();
    }

    private void initAuthViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    private void googleSignIn() {
        // Launches the sign in flow, the result is returned in onActivityResult
        Intent intent = mGogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(data);
            if (task.isSuccessful()) {
                // Sign in succeeded, proceed with account
                GoogleSignInAccount acct = null;
                try {
                    acct = task.getResult(ApiException.class);
                    handleGoogleSignInResults(acct);
                } catch (ApiException e) {
                    Log.d(TAG, "onActivityResult: " + e.getLocalizedMessage());
                }

            } else {
                // Sign in failed, handle failure and update UI
                // ...
                Toast.makeText(getActivity(), getResources().getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                googleSignIn();
                break;
        }

    }

    private void handleGoogleSignInResults(GoogleSignInAccount googleSignInAccount) {
        Log.d(TAG, "handleGoogleSignInResults: " + googleSignInAccount.getEmail());
        Log.d(TAG, "handleGoogleSignInResults: " + googleSignInAccount.getDisplayName());
        String googleTokenId = googleSignInAccount.getIdToken();
        AuthCredential googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null);
        signInWithGoogleAuthCredential(googleAuthCredential);

    }

    private void signInWithGoogleAuthCredential(AuthCredential googleAuthCredential) {
        authViewModel.signInWithGoogle(googleAuthCredential);
        authViewModel.authenticatedUserLiveData.observe(this, authenticatedUser -> {
            if (authenticatedUser.isNew) {
                createNewUser(authenticatedUser);
            } else {
                sendUserToLowLevelAlerts(authenticatedUser);
            }
        });
    }

    private void createNewUser(User authenticatedUser) {
        authViewModel.createUser(authenticatedUser);
        authViewModel.createdUserLiveData.observe(this, user -> {
            if (user.isCreated) {
                Toast.makeText(getActivity(), getResources().getString(R.string.new_user), Toast.LENGTH_SHORT).show();
            }
            sendUserToLowLevelAlerts(authenticatedUser);
//            NavHostFragment.findNavController(this).navigate(R.id.lowAlertFragment);
        });
    }

    private void sendUserToLowLevelAlerts(User authenticatedUser){
        mActionloginFragmentDirections = LoginFragmentDirections.actionLoginFragmentToLowAlertFragment(authenticatedUser);
        mActionloginFragmentDirections.setUser(authenticatedUser);
        navController.navigate(mActionloginFragmentDirections);
    }
}