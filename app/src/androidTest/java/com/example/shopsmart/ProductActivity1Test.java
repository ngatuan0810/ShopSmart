package com.example.shopsmart;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class ProductActivity1Test {

    @Rule
    public ActivityScenarioRule<ProductActivity1> activityScenarioRule = new ActivityScenarioRule<>(ProductActivity1.class);

    @Test
    public void testSearchFunctionality() {
        // Clear the text in the EditText with id 'search_item_input'
        Espresso.onView(withId(R.id.search_item_input)).perform(clearText());

        // Type text into the EditText with id 'search_item_input'
        Espresso.onView(withId(R.id.search_item_input)).perform(typeText("iPhone"));

        // Click the ImageButton with id 'search_btn'
        Espresso.onView(withId(R.id.search_btn)).perform(click());

        // Check if the TextView with id 'results_found' displays the expected text
        Espresso.onView(withId(R.id.results_found)).check(matches(withText("3 Results for \"iphone\"")));

        // Check if the RecyclerView contains items with text containing "iPhone"
        Espresso.onView(withId(R.id.recyclerView))
                .check(matches(ViewMatchers.hasDescendant(withText(Matchers.containsString("iPhone")))));
    }

    @Test
    public void testPriceFilterSorting() {
        // Click the Button with id 'price_filter_btn'
        Espresso.onView(withId(R.id.price_filter_btn)).perform(click());

        // Check if the products in RecyclerView are sorted by price in descending order
        Espresso.onView(withId(R.id.recyclerView))
                .check(matches(isSortedByPriceDescending()));
    }

    @Test
    public void testLatestFilterSorting() {
        // Click the Button with id 'latest_filter_btn'
        Espresso.onView(withId(R.id.latest_filter_btn)).perform(click());

        // Check if the products in RecyclerView are sorted by latest date
        Espresso.onView(withId(R.id.recyclerView))
                .check(matches(isSortedByLatest()));
    }

    private Matcher<View> isSortedByPriceDescending() {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("is sorted by price in descending order");
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                if (adapter == null) {
                    return false;
                }

                double previousPrice = Double.MAX_VALUE;
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
                    adapter.bindViewHolder(holder, i);
                    TextView priceTextView = holder.itemView.findViewById(R.id.feeTxt);

                    if (priceTextView != null) {
                        double currentPrice = Double.parseDouble(priceTextView.getText().toString().replace(",", ""));
                        if (currentPrice > previousPrice) {
                            return false;
                        }
                        previousPrice = currentPrice;
                    }
                }
                return true;
            }
        };
    }

    private Matcher<View> isSortedByLatest() {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("is sorted by latest date");
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                if (adapter == null) {
                    return false;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date previousDate = null;
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
                    adapter.bindViewHolder(holder, i);
                    TextView dateTextView = holder.itemView.findViewById(R.id.releaseDateTxt);

                    if (dateTextView != null) {
                        try {
                            Date currentDate = dateFormat.parse(dateTextView.getText().toString());
                            if (previousDate != null && currentDate.after(previousDate)) {
                                return false;
                            }
                            previousDate = currentDate;
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }
                }
                return true;
            }
        };
    }
}
