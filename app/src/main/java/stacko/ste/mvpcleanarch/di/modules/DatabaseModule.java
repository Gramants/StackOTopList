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
public class DatabaseModule {


    @Provides
    @Singleton
    ProjectDb provideProjectDb(Context context) {
        return Room.databaseBuilder(context, ProjectDb.class, Config.DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    UsersDao provideRepoDao(ProjectDb projectDb) {
        return projectDb.usersDao();
    }


}