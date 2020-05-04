package agencyvnn.team.LeagueWallpaperHD.Adapter;

import android.content.Context;
import android.graphics.Typeface;
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

import java.util.List;

import agencyvnn.team.LeagueWallpaperHD.Activity.MainActivity;
import agencyvnn.team.LeagueWallpaperHD.Contanst.AppContants;
import agencyvnn.team.LeagueWallpaperHD.Models.GetCategory.Datum;
import agencyvnn.team.LeagueWallpaperHD.R;

/**
 * Created by hiephunbie on 4/13/18.
 */

public class AllChampionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Datum> mItems;
    private MainActivity mContext;
    private AllChampionsAdapter.PostItemListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RecyclerView mRecyclerView;
        private TextView txtTitle, txtNumber;
        ProgressBar progressLoading_not_invite;
        AllChampionsAdapter.PostItemListener mItemListener;
        private ImageView list_image;

        public ViewHolder(View itemView, AllChampionsAdapter.PostItemListener postItemListener) {
            super(itemView);

            progressLoading_not_invite = (ProgressBar) itemView.findViewById(R.id.progressLoading_not_invite);
            txtTitle = (TextView)itemView.findViewById(R.id.title);
            txtNumber = (TextView)itemView.findViewById(R.id.artist);
            list_image = (ImageView)itemView.findViewById(R.id.list_image);
            Typeface tfFutura = Typeface.createFromAsset(mContext.getAssets(), AppContants.FONT_TEXT);
            txtTitle.setTypeface(tfFutura);
            txtNumber.setTypeface(tfFutura);
            // add a divider after each item for more clarity
//            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, R.dimen._1sdp);
//            mRecyclerView.addItemDecoration(itemDecoration);
            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Datum item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(getAdapterPosition());

            notifyDataSetChanged();
        }
    }

    public AllChampionsAdapter(MainActivity context, List<Datum> posts,AllChampionsAdapter.PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_all_champions, parent, false);

        viewHolder = new AllChampionsAdapter.ViewHolder(postView, this.mItemListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Datum item = mItems.get(position);
        RecyclerView mRecyclerView = ((ViewHolder) holder).mRecyclerView;

        TextView txtTitle = ((ViewHolder)holder).txtTitle;
        TextView txtNumber = ((ViewHolder)holder).txtNumber;
        ImageView list_image = ((ViewHolder)holder).list_image;
        txtTitle.setText(item.getCategoryName().toString());
        txtNumber.setText(item.getTotalImages()+"");
        final ProgressBar progressBar = ((ViewHolder) holder).progressLoading_not_invite;
        if(item.getThumb() != null){
            loadImage(item.getThumb().toString(),list_image,progressBar);
        }else {
            loadImage(item.getImageInCategory().get(0).getUrl().toString(),list_image,progressBar);
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

    public void updateAnswers(List<Datum> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private Datum getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }
}