package stacko.ste.mvpcleanarch.di.components;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import stacko.ste.mvpcleanarch.App;
import stacko.ste.mvpcleanarch.AppTest;
import stacko.ste.mvpcleanarch.di.modules.AppModule;
import stacko.ste.mvpcleanarch.di.modules.AppModuleMock;
import stacko.ste.mvpcleanarch.di.modules.DatabaseModule;
import stacko.ste.mvpcleanarch.di.modules.DatabaseModuleMock;
import stacko.ste.mvpcleanarch.di.modules.InteractorModule;
import stacko.ste.mvpcleanarch.di.modules.NetModule;
import stacko.ste.mvpcleanarch.di.modules.NetModuleMock;
import stacko.ste.mvpcleanarch.di.modules.SharedPrefModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModuleMock.class,
        DatabaseModuleMock.class,
        SharedPrefModule.class,
        NetModuleMock.class,
        InteractorModule.class,
        BuildersModule.class})
public interface AppComponentTest {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(AppTest application);

        AppComponentTest build();
    }

    void inject(AppTest app);
}