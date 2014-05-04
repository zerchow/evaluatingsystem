package com.zhou.model;

import java.io.Serializable;

public class Cued implements Serializable
{
	private String hospital_id;
	private String doctor_name;
	private String evaluate_date;
	private String evaluate_starttime;
	private String evaluate_endtime;
	private long evaluate_millisecond;
	private String starttime;
	private String endtime;
	private long millisecond;
	private int correct;
	private int error;
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
	public int getCorrect() 
	{
		return correct;
	}
	public void setCorrect(int correct) 
	{
		this.correct = correct;
	}
	public int getError() 
	{
		return error;
	}
	public void setError(int error) 
	{
		this.error = error;
	}
	public long getEvaluate_millisecond() 
	{
		return evaluate_millisecond;
	}
	public void setEvaluate_millisecond(long evaluate_millisecond) 
	{
		this.evaluate_millisecond = evaluate_millisecond;
	}
	public String getStarttime() 
	{
		return starttime;
	}
	public void setStarttime(String starttime) 
	{
		this.starttime = starttime;
	}
	public String getEndtime() 
	{
		return endtime;
	}
	public void setEndtime(String endtime) 
	{
		this.endtime = endtime;
	}
	public long getMillisecond() 
	{
		return millisecond;
	}
	public void setMillisecond(long millisecond) 
	{
		this.millisecond = millisecond;
	}
}
