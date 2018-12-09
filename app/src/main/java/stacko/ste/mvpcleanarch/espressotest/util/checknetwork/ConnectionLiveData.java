package stacko.ste.mvpcleanarch.espressotest.util.checknetwork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import stacko.ste.mvpcleanarch.model.ConnectionModel;
import stacko.ste.mvpcleanarch.espressotest.util.Utils;



public class ConnectionLiveData extends SingleLiveEvent<ConnectionModel> {

    private Context context;
    private Boolean initialized = false;
    private ConnectionModel lastObj = null;

    public ConnectionLiveData(Context context) {
        this.context = context;
    }

    @Override
    protected void onActive() {
        super.onActive();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(networkReceiver, filter);
    }

    @Override
    protected void onInactive() {
        super.onInactive();

        context.unregisterReceiver(networkReceiver);

    }

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @SuppressWarnings("deprecation")
        @Override
        public void onReceive(Context context, Intent intent) {


            if (intent.getExtras() != null) {

                final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (isConnected) {
                    switch (activeNetwork.getType()) {
                        case ConnectivityManager.TYPE_WIFI:
                            postValue(new ConnectionModel(Utils.WIFI, true));
                            break;
                        case ConnectivityManager.TYPE_MOBILE:
                            postValue(new ConnectionModel(Utils.NETWORK, true));
                            break;
                    }
                    initialized = false;
                } else {

                    if (!initialized) {
                        initialized = true;
                        lastObj = new ConnectionModel(Utils.NO_CONNECTION, false);
                        postValue(lastObj);
                    }

                }
            }
        }
    };
}