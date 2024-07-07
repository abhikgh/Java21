package com.src.stream;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
class MyThread3 extends Thread{

    private String name;



    @Override
    public void run() {

        Map<String,String> map1 = new HashMap<>();
        map1.put("net-ip","333");map1.put("ip-address","132333");
        Map<String,String> map2 = new HashMap<>();
        map2.put("hostname","kekdkd");map2.put("ip-address","232233");map2.put("operating-system","win11");
        Map<String,String> map3 = new HashMap<>();
        map3.put("processor","intel");map3.put("ip-address","344322");map3.put("graphics","nvidia");
        AtomicReference<String> ipAddress = new AtomicReference<>();
        if(name.equals("t1") && StringUtils.isEmpty(ipAddress.get())){
            map1.entrySet().stream().filter(entry -> entry.getKey().equals("ip-address")).findFirst().ifPresent(entry -> ipAddress.set(entry.getValue()));
        }else if(name.equals("t2") && StringUtils.isEmpty(ipAddress.get())){
            map2.entrySet().stream().filter(entry -> entry.getKey().equals("ip-address")).findFirst().ifPresent(entry -> ipAddress.set(entry.getValue()));
        }else if(name.equals("t3") && StringUtils.isEmpty(ipAddress.get())){
            map3.entrySet().stream().filter(entry -> entry.getKey().equals("ip-address")).findFirst().ifPresent(entry -> ipAddress.set(entry.getValue()));
        }

        System.out.println("ipAddress :: " + ipAddress);

    }
}
public class ThreadsSimultaneousEx {

    public static void main(String[] args) {

        MyThread3 t1 = new MyThread3("t1");
        MyThread3 t2 = new MyThread3("t2");
        MyThread3 t3 = new MyThread3("t3");

        t1.start();
        t2.start();
        t3.start();

    }
}
