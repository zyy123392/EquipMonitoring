package com.zyy.service.impl;

import com.zyy.dao.EquipInfoMapper;
import com.zyy.model.EquipInfo;
import com.zyy.service.EquipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "equipInfoService")
public class EquipInfoServiceImpl implements EquipInfoService {
    @Autowired
    EquipInfoMapper equipInfoMapper;


    @Override
    public List<EquipInfo> findAll() {
        List<EquipInfo> res = equipInfoMapper.findAll();
        return res;
    }
}
