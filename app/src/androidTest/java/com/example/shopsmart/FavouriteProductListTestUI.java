package com.example.shopsmart;

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
public class FavouriteProductListTestUI {

    @Rule
    public ActivityScenarioRule<ProductActivity1> activityRule = new ActivityScenarioRule<>(ProductActivity1.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testAddFavouriteProduct() {
        // Ensure RecyclerView is displayed
        Espresso.onView(withId(R.id.recyclerView))
                .perform(ViewActions.scrollTo())
                .check(matches(isDisplayed()));

        // Click on the favourite button of the first item in the recyclerView
        Espresso.onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, new ClickOnViewChild(R.id.favourite_btn)));

        // Open UserProfileActivity by clicking on the menu item
        Espresso.onView(withId(R.id.nav))
                .perform(ViewActions.click());

        Espresso.onView(withId(R.id.me))
                .perform(ViewActions.click());

        // Verify that UserProfileActivity is started
        Intents.intended(IntentMatchers.hasComponent(UserProfileActivity.class.getName()));

        // Click on the likedButton to show favourite products
        Espresso.onView(withId(R.id.likedButton))
                .perform(ViewActions.click());

        // Verify the product is added to recyclerView in MyInterestActivity
        Espresso.onView(withId(R.id.recycler))
                .check(matches(isDisplayed()))
                .check(matches(hasDescendant(withText("Apple iPhone 15 Plus 128GB (Green)")))); // Replace "Samsung Galaxy Buds FE (Graphite)" with the actual product title
    }
}
