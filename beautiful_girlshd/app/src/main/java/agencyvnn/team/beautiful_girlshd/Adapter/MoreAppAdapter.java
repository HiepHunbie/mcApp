package agencyvnn.team.beautiful_girlshd.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import agencyvnn.team.beautiful_girlshd.Activity.MainActivity;
import agencyvnn.team.beautiful_girlshd.Model.MoreApp;
import agencyvnn.team.beautiful_girlshd.R;

import java.util.List;

/**
 * Created by hiepnt on 23/02/2018.
 */

public class MoreAppAdapter extends RecyclerView.Adapter<MoreAppAdapter.ViewHolder> {

    private List<MoreApp> mItems;
    private Context mContext;
    private MoreAppAdapter.PostItemListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView title,txtValue;
        public com.github.siyamed.shapeimageview.CircularImageView list_image;
        public ListView lvDetail;
        MoreAppAdapter.PostItemListener mItemListener;
        private ImageView img_star_1,img_star_2,img_star_3,img_star_4,img_star_5;
        private Button btnInstall;

        public ViewHolder(final View itemView, MoreAppAdapter.PostItemListener postItemListener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            list_image = (com.github.siyamed.shapeimageview.CircularImageView) itemView.findViewById(R.id.list_image);
            img_star_1 = (ImageView) itemView.findViewById(R.id.img_star_1);
            img_star_2 = (ImageView) itemView.findViewById(R.id.img_star_2);
            img_star_3 = (ImageView) itemView.findViewById(R.id.img_star_3);
            img_star_4 = (ImageView) itemView.findViewById(R.id.img_star_4);
            img_star_5 = (ImageView) itemView.findViewById(R.id.img_star_5);
            btnInstall = (Button)itemView.findViewById(R.id.btnInstall);

            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
            btnInstall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemView.performClick();
                }
            });
        }

        @Override
        public void onClick(View view) {
            MoreApp item = getItem(getAdapterPosition());
            this.mItemListener.onPostClick(getAdapterPosition());

            notifyDataSetChanged();
        }
    }

    public MoreAppAdapter(MainActivity context, List<MoreApp> posts, MoreAppAdapter.PostItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public MoreAppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_more_app, parent, false);

        MoreAppAdapter.ViewHolder viewHolder = new MoreAppAdapter.ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoreAppAdapter.ViewHolder holder, int position) {

        MoreApp item = mItems.get(position);
        TextView txtCompany = holder.title;
        ImageView img_star_1 = holder.img_star_1;
        ImageView img_star_2 = holder.img_star_2;
        ImageView img_star_3 = holder.img_star_3;
        ImageView img_star_4 = holder.img_star_4;
        ImageView img_star_5 = holder.img_star_5;
        txtCompany.setText(item.getName());
        if(item.getRate()==5){
            img_star_1.setVisibility(View.VISIBLE);
            img_star_2.setVisibility(View.VISIBLE);
            img_star_3.setVisibility(View.VISIBLE);
            img_star_4.setVisibility(View.VISIBLE);
            img_star_5.setVisibility(View.VISIBLE);
        }else if(item.getRate()==4){
            img_star_1.setVisibility(View.VISIBLE);
            img_star_2.setVisibility(View.VISIBLE);
            img_star_3.setVisibility(View.VISIBLE);
            img_star_4.setVisibility(View.VISIBLE);
            img_star_5.setVisibility(View.GONE);
        }else if(item.getRate()==3){
            img_star_1.setVisibility(View.VISIBLE);
            img_star_2.setVisibility(View.VISIBLE);
            img_star_3.setVisibility(View.VISIBLE);
            img_star_4.setVisibility(View.GONE);
            img_star_5.setVisibility(View.GONE);
        }else if(item.getRate()==2){
            img_star_1.setVisibility(View.VISIBLE);
            img_star_2.setVisibility(View.VISIBLE);
            img_star_3.setVisibility(View.GONE);
            img_star_4.setVisibility(View.GONE);
            img_star_5.setVisibility(View.GONE);
        }else if(item.getRate()==1){
            img_star_1.setVisibility(View.VISIBLE);
            img_star_2.setVisibility(View.GONE);
            img_star_3.setVisibility(View.GONE);
            img_star_4.setVisibility(View.GONE);
            img_star_5.setVisibility(View.GONE);
        }else {
            img_star_1.setVisibility(View.GONE);
            img_star_2.setVisibility(View.GONE);
            img_star_3.setVisibility(View.GONE);
            img_star_4.setVisibility(View.GONE);
            img_star_5.setVisibility(View.GONE);
        }
        com.github.siyamed.shapeimageview.CircularImageView img_avatar = holder.list_image;
        if(item.getIcon().length()>0){
            Glide.with(mContext).load(item.getIcon())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_avatar);
        }else {
            img_avatar.setImageResource(R.drawable.ic_launcher_background);
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(List<MoreApp> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private MoreApp getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }
}