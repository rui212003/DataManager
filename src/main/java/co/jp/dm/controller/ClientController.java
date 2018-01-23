package co.jp.dm.controller;

import co.jp.dm.controller.Config;
import co.jp.dm.entity.Client;
import co.jp.dm.entity.User;
import co.jp.dm.entity.Warehouse;
import co.jp.dm.service.ClientService;
import co.jp.dm.service.HistoryValveService;
import co.jp.dm.service.TypeService;
import co.jp.dm.service.UserService;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rui on 2018/01/09.
 */

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    HistoryValveService historyValveService;


    //*******************************************
    //               顧客部分
    //*******************************************

    /**
     * 顧客ページを遷移する
     *
     * @return  String 顧客を取得
     *
     * */
    @RequestMapping(value="/toClientPage", method=RequestMethod.GET , produces = "text/html;charset=UTF-8")
    public String toClientPage(HttpSession session, ModelMap modelMap,HttpServletRequest request) throws IOException {
        //
        User user = (User) session.getAttribute("user");
        if(user == null){
            return Config.LoginSession;
        } else {

            //顧客を取得

            List<Client> clientList=clientService.getAllClient();
            session.setAttribute("masterClientList", clientList);
            modelMap.addAttribute("masterClientList",clientList);

            //historyテーブルを更新
            historyValveService.addHistoryValve("","",Config.TClientList,session,request);

            return "master/client/client";

        }
    }

    /**
     * 顧客データを更新する
     * */
    @RequestMapping(value="/updateClient",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateClient(@RequestParam("clientId")String clientId,@RequestParam("tempClientName")String tempClientName,@RequestParam("tempClientPhone")String tempClientPhone,@RequestParam("tempClientZip")String tempClientZip,@RequestParam("tempClientAddress")String tempClientAddress,@RequestParam("tempClientBiko")String tempClientBiko,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Client client=new Client();
        client.setClientId(Integer.valueOf(clientId));
        client.setClientName(tempClientName);
        client.setClientPhone(tempClientPhone);
        client.setClientZip(tempClientZip);
        client.setClientAddress(tempClientAddress);
        client.setClientBiko(tempClientBiko);

        client=clientService.updateClient(client);

        //session更新
        List<Client> clientList=( List<Client>)session.getAttribute("masterClientList");
        List<Client> clientListNew=new ArrayList<Client>();
        if(clientList!=null){
            for(int nIndex=0;nIndex<clientList.size();nIndex++){
                String temp=clientList.get(nIndex).getClientId()+"";
                if(clientId.equals(temp)){
                    clientListNew.add(client);
                }else{
                    clientListNew.add(clientList.get(nIndex));
                }
            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TClientList,session,request);

        session.setAttribute("masterClientList", clientListNew);
        return "true";

    }

    /**
     * 顧客データを削除する
     * */
    @RequestMapping(value="/deleteClient",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteClient(@RequestParam("clientId")String clientId,ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Client client=new Client();
        client.setClientId(Integer.valueOf(clientId));
        clientService.deleteClient(client);

        //session更新
        List<Client> clientList=( List<Client>)session.getAttribute("masterClientList");
        List<Client> clientListNew=new ArrayList<Client>();
        if(clientList!=null){
            for(int nIndex=0;nIndex<clientList.size();nIndex++){
                String temp=clientList.get(nIndex).getClientId()+"";
                if(clientId.equals(temp)){
                    System.out.println("eee");
                }else{
                    clientListNew.add(clientList.get(nIndex));
                }
            }
        }

        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TClientList,session,request);

        session.setAttribute("masterClientList", clientListNew);

        Gson gson=new Gson();

        return gson.toJson(clientListNew);

    }


    /**
     * 顧客データを追加する
     * */
    @RequestMapping(value="/addClient",  method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addClient(ModelMap modelMap,HttpSession session,HttpServletRequest request){
        //session　check
        User user=(User)session.getAttribute("user");
        if(user == null){
            modelMap.addAttribute("message",Config.TUserNull);
            return Config.LoginSession;
        }

        Client client=new Client();
        client=clientService.addClient();

        List<Client> clientList=clientService.getAllClient();
        session.setAttribute("masterClientList", clientList);
        modelMap.addAttribute("masterClientList",clientList);


        //historyテーブルを更新
        historyValveService.addHistoryValve("","",Config.TClientList,session,request);

        Gson gson=new Gson();

        return gson.toJson(clientList);

    }


}
