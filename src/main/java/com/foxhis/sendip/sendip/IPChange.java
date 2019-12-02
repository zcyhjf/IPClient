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
            long lastSendTime = System.currentTimeMillis();
            while(true){
                Thread.sleep(20000);
                long currentTime = System.currentTimeMillis();
                //获取公网ip
                String remoteIp = GetRemoteIp.getV4Ip();
                if (!remoteIp.equals(oriIp) || currentTime-lastSendTime > MAX_SEND_INTERVAL) {
                    JSONObject IPJson = new JSONObject();
                    IPJson.put("hotelid",hotelid);
                    IPJson.put("tenantid",tenantid);
                    IPJson.put("Date",sdf.format(new Date()));
                    IPJson.put("IP",remoteIp+":"+localport);
                    String IPString = IPJson.toJSONString();
                    webSocket.send(IPString);
                    oriIp = remoteIp;
                    System.out.println("发送成功" + remoteIp);
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