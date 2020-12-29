package com.hudson.donglingmusic.common.Utils;

import android.database.Cursor;

import java.io.Closeable;

/**
 * Created by Hudson on 2020/3/8.
 */
public class IOUtils {

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
            }
        }
    }

    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable e) {
            }
        }
    }
}
