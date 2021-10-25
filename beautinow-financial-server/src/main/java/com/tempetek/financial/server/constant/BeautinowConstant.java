package com.tempetek.financial.server.constant;

public class BeautinowConstant {

    // 删除状态
    // 已删除
    public static Integer DELETED = 1;
    // 未删除
    public static Integer NOT_DELETED = 0;

    // 异常类型
    public static String EXCEPTION_TYPE = "EXCEPTION_TYPE";
    // 重复
    public static Integer REPEAT = 0;
    // 异常订单
    public static Integer AMOUNT_NOT_MATCH  = 1;
    // 订单不存在
    public static Integer ORDER_NOT_EXIST  = 2;
    // 纯余额支付订单
    public static Integer OVERAGE_ORDER = 3;

    public static String ABNORNAL_TITLE = "异常流水统计";

    public static String EXCEPTION_TYPE_TITLE = "异常占比统计";

}
