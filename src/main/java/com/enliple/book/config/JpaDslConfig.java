package com.enliple.book.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class JpaDslConfig {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Bean( name = "jpaQueryFactory" )
	public JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory( entityManager );
	}
	
//	@Bean( name = "dslDataSource" )
//	@ConfigurationProperties("spring.datasource")
//	public DataSource dataSource() {
//		return DataSourceBuilder.create().type(HikariDataSource.class).build();
//	}
//	
//	@Bean( name = "dslEntityManagerFactory" )
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//					EntityManagerFactoryBuilder builder,
//					@Qualifier("dslDataSource") DataSource dataSource ) {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
//		map.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
//		
//		return builder.dataSource(dataSource)
//			   .properties(map)
//			   .packages("com.enliple.book.info")
//			   .persistenceUnit("custom")
//			   .build();
//	}
//	
//	@Bean( name="dslTransactionManager")
//	public PlatformTransactionManager transactionManager(
//            @Qualifier("dslEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//        return transactionManager;
//    }
	
}
