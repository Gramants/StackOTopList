package stacko.ste.mvpcleanarch.di.modules;

import dagger.Binds;
import dagger.Module;
import stacko.ste.mvpcleanarch.interfaces.DetailActivityView;
import stacko.ste.mvpcleanarch.view.DetailActivity;

@Module
public abstract class PresenterDetailViewModule {

    @Binds
    abstract DetailActivityView provideViewDetail(DetailActivity activity);

}