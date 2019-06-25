package com.zyy.service.impl;

import com.zyy.dao.SetDataMapper;
import com.zyy.model.MyResult;
import com.zyy.model.SetData;
import com.zyy.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "settingService")
public class SettingServiceImpl implements SettingService {

    @Autowired
    SetDataMapper setDataMapper;
    @Override
    public MyResult updateSetting(SetData record) {
        MyResult result = new MyResult();
        result.setCode(setDataMapper.updateByPrimaryKey(record));
        if(result.getCode() == 1){
            result.setMsg("设置成功！");
            System.out.println("设置成功！");
        }else{
            result.setMsg("设置失败！");
            System.out.println("设置失败");
        }
        return result;
    }

    @Override
    public SetData getSetting() {
        SetData res = setDataMapper.selectByPrimaryKey();

        return res;
    }

    @Override
    public int updateSettingTem(int settingTem) {
        int i = setDataMapper.updateSettingTem(settingTem);

        return i;
    }

    @Override
    public SetData selectTem() {
        SetData res = setDataMapper.selectTem();
        return res;
    }

    @Override
    public int updateStatus(int status) {
        int i = setDataMapper.updateStatus(status);
        return i;
    }

    @Override
    public SetData selectByPrimaryKey() {
        SetData res = setDataMapper.selectByPrimaryKey();
        return res;
    }
}
