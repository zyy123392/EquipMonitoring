package com.zyy.controller;

import com.zyy.service.AirConditionService;
import com.zyy.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/control")
public class AirConditionController {
    @Autowired
    AirConditionService airConditionService;
    @Autowired
    SettingService settingService;
    @ResponseBody
    @GetMapping("/turnOn")
    public String turnOn() throws IOException {

        return airConditionService.turnOn();
    }
    @ResponseBody
    @GetMapping("/turnOff")
    public String turnOff() throws IOException {
        return airConditionService.turnOff();
    }
    @ResponseBody
    @GetMapping("/turnUp")
    public String turnUp() throws IOException {
        int tem = settingService.selectTem().getSettingTem();
        if(settingService.selectTem().getStatus() == 0){
            return "请先开启空调！";
        }else{
            return airConditionService.turnUp(tem+ 1);
        }

    }
    @ResponseBody
    @GetMapping("/turnDown")
    public String turnDown() throws IOException {
        int tem = settingService.selectTem().getSettingTem();
        if(settingService.selectTem().getStatus() == 0){
            return "请先开启空调！";
        }else{
            return airConditionService.turnDown(tem- 1);
        }

    }
}
