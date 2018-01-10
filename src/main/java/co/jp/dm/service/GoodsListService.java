package co.jp.dm.service;

import co.jp.dm.dao.GoodsListMapper;
import co.jp.dm.dao.InputMapper;
import co.jp.dm.entity.GoodsList;
import co.jp.dm.entity.InputList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */
@Service
public class GoodsListService {


    @Resource
    GoodsListMapper goodsListMapper;

    /**入庫データを抽出する*/
    public GoodsList getGoodsLisById(Integer goodsListId){
        GoodsList goodsLists=new GoodsList();
        goodsLists.setGoodsListId(goodsListId);
        goodsLists.setOutputDelFlg("0");

        goodsLists = goodsListMapper.findGoodsListById(goodsLists);
        return goodsLists;
    }


}
