package agencyvnn.team.beautiful_girlshd.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

import agencyvnn.team.beautiful_girlshd.Activity.MainActivity;
import agencyvnn.team.beautiful_girlshd.Adapter.CustomListSearchAdapter;
import agencyvnn.team.beautiful_girlshd.Adapter.DialogListImageAdapter;
import agencyvnn.team.beautiful_girlshd.Adapter.ImageDetailAdapter;
import agencyvnn.team.beautiful_girlshd.Api.Service.SOService;
import agencyvnn.team.beautiful_girlshd.Api.Utils.ApiUtils;
import agencyvnn.team.beautiful_girlshd.Base.BaseFragment;
import agencyvnn.team.beautiful_girlshd.Contants.AppContants;
import agencyvnn.team.beautiful_girlshd.Dialog.DialogCall;
import agencyvnn.team.beautiful_girlshd.Model.Category_Data;
import agencyvnn.team.beautiful_girlshd.Model.Category_Detail;
import agencyvnn.team.beautiful_girlshd.R;
import agencyvnn.team.beautiful_girlshd.Utils.DownLoadImageTask;
import agencyvnn.team.beautiful_girlshd.Utils.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.List;

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
                        }else if(id == R.id.btnImageSetWall){
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
        loadImage(count_page);
        mActivity.setHideToolbar();
        mActivity.setHideToolbar();
        edt_search_list.setThreshold(1);//will start working from first character
        edt_search_list.setAdapter(adapter);

        edt_search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchValue = (String)parent.getItemAtPosition(position);
                count_page = 1;
                loadImage(count_page);
                Log.d("searchValue :","  ss  "+searchValue);
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchValue = edt_search_list.getText().toString();
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
                        count_page = 1;
                        loadImage(count_page);
                        return true; // consume.
                    }
                }
                return false;
            }
        });
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                edt_search_list.showDropDown();
            }
        };
        handler.postDelayed(runnable,200);
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

        mService.getSearchImages(count_page,AppContants.lenght_list,searchValue).enqueue(new Callback<Category_Detail>() {
            @Override
            public void onResponse(Call<Category_Detail> call, Response<Category_Detail> response) {

                if(response.isSuccessful()) {
                    listDataImage.clear();
                    listIdImage.clear();
                    itemList.clear();
                    optimizeUrlList.clear();
                    for(int i = 0;i<response.body().getDatas().size();i++){
                        listDataImage.add(response.body().getDatas().get(i).getUrl());
                        listIdImage.add(response.body().getDatas().get(i).getPublic_id());
                        itemList.add(response.body().getDatas().get(i).getUrl());
                        optimizeUrlList.add(response.body().getDatas().get(i).getOptimizeUrl());
                    }
                    mAdapter.notifyDataSetChanged();
                    progressLoading.setVisibility(View.GONE);
                }else {
                    int statusCode  = response.code();
                    progressLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Category_Detail> call, Throwable t) {
                //showErrorMessage();
                progressLoading.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_DATA_KEY, mData);
    }

    private void loadMoreItemList() {
        count_page +=1;
        mService.getSearchImages(count_page,AppContants.lenght_list,searchValue).enqueue(new Callback<Category_Detail>() {
            @Override
            public void onResponse(Call<Category_Detail> call, Response<Category_Detail> response) {

                if(response.isSuccessful()) {
                    if(response.body().getDatas().size()>0){
                        for(int i = 0;i<response.body().getDatas().size();i++){
                            itemList.add(response.body().getDatas().get(i).getUrl());
                            listIdImage.add(response.body().getDatas().get(i).getPublic_id());
                            optimizeUrlList.add(response.body().getDatas().get(i).getOptimizeUrl());
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
                    Toast.makeText(mActivity, "Loi!",
                            Toast.LENGTH_LONG).show();
                    count_page -=1;
                    loadMoreItemList();
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
            if (mActivity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
        ImageButton btnImage = (ImageButton) window.findViewById(R.id.btnImageSetWall);
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
        btnImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if(listener != null){
                    listener.onClick(arg0);
                }
                new DownLoadImageTask(mContext).execute(itemList.get(mViewPager.getCurrentItem()));
                Toast toast = Toast.makeText(mContext, mContext.getString(R.string.is_setting_wallpaper), Toast.LENGTH_LONG);
                toast.show();
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
                DialogCall.showDialogRating(mContext, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();
                        if (id == R.id.btnEvaluete) {
                            Uri uri = Uri.parse("market://details?id="+getString(R.string.pagekage_id_store));
                            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                            // To count with Play market backstack, After pressing back button,
                            // to taken back to our application, we need to add following flags to intent.
                            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            try {
                                startActivity(goToMarket);
                            } catch (ActivityNotFoundException e) {
                                startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("http://play.google.com/store/apps/details?id="+getString(R.string.pagekage_id_store))));
                            }
                        }
                        else if (id == R.id.btnCancel) {
                        }
                    }
                });
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
