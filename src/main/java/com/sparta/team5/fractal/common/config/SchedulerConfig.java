package com.sparta.team5.fractal.common.config;

import com.sparta.team5.fractal.domain.product.service.ProductServiceApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final ProductServiceApi productServiceApi;

    // 밀리초 단위로 계산
    @Scheduled(fixedRate = 60000)
    @Transactional(readOnly = true)
    public void productCacheEvict() {

        productServiceApi.productCacheEvict();
    }
}
