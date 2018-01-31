package co.jp.dm.controller;

import co.jp.dm.entity.*;
import co.jp.dm.service.GoodsListService;
import co.jp.dm.service.HistoryValveService;
import co.jp.dm.service.InputService;
import co.jp.dm.service.TypeService;
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
    TypeService typeService;

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

    /**
     * バーコードから商品情報を取得
     * */
    @RequestMapping(value="/getGoodListByBarcode",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getGoodListByBarcode(@RequestParam("inputBarcode")String inputBarcode,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Gson gson=new Gson();

        InputList inputListTemp=new InputList();

        //バーコード番号から商品情報を取得
        GoodsList goodsList=goodsListService.getGoodsLisByBarcode(inputBarcode);

        if(goodsList!=null){
            inputListTemp.setGoodsList(goodsList);
            inputListTemp.setGoodsListId(goodsList.getGoodsListId());


            //大分類の文字を取得
            Bigtype bittypetemp=typeService.getBigTypeByBigtypeId(goodsList.getGoodsBigtypeId());
            inputListTemp.setInputBigtypeName(bittypetemp.getBigtypeName());
            inputListTemp.setInputBigtypeId(bittypetemp.getBigtypeId());

            //中分類の文字を取得
            Middletype middletype=typeService.getMiddleTypeBytypeId(goodsList.getGoodsMiddletypeId());
            inputListTemp.setInputMiddletypeName(middletype.getMiddletypeName());
            inputListTemp.setInputMiddletypeId(middletype.getMiddletypeId());

            //小分類の文字を取得
            Smalltype smalltype=typeService.getSmallTypeBytypeId(goodsList.getGoodsSmalltypeId());
            inputListTemp.setInputSmalltypeName(smalltype.getSmalltypeName());
            inputListTemp.setInputSmalltypeId(smalltype.getSmalltypeId());

            //historyテーブルを更新
            historyValveService.addHistoryValve(Config.TInputList,"バーコードから商品取得","",session,request);

            return gson.toJson(inputListTemp);
        }else{

            //historyテーブルを更新
            historyValveService.addHistoryValve(Config.TInputList,"バーコードから商品取得できない","",session,request);
            return gson.toJson("login");
        }

    }


    /**
     * 出庫バーコードから商品情報を取得
     * */
    @RequestMapping(value="/getGoodListByBarcodeOutput",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getGoodListByBarcodeOutput(@RequestParam("outputBarcode")String outputBarcode,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Gson gson=new Gson();

        OutputList outputListTemp=new OutputList();

        //バーコード番号から商品情報を取得
        GoodsList goodsList=goodsListService.getGoodsLisByBarcode(outputBarcode);

        if(goodsList!=null){
            outputListTemp.setGoodsList(goodsList);
            outputListTemp.setGoodsListId(goodsList.getGoodsListId());

            //大分類の文字を取得
            Bigtype bittypetemp=typeService.getBigTypeByBigtypeId(goodsList.getGoodsBigtypeId());
            outputListTemp.setOutputBigtypeName(bittypetemp.getBigtypeName());
            outputListTemp.setOutputBigtypeId(bittypetemp.getBigtypeId());

            //中分類の文字を取得
            Middletype middletype=typeService.getMiddleTypeBytypeId(goodsList.getGoodsMiddletypeId());
            outputListTemp.setOutputMiddletypeName(middletype.getMiddletypeName());
            outputListTemp.setOutputMiddletypeId(middletype.getMiddletypeId());

            //小分類の文字を取得
            Smalltype smalltype=typeService.getSmallTypeBytypeId(goodsList.getGoodsSmalltypeId());
            outputListTemp.setOutputSmalltypeName(smalltype.getSmalltypeName());
            outputListTemp.setOutputSmalltypeId(smalltype.getSmalltypeId());

            //historyテーブルを更新
            historyValveService.addHistoryValve(Config.TOutputList,"バーコードから商品取得出庫","",session,request);

            return gson.toJson(outputListTemp);
        }else{

            //historyテーブルを更新
            historyValveService.addHistoryValve(Config.TOutputList,"バーコードから商品取得できない出庫","",session,request);
            return gson.toJson("login");
        }

    }


}
