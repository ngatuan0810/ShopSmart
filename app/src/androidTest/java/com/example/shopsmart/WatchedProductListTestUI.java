package com.example.shopsmart;

import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class WatchedProductListTestUI {

    @Rule
    public ActivityScenarioRule<ScreenActivity2> activityRule = new ActivityScenarioRule<>(ScreenActivity2.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testAddWatchedProduct() {
        // Ensure RecyclerView is displayed
        Espresso.onView(withId(R.id.recommend_products))
                .perform(ViewActions.scrollTo())
                .check(matches(isDisplayed()));

        // Click on the first item in the recommend_products RecyclerView
        Espresso.onView(withId(R.id.recommend_products))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        // Open UserProfileActivity by clicking on the menu item
        Espresso.onView(withId(R.id.nav))
                .perform(ViewActions.click());

        Espresso.onView(withId(R.id.me))
                .perform(ViewActions.click());

        // Verify that UserProfileActivity is started
        Intents.intended(IntentMatchers.hasComponent(UserProfileActivity.class.getName()));

        // Click on the watchedButton to show watched products
        Espresso.onView(withId(R.id.watchedButton))
                .perform(ViewActions.click());

        // Verify the product is added to watched_products_recycler in UserProfileActivity
        Espresso.onView(withId(R.id.watched_products_recycler))
                .check(matches(isDisplayed()))
                .check(matches(hasDescendant(withText("Samsung Galaxy Buds FE (Graphite)")))); // Replace "Samsung Galaxy Buds FE (Graphite)" with the actual product title
    }
}
