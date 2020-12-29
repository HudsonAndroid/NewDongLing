package com.hudson.donglingplayer.exception;

/**
 * Created by hudson on 2019/8/29.
 */
public class PlayerNotInitException extends RuntimeException{

    public PlayerNotInitException(String from) {
        super("You should initialize player before you do it,exception from :"+from);
    }
}
