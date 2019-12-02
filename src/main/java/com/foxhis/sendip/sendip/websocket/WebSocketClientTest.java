package com.foxhis.sendip.sendip.websocket;

import org.apache.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketClientTest extends WebSocketClient {

    Logger logger = Logger.getLogger(WebSocketClientTest.class);

    public WebSocketClientTest(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake arg0) {
        // TODO Auto-generated method stub
        logger.info("------ MyWebSocket onOpen ------");
    }

    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
        // TODO Auto-generated method stub
        logger.info("------ MyWebSocket onClose ------");
    }

    @Override
    public void onError(Exception arg0) {
        // TODO Auto-generated method stub
        logger.info("------ MyWebSocket onError ------");
    }

    @Override
    public void onMessage(String arg0) {
        // TODO Auto-generated method stub
        logger.info("-------- 接收到服务端数据： " + arg0 + "--------");
    }
}
