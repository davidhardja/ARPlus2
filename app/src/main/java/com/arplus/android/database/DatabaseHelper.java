package com.arplus.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.arplus.android.common.Constant;
import com.arplus.android.database.data.ArPlus;
import com.arplus.android.database.data.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by octagon-dicky on 12/23/16.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private ArrayList<Class> classes = new ArrayList<>();

    public DatabaseHelper(Context context){
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);

        classes = new ArrayList<>();
        classes.add(ArPlus.class);
        classes.add(User.class);
        //Add new class here for table
        //exp classes.add(Dummy.class);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        createAllTable(connectionSource);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        resetDatabase();
    }

    public void resetDatabase() {
        dropAllTable(getConnectionSource());
        createAllTable(getConnectionSource());
    }

    private void createAllTable(ConnectionSource connectionSource) {
        for (Class c : classes) {
            if (c != null) {
                try {
                    TableUtils.createTableIfNotExists(connectionSource, c);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void dropAllTable(ConnectionSource connectionSource) {
        for (Class c : classes) {
            if (c != null) {
                try {
                    TableUtils.dropTable(connectionSource, c, true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void close() {
        super.close();
    }

}
