package co.jp.dm.dao;


import co.jp.dm.entity.Bigtype;
import co.jp.dm.entity.InputList;
import co.jp.dm.entity.Middletype;
import co.jp.dm.entity.Smalltype;

import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */
public interface InputMapper {

    /**入庫データを抽出する*/
    public List<InputList> findInputList(InputList inputList);


}
