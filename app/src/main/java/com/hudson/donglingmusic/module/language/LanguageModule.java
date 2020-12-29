package com.hudson.donglingmusic.module.language;

import android.support.annotation.Keep;

import com.hudson.donglingmusic.module.IModule;
import com.hudson.donglingmusic.module.ModuleTarget;


@ModuleTarget(LanguageModuleImpl.class)@Keep
public interface LanguageModule extends IModule {
    boolean isTraditional();
    String getLanguageString();
}
