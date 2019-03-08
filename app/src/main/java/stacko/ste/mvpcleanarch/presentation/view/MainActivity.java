package stacko.ste.mvpcleanarch.presentation.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import stacko.ste.mvpcleanarch.R;
import stacko.ste.mvpcleanarch.presentation.interfaces.MainActivityView;
import stacko.ste.mvpcleanarch.domain.model.ConnectionModel;
import stacko.ste.mvpcleanarch.domain.model.entities.ItemDbEntity;
import stacko.ste.mvpcleanarch.presentation.MainActivityPresenterImpl;
import stacko.ste.mvpcleanarch.base.Utils;
import stacko.ste.mvpcleanarch.domain.checknetwork.ConnectionLiveData;
import stacko.ste.mvpcleanarch.presentation.view.adapters.RepoListAdapter;

import android.arch.lifecycle.Observer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MainActivityView, RepoListAdapter.OnItemClickListener {

    @Inject
    MainActivityPresenterImpl presenter;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.repo_list)
    RecyclerView rvRepoList;
    @BindView(R.id.text_status)
    TextView statusText;
    @BindView(R.id.fetch)
    TextView forceFetch;

    private RepoListAdapter repoAdapter;
    private ConnectionLiveData connectionLiveData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        connectionLiveData = new ConnectionLiveData(getApplicationContext());
        repoAdapter = new RepoListAdapter(this);
        rvRepoList.setAdapter(repoAdapter);
        rvRepoList.setLayoutManager(new LinearLayoutManager(this));
        subscribeUiUpdates();


        forceFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doFetchAllRepos();
            }
        });
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String errorMessage) {
        hideProgress();
        statusMessage(errorMessage);
    }


    private void statusMessage(String msg) {
        statusText.setText(msg);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void writeToStatus(String status) {
        statusMessage(status);
    }


    @Override
    public void onGetRepoListFromDb(List<ItemDbEntity> list) {
        statusMessage(getString(R.string.reposfound, String.valueOf(list.size())));
        Collections.sort(list, new Utils.CustomComparator());
        forceFetch.setVisibility(View.VISIBLE);
        repoAdapter.updateRepoList(list);
    }


    @Override
    public void writeToStatus(int stringId) {
        statusMessage(getString(stringId));
    }


    @Override
    public void onItemClick(ItemDbEntity item) {
        Utils.openDetail(this, item.getId());
    }

    @Override
    public void setBlockedStatusAs(boolean isOn, ItemDbEntity item) {
        presenter.updateBlockedStatusTo(isOn, item.getId());
    }

    @Override
    public void setFollowStatusAs(boolean isOn, ItemDbEntity item) {
        presenter.updateFollowingStatusTo(isOn, item.getId());
    }

    @Override
    public void showToastIsBlocked() {
        Toast.makeText(getApplicationContext(), getString(R.string.item_blocked), Toast.LENGTH_SHORT).show();
    }

    private void subscribeUiUpdates() {
        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(@Nullable ConnectionModel connection) {
                presenter.networkStatusChanged(connection.mActive);

            }
        });

    }
}