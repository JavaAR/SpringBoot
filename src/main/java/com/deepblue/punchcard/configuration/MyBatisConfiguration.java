package com.deepblue.punchcard.configuration;

import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;


/**
 * Mybayis的配置文件类
 * 配置了SqlSessionFactoryBean xml位置  dao接口的配置
 */
@Configuration
@Data
public class MyBatisConfiguration {

    /**
     * 相当于在yml文件中配置mybatis.mapper-locations:classpath: /mapper/*mapper.xml
     * 配置SqlSessionFactoryBean和配置mapper扫描技术
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        //1.配置数据源
        factory.setDataSource(dataSource);
        //2.配置类型别名
        factory.setTypeAliasesPackage("com.deepblue.punchcard.entity");
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //指定XML路径
        factory.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
        return factory.getObject();
    }
    /**
     * 相当于在主类中配置@MapperScan（"com.deepblue.punchcard.dao"）
     * 配置dao接口的位置
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScanner(){
        MapperScannerConfigurer mapperScanner = new MapperScannerConfigurer();
        mapperScanner.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        mapperScanner.setBasePackage("com.deepblue.punchcard.dao");
        return mapperScanner;
    }
}
