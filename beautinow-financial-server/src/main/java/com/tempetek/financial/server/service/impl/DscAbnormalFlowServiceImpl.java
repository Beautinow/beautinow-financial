package com.tempetek.financial.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tempetek.financial.server.constant.BeautinowConstant;
import com.tempetek.financial.server.entity.DscAbnormalFlow;
import com.tempetek.financial.server.entity.DscPaymentFlow;
import com.tempetek.financial.server.exception.BeautinowException;
import com.tempetek.financial.server.mapper.DscAbnormalFlowMapper;
import com.tempetek.financial.server.model.ReqAbnormalFlowPageDTO;
import com.tempetek.financial.server.model.ReqAbnormalFlowPagePO;
import com.tempetek.financial.server.model.ReqHandleFlowDTO;
import com.tempetek.financial.server.model.ResAbnormalFlowDTO;
import com.tempetek.financial.server.service.IDscAbnormalFlowService;
import com.tempetek.financial.server.service.IDscPaymentFlowService;
import com.tempetek.financial.server.util.DateUtils;
import com.tempetek.maviki.enums.MessageEnum;
import com.tempetek.maviki.web.Echarts;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DscAbnormalFlowServiceImpl extends ServiceImpl<DscAbnormalFlowMapper, DscAbnormalFlow> implements IDscAbnormalFlowService {

    @Autowired
    private IDscPaymentFlowService dscPaymentFlowService;

    @Override
    public Page<ResAbnormalFlowDTO> findByPager(ReqAbnormalFlowPageDTO reqAbnormalFlowPageDTO) {
        try {
            if (reqAbnormalFlowPageDTO == null) {
                throw new BeautinowException(MessageEnum.FAIL.getCode(), "查询分页列表参数不能为空!");
            }

            Page page = new Page(reqAbnormalFlowPageDTO.getPageNo(), reqAbnormalFlowPageDTO.getPageSize());
            ReqAbnormalFlowPagePO reqAbnormalFlowPagePO = new ReqAbnormalFlowPagePO();
            BeanUtils.copyProperties(reqAbnormalFlowPageDTO, reqAbnormalFlowPagePO);
            Page<ResAbnormalFlowDTO> pageList = baseMapper.findByPager(page, reqAbnormalFlowPagePO);
            return pageList;
        } catch (Exception e) {
            log.error(e.getMessage() + "获取异常流水分页列表信息失败!");
            throw new BeautinowException(MessageEnum.FAIL.getCode(), "获取异常流水分页列表信息失败!");
        }
    }

    @Override
    public String handleFlow(ReqHandleFlowDTO reqHandleFlowDTO) {
        try {
            if (reqHandleFlowDTO == null) {
                throw new BeautinowException(MessageEnum.FAIL.getCode(), "处理异常流水信息不能为空!");
            }

            /*DscAbnormalFlow dscAbnormalFlow = baseMapper.selectOne(new QueryWrapper<DscAbnormalFlow>().lambda()
                .eq(DscAbnormalFlow::getId, ));*/
        } catch (BeautinowException e) {
            log.error(e.getMessage());
            throw new BeautinowException(MessageEnum.FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage() + "处理异常流水失败!");
            throw new BeautinowException(MessageEnum.FAIL.getCode(), "处理异常流水失败!");
        }
        return null;
    }

    @Override
    public Echarts analyse() {
        try {
            Echarts echarts = new Echarts();
            echarts.setTitle(BeautinowConstant.ABNORNAL_TITLE);
            List<String> xAxis = new LinkedList<>();
            List<Integer> flowCountList = new LinkedList<>();
            List<Integer> abnormalFlowCountList = new LinkedList<>();
            Map<String, Object> map = new HashMap<>();

            for (int i = 7; i > 0; i --) {
                String date = DateUtils.getPastDate(i);
                xAxis.add(date);
                int flowCount = dscPaymentFlowService.count(new QueryWrapper<DscPaymentFlow>().lambda()
                    .apply("date_format(from_unixtime(created_at), '%Y-%m-%d') = " + date));
                int abnormalFlowCount = baseMapper.countByTime(date);
                flowCountList.add(flowCount);
                abnormalFlowCountList.add(abnormalFlowCount);
            }

            map.put("流水数量", flowCountList);
            map.put("异常流水数量", abnormalFlowCountList);
            echarts.setXAxis(xAxis);
            echarts.setLegend(new ArrayList<>(map.keySet()));
            echarts.setYData(map);
            return echarts;
        } catch (BeautinowException e) {
            log.error(e.getMessage());
            throw new BeautinowException(MessageEnum.FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage() + "异常流水分析失败!");
            throw new BeautinowException(MessageEnum.FAIL.getCode(), "异常流水分析失败!");
        }
    }


}
