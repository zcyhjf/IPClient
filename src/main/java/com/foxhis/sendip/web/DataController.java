package com.foxhis.sendip.web;

import com.alibaba.fastjson.JSONObject;
import com.foxhis.sendip.sendip.websocket.WebSocketClientTest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class DataController {

    @Value("${sendaddress}")
    String address;

    Logger logger = Logger.getLogger(DataController.class);

    @RequestMapping(value = "data" ,method = RequestMethod.GET)
    public void sendData(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Data","1234");
        jsonObject.put("Date",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String serverUrl = address+"/data";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(serverUrl);
        StringEntity param = null ;
        try {
            param = new StringEntity(jsonObject.toString(),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        httpPost.setEntity(param);
        try {
            httpClient.execute(httpPost);
            logger.info("向服务器"+address+"发送成功");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("发送失败");
        }
    }


 /*   @RequestMapping("/file")
    public String UploadFile(String filename) {
        //读取文件
        String fileUrl = dirurl+ File.separator+filename;
        FileBody fileBody = new FileBody(new File(fileUrl));
        Long starttime = System.currentTimeMillis();
        String serverUrl = address+"/upload?starttime="+starttime;
        //创建HttpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(serverUrl);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("utf-8"));
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("file",fileBody);
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);

        //执行提交
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
            return ("发送失败");
        }
        return "发送成功,耗时"+(System.currentTimeMillis()-starttime)/1000+"s";
    }
*/


}