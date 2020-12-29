package com.hudson.donglingmusic.persistent.db.wrapper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.hudson.dongling.db.gen.DaoMaster;
import com.hudson.dongling.db.gen.LocalDataDao;

import org.greenrobot.greendao.database.Database;

/**
 * 数据库升级参考；
 * https://github.com/yuweiguocn/GreenDaoUpgradeHelper/blob/master/README_CH.md
 * Created by Hudson on 2020/2/29.
 */
public class DbOpenHelper extends DaoMaster.OpenHelper {

    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        // 迁移数据库(如果修改了多个实体类，则需要把对应的Dao都传进来)
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, LocalDataDao.class);
    }
}
