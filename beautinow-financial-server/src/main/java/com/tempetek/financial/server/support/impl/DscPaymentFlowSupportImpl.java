package com.tempetek.financial.server.support.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tempetek.dearsystem.middleware.client.client.DearsystemFeignClient;
import com.tempetek.dearsystem.middleware.client.vo.ResOrderVO;
import com.tempetek.financial.server.constant.BeautinowConstant;
import com.tempetek.financial.server.entity.DscAbnormalFlow;
import com.tempetek.financial.server.entity.DscPaymentFlow;
import com.tempetek.financial.server.mapper.DscPaymentFlowMapper;
import com.tempetek.financial.server.model.ReqBaseParamDTO;
import com.tempetek.financial.server.model.ResTransactionDTO;
import com.tempetek.financial.server.model.ResTransactionInfoDTO;
import com.tempetek.financial.server.model.ResYabandResponseDTO;
import com.tempetek.financial.server.service.IDscAbnormalFlowService;
import com.tempetek.financial.server.support.IDscPaymentFlowSupport;
import com.tempetek.financial.server.util.HttpUtil;
import com.tempetek.financial.server.util.ParseUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DscPaymentFlowSupportImpl implements IDscPaymentFlowSupport {

    @Autowired
    private IDscAbnormalFlowService dscAbnormalFlowService;
    @Resource
    private DscPaymentFlowMapper dscPaymentFlowMapper;
    @Autowired
    private DearsystemFeignClient dearsystemFeignClient;
    @Value("${yaband.apiUrl}")
    private String apiUrl;

    @Override
    @Async("asyncAbnormalFlowExecutor")
    public void analyzeAbnormalFlow(ReqBaseParamDTO reqBaseParamDTO) {
        String jsonObject = HttpUtil.HttpPostData(apiUrl, JSONObject.fromObject(reqBaseParamDTO).toString());
        ResYabandResponseDTO resYabandResponseDTO = ParseUtil.getResult(jsonObject, ResYabandResponseDTO.class);
        ResTransactionDTO resTransactionDTO = resYabandResponseDTO.getData();
        List<ResTransactionInfoDTO> resTransactionInfoDTOList = resTransactionDTO.getTransaction_info();

        for (ResTransactionInfoDTO resTransactionInfoDTO : resTransactionInfoDTOList) {
            String preOrderId = "";

            if (StringUtils.isNotBlank(resTransactionInfoDTO.getOrder_id())) {
                preOrderId = resTransactionInfoDTO.getOrder_id().split("_")[0];
            }

            List<DscPaymentFlow> dscPaymentFlowList = dscPaymentFlowMapper.selectList(new QueryWrapper<DscPaymentFlow>().lambda().likeRight(StringUtils.isNotBlank(preOrderId), DscPaymentFlow::getOrderId, preOrderId + "_"));
            DscPaymentFlow dscPaymentFlow = new DscPaymentFlow();
            dscPaymentFlow.setOrderId(resTransactionInfoDTO.getOrder_id());
            dscPaymentFlow.setPayTime(Long.valueOf(resTransactionInfoDTO.getPaid_time()));
            dscPaymentFlow.setAmountCny(Double.parseDouble(resTransactionInfoDTO.getAmount()));
            dscPaymentFlow.setCreatedAt(Long.valueOf(resTransactionInfoDTO.getCreateDate()));
            dscPaymentFlow.setIsRefund(Integer.valueOf(resTransactionInfoDTO.getIs_refund()));
            dscPaymentFlow.setStatus(0);
            dscPaymentFlow.setSubPayMethod(resTransactionInfoDTO.getSub_pay_method());
            dscPaymentFlow.setTradeId(resTransactionInfoDTO.getTrade_id());
            dscPaymentFlowMapper.insert(dscPaymentFlow);

            if (dscPaymentFlowList.size() > 0) {
                DscAbnormalFlow dscAbnormalFlow = new DscAbnormalFlow();
                dscAbnormalFlow.setFlowId(dscPaymentFlow.getId());
                dscAbnormalFlow.setStatus(0);
                dscAbnormalFlow.setCreateTime(System.currentTimeMillis());
                dscAbnormalFlow.setCreator("admin");
                dscAbnormalFlow.setModifiedTime(System.currentTimeMillis());
                dscAbnormalFlow.setModifier("admin");
                dscAbnormalFlow.setExceptionType(BeautinowConstant.REPEAT);
                dscAbnormalFlowService.save(dscAbnormalFlow);

                for (DscPaymentFlow dscPaymentFlow1 : dscPaymentFlowList) {
                    DscAbnormalFlow dscAbnormalFlow1 = dscAbnormalFlowService.getOne(new QueryWrapper<DscAbnormalFlow>().lambda()
                            .eq(DscAbnormalFlow::getFlowId, dscPaymentFlow1.getId()));

                    if (dscAbnormalFlow1 == null) {
                        dscAbnormalFlow1 = new DscAbnormalFlow();
                        dscAbnormalFlow1.setFlowId(dscPaymentFlow1.getId());
                        dscAbnormalFlow1.setStatus(0);
                        dscAbnormalFlow1.setCreateTime(System.currentTimeMillis());
                        dscAbnormalFlow1.setModifiedTime(System.currentTimeMillis());
                        dscAbnormalFlow1.setCreator("admin");
                        dscAbnormalFlow1.setModifier("admin");
                        dscAbnormalFlow1.setExceptionType(BeautinowConstant.REPEAT);
                        dscAbnormalFlowService.save(dscAbnormalFlow1);
                    }
                }
            } else {
                Object data = dearsystemFeignClient.getByOrderNo(preOrderId).getData();
                ResOrderVO resOrderVO = ParseUtil.getResult(JSONObject.fromObject(data).toString(), ResOrderVO.class);

                if (resOrderVO == null) {
                    DscAbnormalFlow dscAbnormalFlow = dscAbnormalFlowService.getOne(new QueryWrapper<DscAbnormalFlow>().lambda()
                            .eq(DscAbnormalFlow::getFlowId, dscPaymentFlow.getId()));

                    if (dscAbnormalFlow == null) {
                        dscAbnormalFlow = new DscAbnormalFlow();
                        dscAbnormalFlow.setFlowId(dscPaymentFlow.getId());
                        dscAbnormalFlow.setStatus(0);
                        dscAbnormalFlow.setCreateTime(System.currentTimeMillis());
                        dscAbnormalFlow.setModifiedTime(System.currentTimeMillis());
                        dscAbnormalFlow.setCreator("admin");
                        dscAbnormalFlow.setModifier("admin");
                        dscAbnormalFlow.setExceptionType(BeautinowConstant.ORDER_NOT_EXIST);
                        dscAbnormalFlowService.save(dscAbnormalFlow);
                    }
                } else {
                    if (Double.doubleToLongBits(resOrderVO.getBankFee()) == Double.doubleToLongBits(0.00d)) {
                        DscAbnormalFlow dscAbnormalFlow = new DscAbnormalFlow();
                        dscAbnormalFlow.setFlowId(dscPaymentFlow.getId());
                        dscAbnormalFlow.setStatus(0);
                        dscAbnormalFlow.setCreateTime(System.currentTimeMillis());
                        dscAbnormalFlow.setModifiedTime(System.currentTimeMillis());
                        dscAbnormalFlow.setCreator("admin");
                        dscAbnormalFlow.setModifier("admin");
                        dscAbnormalFlow.setExceptionType(BeautinowConstant.OVERAGE_ORDER);
                        dscAbnormalFlowService.save(dscAbnormalFlow);
                    } else if (!(Double.doubleToLongBits(Double.valueOf(resTransactionInfoDTO.getAmount())) == Double.doubleToLongBits(resOrderVO.getBankFee()))) {
                        DscAbnormalFlow dscAbnormalFlow = new DscAbnormalFlow();
                        dscAbnormalFlow.setFlowId(dscPaymentFlow.getId());
                        dscAbnormalFlow.setStatus(0);
                        dscAbnormalFlow.setCreateTime(System.currentTimeMillis());
                        dscAbnormalFlow.setModifiedTime(System.currentTimeMillis());
                        dscAbnormalFlow.setCreator("admin");
                        dscAbnormalFlow.setModifier("admin");
                        dscAbnormalFlow.setExceptionType(BeautinowConstant.AMOUNT_NOT_MATCH);
                        dscAbnormalFlowService.save(dscAbnormalFlow);
                    }
                }
            }
        }
    }
}
