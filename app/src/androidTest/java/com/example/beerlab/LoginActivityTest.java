package com.example.beerlab;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.beerlab.activity.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4ClassRunner.class)
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

    @Test
    public void test_loginWithEmptyForm() {
        onView(withId(R.id.button_login_id)).perform(click());

        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    @Test
    public void test_loginWithInvalidEmailFormat() {
        ViewInteraction loginText = onView(withId(R.id.login_act_email_id));
        loginText.perform(typeText("notAnEmail"));
        loginText.check(matches(withText("notAnEmail")));
        loginText.check(matches(hasErrorText("Invalid email format")));
    }

    @Test
    public void test_registerClick() {
        ViewInteraction registerButton = onView(withId(R.id.textView_registerHere));
        registerButton.check(matches(isDisplayed()));
        registerButton.perform(click());
        onView(withId(R.id.register)).check(matches(isDisplayed()));
    }

//    @Test
//    public void test_validLogin() throws Exception{
//        server = new MockWebServer();
//        server.enqueue(new MockResponse()
//                        .setResponseCode(200)
//                        .setBody("OK"));
//        // TODO: use server.url() in the request call
//        server.start();
//        onView(withId(R.id.login_act_email_id)).perform(typeText("valid@email.com"));
//        onView(withId(R.id.login_actv_password_id)).perform(typeText("password"));
//        onView(withId(R.id.button_login_id)).perform(click());
//
//        onView(withId(R.id.menu)).check(matches(isDisplayed()));
//    }
}