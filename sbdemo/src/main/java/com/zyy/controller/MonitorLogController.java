package com.zyy.controller;

import com.zyy.model.MonitorLog;
import com.zyy.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/mlog")
public class MonitorLogController {
    @Autowired
    MonitorService monitorService;
    @ResponseBody
    @RequestMapping("/find")
    public List<MonitorLog> findAll(){
        return monitorService.selectAll();
    }
}
