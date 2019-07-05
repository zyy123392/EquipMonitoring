package com.zyy.dao;

import com.zyy.model.MonitorLog;

import java.util.List;

public interface MonitorLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(MonitorLog record);

    int insertSelective(MonitorLog record);

    MonitorLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MonitorLog record);

    int updateByPrimaryKey(MonitorLog record);

    List<MonitorLog> selectAll();
}