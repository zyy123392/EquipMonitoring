package com.zyy.service;

import com.zyy.model.EquipData;
import com.zyy.model.MonitorLog;
import com.zyy.model.SetData;
import com.zyy.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
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
    @Autowired
    WebSocketServer webSocketServer;
    @Autowired
    MonitorService monitorService;

    static double tmptem = Double.MAX_VALUE;



    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //项目部署时，会在这里执行一次，从数据库拿到cron表达式
        SetData setting = settingService.getSetting();
        cron = "0/"+setting.getTimeslot()+" * * * * *";

        Runnable task = new Runnable() {
            @Override
            public void run() {
                //任务逻辑代码部分.
                System.out.println("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yy-MM-dd HH:mm:ss");
                String str = sdf.format(new Date());
                SetData setting = settingService.getSetting();
                List<EquipData> res = equipDataService.showLatest();
                for(int i = 0;i < res.size();i++){
                    if(res.get(i).getTem() > setting.getTemthreshold()){
                        if(tmptem > res.get(i).getTem()){
                            tmptem = res.get(i).getTem();

                        }else{
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

                                    webSocketServer.sendInfo(str+","+res.get(i).getTem()+","+tem,null);

                                    MonitorLog tmp = new MonitorLog();
                                    tmp.setId(Util.getUUID());
                                    tmp.setTem(res.get(i).getTem());
                                    tmp.setTime(str);
                                    tmp.setSettingtem(tem);
                                    tmp.setStatus("已超过限定温度");
                                    tmp.setOperation("已自动调低1度。");
                                    monitorService.insert(tmp);


                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println(a);
                        }


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
                cron = "0/"+setting.getTimeslot()+" * * * * *";

                CronTrigger trigger = new CronTrigger(cron);
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }
        };
        taskRegistrar.addTriggerTask(task, trigger);
    }

    public void setCron(String cron) {
        this.cron = cron;
    }


}
