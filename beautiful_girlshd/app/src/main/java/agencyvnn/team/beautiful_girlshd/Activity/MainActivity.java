package agencyvnn.team.beautiful_girlshd.Activity;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import agencyvnn.team.beautiful_girlshd.Api.Service.SOService;
import agencyvnn.team.beautiful_girlshd.Api.Utils.ApiUtils;
import agencyvnn.team.beautiful_girlshd.Base.BaseFragment;
import agencyvnn.team.beautiful_girlshd.Base.MyApplication;
import agencyvnn.team.beautiful_girlshd.Base.PermissionActivity;
import agencyvnn.team.beautiful_girlshd.Contants.AppContants;
import agencyvnn.team.beautiful_girlshd.Fragment.CatelogyDetailFragment;
import agencyvnn.team.beautiful_girlshd.Fragment.CatelogyFragment;
import agencyvnn.team.beautiful_girlshd.Fragment.MoreAppFragment;
import agencyvnn.team.beautiful_girlshd.Fragment.RecommendBySortFragment;
import agencyvnn.team.beautiful_girlshd.Fragment.SearchFragment;
import agencyvnn.team.beautiful_girlshd.Model.ImageDetail;
import agencyvnn.team.beautiful_girlshd.Model.Tags;
import agencyvnn.team.beautiful_girlshd.Utils.Pref;
import agencyvnn.team.beautiful_girlshd.Utils.Utility;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.onesignal.OneSignal;

import java.util.ArrayList;

import agencyvnn.team.beautiful_girlshd.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends PermissionActivity {

    private BaseFragment fragment;
    public TextView mTitleToolBar;
    public Toolbar toolbar_main;
    public Pref p;
    private ImageButton btnRecommend,btnCatelogy,btnMoreApp,btnSearch;
    public static final String KEY_TAB = "Key_Tab_Select";
    public String imagePath = "";
    private RelativeLayout container_toolbar;
    public Button btnSetting,btnOrder,btnFillter;
    public ImageView btnBack;
    private EditText edtSearch;
    public TextView edt_edit;
    private RelativeLayout layout_control;
    private FrameLayout content_frame;
    public int tabBonus = 0;
    private LinearLayout layout_view;
    public static String titleCatelogy = "";
    private DownloadManager downloadManager;
    private long refid;
    private Uri Download_Uri;
    ArrayList<Long> list = new ArrayList<>();
    private SOService mService;
    public static ArrayList<String> mDataSearch = new ArrayList<String>();
    private com.google.android.gms.ads.AdView mAdView;
    private  AdRequest adRequest;
    InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    private static TextView txtError;
    public static int seletedFragment = 0;
    private LinearLayout layout_view_fragment;
    public int tabSaleProduct = 0;
    public boolean isLoadingAdsFull = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                String text = "OneSignal UserID:\n" + userId + "\n\n";
                if (registrationId != null)
                    text += "Google Registration Id:\n" + registrationId;
                else
                    text += "Google Registration Id:\nCould not subscribe for push";
//                TextView textView = (TextView)findViewById(R.id.debug_view);
//                textView.setText(text);
            }
        });
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        mInterstitialAd = new InterstitialAd(this);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.background_color_all));
        }
        mService = ApiUtils.getSOService();
        loadListSuggestion();

        String yourDataTab = "";
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        btnRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(0);
            }
        });
        btnCatelogy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(1);
            }
        });
        btnMoreApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(2);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(4);
            }
        });
        Utility.checkPermission(MainActivity.this);

        showAds();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            changeTextStatus(true);
        } else {
            changeTextStatus(false);
        }
        setHideToolbar();

        String notification = getIntent().getStringExtra(AppContants.NOTIFICATION);
        if(notification != null){
            if(notification.length()>0){
                if(notification.equals("0")){
                    tabSaleProduct = 1;
                    selectItem(0);
                }else if(notification.equals("1")){
                    tabSaleProduct = 0;
                    selectItem(0);
                }else{
                    selectItem(1);
                }
            }else {
                if (getIntent().hasExtra(KEY_TAB)) {
                    yourDataTab = getIntent().getStringExtra(KEY_TAB);
                    selectItem(Integer.parseInt(yourDataTab));
                } else {
                    selectItem(0);
                }
            }
        }else {
            if (getIntent().hasExtra(KEY_TAB)) {
                yourDataTab = getIntent().getStringExtra(KEY_TAB);
                selectItem(Integer.parseInt(yourDataTab));
            } else {
                selectItem(0);
            }
        }

    }
    public void changeTextStatus(boolean isConnected) {
        // Change status according to boolean value
        if (isConnected) {
            txtError.setVisibility(View.GONE);
        } else {
            txtError.setVisibility(View.VISIBLE);
        }
    }

    public void loadAdsBanner(){
        adRequest = new AdRequest.Builder()
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
                showAds();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                showAds();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        mAdView.loadAd(adRequest);
    }
//    public void loadAdsVideo(){
//
//        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
//
//            @Override
//            public void onRewarded(RewardItem rewardItem) {
//                Toast.makeText(getApplicationContext(), "onRewarded! currency: " + rewardItem.getType() + "  amount: " +
//                        rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoAdLeftApplication() {
//                Toast.makeText(getApplicationContext(), "onRewardedVideoAdLeftApplication",
//                        Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoAdClosed() {
//                Toast.makeText(getApplicationContext(), "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoAdFailedToLoad(int errorCode) {
//                Toast.makeText(getApplicationContext(), "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
//                loadAdsVideo();
//            }
//
//            @Override
//            public void onRewardedVideoAdLoaded() {
//                Toast.makeText(getApplicationContext(), "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoAdOpened() {
//                Toast.makeText(getApplicationContext(), "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRewardedVideoStarted() {
//                Toast.makeText(getApplicationContext(), "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        loadRewardedVideoAd();
//    }
    public void loadAdsFull(){
        isLoadingAdsFull = true;
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();

            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                loadAdsFull();
            }
        });
    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            isLoadingAdsFull = false;
        }
    }

    private void showAds(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                mAdView.loadAd(adRequest);
//                if (mAdView != null) {
//                    mAdView.resume();
//                }
                mAdView.setVisibility(View.VISIBLE);
//                hideAds();
            }
        }, AppContants.lenght_show_ads);
    }

    private void hideAds(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (mAdView != null) {
//                    mAdView.destroy();
//                }
                mAdView.setVisibility(View.GONE);
                showAds();
            }
        }, AppContants.lenght_hide_ads);
    }


    public void DownloadFromUrl(String url){
        list.clear();
        Download_Uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle(getString(R.string.app_name) + " "+getString(R.string.Downloading) +" "+ getString(R.string.sample) + ".png");
        request.setDescription(getString(R.string.Downloading) + " "+getString(R.string.sample) + ".png");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/"+ getString(R.string.app_name)+ "/" + getString(R.string.sample) + ".png");

        refid = downloadManager.enqueue(request);

        Log.e("OUT", "" + url);

        list.add(refid);


    }

//    public class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
//
//        protected Bitmap doInBackground(String...urls){
//            String urlOfImage = urls[0];
//            Bitmap logo = null;
//            try{
//                InputStream is = new URL(urlOfImage).openStream();
//                /*
//                    decodeStream(InputStream is)
//                        Decode an input stream into a bitmap.
//                 */
//                logo = BitmapFactory.decodeStream(is);
//            }catch(Exception e){ // Catch the download exception
//                e.printStackTrace();
//            }
//            return logo;
//        }
//
//        /*
//            onPostExecute(Result result)
//                Runs on the UI thread after doInBackground(Params...).
//         */
//        protected void onPostExecute(Bitmap result){
//
//            WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this);
//            try {
//                wallpaperManager.setBitmap(result);
//                Toast toast = Toast.makeText(MainActivity.this, "Set wallpaper successfully!", Toast.LENGTH_LONG);
//                toast.show();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
    private void initUi(){
        p = new Pref(MainActivity.this);
        toolbar_main = (Toolbar) findViewById(R.id.toolbar_main);
        mTitleToolBar = (TextView) toolbar_main.findViewById(R.id.toolbar_title);
        toolbar_main.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar_main.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.background_color_all)));
        container_toolbar = (RelativeLayout)findViewById(R.id.container_toolbar);
        layout_control = (RelativeLayout)findViewById(R.id.layout_control);
        layout_view_fragment = (LinearLayout) findViewById(R.id.layout_view_fragment);
        layout_view = (LinearLayout)findViewById(R.id.layout_view);
        content_frame = (FrameLayout)findViewById(R.id.content_frame);
        btnBack = (ImageView)findViewById(R.id.btnBack);
        mAdView = (com.google.android.gms.ads.AdView) findViewById(R.id.adView);

        btnRecommend = (ImageButton)findViewById(R.id.btnRecommend);
        btnCatelogy = (ImageButton)findViewById(R.id.btnCatelogy);
        btnMoreApp = (ImageButton)findViewById(R.id.btnMoreApp);
        btnSearch = (ImageButton)findViewById(R.id.btnSearch);
        txtError = (TextView)findViewById(R.id.txtError);

    }
    private void selectedButton(boolean slbtnRecommend,boolean slbtnCatelogy,boolean slbtnMoreApp,boolean slbtnSearch){
        btnRecommend.setSelected(slbtnRecommend);
        btnCatelogy.setSelected(slbtnCatelogy);
        btnMoreApp.setSelected(slbtnMoreApp);
        btnSearch.setSelected(slbtnSearch);
    }

    private void loadListSuggestion(){
        mService.getSuggestion().enqueue(new Callback<Tags>() {
            @Override
            public void onResponse(Call<Tags> call, Response<Tags> response) {

                if(response.isSuccessful()) {
                    mDataSearch.clear();
                    for(int i = 0;i<response.body().getCategories().size();i++){
                        mDataSearch.add(response.body().getCategories().get(i));
                    }
                    for(int i = 0;i<response.body().getTags().size();i++){
                        mDataSearch.add(response.body().getTags().get(i));
                    }
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<Tags> call, Throwable t) {
                //showErrorMessage();

            }
        });
    }


    public void selectItem(int position) {

        fragment = null;
        switch (position) {
            case 0:
                fragment = new RecommendBySortFragment();
                if(mTitleToolBar != null){
//                    mTitleToolBar.setText(R.string.recommend_for_you);
                    selectedButton(true,false,false,false);
                }
                seletedFragment = 0;
                break;
            case 1:
                fragment = new CatelogyFragment();
                selectedButton(false,true,false,false);
                seletedFragment = 1;
                break;
            case 2:
                fragment = new MoreAppFragment();
                selectedButton(false,false,true,false);
                seletedFragment = 2;
                break;
            case 3:
                fragment = new CatelogyDetailFragment();
                selectedButton(false,true,false,false);
                mTitleToolBar.setText(titleCatelogy);
                seletedFragment = 3;
                break;
            case 4:
                fragment = new SearchFragment();
                selectedButton(false,false,false,true);
                seletedFragment = 4;
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            loadAdsBanner();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setHideToolbar(){
        container_toolbar.setVisibility(View.GONE);
        LinearLayout.LayoutParams layoutParamslayout_view = (LinearLayout.LayoutParams) layout_view.getLayoutParams();
        layout_view.setWeightSum(616);
        layoutParamslayout_view.weight = 616;
        LinearLayout.LayoutParams layoutParamslayout_view_fragment = (LinearLayout.LayoutParams) layout_view_fragment.getLayoutParams();
        layoutParamslayout_view_fragment.weight = 580;
        layout_view_fragment.requestLayout();
        layout_view.requestLayout();
    }
    public void setShowToolbar(){
        container_toolbar.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams layoutParamslayout_view = (LinearLayout.LayoutParams) layout_view.getLayoutParams();
        layout_view.setWeightSum(574);
        layoutParamslayout_view.weight = 574;
        LinearLayout.LayoutParams layoutParamslayout_view_fragment = (LinearLayout.LayoutParams) layout_view_fragment.getLayoutParams();
        layoutParamslayout_view_fragment.weight = 538;
        layout_view_fragment.requestLayout();
        layout_view.requestLayout();
    }

    public void showHideBtnBack(boolean isShow){
        if(isShow){
            btnBack.setVisibility(View.VISIBLE);
        }else {
            btnBack.setVisibility(View.INVISIBLE);
        }
    }


    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {




            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            Log.e("IN", "" + referenceId);

            list.remove(referenceId);


            if (list.isEmpty())
            {
                Log.e("INSIDE", "" + referenceId);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(getString(R.string.app_name))
                                .setContentText(getString(R.string.download_complete));


                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());
            }

        }
    };

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        mRewardedVideoAd.pause(this);
        MyApplication.activityPaused();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        mRewardedVideoAd.resume(this);
        MyApplication.activityResumed();

    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
        unregisterReceiver(onComplete);
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getString(R.string.rewarded_video),
                new AdRequest.Builder().build());

        // showing the ad to user
        showRewardedVideo();
    }

    private void showRewardedVideo() {
        // make sure the ad is loaded completely before showing it
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    public void selectImage(final String public_id){
        // do something long
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mService.getImageDetail(public_id).enqueue(new Callback<ImageDetail>() {
                    @Override
                    public void onResponse(Call<ImageDetail> call, Response<ImageDetail> response) {

                        if(response.isSuccessful()) {

                        }else {
                            int statusCode  = response.code();
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageDetail> call, Throwable t) {

                    }
                });
            }
        };
        new Thread(runnable).start();

    }

}
