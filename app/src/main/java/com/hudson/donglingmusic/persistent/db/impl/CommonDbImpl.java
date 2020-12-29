package com.hudson.donglingmusic.persistent.db.impl;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hudson.dongling.db.gen.LocalDataDao;
import com.hudson.donglingmusic.common.Utils.jsonUtils.JsonUtils;
import com.hudson.donglingmusic.entity.LocalData;
import com.hudson.donglingmusic.persistent.db.CommonDb;
import com.hudson.donglingmusic.persistent.db.interfaces.ICommonDb;

/**
 * Created by Hudson on 2020/2/29.
 */
public class CommonDbImpl extends BaseWriteableDb<CommonDb> implements ICommonDb {

    public CommonDbImpl() {
        super(CommonDb.getInstance());
        //删除过期的localData数据
        mDB.delete(LocalData.class,LocalDataDao.Properties.MExpireTime.lt(System.currentTimeMillis()));
    }


    @Override
    public <T> T getLocalData(@NonNull LocalData<T> localData) {
        LocalData result = mDB.query(LocalData.class, LocalDataDao.Properties.MUniqueId.eq(localData.getUniqueId()));
        if(result != null){
            if(!TextUtils.isEmpty(result.getData())){
                return JsonUtils.fromJson(localData.getTargetClass(),result.getData());
            }
        }
        return null;
    }
}
