package com.example.shopsmart;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



@RunWith(AndroidJUnit4.class)
public class LogInActivityInstrumentedTest extends TestCase {
    @Rule
    public ActivityScenarioRule<LogInActivity> loginActivityRule = new ActivityScenarioRule<>(LogInActivity.class);
    @Test
    public void testHandleGoogleLogin() {

        loginActivityRule.getScenario().onActivity(activity -> {

            activity.handleGoogleLogin();
            assertNotNull(activity.gso);
            assertNotNull(activity.client);
        });
    }

    @Test
    public void testHandleFacebookLogin() {

        loginActivityRule.getScenario().onActivity(activity -> {
            activity.handleFacebookLogin();
            assertNotNull(activity.callBackManager);
        });
    }
}

