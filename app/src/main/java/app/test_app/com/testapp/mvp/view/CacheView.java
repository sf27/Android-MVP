package app.test_app.com.testapp.mvp.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

import app.test_app.com.testapp.mvp.model.Entry;

/**
 * Created by elio on 6/6/16.
 */
public interface CacheView extends MvpView {

    void showCache(List<Entry> entries);
}
