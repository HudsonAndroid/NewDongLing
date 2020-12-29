package com.hudson.donglingmusic.module.data.fetcher.lyricsFetcher;

import android.text.TextUtils;
import android.util.Log;

import com.hudson.donglingmusic.common.Utils.IOUtils;
import com.hudson.donglingmusic.entity.LyricsResult;
import com.hudson.newlyricsview.lyrics.entity.Lyrics;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Hudson on 2018/12/8.
 */
public class LocalLyricsDecoder  {

    public static LyricsResult decode(String path) {
        final InputStreamReader isr;
        FileInputStream fis= null;
        if (!TextUtils.isEmpty(path) && new File(path).exists()) {
            File file = new File(path);
            try {
                //创建一个文件输入流对象
                fis = new FileInputStream(file);
                String fileCode = getFileEncode(file);
                isr = new InputStreamReader(fis,(fileCode==null)?"utf-8":fileCode);
                return readLrcFromInputStream(isr);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                if(fis!=null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            Log.e("LyricsDecoder","the lyric file doesn't exist!");
        }
        return null;
    }

    //从流中读取歌词
    public static LyricsResult readLrcFromInputStream(InputStreamReader isr){
        LyricsResult result = new LyricsResult();
        BufferedReader br = null;
        try{
            br = new BufferedReader(isr);
            String s = "";
            Lyrics lyrics ;
            int len;
            String splitLrcData[];
            while((s = br.readLine()) != null) {
                //替换字符
                s = s.replace("[", "");
                //分离“@”字符
                splitLrcData = s.split("]");
                len = splitLrcData.length;
                //判断是否以]结尾，存在[xx][xx]情况
                if(s.endsWith("]")){//不加入该歌词
                    continue;
                }
                if(len > 1) {
                    for(int i = 0;i<len-1;i++){//存在多个时间戳使用同一句歌词的情况
                        lyrics = new Lyrics();
                        lyrics.setLrcContent(splitLrcData[len-1]);//添加内容
                        long time = StringTime2IntTime(splitLrcData[i]);
                        if(time!=-1){
                            lyrics.setLrcProgressTime(time);
                            result.addTimeItem(time);
                            result.addLyricsItem(lyrics);
                        }
                    }
                }
            }
            return result.sort();
        }catch(Exception e){
            Log.e("LyricsDecoder","read lyric occur error!");
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(isr);
        }
        return null;
    }

    public static String getFileEncode(File file) {
        if (!file.exists()) {
            Log.e("LyricsDecoder","getFileEncode: file not exists!");
            return null;
        }
        byte[] buf = new byte[4096];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            UniversalDetector detector = new UniversalDetector(null);
            int nread;
            while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            detector.reset();
            fis.close();
            return encoding;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析歌词时间
     * 1.标准格式歌词内容格式如下：
     * [00:02.32]陈奕迅
     * [00:03.43]好久不见
     * [00:05.22]歌词制作  王涛
     * 2.其他格式：
     * [00:02]陈奕迅
     * [00:03]好久不见
     * [00:05]歌词制作  王涛
     * 3.其他格式  本格式忽略
     * [00:02:32]陈奕迅
     * [00:03:43]好久不见
     * [00:05:22]歌词制作  王涛
     * @param timeStr 时间字符串
     * @return
     */
    public static long StringTime2IntTime(String timeStr) {
        long currentTime = 0,second,minute;
        if(timeStr.contains(".")){
            timeStr = timeStr.replace(":", ".");  //用后面的替换前面的
            timeStr = timeStr.replace(".", "@");
            String timeData[] = timeStr.split("@"); //将时间分隔成字符串数组
            //分离出分、秒并转换为整型
            minute = Integer.parseInt(timeData[0]);
            second = Integer.parseInt(timeData[1]);
            long millisecond = Integer.parseInt(timeData[2]);  //我认为是10毫秒为单位，但是百度百科说是毫秒
            //计算上一行与下一行的时间转换为毫秒数
            currentTime = (minute * 60 + second) * 1000 + millisecond * 10;
        }else if(timeStr.contains(":")&&timeStr.length()<6){//第三种格式几乎没见过，不予考虑
            String timeData[] = timeStr.split(":"); //将时间分隔成字符串数组
            //分离出分、秒并转换为整型
            minute = Integer.parseInt(timeData[0]);
            second = Integer.parseInt(timeData[1]);
            currentTime = (minute * 60 + second) * 1000;
        }else{//说明字符串不是时间格式或者字符串是第三种类型（没处理）
            return -1;
        }
        return currentTime;
    }
}
