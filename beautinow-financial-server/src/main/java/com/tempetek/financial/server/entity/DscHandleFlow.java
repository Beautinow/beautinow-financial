package com.tempetek.financial.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@ApiModel(value = "异常流水处理结果")
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dsc_handle_flow")
public class DscHandleFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @ApiModelProperty("异常流水ID")
    private String abnormalId;
    @ApiModelProperty("关联号")
    private String orderId;
    @ApiModelProperty("处理人")
    private String handler;
    @ApiModelProperty("处理方式")
    private String handleMethod;
    @ApiModelProperty("处理日期")
    private Long handleTime;

}
