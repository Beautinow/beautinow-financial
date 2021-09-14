package com.tempetek.financial.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResTransactionInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private String id;
    @ApiModelProperty("YabandPay的交易编号")
    private String trade_id;
    @ApiModelProperty("设备ID")
    private String device_id;
    @ApiModelProperty("来源")
    private String origin;
    @ApiModelProperty("商户ID")
    private String merchant_id;
    @ApiModelProperty("商户名称")
    private String merchant_name;
    @ApiModelProperty("商店ID")
    private String store_id;
    @ApiModelProperty("商店名称")
    private String store_name;
    @ApiModelProperty("商店token")
    private String store_token;
    @ApiModelProperty("")
    private String store_mid;
    @ApiModelProperty("")
    private String gateways_id;
    @ApiModelProperty("")
    private String gateways_name;
    @ApiModelProperty("支付账户email")
    private String cashier_email;
    @ApiModelProperty("支付账户名称")
    private String cashier_name;
    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("支付方法")
    private String pay_method;
    @ApiModelProperty("支付方式")
    private String sub_pay_method;
    @ApiModelProperty("订单ID")
    private String order_id;
    @ApiModelProperty("支付提交金额")
    private String amount;
    @ApiModelProperty("支付提交的货币种类")
    private String currency;
    @ApiModelProperty("结算货币金额")
    private String settlement_amount;
    @ApiModelProperty("结算货币种类")
    private String settlement_currency;
    @ApiModelProperty("支付金额/结算金额")
    private String exchange_rate;
    @ApiModelProperty("人民币结算汇率")
    private String settlement_rate;
    @ApiModelProperty("支付人民币金额")
    private String amount_cny;
    @ApiModelProperty("")
    private String post_description;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("订单创建时间")
    private String createDate;
    @ApiModelProperty("付款费用")
    private String payment_fee;
    @ApiModelProperty("合作伙伴利润")
    private String partner_profit;
    @ApiModelProperty("利润")
    private String profit;
    @ApiModelProperty("商户手续费")
    private String merchant_cost;
    @ApiModelProperty("合作伙伴手续费")
    private String parnter_cost;
    @ApiModelProperty("合作伙伴分享")
    private String parner_share;
    @ApiModelProperty("yaband手续费")
    private String yb_cost;
    @ApiModelProperty("固定成本")
    private String cost_fixed;
    @ApiModelProperty("成本")
    private String cost;
    @ApiModelProperty("渠道成本")
    private String channel_cost;
    @ApiModelProperty("商户结算金额")
    private String revenue;
    @ApiModelProperty("跳转地址")
    private String redirect_url;
    @ApiModelProperty("通知网址")
    private String notify_url;
    @ApiModelProperty("授权码")
    private String auth_code;
    @ApiModelProperty("支付账号appid")
    private String sub_app_id;
    @ApiModelProperty("支付账号openid")
    private String sub_open_id;
    @ApiModelProperty("")
    private String mid;
    @ApiModelProperty("")
    private String ideal_post_email;
    @ApiModelProperty("支付状态")
    private String state;
    @ApiModelProperty("支付账户类型")
    private String sub_pay_type;
    @ApiModelProperty("")
    private String is_send_customs;
    @ApiModelProperty("")
    private String customs_res;
    @ApiModelProperty("限制类型")
    private String limit_type;
    @ApiModelProperty("超时时间")
    private String time_out;
    @ApiModelProperty("退款金额")
    private String refund_amount;
    @ApiModelProperty("退款结算金额")
    private String settlement_refund_amount;
    @ApiModelProperty("")
    private String demo;
    @ApiModelProperty("")
    private String nonce_string;
    @ApiModelProperty("")
    private String is_send_cashier;
    @ApiModelProperty("")
    private String iban;
    @ApiModelProperty("支付超时时间")
    private String pay_timeout;
    @ApiModelProperty("")
    private String push_status;
    @ApiModelProperty("")
    private String tran_id;
    @ApiModelProperty("")
    private String transaction_id;
    @ApiModelProperty("")
    private String wa_transaction_id;
    @ApiModelProperty("支付时间")
    private String paid_time;
    @ApiModelProperty("详细信息")
    private String details;
    @ApiModelProperty("二维码地址")
    private String qrcode_url;
    @ApiModelProperty("支付类型")
    private String pay_type;
    @ApiModelProperty("")
    private String is_round;
    @ApiModelProperty("删除时间")
    private String deleted_at;
    @ApiModelProperty("是否退款")
    private String is_refund;

}
