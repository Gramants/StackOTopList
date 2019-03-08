package stacko.ste.mvpcleanarch.presentation.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import stacko.ste.mvpcleanarch.R;
import stacko.ste.mvpcleanarch.presentation.interfaces.DetailActivityView;
import stacko.ste.mvpcleanarch.domain.model.entities.ItemDbEntity;
import stacko.ste.mvpcleanarch.presentation.DetailActivityPresenterImpl;
import stacko.ste.mvpcleanarch.base.Utils;


public class DetailActivity extends AppCompatActivity implements DetailActivityView {

    @Inject
    DetailActivityPresenterImpl presenter;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_id)
    TextView userId;
    @BindView(R.id.user_reputation)
    TextView userReputation;
    @BindView(R.id.user_location)
    TextView userLocation;
    @BindView(R.id.user_creationdate)
    TextView userCreationdate;
    @BindView(R.id.image)
    ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        presenter.getRepoById(Utils.getRepoId(getIntent()));

    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String errMsg) {
        updateStatus(errMsg);

    }

    private void updateStatus(String msg) {
        userReputation.setText("");
        userLocation.setText("");
        userId.setText("");
        userName.setText("");
        userCreationdate.setText("");
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onGetRepoDetailFromDb(ItemDbEntity userItem) {
        DateFormat format = new SimpleDateFormat("MMddyyHHmmss");
        Date date = null;
        try {
            date = format.parse(String.valueOf(userItem.getCreationDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        userReputation.setText(getResources().getString(R.string.reputation, String.valueOf(userItem.getReputation())));
        userLocation.setText(getResources().getString(R.string.location, userItem.getLocation()));
        userId.setText(getResources().getString(R.string.user_id, String.valueOf(userItem.getId())));
        userName.setText(userItem.getDisplayName());
        userCreationdate.setText(getResources().getString(R.string.creation_date, convertTimeWithTimeZome(userItem.getCreationDate())));
        Glide.with(this)
                .load(userItem.getProfileImage())
                .into(userImage);
    }

    @Override
    public void writeToStatus(String msg) {
        updateStatus(msg);
    }

    public String convertTimeWithTimeZome(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(time * 1000);
        return (cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/"
                + cal.get(Calendar.YEAR));

    }
}
