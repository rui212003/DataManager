package co.jp.dm.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangrui on 15/01/29.
 * 共有部分を定義する
 */
public class Config {


    /**操作履歴用  テーブル名*/
    public static final String TBigtype="bigtype";
    public static final String TClientList = "clientList";
    public static final String TGoodsList = "goodsList";
    public static final String TGoodsUnit = "goodsUnit";
    public static final String TInputList = "inputList";
    public static final String TLogList = "logList";
    public static final String TMiddletype = "middletype";
    public static final String TOutputList = "outputList";
    public static final String TSmalltype = "smalltype";
    public static final String TStockList = "stockList";
    public static final String TUsers = "users";
    public static final String TWarehouse = "warehouse";

    /**操作履歴用*/
    public static final String TInsert = "新規追加";
    public static final String TEdit = "更新";
    public static final String TDelete = "削除";
    public static final String TMaster = "master取得";

    public static final String TLogin="ログイン";
    public static final String TLogout="ログアウト";
    public static final String TUserNull = "サーバーへの接続がタイムアウトしました。 もう一度ログインしてください。";
    public static final String LoginSession="login";

    //guest　IP 取得
    public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("Proxy-Client-IP");

        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("WL-Proxy-Client-IP");

        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getRemoteAddr();

        }

        return ip;

    }

}
