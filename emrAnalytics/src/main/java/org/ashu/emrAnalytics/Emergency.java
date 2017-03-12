package org.ashu.emrAnalytics;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * @author ashu
 *
 */
public class Emergency implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double lat;
	private Double lng;
	private String desc;
	private Integer zip;
	private String title;
	private Timestamp timeStamp;
	private String twp;
	private String addr;
	private Integer e;

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public int getYear() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeStamp.getTime());
		return cal.get(Calendar.YEAR);
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTwp() {
		return twp;
	}

	public void setTwp(String twp) {
		this.twp = twp;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getE() {
		return e;
	}

	public void setE(Integer e) {
		this.e = e;
	}

	public Emergency(Double lat, Double lng, String desc, Integer zip, String title, Timestamp timeStamp, String twp,
			String addr, Integer e) {
		super();
		this.lat = lat;
		this.lng = lng;
		this.desc = desc;
		this.zip = zip;
		this.title = title;
		this.timeStamp = timeStamp;
		this.twp = twp;
		this.addr = addr;
		this.e = e;
	}

	public Emergency() {

	}

	@Override
	public String toString() {
		return "Emergency [lat=" + lat + ", lng=" + lng + ", desc=" + desc + ", zip=" + zip + ", title=" + title
				+ ", timeStamp=" + timeStamp + ", twp=" + twp + ", addr=" + addr + ", e=" + e + "]";
	}

}
