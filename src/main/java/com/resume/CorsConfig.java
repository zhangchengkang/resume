/**
 * Copyright © 2019湖北金拓维信息技术有限公司. All rights reserved.
 *
 * @Title: CorsConfig
 * @Prject: resume
 * @Package: com.resume
 * @ClassName: CorsConfig
 * @Description: 跨域配置
 * @author: ZhangChengKang
 * @date: 2019/2/28
 * @version: V1.0
 */
package com.resume;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @ClassName: CorsConfig
 * @Description: 跨域配置
 * @author: ZhangChengKang
 * @date: 2019/2/28
 */
@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 2允许任何头
        corsConfiguration.addAllowedMethod("*"); // 3允许任何方法（post、get等）
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}