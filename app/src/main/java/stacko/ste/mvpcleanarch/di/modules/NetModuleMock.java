package stacko.ste.mvpcleanarch.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import stacko.ste.mvpcleanarch.interfaces.Api;
import stacko.ste.mvpcleanarch.espressotest.util.Config;


@Module
public class NetModuleMock {




    @Provides
    @Singleton
    HttpLoggingInterceptor providehttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(httpLoggingInterceptor);
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Config.MOCK_ENDPOINT)
                .client(httpClient)
                .build();
    }


    @Provides
    @Singleton
    Api provideGitHubService(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }




}