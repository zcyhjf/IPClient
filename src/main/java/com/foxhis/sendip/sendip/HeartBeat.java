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
                Thread.sleep(5000);
                webSocket.send("alive");
                System.out.println("客户端发送心跳");
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("服务器连接失败,重试中");
            try {
                webSocket.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}