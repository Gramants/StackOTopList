package stacko.ste.mvpcleanarch.domain;

import com.google.common.collect.Lists;

import java.util.List;

import stacko.ste.mvpcleanarch.data.database.DeleteRecords;
import stacko.ste.mvpcleanarch.data.database.ProjectDb;
import stacko.ste.mvpcleanarch.data.database.UsersDao;
import stacko.ste.mvpcleanarch.data.database.UpdateRecord;
import stacko.ste.mvpcleanarch.data.database.UpdateRecords;
import stacko.ste.mvpcleanarch.interfaces.Api;
import stacko.ste.mvpcleanarch.interfaces.Interactor;
import stacko.ste.mvpcleanarch.model.Item;
import stacko.ste.mvpcleanarch.model.TopUsersResponse;
import stacko.ste.mvpcleanarch.model.entities.ItemDbEntity;
import stacko.ste.mvpcleanarch.util.Config;
import io.reactivex.Flowable;

import com.google.common.base.Function;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InteractorImpl implements Interactor {

    private Api api;
    private UsersDao usersDao;
    private ProjectDb projectDb;


    public InteractorImpl(Api api, ProjectDb projectDb, UsersDao usersDao) {
        this.api = api;
        this.usersDao = usersDao;
        this.projectDb = projectDb;
    }




    @Override
    public void fetchAllReposUseCase(DatafromServer datafromServer) {

        api.getTopUsers(Config.QUERY_VAL_DIRECTION, Config.QUERY_VAL_ORDERBY,Config.QUERY_VAL_SITENAME,Config.QUERY_VAL_PAGESIZE).enqueue(new Callback<TopUsersResponse>() {

            @Override
            public void onResponse(Call<TopUsersResponse> call, Response<TopUsersResponse> response) {
                    if (response.isSuccessful()){
                        TopUsersResponse fullResponse=response.body();
                        datafromServer.onGetRepoFromRemote(Lists.transform(fullResponse.getItems(),dbEntity));
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                datafromServer.setError(t.getMessage());
            }
        });

    }





    public Function<Item, ItemDbEntity> dbEntity =
        new Function<Item, ItemDbEntity>() {
            @Override
            public ItemDbEntity apply(Item item) {
                return new ItemDbEntity(item.getAccountId(),item.getReputation(),item.getCreationDate(),item.getUserId(),item.getProfileImage(),item.getDisplayName(),item.getLocation(),false,false);
            }

        };

    @Override
    public Flowable<List<ItemDbEntity>> loadReposFromDb() {
        return usersDao.getAllRepos();
    }

    @Override
    public Flowable<ItemDbEntity> loadRepoFromDbById(long repoId) {
        return usersDao.getRepoById(repoId);
    }



    @Override
    public void updateDb(List<ItemDbEntity> sanitizedRecords) {
        new UpdateRecords() {

            @Override
            protected void updateRecords() {
                projectDb.beginTransaction();
                try {
                    usersDao.updateData(sanitizedRecords);
                    projectDb.setTransactionSuccessful();
                } finally {
                    projectDb.endTransaction();
                }
            }

        };

    }

    @Override
    public void deleteAllUseCase() {
        new DeleteRecords() {

            @Override
            protected void deleteRecords() {
                projectDb.beginTransaction();
                try {
                    usersDao.deleteAll();
                    projectDb.setTransactionSuccessful();
                } finally {
                    projectDb.endTransaction();
                }
            }

        };
    }

    @Override
    public void updateBlockedStatusUseCase(boolean isOn, Long id) {
        new UpdateRecord() {

            @Override
            protected void updateRecord() {
                projectDb.beginTransaction();
                try {
                    usersDao.updateBlockedStatus(id,isOn);
                    projectDb.setTransactionSuccessful();
                } finally {
                    projectDb.endTransaction();
                }
            }

        };

    }

    @Override
    public void updateFollowingStatusUseCase(boolean isOn, Long id) {
        new UpdateRecord() {

            @Override
            protected void updateRecord() {
                projectDb.beginTransaction();
                try {
                    usersDao.updateFollowingStatus(id,isOn);
                    projectDb.setTransactionSuccessful();
                } finally {
                    projectDb.endTransaction();
                }
            }

        };
    }


}
