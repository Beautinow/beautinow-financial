package com.tempetek.financial.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqQueryOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("YabandPay订单编号")
    private String trade_id;

}
