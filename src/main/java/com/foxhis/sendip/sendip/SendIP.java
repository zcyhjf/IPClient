package com.foxhis.sendip.sendip;

import com.foxhis.sendip.sendip.websocket.WebSocketClientTest;
import com.foxhis.sendip.sendip.websocket.WebSocketUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.*;

@Component
public class SendIP implements ApplicationRunner {

    @Value("${sendaddress}")
    String address;

    @Value("${send.ip}")
    String ip;

    @Value("${send.port}")
    Integer port;

    @Value("${hotelid}")
    String hotelid;

    @Value("${tenantid}")
    String tenantid;

    @Value("${server.port}")
    String localport;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        WebSocketClientTest webSocket = new WebSocketClientTest(URI.create(WebSocketUtil.parseURL(address,tenantid,hotelid)));
        webSocket.connect();
        new IPChange(tenantid,hotelid,webSocket,localport).start();
        new HeartBeat(webSocket).start();
        while(true) {
            if (webSocket.isClosed()) {
                try{
                    webSocket.reconnect();
                    if (!webSocket.isClosed()){
                        Thread.sleep(3000);
                        new IPChange(tenantid,hotelid,webSocket,localport).start();
                        new HeartBeat(webSocket).start();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("重连中");
                }
            }
            Thread.sleep(2000);
        }
        //new IsAlive(socket).start();
    }
}