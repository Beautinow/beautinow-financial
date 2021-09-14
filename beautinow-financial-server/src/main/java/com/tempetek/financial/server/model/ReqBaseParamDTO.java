package com.tempetek.financial.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqBaseParamDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("YabandPay后台开出的收银员帐号")
    private String user;
    @ApiModelProperty("签名")
    private String sign;
    @ApiModelProperty("方法")
    private String method;
    @ApiModelProperty("时间戳")
    private Long time;
    @ApiModelProperty("数据")
    private Object data;
}
