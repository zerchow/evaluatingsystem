package com.zhou.dao;

import java.io.Serializable;

public class Mmse implements Serializable 
{
	private String hospital_id;
	private String doctor_name;
	private String evaluate_date;
	private String evaluate_starttime;
	private String evaluate_endtime;
	private int score;
	private String awareness_state;
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
	public String getAwareness_state() 
	{
		return awareness_state;
	}
	public void setAwareness_state(String awareness_state) 
	{
		this.awareness_state = awareness_state;
	}
}
