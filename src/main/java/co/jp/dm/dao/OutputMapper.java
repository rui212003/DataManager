package co.jp.dm.dao;



import co.jp.dm.entity.OutputList;

import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */
public interface OutputMapper {

    /**入庫データを抽出する*/
    public List<OutputList> findOutputList(OutputList outputList);

    /**入庫データを取得する*/
    public OutputList findOutputListById(OutputList outputList);

    /**入庫データを更新する*/
    public void updateOutputData(OutputList outputList);

    /**入庫データを削除する*/
    public void deleteOutputData(OutputList outputList);

    /**入庫データを追加する*/
    public void batchInsertOutputData(List<OutputList> outputLists);

}
