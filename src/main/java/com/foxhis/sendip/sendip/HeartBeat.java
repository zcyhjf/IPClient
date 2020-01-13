package com.foxhis.sendip.sendip;


import com.foxhis.sendip.sendip.websocket.WebSocketClientTest;

public class HeartBeat extends Thread {

    WebSocketClientTest webSocket;

    public HeartBeat(){

    }

    public HeartBeat(WebSocketClientTest webSocketClientTest){
        this.webSocket = webSocketClientTest;
    }


    public void run(){
        try {
            while (true){
                webSocket.send("alive");
                System.out.println("客户端发送心跳");
                Thread.sleep(5000);
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("服务器连接失败,重试中");
        }
    }
}