package com.example.shopsmart;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class LogInActivityTest {
    @Rule
    public ActivityScenarioRule<LogInActivity> loginActivityRule = new ActivityScenarioRule<>(LogInActivity.class);
    @Test
    public void testHandleGoogleLogin() {
        loginActivityRule.getScenario().onActivity(activity -> {
            // Call the handleGoogleLogin method
            activity.handleGoogleLogin();

            // Check if GoogleSignInOptions and GoogleSignInClient are initialized
            assertNotNull(activity.gso);
            assertNotNull(activity.client);
        });
    }
}
