package com.tempetek.financial.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResAbnormalFlowDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private String id;
    @ApiModelProperty("流水ID")
    private String flowId;
    @ApiModelProperty("YabandPay的交易编号")
    private String tradeId;
    @ApiModelProperty("流水金额")
    private Double amountCny;
    @ApiModelProperty("订单ID")
    private String orderId;
    @ApiModelProperty("异常类型")
    private Integer exceptionType;
    @ApiModelProperty("处理状态")
    private Integer status;
    @ApiModelProperty("流水时间")
    private Long orderTime;
    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("用户等级")
    private String userLevel;
    @ApiModelProperty("汇款账户")
    private String remittanceAccount;
    @ApiModelProperty("订单金额")
    private Double totalFee;
    /*@ApiModelProperty("流水金额")
    private Double bankFee;*/
    @ApiModelProperty("余额金额")
    private Double overageFee;
    @ApiModelProperty("偏差额")
    private Double deviation;
    
}
