package com.lizhihao.cms.comons;

import java.io.Serializable;

/**
 * @author LZH
 * @Date 2019年10月22日 用户前后端交互数据结构体
 */

public class ResultMsg implements Serializable {

	private static final long serialVersionUID = 347281007166872483L;
	
	private int result;          // 处理的结果
	private String message;      // 消息
	private Object data;         // 返回的数据

	
	public ResultMsg(int result, String message, Object data) {
		super();
		this.result = result;
		this.message = message;
		this.data = data;
	}

	/**
	 * @return the result
	 */
	public int getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
