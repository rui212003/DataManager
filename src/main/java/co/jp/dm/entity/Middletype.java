package co.jp.dm.entity;

import java.io.Serializable;

/**
 * Created by rui on 2018/1/9.
 * userエンティティ
 */
public class Middletype implements Serializable {

    public  int  middletypeId;    //中分類ID
    public  int  bigtypeId;    //大分類ID
    public  String  middletypeName;    //中分類名
    public  String  outputDelFlg;    //削除フラグ
    public  String  trkDate;    //登録日付
    public  String  updDate;    //更新日付


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

    public String getMiddletypeName() {
        return middletypeName;
    }

    public void setMiddletypeName(String middletypeName) {
        this.middletypeName = middletypeName;
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

