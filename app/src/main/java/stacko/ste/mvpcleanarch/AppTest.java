package stacko.ste.mvpcleanarch;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import stacko.ste.mvpcleanarch.di.components.DaggerAppComponent;
import stacko.ste.mvpcleanarch.di.components.DaggerAppComponentTest;

import static java.security.AccessController.getContext;


public class AppTest extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponentTest
                .builder()
                .application(this)
                .build()
                .inject(this);


        initMockWebServer();


    }

    private void initMockWebServer() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                MockWebServer webServer = null;
                webServer = new MockWebServer();
                try {
                    webServer.start(1234);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                webServer.setDispatcher(getDispatcher());
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    private Dispatcher getDispatcher() {
        final Dispatcher dispatcher = new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                if (request.getPath().contains("/2.2/users")) {
                    return new MockResponse().setResponseCode(200)
                            .setBody(getLocalJson("mockedresults_success.json"));
                }
                throw new IllegalStateException("no mock set up for " + request.getPath());
            }
        };
        return dispatcher;
    }


    public String getLocalJson(String fileName) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = getAssets()
                    .open("staticjsons/"+fileName);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            Log.e("STE2", e.getLocalizedMessage());

        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
                Log.e("STE3", e2.getLocalizedMessage());
            }
        }
        return returnString.toString();
    }
}
