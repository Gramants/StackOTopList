package stacko.ste.mvpcleanarch.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import stacko.ste.mvpcleanarch.App;
import stacko.ste.mvpcleanarch.AppTest;

@Module
public class AppModuleMock {


    @Provides
    Context provideContext(AppTest application) {
        return application.getApplicationContext();
    }



}


