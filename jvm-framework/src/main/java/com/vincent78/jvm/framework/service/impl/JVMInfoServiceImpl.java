package com.vincent78.jvm.framework.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vincent78.jvm.framework.service.JVMInfoService;
import org.springframework.stereotype.Service;

import java.lang.management.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;


@Service
public class JVMInfoServiceImpl implements JVMInfoService {

    private NumberFormat fmtI = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.ENGLISH));
    private NumberFormat fmtD = new DecimalFormat("###,##0.000", new DecimalFormatSymbols(Locale.ENGLISH));

    private final int UnitKb = 1024;

    private final int UnitMb = UnitKb * 1024;

    //获取JVM的启动时间，版本及名称，当前进程ID,环境变量等
    public JSONObject getRuntime() {
        JSONObject obj = new JSONObject();
        try {
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            obj.put("name", runtime.getVmName());
            obj.put("version", runtime.getVmVersion());
            obj.put("javaVer", System.getProperty("java.version"));
            obj.put("vendor", runtime.getVmVendor());
            obj.put("uptime", toDuration(runtime.getUptime()));
            obj.put("startTime", runtime.getStartTime());
            obj.put("systemProperties", runtime.getSystemProperties());
            obj.put("processId", runtime.getName().split("@")[0]);
        } catch (Exception e) {
            obj.put("exception", e.getMessage());
        }
        return obj;
    }

    //操作系统及硬件信息：系统名称、版本，CPU内核，机器总内存、可用内存、可用内存占比
    public JSONObject getOS() {
        JSONObject obj = new JSONObject();
        try {
            OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
            obj.put("osName", os.getName());
            obj.put("osArch", os.getArch());
            obj.put("osVersion", os.getAvailableProcessors());
            obj.put("osAvailableProcessors", os.getName());
        } catch (Exception e) {
            obj.put("exception", e.getMessage());
        }
        return obj;
    }

    //获取JVM内存使用状况，包括堆内存和非堆内存
    public JSONObject getMem(int unit) {
        JSONObject obj = new JSONObject();
        try {
            MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
            JSONObject heapObj = new JSONObject();
            JSONArray heapPools = new JSONArray();
            JSONObject noHeapObj = new JSONObject();
            JSONArray noHeapPools = new JSONArray();

            MemoryUsage heapMemoryUsage = mem.getHeapMemoryUsage();
            heapObj.put("init", heapMemoryUsage.getInit() / unit);
            heapObj.put("used", heapMemoryUsage.getUsed() / unit);
            heapObj.put("max", heapMemoryUsage.getMax() / unit);
            heapObj.put("committed", heapMemoryUsage.getCommitted() / unit);


            MemoryUsage nonHeapMemoryUsage = mem.getNonHeapMemoryUsage();
            noHeapObj.put("init", nonHeapMemoryUsage.getInit() / unit);
            noHeapObj.put("used", nonHeapMemoryUsage.getUsed() / unit);
            noHeapObj.put("committed", nonHeapMemoryUsage.getCommitted() / unit);
            noHeapObj.put("max", nonHeapMemoryUsage.getMax() / unit);


            for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
                final MemoryUsage usage = pool.getUsage();
                JSONObject poolObj = new JSONObject();
                poolObj.put("name",pool.getName());
                poolObj.put("init",usage.getInit() / unit);
                poolObj.put("used",usage.getUsed() / unit);
                poolObj.put("committed",usage.getCommitted() / unit);
                poolObj.put("max",usage.getMax() / unit);
                if (MemoryType.HEAP == pool.getType()) {
                    heapPools.add(poolObj);
                } else {
                    noHeapPools.add(poolObj);
                }
            }

            heapObj.put("pools",heapPools);
            noHeapObj.put("pools",noHeapPools);

            obj.put("heap",heapObj);
            obj.put("noheap",noHeapObj);

        } catch (Exception e) {
            obj.put("exception", e.getMessage());
        }
        return obj;
    }

    //虚拟机线程系统的管理,可以获取某个线程信息(阻塞时间，次数，堆栈信息等)
    public JSONObject getThread() {
        JSONObject obj = new JSONObject();
        try {
            ThreadMXBean threads = ManagementFactory.getThreadMXBean();
            obj.put("threadsLiveCount", threads.getThreadCount());
            obj.put("threadsDaemonCount", threads.getDaemonThreadCount());
            obj.put("threadsPeakCount", threads.getPeakThreadCount());
            obj.put("threadsTotalCount", threads.getTotalStartedThreadCount());
            obj.put("AllIds", toString(threads.getAllThreadIds()));
        } catch (Exception e) {
            obj.put("exception", e.getMessage());
        }
        return obj;
    }

    //Java虚拟机类加载系统的管理接口。
    public JSONObject getLoad() {
        JSONObject obj = new JSONObject();
        try {
            ClassLoadingMXBean cl = ManagementFactory.getClassLoadingMXBean();
            obj.put("clsCurrLoaded", cl.getLoadedClassCount());
            obj.put("clsLoaded", cl.getTotalLoadedClassCount());
            obj.put("clsUnloaded", cl.getUnloadedClassCount());
        } catch (Exception e) {
            obj.put("exception", e.getMessage());
        }
        return obj;
    }


    public JSONObject summary() {
        JSONObject obj = new JSONObject();
        obj.put("runtime",getRuntime());
        obj.put("os",getOS());
        obj.put("mem",getMem(UnitMb));
        obj.put("thread",getThread());
        obj.put("load",getLoad());
        obj.put("timestamp",new Date().getTime());
        return obj;
    }



    protected String toDuration(double uptime) {
        uptime /= 1000;
        if (uptime < 60) {
            return fmtD.format(uptime) + " seconds";
        }
        uptime /= 60;
        if (uptime < 60) {
            long minutes = (long) uptime;
            String s = fmtI.format(minutes) + (minutes > 1 ? " minutes" : " minute");
            return s;
        }
        uptime /= 60;
        if (uptime < 24) {
            long hours = (long) uptime;
            long minutes = (long) ((uptime - hours) * 60);
            String s = fmtI.format(hours) + (hours > 1 ? " hours" : " hour");
            if (minutes != 0) {
                s += " " + fmtI.format(minutes) + (minutes > 1 ? " minutes" : " minute");
            }
            return s;
        }
        uptime /= 24;
        long days = (long) uptime;
        long hours = (long) ((uptime - days) * 24);
        String s = fmtI.format(days) + (days > 1 ? " days" : " day");
        if (hours != 0) {
            s += " " + fmtI.format(hours) + (hours > 1 ? " hours" : " hour");
        }
        return s;
    }

    protected String toString(long[] collection) {
        if (collection.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (long id : collection) {
                stringBuilder.append(id).append(",");
            }
            return stringBuilder.substring(0, stringBuilder.length() - 1);
        }
        return null;
    }
}
