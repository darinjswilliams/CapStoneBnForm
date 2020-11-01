package com.dw.capstonebnform.view.fragments;

import com.dw.capstonebnform.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class LoginFragmentTest {

    @Test
    public void testNavigation_fromLoginToAlerts_WhenSuccessful() {
        TestNavHostController navHostController = new TestNavHostController(
                ApplicationProvider.getApplicationContext());

        navHostController.setGraph(R.id.loginFragment);

        FragmentScenario<LoginFragment> loginFragmentFragmentScenario
                = FragmentScenario.launchInContainer(LoginFragment.class);

        // Set the NavController property on the fragment
        loginFragmentFragmentScenario.onFragment(fragment ->
                Navigation.setViewNavController(fragment.requireView(), navHostController)
        );

        // Verify that performing a click changes the NavController’s state
//        onView(ViewMatchers.withId(R.id.play_btn)).perform(ViewActions.click());
//        assertThat(navHostController.getCurrentDestination().getId()).isEqualTo(R.id.lowAlertFragment);
    }

    @Test
    public void onActivityResult() {
    }
}