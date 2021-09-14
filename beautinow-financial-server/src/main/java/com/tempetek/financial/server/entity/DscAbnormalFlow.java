package com.tempetek.financial.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@ApiModel(value = "yaband支付流水表")
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dsc_abnormal_flow")
public class DscAbnormalFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @ApiModelProperty("流水ID")
    private String flowId;
    @ApiModelProperty("异常类型")
    private Integer exceptionType;
    @ApiModelProperty("处理状态")
    private Integer status;
    @ApiModelProperty("创建人")
    private String creator;
    @ApiModelProperty("创建时间")
    private Long createTime;
    @ApiModelProperty("修改人")
    private String modifier;
    @ApiModelProperty("修改时间")
    private Long modifiedTime;

}
