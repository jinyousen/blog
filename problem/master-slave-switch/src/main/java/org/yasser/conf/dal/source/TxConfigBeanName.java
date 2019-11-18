package org.yasser.conf.dal.source;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Arrays;
import java.util.Properties;

/**
 * SLOGAN: 做一个会写四种茴，笨且自律的人
 *
 * @Author Yasser
 * @Date 2019-11-12
 * @Description
 */

@Configuration
public class TxConfigBeanName {

    @Autowired
    private DataSourceTransactionManager transactionManager;


    // 创建事务通知
    @Bean(name = "txAdvice")
    public TransactionInterceptor getAdvisor() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("add*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("save*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("delete*", "PROPAGATION_REQUIRED,-Exception");

        TransactionInterceptor tsi = new TransactionInterceptor(transactionManager, properties);
        return tsi;
    }

//    @Bean
//    public BeanNameAutoProxyCreator txProxy() {
//        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
//        creator.setInterceptorNames("txAdvice");
//        creator.setBeanNames("*Service", "*ServiceImpl");
//        creator.setProxyTargetClass(true);
//        return creator;
//    }


    @Bean
    public AnnotationAwareAspectJAutoProxyCreator txProxy() {
        /*
         * 必须使用AspectJ方式的AutoProxy，这样才能和DataSourceSwitchAspect保持统一的aop拦截方式，否则不同的拦截方式会导致order失效
         */
        AnnotationAwareAspectJAutoProxyCreator creator = new AnnotationAwareAspectJAutoProxyCreator();
        creator.setInterceptorNames("txAdvice");
        creator.setIncludePatterns(Arrays.asList("execution (public org.yasser..*Service(..))"));
        creator.setProxyTargetClass(true);
        creator.setOrder(2);
        return creator;
    }

}
