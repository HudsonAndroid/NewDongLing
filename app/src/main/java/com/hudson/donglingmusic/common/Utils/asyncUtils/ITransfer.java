package com.hudson.donglingmusic.common.Utils.asyncUtils;

public interface ITransfer<BEFORE,AFTER> {
    AFTER doTransfer(BEFORE pBEFORE);
}
