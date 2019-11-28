package com.foxhis.sendip.web;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

@RestController
public class FileController {


    @Value("${dirurl}")
    String dirurl;

    @Value("${send.ip}")
    String ip;

    @Value("${file.port}")
    String port;

    @RequestMapping("/file")
    public String UploadFile(String filename) {
        //读取文件
        String fileUrl = dirurl+ File.separator+filename;
        FileBody fileBody = new FileBody(new File(fileUrl));
        Long starttime = System.currentTimeMillis();
        String serverUrl = "http://"+ip+":"+port+"/upload?starttime="+starttime;
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

}