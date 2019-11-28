package com.foxhis.sendip.sendip;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HeartBeat extends Thread {

    WebSocketClientTest webSocket;

    public HeartBeat(){

    }

    public HeartBeat(WebSocketClientTest webSocketClientTest){
        this.webSocket = webSocketClientTest;
    }


    public void run(){
        try {
            //this.socket = new Socket(ip,port);
            //DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //dataOutputStream.write(0);
            while (true){
                Thread.sleep(5000);
                //dataOutputStream.writeUTF("alive");
                webSocket.send("alive");
                System.out.println("客户端发送心跳");
                //dataOutputStream.flush();
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