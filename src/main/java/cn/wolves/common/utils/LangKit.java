package cn.wolves.common.utils;

import static java.util.regex.Pattern.compile;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jumping
 */
public final class LangKit {

	/**
	 * 判断是否是数字
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = compile("-?[0-9]+\\.?[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否是数字类型
	 *
	 * @param type
	 * @return
	 */
	public static boolean isNumber(Class<?> type) {
		if (Number.class.isAssignableFrom(type)) {
			return true;
		}
		if (type.equals(int.class)) {
			return true;
		}
		if (type.equals(short.class)) {
			return true;
		}
		if (type.equals(long.class)) {
			return true;
		}
		if (type.equals(float.class)) {
			return true;
		}
		if (type.equals(double.class)) {
			return true;
		}
		if (type.equals(byte.class)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个对象是否是空对象
	 *
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof CharSequence) {
			return obj.toString().trim().length() == 0;
		}
		if (obj.getClass().equals(Object.class)) {
			return true;
		}
		if (isBaseType(obj.getClass())) {
			return false;
		}
		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		try {
			return Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors().length == 1;
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 判断一个类型是否是基本类型
	 *
	 * @param type
	 * @return
	 */
	public static boolean isBaseType(Class<?> type) {
		if (type.isPrimitive()) {
			return true;
		}
		if (CharSequence.class.isAssignableFrom(type)) {
			return true;
		}
		if (Number.class.isAssignableFrom(type)) {
			return true;
		}
		if (Date.class.isAssignableFrom(type)) {
			return true;
		}
		if (Boolean.class.equals(type)) {
			return true;
		}
		if (Character.class.equals(type)) {
			return true;
		}
		if (Class.class.equals(type)) {
			return true;
		}
		if (StringBuilder.class.equals(type)) {
			return true;
		}
		if (StringBuffer.class.equals(type)) {
			return true;
		}
		if (Object.class.equals(type)) {
			return true;
		}
		if (Void.class.equals(type)) {
			return true;
		}
		return false;
	}

}
