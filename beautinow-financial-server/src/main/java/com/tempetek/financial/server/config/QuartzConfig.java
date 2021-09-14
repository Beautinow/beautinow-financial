package com.tempetek.financial.server.config;

import com.tempetek.financial.server.quartz.YabandPaymentFlowJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail syncPaymentFlowJob() {
        JobDetail jobDetail = JobBuilder.newJob(YabandPaymentFlowJob.class)
                .withIdentity("syncPaymentFlowJob", "syncPaymentFlowJobGroup")
                .storeDurably()
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger syncPaymentFlowTrigger() {
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(syncPaymentFlowJob())
                .withIdentity("syncPaymentTrigger", "syncPaymentTriggerGroup")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 02 14 * * ?"))
                .build();
        return trigger;
    }

}
