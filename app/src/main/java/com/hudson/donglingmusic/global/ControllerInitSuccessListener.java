package com.hudson.donglingmusic.global;

import com.hudson.donglingmusic.service.IPlayerController;

/**
 * Created by Hudson on 2020/4/24.
 */
public interface ControllerInitSuccessListener {
    void onPlayerControllerInitSuccess(IPlayerController controller);
}
