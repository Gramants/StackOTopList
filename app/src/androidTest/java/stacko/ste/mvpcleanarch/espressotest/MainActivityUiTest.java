package stacko.ste.mvpcleanarch.espressotest;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import stacko.ste.mvpcleanarch.R;
import stacko.ste.mvpcleanarch.espressotest.util.ActivityFinisher;
import stacko.ste.mvpcleanarch.espressotest.util.EspressoUtils;
import stacko.ste.mvpcleanarch.espressotest.util.RecyclerViewMatcher;
import stacko.ste.mvpcleanarch.view.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MainActivityUiTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @After
    public void tearDown() {
        ActivityFinisher.finishOpenActivities();
    }


    @Test
    public void IsRecyclerViewDisplayed(){
        EspressoUtils.checkViewWithIdIsCompletelyDisplayed(R.id.repo_list);
    }


    @Test
    public void CheckRecyclerViewFirstItem(){
       onView(new RecyclerViewMatcher(R.id.repo_list).atPositionOnView(0, R.id.tv_name)).check(matches(withText("TEST ITEM")));
    }





}