package agencyvnn.team.game_wallpaper_hd.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import agencyvnn.team.game_wallpaper_hd.Activity.MainActivity;
import agencyvnn.team.game_wallpaper_hd.Contanst.AppContants;
import agencyvnn.team.game_wallpaper_hd.R;

/**
 * Created by hiephunbie on 4/9/18.
 */

public class HorizanImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mItems;
    private Context mContext;
    private HorizanImageListAdapter.PostItemListener mItemListener;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        ProgressBar progressLoading_not_invite;
        HorizanImageListAdapter.PostItemListener mItemListener;

        public ViewHolder(View itemView, HorizanImageListAdapter.PostItemListener postItemListener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgImage);
            progressLoading_not_invite = (ProgressBar)itemView.findViewById(R.id.progressLoading_not_invite);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(getAdapterPosition());

            notifyDataSetChanged();
        }
    }

    public HorizanImageListAdapter(MainActivity context, List<String> posts, HorizanImageListAdapter.PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_image, parent, false);

        viewHolder = new HorizanImageListAdapter.ViewHolder(postView, this.mItemListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageDetailAdapter.ViewHolder) {
            String item = mItems.get(position);

            ImageView imageView = ((ImageDetailAdapter.ViewHolder) holder).imageView;
            final ProgressBar progressBar = ((ImageDetailAdapter.ViewHolder) holder).progressLoading_not_invite;
            loadImage(item,imageView,progressBar);
        }

    }

    private void loadImage(final String url, final ImageView imageView, final ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
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

    public void updateAnswers(List<String> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private String getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }
}
