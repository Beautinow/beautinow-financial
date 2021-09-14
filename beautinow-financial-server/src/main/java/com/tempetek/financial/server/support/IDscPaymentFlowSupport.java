package com.tempetek.financial.server.support;

import com.tempetek.financial.server.model.ReqBaseParamDTO;
import com.tempetek.financial.server.model.ResTransactionInfoDTO;

public interface IDscPaymentFlowSupport {

    void analyzeAbnormalFlow(ReqBaseParamDTO reqBaseParamDTO);
}
