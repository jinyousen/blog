package org.yasser.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
public class DozerUtils {

    private static DozerBeanMapper mapper = new DozerBeanMapper();

    /**
     * 单个对象数据转换
     *
     * @param source
     * @param destinationClass
     * @param <T>
     * @return
     */
    public static <T> T convert(Object source, Class<T> destinationClass) {
        if (source == null)
            return null;
        return mapper.map(source, destinationClass);
    }

    /**
     * 单个对象数据转换
     *
     * @param source
     * @param destinationClass
     * @param <T>
     * @return
     */
    public static <T, S> T convert(S source, Class<T> destinationClass, AbstractConvertCallBack<T, S> callBack) {
        if (source == null)
            return null;
        T target = mapper.map(source, destinationClass);
        callBack.callBack(target, source, destinationClass);
        return target;
    }

    /**
     * 列表数据转换
     *
     * @param sourceList
     * @param destinationClass
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T, S> List<T> convertList(List<S> sourceList, Class<T> destinationClass) {
        if (CollectionUtils.isNotEmpty(sourceList)) {
            List<T> retList = new ArrayList<T>();
            for (S source : sourceList) {
                retList.add(mapper.map(source, destinationClass));
            }
            return retList;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 列表数据转换
     *
     * @param sourceList
     * @param destinationClass
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T, S> List<T> convertList(List<S> sourceList, Class<T> destinationClass, AbstractConvertCallBack<T, S> callBackAbstract) {
        if (CollectionUtils.isNotEmpty(sourceList)) {
            List<T> retList = new ArrayList<T>();
            for (S source : sourceList) {
                T t1 = mapper.map(source, destinationClass);
                callBackAbstract.callBackForList(retList, t1, source, destinationClass);
            }
            return retList;
        }
        return new ArrayList();
    }


    public static Object poToVo(Object t, Object e) {
        Field[] po = getFields(t.getClass());
        Field[] vo = getFields(e.getClass());
        for (Field p : po) {
            for (Field v : vo) {
                if (v.getName().equals(p.getName())) {
                    try {
                        p.setAccessible(true);
                        v.setAccessible(true);
                        if (p.getType() == v.getType()) {
                            v.set(e, p.get(t));
                        } else if (p.getType() == Date.class && v.getType() == String.class) {
                            Date date = (Date) p.get(t);
                            String time = DateUtil.convertDateToStrFormat(date, "yyyy-MM-dd HH:mm:ss");
                            v.set(e, time);
                        } else if (Number.class.isAssignableFrom(p.getType()) && v.getType() == String.class) {
                            v.set(e, String.valueOf(p.get(t)));
                        }
                        break;
                    } catch (Exception ex) {
                        log.error("DozerUtils 实体信息转换有误 error: {}", ex);
                    }
                }
            }
        }
        return e;
    }

    public static Field[] getFields(Class clazz) {
        List<Field> list = new ArrayList<Field>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            for (Field f : clazz.getDeclaredFields()) {
                list.add(f);
            }
        }
        return list.toArray(new Field[list.size()]);
    }

}

class AbstractConvertCallBack<T, S> {
    public void callBackForList(List<T> list, T element, S source, Class clazz) {
    }

    public void callBack(T element, S source, Class clazz) {

    }
}
