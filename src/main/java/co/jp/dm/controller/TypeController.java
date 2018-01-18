package co.jp.dm.controller;

import co.jp.dm.entity.Bigtype;
import co.jp.dm.entity.Middletype;
import co.jp.dm.entity.Smalltype;
import co.jp.dm.entity.User;
import co.jp.dm.service.HistoryValveService;
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
import java.io.IOException;
import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */

@Controller
@RequestMapping("/type")
public class TypeController {


    @Autowired
    TypeService typeService;

    @Autowired
    HistoryValveService historyValveService;

    /**
     * 大分類から中分類を取得
     * */
    @RequestMapping(value="/getMiddleTypeByBigType",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMiddleTypeByBigType(@RequestParam("outInput")String outInput,@RequestParam("bigtypeValue")String bigtypeValue,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Gson gson=new Gson();

        List<Middletype> middletypeList=typeService.getMiddleTypeByBigtype(bigtypeValue);

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TMiddletype,session,request);

        if("1".equals(outInput)){
            //inputページ場合
            session.setAttribute("middletypeList", middletypeList);

            return gson.toJson(middletypeList);
        }else{
            //outputページ場合
            session.setAttribute("outputmiddletypeList", middletypeList);

            return gson.toJson(middletypeList);
        }


    }

    /**
     * 大分類、中分類から小分類を取得
     * */
    @RequestMapping(value="/getSmallTypeByBigType",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getSmallTypeByBigType(@RequestParam("outInput")String outInput,@RequestParam("bigtypeValue")String bigtypeValue,@RequestParam("middletypeValue")String middletypeValue,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Gson gson=new Gson();

        List<Smalltype> smalltypeList=typeService.getSmallTypeByBigType(bigtypeValue,middletypeValue);

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TSmalltype,session,request);

        if("1".equals(outInput)){
            //inputページ場合
            session.setAttribute("smalltypeList", smalltypeList);

            return gson.toJson(smalltypeList);
        }else{
            //outputページ場合
            session.setAttribute("outputsmalltypeList", smalltypeList);

            return gson.toJson(smalltypeList);
        }

    }

}
