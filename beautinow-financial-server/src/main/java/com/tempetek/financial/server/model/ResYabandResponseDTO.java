package com.tempetek.financial.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResYabandResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("状态码")
    private String code;
    @ApiModelProperty("返回数据")
    private ResTransactionDTO data;
    @ApiModelProperty("返回信息")
    private String message;

}
