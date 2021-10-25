package com.tempetek.financial.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tempetek.dearsystem.middleware.client.client.DearsystemFeignClient;
import com.tempetek.financial.server.entity.DscPaymentFlow;
import com.tempetek.financial.server.exception.BeautinowException;
import com.tempetek.financial.server.mapper.DscPaymentFlowMapper;
import com.tempetek.financial.server.model.*;
import com.tempetek.financial.server.service.IDscPaymentFlowService;
import com.tempetek.financial.server.support.IDscPaymentFlowSupport;
import com.tempetek.financial.server.util.DateUtils;
import com.tempetek.financial.server.util.EncryptUtil;
import com.tempetek.financial.server.util.HttpUtil;
import com.tempetek.financial.server.util.ParseUtil;
import com.tempetek.maviki.enums.MessageEnum;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DscPaymentFlowServiceImpl extends ServiceImpl<DscPaymentFlowMapper, DscPaymentFlow> implements IDscPaymentFlowService {

    @Value("${yaband.user}")
    private String user;
    @Value("${yaband.apiUrl}")
    private String apiUrl;
    @Value("${yaband.secretKey}")
    private String secretKey;
    @Value("${yaband.listOrderByTime}")
    private String listOrderByTime;
    @Value("${yaband.queryOrder}")
    private String queryOrder;
    @Autowired
    private IDscPaymentFlowSupport  dscPaymentFlowSupport;

    @Override
    @Transactional
    public String queryListOrderByTime(ReqListOrderByTimeDTO reqListOrderByTimeDTO) {
        try {
            if (reqListOrderByTimeDTO == null) {
                throw new BeautinowException(MessageEnum.FAIL.getCode(), "同步流水参数不能为空!");
            }

            Long latestTime = baseMapper.getLatestTime();
            Long time = System.currentTimeMillis() / 1000;

            if (reqListOrderByTimeDTO.getStartTime() == null) {
                reqListOrderByTimeDTO.setStartTime(latestTime == null ? DateUtils.defaultTime() : latestTime + 1);
            }

            if (reqListOrderByTimeDTO.getEndTime() == null) {
                reqListOrderByTimeDTO.setEndTime(time);
            }

            Map<String, String> map = new HashMap<>();
            map.put("user", user);
            map.put("method", listOrderByTime);
            map.put("start_time", reqListOrderByTimeDTO.getStartTime() + "");
            map.put("end_time", reqListOrderByTimeDTO.getEndTime() + "");
            map.put("page", reqListOrderByTimeDTO.getPage() + "");
            map.put("limit", reqListOrderByTimeDTO.getLimit() + "");
            map.put("state", reqListOrderByTimeDTO.getState());
            map.put("time", time + "");
            String result = EncryptUtil.formatUrlMap(map, false, false);
            String code = EncryptUtil.SHA256Encrypt(result, secretKey);
            ReqBaseParamDTO reqBaseParamDTO = new ReqBaseParamDTO();
            reqBaseParamDTO.setMethod(listOrderByTime);
            reqBaseParamDTO.setTime(time);
            reqBaseParamDTO.setSign(code);
            reqBaseParamDTO.setUser(user);
            ReqTimeRefundOrderDTO reqTimeRefundOrderDTO = new ReqTimeRefundOrderDTO();
            reqTimeRefundOrderDTO.setEnd_time(reqListOrderByTimeDTO.getEndTime());
            reqTimeRefundOrderDTO.setStart_time(reqListOrderByTimeDTO.getStartTime());
            reqTimeRefundOrderDTO.setState(reqListOrderByTimeDTO.getState());
            reqTimeRefundOrderDTO.setLimit(reqListOrderByTimeDTO.getLimit() + "");
            reqTimeRefundOrderDTO.setPage(reqListOrderByTimeDTO.getPage() + "");
            reqBaseParamDTO.setData(reqTimeRefundOrderDTO);
            String jsonObject = HttpUtil.HttpPostData(apiUrl, JSONObject.fromObject(reqBaseParamDTO).toString());
            ResYabandResponseDTO resYabandResponseDTO = ParseUtil.getResult(jsonObject, ResYabandResponseDTO.class);
            ResTransactionDTO resTransactionDTO = resYabandResponseDTO.getData();
            int totalCount = Integer.valueOf(resTransactionDTO.getTotal_count());
            int limit = reqListOrderByTimeDTO.getLimit();
            int totalPage = totalCount / limit + 1;

            for (int i = 1; i <= totalPage ; i ++) {
                time = System.currentTimeMillis() / 1000;
                map = new HashMap<>();
                map.put("user", user);
                map.put("method", listOrderByTime);
                map.put("start_time", reqListOrderByTimeDTO.getStartTime() + "");
                map.put("end_time", reqListOrderByTimeDTO.getEndTime() + "");
                map.put("page", i + "");
                map.put("limit", reqListOrderByTimeDTO.getLimit() + "");
                map.put("state", reqListOrderByTimeDTO.getState());
                map.put("time", time + "");
                result = EncryptUtil.formatUrlMap(map, false, false);
                code = EncryptUtil.SHA256Encrypt(result, secretKey);
                reqBaseParamDTO = new ReqBaseParamDTO();
                reqBaseParamDTO.setMethod(listOrderByTime);
                reqBaseParamDTO.setTime(time);
                reqBaseParamDTO.setSign(code);
                reqBaseParamDTO.setUser(user);
                reqTimeRefundOrderDTO = new ReqTimeRefundOrderDTO();
                reqTimeRefundOrderDTO.setEnd_time(reqListOrderByTimeDTO.getEndTime());
                reqTimeRefundOrderDTO.setStart_time(reqListOrderByTimeDTO.getStartTime());
                reqTimeRefundOrderDTO.setState(reqListOrderByTimeDTO.getState());
                reqTimeRefundOrderDTO.setLimit(limit + "");
                reqTimeRefundOrderDTO.setPage(i + "");
                reqBaseParamDTO.setData(reqTimeRefundOrderDTO);
                dscPaymentFlowSupport.analyzeAbnormalFlow(reqBaseParamDTO);
            }
            return "根据条件同步yaband流水信息成功!";
        } catch (Exception e) {
            log.error(e.getMessage() + "同步yaband流水信息失败!");
            throw new BeautinowException(MessageEnum.FAIL.getCode(), "同步yaband流水信息失败!");
        }
    }

    @Override
    public String getQueryOrder(String tradeId) {
        try {
            if (StringUtils.isBlank(tradeId)) {
                throw new BeautinowException(MessageEnum.FAIL.getCode(), "yaband交易编号不能为空!");
            }

            DscPaymentFlow dscPaymentFlow = baseMapper.selectOne(new QueryWrapper<DscPaymentFlow>().lambda().eq(DscPaymentFlow::getTradeId, tradeId));

            if (dscPaymentFlow == null) {
                throw new BeautinowException(MessageEnum.FAIL.getCode(), "根据yaband交易编号查询不到流水信息!");
            }

            Long time = System.currentTimeMillis();
            Map<String, String> map = new HashMap<>();
            map.put("user", user);
            map.put("method", queryOrder);
            map.put("trade_id", tradeId);
            map.put("time", time+"");
            String result = EncryptUtil.formatUrlMap(map, false, false);
            String code = EncryptUtil.SHA256Encrypt(result, secretKey);
            ReqBaseParamDTO reqBaseParamDTO = new ReqBaseParamDTO();
            reqBaseParamDTO.setMethod(queryOrder);
            reqBaseParamDTO.setTime(time);
            reqBaseParamDTO.setSign(code);
            reqBaseParamDTO.setUser(user);
            ReqQueryOrderDTO reqQueryOrderDTO = new ReqQueryOrderDTO();
            reqQueryOrderDTO.setTrade_id(tradeId);
            reqBaseParamDTO.setData(reqQueryOrderDTO);
            String jsonObject = HttpUtil.HttpPostData(apiUrl, JSONObject.fromObject(reqBaseParamDTO).toString());
            return jsonObject;
        } catch (BeautinowException e) {
            log.error(e.getMessage());
            throw new BeautinowException(MessageEnum.FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage() + "根据yaband交易编号获取订单信息失败!");
            throw new BeautinowException(MessageEnum.FAIL.getCode(), "根据yaband交易编号获取订单信息失败!");
        }
    }

    @Override
    public Page<ResPaymentFlowDTO> findByPager(ReqPaymentFlowPageDTO reqPaymentFlowPageDTO) {
        try {
            if (reqPaymentFlowPageDTO == null) {
                throw new BeautinowException(MessageEnum.FAIL.getCode(), "查询参数不能为空!");
            }

            Page page = new Page(reqPaymentFlowPageDTO.getPageNo(), reqPaymentFlowPageDTO.getPageSize());
            Page<DscPaymentFlow> pageList = baseMapper.selectPage(page, new QueryWrapper<DscPaymentFlow>().lambda()
                .gt(reqPaymentFlowPageDTO.getStartTime() != null, DscPaymentFlow::getCreatedAt, reqPaymentFlowPageDTO.getStartTime())
                .lt(reqPaymentFlowPageDTO.getEndTime() != null, DscPaymentFlow::getCreatedAt, reqPaymentFlowPageDTO.getEndTime())
                .eq(reqPaymentFlowPageDTO.getIsRefund() != null, DscPaymentFlow::getIsRefund, reqPaymentFlowPageDTO.getIsRefund()));
            Page<ResPaymentFlowDTO> resPaymentFlowDTOPage = new Page<>();
            List<ResPaymentFlowDTO> resPaymentFlowDTOList = new ArrayList<>();

            for (DscPaymentFlow dscPaymentFlow : pageList.getRecords()) {
                ResPaymentFlowDTO resPaymentFlowDTO = new ResPaymentFlowDTO();
                BeanUtils.copyProperties(dscPaymentFlow, resPaymentFlowDTO);
                resPaymentFlowDTOList.add(resPaymentFlowDTO);
            }

            resPaymentFlowDTOPage.setTotal(pageList.getTotal());
            resPaymentFlowDTOPage.setRecords(resPaymentFlowDTOList);
            return resPaymentFlowDTOPage;
        } catch (BeautinowException e) {
            log.error(e.getMessage());
            throw new BeautinowException(MessageEnum.FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage() + "获取yaband支付流水分页列表失败!");
            throw new BeautinowException(MessageEnum.FAIL.getCode(), "获取yaband支付流水分页列表失败!");
        }
    }

    @Override
    public void syncPaymentFlow() {
        try {
            ReqListOrderByTimeDTO reqListOrderByTimeDTO = new ReqListOrderByTimeDTO();
            Long latestTime = baseMapper.getLatestTime();
            Long time = System.currentTimeMillis();
            reqListOrderByTimeDTO.setStartTime(latestTime == null ? DateUtils.defaultTime() : latestTime + 1);
            reqListOrderByTimeDTO.setEndTime(DateUtils.nextDay());
            reqListOrderByTimeDTO.setPage(1);
            reqListOrderByTimeDTO.setLimit(100);
            reqListOrderByTimeDTO.setState("paid");
            Map<String, String> map = new HashMap<>();
            map.put("user", user);
            map.put("method", listOrderByTime);
            map.put("start_time", reqListOrderByTimeDTO.getStartTime() + "");
            map.put("end_time", reqListOrderByTimeDTO.getEndTime() + "");
            map.put("page", reqListOrderByTimeDTO.getPage() + "");
            map.put("limit", reqListOrderByTimeDTO.getLimit() + "");
            map.put("state", reqListOrderByTimeDTO.getState());
            map.put("time", time + "");
            String result = EncryptUtil.formatUrlMap(map, false, false);
            String code = EncryptUtil.SHA256Encrypt(result, secretKey);
            ReqBaseParamDTO reqBaseParamDTO = new ReqBaseParamDTO();
            reqBaseParamDTO.setMethod(listOrderByTime);
            reqBaseParamDTO.setTime(time);
            reqBaseParamDTO.setSign(code);
            reqBaseParamDTO.setUser(user);
            ReqTimeRefundOrderDTO reqTimeRefundOrderDTO = new ReqTimeRefundOrderDTO();
            reqTimeRefundOrderDTO.setEnd_time(reqListOrderByTimeDTO.getEndTime());
            reqTimeRefundOrderDTO.setStart_time(reqListOrderByTimeDTO.getStartTime());
            reqTimeRefundOrderDTO.setState(reqListOrderByTimeDTO.getState());
            reqTimeRefundOrderDTO.setPage(reqListOrderByTimeDTO.getPage() + "");
            reqTimeRefundOrderDTO.setLimit(reqListOrderByTimeDTO.getLimit() + "");
            reqBaseParamDTO.setData(reqTimeRefundOrderDTO);
            String jsonObject = HttpUtil.HttpPostData(apiUrl, JSONObject.fromObject(reqBaseParamDTO).toString());
            ResYabandResponseDTO resYabandResponseDTO = ParseUtil.getResult(jsonObject, ResYabandResponseDTO.class);
            ResTransactionDTO resTransactionDTO = resYabandResponseDTO.getData();
            int totalCount = Integer.valueOf(resTransactionDTO.getTotal_count());
            int limit = reqListOrderByTimeDTO.getLimit();
            int totalPage = totalCount / limit + 1;

            for (int i = 1; i <= totalPage ; i ++) {
                time = System.currentTimeMillis() / 1000;
                map = new HashMap<>();
                map.put("user", user);
                map.put("method", listOrderByTime);
                map.put("start_time", reqListOrderByTimeDTO.getStartTime() + "");
                map.put("end_time", reqListOrderByTimeDTO.getEndTime() + "");
                map.put("page", i + "");
                map.put("limit", reqListOrderByTimeDTO.getLimit() + "");
                map.put("state", reqListOrderByTimeDTO.getState());
                map.put("time", time + "");
                result = EncryptUtil.formatUrlMap(map, false, false);
                code = EncryptUtil.SHA256Encrypt(result, secretKey);
                reqBaseParamDTO = new ReqBaseParamDTO();
                reqBaseParamDTO.setMethod(listOrderByTime);
                reqBaseParamDTO.setTime(time);
                reqBaseParamDTO.setSign(code);
                reqBaseParamDTO.setUser(user);
                reqTimeRefundOrderDTO = new ReqTimeRefundOrderDTO();
                reqTimeRefundOrderDTO.setEnd_time(reqListOrderByTimeDTO.getEndTime());
                reqTimeRefundOrderDTO.setStart_time(reqListOrderByTimeDTO.getStartTime());
                reqTimeRefundOrderDTO.setState(reqListOrderByTimeDTO.getState());
                reqTimeRefundOrderDTO.setLimit(limit + "");
                reqTimeRefundOrderDTO.setPage(i + "");
                reqBaseParamDTO.setData(reqTimeRefundOrderDTO);
                dscPaymentFlowSupport.analyzeAbnormalFlow(reqBaseParamDTO);
            }
        } catch (BeautinowException e) {
            log.error(e.getMessage());
            throw new BeautinowException(MessageEnum.FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage() + "定时获取yaband流水信息失败!");
            throw new BeautinowException(MessageEnum.FAIL.getCode(), "定时获取yaband流水信息失败!");
        }
    }

    @Override
    public int countByTime(String date) {
        return baseMapper.countByTime(date);
    }

}
