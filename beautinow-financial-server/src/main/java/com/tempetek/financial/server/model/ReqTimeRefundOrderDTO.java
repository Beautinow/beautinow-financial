package com.tempetek.financial.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqTimeRefundOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("开始时间")
    private Long start_time;
    @ApiModelProperty("结束时间")
    private Long end_time;
    @ApiModelProperty("状态")
    private String state;
    @ApiModelProperty(value = "当前页码", example = "1")
    private String page;
    @ApiModelProperty(value = "每页条数", example = "100")
    private String limit;

}
