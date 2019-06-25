package com.zyy.service.impl;

import com.zyy.dao.EquipHistoryDataMapper;
import com.zyy.dao.EquipTmpDataMapper;
import com.zyy.model.EquipData;
import com.zyy.model.MyResult;
import com.zyy.model.StatisticResult;
import com.zyy.service.EquipDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "equipDataService")
@Transactional
public class EquipDateServiceImpl implements EquipDataService {

    @Autowired
    EquipHistoryDataMapper equipHistoryDataMapper;
    @Autowired
    EquipTmpDataMapper equipTmpDataMapper;

    @Override
    public MyResult insert(EquipData record) {
        MyResult result = new MyResult();
        result.setCode(equipHistoryDataMapper.insert(record));
        if(result.getCode() == 1){
            result.setMsg("添加成功！");
            System.out.println("添加成功！");
        }else{
            result.setMsg("添加失败！");
            System.out.println("添加失败！");
        }
        return null;
    }

    @Override
    public List<EquipData> showLatest() {
        MyResult result = new MyResult();
        List<EquipData> res = equipTmpDataMapper.findLatest();

        return res;
    }

    @Override
    public List<StatisticResult> statistics(int days,int index,int length) {
        List<StatisticResult> res = equipHistoryDataMapper.statistics(days,index,length);
        return res;
    }

    @Override
    public List<StatisticResult> statisticsMonth(int index, int length) {
        List<StatisticResult> res = equipHistoryDataMapper.statisticsMonth(index,length);
        return res;
    }

    @Override
    public List<StatisticResult> statisticsYear(int index, int length) {
        List<StatisticResult> res = equipHistoryDataMapper.statisticsYear(index,length);
        return res;
    }

}
