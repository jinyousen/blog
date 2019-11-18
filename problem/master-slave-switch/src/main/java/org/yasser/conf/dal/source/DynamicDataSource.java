package org.yasser.conf.dal.source;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * SLOGAN: 做一个会写四种茴，笨且自律的人
 *
 * @Author Yasser
 * @Date 2019-11-12
 * @Description
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 本地线程共享对象
     * 动态数据源持有者，负责利用ThreadLocal存取数据源名称
     */
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void putDataSource(String name) {
        THREAD_LOCAL.set(name);
    }

    public static String getDataSource() {
        return THREAD_LOCAL.get();
    }

    public static void removeDataSource() {
        THREAD_LOCAL.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }
}
