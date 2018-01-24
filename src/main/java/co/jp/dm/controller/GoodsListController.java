package co.jp.dm.controller;

import co.jp.dm.entity.*;
import co.jp.dm.service.GoodsListService;
import co.jp.dm.service.HistoryValveService;
import co.jp.dm.service.InputService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */

@Controller
@RequestMapping("/goodsList")
public class GoodsListController {


    @Autowired
    InputService inputService;

    @Autowired
    GoodsListService goodsListService;

    @Autowired
    HistoryValveService historyValveService;


    /**
     * を取得
     * */
    @RequestMapping(value="/getGoodsListByKeywords",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getGoodsListByKeywords(@RequestParam("bigtypeValue")String bigtypeValue,@RequestParam("middletypeValue")String middletypeValue,@RequestParam("smalltypeValue")String smalltypeValue,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Gson gson=new Gson();

        GoodsList goodsList=new GoodsList();
        goodsList.setGoodsBigtypeId(Integer.valueOf(bigtypeValue));
        goodsList.setGoodsMiddletypeId(Integer.valueOf(middletypeValue));
        goodsList.setGoodsSmalltypeId(Integer.valueOf(smalltypeValue));
        goodsList.setOutputDelFlg("0");

        List<GoodsList> goodsLists=goodsListService.getAllGoodsListByType(goodsList);


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TGoodsList,session,request);

        session.setAttribute("masterGoodsLists", goodsLists);
        session.setAttribute("masterGoodsListSearchBigtype", bigtypeValue);
        session.setAttribute("masterGoodsListSearchMiddletype", middletypeValue);
        session.setAttribute("masterGoodsListSearchSmalltype", smalltypeValue);

        return gson.toJson(goodsLists);

    }

    /**
     * 顧客データを更新する
     * */
    @RequestMapping(value="/updateGoodsList",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateGoodsList(@RequestParam("goodsListId")String goodsListId,@RequestParam("goodsListName")String goodsListName,@RequestParam("goodsBarcode")String goodsBarcode,@RequestParam("goodsPriceJP")String goodsPriceJP,@RequestParam("goodsPriceCH")String goodsPriceCH,@RequestParam("goodsUnitName")String goodsUnitName,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        GoodsList goodsList=new GoodsList();
        goodsList.setGoodsListId(Integer.valueOf(goodsListId));
        goodsList.setGoodsListName(goodsListName);
        goodsList.setGoodsBarcode(goodsBarcode);
        goodsList.setGoodsPriceJP(Double.valueOf(goodsPriceJP));
        goodsList.setGoodsPriceCH(Double.valueOf(goodsPriceCH));
        goodsList.setGoodsUnitName(goodsUnitName);


        goodsList=goodsListService.updateGoodsList(goodsList);

        //session更新
        List<GoodsList> goodsLists=( List<GoodsList>)session.getAttribute("masterGoodsLists");
        List<GoodsList> goodsListsNew=new ArrayList<GoodsList>();
        if(goodsLists!=null){
            for(int nIndex=0;nIndex<goodsLists.size();nIndex++){
                String temp=goodsLists.get(nIndex).getGoodsListId()+"";
                if(goodsListId.equals(temp)){
                    goodsListsNew.add(goodsList);
                }else{
                    goodsListsNew.add(goodsLists.get(nIndex));
                }
            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TGoodsList,session,request);

        session.setAttribute("masterGoodsLists", goodsListsNew);
        return "true";

    }

    /**
     * 顧客データを削除する
     * */
    @RequestMapping(value="/deleteGoodsList",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteGoodsList(@RequestParam("goodsListId")String goodsListId,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        GoodsList goodsList=new GoodsList();
        goodsList.setGoodsListId(Integer.valueOf(goodsListId));
        goodsListService.deleteGoodsList(goodsList);

        //session更新
        List<GoodsList> goodsLists=( List<GoodsList>)session.getAttribute("masterGoodsLists");
        List<GoodsList> goodsListsNew=new ArrayList<GoodsList>();
        if(goodsLists!=null){
            for(int nIndex=0;nIndex<goodsLists.size();nIndex++){
                String temp=goodsLists.get(nIndex).getGoodsListId()+"";
                if(goodsListId.equals(temp)){
                    System.out.println("eee");
                }else{
                    goodsListsNew.add(goodsLists.get(nIndex));
                }
            }
        }


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TGoodsList,session,request);

        session.setAttribute("masterGoodsLists", goodsListsNew);

        Gson gson=new Gson();

        return gson.toJson(goodsListsNew);

    }


    /**
     * 顧客データを追加する
     * */
    @RequestMapping(value="/addGoodsList",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addGoodsList(@RequestParam("bigtypeValue")String bigtypeValue,@RequestParam("middletypeValue")String middletypeValue,@RequestParam("smalltypeValue")String smalltypeValue,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        GoodsList goodsList=new GoodsList();
        goodsList.setGoodsBigtypeId(Integer.valueOf(bigtypeValue));
        goodsList.setGoodsMiddletypeId(Integer.valueOf(middletypeValue));
        goodsList.setGoodsSmalltypeId(Integer.valueOf(smalltypeValue));
        goodsList=goodsListService.addGoodsList(goodsList);
        List<GoodsList> goodsLists=goodsListService.getAllGoodsListByType(goodsList);


        session.setAttribute("masterGoodsLists", goodsLists);


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TGoodsList,session,request);

        Gson gson=new Gson();

        return gson.toJson(goodsLists);

    }

}
