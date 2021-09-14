package com.tempetek.financial.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqPaymentFlowPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer pageNo;
    @ApiModelProperty(value = "每页数量", example = "10")
    private Integer pageSize;
    @ApiModelProperty("是否退款")
    private Integer isRefund;
    @ApiModelProperty("开始时间")
    private Long startTime;
    @ApiModelProperty("结束时间")
    private Long endTime;

}
