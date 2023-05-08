package com.pipe09.OnlineShop.Utils;


import com.pipe09.OnlineShop.Domain.Delivery.Deliverystatus;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDate;


public class Utils {
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";
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
    public static String MakingErrmessage(boolean type){
        if(type){
            return "삭제에 성공하였습니다.";
        }
        else{
            return "삭제에 실패하였습니다.(조회 실패)";
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
        str=str.substring(0,6);
        return Long.toString(System.nanoTime())+str;
    }
    public static String getAccessToken(HttpServletRequest request){
        String headerValue=request.getHeader(HEADER_AUTHORIZATION);

        if(headerValue==null){
            return null;
        }
        if(headerValue.startsWith(TOKEN_PREFIX)){
            return headerValue.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
    public static String getAnnouncedDeliverystatus(Deliverystatus deliverystatus){
        if(deliverystatus.equals(Deliverystatus.BEFORE)){
            return "결제 대기중";
        }else if(deliverystatus.equals(Deliverystatus.READY)){
            return "배송 대기중";
        }else if(deliverystatus.equals(Deliverystatus.CANCEL)){
            return "결제 취소";
        }else if(deliverystatus.equals(Deliverystatus.DELIVERY)){
            return "배송 중";
        }else if(deliverystatus.equals(Deliverystatus.COMPLETE)){
            return "배송 완료";
        }else{
            return "에러 발생";
        }
    }

    /**
     * for Migration
     *
     */

    public static String translateDtype( String type ){
        switch ( type ){
            case "L":
                return "누수탐지기";
            case "S":
                return "하수관청소기";
            case "G":
                return "글라인더";
            case "E":
                return "내시경";
            case "C":
                return "커터";
            case "N" :
                return "니퍼";
            case "W":
                return "렌치";
            case "H":
                return "해머";
            case "D":
                return "드릴";
            default:
                return "분류 없음";
        }
    }

}
