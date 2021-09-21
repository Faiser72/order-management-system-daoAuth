package com.aspiresys.oms.beans;

import java.util.List;
import java.util.Map;

public class OmsResponse {

	private boolean success;
	private String message;
	private Object object;
	private List<?> listObject;
	private int status;
	private double value;
	private Map<?, ?> map;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public List<?> getListObject() {
		return listObject;
	}

	public void setListObject(List<?> listObject) {
		this.listObject = listObject;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Map<?, ?> getMap() {
		return map;
	}

	public void setMap(Map<?, ?> map) {
		this.map = map;
	}

}
