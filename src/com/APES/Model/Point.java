package com.APES.Model;


public class Point {
	private double lon; // ���徭��
	private double lat; // ����γ��
	private String time;
	
	public Point() {
	}
	public Point(double lon, double lat, String time) {
		super();
		this.lon = lon;
		this.lat = lat;
		this.time = time;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
