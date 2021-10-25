package com.tempetek.financial.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tempetek.financial.server.entity.DscAbnormalFlow;
import com.tempetek.financial.server.model.ReqAbnormalFlowPagePO;
import com.tempetek.financial.server.model.ReqAnalyseParamPO;
import com.tempetek.financial.server.model.ResAbnormalFlowDTO;
import org.apache.ibatis.annotations.Param;

public interface DscAbnormalFlowMapper extends BaseMapper<DscAbnormalFlow> {

    Page<ResAbnormalFlowDTO> findByPager(Page page, @Param("po") ReqAbnormalFlowPagePO reqAbnormalFlowPagePO);

    int countByTime(@Param("date") String date);

    int countByTimeAndExceptionType(@Param("po") ReqAnalyseParamPO reqAnalyseParamPO);

    int countByOrder(String date);

    int countOrderByTime(String date);

    int countOrderByYear(String date);

}
