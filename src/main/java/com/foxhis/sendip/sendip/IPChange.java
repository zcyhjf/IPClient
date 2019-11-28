package com.foxhis.sendip.sendip;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IPChange extends Thread {

    String tenantid;
    String hotelid;
    String localport;
    WebSocketClientTest webSocket;
    private static final long MAX_SEND_INTERVAL = 5*60*1000;//最大发送间隔5分钟

    public IPChange(){

    }
    public IPChange(String tenantid,String hotelid,WebSocketClientTest webSocket,String localport ){
        this.hotelid = hotelid;
        this.tenantid = tenantid;
        this.webSocket = webSocket;
        this.localport = localport;
    }


    public void run(){
        String oriIp = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //this.socket = new Socket(ip,port);
            long lastSendTime = System.currentTimeMillis();
            while(true){
                Thread.sleep(10000);
                long currentTime = System.currentTimeMillis();
                InetAddress host = InetAddress.getLocalHost();
                String localIp = host.getHostAddress();
                JSONObject IPJson = new JSONObject();
                IPJson.put("hotelid",hotelid);
                IPJson.put("tenantid",tenantid);
                IPJson.put("Date",sdf.format(new Date()));
                IPJson.put("IP",localIp+":"+localport);
                String IPString = IPJson.toJSONString();
                if (!localIp.equals(oriIp) || currentTime-lastSendTime > MAX_SEND_INTERVAL) {
                    webSocket.send(IPString);
                    oriIp = localIp;
                    System.out.println("发送成功" + localIp);
                    lastSendTime = System.currentTimeMillis();
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("IP发送失败，服务器未连接");
            try {
                webSocket.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}