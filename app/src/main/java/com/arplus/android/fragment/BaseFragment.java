package com.arplus.android.fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.arplus.android.Application;
import com.arplus.android.common.Constant;
import com.arplus.android.database.DatabaseHelper;
import com.arplus.android.service.ApiService;
import com.arplus.android.tools.SessionManagement;

import static android.R.attr.version;

/**
 * Created by octagon-dicky on 12/23/16.
 */

public class BaseFragment extends Fragment {
    protected DatabaseHelper helper;
    protected SessionManagement session;
    protected ApiService service;
    private String version;
    private int parentIndex = 0;

    public BaseFragment(){}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeAllService();
    }

    protected void initializeAllService() {
        if (service == null) {
            service = ((Application) getActivity().getApplication()).getService();
            helper = ((Application) getActivity().getApplication()).getHelper();
            session = ((Application) getActivity().getApplication()).getSession();
        }

        //Initialize version
        version = Constant.APP_VERSION;
        try {
            version = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (Exception e) {
            Log.e(Constant.tagName, e.getLocalizedMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setParentTab(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public int getParentTab() {
        return parentIndex;
    }

    protected boolean isNetworkConnected() {
        if (getActivity() != null) {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo ni = cm.getActiveNetworkInfo();
                return ni != null;
            }
            return false;
        }
        return false;
    }

    public ApiService getService() {
        return service;
    }

    public DatabaseHelper getHelper() {
        return helper;
    }

    public SessionManagement getSession() {
        return session;
    }

}
