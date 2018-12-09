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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;
import stacko.ste.mvpcleanarch.domain.InteractorImpl;
import stacko.ste.mvpcleanarch.interfaces.DetailActivityPresenter;
import stacko.ste.mvpcleanarch.interfaces.DetailActivityView;
import stacko.ste.mvpcleanarch.model.entities.ItemDbEntity;
import stacko.ste.mvpcleanarch.presenter.DetailActivityPresenterImpl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class DetailActivityPresenterTest {
    private static final String ANY_STRING = "anystring";
    private static final Boolean ANY_BOOLEAN = true;
    private static final Long ANY_LONG = 1L;
    private static final int ANY_INT = 1;

    private TestScheduler testScheduler;

    @Mock
    InteractorImpl interactor;
    @Mock
    DetailActivityView view;

    DetailActivityPresenter presenter;

    ItemDbEntity item = new ItemDbEntity(ANY_LONG,ANY_INT,ANY_LONG, ANY_INT,ANY_STRING, ANY_STRING,ANY_STRING,ANY_BOOLEAN, ANY_BOOLEAN);

    @ClassRule
    public static final ImmediateSchedulersRule schedulers =
            new ImmediateSchedulersRule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new DetailActivityPresenterImpl(view, interactor);
        testScheduler = new TestScheduler();

    }



    @Test
    public void getRepoFromDbByIdSuccess() {

        // GIVEN
        Flowable<ItemDbEntity> responseObservable = Flowable.just(item);

        // WHEN
        responseObservable
                .subscribeOn(testScheduler)
                .observeOn(AndroidSchedulers.mainThread());


        when(interactor.loadRepoFromDbById(ANY_INT)).thenReturn(responseObservable);


        presenter.getRepoById(ANY_INT);
        testScheduler.triggerActions();


        // THEN
        verify(view).hideProgress();
        verify(view).onGetRepoDetailFromDb(item);
        verify(view).hideProgress();
    }


    @Test
    public void getRepoFromDbByIdError(){

        // GIVEN
        Flowable<ItemDbEntity> responseObservable = Flowable.error(new RuntimeException("error in Observable"));

        // WHEN
        responseObservable
                .subscribeOn(testScheduler)
                .observeOn(AndroidSchedulers.mainThread());


        when(interactor.loadRepoFromDbById(ANY_INT)).thenReturn(responseObservable);


        presenter.getRepoById(ANY_INT);
        testScheduler.triggerActions();


        // THEN
        verify(view).writeToStatus("error in Observable");

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
