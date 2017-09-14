package com.eyck.fxreading.utils;

import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eyck on 2017/9/1.
 */

public class FileUtil {

    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static final String FXREADPATH = PATH+"FXRead";

    public static void createSdDir(){
        File file = new File(FileUtil.FXREADPATH);
        if(!file.exists()) {
            boolean create = file.mkdirs();
            Logger.d("create="+create);
        }else {
            if(!file.isDirectory()) {
                file.delete();
                file.mkdir();
            }
        }
    }

    public static boolean isFileExist(String param){
        if(param == null) {
            return false;
        }
        File localFile = new File(FXREADPATH + "/" + param);
        if(localFile .exists()) {
            return true;
        }
        return false;
    }

    public static File createFile(String fileName) throws IOException {
        File file = new File(FXREADPATH , fileName);
        file.createNewFile();
        return file;
    }

    public static List<String> getAllAD(){
        File file = new File(FileUtil.FXREADPATH);
        File[] fileList = file.listFiles();
        List<String> list = new ArrayList<>();
        if(null != fileList){
            for (File f:fileList) {
                list.add(f.getAbsolutePath());
            }
        }
        return list;
    }



}
