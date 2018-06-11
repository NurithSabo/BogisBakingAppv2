package com.example.android.bogisbakingappv2;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.android.bogisbakingappv2.ActivityDetail;
import com.google.android.exoplayer2.SimpleExoPlayer;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created by Bogi on 2018. 06. 06..
 */

public class ActivityDetailTest {


    @Rule
    public ActivityTestRule<ActivityDetail> mActivityTestRule =
            new ActivityTestRule<>(ActivityDetail.class);

    @Test
    public void clickOnRecyclerViewItem() {

        onView(withId(R.id.recipe_details_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.content_frame_recipe_details))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnStepWithVideo() {

        onView(withId(R.id.recipe_details_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(
                allOf(
                        withId(R.id.recipe_step_video),
                        withClassName(is(SimpleExoPlayer.class.getName()))));
    }

   @Test
    public void clickOnStepWithoutVideo() {

        onView(withId(R.id.recipe_details_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(
                allOf(
                        withId(R.id.android_eats_apple_image),
                        isDisplayed()))
                .check(matches(isDisplayed()));
    }

}
