package co.jp.dm.service;

import co.jp.dm.dao.HistoryValveMapper;
import co.jp.dm.dao.TypeMapper;
import co.jp.dm.entity.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */
@Service
public class TypeService {

    @Resource
    HistoryValveMapper historyValveMapper;

    @Resource
    TypeMapper typeMapper;

    /**全ての大分類マスターを抽出する*/
    public List<Bigtype> getAllBigtype(){
        List<Bigtype> bigtypeList = typeMapper.findAllBigtype();
        return bigtypeList;
    }

    /**単位マスターを抽出する*/
    public List<GoodsUnit> getAllGoodsUnit(){
        List<GoodsUnit> goodsUnitList = typeMapper.findAllGoodsUnit();
        return goodsUnitList;
    }

    /**大分類から中分類を抽出する*/
    public List<Middletype> getMiddleTypeByBigtype(String bigtypeValue){
        Middletype middletype=new Middletype();
        middletype.setBigtypeId(Integer.valueOf(bigtypeValue));
        middletype.setOutputDelFlg("0");

        List<Middletype> middletypeList = typeMapper.findMiddleTypeByBigtype(middletype);
        return middletypeList;
    }

    /**大分類、中分類から小分類を取得*/
    public List<Smalltype> getSmallTypeByBigType(String bigtypeValue,String middletypeValue){
        Smalltype smalltype=new Smalltype();
        smalltype.setBigtypeId(Integer.valueOf(bigtypeValue));
        smalltype.setMiddletypeId(Integer.valueOf(middletypeValue));
        smalltype.setOutputDelFlg("0");

        List<Smalltype> smalltypeList = typeMapper.findSmallTypeByBigtype(smalltype);
        return smalltypeList;
    }

    /**大分類IDから大分類名称を抽出する*/
    public Bigtype getBigTypeByBigtypeId(int bigtypeId){
        Bigtype bigtype=new Bigtype();
        bigtype.setBigtypeId(bigtypeId);

        bigtype = typeMapper.findBigTypeByBigtypeId(bigtype);
        return bigtype;
    }

    /**中分類IDから中分類名称を抽出する*/
    public Middletype getMiddleTypeBytypeId(int middletypeId){
        Middletype middletype=new Middletype();
        middletype.setMiddletypeId(middletypeId);

        middletype = typeMapper.findMiddleTypeByMiddletypeId(middletype);
        return middletype;
    }

    /**小分類IDから小分類名称を抽出する*/
    public Smalltype getSmallTypeBytypeId(int smalltypeId){
        Smalltype smalltype=new Smalltype();
        smalltype.setSmalltypeId(smalltypeId);

        smalltype = typeMapper.findSmallTypeBySmalltypeId(smalltype);
        return smalltype;
    }

    //***************************************
    //                 大分類
    //***************************************

    /**大分類を修正する*/
    public Bigtype updateBigtypeData(Bigtype bigtype){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        bigtype.setUpdDate(sdf1.format(date));

        typeMapper.updateBigtypeDateBybigtype(bigtype);
        bigtype = typeMapper.findBigTypeByBigtypeId(bigtype);
        return bigtype;
    }

    /**大分類を削除する*/
    public void deleteBigtypeData(Bigtype bigtype){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        bigtype.setUpdDate(sdf1.format(date));
        bigtype.setOutputDelFlg("1");
        typeMapper.deleteBigTypeByBigtype(bigtype);

    }

    /**大分類を追加する*/
    public Bigtype addBigtypeData(){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        Bigtype bigtype=new Bigtype();
        bigtype.setUpdDate(sdf1.format(date));
        bigtype.setTrkDate(sdf1.format(date));
        bigtype.setOutputDelFlg("0");
        bigtype.setBigtypeName("");

        typeMapper.insertBigType(bigtype);
        int tempId=typeMapper.getLastInsertBigTypeId();
        bigtype.setBigtypeId(tempId);
        return bigtype;
    }


    //***************************************
    //                 中分類
    //***************************************

    /**中分類を修正する*/
    public Middletype updateMiddletypeData(Middletype middletype){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        middletype.setUpdDate(sdf1.format(date));

        typeMapper.updateMiddletypeDateByMiddletype(middletype);
        middletype = typeMapper.findMiddleTypeByMiddletypeId(middletype);
        return middletype;
    }

    /**中分類を削除する*/
    public void deleteMiddletypeData(Middletype middletype){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        middletype.setUpdDate(sdf1.format(date));
        middletype.setOutputDelFlg("1");
        typeMapper.deleteMiddleTypeByMiddletype(middletype);

    }

    /**中分類を追加する*/
    public Middletype addMiddletype(Middletype middletype){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");


        middletype.setUpdDate(sdf1.format(date));
        middletype.setTrkDate(sdf1.format(date));
        middletype.setOutputDelFlg("0");
        middletype.setMiddletypeName("");


        typeMapper.insertMiddleType(middletype);
        int tempId=typeMapper.getLastInsertMiddleTypeId();
        middletype.setMiddletypeId(tempId);
        return middletype;
    }

    //***************************************
    //                 小分類
    //***************************************

    /**小分類を修正する*/
    public Smalltype updateSmalltypeData(Smalltype smalltype){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        smalltype.setUpdDate(sdf1.format(date));

        typeMapper.updateSmalltypeDateBySmalltype(smalltype);
        smalltype = typeMapper.findSmallTypeBySmalltypeId(smalltype);
        return smalltype;
    }

    /**小分類を削除する*/
    public void deleteSmalltypeData(Smalltype smalltype){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        smalltype.setUpdDate(sdf1.format(date));
        smalltype.setOutputDelFlg("1");
        typeMapper.deleteSmallTypeBySmalltype(smalltype);

    }

    /**小分類を追加する*/
    public Smalltype addSmalltype(Smalltype smalltype){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");


        smalltype.setUpdDate(sdf1.format(date));
        smalltype.setTrkDate(sdf1.format(date));
        smalltype.setOutputDelFlg("0");
        smalltype.setSmalltypeName("");


        typeMapper.insertSmallType(smalltype);
        int tempId=typeMapper.getLastInsertSmallTypeId();
        smalltype.setSmalltypeId(tempId);
        return smalltype;
    }


    //*******************************************
    //               単位部分
    //*******************************************

    /**大分類を修正する*/
    public GoodsUnit updateGoodsUnit(GoodsUnit goodsUnit){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        goodsUnit.setUpdDate(sdf1.format(date));

        typeMapper.updateGoodsUnit(goodsUnit);
        goodsUnit = typeMapper.findGoodUnitByGoodUnitId(goodsUnit);
        return goodsUnit;
    }

    /**大分類を削除する*/
    public void deleteGoodsUnit(GoodsUnit goodsUnit){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        goodsUnit.setUpdDate(sdf1.format(date));
        goodsUnit.setOutputDelFlg("1");
        typeMapper.deleteGoodsUnit(goodsUnit);

    }

    /**大分類を追加する*/
    public GoodsUnit addGoodsUnit(){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        GoodsUnit goodsUnit=new GoodsUnit();
        goodsUnit.setUpdDate(sdf1.format(date));
        goodsUnit.setTrkDate(sdf1.format(date));
        goodsUnit.setOutputDelFlg("0");
        goodsUnit.setGoodsUnitName("");

        typeMapper.insertGoodsUnit(goodsUnit);
        int tempId=typeMapper.getLastInsertGoodsUnitId();
        goodsUnit.setGoodsUnitId(tempId);
        return goodsUnit;
    }


    //*******************************************
    //              倉庫部分
    //*******************************************

    /**倉庫マスターを抽出する*/
    public List<Warehouse> getAllWarehouse(){
        List<Warehouse> warehouseList = typeMapper.findAllWarehouse();
        return warehouseList;
    }

    /**倉庫IDによりデータを抽出する*/
    public Warehouse getWarehouseByWarehouseId(int warehouseId){
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId(warehouseId);
        warehouse.setOutputDelFlg("0");

        warehouse=typeMapper.findWarehouseByWarehouseId(warehouse);
        return warehouse;
    }

    /**倉庫を修正する*/
    public Warehouse updateWarehouseName(Warehouse warehouse){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        warehouse.setUpdDate(sdf1.format(date));

        typeMapper.updateWarehouse(warehouse);
        warehouse = typeMapper.findWarehouseByWarehouseId(warehouse);
        return warehouse;
    }

    /**倉庫を削除する*/
    public void deleteWarehouse(Warehouse warehouse){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        warehouse.setUpdDate(sdf1.format(date));
        warehouse.setOutputDelFlg("1");
        typeMapper.deleteWarehouse(warehouse);

    }

    /**倉庫を追加する*/
    public Warehouse addWarehouse(){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        Warehouse warehouse=new Warehouse();
        warehouse.setUpdDate(sdf1.format(date));
        warehouse.setTrkDate(sdf1.format(date));
        warehouse.setOutputDelFlg("0");
        warehouse.setWarehouseName("");
        warehouse.setUserGroupName("");

        typeMapper.insertWarehouse(warehouse);
        int tempId=typeMapper.getLastInsertWarehouseId();
        warehouse.setWarehouseId(tempId);
        return warehouse;
    }
}
