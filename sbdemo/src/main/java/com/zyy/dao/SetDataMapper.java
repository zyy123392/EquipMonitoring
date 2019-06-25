package com.zyy.dao;

import com.zyy.model.SetData;

public interface SetDataMapper {
    int deleteByPrimaryKey(String id);

    int insert(SetData record);

    int insertSelective(SetData record);

    SetData selectByPrimaryKey();

    int updateByPrimaryKeySelective(SetData record);

    int updateByPrimaryKey(SetData record);

    int updateSettingTem(int settingTem);

    SetData selectTem();

    int updateStatus(int status);

}