package agencyvnn.team.beautiful_girlshd.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import agencyvnn.team.beautiful_girlshd.Base.BaseFragment;
import agencyvnn.team.beautiful_girlshd.Dialog.DialogCall;
import agencyvnn.team.beautiful_girlshd.R;

/**
 * Created by hiephunbie on 3/20/18.
 */

public class RecommendBySortFragment extends BaseFragment {
    public FragmentTabHost mTabHost;
    public Context mContext;
    private RelativeLayout btnHotest,btnNewest;
    private ViewPager pager;

    public RecommendBySortFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recomment_use_sort, container, false);
        mContext= getActivity().getApplicationContext();
        btnHotest = (RelativeLayout)rootView.findViewById(R.id.btnHotest);
        btnNewest = (RelativeLayout)rootView.findViewById(R.id.btnNewest);
        selectItem(mActivity.tabSaleProduct);
        mActivity.setHideToolbar();

        btnHotest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(1);
            }
        });
        btnNewest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(0);
            }
        });
        mActivity.setHideToolbar();
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && !event.isCanceled())
                {
                    DialogCall.showDialogRating(mActivity, new View.OnClickListener() {
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
                                mActivity.loadAdsFull();
                                DialogCall.showConfirmDialog(mActivity, getString(R.string.confirm_close_app), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        int id = v.getId();
                                        if (id == R.id.btnOk) {
                                            mActivity.finish();
                                            System.exit(0);
                                        }
                                        else if (id == R.id.btnCancel) {
                                        }
                                    }
                                });
                            }
                        }
                    });
                    return true;
                }
                return false;
            }
        } );
        return rootView;
    }
    private void selectedUser(boolean slbtnHotest,boolean slbtnNewest){
        btnHotest.setSelected(slbtnHotest);
        btnNewest.setSelected(slbtnNewest);
    }
    private void selectItem(int position) {

        BaseFragment fragment = null;

        switch (position) {
            case 0:
                fragment = new RecommendNewestFragment();
                selectedUser(false,true);
                mActivity.tabSaleProduct = 0;
                break;
            case 1:
                fragment = new RecommendHostestFragment();
                selectedUser(true,false);
                mActivity.tabSaleProduct = 1;
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame_tab, fragment).commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
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
}
