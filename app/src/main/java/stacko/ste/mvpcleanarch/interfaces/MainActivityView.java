package stacko.ste.mvpcleanarch.interfaces;


import java.util.List;


import stacko.ste.mvpcleanarch.model.entities.ItemDbEntity;

public interface MainActivityView {
    void showProgress();

    void hideProgress();

    void onError(String type);

    void writeToStatus(String status);

    void onGetRepoListFromDb(List<ItemDbEntity> list);

    void writeToStatusBackOnline();

    void writeToStatusOffline();
}
