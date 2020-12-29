package com.hudson.donglingmusic.common.Utils.asyncUtils.pool;

import java.util.concurrent.ExecutorService;

/**
 * Created by Hudson on 2020/3/24.
 */
public enum PoolType implements IPoolType {

    DbType{
        @Override
        public ExecutorService getPool() {
            return ThreadPools.getInstance().getDbOperatePool();
        }
    },
    NetType{
        @Override
        public ExecutorService getPool() {
            return ThreadPools.getInstance().getNetAccessPool();
        }
    }
}
