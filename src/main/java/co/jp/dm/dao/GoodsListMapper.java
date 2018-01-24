package co.jp.dm.dao;


import co.jp.dm.entity.Client;
import co.jp.dm.entity.GoodsList;
import co.jp.dm.entity.InputList;

import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */
public interface GoodsListMapper {

    /**商品IDからデータを抽出する*/
    public GoodsList findGoodsListById(GoodsList goodsList);

    /**商品バーコードからデータを抽出する*/
    public GoodsList findGoodsListByBarcode(GoodsList goodsList);

    /**商品　全部データを取得*/
    public List<GoodsList> findAllGoodsListByType(GoodsList goodsList);

    /**商品　を更新する*/
    public void updateGoodsList(GoodsList goodsList);

    /**商品　を削除する*/
    public void deleteGoodsList(GoodsList goodsList);

    /**商品　を追加する*/
    public void insertGoodsList(GoodsList goodsList);

    /**商品ID　を取得する*/
    public int getLastInsertGoodsListId();

}
