package com.tempetek.financial.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqHandleFlowDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("异常流水ID")
    private String abnormalId;
    @ApiModelProperty("退款单号")
    private String orderId;
    @ApiModelProperty("处理人")
    private String handler;
    @ApiModelProperty("处理方式")
    private String handleMethod;
    @ApiModelProperty("处理日期")
    private Long handleTime;

}
