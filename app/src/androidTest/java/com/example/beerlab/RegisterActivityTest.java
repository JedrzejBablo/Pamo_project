package com.example.beerlab;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RegisterActivityTest {

    @Rule
    public ActivityScenarioRule<RegisterActivity> activityScenarioRule = new ActivityScenarioRule<>(RegisterActivity.class);

    @Test
    public void test_isEverythingDisplayed() {
        onView(withId(R.id.register)).check(matches(isDisplayed()));
        onView(withId(R.id.textView_register)).check(matches(isDisplayed()));
        onView(withId(R.id.editText_register_nickname)).check(matches(isDisplayed()));
        onView(withId(R.id.editText_register_email)).check(matches(isDisplayed()));
        onView(withId(R.id.editText_register_password)).check(matches(isDisplayed()));
        onView(withId(R.id.textView_gender)).check(matches(isDisplayed()));
        onView(withId(R.id.radioGroup_gender)).check(matches(isDisplayed()));
        onView(withId(R.id.register_button_id)).check(matches(isDisplayed()));
        onView(withId(R.id.ageCheckBox)).check(matches(isDisplayed()));
    }

    @Test
    public void test_registerEmpty() {
        onView(withId(R.id.register_button_id)).perform(click());
        onView(withId(R.id.errorText)).check(matches(not(withText(""))));
    }

    @Test
    public void test_invalidEmailFormat() {
        ViewInteraction emailText = onView(withId(R.id.editText_register_email));
        emailText.perform(typeText("notAnEmail"));
        emailText.check(matches(hasErrorText("Invalid email format")));
    }

    @Test
    public void test_validEmailFormat() {
        ViewInteraction emailText = onView(withId(R.id.editText_register_email));
        emailText.perform(typeText("example@email.com"));
        emailText.check(matches(not(hasErrorText("Invalid email format"))));
    }

    @Test
    public void test_registerWithNoAge() {
        onView(withId(R.id.editText_register_email)).perform(typeText("example@email.com"));
        onView(withId(R.id.editText_register_password)).perform(typeText("example"));
        onView(withId(R.id.editText_register_nickname)).perform(typeText("nickname"));
        closeSoftKeyboard();
        onView(withId(R.id.register_button_id)).perform(click());
        onView(withId(R.id.errorText)).check(matches(withText(R.string.user_age)));
    }

    @Test
    public void test_registerWithNoGender() {
        onView(withId(R.id.editText_register_email)).perform(typeText("example@email.com"));
        onView(withId(R.id.editText_register_password)).perform(typeText("example"));
        onView(withId(R.id.editText_register_nickname)).perform(typeText("nickname"));
        onView(withId(R.id.ageCheckBox)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.register_button_id)).perform(click());

        onView(withId(R.id.errorText)).check(matches(withText(R.string.user_gender)));
    }

    @Test
    public void valid_registration() {
        onView(withId(R.id.editText_register_email)).perform(typeText("example@email.com"));
        onView(withId(R.id.editText_register_password)).perform(typeText("example"));
        onView(withId(R.id.editText_register_nickname)).perform(typeText("nickname"));
        onView(withId(R.id.ageCheckBox)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.register_gender_male)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.register_button_id)).perform(click());

        // TODO: mock API - same as Login

        onView(withId(R.id.errorText)).check(matches(withText("")));
    }
}