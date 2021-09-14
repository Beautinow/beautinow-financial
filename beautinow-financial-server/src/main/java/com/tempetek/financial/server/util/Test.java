//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package com.tempetek.financial.server.util;

import com.tempetek.maviki.entities.Report;
import com.tempetek.maviki.util.BeanUtil;
import com.tempetek.maviki.util.DateUtil;
import com.tempetek.maviki.util.ReflectUtil;
import com.tempetek.maviki.web.Echarts;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Test {
    public Test() {
    }

    public static Echarts handler(Date startTime, Date endTime, Object o, Report report) {
        Map<String, ?> map = fetchValue((List)o, report);
        List<String> xAxis = handlerX(report.isExsitTime(), startTime, endTime, report.getTimeType(), report.getInterval());
        Map<String, Object> yData = handlerY(map, xAxis);
        Echarts echarts = new Echarts();
        echarts.setTitle(report.getTitle());
        echarts.setLegend(new ArrayList(yData.keySet()));
        echarts.setXAxis(xAxis);
        echarts.setYData(yData);
        echarts.setExtend(report.getExtend());
        return echarts;
    }

    private static List<String> handlerX(boolean existTime, Date startTime, Date endTime, int timeType, int interval) {
        List<String> xAxis = new ArrayList();
        if (!existTime) {
            xAxis.add("");
        } else {
            while(true) {
                while(1 != timeType) {
                    Date date;
                    Date _startTime;
                    if (2 == timeType) {
                        if (BeanUtil.isEmpty(xAxis)) {
                            xAxis.add(DateUtil.format(startTime, DateUtil.DATE_FORMAT_YM));
                        }

                        date = DateUtil.parse((String)xAxis.get(xAxis.size() - 1), DateUtil.DATE_FORMAT_YM);
                        _startTime = DateUtil.add(date, 2, interval);
                        if (_startTime.getTime() > endTime.getTime()) {
                            return xAxis;
                        }

                        xAxis.add(DateUtil.format(_startTime, DateUtil.DATE_FORMAT_YM));
                    } else if (5 == timeType) {
                        if (BeanUtil.isEmpty(xAxis)) {
                            xAxis.add(DateUtil.format(startTime, DateUtil.DATE_FORMAT_YMD));
                        }

                        date = DateUtil.parse((String)xAxis.get(xAxis.size() - 1), DateUtil.DATE_FORMAT_YMD);
                        _startTime = DateUtil.add(date, 5, interval);
                        if (_startTime.getTime() > endTime.getTime()) {
                            return xAxis;
                        }

                        xAxis.add(DateUtil.format(_startTime, DateUtil.DATE_FORMAT_YMD));
                    } else if (11 == timeType) {
                        if (BeanUtil.isEmpty(xAxis)) {
                            xAxis.add(DateUtil.format(startTime, DateUtil.DATE_FORMAT_YMDH));
                        }

                        date = DateUtil.parse((String)xAxis.get(xAxis.size() - 1), DateUtil.DATE_FORMAT_YMDH);
                        _startTime = DateUtil.add(date, 11, interval);
                        if (_startTime.getTime() > endTime.getTime()) {
                            return xAxis;
                        }

                        xAxis.add(DateUtil.format(_startTime, DateUtil.DATE_FORMAT_YMDH));
                    } else if (12 == timeType) {
                        if (BeanUtil.isEmpty(xAxis)) {
                            xAxis.add(DateUtil.format(startTime, DateUtil.DATE_FORMAT_YMDHM));
                        }

                        date = DateUtil.parse((String)xAxis.get(xAxis.size() - 1), DateUtil.DATE_FORMAT_YMDHM);
                        _startTime = DateUtil.add(date, 12, interval);
                        if (_startTime.getTime() > endTime.getTime()) {
                            return xAxis;
                        }

                        xAxis.add(DateUtil.format(_startTime, DateUtil.DATE_FORMAT_YMDHM));
                    }
                }

                if (BeanUtil.isEmpty(xAxis)) {
                    xAxis.add(String.valueOf(DateUtil.get(startTime, 1)));
                }

                int startYear = Integer.valueOf((String)xAxis.get(xAxis.size() - 1)) + interval;
                int endYear = DateUtil.get(endTime, 1);
                if (Integer.valueOf(startYear) > endYear) {
                    break;
                }

                xAxis.add(String.valueOf(startYear));
            }
        }

        return xAxis;
    }

    private static Map<String, Object> handlerY(Map<String, ?> map, List<String> xAxis) {
        Map<String, Object> yData = new HashMap();
        Iterator var3 = map.entrySet().iterator();

        while(var3.hasNext()) {
            Entry<String, ?> entry = (Entry)var3.next();
            String key = (String)entry.getKey();
            Map<String, ?> value = (Map)entry.getValue();
            List<Object> dataList = new ArrayList();
            Iterator var8 = xAxis.iterator();

            while(var8.hasNext()) {
                String x = (String)var8.next();
                if (value.containsKey(x)) {
                    dataList.add(value.get(x));
                } else {
                    dataList.add("");
                }
            }

            yData.put(key, dataList);
        }

        return yData;
    }

    private static Map<String, ?> fetchValue(List<?> list, Report report) {
        Map<String, ?> map = null;
        if (1 == report.getMethod()) {
            if (1 == report.getValueType()) {
                map = (Map)list.stream().collect(Collectors.groupingBy((v) -> {
                    return fetchGroup(v, report);
                }, Collectors.groupingBy((v) -> {
                    return fetchTime(v, report);
                }, Collectors.summingInt((v) -> {
                    return fetchInt(v, report);
                }))));
            } else if (2 == report.getValueType()) {
                map = (Map)list.stream().collect(Collectors.groupingBy((v) -> {
                    return fetchGroup(v, report);
                }, Collectors.groupingBy((v) -> {
                    return fetchTime(v, report);
                }, Collectors.summingLong((v) -> {
                    return fetchLong(v, report);
                }))));
            } else if (3 == report.getValueType()) {
                map = (Map)list.stream().collect(Collectors.groupingBy((v) -> {
                    return fetchGroup(v, report);
                }, Collectors.groupingBy((v) -> {
                    return fetchTime(v, report);
                }, Collectors.summingDouble((v) -> {
                    return fetchDouble(v, report);
                }))));
            }
        } else if (2 == report.getMethod()) {
            if (1 == report.getValueType()) {
                map = (Map)list.stream().collect(Collectors.groupingBy((v) -> {
                    return fetchGroup(v, report);
                }, Collectors.groupingBy((v) -> {
                    return fetchTime(v, report);
                }, Collectors.averagingInt((v) -> {
                    return fetchInt(v, report);
                }))));
            } else if (2 == report.getValueType()) {
                map = (Map)list.stream().collect(Collectors.groupingBy((v) -> {
                    return fetchGroup(v, report);
                }, Collectors.groupingBy((v) -> {
                    return fetchTime(v, report);
                }, Collectors.averagingLong((v) -> {
                    return fetchLong(v, report);
                }))));
            } else if (3 == report.getValueType()) {
                map = (Map)list.stream().collect(Collectors.groupingBy((v) -> {
                    return fetchGroup(v, report);
                }, Collectors.groupingBy((v) -> {
                    return fetchTime(v, report);
                }, Collectors.averagingDouble((v) -> {
                    return fetchDouble(v, report);
                }))));
            }
        } else if (3 == report.getMethod()) {
            map = (Map)list.stream().collect(Collectors.groupingBy((v) -> {
                return fetchGroup(v, report);
            }, Collectors.groupingBy((v) -> {
                return fetchTime(v, report);
            }, Collectors.counting())));
        } else if (4 == report.getMethod()) {
            if (1 == report.getValueType()) {
                map = (Map)list.stream().collect(Collectors.groupingBy((v) -> {
                    return fetchGroup(v, report);
                }, Collectors.groupingBy((v) -> {
                    return fetchTime(v, report);
                }, Collectors.minBy(Comparator.comparingInt((v) -> {
                    return fetchInt(v, report);
                })))));
            } else if (2 == report.getValueType()) {
                map = (Map)list.stream().collect(Collectors.groupingBy((v) -> {
                    return fetchGroup(v, report);
                }, Collectors.groupingBy((v) -> {
                    return fetchTime(v, report);
                }, Collectors.minBy(Comparator.comparingLong((v) -> {
                    return fetchLong(v, report);
                })))));
            } else if (3 == report.getValueType()) {
                map = (Map)list.stream().collect(Collectors.groupingBy((v) -> {
                    return fetchGroup(v, report);
                }, Collectors.groupingBy((v) -> {
                    return fetchTime(v, report);
                }, Collectors.minBy(Comparator.comparingDouble((v) -> {
                    return fetchDouble(v, report);
                })))));
            }
        } else if (5 == report.getMethod()) {
            if (1 == report.getValueType()) {
                map = (Map)list.stream().collect(Collectors.groupingBy((v) -> {
                    return fetchGroup(v, report);
                }, Collectors.groupingBy((v) -> {
                    return fetchTime(v, report);
                }, Collectors.maxBy(Comparator.comparingInt((v) -> {
                    return fetchInt(v, report);
                })))));
            } else if (2 == report.getValueType()) {
                map = (Map)list.stream().collect(Collectors.groupingBy((v) -> {
                    return fetchGroup(v, report);
                }, Collectors.groupingBy((v) -> {
                    return fetchTime(v, report);
                }, Collectors.maxBy(Comparator.comparingLong((v) -> {
                    return fetchLong(v, report);
                })))));
            } else if (3 == report.getValueType()) {
                map = (Map)list.stream().collect(Collectors.groupingBy((v) -> {
                    return fetchGroup(v, report);
                }, Collectors.groupingBy((v) -> {
                    return fetchTime(v, report);
                }, Collectors.maxBy(Comparator.comparingDouble((v) -> {
                    return fetchDouble(v, report);
                })))));
            }
        }

        return map;
    }

    private static String fetchGroup(Object o, Report report) {
        String[] groupFields = report.getGroupFields();
        String template = report.getTemplate();
        String[] var4 = groupFields;
        int var5 = groupFields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String groupField = var4[var6];
            Object value = ReflectUtil.invokeGetter(o, groupField);
            template = template.replace("[" + groupField + "]", String.valueOf(value));
        }

        return template;
    }

    private static Object fetchTime(Object o, Report report) {
        boolean existTime = report.isExsitTime();
        String timeField = report.getTimeField();
        int interval = report.getInterval();
        if (existTime) {
            Object value = ReflectUtil.invokeGetter(o, timeField);
            int minute;
            if (1 == report.getTimeType()) {
                minute = DateUtil.get((Date)value, 1);
                return String.valueOf(minute / interval * interval);
            }

            Date date;
            if (2 == report.getTimeType()) {
                minute = DateUtil.get((Date)value, 2) - 1;
                date = DateUtil.set((Date)value, 2, minute / interval * interval);
                return DateUtil.format(date, DateUtil.DATE_FORMAT_YM);
            }

            if (5 == report.getTimeType()) {
                minute = DateUtil.get((Date)value, 5);
                date = DateUtil.set((Date)value, 5, minute / interval * interval);
                return DateUtil.format(date, DateUtil.DATE_FORMAT_YMD);
            }

            if (11 == report.getTimeType()) {
                minute = DateUtil.get((Date)value, 11);
                date = DateUtil.set((Date)value, 11, minute / interval * interval);
                return DateUtil.format(date, DateUtil.DATE_FORMAT_YMDH);
            }

            if (12 == report.getTimeType()) {
                minute = DateUtil.get((Date)value, 12);
                date = DateUtil.set((Date)value, 12, minute / interval * interval);
                return DateUtil.format(date, DateUtil.DATE_FORMAT_YMDHM);
            }
        }

        return "";
    }

    private static Integer fetchInt(Object o, Report report) {
        Object value = ReflectUtil.invokeGetter(o, report.getValueField());
        return Integer.valueOf(String.valueOf(value == null ? "0" : String.valueOf(value)));
    }

    private static Long fetchLong(Object o, Report report) {
        Object value = ReflectUtil.invokeGetter(o, report.getValueField());
        return Long.valueOf(String.valueOf(value == null ? "0" : String.valueOf(value)));
    }

    private static Double fetchDouble(Object o, Report report) {
        Object value = ReflectUtil.invokeGetter(o, report.getValueField());
        return Double.valueOf(String.valueOf(value == null ? "0.0" : String.valueOf(value)));
    }
}
