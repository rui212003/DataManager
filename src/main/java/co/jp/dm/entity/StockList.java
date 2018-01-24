package co.jp.dm.entity;

import java.io.Serializable;

/**
 * Created by rui on 2018/1/24.
 * userエンティティ
 */
public class StockList implements Serializable {

    public  int  stockListId;    //在庫ID
    public  int  goodsListId;    //商品ID
    public  int  stockBigtypeId;    //商品大分類
    public  int  stockMiddletypeId;    //商品中分類
    public  int  stocksSmalltypeId;    //商品小分類
    public  int  stockWarehouseId;    //倉庫

    public  int  stockputNum;    //在庫数
    public  String  stockDelFlg;    //削除FLG
    public  String  trkDate;    //登録日付
    public  String  updDate;    //更新日付

    GoodsList goodsList; //商品情報
    Warehouse warehouse;//倉庫情報

    public GoodsList getGoodsList(){return  goodsList;}

    public void setGoodsList(GoodsList goodsList){this.goodsList=goodsList;}

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse (Warehouse warehouse) { this.warehouse = warehouse;}

    public int getStockListId() {
        return stockListId;
    }

    public void setStockListId(int stockListId) {
        this.stockListId = stockListId;
    }

    public int getGoodsListId() {
        return goodsListId;
    }

    public void setGoodsListId(int goodsListId) {
        this.goodsListId = goodsListId;
    }

    public int getStockBigtypeId() {
        return stockBigtypeId;
    }

    public void setStockBigtypeId(int stockBigtypeId) {
        this.stockBigtypeId = stockBigtypeId;
    }

    public int getStockMiddletypeId() {
        return stockMiddletypeId;
    }

    public void setStockMiddletypeId(int stockMiddletypeId) {
        this.stockMiddletypeId = stockMiddletypeId;
    }

    public int getStocksSmalltypeId() {
        return stocksSmalltypeId;
    }

    public void setStocksSmalltypeId(int stocksSmalltypeId) {
        this.stocksSmalltypeId = stocksSmalltypeId;
    }

    public int getStockWarehouseId() {
        return stockWarehouseId;
    }

    public void setStockWarehouseId(int stockWarehouseId) {
        this.stockWarehouseId = stockWarehouseId;
    }

    public int getStockputNum() {
        return stockputNum;
    }

    public void setStockputNum(int stockputNum) {
        this.stockputNum = stockputNum;
    }

    public String getStockDelFlg() {
        return stockDelFlg;
    }

    public void setStockDelFlg(String stockDelFlg) {
        this.stockDelFlg = stockDelFlg;
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

