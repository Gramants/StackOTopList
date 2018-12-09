package stacko.ste.mvpcleanarch.di.modules;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import stacko.ste.mvpcleanarch.interfaces.PersistentStorageProxy;
import stacko.ste.mvpcleanarch.espressotest.util.PersistentStorageProxyImpl;


@Module
public class SharedPrefModule {


    @Singleton
    @Provides
    SharedPreferences provideDefaultSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Provides
    PersistentStorageProxy providePersistentStorageProxy(SharedPreferences preferences) {
        return new PersistentStorageProxyImpl(preferences);
    }

}