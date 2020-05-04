package agencyvnn.team.game_wallpaper_hd.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import agencyvnn.team.game_wallpaper_hd.Activity.MainActivity;
import agencyvnn.team.game_wallpaper_hd.Contanst.AppContants;
import agencyvnn.team.game_wallpaper_hd.R;

/**
 * Created by hiephunbie on 3/16/18.
 */

public class DialogListImageAdapter extends PagerAdapter {

    MainActivity mContext;
    LayoutInflater mLayoutInflater;
    private List<String> listImage = new ArrayList<String>();
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    public DialogListImageAdapter(MainActivity context, List<String> listImage) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listImage = listImage;
    }

    @Override
    public int getCount() {
        return listImage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_dialog_image, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imgImage);
        ProgressBar progressBar = (ProgressBar)itemView.findViewById(R.id.progressLoading_not_invite);
        final AdView mAdView = (AdView)itemView.findViewById(R.id.adView);
        loadImage(listImage.get(position),imageView,progressBar);


        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
//                hideAds();
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClosed() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adRequest);
        container.addView(itemView);

        return itemView;
    }

    private void loadImage(final String url, final ImageView imageView, final ProgressBar progressBar){
        final String urlLoad = AppContants.ProxyUrl+ url.replace("http://","").replace("https://","")+"&q=100";
        Glide.with(mContext).load(urlLoad)
                .thumbnail(0.5f)
                .crossFade()
                .fitCenter()
//                .dontTransform()
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
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        imageView.setOnTouchListener(new ImageMatrixTouchHandler(mContext));
                        return false;
                    }

                })
                .into(imageView);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
    // determine which layout to use for the row


}