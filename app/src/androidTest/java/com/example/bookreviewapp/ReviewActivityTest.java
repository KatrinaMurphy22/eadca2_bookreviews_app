package com.example.bookreviewapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class ReviewActivityTest {

    @Rule
    public ActivityScenarioRule<ReviewActivity> activityRule =
            new ActivityScenarioRule<>(ReviewActivity.class);

    @Test
    public void testReviewDisplay() {
        onView(withId(R.id.reviews_text_view)).check(matches(withText("")));
    }
}

