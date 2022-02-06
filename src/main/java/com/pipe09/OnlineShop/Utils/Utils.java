package com.pipe09.OnlineShop.Utils;


import java.io.File;
import java.time.LocalDate;

public class Utils {
    private static final String IMGPATH=System.getProperty("user.home");
    public static String getImgPATHwithOS(){
        String os=System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return IMGPATH +File.separator+"img"+File.separator;
        }

        else{
            return IMGPATH+"/"+"img"+ File.separator;
        }




    }
    public static LocalDate getNow(){
        return LocalDate.now();
    }

    public static boolean Null(Object obj){

        if(obj==null){
            return true;
        }
        return false;
    }
    public static String deleteKorean(String str){
        str=str.replaceAll("[ㄱ-힣]","");
        return str;
    }
}
