package co.jp.dm.dao;


import co.jp.dm.entity.Bigtype;
import co.jp.dm.entity.Middletype;
import co.jp.dm.entity.Smalltype;

import java.util.List;

/**
 * Created by rui on 2018/1/9.
 */
public interface TypeMapper {

    /**大分類を取得*/
    public List<Bigtype> findAllBigtype();

    /**大分類から中分類を取得*/
    public List<Middletype> findMiddleTypeByBigtype(Middletype middletype);

    /**大分類、中分類から小分類を取得*/
    public List<Smalltype> findSmallTypeByBigtype(Smalltype smalltype);

    /**大分類　名称を取得*/
    public Bigtype findBigTypeByBigtypeId(Bigtype bigtype);

    /**中分類　名称を取得*/
    public Middletype findMiddleTypeByMiddletypeId(Middletype middletype);

    /**小分類　名称を取得*/
    public Smalltype findSmallTypeBySmalltypeId(Smalltype smalltype);
}
