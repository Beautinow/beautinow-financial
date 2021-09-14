package com.tempetek.financial.server.util;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class ParseUtil {

    public static <T> List<T> getResults(String jsonString, Class cls) {
        List<T> list = new ArrayList<T>();

        try {
            list = JSON.parseArray(jsonString,cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> T getResult(String jsonString ,Class cls) {
        T t = null;

        try {
            t = (T) JSON.parseObject(jsonString,cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

}
