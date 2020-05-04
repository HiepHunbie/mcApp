package agencyvnn.team.beautiful_girlshd.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import agencyvnn.team.beautiful_girlshd.Activity.MainActivity;
import agencyvnn.team.beautiful_girlshd.Contants.AppContants;
import agencyvnn.team.beautiful_girlshd.Model.Category_Data;
import agencyvnn.team.beautiful_girlshd.Model.Item;
import agencyvnn.team.beautiful_girlshd.R;

import java.util.List;

/**
 * Created by hiepnt on 23/02/2018.
 */

public class CatelogyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Category_Data> mItems;
    private Context mContext;
    private CatelogyAdapter.PostItemListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView titleTv,artist;
        public ImageView imageView;
        ProgressBar progressLoading_not_invite;
        CatelogyAdapter.PostItemListener mItemListener;

        public ViewHolder(View itemView, CatelogyAdapter.PostItemListener postItemListener) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.title);

            artist= (TextView) itemView.findViewById(R.id.artist);
            imageView = (ImageView) itemView.findViewById(R.id.list_image);
            progressLoading_not_invite = (ProgressBar)itemView.findViewById(R.id.progressLoading_not_invite);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Category_Data item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(getAdapterPosition());

            notifyDataSetChanged();
        }
    }

    public CatelogyAdapter(MainActivity context, List<Category_Data> posts, RecyclerView recyclerView, CatelogyAdapter.PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
            LayoutInflater inflater = LayoutInflater.from(context);

            View postView = inflater.inflate(R.layout.item_catelogy, parent, false);

            viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            Category_Data item = mItems.get(position);
            TextView textView = ((ViewHolder) holder).titleTv;
            textView.setText(item.getCategory_name());
            TextView textViewartist = ((ViewHolder) holder).artist;
            textViewartist.setText(item.getTotal_image()+"");
            ImageView imageView = ((ViewHolder) holder).imageView;
            final ProgressBar progressBar = ((ViewHolder) holder).progressLoading_not_invite;
            loadImage(item.getThumb(),imageView,progressBar);
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }

    private void loadImage(final String url, final ImageView imageView, final ProgressBar progressBar){
        final String urlLoad = AppContants.ProxyUrl+ url.replace("http://","").replace("https://","")+AppContants.ProxyUrlEnd;
        Glide.with(mContext).load(urlLoad)
                .thumbnail(0.5f)
                .crossFade()
                .fitCenter()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        loadImage(urlLoad,imageView,progressBar);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                })
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(List<Category_Data> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private Category_Data getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }



}
