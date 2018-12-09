package stacko.ste.mvpcleanarch;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import stacko.ste.mvpcleanarch.domain.InteractorImpl;
import stacko.ste.mvpcleanarch.interfaces.Interactor;
import stacko.ste.mvpcleanarch.interfaces.MainActivityPresenter;
import stacko.ste.mvpcleanarch.interfaces.MainActivityView;
import stacko.ste.mvpcleanarch.interfaces.PersistentStorageProxy;
import stacko.ste.mvpcleanarch.model.entities.ItemDbEntity;
import stacko.ste.mvpcleanarch.presenter.MainActivityPresenterImpl;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {
    private static final String ANY_STRING = "anystring";
    private static final Boolean ANY_BOOLEAN = true;
    private static final Long ANY_LONG = 1L;
    private static final int ANY_INT = 1;

    private TestScheduler testScheduler;

    @Mock
    InteractorImpl interactor;
    @Mock
    MainActivityView view;
    @Mock
    PersistentStorageProxy persistence;


    Interactor.DatafromServer presenterdatafromserver;

    MainActivityPresenter presenter;


    // setup vars
    ItemDbEntity item = new ItemDbEntity(ANY_LONG,ANY_INT,ANY_LONG, ANY_INT,ANY_STRING, ANY_STRING,ANY_STRING,ANY_BOOLEAN, ANY_BOOLEAN);
    List<ItemDbEntity> items = Arrays.asList(item);

    @ClassRule
    public static final ImmediateSchedulersRule schedulers =
            new ImmediateSchedulersRule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new MainActivityPresenterImpl(view, interactor,persistence);
        presenterdatafromserver = new MainActivityPresenterImpl(view, interactor,persistence);
        testScheduler = new TestScheduler();

    }



    @Test
    public void getAllReposFromDBWithNoResults() {

        List<ItemDbEntity> items = new ArrayList<ItemDbEntity>();

        // GIVEN
        Flowable<List<ItemDbEntity>> responseObservable = Flowable.just(items);

        // WHEN
        responseObservable
                .subscribeOn(testScheduler)
                .observeOn(AndroidSchedulers.mainThread());


        when(interactor.loadReposFromDb()).thenReturn(responseObservable);


        presenter.getResultFromDb();
        testScheduler.triggerActions();


        // THEN

        verify(view).showProgress();

    }


    @Test
    public void getAllReposFromDBWithResults() {




        // GIVEN
        TestObserver<List<ItemDbEntity>> testObserver = new TestObserver<>();
        Flowable<List<ItemDbEntity>> responseObservable = Flowable.just(items);

        // WHEN
        responseObservable
                .subscribeOn(testScheduler)
                .observeOn(AndroidSchedulers.mainThread());

        when(interactor.loadReposFromDb()).thenReturn(responseObservable);
        presenter.getResultFromDb();
        testScheduler.triggerActions();


        // THEN
        testObserver.assertNoErrors();
        testObserver.onComplete();
        verify(view).onGetRepoListFromDb(items);

    }



    @Test
    public void showErrorMessage() {

        // WHEN
        presenterdatafromserver.setError(ANY_STRING);

        // THEN
        verify(view).onError(ANY_STRING);

    }


    @Test
    public void fetchDataFromServer() {

        // WHEN
        presenterdatafromserver.onGetRepoFromRemote(items);

        // THEN
        verify(interactor,times(1)).updateDb(items);

    }


    @Test
    public void updateBlocketStatus() {

        // WHEN
        presenter.updateBlockedStatusTo(ANY_BOOLEAN,ANY_LONG);

        // THEN
        verify(interactor,times(1)).updateBlockedStatusUseCase(ANY_BOOLEAN,ANY_LONG);

    }

    @Test
    public void updateFollowingStatus() {

        // WHEN
        presenter.updateFollowingStatusTo(ANY_BOOLEAN,ANY_LONG);

        // THEN
        verify(interactor,times(1)).updateFollowingStatusUseCase(ANY_BOOLEAN,ANY_LONG);

    }


    @Test
    public void networkStatusChangedInNetworkActive() {

        // WHEN
        presenter.networkStatusChanged(true);

        // THEN
        verify(view).writeToStatusBackOnline();

    }

    @Test
    public void networkStatusChangedInNetworkInactive() {

        // WHEN
        presenter.networkStatusChanged(false);

        // THEN
        verify(view).writeToStatusOffline();

    }




    private static class ImmediateSchedulersRule implements TestRule {
        private Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        @Override
        public Statement apply(final Statement base, Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
                    RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
                    RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
                    RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
                    RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);

                    try {
                        base.evaluate();
                    } finally {
                        RxJavaPlugins.reset();
                        RxAndroidPlugins.reset();
                    }
                }
            };
        }
    }
}
