package com.ajithsolomon.ajiranet.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
public class ResponseObject {

	@JsonInclude(Include.NON_NULL)
	private String msg;

	@JsonInclude(Include.NON_NULL)
	private String value;

	public ResponseObject(String msg) {
		super();
		this.msg = msg;
	}

	public ResponseObject(String msg, String value) {
		super();
		this.msg = msg;
		this.value = value;
	}

	public ResponseObject() {
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "[msg=" + msg + ", value=" + value + "]";
	}

}
