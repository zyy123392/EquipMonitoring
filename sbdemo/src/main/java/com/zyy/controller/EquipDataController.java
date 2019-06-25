package com.zyy.controller;

import com.zyy.model.EquipData;
import com.zyy.model.MyResult;
import com.zyy.model.StatisticResult;
import com.zyy.service.EquipDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/data")
public class EquipDataController {

    @Autowired
    private EquipDataService equipDataService;
    @ResponseBody
    @PostMapping("/add")
    public MyResult add(EquipData equipData){
        return equipDataService.insert(equipData);
    }
    @ResponseBody
    @RequestMapping("/test")
    public void test(){
        System.out.println("dsdd");
    }
    @ResponseBody
    @RequestMapping("/show")
    public List<EquipData> show(){
        return equipDataService.showLatest();
    }

    @RequestMapping("/statistics")
    public List<StatisticResult> statistics(@RequestParam("type") String type){
        System.out.println(type);
        if(type.equals("week")){
            List<StatisticResult> res = equipDataService.statistics(7,4,5);
            return res;
        }else if(type.equals("month")){
            List<StatisticResult> res = equipDataService.statisticsMonth(4,2);
            return res;
        }else if(type.equals("year")){
            List<StatisticResult> res = equipDataService.statisticsYear(1,2);
            return res;
        }


        return null;
    }

}
