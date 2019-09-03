package com.hudson.donglingmusic.service.exception;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.Utils.CommonUtils;

/**
 * Created by Hudson on 2019/1/20.
 */
public class PlayListEmptyException extends RuntimeException {
    public PlayListEmptyException(){
        super(CommonUtils.getString(R.string.play_list_empty_tips));
    }
}
