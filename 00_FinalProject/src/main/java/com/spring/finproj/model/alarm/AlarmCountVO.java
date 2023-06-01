package com.spring.finproj.model.alarm;

public class AlarmCountVO {
	//count
	private int totalCount;
	private int checkCount;
	
	public AlarmCountVO() {
		this.totalCount = 0;
		this.checkCount = 0;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount() {
		this.totalCount += 1;
	}

	public int getCheckCount() {
		return checkCount;
	}

	public void setCheckCount() {
		this.checkCount += 1;
	}
}