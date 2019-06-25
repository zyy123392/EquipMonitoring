package com.zyy.dao;

import com.zyy.model.EquipData;
import com.zyy.model.StatisticResult;

import java.util.List;

public interface EquipHistoryDataMapper {
    int deleteByPrimaryKey(String id);

    int insert(EquipData record);

    int insertSelective(EquipData record);

    EquipData selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EquipData record);

    int updateByPrimaryKey(EquipData record);

    List<StatisticResult> statistics(int days,int index,int length);

    List<StatisticResult> statisticsMonth(int index,int length);

    List<StatisticResult> statisticsYear(int index,int length);

}