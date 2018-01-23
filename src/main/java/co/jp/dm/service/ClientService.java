package co.jp.dm.service;

import co.jp.dm.dao.ClientMapper;
import co.jp.dm.dao.GoodsListMapper;
import co.jp.dm.entity.Client;
import co.jp.dm.entity.GoodsList;
import co.jp.dm.entity.Warehouse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */
@Service
public class ClientService {


    @Resource
    ClientMapper clientMapper;

    /**顧客データを抽出する*/
    public List<Client> getAllClient(){
        List<Client> clientList=clientMapper.findAllClient();

        return clientList;
    }



    /**顧客を修正する*/
    public Client updateClient(Client client){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        client.setUpdDate(sdf1.format(date));

        clientMapper.updateClient(client);
        client = clientMapper.findClientByClientId(client);
        return client;
    }

    /**顧客を削除する*/
    public void deleteClient(Client client){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        client.setUpdDate(sdf1.format(date));
        client.setOutputDelFlg("1");
        clientMapper.deleteClient(client);

    }

    /**顧客を追加する*/
    public Client addClient(){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        Client client=new Client();
        client.setUpdDate(sdf1.format(date));
        client.setTrkDate(sdf1.format(date));
        client.setOutputDelFlg("0");
        client.setClientName("");
        client.setClientPhone("");
        client.setClientZip("");
        client.setClientAddress("");
        client.setClientBiko("");

        clientMapper.insertClient(client);
        int tempId=clientMapper.getLastInsertClientId();
        client.setClientId(tempId);
        return client;
    }

}
