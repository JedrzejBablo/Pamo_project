package com.example.beerlab;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void test_isActivityInView() {
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isLogoInView() {
        onView(withId(R.id.imageView_logo)).check(matches(isDisplayed()));
        onView(withId(R.id.textView_logo)).check(matches(isDisplayed()));

    }

    @Test
    public void test_isLoginFormInView() {
        onView(withId(R.id.login_act_email_id)).check(matches(isDisplayed()));
        onView(withId(R.id.login_actv_password_id)).check(matches(isDisplayed()));
        onView(withId(R.id.button_login_id)).check(matches(isDisplayed()));
    }
}