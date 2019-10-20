package com.deepblue.punchcard.configuration;

import lombok.Data;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * web配置类
 */
@Configuration
@Data
public class WebConfiguration implements WebMvcConfigurer {

    private final  Logger logger = LoggerFactory.getLogger(WebConfiguration.class);
    @Resource
    private CustomInterceptors customInterceptors;
    @Value("${spring.request.url}")
    private String requestUrl;
    @Value("${spring.request.path}")
    private String requestPath;
    @Value("${spring.request.interceptUrl}")
    private String intercept;
    @Value("${spring.request.excludeInterceptUrls}")
    private List<String> excludeInterceptUrls;

    /**
     * 配置静态资源放行
      * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler(requestUrl).addResourceLocations(requestPath);
    }

    /**
     * 添加自定义的全局拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println(intercept);
        System.out.println(excludeInterceptUrls);
        registry.addInterceptor(customInterceptors).addPathPatterns(intercept).excludePathPatterns(excludeInterceptUrls);
    }

    /**
     * 自定义全局的拦截器（内部类）
     * 开发人员可以自定义拦截规则
     */
    @Component
    class CustomInterceptors extends HandlerInterceptorAdapter {
        private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CustomInterceptors.class);
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            logger.info(request.getRequestURI());
            //自定义拦截规则规则
            System.out.println("我被拦截了");
            return true;
        }
    }

}
