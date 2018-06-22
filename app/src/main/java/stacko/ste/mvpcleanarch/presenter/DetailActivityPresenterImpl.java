package stacko.ste.mvpcleanarch.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import stacko.ste.mvpcleanarch.interfaces.DetailActivityPresenter;
import stacko.ste.mvpcleanarch.interfaces.DetailActivityView;
import stacko.ste.mvpcleanarch.interfaces.Interactor;
import stacko.ste.mvpcleanarch.model.entities.ItemDbEntity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class DetailActivityPresenterImpl implements DetailActivityPresenter, LifecycleObserver {


    private Interactor interactor;
    private DetailActivityView viewInterface;
    private CompositeDisposable disposeBag;


    public DetailActivityPresenterImpl(DetailActivityView viewInterface, Interactor interactor) {
        this.viewInterface = viewInterface;
        this.interactor = interactor;

        if (viewInterface instanceof LifecycleOwner) {
            ((LifecycleOwner) viewInterface).getLifecycle().addObserver(this);
        }
        disposeBag = new CompositeDisposable();
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        disposeBag.clear();
    }


    private void handleReturnedData(ItemDbEntity repo) {
        viewInterface.hideProgress();
        viewInterface.onGetRepoDetailFromDb(repo);

    }


    private void handleError(Throwable error) {
        viewInterface.hideProgress();
        viewInterface.writeToStatus(error.getLocalizedMessage());
    }


    @Override
    public void getRepoById(long repoId) {
        viewInterface.showProgress();
        Disposable disposable = interactor.loadRepoFromDbById(repoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .subscribe(this::handleReturnedData, this::handleError, () -> viewInterface.hideProgress());

        disposeBag.add(disposable);

    }
}
