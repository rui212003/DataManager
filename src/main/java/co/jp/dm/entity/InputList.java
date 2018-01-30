package co.jp.dm.entity;

import java.io.Serializable;

/**
 * Created by rui on 2018/1/9.
 * userエンティティ
 */
public class InputList implements Serializable {

    public  int  inputListId;   //入庫ID
    public  int  goodsListId;   //商品表ID
    public  int  inputBigtypeId;    //商品大分類
    public  int  inputMiddletypeId;    //商品中分類
    public  int  inputSmalltypeId;    //商品小分類

    public  String  goodsBarcode;    //商品バーコード

    public  String  inputBigtypeName;    //商品大分類
    public  String  inputMiddletypeName;    //商品中分類
    public  String  inputSmalltypeName;    //商品小分類

    public  int  inputNum;   //入庫数量
    public  Double  inputDiscount;   //入庫割引
    public  String  inputDate;   //入庫日付
    public  String  inputTrackNum;   //入庫追跡番号
    public  int  warehouseId;   //倉庫名
    public  String  inputDelFlg;   //削除フラグ
    public  String  trkDate;   //登録日付
    public  String  updDate;   //更新日付

    GoodsList goodsList; //商品情報


    public GoodsList getGoodsList(){return  goodsList;}

    public void setGoodsList(GoodsList goodsList){this.goodsList=goodsList;}

    public int getInputListId() {
        return inputListId;
    }

    public void setInputListId(int inputListId) {
        this.inputListId = inputListId;
    }

    public int getInputBigtypeId() {
        return inputBigtypeId;
    }

    public void setInputBigtypeId(int inputBigtypeId) {
        this.inputBigtypeId = inputBigtypeId;
    }

    public int getInputMiddletypeId() {
        return inputMiddletypeId;
    }

    public void setInputMiddletypeId(int inputMiddletypeId) {
        this.inputMiddletypeId = inputMiddletypeId;
    }

    public int getInputSmalltypeId() {
        return inputSmalltypeId;
    }

    public void setInputSmalltypeId(int inputSmalltypeId) {
        this.inputSmalltypeId = inputSmalltypeId;
    }

    public int getGoodsListId() {
        return goodsListId;
    }

    public void setGoodsListId(int goodsListId) {
        this.goodsListId = goodsListId;
    }

    public String getGoodsBarcode() {
        return goodsBarcode;
    }

    public void setGoodsBarcode(String goodsBarcode) {
        this.goodsBarcode = goodsBarcode;
    }

    public String getInputBigtypeName() {
        return inputBigtypeName;
    }

    public void setInputBigtypeName(String inputBigtypeName) {
        this.inputBigtypeName = inputBigtypeName;
    }

    public String getInputMiddletypeName() {
        return inputMiddletypeName;
    }

    public void setInputMiddletypeName(String inputMiddletypeName) {
        this.inputMiddletypeName = inputMiddletypeName;
    }

    public String getInputSmalltypeName() {
        return inputSmalltypeName;
    }

    public void setInputSmalltypeName(String inputSmalltypeName) {
        this.inputSmalltypeName = inputSmalltypeName;
    }

    public int getInputNum() {
        return inputNum;
    }

    public void setInputNum(int inputNum) {
        this.inputNum = inputNum;
    }

    public Double getInputDiscount() {
        return inputDiscount;
    }

    public void setInputDiscount(Double inputDiscount) {
        this.inputDiscount = inputDiscount;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getInputTrackNum() {
        return inputTrackNum;
    }

    public void setInputTrackNum(String inputTrackNum) {
        this.inputTrackNum = inputTrackNum;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getInputDelFlg() {
        return inputDelFlg;
    }

    public void setInputDelFlg(String inputDelFlg) {
        this.inputDelFlg = inputDelFlg;
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

