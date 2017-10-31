package com.configuration;

import com.adcc.utility.log.Log;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 应用配置类
 */
@Configuration
@EnableConfigurationProperties
public class AppConfiguration implements TransactionManagementConfigurer {

    @Value("${mybatis.configLocations}")
    private String configLocations;

    @Value("${mybatis.mapperLocations}")
    private String  mapperLocations;

    @Value("${mybatis.typeAliasesPackage}")
    private String  typeAliasesPackage;

    @Autowired
    private DataSource dataSource;

    public String getConfigLocations() {
        return configLocations;
    }

    public void setConfigLocations(String configLocations) {
        this.configLocations = configLocations;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    // 提供SqlSeesion
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        try {
            SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
            sessionFactoryBean.setDataSource(dataSource);

            // 读取配置
            sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);

            //
            Resource[] resources = new PathMatchingResourcePatternResolver()
                    .getResources(mapperLocations);
            sessionFactoryBean.setMapperLocations(resources);
            //      //
            sessionFactoryBean.setConfigLocation(
                    new DefaultResourceLoader().getResource(configLocations));

            return sessionFactoryBean.getObject();
        } catch (IOException e) {
            Log.warn("mybatis resolver mapper*xml is error");
            return null;
        } catch (Exception e) {
            Log.warn("mybatis sqlSessionFactoryBean create error");
            return null;
        }
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
