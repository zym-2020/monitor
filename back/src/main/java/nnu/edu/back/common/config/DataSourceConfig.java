package nnu.edu.back.common.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Yiming
 * @Date: 2023/07/28/20:19
 * @Description:
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {
    /**
     * 默认基础数据源
     *
     * @return
     */
    @Bean("defaultSource")
    @ConfigurationProperties("spring.datasource.default")
    public DataSource defaultSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("shpSource")
    @ConfigurationProperties("spring.datasource.shp")
    public DataSource shpSource() { return DataSourceBuilder.create().build(); }

    @Bean("shipSource")
    @ConfigurationProperties("spring.datasource.ship")
    public DataSource shipSource() { return DataSourceBuilder.create().build(); }

    @Bean("waterwaySource")
    @ConfigurationProperties("spring.datasource.waterway")
    public DataSource waterwaySource() { return DataSourceBuilder.create().build(); }

    @Bean("mapSource")
    @ConfigurationProperties("spring.datasource.map")
    public DataSource mapSource() { return DataSourceBuilder.create().build(); }

    /**
     * 自定义动态数据源
     *
     * @return
     */
    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("default", defaultSource());
        dataSourceMap.put("shp", shpSource());
        dataSourceMap.put("waterway", waterwaySource());
        dataSourceMap.put("ship", shipSource());
        dataSourceMap.put("map", mapSource());

        // 默认数据源
        dynamicDataSource.setDefaultDataSource(defaultSource());
        // 动态数据源
        dynamicDataSource.setDataSources(dataSourceMap);

        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // 配置自定义动态数据源
        sessionFactory.setDataSource(dynamicDataSource());
        // 开启驼峰转下划线设置
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(configuration);
        // 实体、Mapper类映射
//        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*/*.xml"));
        return sessionFactory;
    }

    /**
     * 开启动态数据源@Transactional注解事务管理的支持
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
