package com.tempetek.financial.server.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResTransactionDTO implements Serializable {

    @ApiModelProperty("数据")
    private List<ResTransactionInfoDTO> transaction_info;
    @ApiModelProperty("总条数")
    private String total_count;

}
