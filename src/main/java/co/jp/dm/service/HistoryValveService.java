package co.jp.dm.service;

import co.jp.dm.controller.Config;
import co.jp.dm.dao.HistoryValveMapper;
import co.jp.dm.entity.HistoryValve;
import co.jp.dm.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangrui on 2015/2/4.
 */
@Service
public class HistoryValveService {

    @Resource
    HistoryValveMapper historyValveMapper;


    /**ics 新規追加*/
    public HistoryValve addHistoryValve(String hTablename,String hTargetId,String hDetail,HttpSession session,HttpServletRequest request){
        User user=(User)session.getAttribute("user");
        HistoryValve historyValve=new HistoryValve();
        if(user != null){
            historyValve.sethUsername(user.getUserName());
            historyValve.sethTablename(hTablename);
            if(hTargetId.length()>255){
                hTargetId=hTargetId.substring(0,254);//項目の長さに制限があるため
            }
            historyValve.sethTargetId(hTargetId);
            historyValve.sethDetail(hDetail);

            String ipTmp="unKonwn";
            historyValve.sethIp(Config.getIpAddr(request));
            System.out.println("ip config="+ Config.getIpAddr(request));

            //append Date
            Date date = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            historyValve.sethTrkDate(sdf1.format(date));
            System.out.println("作業データ="+ sdf1.format(date));

            historyValveMapper.insertHV(historyValve);
        }
        return historyValve;

    }

    /** 検索*/
    public List<HistoryValve> getHistoryValve(HistoryValve historyValve) {

        List<HistoryValve> historyValveList=new ArrayList<HistoryValve>();
        historyValveList=historyValveMapper.findHistoryValve(historyValve);
        return  historyValveList;
    }

    /** 今日の履歴取得*/
    public List<HistoryValve> getHistoryForToday() {
        HistoryValve historyValve=new HistoryValve();

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
        historyValve.sethTrkDate(sdf1.format(date));
        System.out.println("time="+sdf1.format(date));

        List<HistoryValve> historyValveList=historyValveMapper.findHistoryValve(historyValve);
        return  historyValveList;
    }
}
