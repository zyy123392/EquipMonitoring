package com.zyy.service;

import com.zyy.model.EquipData;
import com.zyy.model.SetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class MonitorTemperature implements SchedulingConfigurer {

    @Autowired
    EquipDataService equipDataService;
    @Autowired
    AirConditionService airConditionService;
    @Autowired
    SettingService settingService;
    public static String cron;



    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //项目部署时，会在这里执行一次，从数据库拿到cron表达式
        SetData setting = settingService.getSetting();
        cron = "0/"+setting.getTimeslot()*60+" * * * * *";

        Runnable task = new Runnable() {
            @Override
            public void run() {
                //任务逻辑代码部分.
                System.out.println("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
                SetData setting = settingService.getSetting();
                List<EquipData> res = equipDataService.showLatest();
                for(int i = 0;i < res.size();i++){
                    if(res.get(i).getTem() > setting.getTemthreshold()){
                        try {
                            airConditionService.turnOn();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(1000 * 3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String a = null;
                        try {
                            SetData set = settingService.selectTem();
                            int tem = set.getSettingTem();
                            int sta =set.getStatus();
                            if(tem > 16 && tem < 30 && sta == 1){
                                a = airConditionService.turnDown(tem - 1);
                                int b = settingService.updateSettingTem(tem -1);

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(a);
                    }
                }
                System.out.println();

            }
        };
        Trigger trigger = new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                //任务触发，可修改任务的执行周期.
                //每一次任务触发，都会执行这里的方法一次，重新获取下一次的执行时间
                SetData setting = settingService.getSetting();
                cron = "0/"+setting.getTimeslot()*60+" * * * * *";

                CronTrigger trigger = new CronTrigger(cron);
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }
        };
        taskRegistrar.addTriggerTask(task, trigger);
    }

}
