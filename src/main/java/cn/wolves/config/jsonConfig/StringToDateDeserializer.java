package cn.wolves.config.jsonConfig;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jumping
 */
public class StringToDateDeserializer extends DateDeserializers.DateDeserializer {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
	private static final String DATE_FORMAT2 = "yyyy/MM/dd HH:mm:ss";
	private static final String SHORT_DATE_FORMAT2 = "yyyy/MM/dd";
	private static final String DATE_FORMAT3 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	private static final String DATE_FORMAT4 = "yyyy/MM/dd'T'HH:mm:ss'Z'";

	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException,
        JsonProcessingException {
		String source = p.getText();
		if (StrUtil.isEmpty(source)) {
			return null;
		}
		source = source.trim();
		try {
			return super.deserialize(p, ctxt);
		} catch (Exception e) {
			try {
				SimpleDateFormat formatter;
				if (source.contains("T")) {
					if (source.contains("-")) {
						formatter = new SimpleDateFormat(DATE_FORMAT3);
                        return formatter.parse(source);
					} else {
						formatter = new SimpleDateFormat(DATE_FORMAT4);
                        return formatter.parse(source);
					}
				} else if (source.contains("-")) {
					if (source.contains(":")) {
						formatter = new SimpleDateFormat(DATE_FORMAT);
					} else {
						formatter = new SimpleDateFormat(SHORT_DATE_FORMAT);
					}
                    return formatter.parse(source);
				} else if (source.contains("/")) {
					if (source.contains(":")) {
						formatter = new SimpleDateFormat(DATE_FORMAT2);
					} else {
						formatter = new SimpleDateFormat(SHORT_DATE_FORMAT2);
					}
                    return formatter.parse(source);
				}
			} catch (Exception e1) {
				throw new RuntimeException(String.format("parser %s to Date fail", source));
			}
		}
		throw new RuntimeException(String.format("parser %s to Date fail", source));
	}
}