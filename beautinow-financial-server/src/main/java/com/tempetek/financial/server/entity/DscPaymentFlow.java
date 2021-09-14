package com.tempetek.financial.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@ApiModel(value = "yaband支付流水表")
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dsc_payment_flow")
public class DscPaymentFlow implements Serializable {

    @ApiModelProperty("主键ID")
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @ApiModelProperty("流水ID")
    private String tradeId;
    @ApiModelProperty("支付金额")
    private Double amountCny;
    @ApiModelProperty("订单ID")
    private String orderId;
    @ApiModelProperty("订单创建时间")
    private Long createdAt;
    @ApiModelProperty("支付时间")
    private Long payTime;
    @ApiModelProperty("支付方式")
    private String subPayMethod;
    @ApiModelProperty("流水状态")
    private Integer status;
    @ApiModelProperty("是否退款")
    private Integer isRefund;

}
