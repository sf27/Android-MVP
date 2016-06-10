package app.test_app.com.testapp.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ApiClient {
    public static final String API_URL = "https://itunes.apple.com";

    public static Call<Objs> getObjects() {
        Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(API_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

        Itunes itunes = retrofit.create(Itunes.class);
        return itunes.apps(20);
    }

    public interface Itunes {
        @GET("/us/rss/topfreeapplications/limit={limit}/json")
        Call<Objs> apps(@Path("limit") int limit);
    }

    public static class Summary {
        public final String label;

        public Summary(String label) {
            this.label = label;
        }
    }

    public static class Category {
        public final Attributes attributes;

        public Category(Attributes attributes) {
            this.attributes = attributes;
        }

        public static class Attributes {
            public final String label;

            public Attributes(String label) {
                this.label = label;
            }
        }
    }

    public static class Title {
        public final String label;

        public Title(String label) {
            this.label = label;
        }
    }

    public static class Image {
        public final String label;

        public Image(String label) {
            this.label = label;
        }
    }

    public static class Entry {
        public Title title;
        public Category category;
        public Summary summary;
        @SerializedName("im:image")
        public List<Image> images;

        public Entry(Title title, Category category, Summary summary, List<Image> images) {
            this.title = title;
            this.category = category;
            this.summary = summary;
            this.images = images;
        }
    }

    public static class Feed {
        public final List<Entry> entry;

        public Feed(List<Entry> entry) {
            this.entry = entry;
        }
    }

    public static class Objs {
        public final Feed feed;

        public Objs(Feed feed) {
            this.feed = feed;
        }
    }
}
