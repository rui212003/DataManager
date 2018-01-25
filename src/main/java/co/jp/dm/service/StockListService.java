package co.jp.dm.service;

import co.jp.dm.dao.ClientMapper;
import co.jp.dm.dao.StockListMapper;
import co.jp.dm.entity.Client;
import co.jp.dm.entity.StockList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by rui on 2018/1/24.
 */
@Service
public class StockListService {


    @Resource
    StockListMapper stockListMapper;

    /**在庫データを抽出する*/
    public List<StockList> getAllStockListBykeyword(StockList stockList){

        List<StockList> stockLists=stockListMapper.findAllStockListBykeyword(stockList);

        return stockLists;
    }

    /**在庫データを抽出する*/
    public StockList getStockListByGoodsIdAndWarehouseId(StockList stockList){

        stockList=stockListMapper.findStockListByGoodsIdAndWarehouseId(stockList);

        return stockList;
    }


    /**在庫データを更新する 複数*/
    public void updateStockNum(List<StockList> stockLists){

        stockListMapper.updateStockNum(stockLists);

    }

    /**在庫データを更新する */
    public void updateStockNumOneByOne(StockList stockLists){
        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        stockLists.setUpdDate(sdf1.format(date));

        stockListMapper.updateStockNumOneByOne(stockLists);

    }

}
