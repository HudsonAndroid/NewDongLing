package com.hudson.donglingmusic.common.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Hudson on 2020/3/12.
 */
public class FileUtils {

    public static boolean saveStrList(List<String> lyrics, String path) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(path));
            for (String lyric : lyrics) {
                bw.write(lyric);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bw);
        }
        return false;
    }
}
