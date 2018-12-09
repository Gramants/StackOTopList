package stacko.ste.mvpcleanarch.di.components;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import stacko.ste.mvpcleanarch.App;
import stacko.ste.mvpcleanarch.di.modules.AppModule;
import stacko.ste.mvpcleanarch.di.modules.DatabaseModule;
import stacko.ste.mvpcleanarch.di.modules.InteractorModule;
import stacko.ste.mvpcleanarch.di.modules.NetModule;
import stacko.ste.mvpcleanarch.di.modules.SharedPrefModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        DatabaseModule.class,
        SharedPrefModule.class,
        NetModule.class,
        InteractorModule.class,
        BuildersModule.class})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App application);

        AppComponent build();
    }

    void inject(App app);
}