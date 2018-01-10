package co.jp.dm.entity;

import java.io.Serializable;

/**
 * Created by rui on 2018/1/9.
 * userエンティティ
 */
public class Bigtype implements Serializable {

    public  int  bigtypeId;    //大分類ID
    public  String  bigtypeName;    //大分類名
    public  String  outputDelFlg;    //削除フラグ
    public  String  trkDate;    //登録日付
    public  String  updDate;    //更新日付

    public int getBigtypeId() {
        return bigtypeId;
    }

    public void setBigtypeId(int bigtypeId) {
        this.bigtypeId = bigtypeId;
    }

    public String getBigtypeName() {
        return bigtypeName;
    }

    public void setBigtypeName(String bigtypeName) {
        this.bigtypeName = bigtypeName;
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

