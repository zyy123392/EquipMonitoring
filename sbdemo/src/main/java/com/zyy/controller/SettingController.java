package com.zyy.controller;

import com.zyy.model.MyResult;
import com.zyy.model.SetData;
import com.zyy.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/setting")
public class SettingController {

    @Autowired
    SettingService settingService;

    @ResponseBody
    @PostMapping("/update")
    public MyResult update(@RequestBody SetData setData){
        return settingService.updateSetting(setData);
    }

    @RequestMapping("/updateTem")
    public int updateSettingTem(@RequestParam("tem")int settingTem){
        return settingService.updateSettingTem(settingTem);
    }

    @ResponseBody
    @RequestMapping("/getStatus")
    public SetData getTem(){
        return settingService.selectTem();
    }

    @ResponseBody
    @RequestMapping("/getSetting")
    public SetData getSetting(){
        return settingService.selectByPrimaryKey();
    }
}
