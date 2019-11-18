package org.yasser.conf.dal.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.yasser.annotation.TargetDataSource;
import org.yasser.conf.dal.source.DynamicDataSource;
import org.yasser.constant.DalConstant;

import java.lang.reflect.Method;

/**
 * SLOGAN: 做一个会写四种茴，笨且自律的人
 *
 * @Author Yasser
 * @Date 2019-11-12
 * @Description
 */


@Slf4j
@Aspect
@Order(-2147483647)//保证该AOP在@Transactional之前执行
@Configuration
public class DataSourceAspect {

    @Value("${datasource.read.list:}")
    private String dataSourceReadList;

    /*
     * 定义一个切入点
     */
    @Pointcut("execution(* org.yasser.service.*..*(..))")
    public void dataSourcePointCut() {
    }

    /*
     * 通过连接点切入
     */
    @Before("dataSourcePointCut()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            String method = joinPoint.getSignature().getName();
            Object target = joinPoint.getTarget();
            Class<?>[] classz = target.getClass().getInterfaces();
            Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(TargetDataSource.class)) {
                TargetDataSource data = m.getAnnotation(TargetDataSource.class);
                String dataSourceName = data.value();
                DynamicDataSource.putDataSource(dataSourceName);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            DynamicDataSource.putDataSource(DalConstant.DATA_SOURCE_MASTER);
            log.error("DataSourceAspect is error!", e);
        }
    }

    @AfterReturning(returning = "ret", pointcut = "dataSourcePointCut()")
    public void doAfterReturning(Object ret) throws Throwable {
    }


    //执行完切面后，将线程共享中的数据源名称清空
    @After("dataSourcePointCut()")
    public void after(JoinPoint joinPoint) {
        DynamicDataSource.removeDataSource();
    }

}
