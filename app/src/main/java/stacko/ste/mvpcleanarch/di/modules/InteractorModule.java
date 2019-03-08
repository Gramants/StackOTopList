package stacko.ste.mvpcleanarch.di.modules;

import dagger.Module;
import dagger.Provides;
import stacko.ste.mvpcleanarch.data.database.ProjectDb;
import stacko.ste.mvpcleanarch.data.database.UsersDao;
import stacko.ste.mvpcleanarch.domain.InteractorImpl;
import stacko.ste.mvpcleanarch.data.api.Api;
import stacko.ste.mvpcleanarch.domain.Interactor;

@Module
public class InteractorModule {

    @Provides
    Interactor provideInteractor(
            Api api,
            ProjectDb db,
            UsersDao dao) {

        return new InteractorImpl(api, db, dao);
    }

}