package com.tempetek.financial.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqListOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("当前页码")
    private String page;
    @ApiModelProperty("每页数量")
    private String limit;
    @ApiModelProperty("状态")
    private String filter;
}
