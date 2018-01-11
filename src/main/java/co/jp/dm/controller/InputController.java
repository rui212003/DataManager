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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

        InputList inputList=new InputList();
        inputList.setInputListId(Integer.valueOf(inputDataId));
        inputList.setInputNum(Integer.valueOf(tmpInputNum));
        inputList.setInputDiscount(Double.valueOf(tmpInputDiscount));
        inputList.setInputDate(tmpInputDate);
        inputList=inputService.updateInputdata(inputList);

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

        InputList inputList=new InputList();
        inputList.setInputListId(Integer.valueOf(inputDataId));
        inputService.deteleInputdata(inputList);

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


}
