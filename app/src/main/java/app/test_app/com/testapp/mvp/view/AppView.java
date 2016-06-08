package app.test_app.com.testapp.mvp.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import app.test_app.com.testapp.mvp.model.App;
import io.realm.RealmResults;

/**
 * Created by elio on 6/6/16.
 */
public interface AppView extends MvpView {

    void showData(RealmResults<App> entries);
}
