package com.ynan.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author yuannan
 * @Date 2022/4/14 23:03
 */
@FeignClient(value = "remote-demo", path = "/remote")
public interface RemoteClient {

    @RequestMapping("/name")
    String remote(@RequestParam String name);


    @RequestMapping("/s")
    String remote1(@RequestParam String name);
}
