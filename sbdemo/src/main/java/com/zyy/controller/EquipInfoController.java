package com.zyy.controller;

import com.zyy.model.EquipInfo;
import com.zyy.service.EquipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/equip")
public class EquipInfoController {

    @Autowired
    EquipInfoService equipInfoService;

    @ResponseBody
    @RequestMapping("/show")
    public List<EquipInfo> show(){
        return equipInfoService.findAll();
    }
}
