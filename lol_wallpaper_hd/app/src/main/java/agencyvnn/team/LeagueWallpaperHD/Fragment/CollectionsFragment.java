package agencyvnn.team.LeagueWallpaperHD.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import agencyvnn.team.LeagueWallpaperHD.Adapter.AllChampionsAdapter;
import agencyvnn.team.LeagueWallpaperHD.Api.Service.SOService;
import agencyvnn.team.LeagueWallpaperHD.Api.Utils.ApiUtils;
import agencyvnn.team.LeagueWallpaperHD.Base.BaseFragment;
import agencyvnn.team.LeagueWallpaperHD.Contanst.AppContants;
import agencyvnn.team.LeagueWallpaperHD.Models.GetCategory.CategoryResult;
import agencyvnn.team.LeagueWallpaperHD.Models.GetCategory.Datum;
import agencyvnn.team.LeagueWallpaperHD.R;
import agencyvnn.team.LeagueWallpaperHD.Utils.ItemOffsetDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hiephunbie on 4/13/18.
 */

public class CollectionsFragment extends BaseFragment {

    private static final String TAG = "StaggeredGridActivity";
    public static final String SAVED_DATA_KEY = "SAVED_DATA";

    private ProgressBar progressLoading;

    private ArrayList<String> mData;
    private StaggeredGridLayoutManager layoutManager;
    private TextView edt_search_list;

    private ArrayList<String> listDataImage = new ArrayList<String>();
    private ArrayList<String> listIdImage = new ArrayList<String>();
    private String searchValue = "";

    private RecyclerView mRecyclerView;
    private SOService mService;
    private AllChampionsAdapter mAdapter;
    private List<String> itemList = new ArrayList<String>();
    private List<Datum> optimizeUrlList = new ArrayList<Datum>();
    private Handler handler = new Handler();
    private int count_page = AppContants.count_page;
    private int visibleThreshold = AppContants.visibleThreshold;
    private int lastVisibleItem, totalItemCount;
    private boolean loading = false;
    private ProgressBar item_progress_bar;
    private int totalScroll = 0;
    private View viewBack;
    private ImageView imgSearch;
    private TextView txtTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collections, container, false);
        mService = ApiUtils.getSOService();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_answers);
        progressLoading = (ProgressBar)view.findViewById(R.id.progressLoading);
        item_progress_bar = (ProgressBar)view.findViewById(R.id.item_progress_bar);
        edt_search_list = (TextView) view.findViewById(R.id.edt_search);
        viewBack = (View)view.findViewById(R.id.viewBack);
        imgSearch = (ImageView)view.findViewById(R.id.imgSearch);
        txtTitle = (TextView)view.findViewById(R.id.txtTitle);
        Typeface tfFutura = Typeface.createFromAsset(mActivity.getAssets(), AppContants.FONT_TEXT);
        txtTitle.setTypeface(tfFutura);
        mAdapter = new AllChampionsAdapter(mActivity, optimizeUrlList, new AllChampionsAdapter.PostItemListener() {

            @Override
            public void onPostClick(final long idClick) {
                mActivity.titleCatelogy = optimizeUrlList.get((int) idClick).getCategoryName();
                mActivity.catelogy_id = optimizeUrlList.get((int) idClick).getCategoryId();
                mActivity.selectItem(1);
            }
        });
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
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                edt_search_list.showDropDown();
//            }
//        };
//        handler.postDelayed(runnable,200);
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
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.selectItem(5);
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
                    mActivity.selectItem(0);
                    return true;
                }
                return false;
            }
        } );
        return view;
    }

    public void loadImage(int count_page) {
        progressLoading.setVisibility(View.VISIBLE);
        mService.getCategorys(count_page,AppContants.lenght_list,10,2).enqueue(new Callback<CategoryResult>() {
            @Override
            public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {

                if(response.isSuccessful()) {

                    if(optimizeUrlList != null && optimizeUrlList.size()>0){
                        optimizeUrlList.clear();
                    }
                    for(int i = 0;i<response.body().getData().size();i++){
                        optimizeUrlList.add(response.body().getData().get(i));
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
            public void onFailure(Call<CategoryResult> call, Throwable t) {
                //showErrorMessage();
                progressLoading.setVisibility(View.GONE);
                Log.d("MainActivity", "Loi API");

            }
        });
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_DATA_KEY, mData);
    }

    private void loadMoreItemList(){
        count_page +=1;
        mService.getCategorys(count_page,AppContants.lenght_list,10,2).enqueue(new Callback<CategoryResult>() {
            @Override
            public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {

                if(response.isSuccessful()) {
                    if(response.body().getData().size()>0){
                        for(int i = 0;i<response.body().getData().size();i++){
                            optimizeUrlList.add(response.body().getData().get(i));
                        }
                        mAdapter.notifyDataSetChanged();
                        item_progress_bar.setVisibility(View.GONE);
                        loading=false;
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
            public void onFailure(Call<CategoryResult> call, Throwable t) {
                //showErrorMessage();

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

}
