package agencyvnn.team.game_wallpaper_hd.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

import java.util.ArrayList;
import java.util.List;

import agencyvnn.team.game_wallpaper_hd.Activity.MainActivity;
import agencyvnn.team.game_wallpaper_hd.Contanst.AppContants;
import agencyvnn.team.game_wallpaper_hd.Dialog.DialogCall;
import agencyvnn.team.game_wallpaper_hd.Models.Category_Data;
import agencyvnn.team.game_wallpaper_hd.Models.GetCategory.Datum;
import agencyvnn.team.game_wallpaper_hd.R;
import agencyvnn.team.game_wallpaper_hd.Utils.ItemOffsetDecoration;

/**
 * Created by hiephunbie on 4/9/18.
 */

public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private HoriImageListAdapter groceryAdapter;
    private List<Datum> mItems;
    private MainActivity mContext;
    private HomePageAdapter.PostItemListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RecyclerView mRecyclerView;
        private TextView txtTitle, txtShowAll;
        ProgressBar progressLoading_not_invite;
        HomePageAdapter.PostItemListener mItemListener;

        public ViewHolder(View itemView, HomePageAdapter.PostItemListener postItemListener) {
            super(itemView);

            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.idRecyclerViewHorizontalList);
            txtTitle = (TextView)itemView.findViewById(R.id.txtTitle);
            txtShowAll = (TextView)itemView.findViewById(R.id.txtShowAll);
            // add a divider after each item for more clarity
//            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, R.dimen._1sdp);
//            mRecyclerView.addItemDecoration(itemDecoration);
            this.mItemListener = postItemListener;
            txtShowAll.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Datum item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(getAdapterPosition());

            notifyDataSetChanged();
        }
    }

    public HomePageAdapter(MainActivity context, List<Datum> posts,HomePageAdapter.PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_home_page, parent, false);

        viewHolder = new HomePageAdapter.ViewHolder(postView, this.mItemListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Datum item = mItems.get(position);
            final ArrayList<String> arrayList = new ArrayList<String >();
            final ArrayList<String> arrayListId = new ArrayList<String>();

            RecyclerView mRecyclerView = ((ViewHolder) holder).mRecyclerView;

//            mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.HORIZONTAL));
            groceryAdapter = new HoriImageListAdapter( arrayList,mContext);
//            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mContext, R.dimen._1sdp);
//            mRecyclerView.addItemDecoration(itemDecoration);
//            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
//            mRecyclerView.setLayoutManager(horizontalLayoutManager);
            final GridLayoutManager manager = new GridLayoutManager(mContext, 1, GridLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(groceryAdapter);
            for(int i = 0;i<item.getImageInCategory().size();i++){
                arrayList.add(item.getImageInCategory().get(i).getUrl());
                arrayListId.add(item.getImageInCategory().get(i).getCategoryId());
            }
            TextView txtTitle = ((ViewHolder)holder).txtTitle;
            TextView txtShowAll = ((ViewHolder)holder).txtShowAll;
            txtTitle.setText(item.getCategoryName().toString());
//        txtTitle.setText(item.getCategoryName().toString() + " ("+item.getTotalImages()+")");
        groceryAdapter.notifyDataSetChanged();
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
