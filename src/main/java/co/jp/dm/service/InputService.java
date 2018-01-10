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
import java.text.SimpleDateFormat;
import java.util.Date;
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

    /**入庫データを修正する*/
    public InputList updateInputdata(InputList inputList){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

        inputList.setUpdDate(sdf1.format(date));
        inputMapper.updateInputData(inputList);

        inputList=inputMapper.findInputListById(inputList);

        return inputList;
    }

    /**入庫データを削除する*/
    public void deteleInputdata(InputList inputList){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

        inputList.setInputDelFlg("1");
        inputList.setUpdDate(sdf1.format(date));
        inputMapper.deleteInputData(inputList);
    }

}
