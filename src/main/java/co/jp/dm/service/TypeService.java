package co.jp.dm.service;

import co.jp.dm.dao.HistoryValveMapper;
import co.jp.dm.dao.TypeMapper;
import co.jp.dm.entity.Bigtype;
import co.jp.dm.entity.Middletype;
import co.jp.dm.entity.Smalltype;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */
@Service
public class TypeService {

    @Resource
    HistoryValveMapper historyValveMapper;

    @Resource
    TypeMapper typeMapper;

    /**全ての大分類マスターを抽出する*/
    public List<Bigtype> getAllBigtype(){
        List<Bigtype> bigtypeList = typeMapper.findAllBigtype();
        return bigtypeList;
    }

    /**大分類から中分類を抽出する*/
    public List<Middletype> getMiddleTypeByBigtype(String bigtypeValue){
        Middletype middletype=new Middletype();
        middletype.setBigtypeId(Integer.valueOf(bigtypeValue));
        middletype.setOutputDelFlg("0");

        List<Middletype> middletypeList = typeMapper.findMiddleTypeByBigtype(middletype);
        return middletypeList;
    }

    /**大分類、中分類から小分類を取得*/
    public List<Smalltype> getSmallTypeByBigType(String bigtypeValue,String middletypeValue){
        Smalltype smalltype=new Smalltype();
        smalltype.setBigtypeId(Integer.valueOf(bigtypeValue));
        smalltype.setMiddletypeId(Integer.valueOf(middletypeValue));
        smalltype.setOutputDelFlg("0");

        List<Smalltype> smalltypeList = typeMapper.findSmallTypeByBigtype(smalltype);
        return smalltypeList;
    }

    /**大分類IDから大分類名称を抽出する*/
    public Bigtype getBigTypeByBigtypeId(int bigtypeId){
        Bigtype bigtype=new Bigtype();
        bigtype.setBigtypeId(bigtypeId);

        bigtype = typeMapper.findBigTypeByBigtypeId(bigtype);
        return bigtype;
    }

    /**中分類IDから中分類名称を抽出する*/
    public Middletype getMiddleTypeBytypeId(int middletypeId){
        Middletype middletype=new Middletype();
        middletype.setMiddletypeId(middletypeId);

        middletype = typeMapper.findMiddleTypeByMiddletypeId(middletype);
        return middletype;
    }

    /**小分類IDから小分類名称を抽出する*/
    public Smalltype getSmallTypeBytypeId(int smalltypeId){
        Smalltype smalltype=new Smalltype();
        smalltype.setSmalltypeId(smalltypeId);

        smalltype = typeMapper.findSmallTypeBySmalltypeId(smalltype);
        return smalltype;
    }

}
