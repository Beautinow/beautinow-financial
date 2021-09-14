package com.tempetek.financial.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqAbnormalFlowPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer pageNo;
    @ApiModelProperty(value = "每页条数", example = "10")
    private Integer pageSize;
    @ApiModelProperty("异常类型")
    private Integer exceptionType;
    @ApiModelProperty("处理状态")
    private Integer status;
    @ApiModelProperty("开始时间")
    private Long startTime;
    @ApiModelProperty("结束时间")
    private Long endTime;

}
