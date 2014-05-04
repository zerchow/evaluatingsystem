package com.zhou.model;

import java.io.Serializable;

public class Hanoi implements Serializable 
{
	private String hospital_id;
	private String doctor_name;
	private String evaluate_date;
	private String evaluate_starttime;
	private String evaluate_endtime;
	private long evaluate_millisecond;
	private String prac_starttime;
	private String prac_endtime;
	private long prac_millisecond;
	private String test_starttime;
	private String test_endtime;
	private long test_millisecond;
	public long getEvaluate_millisecond() 
	{
		return evaluate_millisecond;
	}
	public void setEvaluate_millisecond(long evaluate_millisecond) 
	{
		this.evaluate_millisecond = evaluate_millisecond;
	}
	private int score;
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
	public int getScore() 
	{
		return score;
	}
	public void setScore(int score) 
	{
		this.score = score;
	}
	public String getPrac_starttime() 
	{
		return prac_starttime;
	}
	public void setPrac_starttime(String prac_starttime) 
	{
		this.prac_starttime = prac_starttime;
	}
	public String getPrac_endtime() 
	{
		return prac_endtime;
	}
	public void setPrac_endtime(String prac_endtime) 
	{
		this.prac_endtime = prac_endtime;
	}
	public long getPrac_millisecond() 
	{
		return prac_millisecond;
	}
	public void setPrac_millisecond(long prac_millisecond) 
	{
		this.prac_millisecond = prac_millisecond;
	}
	public String getTest_starttime() 
	{
		return test_starttime;
	}
	public void setTest_starttime(String test_starttime) 
	{
		this.test_starttime = test_starttime;
	}
	public String getTest_endtime() 
	{
		return test_endtime;
	}
	public void setTest_endtime(String test_endtime) 
	{
		this.test_endtime = test_endtime;
	}
	public long getTest_millisecond() 
	{
		return test_millisecond;
	}
	public void setTest_millisecond(long test_millisecond) 
	{
		this.test_millisecond = test_millisecond;
	}
}
