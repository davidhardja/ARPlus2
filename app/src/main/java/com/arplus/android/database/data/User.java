package com.arplus.android.database.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by octagon-dicky on 12/23/16.
 */

@DatabaseTable
public class User extends BaseDao {

    @DatabaseField(id = true)
    private String user_id;
}
