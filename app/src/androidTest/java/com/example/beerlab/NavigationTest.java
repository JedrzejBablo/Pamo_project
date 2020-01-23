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
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isNavbarToggledOnClick() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
        onView(withText(R.string.profile)).check(matches(isDisplayed()));
        onView(withText(R.string.currency)).check(matches(isDisplayed()));
        onView(withText(R.string.menu)).check(matches(isDisplayed()));
        onView(withText(R.string.cart)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isProfileDisplayedOnClick() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withText(R.string.profile)).perform(click());
        onView(withId(R.id.profile_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.imgUser)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_name)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_address)).check(matches(isDisplayed()));
        onView(withId(R.id.linlay1)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isCartDisplayedOnClick() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withText(R.string.cart)).perform(click());
        onView(withId(R.id.cart)).check(matches(isDisplayed()));
        onView(withId(R.id.order_item_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.textView_total)).check(matches(isDisplayed()));
        onView(withId(R.id.button_confirmOrder)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isCurrencyDisplayedOnClick() {
        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click());
        onView(withText(R.string.currency)).perform(click());
        onView(withId(R.id.currency_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.linearLayout1)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView1)).check(matches(isDisplayed()));
        onView(withId(R.id.currency_10)).check(matches(isDisplayed()));
        onView(withId(R.id.currency_20)).check(matches(isDisplayed()));
        onView(withId(R.id.currency_30)).check(matches(isDisplayed()));
        onView(withId(R.id.currency_40)).check(matches(isDisplayed()));
    }
}