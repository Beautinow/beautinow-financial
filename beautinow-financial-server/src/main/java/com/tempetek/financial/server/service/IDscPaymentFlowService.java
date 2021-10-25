package com.tempetek.financial.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tempetek.financial.server.entity.DscPaymentFlow;
import com.tempetek.financial.server.model.ReqListOrderByTimeDTO;
import com.tempetek.financial.server.model.ReqPaymentFlowPageDTO;
import com.tempetek.financial.server.model.ResPaymentFlowDTO;

public interface IDscPaymentFlowService extends IService<DscPaymentFlow> {

    String queryListOrderByTime(ReqListOrderByTimeDTO reqListOrderByTimeDTO);

    String getQueryOrder(String tradeId);

    Page<ResPaymentFlowDTO> findByPager(ReqPaymentFlowPageDTO reqPaymentFlowPageDTO);

    void syncPaymentFlow();

    int countByTime(String date);

}
