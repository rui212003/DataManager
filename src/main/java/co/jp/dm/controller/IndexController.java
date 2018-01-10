package co.jp.dm.controller;

import co.jp.dm.entity.Bigtype;
import co.jp.dm.entity.User;
import co.jp.dm.service.HistoryValveService;
import co.jp.dm.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */

@Controller
@RequestMapping("/")
public class IndexController {


    @Autowired
    TypeService typeService;

    @Autowired
    HistoryValveService historyValveService;

    /**
     * ログイン後のTopページ
     *
     * @return  String 商品大分類を取得
     *
     * */
    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpSession session, ModelMap modelMap,HttpServletRequest request) throws IOException {
        //
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Config.LoginSession;
        } else {

            //商品大分類を取得

            List<Bigtype> bigtypeList=typeService.getAllBigtype();
            session.setAttribute("bigtypeList", bigtypeList);
            modelMap.addAttribute("bigtypeList",bigtypeList);

            //historyテーブルを更新
            historyValveService.addHistoryValve("","",Config.TLogin,session,request);

            return "input/inputList";
        }
    }


    /**
     * ログアウト
     * */
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout(HttpSession session,HttpServletRequest request){
        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TLogout,session,request);
        session.removeAttribute("user");
        return Config.LoginSession;
    }
}
