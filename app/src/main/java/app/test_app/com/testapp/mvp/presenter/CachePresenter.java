package app.test_app.com.testapp.mvp.presenter;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import app.test_app.com.testapp.api.ApiClient;
import app.test_app.com.testapp.mvp.model.Entry;
import app.test_app.com.testapp.mvp.view.CacheView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// The presenter that coordinates HelloWorldView and business logic (GreetingGeneratorTask)
public class CachePresenter extends MvpBasePresenter<CacheView> {

    private Realm realm;
    private RealmResults<Entry> result;

    public CachePresenter(Context context) {
        // Create the Realm configuration
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);
    }

    public void executeUpdate() {
        Call<ApiClient.Objs> call = ApiClient.getObjects();
        call.enqueue(new Callback<ApiClient.Objs>() {
            @Override
            public void onResponse(Call<ApiClient.Objs> call, Response<ApiClient.Objs> response) {

                clearCache();

                saveData(response);

                RealmQuery<Entry> query = realm.where(Entry.class);
                result = query.findAll();
                if (isViewAttached()) {
                    getView().showCache(result);
                }

            }

            @Override
            public void onFailure(Call<ApiClient.Objs> call, Throwable t) {
                if (isViewAttached()) {
                    getView().showCache(result);
                }
            }
        });
    }

    private void clearCache() {
        RealmQuery<Entry> query = realm.where(Entry.class);
        result = query.findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result.deleteAllFromRealm();
            }
        });
    }

    private void saveData(Response<ApiClient.Objs> response) {
        for (final ApiClient.Entry entry : response.body().feed.entry) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Entry object = realm.createObject(Entry.class);
                    object.setCategory(entry.category.attributes.label);
                    object.setImage(entry.images.get(0).label);
                    object.setSummary(entry.summary.label);
                    object.setTitle(entry.title.label);
                }
            });
        }
    }
}