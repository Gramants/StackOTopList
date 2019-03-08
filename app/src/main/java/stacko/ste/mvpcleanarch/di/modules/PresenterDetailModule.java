package stacko.ste.mvpcleanarch.di.modules;

import dagger.Module;
import dagger.Provides;
import stacko.ste.mvpcleanarch.domain.Interactor;
import stacko.ste.mvpcleanarch.presentation.interfaces.DetailActivityView;
import stacko.ste.mvpcleanarch.presentation.DetailActivityPresenterImpl;

@Module
public class PresenterDetailModule {

    @Provides
    DetailActivityPresenterImpl providePresenterDetail(DetailActivityView view,
                                                       Interactor interactor) {
        return new DetailActivityPresenterImpl(view, interactor);
    }


}