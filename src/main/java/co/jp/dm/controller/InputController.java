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

        //入庫データに商品情報を追加する
        if(inputLists!=null){
            for(int nIndex=0;nIndex<inputLists.size();nIndex++){
                GoodsList goodsListTemp=goodsListService.getGoodsLisById(inputLists.get(nIndex).getGoodsListId());

                inputLists.get(nIndex).setGoodsList(goodsListTemp);
            }
        }


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TInputList,session,request);

        session.setAttribute("inputLists", inputLists);

        return gson.toJson(inputLists);

    }



}
