package com.programmingarea.hystrixdemo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api")
public class ApiResources {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${client.url}")
    private String url;

    @HystrixCommand(groupKey = "fallback", commandKey = "fallback", fallbackMethod = "hystrixFallback")
    @GetMapping(value = "/hystrix")
    public String getWelcomeMessage() {
        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping(value= "/default")
    public String getwelcomeWithoutHytrix()
    {
        return restTemplate.getForObject(url, String.class);
    }

    public String hystrixFallback() {
        return "Welcome to hytrix";
    }

}
