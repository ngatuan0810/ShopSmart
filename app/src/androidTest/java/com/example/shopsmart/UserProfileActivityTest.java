package com.example.shopsmart;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.intent.Intents.intended;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.assertNull;

import android.content.Intent;
import android.net.Uri;

@RunWith(AndroidJUnit4.class)
public class UserProfileActivityTest {

    @Rule
    public ActivityScenarioRule<UserProfileActivity> userProfileActivityRule =
            new ActivityScenarioRule<>(UserProfileActivity.class);
    @Before
    public void intentsInit() {
        // initialize Espresso Intents capturing
        Intents.init();
    }

    @After
    public void intentsTeardown() {
        // release Espresso Intents capturing
        Intents.release();
    }

    @Test
    public void testItemNavigation() {
        int[] buttons = new int[] {R.id.watchedButton, R.id.reviewedButton, R.id.couponButton};
        int[] imageViews = new int[] {R.id.imageView2, R.id.imageView3, R.id.imageView4};
        for (int i = 0; i < buttons.length; i++) {
            onView(withId(buttons[i])).perform(click());
            onView(withId(imageViews[i])).check(matches(isDisplayed()));
            onView(withId(imageViews[i])).perform(click());
        }
    }
    @Test
    public void testSignOut() {
        onView(withId(R.id.imageView36)).perform(click());
        userProfileActivityRule.getScenario().onActivity(activity -> {
            assertNull(activity.firebaseAuth.getCurrentUser());
        });
        intended(hasComponent(LogInActivity.class.getName()));
    }
    @Test
    public void testHomeNavigationBar() {
        onView(withId(R.id.home)).perform(click());
        intended(hasComponent(ScreenActivity2.class.getName()));
    }
    @Test
    public void testProductNavigationBar() {
        onView(withId(R.id.product)).perform(click());
        intended(hasComponent(ProductActivity1.class.getName()));
    }
    @Test
    public void testSeller1ButtonClick_shouldStartBrowserIntent() {
        // Perform click on the seller1 button
        onView(withId(R.id.JBHifi)).perform(click());

        // Verify the intent is sent with the correct action and data
        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(Uri.parse("https://www.jbhifi.com.au"))
        ));

    }

    @Test
    public void testSeller2ButtonClick_shouldStartBrowserIntent() {
        // Perform click on the seller2 button
        onView(withId(R.id.GoodGuys)).perform(click());

        // Verify the intent is sent with the correct action and data
        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(Uri.parse("https://www.thegoodguys.com.au"))
        ));
    }
}

