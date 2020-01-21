package com.example.beerlab;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.beerlab.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class NavigationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void test_menuIsDefault() {
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()));
        onView(withId(R.id.menu)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isNavbarToggledOnClick() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isProfileDisplayedOnClick() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withText(R.string.profile)).perform(click());
        onView(withId(R.id.profile)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isCartDisplayedOnClick() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withText(R.string.cart)).perform(click());
        onView(withId(R.id.cart)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isCurrencyDisplayedOnClick() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withText(R.string.currency)).perform(click());
        onView(withId(R.id.currency)).check(matches(isDisplayed()));
    }
}