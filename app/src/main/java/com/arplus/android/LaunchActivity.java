package com.arplus.android;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.arplus.android.database.data.User;

import java.sql.SQLException;

import butterknife.ButterKnife;

/**
 * Created by octagon-dicky on 12/23/16.
 */

public class LaunchActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            getHelper().getDao(User.class).countOf();
        }catch (SQLException e){
            e.printStackTrace();
        }

        setContentView(R.layout.layout_activity_launch);
        ButterKnife.inject(this);

        showMainPage();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showMainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }
}
