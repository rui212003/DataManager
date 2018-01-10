package co.jp.dm.dao;


import co.jp.dm.entity.GoodsList;
import co.jp.dm.entity.InputList;

import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */
public interface GoodsListMapper {

    /**入庫データを抽出する*/
    public GoodsList findGoodsListById(GoodsList goodsList);


}
