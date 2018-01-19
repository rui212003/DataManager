package co.jp.dm.controller;

import co.jp.dm.entity.*;
import co.jp.dm.service.HistoryValveService;
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
@RequestMapping("/type")
public class TypeController {


    @Autowired
    TypeService typeService;

    @Autowired
    HistoryValveService historyValveService;


    /**
     * 商品大分類ページを遷移する
     *
     * @return  String 商品大分類を取得
     *
     * */
    @RequestMapping(value="{typeNum}/toTypePage", method=RequestMethod.GET , produces = "text/html;charset=UTF-8")
    public String toTypePage(@PathVariable String typeNum,HttpSession session, ModelMap modelMap,HttpServletRequest request) throws IOException {
        //
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Config.LoginSession;
        } else {

            //商品大分類を取得

            List<Bigtype> bigtypeList=typeService.getAllBigtype();
            session.setAttribute("masterBigtypeList", bigtypeList);
            modelMap.addAttribute("masterBigtypeList",bigtypeList);

            //historyテーブルを更新
            historyValveService.addHistoryValve("","",Config.TBigtype,session,request);

            if("big".equals(typeNum)){
                return "master/type/bigtype";
            }else if("middle".equals(typeNum)){
                return "master/type/middletype";
            }else{
                return "master/type/smalltype";
            }

        }
    }

    /**
     * 商品大分類ページを遷移する
     *
     * @return  String 商品大分類を取得
     *
     * */
    @RequestMapping(value="/toGoodsUnitPage", method=RequestMethod.GET , produces = "text/html;charset=UTF-8")
    public String toGoodsUnitPage(HttpSession session, ModelMap modelMap,HttpServletRequest request) throws IOException {
        //
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Config.LoginSession;
        } else {

            //商品単位を取得

            List<GoodsUnit> goodsUnitList=typeService.getAllGoodsUnit();
            session.setAttribute("masterGoodsUnitList", goodsUnitList);
            modelMap.addAttribute("masterGoodsUnitList",goodsUnitList);

            //historyテーブルを更新
            historyValveService.addHistoryValve("","",Config.TBigtype,session,request);

            return "master/type/unit";

        }
    }


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

    /**
     * 出庫データを更新する
     * */
    @RequestMapping(value="/updateBigtypeName",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateBigtypeName(@RequestParam("bigtypeId")String bigtypeId,@RequestParam("tmpbigtypeName")String tmpbigtypeName,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Bigtype bigtype=new Bigtype();
        bigtype.setBigtypeId(Integer.valueOf(bigtypeId));
        bigtype.setBigtypeName(tmpbigtypeName);
        bigtype=typeService.updateBigtypeData(bigtype);

        //session更新
        List<Bigtype> bigtypeList=( List<Bigtype>)session.getAttribute("masterBigtypeList");
        List<Bigtype> bigtypeListNew=new ArrayList<Bigtype>();
        if(bigtypeList!=null){
            for(int nIndex=0;nIndex<bigtypeList.size();nIndex++){
                if(bigtypeId.equals(bigtypeList.get(nIndex).getBigtypeId()+"")){
                    bigtypeListNew.add(bigtype);
                }else{
                    bigtypeListNew.add(bigtypeList.get(nIndex));
                }
            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TBigtype,session,request);

        session.setAttribute("masterBigtypeList", bigtypeListNew);
        return "true";

    }

    /**
     * 出庫データを更新する
     * */
    @RequestMapping(value="/deleteBigtypeName",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteBigtypeName(@RequestParam("bigtypeId")String bigtypeId,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Bigtype bigtype=new Bigtype();
        bigtype.setBigtypeId(Integer.valueOf(bigtypeId));
        typeService.deleteBigtypeData(bigtype);


        //session更新
        List<Bigtype> bigtypeList=( List<Bigtype>)session.getAttribute("masterBigtypeList");
        List<Bigtype> bigtypeListNew=new ArrayList<Bigtype>();
        if(bigtypeList!=null){
            for(int nIndex=0;nIndex<bigtypeList.size();nIndex++){
                String temp=bigtypeList.get(nIndex).getBigtypeId()+"";
                if(bigtypeId.equals(temp)){
                    System.out.println("eee");
                }else{
                    bigtypeListNew.add(bigtypeList.get(nIndex));
                }
            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TOutputList,session,request);

        session.setAttribute("masterBigtypeList", bigtypeListNew);
        Gson gson=new Gson();

        return gson.toJson(bigtypeListNew);

    }


    /**
     * 出庫データを更新する
     * */
    @RequestMapping(value="/addBigtype",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addBigtype(ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Bigtype bigtype=new Bigtype();
        bigtype=typeService.addBigtypeData();

        List<Bigtype> bigtypeList=typeService.getAllBigtype();
        session.setAttribute("masterBigtypeList", bigtypeList);
        modelMap.addAttribute("masterBigtypeList",bigtypeList);


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TBigtype,session,request);

        Gson gson=new Gson();

        return gson.toJson(bigtypeList);

    }

}
