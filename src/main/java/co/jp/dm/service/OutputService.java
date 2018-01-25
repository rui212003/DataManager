package co.jp.dm.service;

import co.jp.dm.dao.OutputMapper;
import co.jp.dm.entity.OutputList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */
@Service
public class OutputService {


    @Resource
    OutputMapper outputMapper;

    /**出庫データを抽出する*/
    public List<OutputList> getOutputList(OutputList outputList){
        List<OutputList> outputLists = outputMapper.findOutputList(outputList);
        return outputLists;
    }

    /**出庫データを抽出する*/
    public OutputList getOutputListById(OutputList outputList){
        outputList.setOutputDelFlg("0");
        OutputList outputLists = outputMapper.findOutputListById(outputList);
        return outputLists;
    }

    /**出庫データを修正する*/
    public OutputList updateOutputdata(OutputList outputList){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        outputList.setUpdDate(sdf1.format(date));
        outputList.setOutputDelFlg("0");
        outputMapper.updateOutputData(outputList);

        outputList=outputMapper.findOutputListById(outputList);

        return outputList;
    }

    /**出庫データを削除する*/
    public void deteleOutputdata(OutputList outputList){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        outputList.setOutputDelFlg("1");
        outputList.setUpdDate(sdf1.format(date));
        outputMapper.deleteOutputData(outputList);
    }

    /**出庫データを削除する*/
    public List<OutputList> insertOutputDataByLists(List<OutputList> OutputLists){

        //append Date
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

        outputMapper.batchInsertOutputData(OutputLists);

        return  OutputLists;
    }

}
