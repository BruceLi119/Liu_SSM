package cn.xintian.controller;

import cn.xintian.domain.WaterSupply;
import cn.xintian.service.AccessTokenService;
import cn.xintian.util.HttpClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sendMessage")
@Api(value="微信模板发送接口",description = "模板发送的前期准备（获取token、用户的openid等到）以及完成发送")
public class SendMessageController {

    @Reference
    private static AccessTokenService accessTokenService;

    //定义我们自己的appid和appsecret
    private static String appid = "wxef8aaf3360204c91";
    private static String appsecret = "wxef8aaf3360204c91";

    //获取到的access_token
    private static String access_token = "";
    //用于存储OpenId的ArrayList
    private static List<String> arrayList = new ArrayList();
    //获取到的此次请求最后一位OpenId
    private static String LastOpenId = "";
    //定义统计初始值
    private static int count = 0;
    //定义统计总量
    private static Integer total = 0;
    //定义发送次数
    private static int sendCount = 0;

/*    public static void main(String[] args) throws Exception {
        //String openId, String orderNum, String goodsName, String buyerName,
        //                                             String consigneeAddress, String consigneePhone,
        //                                             String orderRemark
        //SendTemplateMessage("oluCT59YJP08VnCq6m5ngGoK27OY","9873","好东西","不知道啊","高新区你随便送","13676442344","滴滴滴");
//        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=22_BcXrrooAbaTPb9D-sWwod2eNH9UDvHWnfOhF4CKmSVzgWQPvHwwCOT3GMuo-jaRC3vu0yNGnuEAoZ2dvsZiO2AM-zRRcYArJeZyNlaJHNUx3Ek6bc4U-DlBSqkhHbUAaRh0n22flklRyvSE6VFLbAHANXD&next_openid=";
//        getOpenId();
        WaterSupply waterSupply = new WaterSupply();
        waterSupply.setTitle("停水通知");
        waterSupply.setStopWaterStyle("紧急停水");
        waterSupply.setsTime("2019年5月14日18:00");
        waterSupply.seteTime("2019年5月14日21:00");
        waterSupply.setStopWaterArea("高新区");
        waterSupply.setStyle("滴滴滴");
        Send(waterSupply);
    }*/

    /**
     * 获取access_token
     * @return
     */
    @PostConstruct
    public static void getToken() {
        //定义并拼接好url
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + appsecret;
        //1.微信获取access_token
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        Map map = JSON.parseObject(exchange.getBody(), Map.class);
        //从JSON中获取到access_token
        access_token = (String) map.get("access_token");
        System.out.println("access_token:" + access_token);
        //将access_token存入到数据库中
//        accessTokenService.update(access_token);
    }

    /**
     * 获取全部openId的准备工作
     * @param url
     * @throws Exception
     */
    public static void getOpenIdMethod(String url) throws Exception {
        //构建get请求
        HttpGet httpGet = new HttpGet(url);
        //根据CloseableHttpClient创建好我们的httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //执行http请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //System.out.println(response);
        //根据response，获取数据
        HttpEntity entity = response.getEntity();
        //将数据转换为String
        String content = EntityUtils.toString(entity, Charset.forName("utf-8"));
        System.out.println("content:" + content);
        //将数据转换为JSONObject
        JSONObject exchangeJsonObject = JSON.parseObject(content);
        //通过键获取数据对象
        String data = exchangeJsonObject.getString("data");
        //获取记录统计
        total = exchangeJsonObject.getInteger("total");
        System.out.println("data:" + data);
        //将获取到的值转换为JSONObject
        JSONObject jsonObject = JSONObject.parseObject(data);
        //通过键获取对象
        String openid = jsonObject.getString("openid");
        List<String> strings = JSONArray.parseArray(openid, String.class);
        //获取最后一个OpenId
        LastOpenId = strings.get(strings.size() - 1);
        System.out.println("LastOpenId:" + LastOpenId);
        System.out.println("strings:" + strings);
        //将得到的新的openId集合添加到总的集合中
        arrayList.addAll(strings);
        System.out.println("arrayList:" + arrayList);
    }

    /**
     * 完成循环获取全部openId
     *
     * @return
     */
    public static void getOpenId() throws Exception {
        while (true) {
            if (count == 0) {
                //定义并拼接好url
                String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + access_token + "&next_openid=";
                getOpenIdMethod(url);
                count++;
            } else {
                //定义并拼接好url
                String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + access_token + "&next_openid=" + LastOpenId;
                getOpenIdMethod(url);
                count++;
            }
            //判断数据总量
            if (total<=10000){
                break;
            }else if (count == total/10000){
                break;
            }
        }
    }

    /**
     * 装配发送环境，并完成发送
     *
     * @param waterSupply
     * @throws Exception
     */
    @RequestMapping("/send")
 /*   //@Api 此接口的总体说明 value 的值不会显示在页面上 description 是此接口的主要描述
    @ApiOperation("分页查询页面列表") //接口中方法的说明
    @ApiImplicitParams({ //接口中参数的说明
            @ApiImplicitParam(name="page",value = "页码
                    ",required=true,paramType="path",dataType="int"),
                    @ApiImplicitParam(name="size",value = "每页记录数
                    ",required=true,paramType="path",dataType="int")})*/
    @ApiOperation("装配发送环境，并完成发送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "waterSupply",value = "实体类",required = true,dataType = "String")
    })
    public static void Send(@RequestBody WaterSupply waterSupply) throws Exception {
        //获取到token
//        access_token = accessTokenService.find();
        getToken();
        //获取用户的OpenId
        getOpenId();
        //获取到标题
        String title = waterSupply.getTitle();
        //开始时间
        String eTime = waterSupply.geteTime();
        //结束时间
        String sTime = waterSupply.getsTime();
        //停水类型
        String stopWaterStyle = waterSupply.getStopWaterStyle();
        //备注/类型
        String style = waterSupply.getStyle();
        //停水区域
        String stopWaterArea = waterSupply.getStopWaterArea();
        for (int i = 0; i < total; i++) {
            String s = SendTemplateMessage(title, stopWaterStyle, sTime, eTime, stopWaterArea, style);
            System.out.println(s);
        }

        //推送完消息，将变量重新初始化

        //获取到的access_token
        access_token = "";
        //用于存储OpenId的ArrayList
        arrayList = new ArrayList();
        //获取到的此次请求最后一位OpenId
        LastOpenId = "";
        //定义统计初始值
        count = 1;
        //定义统计总量
        total = 0;
        //定义发送次数
        sendCount = 0;
    }

    /**
     * 发送模板消息
     *
     * @param title
     * @param stopWaterStyle
     * @param sTime
     * @param eTime
     * @param stopWaterArea
     * @param style
     * @return
     * @throws Exception
     */
    public static String SendTemplateMessage(String title, String stopWaterStyle, String sTime,
                                             String eTime, String stopWaterArea, String style) throws Exception {
//        getToken();
//        getOpenId();
        //2.发送模板消息
        //发送模板消息的微信接口
        String postUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;
        //2.1 封装模板数据
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", arrayList.get(sendCount));// 要接收模板消息的微信用户openId
        jsonObject.put("template_id", "hY978Q-V0YAVGA8wdE7LqVkkRafu3sXOOPmzLn-_pzY");   //所使用的消息模板ID
//        jsonObject.put("url", "http://www.baidu.com");   //跳转的路径(非必须)
        JSONObject first = new JSONObject();
        JSONObject data = new JSONObject();
        first.put("value", title);
        first.put("color", "#173177");
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value", stopWaterStyle);
        keyword1.put("color", "#173177");
        JSONObject keyword2 = new JSONObject();
        keyword2.put("value", eTime + "-" + sTime);
        keyword2.put("color", "#173177");
        JSONObject keyword3 = new JSONObject();
        keyword3.put("value", stopWaterArea);
        keyword3.put("color", "#173177");
        JSONObject remark = new JSONObject();
        remark.put("value", "备注:" + (style == null ? "无" : style));
        remark.put("color", "#173177");

        data.put("first", first);
        data.put("keyword1", keyword1);
        data.put("keyword2", keyword2);
        data.put("keyword3", keyword3);
        data.put("remark", remark);
        jsonObject.put("data", data);
        //2.2 发送模板消息
        String s = "";
        try {
            s = HttpClientUtils.sendPostJsonStr(postUrl, jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject result = JSON.parseObject(s);
        int errcode = result.getIntValue("errcode");
        if (errcode == 0) {
            // 发送成功
            sendCount++;
            if (sendCount == total) {
                sendCount = 0;
            }
            return "success";
        } else {
            // 发送失败
            return "error";
        }
    }
}