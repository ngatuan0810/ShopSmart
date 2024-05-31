package com.example.shopsmart;

import android.view.Menu;
import android.view.MenuItem;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class Nav_Instrumented_Test {

    @Rule
    public ActivityScenarioRule<ScreenActivity2> activityRule =
            new ActivityScenarioRule<>(ScreenActivity2.class);

    @Test
    public void testBottomNavigationViewMenuItems() {
        ActivityScenario<ScreenActivity2> scenario = activityRule.getScenario();
        scenario.onActivity(activity -> {
            BottomNavigationView navView = activity.findViewById(R.id.nav);
            assertNotNull("BottomNavigationView should not be null", navView);

            Menu menu = navView.getMenu();
            assertNotNull("Menu should not be null", menu);

            MenuItem meIcon = menu.findItem(R.id.me);
            assertNotNull("meIcon should not be null", meIcon);

            MenuItem productIcon = menu.findItem(R.id.product);
            assertNotNull("productIcon should not be null", productIcon);
        });
    }

    @Test
    public void testMeIconNavigatesToCorrectActivity() {
        Intents.init();

        ActivityScenario<ScreenActivity2> scenario = activityRule.getScenario();
        scenario.onActivity(activity -> {
            BottomNavigationView navView = activity.findViewById(R.id.nav);
            MenuItem meIcon = navView.getMenu().findItem(R.id.me);
            navView.setSelectedItemId(R.id.me);
        });

        intended(hasComponent(UserProfileActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void testProductIconNavigatesToCorrectActivity() {
        Intents.init();

        ActivityScenario<ScreenActivity2> scenario = activityRule.getScenario();
        scenario.onActivity(activity -> {
            BottomNavigationView navView = activity.findViewById(R.id.nav);
            MenuItem productIcon = navView.getMenu().findItem(R.id.product);
            navView.setSelectedItemId(R.id.product);
        });

        intended(hasComponent(ProductActivity1.class.getName()));
        Intents.release();
    }
}
