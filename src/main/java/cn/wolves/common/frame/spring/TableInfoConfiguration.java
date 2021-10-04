package cn.wolves.common.frame.spring;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jumping
 */
@Component
public class TableInfoConfiguration {

	private final static Map<String, String> Messages = new HashMap<String, String>();

	public String getTableFieldMsg(String key) {
		return Messages.get(key);
	}

	public void inintMessages(Map<String, String> messages) {
		Messages.putAll(messages);
	}

}
