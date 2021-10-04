package cn.wolves.common.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * @author Jumping
 */
@Slf4j
public final class BeanKit {

    public static <T, S> T from(S s, Class<T> t) {
        T to = null;
        if (s == null) {
            return to;
        }
        try {
            to = t.newInstance();
            PropertyDescriptor[] sourcePds = BeanUtils.getPropertyDescriptors(s.getClass());
            for (PropertyDescriptor sourcePd : sourcePds) {
                try {
                    Method readMethod = sourcePd.getReadMethod();
                    PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(t, sourcePd.getName());
                    if (targetPd == null) {
                        continue;
                    }
                    Method targetWriteMethod = targetPd.getWriteMethod();
                    if (targetWriteMethod == null) {
                        continue;
                    }
                    if (readMethod == null) {
                        continue;
                    }
                    Object value = readMethod.invoke(s);
                    if (value == null) {
                        continue;
                    }
                    Class<?> sourceObjType = readMethod.getReturnType();
                    if (BeanUtils.isSimpleProperty(sourceObjType)) {
                        value = ChgDataKit.castTo(value, targetWriteMethod.getParameterTypes()[0]);
                    } else if (Map.class.isAssignableFrom(sourceObjType)) {
                        Map instance = HashMap.class.newInstance();
                        instance.putAll((Map) value);
                        value = instance;
                    } else if (Collection.class.isAssignableFrom(sourceObjType)) {
                        ParameterizedType type = (ParameterizedType) targetWriteMethod.getGenericParameterTypes()[0];
                        Type typeC = type.getActualTypeArguments()[0];
                        value = from((Collection<?>) value, Class.forName(typeC.getTypeName()));
                    } else if (!BeanUtils.isSimpleProperty(sourceObjType)) {
                        value = from(value, targetWriteMethod.getParameterTypes()[0]);
                    }
                    targetWriteMethod.invoke(to, value);
                } catch (Exception e) {
                    log.warn("拷贝出错=》{}", e.getMessage());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("拷贝出错=》{}", e.getMessage());
        }
        return to;
    }

    public static <T, S> List<T> from(Collection<S> sources, Class<T> t) {
        if (sources == null) {
            return null;
        }
        List<T> targets = new ArrayList<T>();
        sources.forEach(source -> {
            targets.add(from(source, t));
        });
        return targets;
    }

    public static <T, S> T copy(S s, T t, boolean includeNul, String... excludeProperties) {
        List<String> excluseList = CollUtil.newArrayList(excludeProperties);
        PropertyDescriptor[] sourcePds = BeanUtils.getPropertyDescriptors(s.getClass());
        for (PropertyDescriptor sourcePd : sourcePds) {
            try {
                if (excluseList.contains(sourcePd.getName())) {
                    continue;
                }
                Method readMethod = sourcePd.getReadMethod();
                PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(t.getClass(), sourcePd.getName());
                if (targetPd == null) {
                    continue;
                }
                Method targetWriteMethod = targetPd.getWriteMethod();
                if (targetWriteMethod == null) {
                    continue;
                }
                if (readMethod == null) {
                    continue;
                }
                Object value = readMethod.invoke(s);
                if (includeNul && value == null) {
                    targetPd.getWriteMethod().invoke(t, value);
                    continue;
                } else if (value == null) {
                    continue;
                } else {
                    Class<?> sourceObjType = readMethod.getReturnType();
                    if (Map.class.isAssignableFrom(sourceObjType)) {
                        Map instance = HashMap.class.newInstance();
                        instance.putAll((Map) value);
                        value = instance;
                    } else if (Collection.class.isAssignableFrom(sourceObjType)) {
                        ParameterizedType type = (ParameterizedType) targetWriteMethod.getGenericParameterTypes()[0];
                        Type typeC = type.getActualTypeArguments()[0];
                        value = from((Collection<?>) value, Class.forName(typeC.getTypeName()));
                    } else if (sourceObjType.isArray()) {
                        Class<?> clazz = Class.forName(targetWriteMethod.getParameterTypes()[0].getTypeName()
                            .replace("[", "").replace("]", ""));
                        value = from(Arrays.asList(value), clazz).toArray((Object[]) Array
                            .newInstance(clazz, 0));
                    } else if (!BeanUtils.isSimpleProperty(sourceObjType)) {
                        value = from(value, targetWriteMethod.getParameterTypes()[0]);
                    } else if (BeanUtils.isSimpleProperty(sourceObjType)) {
                        value = ChgDataKit.castTo(value, targetWriteMethod.getParameterTypes()[0]);
                    }
                }
                targetPd.getWriteMethod().invoke(t, value);
            } catch (Exception e) {
                log.warn("拷贝出错=》{}", e.getMessage());
            }

        }
        return t;
    }

    /**
     * 设置默认值
     *
     * @param t
     * @param key
     * @param value
     */
    public static <T> void setDefaultValue(T t, String[] key, Object[] value) {
        PropertyDescriptor[] sourcePds = BeanUtils.getPropertyDescriptors(t.getClass());
        List<String> keys = Arrays.asList(key);
        List<Object> values = Arrays.asList(value);
        for (PropertyDescriptor sourcePd : sourcePds) {
            try {
                Method writeMethod = sourcePd.getWriteMethod();
                PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(t.getClass(), sourcePd.getName());
                if (targetPd == null) {
                    continue;
                }
                if (writeMethod == null) {
                    continue;
                }
                Class<?> sourceObjType = writeMethod.getParameterTypes()[0];
                int index = keys.indexOf(sourcePd.getName());
                if (BeanUtils.isSimpleProperty(sourceObjType) && index != -1) {
                    writeMethod.invoke(t, values.get(index));
                } else if (!BeanUtils.isSimpleProperty(sourceObjType)) {
                    setDefaultValue(sourcePd.getReadMethod().invoke(t), key, value);
                }
            } catch (Exception e) {
                log.warn("拷贝出错=》{}", e.getMessage());
            }
        }

    }

    /**
     * 设置默认值
     *
     * @param t
     * @param key
     * @param value
     */
    public static <T> void setDefaultValue(T t, String key, Object value) {
        PropertyDescriptor[] sourcePds = BeanUtils.getPropertyDescriptors(t.getClass());
        for (PropertyDescriptor sourcePd : sourcePds) {
            try {
                Method writeMethod = sourcePd.getWriteMethod();
                PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(t.getClass(), sourcePd.getName());
                if (targetPd == null) {
                    continue;
                }
                if (writeMethod == null) {
                    continue;
                }
                Class<?> sourceObjType = writeMethod.getParameterTypes()[0];
                if (BeanUtils.isSimpleProperty(sourceObjType) && sourcePd.getName().equals(key)) {
                    writeMethod.invoke(t, value);
                } else if (!BeanUtils.isSimpleProperty(sourceObjType)) {
                    setDefaultValue(sourcePd.getReadMethod().invoke(t), key, value);
                }
            } catch (Exception e) {
                log.warn("拷贝出错=》{}", e.getMessage());
            }
        }

    }

    /**
     * 比较两个对象是否相同，对于数字、日期等按照大小进行比较，自动兼容包装器实例
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(Object a, Object b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        // 比较数字
        if (LangKit.isNumber(a.getClass()) && LangKit.isNumber(b.getClass())) {
            return new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString())) == 0;
        }
        // 比较字符
        if ((a.getClass().equals(Character.class) || a.getClass().equals(Character.TYPE))
            && (b.getClass().equals(Character.class) || b.getClass().equals(Character.TYPE))) {
            return ((Character) a).equals((Character) b);
        }
        // 比较真假值
        if ((a.getClass().equals(Boolean.class) || a.getClass().equals(Boolean.TYPE))
            && (b.getClass().equals(Boolean.class) || b.getClass().equals(Boolean.TYPE))) {
            return ((Boolean) a).equals((Boolean) b);
        }
        // 比较日期
        if (a instanceof Date && b instanceof Date) {
            return ((Date) a).compareTo((Date) b) == 0;
        }
        return a.equals(b);
    }

    public static <T> T fromMap(Map map, Class<T> t) throws Exception {
        return JSON.parseObject(JSON.toJSONString(map), t);
    }

    public static <T> Map<String, Object> toMap(T t) throws Exception {
        return JSON.parseObject(JSON.toJSONString(t), Map.class);
    }

    public static boolean isEmpty(Object value) {
        return value == null ? true : value.toString().isEmpty();
    }

    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }

}
