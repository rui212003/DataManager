package co.jp.dm.dao;


import co.jp.dm.entity.*;

import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */
public interface TypeMapper {

    /**大分類を取得*/
    public List<Bigtype> findAllBigtype();

    /**商品単位を取得*/
    public List<GoodsUnit> findAllGoodsUnit();

    /**大分類から中分類を取得*/
    public List<Middletype> findMiddleTypeByBigtype(Middletype middletype);

    /**大分類、中分類から小分類を取得*/
    public List<Smalltype> findSmallTypeByBigtype(Smalltype smalltype);

    /**大分類　名称を取得*/
    public Bigtype findBigTypeByBigtypeId(Bigtype bigtype);

    /**中分類　名称を取得*/
    public Middletype findMiddleTypeByMiddletypeId(Middletype middletype);

    /**小分類　名称を取得*/
    public Smalltype findSmallTypeBySmalltypeId(Smalltype smalltype);


    //*******************************************
    //***************大分類**********************
    //*******************************************


    /**大分類　を更新する*/
    public void updateBigtypeDateBybigtype(Bigtype bigtype);

    /**大分類　を削除する*/
    public void deleteBigTypeByBigtype(Bigtype bigtype);

    /**大分類　を追加する*/
    public void insertBigType(Bigtype bigtype);

    /**大分類ID　を取得する*/
    public int getLastInsertBigTypeId();

    //*******************************************
    //***************中分類**********************
    //*******************************************


    /**大分類　を更新する*/
    public void updateMiddletypeDateByMiddletype(Middletype middletype);

    /**大分類　を削除する*/
    public void deleteMiddleTypeByMiddletype(Middletype middletype);

    /**大分類　を追加する*/
    public void insertMiddleType(Middletype middletype);

    /**大分類ID　を取得する*/
    public int getLastInsertMiddleTypeId();

    //*******************************************
    //***************小分類**********************
    //*******************************************


    /**小分類　を更新する*/
    public void updateSmalltypeDateBySmalltype(Smalltype smalltype);

    /**小分類　を削除する*/
    public void deleteSmallTypeBySmalltype(Smalltype smalltype);

    /**小分類　を追加する*/
    public void insertSmallType(Smalltype smalltype);

    /**小分類ID　を取得する*/
    public int getLastInsertSmallTypeId();

    //*******************************************
    //***************　単位 **********************
    //*******************************************

    /**大分類　名称を取得*/
    public GoodsUnit findGoodUnitByGoodUnitId(GoodsUnit goodsUnit);

    /**大分類　を更新する*/
    public void updateGoodsUnit(GoodsUnit goodsUnit);

    /**大分類　を削除する*/
    public void deleteGoodsUnit(GoodsUnit goodsUnit);

    /**大分類　を追加する*/
    public void insertGoodsUnit(GoodsUnit goodsUnit);

    /**大分類ID　を取得する*/
    public int getLastInsertGoodsUnitId();

    //*******************************************
    //***************　倉庫 **********************
    //*******************************************

    /**倉庫　全部データを取得*/
    public List<Warehouse> findAllWarehouse();

    /**倉庫　名称を取得*/
    public Warehouse findWarehouseByWarehouseId(Warehouse warehouse);

    /**倉庫　を更新する*/
    public void updateWarehouse(Warehouse warehouse);

    /**倉庫　を削除する*/
    public void deleteWarehouse(Warehouse warehouse);

    /**倉庫　を追加する*/
    public void insertWarehouse(Warehouse warehouse);

    /**倉庫ID　を取得する*/
    public int getLastInsertWarehouseId();



}
