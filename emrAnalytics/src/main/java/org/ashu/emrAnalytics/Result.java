package org.ashu.emrAnalytics;

import scala.Serializable;

/**
 * @author ashu
 *
 */
public class Result implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	
	private String column1;
	
	private Long column2;

	public Result(String _1, Long _2) {
		column1 = _1;
		column2 = _2;
	}
	
	public Result(){
		
	}

	

	

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public Long getColumn2() {
		return column2;
	}

	public void setColumn2(Long column2) {
		this.column2 = column2;
	}
	
	

}
