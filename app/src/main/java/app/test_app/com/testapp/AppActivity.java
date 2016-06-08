package app.test_app.com.testapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.List;

import app.test_app.com.testapp.adapter.AppsRecyclerAdapter;
import app.test_app.com.testapp.mvp.model.App;
import app.test_app.com.testapp.mvp.presenter.AppPresenter;
import app.test_app.com.testapp.mvp.view.AppView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class AppActivity extends MvpActivity<AppView, AppPresenter> implements AppView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.apps_recyclerview)
    RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(AppActivity.this);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setHasFixedSize(true);

        presenter.executeUpdate();
    }

    @NonNull
    @Override
    public AppPresenter createPresenter() {
        return new AppPresenter(AppActivity.this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showData(RealmResults<App> apps) {
        AppsRecyclerAdapter adapter = new AppsRecyclerAdapter(AppActivity.this, apps, true);
        recyclerview.setAdapter(adapter);
    }
}
