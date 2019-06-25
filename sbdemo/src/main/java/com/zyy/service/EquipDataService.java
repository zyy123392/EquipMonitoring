package com.zyy.service;

import com.zyy.model.EquipData;
import com.zyy.model.MyResult;
import com.zyy.model.StatisticResult;

import java.util.List;

public interface EquipDataService {
    MyResult insert(EquipData record);
    List<EquipData> showLatest();
    List<StatisticResult> statistics(int days,int index,int length);
    List<StatisticResult> statisticsMonth(int index,int length);
    List<StatisticResult> statisticsYear(int index,int length);

}
