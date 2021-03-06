package co.jp.dm.controller;

import co.jp.dm.dto.InputListForm;
import co.jp.dm.dto.OutputListForm;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */

@Controller
@RequestMapping("/output")
public class OutputController {


    @Autowired
    OutputService outputService;

    @Autowired
    GoodsListService goodsListService;

    @Autowired
    TypeService typeService;

    @Autowired
    StockListService stockListService;

    @Autowired
    HistoryValveService historyValveService;


    /**
     * ログイン後のTopページ
     *
     * @return  String 商品大分類を取得
     *
     * */
    @RequestMapping(value="/toOutputList", method=RequestMethod.GET , produces = "text/html;charset=UTF-8")
    public String toOutputList(HttpSession session, ModelMap modelMap,HttpServletRequest request) throws IOException {
        //
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Config.LoginSession;
        } else {

            //商品大分類を取得

            List<Bigtype> bigtypeList=typeService.getAllBigtype();
            session.setAttribute("outputBigtypeList", bigtypeList);
            modelMap.addAttribute("outputBigtypeList",bigtypeList);

            //historyテーブルを更新
            historyValveService.addHistoryValve("","",Config.TOutputList,session,request);

            return "output/outputList";
        }
    }

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

        OutputList outputList=new OutputList();
        outputList.setOutputBigtypeId(Integer.valueOf(bigtypeValue));
        outputList.setOutputMiddletypeId(Integer.valueOf(middletypeValue));
        outputList.setOutputSmalltypeId(Integer.valueOf(smalltypeValue));
        outputList.setOutputDelFlg("0");
        outputList.setTrkDate(startDate);
        outputList.setUpdDate(endDate);


        List<OutputList> outputLists=outputService.getOutputList(outputList);
        GoodsList goodsList=new GoodsList();

        int outputSearchData_num=0;
        //出庫データに商品情報を追加する
        if(outputLists!=null){
            for(int nIndex=0;nIndex<outputLists.size();nIndex++){
                GoodsList  goodsListTemp=goodsListService.getGoodsLisById(outputLists.get(nIndex).getGoodsListId());
                goodsList=goodsListTemp;
                outputLists.get(nIndex).setGoodsList(goodsListTemp);
            }
            outputSearchData_num=outputLists.size();
        }


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TOutputList,session,request);

        session.setAttribute("outputLists", outputLists);
        session.setAttribute("outputSearchBigtype", bigtypeValue);
        session.setAttribute("outputSearchMiddletype", middletypeValue);
        session.setAttribute("outputSearchSmalltype", smalltypeValue);
        session.setAttribute("outputSearchStartDate", startDate);
        session.setAttribute("outputSearchEndDate", endDate);
        session.setAttribute("outputSearchData_num", outputSearchData_num);

        return gson.toJson(outputLists);

    }


    /**
     * 出庫データを更新する
     * */
    @RequestMapping(value="/updateOutput",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateOutput(@RequestParam("outputDataId")String outputDataId,@RequestParam("tmpOutputNum")String tmpOutputNum,@RequestParam("tmpOutputDiscount")String tmpOutputDiscount,@RequestParam("tmpOutputDate")String tmpOutputDate,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        //現在在庫数量を更新する
        OutputList outputListOld=new OutputList();
        outputListOld.setOutputListId(Integer.valueOf(outputDataId));
        outputListOld=outputService.getOutputListById(outputListOld);


        OutputList outputList=new OutputList();
        outputList.setOutputListId(Integer.valueOf(outputDataId));
        outputList.setOutputNum(Integer.valueOf(tmpOutputNum));
        outputList.setOutputDiscount(Double.valueOf(tmpOutputDiscount));
        outputList.setOutputDate(tmpOutputDate);
        outputList=outputService.updateOutputdata(outputList);

        //在庫数量を更新する
        Integer numStock=outputListOld.getOutputNum()-Integer.valueOf(tmpOutputNum);
        StockList stockListTemp=new StockList();
        stockListTemp.setGoodsListId(outputList.getGoodsListId());
        stockListTemp.setStockputNum(numStock);
        stockListTemp.setStockWarehouseId(outputList.getWarehouseId());
        stockListService.updateStockNumOneByOne(stockListTemp);

        //session更新
        List<OutputList> outputLists=( List<OutputList>)session.getAttribute("outputLists");
        List<OutputList> outputListsNew=new ArrayList<OutputList>();
        if(outputLists!=null){
            for(int nIndex=0;nIndex<outputLists.size();nIndex++){
              if(outputLists.get(nIndex).getOutputListId()+""==outputDataId){
                  outputListsNew.add(outputList);
              }else{
                  outputListsNew.add(outputLists.get(nIndex));
              }
            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TOutputList,session,request);

        session.setAttribute("outputLists", outputListsNew);
        return "true";

    }

    /**
     * 出庫データを更新する
     * */
    @RequestMapping(value="/deleteOutput",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteOutput(@RequestParam("outputDataId")String outputDataId,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        //現在在庫数量を更新する
        OutputList outputListOld=new OutputList();
        outputListOld.setOutputListId(Integer.valueOf(outputDataId));
        outputListOld=outputService.getOutputListById(outputListOld);


        OutputList outputList=new OutputList();
        outputList.setOutputListId(Integer.valueOf(outputDataId));
        outputService.deteleOutputdata(outputList);

        //在庫数量を更新する
        Integer numStock=outputListOld.getOutputNum();
        StockList stockListTemp=new StockList();
        stockListTemp.setGoodsListId(outputListOld.getGoodsListId());
        stockListTemp.setStockputNum(numStock);
        stockListTemp.setStockWarehouseId(outputListOld.getWarehouseId());
        stockListService.updateStockNumOneByOne(stockListTemp);

        //session更新
        List<OutputList> outputLists=( List<OutputList>)session.getAttribute("outputLists");
        List<OutputList> outputListsNew=new ArrayList<OutputList>();
        if(outputLists!=null){
            for(int nIndex=0;nIndex<outputLists.size();nIndex++){
                if(outputLists.get(nIndex).getOutputListId()+""==outputDataId){
                    outputListsNew.add(outputList);
                }else{
                    outputListsNew.add(outputLists.get(nIndex));
                }
            }
        }

        int outputSearchData_num=0;
        if(outputListsNew!=null){
            outputSearchData_num=outputListsNew.size();
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TOutputList,session,request);

        session.setAttribute("outputLists", outputListsNew);
        session.setAttribute("outputSearchData_num", outputSearchData_num);
        return "true";

    }


    /**
     * 出庫追加ページへ遷移します
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

            return "output/outputAdd";
        }
    }

    /**
     * ファイルから出庫データを追加する
     * */
    @RequestMapping(value="/addOutputFile",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addOutputFile(@RequestParam("outputfileName")String outputfileName,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        //追加用
        List<OutputList> outputListNew=new ArrayList<OutputList>(); //入庫
        List<StockList> stockListsNew=new ArrayList<StockList>();//在庫

        String tempTrackNum="";

        //Fileから
        if(outputfileName.length()>0){
            String[] vids = outputfileName.split("\\r\\n");
            if(vids.length>0){
                for(int i = 0;i<vids.length;i++){
                    String[] outputtemp =vids[i].split("\\t");
                    if(outputtemp.length==4){

                        if("NW7".equals(outputtemp[2])){
                        //追跡番号の場合
                            tempTrackNum=outputtemp[0];
                        }else{
                            //商品の場合
                            OutputList outputListTemp=new OutputList();
                            outputListTemp.setOutputTrackNum(tempTrackNum);
                            outputListTemp.setOutputDate(outputtemp[1]);
                            //バーコード番号から商品情報を取得

                            GoodsList goodsList=goodsListService.getGoodsLisByBarcode(outputtemp[0]);
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

                            //割引を１に設定する
                            outputListTemp.setOutputDiscount(100.0);

                            //数量を1に設定する
                            outputListTemp.setOutputNum(1);
                            //削除フラグを0に設定する
                            outputListTemp.setOutputDelFlg("0");
                            //倉庫を設定する
                            if(outputtemp[3]==""){
                                //デフォルトで一番の倉庫にする
                                outputListTemp.setWarehouseId(1);
                            }else{
                                outputListTemp.setWarehouseId(Integer.valueOf(outputtemp[3]));
                            }


                            //append Date
                            Date date = new Date();
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                            outputListTemp.setTrkDate(sdf1.format(date));
                            outputListTemp.setUpdDate(sdf1.format(date));

                            outputListNew.add(outputListTemp);

                            //在庫更新

                            //現在在庫数量を更新する
                            StockList stockListOldTemp=new StockList();
                            stockListOldTemp.setGoodsListId(goodsList.getGoodsListId());
                            stockListOldTemp.setStockWarehouseId(outputListTemp.getWarehouseId());
                            stockListOldTemp=stockListService.getStockListByGoodsIdAndWarehouseId(stockListOldTemp);

                            //在庫数
                            Integer tempStock=stockListOldTemp.getStockputNum()-1;
                            StockList stockListTemp=new StockList();
                            stockListTemp.setGoodsListId(goodsList.getGoodsListId());
                            stockListTemp.setStockputNum(-1);
                            stockListTemp.setStockWarehouseId(outputListTemp.getWarehouseId());

                            stockListsNew.add(stockListTemp);
                        }

                    }
                }
            }
        }

        //DBに保存する

        //入庫データを保存する
        outputListNew=outputService.insertOutputDataByLists(outputListNew);
        if(stockListsNew!=null){
            for(int nIndex=0;nIndex<stockListsNew.size();nIndex++){
                stockListService.updateStockNumOneByOne(stockListsNew.get(nIndex));
            }
        }


        int outputListNew_num=0;
        //出庫データに商品情報を追加する


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TOutputList,session,request);

        session.setAttribute("outputListNew", outputListNew);
        session.setAttribute("outputListNew_num", outputListNew_num);

        Gson gson=new Gson();
        if(outputListNew!=null){
            return gson.toJson(outputListNew);
        }else{
            return "true";
        }


    }


    /**
     * 入庫追加ページ(単体)へ遷移しますを追加する
     * */
    @RequestMapping(value="/AddOutputOne", method=RequestMethod.POST , produces = "text/html;charset=UTF-8")
    public String AddOutputOne(@ModelAttribute("outputListForm")OutputListForm outputListForm,HttpSession session,HttpServletRequest request){

        //
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Config.LoginSession;
        } else {

            //追加用
            List<OutputList> outputListNew=new ArrayList<OutputList>(); //入庫
            List<StockList> stockListsNew=new ArrayList<StockList>();//在庫

            //商品の場合
            OutputList outputListTemp=new OutputList();
            outputListTemp.setOutputTrackNum(outputListForm.getOutputTrackNum());

            //バーコード番号から商品情報を取得
            GoodsList goodsList=goodsListService.getGoodsLisByBarcode(outputListForm.getGoodsBarcode());
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

            //割引を１に設定する
            outputListTemp.setOutputDiscount(outputListForm.getOutputDiscount());

            //数量を1に設定する
            outputListTemp.setOutputNum(outputListForm.getOutputNum());
            //削除フラグを0に設定する
            outputListTemp.setOutputDelFlg("0");
            //倉庫を設定する
            outputListTemp.setWarehouseId(outputListForm.getWarehouseId());



            //append Date
            Date date = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            outputListTemp.setTrkDate(sdf1.format(date));
            outputListTemp.setUpdDate(sdf1.format(date));
            outputListTemp.setOutputDate(sdf1.format(date));

            outputListNew.add(outputListTemp);

            //在庫更新

            //現在在庫数量を更新する
            StockList stockListOldTemp=new StockList();
            stockListOldTemp.setGoodsListId(goodsList.getGoodsListId());
            stockListOldTemp.setStockWarehouseId(outputListTemp.getWarehouseId());
            stockListOldTemp=stockListService.getStockListByGoodsIdAndWarehouseId(stockListOldTemp);

            //在庫数
            Integer tempStock=stockListOldTemp.getStockputNum()-outputListForm.getOutputNum();
            StockList stockListTemp=new StockList();
            stockListTemp.setGoodsListId(goodsList.getGoodsListId());
            stockListTemp.setStockputNum(-outputListForm.getOutputNum());
            stockListTemp.setStockWarehouseId(outputListTemp.getWarehouseId());

            stockListsNew.add(stockListTemp);


            //DBに保存する

            //入庫データを保存する
            outputListNew=outputService.insertOutputDataByLists(outputListNew);
            if(stockListsNew!=null){
                for(int nIndex=0;nIndex<stockListsNew.size();nIndex++){
                    stockListService.updateStockNumOneByOne(stockListsNew.get(nIndex));
                }
            }

            //historyテーブルを更新
            historyValveService.addHistoryValve(user.getUserName(),Config.TLogin,"単体新規出庫追加",session,request);
            session.setAttribute("outputAddOne", outputListTemp);

            return "redirect:/output/toAddPageOne";

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
            OutputList outputListTemp=new OutputList();
            outputListTemp.setOutputDelFlg("0");
            outputListTemp.setOutputNum(1);

            GoodsList goodsListTemp=new GoodsList();
            outputListTemp.setGoodsList(goodsListTemp);

            List<Warehouse> warehouseList=typeService.getAllWarehouse();

            session.setAttribute("outputAddOne", outputListTemp);
            session.setAttribute("outputAddWarehouseList", warehouseList);
            //historyテーブルを更新
            historyValveService.addHistoryValve("","",Config.TLogin,session,request);

            return "output/outputAddOne";
        }
    }

}
