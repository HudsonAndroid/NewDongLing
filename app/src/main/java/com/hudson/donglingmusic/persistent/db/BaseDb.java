package com.hudson.donglingmusic.persistent.db;

import com.hudson.donglingmusic.persistent.db.wrapper.DbUtils;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2020/2/29.
 */
public class BaseDb {

    private DbUtils mDbUtils;

    protected BaseDb(String dbName){
        mDbUtils = DbUtils.getInstance(dbName);
    }

    public <T> boolean insertOrUpdate(T entity){
        if(entity == null){
            return false;
        }
        try{
            mDbUtils.insertOrUpdate(entity);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public <T> boolean insertOrUpdate(List<T> entities){
        if(entities == null || entities.size() == 0){
            return false;
        }
        try{
            for (T entity : entities) {
                mDbUtils.insertOrUpdate(entity);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public <T> boolean update(T entity){
        if(entity == null){
            return  false;
        }
        try {
            mDbUtils.update(entity);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public <T> List<T> queryAll(Class<T> tClass){
        if(tClass != null){
            try{
                return mDbUtils.queryAll(tClass);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    public <T> boolean delete(T entity){
        if(entity != null){
            try{
                mDbUtils.delete(entity);
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public <T> boolean deleteAll(Class<T> tClass){
        if(tClass != null){
            try {
                mDbUtils.deleteAll(tClass);
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public <T> boolean delete(Class<T> tClass, WhereCondition condition){
        if(tClass != null && condition != null){
            try {
                mDbUtils.delete(tClass,condition);
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public <T> T query(Class<T> tClass,WhereCondition condition){
        if(tClass != null && condition != null){
            try{
                return mDbUtils.query(tClass,condition);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public void clearCache(){
        try{
            mDbUtils.clearCache();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            mDbUtils.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
