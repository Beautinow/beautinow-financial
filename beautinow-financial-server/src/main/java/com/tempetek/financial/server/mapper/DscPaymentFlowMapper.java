package com.tempetek.financial.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tempetek.financial.server.entity.DscPaymentFlow;

public interface DscPaymentFlowMapper extends BaseMapper<DscPaymentFlow> {

    Long getLatestTime();

}
