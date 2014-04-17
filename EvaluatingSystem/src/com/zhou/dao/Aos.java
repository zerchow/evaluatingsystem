package com.zhou.dao;

import java.io.Serializable;

public class Aos implements Serializable 
{
	private String hospital_id;
	private String doctor_name;
	private String evaluate_date;
	private String evaluate_starttime;
	private String evaluate_endtime;
	private float ospan_absoluate_score;
	private float ospan_total_correct;
	private float math_total_errors;
	private float math_speed_errors;
	private float math_accuracy_errors;
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
	public float getOspan_absoluate_score() 
	{
		return ospan_absoluate_score;
	}
	public void setOspan_absoluate_score(float ospan_absoluate_score) 
	{
		this.ospan_absoluate_score = ospan_absoluate_score;
	}
	public float getOspan_total_correct() 
	{
		return ospan_total_correct;
	}
	public void setOspan_total_correct(float ospan_total_correct) 
	{
		this.ospan_total_correct = ospan_total_correct;
	}
	public float getMath_total_errors() 
	{
		return math_total_errors;
	}
	public void setMath_total_errors(float math_total_errors) 
	{
		this.math_total_errors = math_total_errors;
	}
	public float getMath_speed_errors() 
	{
		return math_speed_errors;
	}
	public void setMath_speed_errors(float math_speed_errors) 
	{
		this.math_speed_errors = math_speed_errors;
	}
	public float getMath_accuracy_errors() 
	{
		return math_accuracy_errors;
	}
	public void setMath_accuracy_errors(float math_accuracy_errors) 
	{
		this.math_accuracy_errors = math_accuracy_errors;
	}
}
