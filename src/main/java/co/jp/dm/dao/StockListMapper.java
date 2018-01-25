package co.jp.dm.dao;


import co.jp.dm.entity.Client;
import co.jp.dm.entity.StockList;

import java.util.List;

/**
 * Created by rui on 2018/1/24.
 */
public interface StockListMapper {

    //*******************************************
    //***************　在庫 **********************
    //*******************************************

    /**在庫　全部データを取得*/
    public List<StockList> findAllStockListBykeyword(StockList stockList);

    /**在庫　全部データを取得*/
    public StockList findStockListByGoodsIdAndWarehouseId(StockList stockList);

    /**在庫　全部データを更新　複数*/
    public void updateStockNum(List<StockList> stockLists);

    /**在庫　全部データを更新*/
    public void updateStockNumOneByOne(StockList stockLists);

}
