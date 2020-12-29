package com.hudson.donglingmusic.persistent.db.wrapper;

import com.hudson.dongling.db.gen.DaoMaster;
import com.hudson.dongling.db.gen.DaoSession;
import com.hudson.donglingmusic.global.DongLingApplication;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Hudson on 2020/2/29.
 */
public class DbUtils {

    private static HashMap<String,DbUtils> sDbMap = new HashMap<>();
    private DbOpenHelper mDbOpenHelper;
    private DaoSession mDaoSession;

    private DbUtils(String dbName){
        mDbOpenHelper = new DbOpenHelper(DongLingApplication.getGlobalContext(), dbName, null);
        DaoMaster master = new DaoMaster(mDbOpenHelper.getWritableDatabase());
        mDaoSession = master.newSession();
    }

    public synchronized static DbUtils getInstance(String dbName){
        DbUtils dbUtils = sDbMap.get(dbName);
        if(dbUtils == null){
            dbUtils = new DbUtils(dbName);
            sDbMap.put(dbName,dbUtils);
        }
        return dbUtils;
    }

    public <T> void insertOrUpdate(T entity){
        mDaoSession.insertOrReplace(entity);
    }

    public <T> void update(T entity){
        mDaoSession.update(entity);
    }

    public <T> List<T> queryAll(Class<T> tClass){
        return mDaoSession.loadAll(tClass);
    }

    public <T> void delete(T entity){
        mDaoSession.delete(entity);
    }

    public <T> void deleteAll(Class<T> tClass){
        mDaoSession.deleteAll(tClass);
    }

    public <T> void delete(Class<T> tClass, WhereCondition condition){
        mDaoSession.getDao(tClass).queryBuilder().where(condition).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    @SuppressWarnings("unchecked")
    public <T> T query(Class<T> tClass,WhereCondition condition){
        return (T) mDaoSession.getDao(tClass).queryBuilder().where(condition).build().unique();
    }

    public void clearCache(){
        mDaoSession.clear();
    }

    public void close(){
        mDaoSession.clear();
        mDbOpenHelper.close();
    }
}
