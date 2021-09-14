package com.tempetek.financial.server.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class EncryptUtil {

    public static String MD5Encrypt(String plainText) {
        String result = "";
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        messageDigest.update(plainText.getBytes());
        byte b[] = messageDigest.digest();
        int i;
        StringBuffer stringBuffer = new StringBuffer("");

        for (int offset = 0; offset < b.length; offset ++) {
            i = b[offset];

            if (i < 0) {
                i += 256;
            }

            if (i < 16) {
                stringBuffer.append("0");
            }

            stringBuffer.append(Integer.toHexString(i));
        }

        result = stringBuffer.toString().toUpperCase();
        return result;
    }

    public static String SHA256Encrypt(String plainText, String secretKey) {
        String result = "";
        Mac mac = null;
        SecretKeySpec secretKeySpec = null;

        try {
            mac = Mac.getInstance("HmacSHA256");
            secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte b[] = mac.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        String encodeStr = Base64.encodeBase64String(b);
        result = byte2Hex(b);
        return result;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }



    public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower) {
        String buff = "";
        Map<String, String> tmpMap = paraMap;

        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });

            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();

            for (Map.Entry<String, String> item : infoIds) {
                if (StringUtils.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    String val = item.getValue();

                    if (urlEncode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }

                    if (keyToLower) {
                        buf.append(key.toLowerCase() + "=" + val);
                    } else {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }
            }

            buff = buf.toString();

            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            return null;
        }
        return buff;
    }

    public static void main(String[] args) {
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
        String result = formatUrlMap(map, false, false);
        System.out.println(result);
        String code = SHA256Encrypt(result, secretKey);
        System.out.println(code);
    }

}
