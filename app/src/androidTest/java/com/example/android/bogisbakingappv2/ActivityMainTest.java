package com.example.android.bogisbakingappv2;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by Bogi on 2018. 06. 03..
 */
@RunWith(AndroidJUnit4.class)

public class ActivityMainTest {

@Rule public ActivityTestRule<ActivityMain> mActivityTestRule =
        new ActivityTestRule<>(ActivityMain.class);
@Test
    public void clickRecipe() {
    onView(withId(R.id.recipesList)).perform(click());
    onView(withId(R.id.recipesList)).check(matches(isCompletelyDisplayed()));
}


}
