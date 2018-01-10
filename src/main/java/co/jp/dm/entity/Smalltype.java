package co.jp.dm.entity;

import java.io.Serializable;

/**
 * Created by rui on 2018/1/9.
 * userエンティティ
 */
public class Smalltype implements Serializable {

    public  int  smalltypeId;    //小分類ID
    public  int  bigtypeId;    //大分類ID
    public  int  middletypeId;    //中分類ID
    public  String  smalltypeName;    //小分類名
    public  String  outputDelFlg;    //削除フラグ
    public  String  trkDate;    //登録日付
    public  String  updDate;    //更新日付


    public int getSmalltypeId() {
        return smalltypeId;
    }

    public void setSmalltypeId(int smalltypeId) {
        this.smalltypeId = smalltypeId;
    }

    public int getMiddletypeId() {
        return middletypeId;
    }

    public void setMiddletypeId(int middletypeId) {
        this.middletypeId = middletypeId;
    }

    public int getBigtypeId() {
        return bigtypeId;
    }

    public void setBigtypeId(int bigtypeId) {
        this.bigtypeId = bigtypeId;
    }

    public String getSmalltypeName() {
        return smalltypeName;
    }

    public void setSmalltypeName(String smalltypeName) {
        this.smalltypeName = smalltypeName;
    }

    public String getOutputDelFlg() {
        return outputDelFlg;
    }

    public void setOutputDelFlg(String outputDelFlg) {
        this.outputDelFlg = outputDelFlg;
    }

    public String getTrkDate() {
        return trkDate;
    }

    public void setTrkDate(String trkDate) {
        this.trkDate = trkDate;
    }

    public String getUpdDate() {
        return updDate;
    }

    public void setUpdDate(String updDate) {
        this.updDate = updDate;
    }



}

