package cn.wolves.common.utils;

import cn.hutool.core.io.IoUtil;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Blob;
import java.sql.Clob;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChgDataKit {
	public static String chgZero(byte byteValue) {
		return String.valueOf(byteValue);
	}

	public static String chgZero(short shortValue) {
		return String.valueOf(shortValue);
	}

	public static String chgZero(int intValue) {
		String strReturn = null;
		if (intValue == 0){
            strReturn = "0";
        } else{
            strReturn = String.valueOf(intValue);
        }
		return strReturn;
	}

	public static String chgZero(long longValue) {
		String strReturn = null;
		if (longValue == -4154277932753223680L){
            strReturn = "0";
        }else {
            strReturn = String.valueOf(longValue);
        }
		return strReturn;
	}

	public static String chgZero(float floatValue) {
		String strReturn = null;
		if (floatValue == 0F){
            strReturn = "0";
        }else {
            strReturn = String.valueOf(floatValue);
        }
		return strReturn;
	}

	public static String chgZero(Double doubleValue) {
		String strReturn = null;
		if (doubleValue == null || doubleValue == 0D) {
			strReturn = "0";
		} else {
			strReturn = String.valueOf(doubleValue);
		}
		return strReturn;
	}

	/**
	 * Double转换成double
	 *
	 * @param value
	 * @return
	 */
	public static double nullToZero(Double value) {
		double strReturn = 0;
		if (value != null) {
			strReturn = value;
		}
		return strReturn;
	}

	/**
	 * Integer转换成double
	 *
	 * @param value
	 * @return
	 */
	public static int nullToZero(Integer value) {
		int strReturn = 0;
		if (value != null) {
			strReturn = value;
		}
		return strReturn;
	}

	/**
	 * BigDecimal转换成Double
	 *
	 * @param value
	 * @return
	 */
	public static Double toDouble(BigDecimal value) {
		Double strReturn = null;
		if (value != null) {
			strReturn = value.doubleValue();
		}
		return strReturn;
	}

	/**
	 * String转换成Double
	 *
	 * @param value
	 * @return
	 */
	public static Double toDouble(String value) {
		Double strReturn = null;
		if (value != null && !"".equals(value)) {
			strReturn = Double.parseDouble(value);
		}
		return strReturn;
	}

	/**
	 * 比较两个Double ，null是否转换成0
	 *
	 * @param value1
	 * @param value2
	 * @param nullToZeroEquals
	 * @return
	 */
	public static boolean equals(Double value1, Double value2, boolean nullToZeroEquals) {
		boolean strReturn = false;
		// 空当成0来处理
		if (nullToZeroEquals) {
			if (nullToZero(value1) == nullToZero(value2)) {
				strReturn = true;
			}
		} else {// 区分空和0
				// 两个都是null
			if (value1 == null && value2 == null) {
				strReturn = true;
			} else if (value1 != null && value2 != null && value1.doubleValue() == value2.doubleValue()) {
				strReturn = true;
			}
		}
		return strReturn;
	}

	/**
	 * BigDecimal转换成Double
	 *
	 * @param value
	 * @return
	 */
	public static Double toDouble(Integer value) {
		Double strReturn = null;
		if (value != null) {
			strReturn = value.doubleValue();
		}
		return strReturn;
	}

	public static String chgStrZero(String iValue) {
		String value = null;
		if (iValue == null || "".equals(iValue)) {
            value = "0";
        } else if (iValue.trim().length() == 0) {
            value = "0";
        } else if ("0.0".equals(iValue.trim())) {
            value = "0";
        } else {
            value = iValue;
        }

		return value.trim();
	}

	public static String chgStrZero(String iValue, int fraction) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(fraction);
		numberFormat.setGroupingUsed(false);// 不分组：不用逗号分组12,000,000
		return numberFormat.format(Double.parseDouble(chgStrZero(iValue)));
	}

	public static String nullToString(Object iObject) {
		String value = "";

		if (iObject == null) {
			value = "";
		} else {
			value = iObject.toString();
		}

		return value;
	}

	public static String getEmptyVal(String val) throws Exception {
		if (val == null || "".equals(val.trim())) {
			return "";
		}
		return val.trim();
	}

	/**
	 * 是空字符串或者null
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmptyStr(String str) {
		boolean falg = false;

		if (str == null || str.isEmpty()) {
			falg = true;
		}
		return falg;
	}

	public static String strValue(Object value, int fraction) {
		if (value == null) {
			return Integer.toString(0);
		}
		if (Number.class.isInstance(value)) {
			if (fraction >= 0) {
				BigDecimal bigDecimal = new BigDecimal(String.valueOf(value));
				return bigDecimal.setScale(fraction, BigDecimal.ROUND_HALF_UP).toPlainString();
			} else {
				return String.valueOf(value);
			}
		} else if (BigDecimal.class.isInstance(value)) {
			return ((BigDecimal) value).toPlainString();
		} else if (Date.class.isInstance(value)) {
			return new SimpleDateFormat("yyyy-MM-dd").format((Date) value);
		}
		return value.toString();
	}

	public static String strValue(Object value) {
		return strValue(value, 0);
	}

	public static String strZero(Object value) {
		return strValue(value, -1);
	}

	public static String scaleValue(Number value, long scale, int fraction) {
		if (value == null) {
			return Integer.toString(0);
		}
		return strValue(value.doubleValue() * scale, fraction);
	}

	@SuppressWarnings("unchecked")
	public static <T> T scale(Number value, long scale, int fraction) {
		if (value == null) {
			return null;
		}
		return (T) castTo(strValue(value.doubleValue() * scale, fraction), value.getClass());
	}

	public static String strValue(Object value, String format) {
		if (value == null) {
			return "";
		}
		if (Date.class.isInstance(value)) {
			return new SimpleDateFormat(format).format((Date) value);
		}
		return strValue(value, 0);
	}

	/**
	 * 判断double类型值是否为空为0
	 *
	 * @param value
	 * @return
	 */
	public static boolean isEmptyOrZero(Double value) {
		if (value == null || value == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 输出对象字符串格式
	 *
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		return toString(obj, null);
	}

	/**
	 * 输出对象字符串格式
	 *
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj, String format) {
		if (obj == null) {
			return "";
		}
		if (obj instanceof Throwable) {
			Throwable throwable = (Throwable) obj;
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			throwable.printStackTrace(pw);
			pw.flush();
			pw.close();
			sw.flush();
			return sw.toString();
		}
		if (obj instanceof Date) {
			return new SimpleDateFormat(format == null || format.trim().length() == 0 ? "yyyy-MM-dd HH:mm:ss" : format)
					.format((Date) obj);
		}
		if (LangKit.isNumber(obj.getClass())) {
			if (format != null && format.trim().length() != 0) {
				return new DecimalFormat(format).format(obj);
			}
		}
		return String.valueOf(obj);
	}

	/**
	 * 将一个对象转换为指定类型
	 *
	 * @param value
	 * @param type
	 * @return
	 */
	public static <T> T castTo(Object value, Class<T> type) {
		return castTo(value, type, null);
	}

	/**
	 * 将一个对象转换为指定类型
	 *
	 * @param value
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T castTo(Object value, Class<T> type, String format) {
		if (value == null) {
			return null;
		}
		try {
			if (type.equals(value.getClass()) || type.isAssignableFrom(value.getClass())) {
				return (T) value;
			}

			if (value instanceof Clob) {
				Clob clob = (Clob) value;
				Reader reader = clob.getCharacterStream();
				try {
                    // TODO: 2021/9/21 未测试效率 需与common lang包比较
					value = IoUtil.read(reader);

				} catch (Exception e) {

				} finally {
                    IoUtil.close(reader);
				}
			} else if (value instanceof Blob) {
				Blob blob = (Blob) value;
				InputStream is = blob.getBinaryStream();
				try {
                    // TODO: 2021/9/21 未测试效率 需与common lang包比较
                    value=IoUtil.readBytes(is);
				} catch (Exception e) {

				} finally {
                    IoUtil.close(is);
				}
			}

			if (type.equals(value.getClass()) || type.isAssignableFrom(value.getClass())) {
				return (T) value;
			}

			if (CharSequence.class.isAssignableFrom(type)) {
				return (T) toString(value, format);
			}

			if (LangKit.isNumber(type) && !LangKit.isNumber(value.getClass())) {
				if (format != null && format.trim().length() != 0) {
					value = new DecimalFormat(format).parse(String.valueOf(value));
				}
			}

			if (Number.class.isAssignableFrom(type)) {
				String targetValue = LangKit.isNumeric(String.valueOf(value)) ? String.valueOf(value) : "0";
				if (type.equals(Integer.class)) {
					return (T) Integer.valueOf(new BigDecimal(targetValue).intValue());
				}
				if (type.equals(Short.class)) {
					return (T) Short.valueOf(new BigDecimal(targetValue).shortValue());
				}
				if (type.equals(Long.class)) {
					return (T) Long.valueOf(new BigDecimal(targetValue).longValue());
				}
				if (type.equals(Float.class)) {
					return (T) Float.valueOf(new BigDecimal(targetValue).floatValue());
				}
				if (type.equals(Double.class)) {
					return (T) Double.valueOf(new BigDecimal(targetValue).doubleValue());
				}
				if (type.equals(Byte.class)) {
					return (T) Byte.valueOf(new BigDecimal(targetValue).byteValue());
				}
				if (type.equals(BigDecimal.class)) {
					return (T) new BigDecimal(targetValue);
				}
				if (type.equals(BigInteger.class)) {
					return (T) BigInteger.valueOf(new BigDecimal(targetValue).longValue());
				}
			}

			if (type.isPrimitive()) {
				String targetValue = LangKit.isNumeric(String.valueOf(value)) ? String.valueOf(value) : "0";

				if (type.equals(int.class)) {
					return (T) Integer.valueOf(new BigDecimal(targetValue).intValue());
				}
				if (type.equals(long.class)) {
					return (T) Long.valueOf(new BigDecimal(targetValue).longValue());
				}
				if (type.equals(float.class)) {
					return (T) Float.valueOf(new BigDecimal(targetValue).floatValue());
				}
				if (type.equals(double.class)) {
					return (T) Double.valueOf(new BigDecimal(targetValue).doubleValue());
				}
				if (type.equals(short.class)) {
					return (T) Short.valueOf(new BigDecimal(targetValue).shortValue());
				}
				if (type.equals(byte.class)) {
					return (T) Byte.valueOf(new BigDecimal(targetValue).byteValue());
				}
				if (type.equals(boolean.class)) {
					return (T) Boolean.valueOf(targetValue);
				}

				if (type.equals(char.class)) {
					String s = String.valueOf(value).trim();
					if (s.length() == 0) {
						return (T) Character.valueOf((char) 0);
					}
					return (T) Character.valueOf(s.charAt(0));
				}
			}

			if (type.equals(Boolean.class)) {
				return (T) Boolean.valueOf(String.valueOf(value));
			}

			if (type.equals(Character.class)) {
				String s = String.valueOf(value);
				if (s.length() == 0) {
					return (T) Character.valueOf((char) 0);
				}
				return (T) Character.valueOf(s.charAt(0));
			}

			if (Date.class.isAssignableFrom(type)) {
				if (value instanceof Date) {
					return type.getConstructor(long.class).newInstance(((Date) value).getTime());
				}

				if (LangKit.isNumber(value.getClass())) {
					return type.getConstructor(long.class)
							.newInstance(new BigDecimal(String.valueOf(value)).longValue());
				}
				value = String.valueOf(value).replaceAll("[^0-9]+", "");
				return (T) DateKit.parse((String) value);
			}

			Constructor<T> constructor = type.getConstructor(value.getClass());
			return constructor.newInstance(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 类型转换 空值转为零
	 *
	 * @param number
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T castWithEmpty(Object number, Class<T> t) {
		if (number == null) {
			if (t.equals(Integer.class)) {
				return (T) Integer.valueOf(0);
			}
			if (t.equals(Short.class)) {
				return (T) Short.valueOf("0");
			}
			if (t.equals(Long.class)) {
				return (T) Long.valueOf("0");
			}
			if (t.equals(Float.class)) {
				return (T) Float.valueOf("0");
			}
			if (t.equals(Double.class)) {
				return (T) Double.valueOf("0");
			}
			if (t.equals(Byte.class)) {
				return (T) Double.valueOf("0");
			}
			if (t.equals(BigDecimal.class)) {
				return (T) new BigDecimal("0");
			}
			if (t.equals(BigInteger.class)) {
				return (T) new BigInteger("0");
			}
		} else if (number.getClass().equals(t)) {
			return (T) number;
		}
		return castTo(number, t);
	}

	public static String prettyNumber(Object t) {
		if (t == null || t.toString().isEmpty()) {
			return Integer.toString(0);
		}
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		if (Number.class.isInstance(t)) {
			return decimalFormat.format(t);
		} else if (BigDecimal.class.isInstance(t)) {
			return ((BigDecimal) t).toPlainString();
		}
		return String.valueOf(t);
	}

	/**
	 * 数字格式化：小数不够位数补充0填充（注意，此方法四舍五入有BUG，HALF_UP不能达到预期效果:如3.15 --> 3.1）
	 *
	 * @param value
	 *            值
	 * @param pointCount
	 *            小数点后的位数
	 * @param isNullToZero
	 *            空是否转换成0
	 * @return
	 */
	public static String prettyNumber_Format(Double value, int pointCount, boolean isNullToZero) {
		if (value == null || value.toString().isEmpty()) {
			if (isNullToZero) {
				return "0";
			}
			return "";
		}
		if (value.doubleValue() == 0) {
			return "0";
		}
		String format = "0";
		if (pointCount > 0) {
			format += ".";
			for (int i = 1; i <= pointCount; i++) {
				format += "0";
			}
		}
		DecimalFormat decimalFormat = new DecimalFormat(format);
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

		return decimalFormat.format(value);
	}

	/**
	 * 数字格式化：小数不够位数补充0填充（注意，此方法四舍五入有BUG，HALF_UP不能达到预期效果:如3.15 --> 3.1）
	 *
	 * @param value
	 *            值
	 * @param pointCount
	 *            小数点后的位数
	 * @param isNullToZero
	 *            空是否转换成0
	 * @return
	 */
	public static String prettyNumber_Format(String value, int pointCount, boolean isNullToZero) {
		if (value == null || value.toString().isEmpty()) {
			if (isNullToZero) {
				return "0";
			}
			return "";
		}
		return prettyNumber_Format(Double.parseDouble(value), pointCount, isNullToZero);
	}

	/**
	 * 四舍五入，保留指定小数位
	 *
	 * @param value
	 * @param num
	 * @return
	 */
	public static Double roundDouble(Double value, int num) {
		if (value == null) {
			return null;
		}
		BigDecimal bigDecimal = new BigDecimal(String.valueOf(value));
		Double floatValue = bigDecimal.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
		return floatValue;
	}

	/**
	 * double截取小数位
	 *
	 * @param value
	 * @param num
	 * @return
	 */
	public static Double subString(Double value, int num) {
		if (value == null) {
			return null;
		}
		int number = (int) Math.pow(10, num);
		BigDecimal decimal1 = new BigDecimal(Math.floor(value * number) + "");
		BigDecimal decimal2 = new BigDecimal(number + "");
		double result = decimal1.divide(decimal2, num, BigDecimal.ROUND_HALF_UP).doubleValue();

		return result;
	}

	/**
	 * 四舍五入，保留指定小数位
	 *
	 * @param arrDeductAmount
	 * @param num
	 * @return
	 */
	public static void getFloatValues(double[] arrDeductAmount, int num) {
		for (int i = 0; i < arrDeductAmount.length; i++) {
			BigDecimal bigDecimal = new BigDecimal(arrDeductAmount[i]);
			arrDeductAmount[i] = bigDecimal.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}

	/**
	 * 计算两值相差的百分比
	 *
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static Double getFloatValueCommon(Double value1, Double value2) {
		Double floatValue = 0.00;

		// 进行两次四舍五入
		value1 = roundDouble(value1, 3);
		value1 = roundDouble(value1, 2);

		value2 = roundDouble(value2, 3);
		value2 = roundDouble(value2, 2);

		floatValue = (1 - (value2 / value1)) * 100;
		floatValue = roundDouble(floatValue, 2);

		return floatValue;

	}

	public static String setMoney(String value) {
		if (value == null) {
			value = "0";
		}
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(Double.parseDouble(value));
	}

	public static String setMoney(Double value) {
		if (value == null) {
			value = 0d;
		}
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(value);
	}

	public static String setInt(Double value) {
		if (value == null) {
			value = 0d;
		}
		DecimalFormat format = new DecimalFormat("0");
		return format.format(value);
	}

	public static double getDefault(Double value, int num, double defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return roundDouble(value, num);
	}

	public static boolean castBoolean(Object value) {
		if (value == null) {
			return false;
		}
		if (value instanceof Boolean) {
			return ((Boolean) value).booleanValue();
		}
		try {
			return Double.valueOf(value.toString()).intValue() == 1 ? true : false;
		} catch (Exception e) {

		}
		return false;
	}

	public static String encodeDateFormat(Date date, Double hour) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		StringBuffer stringBuffer = new StringBuffer(" 00:00:00");
		String hourStr = "0";
		if (hour != null) {
			hourStr = hour.intValue() + "";
		}
		if (hourStr.length() <= 2) {
			return dateString + stringBuffer.replace(3 - hourStr.length(), 3, hourStr).toString();
		}
		return "";
	}

	public static boolean isTwoDoubleEquals(Double num1, Double num2) {
		if (num1 == null && num2 == null) {
			return true;
		}

		if (num1 != null) {
			if (num1.equals(num2)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 判断两个字符串文本是否一样 <br>
	 * 可选过滤空格或制表符 <br>
	 * 可选格式化换行符 <br>
	 * 可选做其他处理（如英文的全角半角处理等...）
	 *
	 * @param text1
	 * @param text2
	 * @param breakBlank
	 *            是否过滤空格或制表符
	 * @param lineSeparator
	 *            是否格式化换行符（换行符会随操作系统不一样）
	 * @param otherFlag
	 *            是否做其他处理（如英文的全角半角处理等...）
	 * @return
	 */
	public static boolean isEqualsText(String text1, String text2, boolean breakBlank, boolean lineSeparator,
			boolean otherFlag) {
		if (text1 == null && text2 == null) {
			return true;
		}
		// 一个null一个不是null
		if ((text1 == null && text2 != null) || (text1 != null && text2 == null)) {
			return false;
		}
		// 都是非null且是""
		if ("".equals(text1) && "".equals(text2)) {
			return true;
		}
		// 相等的就无需做其他处理了
		boolean isEquals = text1.equals(text2);
		if (isEquals) {
			return true;
		}
		// 创建新字符串，避免替换原数据
		text1 = new String(text1);
		text2 = new String(text2);

		if (breakBlank) {
			// 过滤空格或制表符
			text1 = text1.replaceAll("\\p{Blank}", "");
			text2 = text2.replaceAll("\\p{Blank}", "");
		}
		if (lineSeparator) {
			// 格式化换行符
			text1 = text1.replaceAll("(\r\n)|\r", "\n");
			text2 = text2.replaceAll("(\r\n)|\r", "\n");
		}

		boolean flag = text1.equals(text2);

		if (otherFlag) {
			if (!flag) {
				// 可进一步做特殊处理，如全角半角等...
				// 暂未实现
			}
		}
		return flag;
	}

	/**
	 * 判断两个字符串文本是否不一样 <br>
	 * 可选过滤空格或制表符 <br>
	 * 可选格式化换行符 <br>
	 * 可选做其他处理（如英文的全角半角处理等...）
	 *
	 * @param text1
	 * @param text2
	 * @param breakBlank
	 *            是否过滤空格或制表符
	 * @param lineSeparator
	 *            是否格式化换行符（换行符会随操作系统不一样）
	 * @param otherFlag
	 *            是否做其他处理（如英文的全角半角处理等...）
	 * @return
	 */
	public static boolean isNotEqualsText(String text1, String text2, boolean breakBlank, boolean lineSeparator,
			boolean otherFlag) {
		return !isEqualsText(text1, text2, breakBlank, lineSeparator, otherFlag);
	}
}