package co.jp.dm.dao;


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

}
