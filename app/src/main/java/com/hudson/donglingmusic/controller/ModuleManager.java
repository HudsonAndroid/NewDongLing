package com.hudson.donglingmusic.controller;

import com.hudson.donglingmusic.module.IModule;
import com.hudson.donglingmusic.module.ModuleTarget;
import com.hudson.donglingmusic.module.account.AccountModule;
import com.hudson.donglingmusic.module.language.LanguageModule;
import com.hudson.donglingmusic.persistent.db.DbModule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Hudson on 2020/2/27.
 */
public class ModuleManager {
    private final static ConcurrentHashMap<Class<? extends IModule>,Object> mModuleMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends IModule> T getModule(Class<T> pModuleClass){
        if (pModuleClass == null) {
            return null;
        }
        IModule module = (IModule) mModuleMap.get(pModuleClass);
        if (module == null) {
            System.out.println( "wait:" +pModuleClass);
            synchronized (pModuleClass) {
                module = (IModule) mModuleMap.get(pModuleClass);
                System.out.println("get:" + module);
                if (module == null) {
                    ModuleTarget implement = pModuleClass.getAnnotation(ModuleTarget.class);
                    if (implement != null) {
                        try {
                            Constructor<IModule> constructor = implement.value().getDeclaredConstructor();
                            constructor.setAccessible(true);
                            module = constructor.newInstance();
                            mModuleMap.put(pModuleClass,module);
                        } catch (InstantiationException pE) {
                            pE.printStackTrace();
                        } catch (IllegalAccessException pE) {
                            pE.printStackTrace();
                        } catch (NoSuchMethodException pE) {
                            pE.printStackTrace();
                        } catch (InvocationTargetException pE) {
                            pE.printStackTrace();
                        }
                    }
                }
            }
        }
        return (T) module;
    }

    public static void remove(Class<? extends IModule> pClass){
        if (pClass != null) {
            mModuleMap.remove(pClass);
        }
    }

    public static LanguageModule getLanguageModule(){
        return getModule(LanguageModule.class);
    }

    public static DbModule getDbModule(){
        return getModule(DbModule.class);
    }

    public static AccountModule getAccountModule(){
        return getModule(AccountModule.class);
    }
}
