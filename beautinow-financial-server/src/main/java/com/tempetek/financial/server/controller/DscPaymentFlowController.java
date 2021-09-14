package com.tempetek.financial.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tempetek.financial.server.model.ReqListOrderByTimeDTO;
import com.tempetek.financial.server.model.ReqListOrderDTO;
import com.tempetek.financial.server.model.ReqPaymentFlowPageDTO;
import com.tempetek.financial.server.model.ResPaymentFlowDTO;
import com.tempetek.financial.server.service.IDscPaymentFlowService;
import com.tempetek.financial.server.util.HttpUtil;
import com.tempetek.maviki.web.Respon;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/dsc/payment/flow")
@Api(tags = "雅本相关接口")
@CrossOrigin
public class DscPaymentFlowController {

    @Autowired
    private IDscPaymentFlowService dscPaymentFlowService;

    @PostMapping("/queryListOrderByTime")
    @ApiOperation(value = "根据时间获取订单列表信息")
    public Respon queryListOrderByTime(@RequestBody ReqListOrderByTimeDTO reqListOrderByTimeDTO) {
        String result = dscPaymentFlowService.queryListOrderByTime(reqListOrderByTimeDTO);
        return Respon.success(result);
    }

    @GetMapping("/queryOrder")
    @ApiOperation(value = "订单查询")
    public Respon getQueryOrder(@RequestParam String tradeId) {
        String result = dscPaymentFlowService.getQueryOrder(tradeId);
        return Respon.success(result);
    }

    /*@PostMapping("/listOrder")
    @ApiOperation(value = "订单列表查询")
    public Respon queryListOrder(@RequestBody ReqListOrderDTO reqListOrderDTO) {
        String result = HttpUtil.queryListOrder(reqListOrderDTO);
        return Respon.success(result);
    }*/

    @ApiOperation("yaband流水分页列表")
    @PostMapping("/findByPager")
    public Respon findByPager(@RequestBody ReqPaymentFlowPageDTO reqPaymentFlowPageDTO) {
        Page<ResPaymentFlowDTO> pageList = dscPaymentFlowService.findByPager(reqPaymentFlowPageDTO);
        return Respon.success(pageList);
    }
}
