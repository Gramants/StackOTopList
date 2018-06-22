package stacko.ste.mvpcleanarch.data.database;


import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

public abstract class UpdateRecords {

    @MainThread
    public UpdateRecords() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                updateRecords();
                return null;
            }

        }.execute();
    }


    @WorkerThread
    protected abstract void updateRecords();
}
