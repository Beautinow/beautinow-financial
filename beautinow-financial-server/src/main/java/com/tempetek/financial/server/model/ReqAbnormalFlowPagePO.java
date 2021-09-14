package com.tempetek.financial.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqAbnormalFlowPagePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("开始时间")
    private Long startTime;
    @ApiModelProperty("结束时间")
    private Long endTime;
    @ApiModelProperty("异常类型")
    private Integer exceptionType;
    @ApiModelProperty("处理状态")
    private Integer status;

}
