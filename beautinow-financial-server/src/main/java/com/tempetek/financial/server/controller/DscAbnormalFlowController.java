package com.tempetek.financial.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tempetek.financial.server.model.ReqAbnormalFlowPageDTO;
import com.tempetek.financial.server.model.ReqHandleFlowDTO;
import com.tempetek.financial.server.model.ResAbnormalFlowDTO;
import com.tempetek.financial.server.service.IDscAbnormalFlowService;
import com.tempetek.maviki.web.Echarts;
import com.tempetek.maviki.web.Respon;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "异常流水管理")
@RequestMapping(value = "/dsc/abnormal/flow")
@CrossOrigin
public class DscAbnormalFlowController {

    @Autowired
    private IDscAbnormalFlowService dscAbnormalFlowService;

    @PostMapping("/findByPager")
    @ApiOperation("获取异常流水分页列表信息")
    public Respon findByPager(@RequestBody ReqAbnormalFlowPageDTO reqAbnormalFlowPageDTO) {
        Page<ResAbnormalFlowDTO> page = dscAbnormalFlowService.findByPager(reqAbnormalFlowPageDTO);
        return Respon.success(page);
    }

    @PostMapping("/handleFlow")
    @ApiOperation("处理异常流水")
    public Respon handleFlow(@RequestBody ReqHandleFlowDTO reqHandleFlowDTO) {
        String result = dscAbnormalFlowService.handleFlow(reqHandleFlowDTO);
        return Respon.success(result);
    }

    @GetMapping("/analyse")
    @ApiOperation("/异常流水分析")
    public Respon analyse() {
        Echarts echarts = dscAbnormalFlowService.analyse();
        return Respon.success(echarts);
    }

}
