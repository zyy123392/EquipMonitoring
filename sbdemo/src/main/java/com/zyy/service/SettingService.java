package com.zyy.service;

import com.zyy.model.MyResult;
import com.zyy.model.SetData;

public interface SettingService {
    MyResult updateSetting(SetData record);

    SetData getSetting();

    int updateSettingTem(int settingTem);

    SetData selectTem();

    int updateStatus(int status);

    SetData selectByPrimaryKey();
}
