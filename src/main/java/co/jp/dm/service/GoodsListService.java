package co.jp.dm.service;

import co.jp.dm.dao.GoodsListMapper;
import co.jp.dm.dao.InputMapper;
import co.jp.dm.entity.Client;
import co.jp.dm.entity.GoodsList;
import co.jp.dm.entity.InputList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */
@Service
public class GoodsListService {


    @Resource
    GoodsListMapper goodsListMapper;

    /**商品IDからデータを抽出する*/
    public GoodsList getGoodsLisById(Integer goodsListId){
        GoodsList goodsLists=new GoodsList();
        goodsLists.setGoodsListId(goodsListId);
        goodsLists.setOutputDelFlg("0");

        goodsLists = goodsListMapper.findGoodsListById(goodsLists);
        return goodsLists;
    }


    /**商品バーコードからデータを抽出する*/
    public GoodsList getGoodsLisByBarcode(String BarcodeNum){
        GoodsList goodsLists=new GoodsList();
        goodsLists.setGoodsBarcode(BarcodeNum);
        goodsLists.setOutputDelFlg("0");

        goodsLists = goodsListMapper.findGoodsListByBarcode(goodsLists);
        return goodsLists;
    }

    /**Typeからデータを抽出する*/
    public List<GoodsList> getAllGoodsListByType(GoodsList goodsList){
        List<GoodsList> goodsLists=new ArrayList<GoodsList>();
        goodsLists = goodsListMapper.findAllGoodsListByType(goodsList);
        return goodsLists;
    }


    /**商品を修正する*/
    public GoodsList updateGoodsList(GoodsList goodsList){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        goodsList.setUpdDate(sdf1.format(date));

        goodsListMapper.updateGoodsList(goodsList);
        goodsList = goodsListMapper.findGoodsListById(goodsList);
        return goodsList;
    }

    /**商品を削除する*/
    public void deleteGoodsList(GoodsList goodsList){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        goodsList.setUpdDate(sdf1.format(date));
        goodsList.setOutputDelFlg("1");
        goodsListMapper.deleteGoodsList(goodsList);

    }

    /**商品を追加する*/
    public GoodsList addGoodsList(GoodsList goodsList){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");


        goodsList.setUpdDate(sdf1.format(date));
        goodsList.setTrkDate(sdf1.format(date));
        goodsList.setOutputDelFlg("0");
        goodsList.setGoodsListName("");
        goodsList.setGoodsBarcode("");
        goodsList.setGoodsPriceJP(0.0);
        goodsList.setGoodsPriceCH(0.0);
        goodsList.setGoodsUnitName("");

        goodsListMapper.insertGoodsList(goodsList);
        int tempId=goodsListMapper.getLastInsertGoodsListId();
        goodsList.setGoodsListId(tempId);
        return goodsList;
    }
}
