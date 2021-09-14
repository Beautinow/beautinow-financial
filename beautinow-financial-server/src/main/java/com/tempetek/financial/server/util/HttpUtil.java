package com.tempetek.financial.server.util;

import com.tempetek.financial.server.model.ReqBaseParamDTO;
import com.tempetek.financial.server.model.ReqListOrderDTO;
import com.tempetek.financial.server.model.ReqQueryOrderDTO;
import com.tempetek.financial.server.model.ReqTimeRefundOrderDTO;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    public static String HttpPostData(String url, String data, String accountId, String applicationKey) {
        String result = null;

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建一个post对象
            HttpPost post = new HttpPost(url);
            //创建一个Entity。模拟一个表单
            StringEntity entity = new StringEntity(data,"UTF-8");
            post.setEntity(entity);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("api-auth-accountid", accountId);
            post.setHeader("api-auth-applicationkey", applicationKey);
            //包装成一个Entity对象
            //设置请求的内容
            post.setEntity(entity);
            //执行post请求
            CloseableHttpResponse response = httpClient.execute(post);
            result = EntityUtils.toString(response.getEntity());
            response.close();
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String HttpPostData(String url, String data) {
        String result = null;

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建一个post对象
            HttpPost post = new HttpPost(url);
            //创建一个Entity。模拟一个表单
            StringEntity entity = new StringEntity(data,"UTF-8");
            post.setEntity(entity);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Accept-Encoding", "*");
            //包装成一个Entity对象
            //设置请求的内容
            post.setEntity(entity);
            //执行post请求
            CloseableHttpResponse response = httpClient.execute(post);
            result = EntityUtils.toString(response.getEntity());
            response.close();
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String HttpPostSAPData(String url, String data, String method, String uid, String appkey) {
        String result = null;

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建一个post对象
            HttpPost post = new HttpPost(url);
            //创建一个Entity。模拟一个表单
            StringEntity entity = new StringEntity(data,"UTF-8");
            post.setEntity(entity);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("method", method);
            Calendar date = Calendar.getInstance();
            String year = String.valueOf(date.get(Calendar.YEAR));
            post.setHeader("timestamp", year);
            post.setHeader("uid", uid);
            String sign = method + "_" + year + "_" + appkey;
            post.setHeader("sign", EncryptUtil.MD5Encrypt(sign));
            //包装成一个Entity对象
            //设置请求的内容
            post.setEntity(entity);
            //执行post请求
            CloseableHttpResponse response = httpClient.execute(post);
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
            response.close();
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String HttpGetData(String url, String accountId, String applicationKey) {
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000000).setSocketTimeout(3000000).build();
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("Content-Type", "application/json");
            httpget.setHeader("api-auth-accountid", accountId);
            httpget.setHeader("api-auth-applicationkey", applicationKey);
            httpget.setConfig(requestConfig);
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);

            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String HttpPutData(String url, String data, String accountId, String applicationKey) {
        String result = null;
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        try {
            HttpPut httpput = new HttpPut(url);
            //设置header
            httpput.setHeader("Content-Type", "application/json");
            httpput.setHeader("api-auth-accountid", accountId);
            httpput.setHeader("api-auth-applicationkey", applicationKey);
            //组织请求参数
            StringEntity stringEntity = new StringEntity(data, "UTF-8");
            httpput.setEntity(stringEntity);
            CloseableHttpResponse  httpResponse = null;
            //响应信息
            httpResponse = closeableHttpClient.execute(httpput);
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String HttpDeleteData(String url, String accountId, String applicationKey) {
        String result = null;
        //since 4.3 不再使用 DefaultHttpClient
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        try {
            HttpDelete httpdelete = new HttpDelete(url);
            httpdelete.setHeader("Content-Type", "application/json");
            httpdelete.setHeader("api-auth-accountid", accountId);
            httpdelete.setHeader("api-auth-applicationkey", applicationKey);
            CloseableHttpResponse httpResponse = null;
            httpResponse = closeableHttpClient.execute(httpdelete);
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String user = "info@bfuturist.com";
        String secretKey = "f0dca1a0c0aeb134dc6d254b1dc55a01";
        String method = "v3.TimeRefundOrder";
        Long time = System.currentTimeMillis();
        Long startTime = DateUtils.preDay();
        Long endTime = DateUtils.nextDay();
        Map<String, String> map = new HashMap<>();
        map.put("user", user);
        map.put("method", method);
        map.put("start_time", startTime+"");
        map.put("end_time", endTime+"");
        map.put("time", time+"");
        String result = EncryptUtil.formatUrlMap(map, false, false);
        System.out.println(result);
        String code = EncryptUtil.SHA256Encrypt(result, secretKey);
        ReqBaseParamDTO reqBaseParamDTO = new ReqBaseParamDTO();
        reqBaseParamDTO.setMethod("v3.TimeRefundOrder");
        reqBaseParamDTO.setTime(time);
        reqBaseParamDTO.setSign(code);
        reqBaseParamDTO.setUser(user);
        ReqTimeRefundOrderDTO reqTimeRefundOrderDTO = new ReqTimeRefundOrderDTO();
        reqTimeRefundOrderDTO.setEnd_time(endTime);
        reqTimeRefundOrderDTO.setStart_time(startTime);
        reqBaseParamDTO.setData(reqTimeRefundOrderDTO);
        String jsonObject = HttpPostData("https://mapi.yabandpay.com/Payments", JSONObject.fromObject(reqBaseParamDTO).toString());
        System.out.println(jsonObject);
    }

    public static String getTimeRefundTime() {
        String user = "info@bfuturist.com";
        String secretKey = "f0dca1a0c0aeb134dc6d254b1dc55a01";
        String method = "v3.ListOrderByTime";
        Long time = System.currentTimeMillis();
        Long startTime = DateUtils.preDay();
        Long endTime = DateUtils.nextDay();
        Map<String, String> map = new HashMap<>();
        map.put("user", user);
        map.put("method", method);
        map.put("start_time", startTime+"");
        map.put("end_time", endTime+"");
        map.put("state", "paid");
        map.put("time", time+"");
        String result = EncryptUtil.formatUrlMap(map, false, false);
        System.out.println(result);
        String code = EncryptUtil.SHA256Encrypt(result, secretKey);
        ReqBaseParamDTO reqBaseParamDTO = new ReqBaseParamDTO();
        reqBaseParamDTO.setMethod("v3.ListOrderByTime");
        reqBaseParamDTO.setTime(time);
        reqBaseParamDTO.setSign(code);
        reqBaseParamDTO.setUser(user);
        ReqTimeRefundOrderDTO reqTimeRefundOrderDTO = new ReqTimeRefundOrderDTO();
        reqTimeRefundOrderDTO.setEnd_time(endTime);
        reqTimeRefundOrderDTO.setStart_time(startTime);
        reqTimeRefundOrderDTO.setState("paid");
        reqBaseParamDTO.setData(reqTimeRefundOrderDTO);
        String jsonObject = HttpPostData("https://mapi.yabandpay.com/Payments", JSONObject.fromObject(reqBaseParamDTO).toString());
        System.out.println(jsonObject);
        return jsonObject;
    }

    public static String getQueryOrder(String tradeId) {
        String user = "info@bfuturist.com";
        String secretKey = "f0dca1a0c0aeb134dc6d254b1dc55a01";
        String method = "v3.QueryOrder";
        Long time = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("user", user);
        map.put("method", method);
        map.put("trade_id", tradeId);
        map.put("time", time+"");
        String result = EncryptUtil.formatUrlMap(map, false, false);
        System.out.println(result);
        String code = EncryptUtil.SHA256Encrypt(result, secretKey);
        ReqBaseParamDTO reqBaseParamDTO = new ReqBaseParamDTO();
        reqBaseParamDTO.setMethod(method);
        reqBaseParamDTO.setTime(time);
        reqBaseParamDTO.setSign(code);
        reqBaseParamDTO.setUser(user);
        ReqQueryOrderDTO reqQueryOrderDTO = new ReqQueryOrderDTO();
        reqQueryOrderDTO.setTrade_id(tradeId);
        reqBaseParamDTO.setData(reqQueryOrderDTO);
        String jsonObject = HttpPostData("https://mapi.yabandpay.com/Payments", JSONObject.fromObject(reqBaseParamDTO).toString());
        System.out.println(jsonObject);
        return jsonObject;
    }

    public static String queryListOrder(ReqListOrderDTO reqListOrderDTO) {
        String user = "info@bfuturist.com";
        String secretKey = "f0dca1a0c0aeb134dc6d254b1dc55a01";
        String method = "v3.ListOrder";
        Long time = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("user", user);
        map.put("method", method);
        map.put("time", time+"");
        map.put("page", reqListOrderDTO.getPage());
        map.put("limit", reqListOrderDTO.getLimit());
        map.put("filter", reqListOrderDTO.getFilter());
        String result = EncryptUtil.formatUrlMap(map, false, false);
        System.out.println(result);
        String code = EncryptUtil.SHA256Encrypt(result, secretKey);
        ReqBaseParamDTO reqBaseParamDTO = new ReqBaseParamDTO();
        reqBaseParamDTO.setMethod(method);
        reqBaseParamDTO.setTime(time);
        reqBaseParamDTO.setSign(code);
        reqBaseParamDTO.setUser(user);
        reqBaseParamDTO.setData(reqListOrderDTO);
        String jsonObject = HttpPostData("https://mapi.yabandpay.com/Payments", JSONObject.fromObject(reqBaseParamDTO).toString());
        System.out.println(jsonObject);
        return jsonObject;
    }
}
