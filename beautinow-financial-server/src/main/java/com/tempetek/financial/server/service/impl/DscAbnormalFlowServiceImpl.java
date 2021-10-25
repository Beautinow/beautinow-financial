package com.tempetek.financial.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tempetek.dearsystem.middleware.client.client.DearsystemFeignClient;
import com.tempetek.dearsystem.middleware.client.vo.ResOrderVO;
import com.tempetek.dictionary.client.client.DictionaryFeignClient;
import com.tempetek.dictionary.client.vo.ResDictionaryDataListVO;
import com.tempetek.financial.server.constant.BeautinowConstant;
import com.tempetek.financial.server.entity.DscAbnormalFlow;
import com.tempetek.financial.server.entity.DscHandleFlow;
import com.tempetek.financial.server.exception.BeautinowException;
import com.tempetek.financial.server.mapper.DscAbnormalFlowMapper;
import com.tempetek.financial.server.model.*;
import com.tempetek.financial.server.service.IDscAbnormalFlowService;
import com.tempetek.financial.server.service.IDscHandleFlowService;
import com.tempetek.financial.server.service.IDscPaymentFlowService;
import com.tempetek.financial.server.util.DateUtils;
import com.tempetek.financial.server.util.ParseUtil;
import com.tempetek.maviki.enums.MessageEnum;
import com.tempetek.maviki.web.Echarts;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

@Service
public class DscAbnormalFlowServiceImpl extends ServiceImpl<DscAbnormalFlowMapper, DscAbnormalFlow> implements IDscAbnormalFlowService {

    @Autowired
    private IDscPaymentFlowService dscPaymentFlowService;
    @Autowired
    private DictionaryFeignClient dictionaryFeignClient;
    @Autowired
    private IDscHandleFlowService dscHandleFlowService;
    @Autowired
    private DearsystemFeignClient dearsystemFeignClient;

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

            for (ResAbnormalFlowDTO resAbnormalFlowDTO : pageList.getRecords()) {
                String preOrderId = "";

                if (StringUtils.isNotBlank(resAbnormalFlowDTO.getOrderId())) {
                    preOrderId = resAbnormalFlowDTO.getOrderId().split("_")[0];
                }

                Object data = dearsystemFeignClient.getByOrderNo(resAbnormalFlowDTO.getOrderId()).getData();
                ResOrderVO resOrderVO = ParseUtil.getResult(JSONObject.fromObject(data).toString(), ResOrderVO.class);

                if (resOrderVO != null) {
                    BigDecimal b1 = new BigDecimal(Double.toString(resOrderVO.getBankFee()));
                    BigDecimal b2 = new BigDecimal(Double.toString(resOrderVO.getOverageFee()));
                    resAbnormalFlowDTO.setTotalFee(b1.add(b2).doubleValue());
                    resAbnormalFlowDTO.setOverageFee(resOrderVO.getOverageFee());
                    BigDecimal b3 = new BigDecimal(Double.toString(resAbnormalFlowDTO.getAmountCny()));
                    BigDecimal b4 = new BigDecimal(resAbnormalFlowDTO.getTotalFee());
                    resAbnormalFlowDTO.setDeviation(b4.subtract(b3).doubleValue());
                }
            }
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

            DscAbnormalFlow dscAbnormalFlow = baseMapper.selectOne(new QueryWrapper<DscAbnormalFlow>().lambda()
                .eq(DscAbnormalFlow::getId, reqHandleFlowDTO.getAbnormalId()));

            if (dscAbnormalFlow == null) {
                throw new BeautinowException(MessageEnum.FAIL.getCode(), "异常流水信息不存在!");
            }

            DscHandleFlow dscHandleFlow = new DscHandleFlow();
            BeanUtils.copyProperties(reqHandleFlowDTO, dscHandleFlow);
            dscHandleFlowService.save(dscHandleFlow);

            dscAbnormalFlow.setModifier("admin");
            dscAbnormalFlow.setModifiedTime(System.currentTimeMillis());
            dscAbnormalFlow.setStatus(BeautinowConstant.DELETED);
            baseMapper.update(dscAbnormalFlow, new UpdateWrapper<DscAbnormalFlow>().lambda().eq(DscAbnormalFlow::getId, dscAbnormalFlow.getId()));
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
    public Echarts analyse(Integer flag, String dateString) {
        try {
            Echarts echarts = new Echarts();
            echarts.setTitle(BeautinowConstant.ABNORNAL_TITLE);
            List<String> xAxis = new LinkedList<>();
            List<Integer> flowCountList = new LinkedList<>();
            List<Integer> abnormalFlowCountList = new LinkedList<>();
            List<Integer> orderCountList = new LinkedList<>();
            Map<String, Object> map = new HashMap<>();

            if (flag == 0) {
                String[] temp = dateString.split("年第");
                int year = Integer.valueOf(temp[0]);
                int week = Integer.valueOf(temp[1].split("周")[0]);
                xAxis = DateUtils.getWeekDays(year, week);
            } else if (flag == 1) {
                String[] temp = dateString.split("年第");
                int year = Integer.valueOf(temp[0]);
                int month = Integer.valueOf(temp[1].split("月")[0]);
                xAxis = DateUtils.getMonthFullDay(year, month);
            } else {
                String[] temp = dateString.split("年");
                int year = Integer.valueOf(temp[0]);
                xAxis = DateUtils.getAllMoth(year);
            }

            for (String date : xAxis) {
                int flowCount = 0;
                int abnormalFlowCount = 0;
                int orderCount = 0;
                if (flag != 2) {
                    flowCount = baseMapper.countByOrder(date);
                    abnormalFlowCount = baseMapper.countByTime(date);
                } else {
                    flowCount = baseMapper.countOrderByYear(date);
                    abnormalFlowCount = baseMapper.countOrderByTime(date);
                }

                Object object = dearsystemFeignClient.getOrderByTime(date).getData();
                orderCount = (Integer) object;
                flowCountList.add(flowCount);
                abnormalFlowCountList.add(abnormalFlowCount);
                orderCountList.add(orderCount);
            }

            map.put("异常订单数量", flowCountList);
            map.put("异常流水数量", abnormalFlowCountList);
            map.put("订单数量", orderCountList);
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

    @Override
    public Echarts analyseExceptionType(Integer flag, String dateString) {
        try {
            Echarts echarts = new Echarts();
            echarts.setTitle(BeautinowConstant.EXCEPTION_TYPE_TITLE);
            List<String> xAxis = new LinkedList<>();
            Map<String, Object> map = new HashMap<>();
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);
            List<String> percentList1 = new LinkedList<>();
            List<String> percentList2 = new LinkedList<>();

            if (flag == 0) {
                String[] temp = dateString.split("年第");
                int year = Integer.valueOf(temp[0]);
                int week = Integer.valueOf(temp[1].split("周")[0]);
                xAxis = DateUtils.getWeekDays(year, week);
            } else if (flag == 1) {
                String[] temp = dateString.split("年第");
                int year = Integer.valueOf(temp[0]);
                int month = Integer.valueOf(temp[1].split("月")[0]);
                xAxis = DateUtils.getMonthFullDay(year, month);
            } else {
                String[] temp = dateString.split("年");
                int year = Integer.valueOf(temp[0]);
                xAxis = DateUtils.getAllMoth(year);
            }

            for (String date : xAxis) {
                int abnormalFlowCount = 0;
                int abnormalOrderCount = 0;
                if (flag != 2) {
                    abnormalFlowCount = baseMapper.countByTime(date);
                    abnormalOrderCount = baseMapper.countByOrder(date);
                } else {
                    abnormalFlowCount = baseMapper.countOrderByTime(date);
                    abnormalOrderCount = baseMapper.countOrderByYear(date);
                }
                String result1 = numberFormat.format((float) abnormalFlowCount /  (float)(abnormalFlowCount + abnormalOrderCount) * 100);
                String result2 = numberFormat.format((float) abnormalOrderCount /  (float)(abnormalFlowCount + abnormalOrderCount) * 100);
                percentList1.add(result1);
                percentList2.add(result2);
            }
            map.put("异常订单占比", percentList2);
            map.put("异常流水占比", percentList1);
            /*}*/

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
