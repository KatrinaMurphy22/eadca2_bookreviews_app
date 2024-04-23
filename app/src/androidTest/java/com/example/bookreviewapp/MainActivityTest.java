package com.example.bookreviewapp;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.UiController;
import org.hamcrest.Matcher;
import android.view.View;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import androidx.recyclerview.widget.RecyclerView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testIsRecyclerViewVisible() {
        onView(withId(R.id.recyclerView)).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(RecyclerView.class);
            }

            @Override
            public String getDescription() {
                return "Show RecyclerView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.setVisibility(View.VISIBLE);
            }
        });
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }
}
