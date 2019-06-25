package com.zyy.sbdemo;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static com.zyy.util.Util.bytesToHexString;
import static com.zyy.util.Util.hexStrToByteArrs;

public class TCPdemo {

    public static void main(String[] args) throws IOException {
        Socket a = new Socket("192.168.99.15",5000);
        OutputStream os = a.getOutputStream();
        byte[] cmd = hexStrToByteArrs("50 fa 50 01 00 51");

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
            strReturn= bytesToHexString(buf);
            System.out.println(strReturn);


        //a.shutdownInput();
        //os.close();
        //is.close();
        a.close();
    }
}
