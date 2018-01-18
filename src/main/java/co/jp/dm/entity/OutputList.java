package co.jp.dm.entity;

import java.io.Serializable;

/**
 * Created by rui on 2018/1/9.
 * userエンティティ
 */
public class OutputList implements Serializable {

    public  int  outputListId;   //入庫ID
    public  int  goodsListId;   //商品表ID
    public  int  outputBigtypeId;    //商品大分類
    public  int  outputMiddletypeId;    //商品中分類
    public  int  outputSmalltypeId;    //商品小分類

    public  String  outputBigtypeName;    //商品大分類
    public  String  outputMiddletypeName;    //商品中分類
    public  String  outputSmalltypeName;    //商品小分類

    public  int  outputNum;   //入庫数量
    public  Double  outputDiscount;   //入庫割引
    public  String  outputDate;   //入庫日付
    public  String  outputTrackNum;   //入庫追跡番号
    public  int  warehouseId;   //倉庫名
    public  String  outputDelFlg;   //削除フラグ
    public  String  trkDate;   //登録日付
    public  String  updDate;   //更新日付

    GoodsList goodsList; //商品情報


    public GoodsList getGoodsList(){return  goodsList;}

    public void setGoodsList(GoodsList goodsList){this.goodsList=goodsList;}

    public int getOutputListId() {
        return outputListId;
    }

    public void setOutputListId(int outputListId) {this.outputListId = outputListId;}

    public int getOutputBigtypeId() {
        return outputBigtypeId;
    }

    public void setOutputBigtypeId(int outputBigtypeId) {
        this.outputBigtypeId = outputBigtypeId;
    }

    public int getOutputMiddletypeId() {
        return outputBigtypeId;
    }

    public void setOutputMiddletypeId(int outputMiddletypeId) {
        this.outputMiddletypeId = outputMiddletypeId;
    }

    public int getOutputSmalltypeId() {
        return outputSmalltypeId;
    }

    public void setOutputSmalltypeId(int outputSmalltypeId) {
        this.outputSmalltypeId = outputSmalltypeId;
    }

    public int getGoodsListId() {
        return goodsListId;
    }

    public void setGoodsListId(int goodsListId) {
        this.goodsListId = goodsListId;
    }

    public String getOutputBigtypeName() {
        return outputBigtypeName;
    }

    public void setOutputBigtypeName(String outputBigtypeName) {
        this.outputBigtypeName = outputBigtypeName;
    }

    public String getOutputMiddletypeName() {
        return outputMiddletypeName;
    }

    public void setOutputMiddletypeName(String outputMiddletypeName) {
        this.outputMiddletypeName = outputMiddletypeName;
    }

    public String getOutputSmalltypeName() {
        return outputSmalltypeName;
    }

    public void setOutputSmalltypeName(String outputSmalltypeName) {
        this.outputSmalltypeName = outputSmalltypeName;
    }

    public int getOutputNum() {
        return outputNum;
    }

    public void setOutputNum(int outputNum) {
        this.outputNum = outputNum;
    }

    public Double getOutputDiscount() {
        return outputDiscount;
    }

    public void setOutputDiscount(Double outputDiscount) {
        this.outputDiscount = outputDiscount;
    }

    public String getOutputDate() {
        return outputDate;
    }

    public void setOutputDate(String outputDate) {
        this.outputDate = outputDate;
    }

    public String getOutputTrackNum() {
        return outputTrackNum;
    }

    public void setOutputTrackNum(String outputTrackNum) {
        this.outputTrackNum = outputTrackNum;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
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

