package com.jerry.demo.organizer.ui.activity;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.jerry.demo.organizer.R;
import com.jerry.demo.organizer.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ItemListTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void itemListTest() {
        ViewInteraction floatingActionButton = Espresso.onView(
                allOf(ViewMatchers.withId(R.id.btnNewItem),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.fragment_pos1),
                                        0),
                                2),
                        ViewMatchers.isDisplayed()));
        floatingActionButton.perform(ViewActions.click());

        ViewInteraction appCompatEditText = Espresso.onView(
                allOf(ViewMatchers.withId(android.R.id.input),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        ViewMatchers.isDisplayed()));
        appCompatEditText.perform(ViewActions.replaceText("test"), ViewActions.closeSoftKeyboard());

        ViewInteraction mDButton = Espresso.onView(
                allOf(ViewMatchers.withId(R.id.md_buttonDefaultPositive), ViewMatchers.withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(android.R.id.content),
                                        0),
                                4),
                        ViewMatchers.isDisplayed()));
        mDButton.perform(ViewActions.click());

        ViewInteraction recyclerView = Espresso.onView(
                allOf(ViewMatchers.withId(R.id.itemsRecyclerView),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.fragment_pos1),
                                        0),
                                1),
                        ViewMatchers.isDisplayed()));
        recyclerView.perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction floatingActionButton2 = Espresso.onView(
                allOf(ViewMatchers.withId(R.id.btnNewItem),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.fragment_pos1),
                                        0),
                                2),
                        ViewMatchers.isDisplayed()));
        floatingActionButton2.perform(ViewActions.click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText2 = Espresso.onView(
                allOf(ViewMatchers.withId(R.id.titleEditText),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.titleInputLayout),
                                        0),
                                0),
                        ViewMatchers.isDisplayed()));
        appCompatEditText2.perform(ViewActions.click());

        ViewInteraction appCompatEditText3 = Espresso.onView(
                allOf(ViewMatchers.withId(R.id.titleEditText),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.titleInputLayout),
                                        0),
                                0),
                        ViewMatchers.isDisplayed()));
        appCompatEditText3.perform(ViewActions.replaceText("test"), ViewActions.closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = Espresso.onView(
                allOf(ViewMatchers.withId(R.id.descriptionTextView),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.descriptionInputLayout),
                                        0),
                                0),
                        ViewMatchers.isDisplayed()));
        appCompatEditText4.perform(ViewActions.replaceText("test"), ViewActions.closeSoftKeyboard());

        ViewInteraction actionMenuItemView = Espresso.onView(
                allOf(ViewMatchers.withId(R.id.menu_item_save), ViewMatchers.withContentDescription("Save"),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.mainToolbar),
                                        3),
                                0),
                        ViewMatchers.isDisplayed()));
        actionMenuItemView.perform(ViewActions.click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.itemsRecyclerView)).check(new RecyclerViewItemCountAssertion(1));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    private class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        private RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            Assert.assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }
}
