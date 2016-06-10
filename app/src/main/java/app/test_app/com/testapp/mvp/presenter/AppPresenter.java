package app.test_app.com.testapp.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import app.test_app.com.testapp.api.ApiClient;
import app.test_app.com.testapp.mvp.model.App;
import app.test_app.com.testapp.mvp.view.AppView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// The presenter that coordinates HelloWorldView and business logic (GreetingGeneratorTask)
public class AppPresenter extends MvpBasePresenter<AppView> {

    private Realm realm;
    private RealmResults<App> result;
    private Context context;

    public AppPresenter(Context context) {
        this.context = context;
        // Create the Realm configuration
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context)
                                                 .deleteRealmIfMigrationNeeded().build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);
    }

    public void executeUpdate() {
        if (isConnectedToInternet()) {
            Call<ApiClient.Objs> call = ApiClient.getObjects();
            call.enqueue(new Callback<ApiClient.Objs>() {
                @Override
                public void onResponse(Call<ApiClient.Objs> call, Response<ApiClient.Objs> response) {

                    clearCache();

                    saveData(response);

                    RealmQuery<App> query = realm.where(App.class);
                    result = query.findAll();
                    if (isViewAttached()) getView().showData(result);

                }

                @Override
                public void onFailure(Call<ApiClient.Objs> call, Throwable t) {
                    if (isViewAttached()) getView().showData(result);
                }
            });

        } else {
            // if the device doesn't have an internet connection, load the data from cache
            RealmQuery<App> query = realm.where(App.class);
            result = query.findAll();
            if (isViewAttached()) {
                getView().showData(result);
            }
        }

    }

    private void clearCache() {
        RealmQuery<App> query = realm.where(App.class);
        result = query.findAll();
        realm.executeTransaction(realm1 -> result.deleteAllFromRealm());
    }

    private void saveData(Response<ApiClient.Objs> response) {
        for (final ApiClient.Entry entry : response.body().feed.entry) {
            realm.executeTransaction(realm1 -> {
                App object = realm1.createObject(App.class);
                object.setCategory(entry.category.attributes.label);
                object.setImage(entry.images.get(0).label);
                object.setSummary(entry.summary.label);
                object.setTitle(entry.title.label);
            });
        }
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}