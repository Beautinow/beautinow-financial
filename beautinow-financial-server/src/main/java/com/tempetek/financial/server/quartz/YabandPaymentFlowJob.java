package com.tempetek.financial.server.quartz;

import com.tempetek.financial.server.service.IDscPaymentFlowService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class YabandPaymentFlowJob extends QuartzJobBean {

    @Autowired
    private IDscPaymentFlowService dscPaymentFlowService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        dscPaymentFlowService.syncPaymentFlow();
    }

}
