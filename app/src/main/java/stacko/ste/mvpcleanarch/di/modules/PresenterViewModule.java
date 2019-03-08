package stacko.ste.mvpcleanarch.di.modules;

import dagger.Binds;
import dagger.Module;
import stacko.ste.mvpcleanarch.presentation.interfaces.MainActivityView;
import stacko.ste.mvpcleanarch.presentation.view.MainActivity;

@Module
public abstract class PresenterViewModule {

    @Binds
    abstract MainActivityView provideView(MainActivity activity);

}