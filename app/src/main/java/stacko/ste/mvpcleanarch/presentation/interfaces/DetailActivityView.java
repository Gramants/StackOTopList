package stacko.ste.mvpcleanarch.presentation.interfaces;


import stacko.ste.mvpcleanarch.domain.model.entities.ItemDbEntity;

public interface DetailActivityView {

    void showProgress();

    void hideProgress();

    void onError(String type);

    void onGetRepoDetailFromDb(ItemDbEntity repoItem);

    void writeToStatus(String localizedMessage);
}
