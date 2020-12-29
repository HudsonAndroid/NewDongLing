package com.hudson.donglingmusic.common.Utils.asyncUtils.pool;

import java.util.concurrent.ExecutorService;

/**
 * Created by Hudson on 2020/3/24.
 */
interface IPoolType {
    ExecutorService getPool();
}
