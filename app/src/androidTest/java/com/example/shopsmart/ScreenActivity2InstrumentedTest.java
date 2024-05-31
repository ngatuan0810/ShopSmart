package com.example.shopsmart;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static org.junit.Assert.assertEquals;
import androidx.core.content.ContextCompat;
import androidx.test.core.app.ActivityScenario;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class ScreenActivity2InstrumentedTest {

    @Rule
    public ActivityScenarioRule<ScreenActivity2> activityScenarioRule = new ActivityScenarioRule<>(ScreenActivity2.class);

    @Test
    public void testGotoUrl() {
        // Initialize Intents
        Intents.init();

        try {
            activityScenarioRule.getScenario().onActivity(activity -> {
                activity.gotoUrl("https://www.example.com");
            });

            // Verify the intent was fired
            Intents.intended(hasAction(Intent.ACTION_VIEW));
        } finally {
            // Release Intents
            Intents.release();
        }
    }

    private ScreenActivity2 activity2;
    private LinearLayout indicatorContainer;

    @Before
    public void setUp() {
        // Launch the activity on the main thread
        ActivityScenario<ScreenActivity2> scenario = ActivityScenario.launch(ScreenActivity2.class);

        // Access the activity instance
        scenario.onActivity(activity -> {
            activity2 = activity;
            indicatorContainer = new LinearLayout(activity.getApplicationContext());
            activity2.indicatorContainer = indicatorContainer;
        });
    }

    @Test
    public void testCreateIndicators() {
        activity2.createIndicators();

        // Verify that the correct number of indicators are created
        assertEquals(activity2.images.length, indicatorContainer.getChildCount());

        // Verify that each indicator has the correct drawable resource set
        for (int i = 0; i < activity2.images.length; i++) {
            ImageView indicator = (ImageView) indicatorContainer.getChildAt(i);
            Drawable expectedDrawable = ContextCompat.getDrawable(activity2, R.drawable.indicator_inactive);
            Drawable actualDrawable = indicator.getDrawable();

            assertNotNull("Drawable should not be null", actualDrawable);

            // Check if the drawables are of the same type
            assertEquals("Drawable class should match", expectedDrawable.getClass(), actualDrawable.getClass());
        }
    }
}


