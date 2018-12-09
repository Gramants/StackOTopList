package stacko.ste.mvpcleanarch.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import stacko.ste.mvpcleanarch.data.database.ProjectDb;
import stacko.ste.mvpcleanarch.data.database.UsersDao;
import stacko.ste.mvpcleanarch.espressotest.util.Config;

@Module
public class DatabaseModuleMock {


    @Provides
    @Singleton
    ProjectDb provideProjectDb(Context context) {
        return Room.inMemoryDatabaseBuilder(context, ProjectDb.class).allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    UsersDao provideRepoDao(ProjectDb projectDb) {
        return projectDb.usersDao();
    }


}