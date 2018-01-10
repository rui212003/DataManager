package co.jp.dm.entity;

import java.io.Serializable;

/**
 * Created by rui on 2018/1/9.
 * userエンティティ
 */
public class GoodsList implements Serializable {

    public  int  goodsListId;    //商品ID
    public  int  goodsBigtypeId;    //商品大分類
    public  int  goodsMiddletypeId;    //商品中分類
    public  int  goodsSmalltypeId;    //商品小分類
    public  String  goodsListName;    //商品名
    public  String  goodsBarcode;    //商品バーコード
    public  Double  goodsPriceJP;    //商品定価（円）
    public  Double  goodsPriceCH;    //商品売価（元）
    public  String  goodsUnitName;    //商品単位
    public  String  outputDelFlg;    //削除フラグ
    public  String  trkDate;    //登録日付
    public  String  updDate;    //更新日付




    public int getGoodsListId() {
        return goodsListId;
    }

    public void setGoodsListId(int goodsListId) {
        this.goodsListId = goodsListId;
    }

    public int getGoodsBigtypeId() {
        return goodsBigtypeId;
    }

    public void setGoodsBigtypeId(int goodsBigtypeId) {
        this.goodsBigtypeId = goodsBigtypeId;
    }

    public int getGoodsMiddletypeId() {
        return goodsMiddletypeId;
    }

    public void setGoodsMiddletypeId(int goodsMiddletypeId) {
        this.goodsMiddletypeId = goodsMiddletypeId;
    }

    public int getGoodsSmalltypeId() {
        return goodsSmalltypeId;
    }

    public void setGoodsSmalltypeId(int goodsSmalltypeId) {
        this.goodsSmalltypeId = goodsSmalltypeId;
    }

    public String getGoodsListName() {
        return goodsListName;
    }

    public void setGoodsListName(String goodsListName) {
        this.goodsListName = goodsListName;
    }

    public String getGoodsBarcode() {
        return goodsBarcode;
    }

    public void setGoodsBarcode(String goodsBarcode) {
        this.goodsBarcode = goodsBarcode;
    }

    public Double getGoodsPriceJP() {
        return goodsPriceJP;
    }

    public void setGoodsPriceJP(Double goodsPriceJP) {
        this.goodsPriceJP = goodsPriceJP;
    }

    public Double getGoodsPriceCH() {
        return goodsPriceCH;
    }

    public void setGoodsPriceCH(Double goodsPriceCH) {
        this.goodsPriceCH = goodsPriceCH;
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

