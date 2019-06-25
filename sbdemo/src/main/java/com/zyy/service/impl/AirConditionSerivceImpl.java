package com.zyy.service.impl;

import com.zyy.service.AirConditionService;
import com.zyy.service.SettingService;
import com.zyy.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


@Service(value = "airConditionService")
public class AirConditionSerivceImpl implements AirConditionService {
    private final String TURNON = "50 fa 50 01 00 51";
    private final String TURNOFF = "50 fa 50 02 00 52";
    private final String TURNOUP = "50 fa 50 03 00 53";
    private final String TURNODOWN = "50 fa 50 04 00 54";

    @Autowired
    SettingService settingService;

    public String sendCommend(String commend)throws IOException {
        Socket a = new Socket("192.168.99.15",5000);
        OutputStream os = a.getOutputStream();
        byte[] cmd = Util.hexStrToByteArrs(commend);

        os.write(cmd);
        //a.shutdownOutput();
        //接收服务器的响应
        int len = 0;
        InputStream is=a.getInputStream();
        byte[] buf = new byte[6];
        String strReturn = null;
        //接收收到的数据
        len=is.read(buf);
        //将字节数组转换成十六进制的字符串
        strReturn= Util.bytesToHexString(buf);

        a.close();
        return strReturn;
    }
    @Override
    public String turnDown(int tem) throws IOException {

        if(tem > 16 && tem < 30){
            String com = "50 "+Util.intToHex(tem)+" 00";

            String com1 = Xor(com);
            String res = sendCommend("50 fa "+com1);
            if(res.equals("50 FC 03 00 00 03 ")){
                int i = settingService.updateSettingTem(tem);
                return "发送成功！当前设定温度为"+tem+"℃";
            }else{
                return "发送失败！";
            }
        }else{
            return "设置温度超出范围！";
        }


    }

    @Override
    public String turnOn() throws IOException {
        String res = sendCommend(TURNON);
        if(res.equals("50 FC 03 00 00 03 ")){
            settingService.updateStatus(1);
            return "发送成功！空调已开启，自动监控功能已开启。";
        }else{
            System.out.println(res);
            return "发送失败！";
        }
    }

    @Override
    public String turnUp(int tem) throws IOException {
        if(tem > 16 && tem < 30){
            String HexTem = Util.intToHex(tem);
            String com = "50 "+HexTem+" 00";
            System.out.println(com);
            String com1 = Xor(com);
            String res = sendCommend("50 fa "+com1);
            System.out.println("50 fa "+com1);
            if(res.equals("50 FC 03 00 00 03 ")){
                int i = settingService.updateSettingTem(tem);
                return "发送成功！当前设定温度为"+tem+"℃";
            }else{
                System.out.println(res);
                return "发送失败！";
            }
        }else{
            return "设定温度超出范围！";
        }

    }

    @Override
    public String turnOff() throws IOException {
        String res = sendCommend(TURNOFF);
        if(res.equals("50 FC 03 00 00 03 ")){
            settingService.updateStatus(0);
            return "发送成功！空调已关闭，自动监控功能已关闭。";
        }else{
            return "发送失败！";
        }
    }

    public String Xor(String hex){
        byte[] res = Util.hexStrToByteArrs(hex);
        int a = res[0] ^ res[1] ^ res[2];
        byte[] res1 = new byte[4];
        for(int i = 0;i < res.length;i++){
            res1[i] = res[i];
        }
        res1[3] = Byte.parseByte(String.valueOf(a));
        String res2 = Util.bytesToHexString(res1);
        return res2;
    }

}
