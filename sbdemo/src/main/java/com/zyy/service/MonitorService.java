package com.zyy.service;

import com.zyy.model.MonitorLog;

import java.util.List;

public interface MonitorService {
    List<MonitorLog> selectAll();
    int insert(MonitorLog record);
}
