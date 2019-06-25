package com.zyy.dao;

import com.zyy.model.EquipData;

import java.util.List;

public interface EquipTmpDataMapper {
    int deleteByPrimaryKey(String id);

    int insert(EquipData record);

    int insertSelective(EquipData record);

    EquipData selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EquipData record);

    int updateByPrimaryKey(EquipData record);

    List<EquipData> findLatest();
}