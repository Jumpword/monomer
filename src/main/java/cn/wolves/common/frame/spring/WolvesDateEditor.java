package cn.wolves.common.frame.spring;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.lang.Nullable;

public class WolvesDateEditor extends CustomDateEditor {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private boolean isSimpleFormat = true;

	public WolvesDateEditor() {
		this(new SimpleDateFormat("yyyy-MM-dd"), false);
	}

	public WolvesDateEditor(DateFormat dateFormat, boolean allowEmpty) {
		super(dateFormat, allowEmpty);
		dateFormat.setLenient(false);
	}

	@Override
	public void setAsText(@Nullable String text) throws IllegalArgumentException {
		try {
			super.setAsText(text);
		} catch (IllegalArgumentException e) {
			try {
				setValue(dateFormat.parse(text));
				isSimpleFormat = false;
			} catch (Exception ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
	}

	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		return isSimpleFormat ? super.getAsText() : (value != null ? this.dateFormat.format(value) : "");
	}

}
