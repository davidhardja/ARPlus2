package com.arplus.android;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.arplus.android.common.Constant;
import com.arplus.android.database.DatabaseHelper;
import com.arplus.android.database.data.ArPlus;

import java.sql.SQLException;

/**
 * Created by octagon-dicky on 12/23/16.
 */

public class SplashActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_splash);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        new CreateDatabaseTask().execute();
    }

    class CreateDatabaseTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            DatabaseHelper helper = new DatabaseHelper(SplashActivity.this);
            try {
                helper.getDao(ArPlus.class).countOf();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //Create new signature to get new profile cache
//            Constant.profileSignature = Calendar.getInstance().getTime().toString();
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            //Start login activity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplashActivity.this, LaunchActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }, Constant.SPLASH_DISPLAY_LENGTH);
        }
    }

}
