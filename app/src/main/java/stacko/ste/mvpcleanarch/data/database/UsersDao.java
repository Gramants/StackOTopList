package stacko.ste.mvpcleanarch.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import stacko.ste.mvpcleanarch.domain.model.entities.ItemDbEntity;
import stacko.ste.mvpcleanarch.base.Config;
import io.reactivex.Flowable;


@Dao
public abstract class UsersDao {
    @Query("SELECT * FROM " + Config.TABLE_USERS)
    public abstract Flowable<List<ItemDbEntity>> getAllRepos();

    @Query("SELECT * FROM " + Config.TABLE_USERS + " where account_id = :id")
    public abstract Flowable<ItemDbEntity> getRepoById(long id);

    @Query("DELETE FROM " + Config.TABLE_USERS)
    public abstract void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<ItemDbEntity> items);

    @Query("UPDATE " + Config.TABLE_USERS + " SET following = :followingstatus  WHERE account_id = :accountid")
    public abstract void updateFollowingStatus(long accountid, Boolean followingstatus);

    @Query("UPDATE " + Config.TABLE_USERS + " SET blocked = :blockedstatus  WHERE account_id = :accountid")
    public abstract void updateBlockedStatus(long accountid, Boolean blockedstatus);

    @Transaction
    public void updateData(List<ItemDbEntity> items) {
        deleteAll();
        insert(items);
    }

}
