package com.zhou.dao;

import java.io.Serializable;

public class Line implements Serializable
{
	private String hospital_id;
	private String doctor_name;
	private String evaluate_date;
	private String evaluate_starttime;
	private String evaluate_endtime;
	private int simple_a_error;
	private int simple_b_error;
	public String getHospital_id() 
	{
		return hospital_id;
	}
	public void setHospital_id(String hospital_id) 
	{
		this.hospital_id = hospital_id;
	}
	public String getDoctor_name() 
	{
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) 
	{
		this.doctor_name = doctor_name;
	}
	public String getEvaluate_date() 
	{
		return evaluate_date;
	}
	public void setEvaluate_date(String evaluate_date) 
	{
		this.evaluate_date = evaluate_date;
	}
	public String getEvaluate_starttime() 
	{
		return evaluate_starttime;
	}
	public void setEvaluate_starttime(String evaluate_starttime) 
	{
		this.evaluate_starttime = evaluate_starttime;
	}
	public String getEvaluate_endtime() 
	{
		return evaluate_endtime;
	}
	public void setEvaluate_endtime(String evaluate_endtime) 
	{
		this.evaluate_endtime = evaluate_endtime;
	}
	public int getSimple_a_error() 
	{
		return simple_a_error;
	}
	public void setSimple_a_error(int simple_a_error) 
	{
		this.simple_a_error = simple_a_error;
	}
	public int getSimple_b_error() 
	{
		return simple_b_error;
	}
	public void setSimple_b_error(int simple_b_error) 
	{
		this.simple_b_error = simple_b_error;
	}
}
