package ${packageName}.service.json;

import com.alibaba.fastjson.JSON;

/**
 * <pre>
 * 基础的json对象，就两个值 code和data
 * </pre>
 * 
 * @author 梁韦江 2015年7月7日
 */
public class BaseJsonResponse{

	/** 成功的返回码 */
	public static final int CODE_OK = 1;

	/** 错误返回码：通用，无需用特别的代码说明原因 */
	public static final int ERROR_COMMON = -1;

	/** 错误返回码：参数错误 */
	public static final int ERROR_INVALID_PARAMS = -2;

	/** 错误返回码：登录超时 */
	public static final int ERROR_LOGIN_TIME_OUT = -3;

	/** 错误返回码：登录时邮箱检验状态为未通过 */
	public static final int ERROR_LOGIN_EMAIL_CHECK_FAILED = -4;

	/** 错误返回码：在已经通过邮箱验证的基础上进行重复验证 */
	public static final int EMAIL_REPEAT_CHECK = -5;
	
	/** 错误返回码：不被允许的输入参数 */
	public static final int ERROR_PARAM = -6;

	private int code = CODE_OK;

	private String message;
	private String data;

	public void setSuccess(boolean success) {
		if (success) {
			this.code = CODE_OK;
		} else {
			this.code = ERROR_COMMON;
		}
	}

	public boolean isSuccess() {
		return this.code == CODE_OK;
	}

	public void setCodeSuccess(String message) {
		this.code = CODE_OK;
		this.message = message;
	}

	public void setSuccessData(String data) {
		this.code = CODE_OK;
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 设置返回代码为失败
	 */
	public void setCodeError(String errorMsg) {
		this.setCodeError(ERROR_COMMON, errorMsg);
	}

	/**
	 * 设置返回代码为失败
	 */
	public void setCodeError(int errorCode, String errorMsg) {
		this.code = errorCode;
		this.message = errorMsg;
	}

	public String toJsonString() {
		return JSON.toJSONString(this);
	}

	@Override
	public String toString() {
		return this.toJsonString();
	}

}
