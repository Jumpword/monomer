package cn.wolves.common.exception;

/**
 * @author Jumping
 */
public class BusinessException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String message;

	protected String errorCode;

	public BusinessException() {
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public BusinessException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.message = message;
	}

	public static BusinessException errorCode(String errorCode) {
		BusinessException exception = new BusinessException();
		exception.errorCode = errorCode;
		return exception;
	}

	public BusinessException msg(String message) {
		this.message = message;
		return this;
	}

	public BusinessException code(String errorCode) {
		this.errorCode = errorCode;
		return this;
	}

	public void ret() throws BusinessException {
		throw this;
	}

	public BusinessException(String message) {
		this.message = message;
	}

	public BusinessException(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
