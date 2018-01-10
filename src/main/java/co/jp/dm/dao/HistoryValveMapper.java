package co.jp.dm.dao;


import co.jp.dm.entity.HistoryValve;

import java.util.List;

/**
 * Created by zhangrui on 2015/2/5.
 */
public interface HistoryValveMapper {

    /**historyTable 新規追加*/
    public void insertHV(HistoryValve historyValve);

    /**historyTable 検索*/
    public List<HistoryValve> findHistoryValve(HistoryValve historyValve);

    /**historyTable Today検索*/
    public List<HistoryValve> findHistoryForToday(HistoryValve historyValve);

}
