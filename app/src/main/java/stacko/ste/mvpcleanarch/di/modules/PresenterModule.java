package stacko.ste.mvpcleanarch.di.modules;

import dagger.Module;
import dagger.Provides;
import stacko.ste.mvpcleanarch.domain.Interactor;
import stacko.ste.mvpcleanarch.presentation.interfaces.MainActivityView;
import stacko.ste.mvpcleanarch.data.persistent.PersistentStorageProxy;
import stacko.ste.mvpcleanarch.presentation.MainActivityPresenterImpl;

@Module
public class PresenterModule {

    @Provides
    MainActivityPresenterImpl providePresenter(MainActivityView view,
                                               Interactor interactor,
                                               PersistentStorageProxy persistence) {
        return new MainActivityPresenterImpl(view, interactor, persistence);
    }


}