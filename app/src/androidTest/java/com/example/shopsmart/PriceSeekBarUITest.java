package com.example.shopsmart;

import android.view.View;
import android.widget.SeekBar;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.shopsmart.Adapter.ProductAdapter;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PriceSeekBarUITest {

    @Rule
    public ActivityScenarioRule<ProductActivity1> activityRule =
            new ActivityScenarioRule<>(ProductActivity1.class);

    @Test
    public void testFilterByPriceRange() {
        // Ensure RecyclerView is displayed
        Espresso.onView(withId(R.id.recyclerView))
                .check(ViewAssertions.matches(isDisplayed()));

        // Click on the filter button
        Espresso.onView(withId(R.id.filter_button))
                .perform(click());

        // Adjust the SeekBar to a specific range
        Espresso.onView(withId(R.id.price_filter_seekbar))
                .perform(setProgress(1000));

        // Click on the filter search button
        Espresso.onView(withId(R.id.search_filter_btn))
                .perform(click());

        // Wait for the filtering process
        try {
            Thread.sleep(2000);  // Wait for 2 seconds to allow the filtering process to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check that all products in the recyclerView are filtered by price range
        Espresso.onView(withId(R.id.recyclerView))
                .check(ViewAssertions.matches(isDisplayed()));

        // Verify that the filtered products match the price range
        activityRule.getScenario().onActivity(activity -> {
            RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
            ProductAdapter adapter = (ProductAdapter) recyclerView.getAdapter();
            if (adapter != null) {
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    double price = adapter.getProduct(i).getMinFee();
                    // Replace minPrice and maxPrice with the expected values based on SeekBar's progress
                    double minPrice = 0; // Adjust as per your SeekBar minimum value
                    double maxPrice = 1000; // Adjust as per your SeekBar maximum value
                    assertTrue("Product price is within range", price >= minPrice && price <= maxPrice);
                }
            }
        });
    }

    private static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }

            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }

            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar) view;
                seekBar.setProgress(progress);
            }
        };
    }
}
