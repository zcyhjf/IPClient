package com.foxhis.sendip.sendip;

import com.foxhis.sendip.sendip.websocket.WebSocketClientTest;
import com.foxhis.sendip.sendip.websocket.WebSocketUtil;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.*;

@Component
public class SendIP implements ApplicationRunner {

    @Value("${sendaddress}")
    String address;

    @Value("${hotelid}")
    String hotelid;

    @Value("${tenantid}")
    String tenantid;


    private  Boolean isConnect = true;

    private  Boolean isCreate = false;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        WebSocketClientTest webSocket = new WebSocketClientTest(URI.create(WebSocketUtil.parseURL(address,tenantid,hotelid)));
        webSocket.connect();
        while(true){
            Thread.sleep(3000);
            isConnect = webSocket.isOpen();
            if (isConnect && !isCreate){
                new IPChange(tenantid,hotelid,webSocket).start();
                new HeartBeat(webSocket).start();
                isCreate = true;
            }else if (!isConnect){
                webSocket.reconnect();
                Thread.sleep(2000);
                isConnect = !webSocket.isClosed();
                isCreate = false;
            }
        }
    }
}