package com.arplus.android;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.arplus.android.common.Constant;
import com.arplus.android.database.DatabaseHelper;
import com.arplus.android.service.ApiService;
import com.arplus.android.tools.SessionManagement;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;


import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;


/**
 * Created by octagon-dicky on 12/23/16.
 */


public class Application extends MultiDexApplication {
    private ApiService service;
    private DatabaseHelper helper;
    private SessionManagement session;

    @Override
    public void onCreate(){
        super.onCreate();

        //create client
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(300,TimeUnit.SECONDS);


        //Initialize API service
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constant.SCHEME_URL + "://" + Constant.API_URL)
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        .serializeNulls()
                        .create()))
                .build();
        service = restAdapter.create(ApiService.class);

        //Initialize SQLite helper
        helper = new DatabaseHelper(this);

        //Initialize session
        session = new SessionManagement(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
