package com.arplus.android.common;

import android.os.Build;

/**
 * Created by octagon-dicky on 12/23/16.
 */

public class Constant {
    public static String APP_NAME = "ARPlus";
    public static String APP_VERSION = "0.1.1";
    public static String tagName = "1.9.22";
    public static String DEVICE_TYPE = "Android-" + Build.MANUFACTURER + "-" + Build.MODEL;


    // ============== APIs =========================
    public static String API_URL = "???";
    public static String SCHEME_URL = "https";

    // ============== Database =====================
    public static String DATABASE_NAME = "ARPlus_DB";
    public static int DATABASE_VERSION = 2;
    public static String DAO_PACKAGE_NAME = "com.arplus.android.database.data";

    public static int SPLASH_DISPLAY_LENGTH = 1000;
    public static String profileSignature = null;

    //Limit Storage
    public static final int LIMIT_STORAGE_IN_MEGA = 30;
    public static final int LIMIT_STORAGE_IN_MEGA_FOR_GRAPH_REQUEST = 16;
    public static final int LIMIT_STORAGE_IN_MEGA_FOR_FRAGMENT_STACK = 500;
    public static final int LIMIT_FRAGMENT_FOR_LOW_SPACE = 50;


}
