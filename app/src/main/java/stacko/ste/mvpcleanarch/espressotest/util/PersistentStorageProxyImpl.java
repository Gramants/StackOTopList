package stacko.ste.mvpcleanarch.espressotest.util;

import android.content.SharedPreferences;

import stacko.ste.mvpcleanarch.interfaces.PersistentStorageProxy;


public class PersistentStorageProxyImpl implements PersistentStorageProxy {

    private static final String LAST_NETWORK_STATUS = "LAST_NETWORK_STATUS";

    private SharedPreferences mPreferences;

    public PersistentStorageProxyImpl(SharedPreferences sharedPreferences) {
        this.mPreferences = sharedPreferences;
    }

    @Override
    public Boolean getLastNetworkStatus() {
        return mPreferences.getBoolean(LAST_NETWORK_STATUS, true);
    }

    @Override
    public void setNetworkStatus(Boolean networkStatus) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(LAST_NETWORK_STATUS, networkStatus);
        editor.apply();
    }
}
