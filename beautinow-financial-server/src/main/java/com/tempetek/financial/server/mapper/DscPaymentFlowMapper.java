package com.tempetek.financial.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tempetek.financial.server.entity.DscPaymentFlow;
import org.apache.ibatis.annotations.Param;

public interface DscPaymentFlowMapper extends BaseMapper<DscPaymentFlow> {

    Long getLatestTime();

    int countByTime(@Param("date") String date);

}
