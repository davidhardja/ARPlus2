package com.arplus.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.arplus.android.database.DatabaseHelper;
import com.arplus.android.service.ApiService;
import com.arplus.android.tools.SessionManagement;

/**
 * Created by octagon-dicky on 12/23/16.
 */

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }

    protected void showToast(String message) {
        if (message != null)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int messageResId) {
        if (getResources() != null)
            Toast.makeText(this, getResources().getString(messageResId), Toast.LENGTH_SHORT).show();
    }

    protected boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null;
    }

    public ApiService getService() {
        return ((Application) getApplication()).getService();
    }

    public DatabaseHelper getHelper() {
        return ((Application) getApplication()).getHelper();
    }

    public SessionManagement getSession() {
        return ((Application) getApplication()).getSession();
    }

    public void removeFragment(Fragment fragment) {
        removeFragment(fragment, true);
    }

    public void removeFragment(Fragment fragment, boolean withTransition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        if (!withTransition)
            transaction.setCustomAnimations(0, 0);
        transaction.remove(fragment);
        transaction.commit();
    }

    public void addFragment(final int frameId, final Fragment fragment, final String fragmentTag, final boolean withTransition) {
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                FragmentManager fragmentManager = getSupportFragmentManager();


                FragmentTransaction transaction = fragmentManager.beginTransaction();
                //Set custom animation before replace
                if (withTransition)
                    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);

                if (!isDestroyed()) {
                    transaction.replace(frameId, fragment, fragmentTag);
                    Fragment existFragment = getSupportFragmentManager().findFragmentById(frameId);
                    if (existFragment != null)
                        transaction.addToBackStack(fragmentTag);
                    transaction.commitAllowingStateLoss();
                }
            }
        });

    }

}
