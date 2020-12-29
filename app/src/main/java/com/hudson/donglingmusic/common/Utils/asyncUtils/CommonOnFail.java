package com.hudson.donglingmusic.common.Utils.asyncUtils;

import com.hudson.donglingmusic.common.exception.AutoHandleException;


/**
 * Created by Hudson on 2020/3/3.
 */
public class CommonOnFail implements IDoOnFail {
//    private final WeakReference<Context> mContextWeakReference;
    private boolean mHasHandle;

//    public CommonOnFail() {
//        mContextWeakReference = new WeakReference<>(DongLingApplication.getGlobalContext());
//    }
//
//    /**
//     * auto handle exception constructor
//     * @param context Context for showing toast. What's more,if it's an activity
//     *                instance,exception will auto handle page jump result codes.
//     *                For example,see
//     *                {@link com.hudson.donglingmusic.common.Utils.networkUtils.exceptions.NetWorkInvalidException}.
//     *                Note:if target context instance has been recycled,it will not work.
//     */
//    public CommonOnFail(@NonNull Context context){
//        mContextWeakReference = new WeakReference<>(context);
//    }

    @Override
    public final void onFail(Throwable e) {
        e.printStackTrace();
        mHasHandle = false;
        if (e instanceof AutoHandleException) {
//            Context context = mContextWeakReference.get();
//            if(context == null){
//                context = DongLingApplication.getGlobalContext();
//            }
            ((AutoHandleException) e).handleException();
            mHasHandle = true;
        }
        handleOnFail(e);
    }

    /**
     * @return return true,if exception has been handled.
     */
    public boolean hasHandle(){
        return mHasHandle;
    }

    /**
     * Override if you want do more actions,or your own catch exception which is not auto handled.
     * @param e target exception
     */
    protected void handleOnFail(Throwable e){}
}
