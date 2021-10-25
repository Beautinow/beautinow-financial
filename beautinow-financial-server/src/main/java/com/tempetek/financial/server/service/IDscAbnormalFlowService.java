package com.tempetek.financial.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tempetek.financial.server.entity.DscAbnormalFlow;
import com.tempetek.financial.server.model.ReqAbnormalFlowPageDTO;
import com.tempetek.financial.server.model.ReqHandleFlowDTO;
import com.tempetek.financial.server.model.ResAbnormalFlowDTO;
import com.tempetek.maviki.web.Echarts;

public interface IDscAbnormalFlowService extends IService<DscAbnormalFlow> {

    Page<ResAbnormalFlowDTO> findByPager(ReqAbnormalFlowPageDTO reqAbnormalFlowPageDTO);

    String handleFlow(ReqHandleFlowDTO reqHandleFlowDTO);

    Echarts analyse(Integer flag, String dateString);

    Echarts analyseExceptionType(Integer flag, String dateString);

}
