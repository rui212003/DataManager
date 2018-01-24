package co.jp.dm.controller;

import co.jp.dm.entity.*;
import co.jp.dm.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */

@Controller
@RequestMapping("/stock")
public class StockListController {


    @Autowired
    TypeService typeService;

    @Autowired
    StockListService stockListService;

    @Autowired
    GoodsListService goodsListService;

    @Autowired
    HistoryValveService historyValveService;


    /**
     * 商品分類ページを遷移する
     *
     * @return  String 商品大分類を取得
     *
     * */
    @RequestMapping(value="/toStockPage", method=RequestMethod.GET , produces = "text/html;charset=UTF-8")
    public String toStockPage(HttpSession session, ModelMap modelMap,HttpServletRequest request) throws IOException {
        //
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Config.LoginSession;
        } else {

            //商品大分類を取得

            List<Bigtype> bigtypeList=typeService.getAllBigtype();

            List<Warehouse> warehouseList=typeService.getAllWarehouse();

            session.setAttribute("stockBigtypeList", bigtypeList);
            session.setAttribute("stockBigtypeList", bigtypeList);
            modelMap.addAttribute("stockWarehouseList",warehouseList);
            modelMap.addAttribute("stockWarehouseList",warehouseList);

            //historyテーブルを更新
            historyValveService.addHistoryValve(Config.TStockList,"",Config.TMaster,session,request);

            return "/stock/stockList";

        }
    }


    /**
     * 在庫データを取得
     * */
    @RequestMapping(value="/getStockListByKeywords",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getStockListByKeywords(@RequestParam("bigtypeValue")String bigtypeValue,@RequestParam("middletypeValue")String middletypeValue,@RequestParam("smalltypeValue")String smalltypeValue,@RequestParam("wareHouseValue")String wareHouseValue,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Gson gson=new Gson();

        StockList stockList=new StockList();
        stockList.setStockBigtypeId(Integer.valueOf(bigtypeValue));
        stockList.setStockMiddletypeId(Integer.valueOf(middletypeValue));
        stockList.setStocksSmalltypeId(Integer.valueOf(smalltypeValue));
        stockList.setStockWarehouseId(Integer.valueOf(wareHouseValue));


        stockList.setStockDelFlg("0");


        List<StockList> stocksLists=stockListService.getAllStockListBykeyword(stockList);

        //在庫データに情報を追加する
        if(stocksLists!=null){
            for(int nIndex=0;nIndex<stocksLists.size();nIndex++){
                //商品情報を追加する
                GoodsList  goodsListTemp=goodsListService.getGoodsLisById(stocksLists.get(nIndex).getGoodsListId());
                stocksLists.get(nIndex).setGoodsList(goodsListTemp);
                //在庫情報を追加する
                Warehouse warehouseTemp=typeService.getWarehouseByWarehouseId(stocksLists.get(nIndex).getStockWarehouseId());
                stocksLists.get(nIndex).setWarehouse(warehouseTemp);

            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TStockList,session,request);

        session.setAttribute("masterStockLists", stocksLists);

        return gson.toJson(stocksLists);

    }


}
