package agencyvnn.team.beautiful_girlshd.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import agencyvnn.team.beautiful_girlshd.Adapter.CatelogyAdapter;
import agencyvnn.team.beautiful_girlshd.Api.Service.SOService;
import agencyvnn.team.beautiful_girlshd.Api.Utils.ApiUtils;
import agencyvnn.team.beautiful_girlshd.Base.BaseFragment;
import agencyvnn.team.beautiful_girlshd.Contants.AppContants;
import agencyvnn.team.beautiful_girlshd.Model.Category;
import agencyvnn.team.beautiful_girlshd.Model.Category_Data;
import agencyvnn.team.beautiful_girlshd.R;
import agencyvnn.team.beautiful_girlshd.Utils.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hiepnt on 23/02/2018.
 */

public class CatelogyFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private SOService mService;
    private CatelogyAdapter mAdapter;
    private ProgressBar progressLoading;
    private List<Category_Data> itemList = new ArrayList<Category_Data>();
    protected Handler handler;
    private int count_page = AppContants.count_page;
    private int visibleThreshold = AppContants.visibleThreshold;
    private int lastVisibleItem, totalItemCount;
    private boolean loading = false;
    private ProgressBar item_progress_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_catelogy, container, false);

        mService = ApiUtils.getSOService();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_answers);
        progressLoading = (ProgressBar)rootView.findViewById(R.id.progressLoading);
        item_progress_bar = (ProgressBar)rootView.findViewById(R.id.item_progress_bar);
        handler = new Handler();
        mAdapter = new CatelogyAdapter(mActivity, itemList,mRecyclerView, new CatelogyAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                mActivity.titleCatelogy = itemList.get((int) id).getCategory_name();
                mActivity.selectItem(3);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mActivity, R.dimen._2sdp);
        mRecyclerView.addItemDecoration(itemDecoration);
        final GridLayoutManager manager = new GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        progressLoading.setVisibility(View.VISIBLE);
        mActivity.setHideToolbar();
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
        mService.getCategorys(count_page,AppContants.lenght_list).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {

                if(response.isSuccessful()) {
                    if(response.body().getDatas().size()>0){
                        for(int i = 0;i<response.body().getDatas().size();i++){
                            itemList.add(response.body().getDatas().get(i));
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
            public void onFailure(Call<Category> call, Throwable t) {
                //showErrorMessage();

            }
        });
    }

    public void loadAnswers(int count_page) {
        mService.getCategorys(count_page,AppContants.lenght_list).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {

                if(response.isSuccessful()) {

                    if(itemList != null && itemList.size()>0){
                        itemList.clear();
                    }
                    for(int i = 0;i<response.body().getDatas().size();i++){
                        itemList.add(response.body().getDatas().get(i));
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
            public void onFailure(Call<Category> call, Throwable t) {
                //showErrorMessage();
                progressLoading.setVisibility(View.GONE);
                Log.d("MainActivity", "Loi API");

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
//        refreshValue = 1;
        mActivity.setShowToolbar();
    }

    @Override
    public void onStop() {
        super.onStop();
//        refreshValue = 1;
        mActivity.setShowToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.setHideToolbar();
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
}
