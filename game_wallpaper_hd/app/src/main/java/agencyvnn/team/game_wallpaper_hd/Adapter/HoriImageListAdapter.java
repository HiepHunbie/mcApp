package agencyvnn.team.game_wallpaper_hd.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import agencyvnn.team.game_wallpaper_hd.Activity.MainActivity;
import agencyvnn.team.game_wallpaper_hd.Contanst.AppContants;
import agencyvnn.team.game_wallpaper_hd.Dialog.DialogCall;
import agencyvnn.team.game_wallpaper_hd.Manifest;
import agencyvnn.team.game_wallpaper_hd.R;
import agencyvnn.team.game_wallpaper_hd.Utils.DownLoadImageTask;
import agencyvnn.team.game_wallpaper_hd.Utils.RoundedCornersTransformation;

/**
 * Created by hiephunbie on 4/9/18.
 */

public class HoriImageListAdapter extends RecyclerView.Adapter<HoriImageListAdapter.GroceryViewHolder>{
    private List<String> horizontalGrocderyList;
    MainActivity context;
    public static int sCorner = 10;
    public static int sMargin = 2;
    public static int sBorder = 10;
    public static String sColor = "#7D9067";
    public HoriImageListAdapter(List<String> horizontalGrocderyList, MainActivity context){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, final int position) {
        loadImage(horizontalGrocderyList.get(position),holder.imageView,holder.progressBar);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCall.showImageFullViewPager(context, horizontalGrocderyList, horizontalGrocderyList, position, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                        if (id == R.id.btnDetail) {
                            new DownLoadImageTask(context).execute(horizontalGrocderyList.get(position));
                            Toast toast = Toast.makeText(context, context.getString(R.string.is_setting_wallpaper), Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }
                });
            }
        });
    }
    private void loadImage(final String url, final ImageView imageView, final ProgressBar progressBar){
        final String urlLoad = AppContants.ProxyUrl+ url.replace("http://","").replace("https://","")+"&q=100";
        Glide.with(context).load(urlLoad)
                .thumbnail(0.5f)
                .crossFade()
                .fitCenter()
                .dontTransform()
//                .bitmapTransform(new RoundedCornersTransformation(context, sCorner, sMargin))
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
        return horizontalGrocderyList.size();
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
        public GroceryViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.imgImageHori);
            progressBar = view.findViewById(R.id.progressLoading_not_invite);
        }
    }

}
