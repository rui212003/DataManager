package co.jp.dm.entity;

import java.io.Serializable;

/**
 * Created by rui on 2018/1/9.
 * userエンティティ
 */
public class GoodsUnit implements Serializable {

    public  int  goodsUnitId;    //単位ID
    public  String  goodsUnitName;    //単位名
    public  String  outputDelFlg;    //削除フラグ
    public  String  trkDate;    //登録日付
    public  String  updDate;    //更新日付

    public int getGoodsUnitId() {
        return goodsUnitId;
    }

    public void setGoodsUnitId(int goodsUnitId) {
        this.goodsUnitId = goodsUnitId;
    }

    public String getGoodsUnitName() {
        return goodsUnitName;
    }

    public void setGoodsUnitName(String goodsUnitName) {
        this.goodsUnitName = goodsUnitName;
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

