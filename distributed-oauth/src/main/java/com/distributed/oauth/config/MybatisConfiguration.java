package com.distributed.oauth.config;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author: Mr.zhang
 * @Date: 2020/2/18 13:45
 */
@Configuration
@MapperScan(basePackages = {"com.distributed.oauth.*.mapper"})
@EnableTransactionManagement(proxyTargetClass = true)
public class MybatisConfiguration {

    @Autowired
    private WallFilter wallFilter;

    @Bean
    @ConfigurationProperties("spring.datasource")
    @Primary
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        List<Filter> filters = CollectionUtil.newArrayList();
        filters.add(wallFilter);
        datasource.setProxyFilters(filters);
        return datasource;
    }

    /**
     * 修改阿里sql执行过滤器的初始配置
     * @param wallConfig
     * @return
     */
    @Bean(name = "wallFilter")
    @DependsOn("wallConfig")
    public WallFilter wallFilter(WallConfig wallConfig){
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig);
        return wallFilter;
    }

    @Bean(name = "wallConfig")
    public WallConfig wallConfig(){
        WallConfig wallConfig = new WallConfig();
        //允许一次执行多条语句
        wallConfig.setMultiStatementAllow(true);
        //允许一次执行多条语句
        wallConfig.setNoneBaseStatementAllow(true);
        return wallConfig;
    }

    @Bean
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/**/*.xml"));
        return sessionFactory.getObject();
    }


}
