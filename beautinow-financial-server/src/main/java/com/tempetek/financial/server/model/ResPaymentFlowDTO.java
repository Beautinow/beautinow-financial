package com.tempetek.financial.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResPaymentFlowDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
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

}
