package stacko.ste.mvpcleanarch.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import stacko.ste.mvpcleanarch.domain.model.entities.ItemDbEntity;


@Database(entities = {ItemDbEntity.class}, version = 1)
public abstract class ProjectDb extends RoomDatabase {
    public abstract UsersDao usersDao();
}
