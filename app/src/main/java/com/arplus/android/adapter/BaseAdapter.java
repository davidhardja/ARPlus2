package com.arplus.android.adapter;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arplus.android.Application;
import com.arplus.android.database.DatabaseHelper;
import com.arplus.android.service.ApiService;
import com.arplus.android.tools.SessionManagement;

import java.util.List;

/**
 * Created by octagon-dicky on 12/23/16.
 */

public class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected LayoutInflater inflater;
    protected Context context;
    protected List<T> dataList;

    public BaseAdapter(Context context, List<T> dataList) {
        super();
        inflater = LayoutInflater.from(context);
        this.dataList = dataList;

    }

    protected boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null;
    }

    public ApiService getService() {
        return ((Application) context.getApplicationContext()).getService();
    }

    public DatabaseHelper getHelper() {
        return ((Application) context.getApplicationContext()).getHelper();
    }

    public SessionManagement getSession() {
        return ((Application) context.getApplicationContext()).getSession();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
