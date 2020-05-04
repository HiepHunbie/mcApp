package agencyvnn.team.game_wallpaper_hd.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import agencyvnn.team.game_wallpaper_hd.Adapter.MoreAppAdapter;
import agencyvnn.team.game_wallpaper_hd.Api.Service.SOService;
import agencyvnn.team.game_wallpaper_hd.Api.Utils.ApiUtils;
import agencyvnn.team.game_wallpaper_hd.Base.BaseFragment;
import agencyvnn.team.game_wallpaper_hd.Models.MoreApp;
import agencyvnn.team.game_wallpaper_hd.R;
import agencyvnn.team.game_wallpaper_hd.Utils.ItemOffsetDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hiephunbie on 4/9/18.
 */

public class MoreAppFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private SOService mService;
    private MoreAppAdapter mAdapter;
    private ProgressBar progressLoading;
    private List<MoreApp> listApp = new ArrayList<MoreApp>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_more_app, container, false);

        mService = ApiUtils.getSOService();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_answers);
        progressLoading = (ProgressBar) rootView.findViewById(R.id.progressLoading);
        mAdapter = new MoreAppAdapter(mActivity, new ArrayList<MoreApp>(0), new MoreAppAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                final String appPackageName = listApp.get((int) id).getUrl(); // getPackageName() from Context or Activity object
                if(appPackageName.length()>0 && appPackageName !=null){
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }

            }
        });

        mActivity.setHideToolbar();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(mActivity, R.dimen._1sdp);
        mRecyclerView.addItemDecoration(itemDecoration);

        progressLoading.setVisibility(View.VISIBLE);
        loadAnswers();
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

    public void loadAnswers() {
        mService.getMoreApp().enqueue(new Callback<List<MoreApp>>() {
            @Override
            public void onResponse(Call<List<MoreApp>> call, Response<List<MoreApp>> response) {

                if (response.isSuccessful()) {
                    listApp.clear();
                    mAdapter.updateAnswers(response.body());
                    for (int i = 0;i<response.body().size();i++){
                        listApp.add(response.body().get(i));
                    }
                    progressLoading.setVisibility(View.GONE);
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                    progressLoading.setVisibility(View.GONE);
                    Toast.makeText(mActivity, "Loi!",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<MoreApp>> call, Throwable t) {
                //showErrorMessage();
                progressLoading.setVisibility(View.GONE);
                Log.d("MainActivity", "Loi API");

            }
        });
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
