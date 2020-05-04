package agencyvnn.team.LeagueWallpaperHD.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import agencyvnn.team.LeagueWallpaperHD.Activity.MainActivity;
import agencyvnn.team.LeagueWallpaperHD.Adapter.DialogListImageAdapter;
import agencyvnn.team.LeagueWallpaperHD.Contanst.AppContants;
import agencyvnn.team.LeagueWallpaperHD.R;


/**
 * Created by hiepnt on 23/02/2018.
 */

public class DialogCall {

    public static void showConfirmDialog(final Activity mContext, String title, final View.OnClickListener listener){
        final AlertDialog builder = new AlertDialog.Builder(mContext).create();
        builder.show();
        builder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);

        Window window = builder.getWindow();
        window.setContentView(R.layout.dialog_confirm);

        TextView txtTitle = (TextView) window.findViewById(R.id.txtTitle);
        txtTitle.setText(title);
        Button btnYes = (Button)window.findViewById(R.id.btnOk);
        Button btnNo = (Button)window.findViewById(R.id.btnCancel);
        Typeface tfFutura = Typeface.createFromAsset(mContext.getAssets(), AppContants.FONT_TEXT);
        txtTitle.setTypeface(tfFutura);
        btnYes.setTypeface(tfFutura);
        btnNo.setTypeface(tfFutura);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(listener != null){
                    listener.onClick(arg0);
                }
                builder.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(listener != null){
                    listener.onClick(arg0);
                }
                builder.dismiss();
            }
        });

    }

    public static void showImageFullViewPager(final MainActivity mContext, final List<String> imagePath, final List<String> listIdImage, final int posItem, final View.OnClickListener listener){
        final AlertDialog builder = new AlertDialog.Builder(mContext,R.style.Theme_AppCompat_Light_NoActionBar_FullScreen).create();
        builder.show();
        builder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);

        final Window[] window = {builder.getWindow()};
        window[0].setContentView(R.layout.dialog_full_viewpager);

        ImageButton btnBack = (ImageButton) window[0].findViewById(R.id.btnBack);
        ImageButton btnDownload = (ImageButton) window[0].findViewById(R.id.btnDownload);
        ImageButton btnDetail = (ImageButton) window[0].findViewById(R.id.btnDetail);

//        ImageView imgImage = (ImageView) window.findViewById(R.id.imgImage);
        final ViewPager mViewPager = (ViewPager) window[0].findViewById(R.id.pager);
        ImageView imgPre = (ImageView) window[0].findViewById(R.id.imgPre);
        ImageView imgNext = (ImageView) window[0].findViewById(R.id.imgNext);
        DialogListImageAdapter mDialogListImageAdapter = new DialogListImageAdapter(mContext,imagePath);
        mViewPager.setAdapter(mDialogListImageAdapter);
        mViewPager.setCurrentItem(posItem);
        mViewPager.setOffscreenPageLimit(mDialogListImageAdapter.getCount() - 1);
        mContext.selectImage(listIdImage.get(posItem));

        final int[] totalScroll = {0};
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(final int position) {
                mContext.selectImage(listIdImage.get(position));
                totalScroll[0]++;
                if((totalScroll[0] %8) == 0){
                    mContext.loadAdsFull();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewPager.getCurrentItem()>0){
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1, true);
                }

            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewPager.getCurrentItem()<imagePath.size()){
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1, true);
                }

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(listener != null){
                    listener.onClick(arg0);
                }
                builder.dismiss();
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(listener != null){
                    listener.onClick(arg0);
                }
                mContext.DownloadFromUrl(imagePath.get(mViewPager.getCurrentItem()));
                Toast toast = Toast.makeText(mContext, mContext.getString(R.string.is_downloading), Toast.LENGTH_LONG);
                toast.show();
                mContext.loadAdsFull();
                //builder.dismiss();
            }
        });
        btnDetail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(listener != null){
                    listener.onClick(arg0);
                }
                mContext.loadAdsFull();

                //builder.dismiss();
            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && !event.isCanceled())
                {
                    builder.dismiss();
                    return true;
                }
                return false;
            }
        });

    }
    public static void showImageFull(final MainActivity mContext, final String imagePath,final String listIdImage, final View.OnClickListener listener){
        final AlertDialog builder = new AlertDialog.Builder(mContext).create();
        builder.show();
        builder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);

        Window window = builder.getWindow();
        window.setContentView(R.layout.dialog_show_full_image);

        ImageButton btnBack = (ImageButton) window.findViewById(R.id.btnBack);
        ImageButton btnDownload = (ImageButton) window.findViewById(R.id.btnDownload);
        ImageButton btnDetail = (ImageButton) window.findViewById(R.id.btnDetail);
        ImageView imgImage = (ImageView) window.findViewById(R.id.imgImage);
        ProgressBar progressLoading_not_invite = (ProgressBar)window.findViewById(R.id.progressLoading_not_invite);
        loadImage(mContext,imagePath,imgImage,progressLoading_not_invite);

        mContext.selectImage(listIdImage);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(listener != null){
                    listener.onClick(arg0);
                }
                builder.dismiss();
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(listener != null){
                    listener.onClick(arg0);
                }
                mContext.DownloadFromUrl(imagePath);
                Toast toast = Toast.makeText(mContext, mContext.getString(R.string.is_downloading), Toast.LENGTH_LONG);
                toast.show();
                mContext.loadAdsFull();
                //builder.dismiss();
            }
        });
        btnDetail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(listener != null){
                    listener.onClick(arg0);
                }
                mContext.loadAdsFull();
                //builder.dismiss();
            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && !event.isCanceled())
                {
                        builder.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    public static void showDialogRating(final MainActivity mContext,final View.OnClickListener listener){
        final AlertDialog builder = new AlertDialog.Builder(mContext).create();
        builder.show();
        builder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);

        Window window = builder.getWindow();
        window.setContentView(R.layout.dialog_rate_app);

        RatingBar RatingBar = (RatingBar) window.findViewById(R.id.ratingBar);
        TextView btnCancel = (TextView) window.findViewById(R.id.btnCancel);
        TextView btnEvaluete = (TextView) window.findViewById(R.id.btnEvaluete);
        TextView txtTitle = (TextView) window.findViewById(R.id.txtTitle);
        TextView txtValue = (TextView) window.findViewById(R.id.txtValue);
        Typeface tfFutura = Typeface.createFromAsset(mContext.getAssets(), AppContants.FONT_TEXT);
        btnCancel.setTypeface(tfFutura);
        btnEvaluete.setTypeface(tfFutura);
        txtTitle.setTypeface(tfFutura);
        txtValue.setTypeface(tfFutura);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(listener != null){
                    listener.onClick(arg0);
                }
                builder.dismiss();
            }
        });

        btnEvaluete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(listener != null){
                    listener.onClick(arg0);
                }
                builder.dismiss();
            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && !event.isCanceled())
                {
                    builder.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    private static void loadImage(final Context mContext, final String url, final ImageView imageView, final ProgressBar progressBar){
//        final String urlLoad = AppContants.ProxyUrl+ url.replace("http://","").replace("https://","");
        Glide.with(mContext).load(url)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        loadImage(mContext,url,imageView,progressBar);
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
}
