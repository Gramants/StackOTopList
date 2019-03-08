package stacko.ste.mvpcleanarch.di.modules;

import dagger.Binds;
import dagger.Module;
import stacko.ste.mvpcleanarch.presentation.interfaces.DetailActivityView;
import stacko.ste.mvpcleanarch.presentation.view.DetailActivity;

@Module
public abstract class PresenterDetailViewModule {

    @Binds
    abstract DetailActivityView provideViewDetail(DetailActivity activity);

}