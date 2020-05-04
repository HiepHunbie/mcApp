package agencyvnn.team.LeagueWallpaperHD.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;
import java.util.List;

import agencyvnn.team.LeagueWallpaperHD.Activity.MainActivity;
import agencyvnn.team.LeagueWallpaperHD.Adapter.CustomListSearchAdapter;
import agencyvnn.team.LeagueWallpaperHD.Adapter.DialogListImageAdapter;
import agencyvnn.team.LeagueWallpaperHD.Adapter.ImageDetailAdapter;
import agencyvnn.team.LeagueWallpaperHD.Api.Service.SOService;
import agencyvnn.team.LeagueWallpaperHD.Api.Utils.ApiUtils;
import agencyvnn.team.LeagueWallpaperHD.Base.BaseFragment;
import agencyvnn.team.LeagueWallpaperHD.Contanst.AppContants;
import agencyvnn.team.LeagueWallpaperHD.Models.Category_Data;
import agencyvnn.team.LeagueWallpaperHD.Models.CatelogyDetail.CatelogyDetailResult;
import agencyvnn.team.LeagueWallpaperHD.R;
import agencyvnn.team.LeagueWallpaperHD.Utils.DownLoadImageTask;
import agencyvnn.team.LeagueWallpaperHD.Utils.ItemOffsetDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hiepnt on 01/03/2018.
 */

public class SearchFragment extends BaseFragment {

    private static final String TAG = "StaggeredGridActivity";
    public static final String SAVED_DATA_KEY = "SAVED_DATA";

    private StaggeredGridView mGridView;
    private boolean mHasRequestedMore;
    private ProgressBar progressLoading;

    private ArrayList<String> mData;
    private StaggeredGridLayoutManager layoutManager;
    private AutoCompleteTextView edt_search_list;

    private CustomListSearchAdapter adapter;
    private ArrayList<String> listDataImage = new ArrayList<String>();
    private ArrayList<String> listIdImage = new ArrayList<String>();
    private String searchValue = "";

    private RecyclerView mRecyclerView;
    private SOService mService;
    private ImageDetailAdapter mAdapter;
    private List<String> itemList = new ArrayList<String>();
    private List<String> optimizeUrlList = new ArrayList<String>();
    private List<Category_Data> itemData = new ArrayList<Category_Data>();
    private Handler handler = new Handler();
    private int count_page = AppContants.count_page;
    private int visibleThreshold = AppContants.visibleThreshold;
    private int lastVisibleItem, totalItemCount;
    private boolean loading = false;
    private ProgressBar item_progress_bar;
    private DialogListImageAdapter mDialogListImageAdapter;
    private int totalScroll = 0;
    private ImageView imgSearch;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mService = ApiUtils.getSOService();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_answers);
        progressLoading = (ProgressBar)view.findViewById(R.id.progressLoading);
        item_progress_bar = (ProgressBar)view.findViewById(R.id.item_progress_bar);
        edt_search_list = (AutoCompleteTextView) view.findViewById(R.id.edt_search);
        Typeface tfFutura = Typeface.createFromAsset(mActivity.getAssets(), AppContants.FONT_TEXT);
        edt_search_list.setTypeface(tfFutura);
        imgSearch = (ImageView)view.findViewById(R.id.imgSearch);

        mAdapter = new ImageDetailAdapter(mActivity, optimizeUrlList,mRecyclerView, new ImageDetailAdapter.PostItemListener() {

            @Override
            public void onPostClick(final long idClick) {
               showImageFullViewPager(mActivity, itemList,listIdImage,(int) idClick, new View.OnClickListener() {
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
        mDialogListImageAdapter = new DialogListImageAdapter(mActivity,itemList);
         adapter = new CustomListSearchAdapter(mActivity,
                R.layout.row_place, mActivity.mDataSearch);
        if(!isStoragePermissionGranted())
        {


        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mActivity, R.dimen._2sdp);
        mRecyclerView.addItemDecoration(itemDecoration);
        final GridLayoutManager manager = new GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        progressLoading.setVisibility(View.VISIBLE);
//        mActivity.titleCatelogy = "";
//        mActivity.catelogy_id = "";
//        loadImage(count_page);
        mActivity.setHideToolbar();
        mActivity.setHideToolbar();
        edt_search_list.setThreshold(1);//will start working from first character
        edt_search_list.setAdapter(adapter);

        edt_search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchValue = (String)parent.getItemAtPosition(position);
                mActivity.titleCatelogy = searchValue;
                count_page = 1;
                for (int i = 0;i<mActivity.mDataSearch.size();i++){
                    if(mActivity.mDataSearch.get(i).equals(searchValue)){
                        mActivity.catelogy_id = mActivity.mDataSearch_Id.get(i);
                    }
                }
                loadImage(count_page);
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchValue = edt_search_list.getText().toString();
                if(mActivity.mDataSearch.contains(searchValue)){
                    for (int i = 0;i<mActivity.mDataSearch.size();i++){
                        if(mActivity.mDataSearch.get(i).equals(searchValue)){
                            mActivity.catelogy_id = mActivity.mDataSearch_Id.get(i);
                        }
                    }

                }else {
                    mActivity.catelogy_id = "";
                }
                mActivity.titleCatelogy = searchValue;
                count_page = 1;
                loadImage(count_page);
            }
        });
//        edt_search_list.enoughToFilter();
//        edt_search_list.showDropDown();
//        edt_search_list.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    edt_search_list.showDropDown();
//
//            }
//        });
//
//        edt_search_list.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                edt_search_list.showDropDown();
//                return false;
//            }
//        });
        edt_search_list.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null &&
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed()) {
                        // the user is done typing.
                        searchValue = edt_search_list.getText().toString();
                        if(mActivity.mDataSearch.contains(searchValue)){
                            for (int i = 0;i<mActivity.mDataSearch.size();i++){
                                if(mActivity.mDataSearch.get(i).equals(searchValue)){
                                    mActivity.catelogy_id = mActivity.mDataSearch_Id.get(i);
                                }
                            }

                        }else {
                            mActivity.catelogy_id = "";
                        }
                        mActivity.titleCatelogy = searchValue;
                        count_page = 1;
                        loadImage(count_page);
//                        count_page = 1;
//                        loadImage(count_page);
                        return true; // consume.
                    }
                }
                return false;
            }
        });
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
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                edt_search_list.showDropDown();
            }
        };
        handler.postDelayed(runnable,500);
        progressLoading.setVisibility(View.GONE);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && !event.isCanceled())
                {
                    if(!edt_search_list.isPopupShowing()){
                        mActivity.selectItem(0);
                    }else {
                        edt_search_list.dismissDropDown();
                    }

                    return true;
                }
                return false;
            }
        } );
        return view;
    }

    public void loadImage(int count_page) {
        progressLoading.setVisibility(View.VISIBLE);
        String category_name = mActivity.catelogy_id;
        if(category_name.length()>0){
            mService.getSearchImages(count_page, AppContants.lenght_list,mActivity.titleCatelogy,category_name).enqueue(new Callback<CatelogyDetailResult>() {
                @Override
                public void onResponse(Call<CatelogyDetailResult> call, Response<CatelogyDetailResult> response) {

                    if(response.isSuccessful()) {

                        listIdImage.clear();
                        listDataImage.clear();
                        optimizeUrlList.clear();
                        itemList.clear();
                        for(int i = 0;i<response.body().getData().size();i++){
                            itemList.add(response.body().getData().get(i).getUrl());
                            listDataImage.add(response.body().getData().get(i).getUrl());
                            listIdImage.add(response.body().getData().get(i).getPublicId());
                            optimizeUrlList.add(response.body().getData().get(i).getOptimizeUrl());
                        }
                        progressLoading.setVisibility(View.GONE);
                        mAdapter.notifyDataSetChanged();
                        mDialogListImageAdapter.notifyDataSetChanged();
                    }else {
                        int statusCode  = response.code();
                        // handle request errors depending on status code
                        progressLoading.setVisibility(View.GONE);
                        Toast.makeText(mActivity, "Loi!",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<CatelogyDetailResult> call, Throwable t) {
                    //showErrorMessage();
                    progressLoading.setVisibility(View.GONE);
                    Log.d("MainActivity", "Loi API");

                }
            });
        }else {
            mService.getSearchImagesNotId(count_page, AppContants.lenght_list,mActivity.titleCatelogy).enqueue(new Callback<CatelogyDetailResult>() {
                @Override
                public void onResponse(Call<CatelogyDetailResult> call, Response<CatelogyDetailResult> response) {

                    if(response.isSuccessful()) {

                        listIdImage.clear();
                        listDataImage.clear();
                        optimizeUrlList.clear();
                        itemList.clear();
                        for(int i = 0;i<response.body().getData().size();i++){
                            itemList.add(response.body().getData().get(i).getUrl());
                            listDataImage.add(response.body().getData().get(i).getUrl());
                            listIdImage.add(response.body().getData().get(i).getPublicId());
                            optimizeUrlList.add(response.body().getData().get(i).getOptimizeUrl());
                        }
                        progressLoading.setVisibility(View.GONE);
                        mAdapter.notifyDataSetChanged();
                        mDialogListImageAdapter.notifyDataSetChanged();
                    }else {
                        int statusCode  = response.code();
                        // handle request errors depending on status code
                        progressLoading.setVisibility(View.GONE);
                        Toast.makeText(mActivity, "Loi!",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<CatelogyDetailResult> call, Throwable t) {
                    //showErrorMessage();
                    progressLoading.setVisibility(View.GONE);
                    Log.d("MainActivity", "Loi API");

                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_DATA_KEY, mData);
    }

    private void loadMoreItemList() {
        count_page +=1;
        String category_name = mActivity.catelogy_id;
        if(category_name.length()>0){
            mService.getSearchImages(count_page, AppContants.lenght_list,mActivity.titleCatelogy,category_name).enqueue(new Callback<CatelogyDetailResult>() {
                @Override
                public void onResponse(Call<CatelogyDetailResult> call, Response<CatelogyDetailResult> response) {

                    if(response.isSuccessful()) {
                        if(response.body().getData().size()>0){
                            for(int i = 0;i<response.body().getData().size();i++){
                                itemList.add(response.body().getData().get(i).getUrl());
                                listIdImage.add(response.body().getData().get(i).getPublicId());
                                listDataImage.add(response.body().getData().get(i).getUrl());
                                optimizeUrlList.add(response.body().getData().get(i).getOptimizeUrl());
                            }
                            mAdapter.notifyDataSetChanged();
                            item_progress_bar.setVisibility(View.GONE);
                            loading=false;
                            progressLoading.setVisibility(View.GONE);
                            mDialogListImageAdapter.notifyDataSetChanged();
                        }else {
                            loading=true;
                            item_progress_bar.setVisibility(View.GONE);
                            progressLoading.setVisibility(View.GONE);
                        }

                    }else {
                        int statusCode  = response.code();
                        // handle request errors depending on status code
                        progressLoading.setVisibility(View.GONE);
                        Toast.makeText(mActivity, "Loi!",
                                Toast.LENGTH_LONG).show();
                        count_page -=1;
                        loadMoreItemList();
                    }
                }

                @Override
                public void onFailure(Call<CatelogyDetailResult> call, Throwable t) {
                    //showErrorMessage();
                    progressLoading.setVisibility(View.GONE);
                    Log.d("MainActivity", "Loi API");

                }
            });
        }else {
            mService.getSearchImagesNotId(count_page, AppContants.lenght_list,mActivity.titleCatelogy).enqueue(new Callback<CatelogyDetailResult>() {
                @Override
                public void onResponse(Call<CatelogyDetailResult> call, Response<CatelogyDetailResult> response) {

                    if(response.isSuccessful()) {
                        if(response.body().getData().size()>0){
                            for(int i = 0;i<response.body().getData().size();i++){
                                itemList.add(response.body().getData().get(i).getUrl());
                                listIdImage.add(response.body().getData().get(i).getPublicId());
                                listDataImage.add(response.body().getData().get(i).getUrl());
                                optimizeUrlList.add(response.body().getData().get(i).getOptimizeUrl());
                            }
                            mAdapter.notifyDataSetChanged();
                            item_progress_bar.setVisibility(View.GONE);
                            loading=false;
                            progressLoading.setVisibility(View.GONE);
                            mDialogListImageAdapter.notifyDataSetChanged();
                        }else {
                            loading=true;
                            item_progress_bar.setVisibility(View.GONE);
                            progressLoading.setVisibility(View.GONE);
                        }

                    }else {
                        int statusCode  = response.code();
                        // handle request errors depending on status code
                        progressLoading.setVisibility(View.GONE);
                        Toast.makeText(mActivity, "Loi!",
                                Toast.LENGTH_LONG).show();
                        count_page -=1;
                        loadMoreItemList();
                    }
                }

                @Override
                public void onFailure(Call<CatelogyDetailResult> call, Throwable t) {
                    //showErrorMessage();
                    progressLoading.setVisibility(View.GONE);
                    Log.d("MainActivity", "Loi API");

                }
            });
        }
    }


    private ArrayList<String> generateData() {
        ArrayList<String> listData = new ArrayList<String>();
        for (int i = 0;i<listDataImage.size();i++){
            listData.add(listDataImage.get(i));
        }
        progressLoading.setVisibility(View.GONE);
        return listData;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        mActivity.setShowToolbar();
    }

    @Override
    public void onStop() {
        super.onStop();
        mActivity.setShowToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.setHideToolbar();
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (mActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){


            // permission granted

        }
    }

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
                if(mViewPager.getCurrentItem()==itemList.size()-1){
                    loadMoreItemList();

                }
            }

            @Override
            public void onPageSelected(final int position) {
                mContext.selectImage(itemList.get(position));
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
                if(mViewPager.getCurrentItem()<itemList.size()){
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
                mContext.DownloadFromUrl(itemList.get(mViewPager.getCurrentItem()));
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
                new DownLoadImageTask(mContext).execute(itemList.get(mViewPager.getCurrentItem()));
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
