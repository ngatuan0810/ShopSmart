package com.example.shopsmart;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;
import android.widget.TextView;

@RunWith(AndroidJUnit4.class)
public class BrandFilterUITest {

    @Rule
    public ActivityScenarioRule<ProductActivity1> activityScenarioRule = new ActivityScenarioRule<>(ProductActivity1.class);

    @Test
    public void testFilterByBrandSamsung() {
        // Ensure RecyclerView is displayed
        Espresso.onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed()));

        // Click on the filter button
        Espresso.onView(withId(R.id.filter_button))
                .perform(ViewActions.click());

        // Click on the brand button (Samsung) in the brands_recycler
        Espresso.onView(withId(R.id.brands_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new ClickOnViewChild(R.id.brand_button)));

        // Wait for the filtering process
        try {
            Thread.sleep(2000);  // Wait for 2 seconds to allow the filtering process to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check that all products in the recyclerView are filtered by brand: Samsung
        Espresso.onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed()))
                .check(matches(hasItemWithBrand("Samsung")));
    }

    private static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(androidx.test.espresso.UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

    private static Matcher<View> hasItemWithBrand(final String brand) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                if (adapter == null) {
                    return false;
                }
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
                    adapter.bindViewHolder(holder, i);
                    TextView brandTextView = holder.itemView.findViewById(R.id.brand_name_txt);
                    if (brandTextView != null && !brandTextView.getText().toString().contains(brand)) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("RecyclerView should have all items with brand: " + brand);
            }
        };
    }

    private static class ClickOnViewChild implements ViewAction {
        private final int viewId;

        public ClickOnViewChild(int viewId) {
            this.viewId = viewId;
        }

        @Override
        public Matcher<View> getConstraints() {
            return isDisplayed();
        }

        @Override
        public String getDescription() {
            return "Click on a child view with specified id.";
        }

        @Override
        public void perform(androidx.test.espresso.UiController uiController, View view) {
            View v = view.findViewById(viewId);
            v.performClick();
        }
    }
}
