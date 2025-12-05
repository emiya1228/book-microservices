package com.book.bookcore.Controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RefreshScope  // 添加这个注解
public class ConfigController {
    @Resource
    private Environment env;
    @Value("${book.borrow.default-days}")
    private Integer borrowDays;

    @GetMapping("/config/borrow-days")
    public Integer getBorrowDays() {
        return borrowDays;
    }

    @GetMapping("/nacos-status")
    public Map<String, Object> checkNacos() {
        Map<String, Object> result = new HashMap<>();

        // 检查Nacos基本配置
        result.put("nacosAddr", env.getProperty("spring.cloud.nacos.config.server-addr"));
        result.put("appName", env.getProperty("spring.application.name"));
        result.put("nacosNamespace", env.getProperty("spring.cloud.nacos.config.namespace"));

        // 检查配置源
        if (env instanceof ConfigurableEnvironment) {
            ConfigurableEnvironment configurableEnv = (ConfigurableEnvironment) env;
            List<String> nacosSources = new ArrayList<>();
            configurableEnv.getPropertySources().forEach(ps -> {
                if (ps.getName().contains("nacos")) {
                    nacosSources.add(ps.getName());
                }
            });
            result.put("nacosSources", nacosSources);
        }

        // 尝试直接读取Nacos配置
        result.put("borrowDays", env.getProperty("book.borrow.default-days"));
        result.put("datasourceUrl", env.getProperty("spring.datasource.url"));

        return result;
    }
}

