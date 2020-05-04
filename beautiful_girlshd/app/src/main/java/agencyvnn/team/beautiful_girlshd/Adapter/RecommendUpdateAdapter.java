package agencyvnn.team.beautiful_girlshd.Adapter;

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
import agencyvnn.team.beautiful_girlshd.Activity.MainActivity;
import agencyvnn.team.beautiful_girlshd.Contants.AppContants;
import agencyvnn.team.beautiful_girlshd.Model.Category_Detail_Data;
import agencyvnn.team.beautiful_girlshd.R;

import java.util.List;

/**
 * Created by hiepnt on 06/03/2018.
 */

public class RecommendUpdateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Category_Detail_Data> mItems;
    private Context mContext;
    private RecommendUpdateAdapter.PostItemListener mItemListener;
    private RecyclerView mRecyclerView;
    public final int AD_TYPE = 1;
    public final int DEFAULT_TYPE = 0;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        ProgressBar progressLoading_not_invite;
        RecommendUpdateAdapter.PostItemListener mItemListener;

        public ViewHolder(View itemView, RecommendUpdateAdapter.PostItemListener postItemListener) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.list_image);
            progressLoading_not_invite = (ProgressBar)itemView.findViewById(R.id.progressLoading_not_invite);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Category_Detail_Data item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(getAdapterPosition());

            notifyDataSetChanged();
        }
    }

    public RecommendUpdateAdapter(MainActivity context, List<Category_Detail_Data> posts, RecyclerView recyclerView, RecommendUpdateAdapter.PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
        mRecyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        View postView = null;
                LayoutInflater inflater = LayoutInflater.from(context);

                postView = inflater.inflate(R.layout.item_recommend_update, parent, false);

                viewHolder = new RecommendUpdateAdapter.ViewHolder(postView, this.mItemListener);
                return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecommendUpdateAdapter.ViewHolder) {
            Category_Detail_Data item = mItems.get(position);

            ImageView imageView = ((RecommendUpdateAdapter.ViewHolder) holder).imageView;
            final ProgressBar progressBar = ((RecommendUpdateAdapter.ViewHolder) holder).progressLoading_not_invite;
            loadImage(item.getOptimizeUrl(),imageView,progressBar);
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

    public void updateAnswers(List<Category_Detail_Data> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private Category_Detail_Data getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position % 6 == 0){
            return AD_TYPE;
        }else {
            return DEFAULT_TYPE;
        }
    }
}

