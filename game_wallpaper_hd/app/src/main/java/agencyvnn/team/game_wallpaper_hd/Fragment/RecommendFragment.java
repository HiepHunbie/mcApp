package agencyvnn.team.game_wallpaper_hd.Fragment;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import agencyvnn.team.game_wallpaper_hd.Activity.MainActivity;
import agencyvnn.team.game_wallpaper_hd.Adapter.DialogListImageAdapter;
import agencyvnn.team.game_wallpaper_hd.Adapter.RecommendUpdateAdapter;
import agencyvnn.team.game_wallpaper_hd.Api.Service.SOService;
import agencyvnn.team.game_wallpaper_hd.Api.Utils.ApiUtils;
import agencyvnn.team.game_wallpaper_hd.Base.BaseFragment;
import agencyvnn.team.game_wallpaper_hd.Contanst.AppContants;
import agencyvnn.team.game_wallpaper_hd.Dialog.DialogCall;
import agencyvnn.team.game_wallpaper_hd.Models.Category_Detail;
import agencyvnn.team.game_wallpaper_hd.Models.Category_Detail_Data;
import agencyvnn.team.game_wallpaper_hd.Models.ImageDetail;
import agencyvnn.team.game_wallpaper_hd.R;
import agencyvnn.team.game_wallpaper_hd.Utils.DownLoadImageTask;
import agencyvnn.team.game_wallpaper_hd.Utils.EndlessRecyclerViewScrollListener;
import agencyvnn.team.game_wallpaper_hd.Utils.ItemOffsetDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hiephunbie on 4/9/18.
 */

public class RecommendFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private SOService mService;
    private RecommendUpdateAdapter mAdapter;
    private ProgressBar progressLoading;
    private List<Category_Detail_Data> itemList = new ArrayList<Category_Detail_Data>();
    protected Handler handler;
    private int count_page = AppContants.count_page;
    private int visibleThreshold = AppContants.visibleThreshold;
    private int lastVisibleItem, totalItemCount;
    private boolean loading = false;
    private ProgressBar item_progress_bar;
    private EndlessRecyclerViewScrollListener scrollListener;
    private List<String> listImage = new ArrayList<String>();
    private List<String> listIdImage = new ArrayList<String>();
    private DialogListImageAdapter mDialogListImageAdapter;
    private int totalScroll = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recommend, container, false);

        mService = ApiUtils.getSOService();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_answers_recommend);
        progressLoading = (ProgressBar)rootView.findViewById(R.id.progressLoading);
        item_progress_bar = (ProgressBar)rootView.findViewById(R.id.item_progress_bar);
        handler = new Handler();
        mAdapter = new RecommendUpdateAdapter(mActivity, itemList,mRecyclerView, new RecommendUpdateAdapter.PostItemListener() {

            @Override
            public void onPostClick(final long idClick) {
                showImageFullViewPager(mActivity,listImage,listIdImage,(int) idClick, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                        if (id == R.id.btnBack) {

                        }
                        else if (id == R.id.btnDownload) {
                        }else if(id == R.id.btnDetail){
                        }
                    }
                });
            }
        });
        mDialogListImageAdapter = new DialogListImageAdapter(mActivity,listImage);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mActivity, R.dimen._1sdp);
        mRecyclerView.addItemDecoration(itemDecoration);
        final GridLayoutManager manager = new GridLayoutManager(mActivity, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        progressLoading.setVisibility(View.VISIBLE);
        loadAnswers(count_page);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = manager.getItemCount();
                lastVisibleItem = manager.findLastVisibleItemPosition();
                if (!loading && (totalItemCount <= (lastVisibleItem + visibleThreshold))) {
                    loading=true;
                    item_progress_bar.setVisibility(View.VISIBLE);
                    loadMoreItemList();

                }
            }
        });
        mActivity.setShowToolbar();
        mActivity.showHideBtnBack(true);
        mActivity.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.selectItem(0);
            }
        });
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && !event.isCanceled())
                {
                    mActivity.selectItem(0);
                    return true;
                }
                return false;
            }
        } );
        return rootView;
    }

    private void loadMoreItemList(){
        count_page +=1;
        mService.getNewestRecommend(count_page,AppContants.lenght_list).enqueue(new Callback<Category_Detail>() {
            @Override
            public void onResponse(Call<Category_Detail> call, Response<Category_Detail> response) {

                if(response.isSuccessful()) {
                    if(response.body().getDatas().size()>0){
                        for(int i = 0;i<response.body().getDatas().size();i++){
                            itemList.add(response.body().getDatas().get(i));
                            listImage.add(response.body().getDatas().get(i).getUrl());
                            listIdImage.add(response.body().getDatas().get(i).getPublic_id());
                        }
                        mAdapter.notifyDataSetChanged();
                        item_progress_bar.setVisibility(View.GONE);
                        loading=false;
                        mDialogListImageAdapter.notifyDataSetChanged();
                    }else {
                        loading=true;
                        item_progress_bar.setVisibility(View.GONE);
                    }

                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                    Toast.makeText(mActivity, "Loi!",
                            Toast.LENGTH_LONG).show();
                    count_page -=1;
                    loadMoreItemList();
                }
            }

            @Override
            public void onFailure(Call<Category_Detail> call, Throwable t) {
                //showErrorMessage();

            }
        });
    }

    public void loadAnswers(int count_page) {
        mService.getNewestRecommend(count_page,AppContants.lenght_list).enqueue(new Callback<Category_Detail>() {
            @Override
            public void onResponse(Call<Category_Detail> call, Response<Category_Detail> response) {

                if(response.isSuccessful()) {

                    //mAdapter.updateAnswers(response.body().getDatas());
                    if(itemList != null && itemList.size()>0){
                        itemList.clear();
                    }
                    for(int i = 0;i<response.body().getDatas().size();i++){
                        itemList.add(response.body().getDatas().get(i));
                        listImage.add(response.body().getDatas().get(i).getUrl());
                        listIdImage.add(response.body().getDatas().get(i).getPublic_id());
                    }
                    mAdapter.notifyDataSetChanged();
                    progressLoading.setVisibility(View.GONE);

                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                    progressLoading.setVisibility(View.GONE);
                    Toast.makeText(mActivity, "Loi!",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Category_Detail> call, Throwable t) {
                //showErrorMessage();
                progressLoading.setVisibility(View.GONE);
                Log.d("MainActivity", "Loi API");

            }
        });
    }

    private void selectImage(String public_id){
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
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //    public static DraftRecruitmentFragment newInstance(String text) {
//
//        DraftRecruitmentFragment f = new DraftRecruitmentFragment();
//        Bundle b = new Bundle();
//        b.putString("msg", text);
//
//        f.setArguments(b);
//
//        return f;
//    }
    public  void showImageFullViewPager(final MainActivity mContext, final List<String> imagePath, final List<String> listIdImage, final int posItem, final View.OnClickListener listener){
        final AlertDialog builder = new AlertDialog.Builder(mContext,R.style.Theme_AppCompat_Light_NoActionBar_FullScreen).create();
        builder.show();
        builder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);

        Window window = builder.getWindow();
        window.setContentView(R.layout.dialog_full_viewpager);

        ImageButton btnBack = (ImageButton) window.findViewById(R.id.btnBack);
        ImageButton btnDownload = (ImageButton) window.findViewById(R.id.btnDownload);
        ImageButton btnDetail = (ImageButton) window.findViewById(R.id.btnDetail);

//        ImageView imgImage = (ImageView) window.findViewById(R.id.imgImage);
        final ViewPager mViewPager = (ViewPager) window.findViewById(R.id.pager);
        ImageView imgPre = (ImageView) window.findViewById(R.id.imgPre);
        ImageView imgNext = (ImageView) window.findViewById(R.id.imgNext);

        mViewPager.setAdapter(mDialogListImageAdapter);
        mViewPager.setCurrentItem(posItem);
        mViewPager.setOffscreenPageLimit(mDialogListImageAdapter.getCount() - 1);
        mContext.selectImage(listIdImage.get(posItem));


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {
                if(mViewPager.getCurrentItem()==listImage.size()-1){
                    loadMoreItemList();

                }
            }

            @Override
            public void onPageSelected(final int position) {
                mContext.selectImage(listImage.get(position));
                totalScroll+=1;
                if((totalScroll%8) == 0){
                    mActivity.loadAdsFull();
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
                if(mViewPager.getCurrentItem()<listImage.size()){
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
                mContext.DownloadFromUrl(listImage.get(mViewPager.getCurrentItem()));
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
//            mContext.loadAdsFull();
                //builder.dismiss();
                new DownLoadImageTask(mContext).execute(listImage.get(mViewPager.getCurrentItem()));
                Toast toast = Toast.makeText(mContext, mContext.getString(R.string.is_setting_wallpaper), Toast.LENGTH_LONG);
                toast.show();
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
}
