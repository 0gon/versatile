package org.versatile.infra;

import java.lang.reflect.Member;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@org.springframework.context.annotation.Configuration
@MapperScan(basePackages = "org.versatile.domain", annotationClass = Mapper.class,
        sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfig {
 
    @Bean
    public SqlSessionFactory sqlSessionFactory(ApplicationContext applicationContext, DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage("org.versatile.domain");
        // factoryBean.setTypeAliases(Member.class);
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/*.xml"));

        Configuration configuration = new Configuration();
        configuration.setLogImpl(StdOutImpl.class); // 콘솔에 SQL 출력
        factoryBean.setConfiguration(configuration);

        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
