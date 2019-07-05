package com.zyy.service.impl;

import com.zyy.dao.MonitorLogMapper;
import com.zyy.model.MonitorLog;
import com.zyy.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "monitorService")
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    MonitorLogMapper monitorLogMapper;

    @Override
    public List<MonitorLog> selectAll() {
        return monitorLogMapper.selectAll();
    }

    @Override
    public int insert(MonitorLog record) {

        return monitorLogMapper.insert(record);
    }
}
