package stacko.ste.mvpcleanarch.espressotest.util;

import android.app.Activity;
import android.content.Intent;

import java.util.Comparator;

import stacko.ste.mvpcleanarch.model.entities.ItemDbEntity;
import stacko.ste.mvpcleanarch.view.DetailActivity;


public class Utils {

    public final static int WIFI = 1;
    public final static int NETWORK = 2;
    public final static int NO_CONNECTION = 0;

    public static class CustomComparator implements Comparator<Object> {

        @Override
        public int compare(Object o1, Object o2) {
            if ((o1 instanceof ItemDbEntity) && (o2 instanceof ItemDbEntity)) {
                return ((ItemDbEntity) o2).getReputation().compareTo(((ItemDbEntity) o1).getReputation());
            }
            return 0;
        }
    }

    public static void openDetail(Activity fromActivity, long detailId) {
        Intent intent = new Intent(fromActivity, DetailActivity.class);
        intent.putExtra(Config.REPO_ID, detailId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        fromActivity.startActivity(intent);
    }

    public static long getRepoId(Intent intent) {
        return getLongExtra(intent, Config.REPO_ID,0);
    }


    private static long getLongExtra(Intent intent, String extraName, long defaultValue) {
        long value = defaultValue;
        if (intent != null) {
            value = intent.getLongExtra(extraName, value);
        }
        return value;
    }

}