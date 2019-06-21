package cn.xintian.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientUtils {
    /**
     * 以jsonString形式发送HttpPost的Json请求，String形式返回响应结果
     *
     * @param url
     * @param jsonString
     * @return
     */
    public static String sendPostJsonStr(String url, String jsonString) throws Exception {
        if (jsonString == null || jsonString.isEmpty()) {
            return sendPost(url);
        }
        String resp = "";
        StringEntity entityStr = new StringEntity(jsonString, ContentType.create("text/plain", "UTF-8"));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entityStr);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, "UTF-8");
            EntityUtils.consume(entity);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        if (resp == null || resp.equals("")) {
            return "";
        }
        return resp;
    }

    /**
     * 发送不带参数的HttpPost请求
     *
     * @param url
     * @return
     */
    public static String sendPost(String url) throws Exception {
        // 1.获得一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 2.生成一个post请求
        HttpPost httppost = new HttpPost(url);
        CloseableHttpResponse response = null;
        // 3.执行get请求并返回结果
        response = httpclient.execute(httppost);
        // 4.处理结果，这里将结果返回为字符串
        HttpEntity entity = response.getEntity();
        String result = null;
        result = EntityUtils.toString(entity);
        return result;
    }
}
