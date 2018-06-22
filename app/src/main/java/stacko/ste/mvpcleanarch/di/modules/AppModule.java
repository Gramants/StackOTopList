package stacko.ste.mvpcleanarch.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import stacko.ste.mvpcleanarch.App;

@Module
public class AppModule {


    @Provides
    Context provideContext(App application) {
        return application.getApplicationContext();
    }



}


