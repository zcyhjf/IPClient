package com.foxhis.sendip.sendip;

public class WebSocketUtil {
    public static String parseURL(String address){
        String protocol = "https".equals(address.split(":")[0])?"wss://":"ws://";
        String addr = address.split(":")[1].split("//")[1];
        String port ;
        try{
            port = address.split(":")[2];
        }catch (Exception e) {
            port = "";
        }
        String uri = protocol+addr+(port.equals("")?"":":"+port)+"/websocket/1/1";
        return uri;
    }
}
