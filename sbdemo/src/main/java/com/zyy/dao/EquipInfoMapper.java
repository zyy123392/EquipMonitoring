package com.zyy.dao;

import com.zyy.model.EquipInfo;

import java.util.List;

public interface EquipInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(EquipInfo record);

    int insertSelective(EquipInfo record);

    EquipInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EquipInfo record);

    int updateByPrimaryKey(EquipInfo record);

    List<EquipInfo> findAll();
}