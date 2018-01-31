package co.jp.dm.controller;

import co.jp.dm.dto.InputListForm;
import co.jp.dm.entity.*;
import co.jp.dm.service.*;
import com.google.gson.Gson;
import com.sun.corba.se.spi.orbutil.fsm.Input;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */

@Controller
@RequestMapping("/input")
public class InputController {


    @Autowired
    InputService inputService;

    @Autowired
    GoodsListService goodsListService;

    @Autowired
    TypeService typeService;

    @Autowired
    StockListService stockListService;

    @Autowired
    HistoryValveService historyValveService;

    /**
     * 大分類から中分類を取得
     * */
    @RequestMapping(value="/getGoodsListByKeywords",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getGoodsListByKeywords(@RequestParam("bigtypeValue")String bigtypeValue,@RequestParam("middletypeValue")String middletypeValue,@RequestParam("smalltypeValue")String smalltypeValue,@RequestParam("startDate")String startDate,@RequestParam("endDate")String endDate,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Gson gson=new Gson();

        InputList inputList=new InputList();
        inputList.setInputBigtypeId(Integer.valueOf(bigtypeValue));
        inputList.setInputMiddletypeId(Integer.valueOf(middletypeValue));
        inputList.setInputSmalltypeId(Integer.valueOf(smalltypeValue));
        inputList.setInputDelFlg("0");
        inputList.setTrkDate(startDate);
        inputList.setUpdDate(endDate);


        List<InputList> inputLists=inputService.getInputList(inputList);
        GoodsList goodsList=new GoodsList();

        int inputSearchData_num=0;
        //入庫データに商品情報を追加する
        if(inputLists!=null){
            for(int nIndex=0;nIndex<inputLists.size();nIndex++){
                GoodsList  goodsListTemp=goodsListService.getGoodsLisById(inputLists.get(nIndex).getGoodsListId());
                goodsList=goodsListTemp;
                inputLists.get(nIndex).setGoodsList(goodsListTemp);
            }
            inputSearchData_num=inputLists.size();
        }


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TInputList,session,request);

        session.setAttribute("inputLists", inputLists);
        session.setAttribute("inputSearchBigtype", bigtypeValue);
        session.setAttribute("inputSearchMiddletype", middletypeValue);
        session.setAttribute("inputSearchSmalltype", smalltypeValue);
        session.setAttribute("inputSearchStartDate", startDate);
        session.setAttribute("inputSearchEndDate", endDate);
        session.setAttribute("inputSearchData_num", inputSearchData_num);

        return gson.toJson(inputLists);

    }


    /**
     * 入庫データを更新する
     * */
    @RequestMapping(value="/updateInput",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateInput(@RequestParam("inputDataId")String inputDataId,@RequestParam("tmpInputNum")String tmpInputNum,@RequestParam("tmpInputDiscount")String tmpInputDiscount,@RequestParam("tmpInputDate")String tmpInputDate,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        //現在在庫数量を更新する
        InputList inputListOld=new InputList();
        inputListOld.setInputListId(Integer.valueOf(inputDataId));
        inputListOld=inputService.getInputListById(inputListOld);


         //入荷データを更新する
        InputList inputList=new InputList();
        inputList.setInputListId(Integer.valueOf(inputDataId));
        inputList.setInputNum(Integer.valueOf(tmpInputNum));
        inputList.setInputDiscount(Double.valueOf(tmpInputDiscount));
        inputList.setInputDate(tmpInputDate);
        inputList=inputService.updateInputdata(inputList);

        //在庫数量を更新する
        Integer numStock=Integer.valueOf(tmpInputNum)-inputListOld.getInputNum();
        StockList stockListTemp=new StockList();
        stockListTemp.setGoodsListId(inputList.getGoodsListId());
        stockListTemp.setStockputNum(numStock);
        stockListTemp.setStockWarehouseId(inputList.getWarehouseId());
        stockListService.updateStockNumOneByOne(stockListTemp);

        //session更新
        List<InputList> inputLists=( List<InputList>)session.getAttribute("inputLists");
        List<InputList> inputListsNew=new ArrayList<InputList>();
        if(inputLists!=null){
            for(int nIndex=0;nIndex<inputLists.size();nIndex++){
              if(inputLists.get(nIndex).getInputListId()+""==inputDataId){
                  inputListsNew.add(inputList);
              }else{
                  inputListsNew.add(inputLists.get(nIndex));
              }
            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TInputList,session,request);

        session.setAttribute("inputLists", inputListsNew);
        return "true";

    }

    /**
     * 入庫データを更新する
     * */
    @RequestMapping(value="/deleteInput",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteInput(@RequestParam("inputDataId")String inputDataId,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        //現在在庫数量を更新する
        InputList inputListOld=new InputList();
        inputListOld.setInputListId(Integer.valueOf(inputDataId));
        inputListOld=inputService.getInputListById(inputListOld);


        InputList inputList=new InputList();
        inputList.setInputListId(Integer.valueOf(inputDataId));
        inputService.deteleInputdata(inputList);

        //在庫数量を更新する
        Integer numStock=0-inputListOld.getInputNum();
        StockList stockListTemp=new StockList();
        stockListTemp.setGoodsListId(inputListOld.getGoodsListId());
        stockListTemp.setStockputNum(numStock);
        stockListTemp.setStockWarehouseId(inputListOld.getWarehouseId());
        stockListService.updateStockNumOneByOne(stockListTemp);

        //session更新
        List<InputList> inputLists=( List<InputList>)session.getAttribute("inputLists");
        List<InputList> inputListsNew=new ArrayList<InputList>();
        if(inputLists!=null){
            for(int nIndex=0;nIndex<inputLists.size();nIndex++){
                if(inputLists.get(nIndex).getInputListId()+""==inputDataId){
                    inputListsNew.add(inputList);
                }else{
                    inputListsNew.add(inputLists.get(nIndex));
                }
            }
        }

        int inputSearchData_num=0;
        if(inputListsNew!=null){
            inputSearchData_num=inputListsNew.size();
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TInputList,session,request);

        session.setAttribute("inputLists", inputListsNew);
        session.setAttribute("inputSearchData_num", inputSearchData_num);
        return "true";

    }


    /**
     * 入庫追加ページへ遷移します
     * */
    @RequestMapping(value="/toAddPage", method=RequestMethod.GET , produces = "text/html;charset=UTF-8")
    public String toAddPage(HttpSession session,HttpServletRequest request){

        //
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Config.LoginSession;
        } else {


            //historyテーブルを更新
            historyValveService.addHistoryValve("","",Config.TLogin,session,request);

            return "input/inputAdd";
        }
    }

    /**
     * ファイルから入庫データを追加する
     * */
    @RequestMapping(value="/addInputFile",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addInputFile(@RequestParam("inputfileName")String inputfileName,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        //追加用
        List<InputList> inputListNew=new ArrayList<InputList>();
        List<StockList> stockListsNew=new ArrayList<StockList>();

        String tempTrackNum="";

        //Fileから
        if(inputfileName.length()>0){
            String[] vids = inputfileName.split("\\r\\n");
            if(vids.length>0){
                for(int i = 0;i<vids.length;i++){
                    String[] inputtemp =vids[i].split("\\t");
                    if(inputtemp.length==4){

                        if("NW7".equals(inputtemp[2])){
                        //追跡番号の場合
                            tempTrackNum=inputtemp[0];
                        }else{
                            //商品の場合
                            InputList inputListTemp=new InputList();
                            inputListTemp.setInputTrackNum(tempTrackNum);
                            inputListTemp.setInputDate(inputtemp[1]);
                            //バーコード番号から商品情報を取得

                            GoodsList goodsList=goodsListService.getGoodsLisByBarcode(inputtemp[0]);
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

                            //割引を１に設定する
                            inputListTemp.setInputDiscount(100.0);

                            //数量を1に設定する
                            inputListTemp.setInputNum(1);
                            //削除フラグを0に設定する
                            inputListTemp.setInputDelFlg("0");

                            //倉庫を設定する
                            if(inputtemp[3]==""){
                                //デフォルトで一番の倉庫にする
                                inputListTemp.setWarehouseId(1);
                            }else{
                                inputListTemp.setWarehouseId(Integer.valueOf(inputtemp[3]));
                            }


                            //append Date
                            Date date = new Date();
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                            inputListTemp.setTrkDate(sdf1.format(date));
                            inputListTemp.setUpdDate(sdf1.format(date));

                            inputListNew.add(inputListTemp);

                            //在庫更新

                            //現在在庫数量を更新する
                            StockList stockListOldTemp=new StockList();
                            stockListOldTemp.setGoodsListId(goodsList.getGoodsListId());
                            stockListOldTemp.setStockWarehouseId(inputListTemp.getWarehouseId());
                            stockListOldTemp=stockListService.getStockListByGoodsIdAndWarehouseId(stockListOldTemp);

                            //在庫数
//                            Integer tempStock=stockListOldTemp.getStockputNum()+1;
                            StockList stockListTemp=new StockList();
                            stockListTemp.setGoodsListId(goodsList.getGoodsListId());
                            stockListTemp.setStockputNum(1);
                            stockListTemp.setStockWarehouseId(inputListTemp.getWarehouseId());

                            stockListsNew.add(stockListTemp);
                        }

                    }
                }
            }
        }

        //DBに保存する

        inputListNew=inputService.insertInputDataByLists(inputListNew);
//        stockListService.updateStockNum(stockListsNew);
        if(stockListsNew!=null){
            for(int nIndex=0;nIndex<stockListsNew.size();nIndex++){
                stockListService.updateStockNumOneByOne(stockListsNew.get(nIndex));
            }
        }



        int inputListNew_num=0;
        //入庫データに商品情報を追加する


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TInputList,session,request);

        session.setAttribute("inputListNew", inputListNew);
        session.setAttribute("inputListNew_num", inputListNew_num);

        Gson gson=new Gson();
        if(inputListNew!=null){
            return gson.toJson(inputListNew);
        }else{
            return "true";
        }


    }

    /**
     * 入庫追加ページ(単体)へ遷移しますを追加する
     * */
    @RequestMapping(value="/AddInputOne", method=RequestMethod.POST , produces = "text/html;charset=UTF-8")
    public String AddInputOne(@ModelAttribute("InputListForm")InputListForm inputListForm,HttpSession session,HttpServletRequest request){

        //
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Config.LoginSession;
        } else {


            //追加用
            List<InputList> inputListNew=new ArrayList<InputList>();
            List<StockList> stockListsNew=new ArrayList<StockList>();

            //商品の場合
            InputList inputListTemp=new InputList();
            inputListTemp.setInputTrackNum(inputListForm.getInputTrackNum());
            //バーコード番号から商品情報を取得
            GoodsList goodsList=goodsListService.getGoodsLisByBarcode(inputListForm.getGoodsBarcode());
            inputListTemp.setGoodsList(goodsList);
            inputListTemp.setGoodsListId(goodsList.getGoodsListId());

            //大分類の文字を取得
            Bigtype bittypetemp=typeService.getBigTypeByBigtypeId(inputListForm.getInputBigtypeId());
            inputListTemp.setInputBigtypeName(bittypetemp.getBigtypeName());
            inputListTemp.setInputBigtypeId(bittypetemp.getBigtypeId());

            //中分類の文字を取得
            Middletype middletype=typeService.getMiddleTypeBytypeId(inputListForm.getInputMiddletypeId());
            inputListTemp.setInputMiddletypeName(middletype.getMiddletypeName());
            inputListTemp.setInputMiddletypeId(middletype.getMiddletypeId());

            //小分類の文字を取得
            Smalltype smalltype=typeService.getSmallTypeBytypeId(inputListForm.getInputSmalltypeId());
            inputListTemp.setInputSmalltypeName(smalltype.getSmalltypeName());
            inputListTemp.setInputSmalltypeId(smalltype.getSmalltypeId());

            //割引を１に設定する
            inputListTemp.setInputDiscount(inputListForm.getInputDiscount());

            //数量を1に設定する
            inputListTemp.setInputNum(inputListForm.getInputNum());
            //削除フラグを0に設定する
            inputListTemp.setInputDelFlg("0");

            //倉庫を設定する
            inputListTemp.setWarehouseId(inputListForm.getWarehouseId());


            //append Date
            Date date = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            inputListTemp.setTrkDate(sdf1.format(date));
            inputListTemp.setUpdDate(sdf1.format(date));
            inputListTemp.setInputDate(sdf1.format(date));

            inputListNew.add(inputListTemp);

            //在庫更新

            //現在在庫数量を更新する
            StockList stockListOldTemp=new StockList();
            stockListOldTemp.setGoodsListId(goodsList.getGoodsListId());
            stockListOldTemp.setStockWarehouseId(inputListTemp.getWarehouseId());
            stockListOldTemp=stockListService.getStockListByGoodsIdAndWarehouseId(stockListOldTemp);

            //在庫数
            Integer tempStock=stockListOldTemp.getStockputNum()+inputListForm.getInputNum();
            StockList stockListTemp=new StockList();
            stockListTemp.setGoodsListId(goodsList.getGoodsListId());
            stockListTemp.setStockputNum(inputListForm.getInputNum());
            stockListTemp.setStockWarehouseId(inputListTemp.getWarehouseId());

            stockListsNew.add(stockListTemp);

            //DB更新
            inputService.insertInputDataByLists(inputListNew);
            if(stockListsNew!=null){
                for(int nIndex=0;nIndex<stockListsNew.size();nIndex++){
                    stockListService.updateStockNumOneByOne(stockListsNew.get(nIndex));
                }
            }


            //historyテーブルを更新
            historyValveService.addHistoryValve(user.getUserName(),Config.TLogin,"単体新規入庫追加",session,request);

            session.setAttribute("inputAddOne", inputListTemp);
            return "redirect:/input/toAddPageOne";
        }
    }

    /**
     * 入庫追加ページ(単体)へ遷移します
     * */
    @RequestMapping(value="/toAddPageOne", method=RequestMethod.GET , produces = "text/html;charset=UTF-8")
    public String toAddPageOne(HttpSession session,HttpServletRequest request){

        //
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Config.LoginSession;
        } else {
            InputList inputListTemp=new InputList();
            inputListTemp.setInputDelFlg("0");
            inputListTemp.setInputNum(1);

            GoodsList goodsListTemp=new GoodsList();
            inputListTemp.setGoodsList(goodsListTemp);

            List<Warehouse> warehouseList=typeService.getAllWarehouse();

            session.setAttribute("inputAddOne", inputListTemp);
            session.setAttribute("inputAddWarehouseList", warehouseList);
            //historyテーブルを更新
            historyValveService.addHistoryValve("","",Config.TLogin,session,request);

            return "input/inputAddOne";
        }
    }


}
