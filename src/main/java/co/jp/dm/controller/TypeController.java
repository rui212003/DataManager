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
     * 商品分類ページを遷移する
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
        }else if("2".equals(outInput)){
            //outputページ場合
            session.setAttribute("outputmiddletypeList", middletypeList);

            return gson.toJson(middletypeList);
        }else if("3".equals(outInput)){
            //マスタ　中分類ページ場合
            session.setAttribute("masterMiddletypeList", middletypeList);

            return gson.toJson(middletypeList);
        }else{
            //マスタ　小分類ページ場合
            session.setAttribute("masterSmalltypeList", middletypeList);

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
        }else if("2".equals(outInput)){
            //outputページ場合
            session.setAttribute("outputsmalltypeList", smalltypeList);

            return gson.toJson(smalltypeList);
        }else{
            //マスタ　小分類ページ場合
            session.setAttribute("masterSmalltypeList", smalltypeList);

            return gson.toJson(smalltypeList);
        }

    }

    //*******************************************
    //               大分類
    //*******************************************

    /**
     * 大分類データを更新する
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
     * 大分類データを更新する
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
     * 大分類データを追加する
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





    //*******************************************
    //               中分類
    //*******************************************

    /**
     * 中分類データを更新する
     * */
    @RequestMapping(value="/updateMiddletypeName",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateMiddletypeName(@RequestParam("middletypeId")String middletypeId,@RequestParam("tmpmiddletypeName")String tmpmiddletypeName,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Middletype middletype=new Middletype();
        middletype.setMiddletypeId(Integer.valueOf(middletypeId));
        middletype.setMiddletypeName(tmpmiddletypeName);
        middletype=typeService.updateMiddletypeData(middletype);

        //session更新
        List<Middletype> middletypeList=( List<Middletype>)session.getAttribute("masterMiddletypeList");
        List<Middletype> middletypeListNew=new ArrayList<Middletype>();
        if(middletypeList!=null){
            for(int nIndex=0;nIndex<middletypeList.size();nIndex++){
                String temp=middletypeList.get(nIndex).getMiddletypeId()+"";
                if(middletypeId.equals(temp)){
                    middletypeListNew.add(middletype);
                }else{
                    middletypeListNew.add(middletypeList.get(nIndex));
                }
            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TMiddletype,session,request);

        session.setAttribute("masterMiddletypeList", middletypeListNew);
        return "true";

    }

    /**
     * 中分類データを更新する
     * */
    @RequestMapping(value="/deleteMiddletypeName",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteMiddletypeName(@RequestParam("middletypeId")String middletypeId,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Middletype middletype=new Middletype();
        middletype.setMiddletypeId(Integer.valueOf(middletypeId));

        typeService.deleteMiddletypeData(middletype);


        //session更新
        List<Middletype> middletypeList=( List<Middletype>)session.getAttribute("masterMiddletypeList");
        List<Middletype> middletypeListNew=new ArrayList<Middletype>();
        if(middletypeList!=null){
            for(int nIndex=0;nIndex<middletypeList.size();nIndex++){
                String temp=middletypeList.get(nIndex).getMiddletypeId()+"";
                if(middletypeId.equals(temp)){
                    System.out.println("eee");
                }else{
                    middletypeListNew.add(middletypeList.get(nIndex));
                }
            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TMiddletype,session,request);

        session.setAttribute("masterMiddletypeList", middletypeListNew);
        Gson gson=new Gson();

        return gson.toJson(middletypeListNew);

    }


    /**
     * 中分類データを追加する
     * */
    @RequestMapping(value="/addMiddletype",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addMiddletype(@RequestParam("bigtypeId")String bigtypeId,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Middletype middletype=new Middletype();
        middletype.setBigtypeId(Integer.valueOf(bigtypeId));
        typeService.addMiddletype(middletype);

        Bigtype bigtype=new Bigtype();
        bigtype.setBigtypeId(Integer.valueOf(bigtypeId));

        List<Middletype> middletypeList=typeService.getMiddleTypeByBigtype(bigtypeId);
        session.setAttribute("masterMiddletypeList", middletypeList);
        modelMap.addAttribute("masterMiddletypeList",middletypeList);


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TBigtype,session,request);

        Gson gson=new Gson();

        return gson.toJson(middletypeList);

    }





    //*******************************************
    //               小分類
    //*******************************************

    /**
     * 小分類データを更新する
     * */
    @RequestMapping(value="/updateSmalltypeName",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateSmalltypeName(@RequestParam("smalltypeId")String smalltypeId,@RequestParam("tmpsmalltypeName")String tmpsmalltypeName,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Smalltype smalltype=new Smalltype();
        smalltype.setSmalltypeId(Integer.valueOf(smalltypeId));
        smalltype.setSmalltypeName(tmpsmalltypeName);
        smalltype=typeService.updateSmalltypeData(smalltype);

        //session更新
        List<Smalltype> smalltypeList=( List<Smalltype>)session.getAttribute("masterSmalltypeList");
        List<Smalltype> smalltypeListNew=new ArrayList<Smalltype>();
        if(smalltypeList!=null){
            for(int nIndex=0;nIndex<smalltypeList.size();nIndex++){
                String temp=smalltypeList.get(nIndex).getSmalltypeId()+"";
                if(smalltypeId.equals(temp)){
                    smalltypeListNew.add(smalltype);
                }else{
                    smalltypeListNew.add(smalltypeList.get(nIndex));
                }
            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TSmalltype,session,request);

        session.setAttribute("masterSmalltypeList", smalltypeListNew);
        return "true";

    }

    /**
     * 小分類データを更新する
     * */
    @RequestMapping(value="/deleteSmalltypeName",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteSmalltypeName(@RequestParam("smalltypeId")String smalltypeId,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Smalltype smalltype=new Smalltype();
        smalltype.setSmalltypeId(Integer.valueOf(smalltypeId));
        typeService.deleteSmalltypeData(smalltype);


        //session更新
        List<Smalltype> smalltypeList=( List<Smalltype>)session.getAttribute("masterSmalltypeList");
        List<Smalltype> smalltypeListNew=new ArrayList<Smalltype>();
        if(smalltypeList!=null){
            for(int nIndex=0;nIndex<smalltypeList.size();nIndex++){
                String temp=smalltypeList.get(nIndex).getSmalltypeId()+"";
                if(smalltypeId.equals(temp)){
                    System.out.println("eee");
                }else{
                    smalltypeListNew.add(smalltypeList.get(nIndex));
                }
            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TSmalltype,session,request);

        session.setAttribute("masterSmalltypeList", smalltypeListNew);
        Gson gson=new Gson();

        return gson.toJson(smalltypeListNew);

    }


    /**
     * 小分類データを追加する
     * */
    @RequestMapping(value="/addSmalltype",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addSmalltype(@RequestParam("bigtypeId")String bigtypeId,@RequestParam("middletypeId")String middletypeId,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Smalltype smalltype=new Smalltype();
        smalltype.setBigtypeId(Integer.valueOf(bigtypeId));
        smalltype.setMiddletypeId(Integer.valueOf(middletypeId));

        typeService.addSmalltype(smalltype);


        List<Smalltype> smalltypeList=typeService.getSmallTypeByBigType(bigtypeId,middletypeId);
        session.setAttribute("masterSmalltypeList", smalltypeList);
        modelMap.addAttribute("masterSmalltypeList",smalltypeList);


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TSmalltype,session,request);

        Gson gson=new Gson();

        return gson.toJson(smalltypeList);

    }






    //*******************************************
    //               単位部分
    //*******************************************

    /**
     * 商品単位ページを遷移する
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
     * 単位データを更新する
     * */
    @RequestMapping(value="/updateGoodsUnitName",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateGoodsUnitName(@RequestParam("goodsUnitId")String goodsUnitId,@RequestParam("tmpGoodsUnitName")String tmpGoodsUnitName,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        GoodsUnit goodsUnit=new GoodsUnit();
        goodsUnit.setGoodsUnitId(Integer.valueOf(goodsUnitId));
        goodsUnit.setGoodsUnitName(tmpGoodsUnitName);
        goodsUnit=typeService.updateGoodsUnit(goodsUnit);

        //session更新
        List<GoodsUnit> goodsUnitsList=( List<GoodsUnit>)session.getAttribute("masterGoodsUnitList");
        List<GoodsUnit> goodsUnitsListNew=new ArrayList<GoodsUnit>();
        if(goodsUnitsList!=null){
            for(int nIndex=0;nIndex<goodsUnitsList.size();nIndex++){
                String temp=goodsUnitsList.get(nIndex).getGoodsUnitId()+"";
                if(goodsUnitId.equals(temp)){
                    goodsUnitsListNew.add(goodsUnit);
                }else{
                    goodsUnitsListNew.add(goodsUnitsList.get(nIndex));
                }
            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TGoodsUnit,session,request);

        session.setAttribute("masterGoodsUnitList", goodsUnitsListNew);
        return "true";

    }

    /**
     * 単位データを削除する
     * */
    @RequestMapping(value="/deleteGoodsUnitName",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteGoodsUnitName(@RequestParam("goodsUnitId")String goodsUnitId,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        GoodsUnit goodsUnit=new GoodsUnit();
        goodsUnit.setGoodsUnitId(Integer.valueOf(goodsUnitId));

        typeService.deleteGoodsUnit(goodsUnit);


        //session更新
        List<GoodsUnit> goodsUnitsList=( List<GoodsUnit>)session.getAttribute("masterGoodsUnitList");
        List<GoodsUnit> goodsUnitsListNew=new ArrayList<GoodsUnit>();
        if(goodsUnitsList!=null){
            for(int nIndex=0;nIndex<goodsUnitsList.size();nIndex++){
                String temp=goodsUnitsList.get(nIndex).getGoodsUnitId()+"";
                if(goodsUnitId.equals(temp)){
                }else{
                    goodsUnitsListNew.add(goodsUnitsList.get(nIndex));
                }
            }
        }


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TGoodsUnit,session,request);

        session.setAttribute("masterGoodsUnitList", goodsUnitsListNew);
        Gson gson=new Gson();

        return gson.toJson(goodsUnitsListNew);

    }


    /**
     * 単位データを追加する
     * */
    @RequestMapping(value="/addGoodsUnit",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addGoodsUnit(ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        GoodsUnit goodsUnit=new GoodsUnit();
        goodsUnit=typeService.addGoodsUnit();

        List<GoodsUnit> goodsUnitList=typeService.getAllGoodsUnit();
        session.setAttribute("masterGoodsUnitList", goodsUnitList);
        modelMap.addAttribute("masterGoodsUnitList",goodsUnitList);


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TGoodsUnit,session,request);

        Gson gson=new Gson();

        return gson.toJson(goodsUnitList);

    }




    //*******************************************
    //               倉庫部分
    //*******************************************

    /**
     * 倉庫ページを遷移する
     *
     * @return  String 倉庫を取得
     *
     * */
    @RequestMapping(value="/toWarehousePage", method=RequestMethod.GET , produces = "text/html;charset=UTF-8")
    public String toWarehousePage(HttpSession session, ModelMap modelMap,HttpServletRequest request) throws IOException {
        //
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Config.LoginSession;
        } else {

            //倉庫を取得

            List<Warehouse> warehouseList=typeService.getAllWarehouse();
            session.setAttribute("masterWarehouseList", warehouseList);
            modelMap.addAttribute("masterWarehouseList",warehouseList);

            //historyテーブルを更新
            historyValveService.addHistoryValve("","",Config.TWarehouse,session,request);

            return "master/warehouse/warehouse";

        }
    }

    /**
     * 倉庫データを更新する
     * */
    @RequestMapping(value="/updateWarehouseName",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateWarehouseName(@RequestParam("warehouseId")String warehouseId,@RequestParam("tmpWarehouseName")String tmpWarehouseName,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Warehouse warehouse=new Warehouse();
        warehouse.setWarehouseId(Integer.valueOf(warehouseId));
        warehouse.setWarehouseName(tmpWarehouseName);
        warehouse=typeService.updateWarehouseName(warehouse);

        //session更新
        List<Warehouse> warehouseList=( List<Warehouse>)session.getAttribute("masterWarehouseList");
        List<Warehouse> warehouseListNew=new ArrayList<Warehouse>();
        if(warehouseList!=null){
            for(int nIndex=0;nIndex<warehouseList.size();nIndex++){
                String temp=warehouseList.get(nIndex).getWarehouseId()+"";
                if(warehouseId.equals(temp)){
                    warehouseListNew.add(warehouse);
                }else{
                    warehouseListNew.add(warehouseList.get(nIndex));
                }
            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TWarehouse,session,request);

        session.setAttribute("masterWarehouseList", warehouseListNew);
        return "true";

    }

    /**
     * 倉庫データを削除する
     * */
    @RequestMapping(value="/deleteWarehouseName",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteWarehouseName(@RequestParam("warehouseId")String warehouseId,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Warehouse warehouse=new Warehouse();
        warehouse.setWarehouseId(Integer.valueOf(warehouseId));
        typeService.deleteWarehouse(warehouse);

        //session更新
        List<Warehouse> warehouseList=( List<Warehouse>)session.getAttribute("masterWarehouseList");
        List<Warehouse> warehouseListNew=new ArrayList<Warehouse>();
        if(warehouseList!=null){
            for(int nIndex=0;nIndex<warehouseList.size();nIndex++){
                String temp=warehouseList.get(nIndex).getWarehouseId()+"";
                if(warehouseId.equals(temp)){
                    System.out.println("eee");
                }else{
                    warehouseListNew.add(warehouseList.get(nIndex));
                }
            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TWarehouse,session,request);

        session.setAttribute("masterWarehouseList", warehouseListNew);

        Gson gson=new Gson();

        return gson.toJson(warehouseListNew);

    }


    /**
     * 倉庫データを追加する
     * */
    @RequestMapping(value="/addWarehouse",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addWarehouse(ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Warehouse warehouse=new Warehouse();
        warehouse=typeService.addWarehouse();

        List<Warehouse> warehouseList=typeService.getAllWarehouse();
        session.setAttribute("masterWarehouseList", warehouseList);
        modelMap.addAttribute("masterWarehouseList",warehouseList);


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TWarehouse,session,request);

        Gson gson=new Gson();

        return gson.toJson(warehouseList);

    }

}
