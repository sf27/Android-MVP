package app.test_app.com.testapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import app.test_app.com.testapp.R;
import app.test_app.com.testapp.mvp.model.App;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by eliosf27 on 11/2/15.
 */

public class AppsRecyclerAdapter extends
        RealmRecyclerViewAdapter<App, AppsRecyclerAdapter.AppsViewHolder> {

    public AppsRecyclerAdapter(Context context, OrderedRealmCollection data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }

    @Override
    public AppsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflatedView = LayoutInflater.from(context).inflate(R.layout.cell_app_list, viewGroup, false);
        return new AppsViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(AppsViewHolder holder, int position) {
        App obj = this.getData().get(position);
        holder.txtTitle.setText(obj.getTitle());
        Glide.with(context).load(obj.getImage()).into(holder.imgView);
    }


    public class AppsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        ImageView imgView;
        CardView cardView;

        public AppsViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            imgView = (ImageView) itemView.findViewById(R.id.imgView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}