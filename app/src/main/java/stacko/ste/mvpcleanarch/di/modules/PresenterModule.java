package stacko.ste.mvpcleanarch.di.modules;

import dagger.Module;
import dagger.Provides;
import stacko.ste.mvpcleanarch.interfaces.Interactor;
import stacko.ste.mvpcleanarch.interfaces.MainActivityView;
import stacko.ste.mvpcleanarch.interfaces.PersistentStorageProxy;
import stacko.ste.mvpcleanarch.presenter.MainActivityPresenterImpl;

@Module
public class PresenterModule {

    @Provides
    MainActivityPresenterImpl providePresenter(MainActivityView view,
                                               Interactor interactor,
                                               PersistentStorageProxy persistence) {
        return new MainActivityPresenterImpl(view, interactor, persistence);
    }


}