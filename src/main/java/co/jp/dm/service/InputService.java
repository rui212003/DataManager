package co.jp.dm.service;

import co.jp.dm.dao.HistoryValveMapper;
import co.jp.dm.dao.InputMapper;
import co.jp.dm.dao.TypeMapper;
import co.jp.dm.entity.Bigtype;
import co.jp.dm.entity.InputList;
import co.jp.dm.entity.Middletype;
import co.jp.dm.entity.Smalltype;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */
@Service
public class InputService {


    @Resource
    InputMapper inputMapper;

    /**入庫データを抽出する*/
    public List<InputList> getInputList(InputList inputList){
        List<InputList> inputLists = inputMapper.findInputList(inputList);
        return inputLists;
    }


}
