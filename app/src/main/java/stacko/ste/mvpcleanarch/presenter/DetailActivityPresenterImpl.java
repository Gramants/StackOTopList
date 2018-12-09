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
    private DetailActivityView view;
    private CompositeDisposable disposeBag;


    public DetailActivityPresenterImpl(DetailActivityView view, Interactor interactor) {
        this.view = view;
        this.interactor = interactor;

        if (view instanceof LifecycleOwner) {
            ((LifecycleOwner) view).getLifecycle().addObserver(this);
        }
        disposeBag = new CompositeDisposable();
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onDetach() {
        disposeBag.clear();
    }


    private void handleReturnedData(ItemDbEntity repo) {
        view.onGetRepoDetailFromDb(repo);

    }


    private void handleError(Throwable error) {
        view.writeToStatus(error.getLocalizedMessage());
    }


    @Override
    public void getRepoById(long repoId) {
        view.showProgress();
        Disposable disposable = interactor.loadRepoFromDbById(repoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinctUntilChanged()
                .subscribe(this::handleReturnedData, this::handleError, () -> view.hideProgress());

        disposeBag.add(disposable);

    }
}
