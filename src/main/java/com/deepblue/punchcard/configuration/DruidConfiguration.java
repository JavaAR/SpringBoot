package com.deepblue.punchcard.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 *
 * 数据池Druid配置类
 * 1.配置Druid数据源
 * 2.配置监控
 */
@Configuration
@Data
public class DruidConfiguration {

    private Logger logger = LoggerFactory.getLogger(DruidConfiguration.class);

    private static final String DB_PREFIX = "spring.datasource";
    @Value("${spring.druid.allowIp}")
    private String allowIp;
    @Value("${spring.druid.denyIp}")
    private String denyIp;
    @Value("${spring.druid.loginUsername}")
    private String druidUserName;
    @Value("${spring.druid.loginPassword}")
    private String druidPassword;
    @Value("${spring.druid.resetEnable}")
    private String resetData;
    @Value("${spring.druid.exclusions}")
    private String exclusions;


    /**
     * 配置监控StatViewServlet配置
     * 提供监控信息展示的html
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //设置访问控制(allow)
        registrationBean.addInitParameter("allow",allowIp);
        //配置访问权限（deny）
        registrationBean.addInitParameter("deny",denyIp);
        //配置页面访问用户名和密码
        registrationBean.addInitParameter("loginUsername",druidUserName);
        registrationBean.addInitParameter("loginPassword",druidPassword);
        //设置不允许清空数据
        registrationBean.addInitParameter("resetEnable",resetData);
        return registrationBean;
    }

    /**
     * 配置WebStatFilter
     * 用于采集jdbc关联的监控数据
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());
        //配置过滤规则
        bean.addUrlPatterns("/*");
        //过滤一些不必要的url 如 *.js//jslib/*
        bean.addInitParameter("exclusions",exclusions);
        return bean;
    }

    /**
     * Druid 配置数据源
     */
    @Component
    @ConfigurationProperties(prefix =DB_PREFIX )
    @Data
    class DataSourceProertice{
        private String url;
        private String username;
        private String password;
        private String driverClassName;
        private int initialSize;
        private int minIdle;
        private int maxActive;
        private int maxWait;
        private int timeBetweenEvictionRunsMillis;
        private int minEvictableIdleTimeMillis;
        private String validationQuery;
        private boolean testWhileIdle;
        private boolean testOnBorrow;
        private boolean testOnReturn;
        private boolean poolPreparedStatements;
        private int maxPoolPreparedStatementPerConnectionSize;
        private String filters;
        private String connectionProperties;
        private boolean useGlobalDataSourceStat;


        @Bean
        @Primary //同样数据源下首先使用该注解下的DataSource
        public DataSource dataSource(){

            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setDriverClassName(driverClassName);

            dataSource.setInitialSize(initialSize);
            dataSource.setMinIdle(minIdle);
            dataSource.setMaxActive(maxActive);
            dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
            dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            dataSource.setValidationQuery(validationQuery);
            dataSource.setTestOnBorrow(testOnBorrow);
            dataSource.setTestOnReturn(testOnReturn);
            dataSource.setPoolPreparedStatements(poolPreparedStatements);
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

            try {
                dataSource.setFilters(filters);
            } catch (SQLException e) {
               logger.error("druid configuration initialization filter",e);
            }
            dataSource.setConnectionProperties(connectionProperties);
            dataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
            return dataSource;
        }
    }
}
