package stacko.ste.mvpcleanarch.di.components;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import stacko.ste.mvpcleanarch.di.modules.PresenterDetailModule;
import stacko.ste.mvpcleanarch.di.modules.PresenterDetailViewModule;
import stacko.ste.mvpcleanarch.di.modules.PresenterModule;
import stacko.ste.mvpcleanarch.di.modules.PresenterViewModule;
import stacko.ste.mvpcleanarch.view.DetailActivity;
import stacko.ste.mvpcleanarch.view.MainActivity;


@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = {PresenterViewModule.class, PresenterModule.class})
    abstract MainActivity bindActivity();

    @ContributesAndroidInjector(modules = {PresenterDetailViewModule.class, PresenterDetailModule.class})
    abstract DetailActivity bindDetailActivity();
}