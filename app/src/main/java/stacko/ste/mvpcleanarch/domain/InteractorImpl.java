package stacko.ste.mvpcleanarch.domain;

import java.util.List;

import javax.annotation.Nullable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import stacko.ste.mvpcleanarch.data.database.DeleteRecords;
import stacko.ste.mvpcleanarch.data.database.ProjectDb;
import stacko.ste.mvpcleanarch.data.database.UpdateRecord;
import stacko.ste.mvpcleanarch.data.database.UpdateRecords;
import stacko.ste.mvpcleanarch.data.database.UsersDao;
import stacko.ste.mvpcleanarch.interfaces.Api;
import stacko.ste.mvpcleanarch.interfaces.Interactor;
import stacko.ste.mvpcleanarch.model.Item;
import stacko.ste.mvpcleanarch.model.TopUsersResponse;
import stacko.ste.mvpcleanarch.model.entities.ItemDbEntity;
import stacko.ste.mvpcleanarch.espressotest.util.Config;


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

        api.getTopUsers(Config.QUERY_VAL_DIRECTION, Config.QUERY_VAL_ORDERBY, Config.QUERY_VAL_SITENAME, Config.QUERY_VAL_PAGESIZE)
                .subscribeOn(Schedulers.io()) // do the network call on another thread
                .observeOn(AndroidSchedulers.mainThread()) // return the result in mainThread
                .map(new Function<TopUsersResponse, List<Item>>() {

                    @Nullable
                    @Override
                    public List<Item> apply(@Nullable TopUsersResponse topUsersResponse) {
                        return topUsersResponse.getItems();
                    }
                })
                .flatMap(list ->
                        Observable.fromIterable(list)
                                .map(new Function<Item, ItemDbEntity>() {
                                    @Override
                                    public ItemDbEntity apply(Item item) throws Exception {
                                        return new ItemDbEntity(item.getAccountId(), item.getReputation(), item.getCreationDate(), item.getUserId(), item.getProfileImage(), item.getDisplayName(), item.getLocation(), false, false);
                                    }

                                })
                                .toList()
                                .toObservable()

                )

                .subscribe(new Observer<List<ItemDbEntity>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ItemDbEntity> itemDbEntities) {
                        datafromServer.onGetRepoFromRemote(itemDbEntities);
                    }

                    @Override
                    public void onError(Throwable e) {
                        datafromServer.setError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                });


    }


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
                    usersDao.updateBlockedStatus(id, isOn);
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
                    usersDao.updateFollowingStatus(id, isOn);
                    projectDb.setTransactionSuccessful();
                } finally {
                    projectDb.endTransaction();
                }
            }

        };
    }


}
