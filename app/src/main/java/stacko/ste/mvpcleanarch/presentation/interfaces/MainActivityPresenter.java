package stacko.ste.mvpcleanarch.presentation.interfaces;


public interface MainActivityPresenter {

    void doFetchAllRepos();

    void getResultFromDb();

    void updateBlockedStatusTo(boolean isOn, Long id);

    void updateFollowingStatusTo(boolean isOn, Long id);

    void networkStatusChanged(boolean mActive);
}
