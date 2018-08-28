package com.lin;

import com.alibaba.fastjson.JSONObject;
import com.lin.model.Device;

import com.lin.utils.DateUtils;
import com.lin.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/8/22.
 */
public class CompleteableFutureTask2 {


    private static final Logger logger = LoggerFactory.getLogger(CompleteableFutureTask2.class);

    protected static ExecutorService executorService = new ThreadPoolExecutor(300, 300, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    //获取工厂下的设备
    private final static String DEVICE_URL = "http://118.31.228.81:20080/fiweb/api/networkProbe/ListDeviceByFactoryId";


    static List<String> countList = new ArrayList<String>();


    private static List<Device> listFactoryDevices() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("factoryId", "13");
        String deviceList = HttpUtils.sendHttpPost(DEVICE_URL, params);
        return JSONObject.parseArray(deviceList, Device.class);
    }


    private static String testConnectivity(Device device) {
        String ip = device.getIp();
        if (ip.equals("172.16.0.25")) {
            System.out.println("---------------" + DateUtils.formatToDateTime(new Date()));
        }
        boolean status = true;
        try {
            // 连接超时时间1秒
            status = InetAddress.getByName(ip).isReachable(200);
        } catch (IOException e) {
            logger.error("ip connect fail：" + e.getMessage(), e);
        }
        if (ip == null) {
            status = false;
            logger.info("--null--null--null--null--null--null--null---设备" + device.getDeviceName() + "ip为空");
        }
        if (status == false) {
            return ip;
        }

        return null;
    }

    static Supplier<String> getSupplier(Device device) {
        return () -> {
            return testConnectivity(device);
        };
    }

    private static List<CompletableFuture<String>> doPing() {
        List<Device> devices = listFactoryDevices();
        List<CompletableFuture<String>> futures = devices.stream()
                .map(device -> CompletableFuture.supplyAsync(getSupplier(device), executorService)).collect(Collectors.toList());
        return futures;
    }

    public static void main(String[] args) {
        // 启动定时器
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<CompletableFuture<String>> futures = doPing();
                operationCountList(futures, 1);
            }
        }, 100, 1000 * 5);


        //统计一分钟
        Timer countTimer = new Timer();
        countTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Map<String, Long> map = countList.stream().collect(Collectors.groupingBy(String::new, Collectors.counting()));
                System.out.println("-----------------------" + map.size());
                operationCountList(null, 2);
                for (String key : map.keySet()) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    logger.info(simpleDateFormat.format(new Date()) + "," + key + "," + map.get(key));
                }
            }
        }, 60 * 1000 + 100, 60 * 1000);

    }

    public static synchronized void operationCountList(List<CompletableFuture<String>> list, Integer type) {
        if (type == 1) {
            list.stream()
                    .forEach(future -> {
                        future.whenCompleteAsync((r, e) -> {
                            countList.add(r);
                            if (e != null) {
                                System.out.println("e: " + e);
                            }
                        });
                    });
            countList = countList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        } else {
            countList = new ArrayList<>();
        }
    }

}

