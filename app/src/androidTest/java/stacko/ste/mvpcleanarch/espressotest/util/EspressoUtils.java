package stacko.ste.mvpcleanarch.espressotest.util;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class EspressoUtils {

    public static void checkViewWithIdIsCompletelyDisplayed(final int id) {
        int retries = 20;
        do {
            try {
                onView(withId(id)).check(matches(isCompletelyDisplayed()));
                return;
            } catch (final junit.framework.AssertionFailedError e) {
                try { // Retry every half a second during 10 seconds
                    Thread.sleep(500);
                } catch (final InterruptedException ex) {
                }
            }
        } while (--retries > 0);

    }
}
