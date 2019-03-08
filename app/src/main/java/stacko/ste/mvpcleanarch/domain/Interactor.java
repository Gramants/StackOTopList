package stacko.ste.mvpcleanarch.domain;


import java.util.List;

import stacko.ste.mvpcleanarch.domain.model.entities.ItemDbEntity;
import io.reactivex.Flowable;

public interface Interactor {

    void fetchAllReposUseCase(DatafromServer datafromServer);

    Flowable<List<ItemDbEntity>> loadReposFromDb();

    Flowable<ItemDbEntity> loadRepoFromDbById(long repoId);

    void updateDb(List<ItemDbEntity> data);

    void deleteAllUseCase();

    void updateBlockedStatusUseCase(boolean isOn, Long id);

    void updateFollowingStatusUseCase(boolean isOn, Long id);


    interface DatafromServer {

        void onGetRepoFromRemote(List<ItemDbEntity> data);

        void setError(String message);
    }

}
