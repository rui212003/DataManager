package co.jp.dm.dao;


import co.jp.dm.entity.*;

import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */
public interface ClientMapper {

    //*******************************************
    //***************　顧客 **********************
    //*******************************************

    /**倉庫　全部データを取得*/
    public List<Client> findAllClient();

    /**倉庫　名称を取得*/
    public Client findClientByClientId(Client client);

    /**倉庫　を更新する*/
    public void updateClient(Client client);

    /**倉庫　を削除する*/
    public void deleteClient(Client client);

    /**倉庫　を追加する*/
    public void insertClient(Client client);

    /**倉庫ID　を取得する*/
    public int getLastInsertClientId();



}
