package co.jp.dm.entity;

import java.io.Serializable;

/**
 * Created by rui on 2018/1/9.
 * userエンティティ
 */
public class Warehouse implements Serializable {

    public  int  warehouseId;    //倉庫ID
    public  String  userGroupName;    //ユーザ名
    public  String  warehouseName;    //倉庫名
    public  String  outputDelFlg;    //削除フラグ
    public  String  trkDate;    //登録日付
    public  String  updDate;    //更新日付

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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

