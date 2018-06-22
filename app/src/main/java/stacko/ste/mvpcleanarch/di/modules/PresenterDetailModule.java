package stacko.ste.mvpcleanarch.di.modules;

import dagger.Module;
import dagger.Provides;
import stacko.ste.mvpcleanarch.interfaces.Interactor;
import stacko.ste.mvpcleanarch.interfaces.DetailActivityView;
import stacko.ste.mvpcleanarch.presenter.DetailActivityPresenterImpl;

@Module
public class PresenterDetailModule {

    @Provides
    DetailActivityPresenterImpl providePresenterDetail(DetailActivityView view,
                                                       Interactor interactor) {
        return new DetailActivityPresenterImpl(view, interactor);
    }


}