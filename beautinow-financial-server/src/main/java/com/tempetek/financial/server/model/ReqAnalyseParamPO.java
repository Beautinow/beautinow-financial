package com.tempetek.financial.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqAnalyseParamPO implements Serializable {

    private static final long serilalVersionUID = 1L;

    @ApiModelProperty("查询时间")
    private String date;
    @ApiModelProperty("异常类型")
    private Integer exceptionType;

}
