package com.zhou.model;

import java.io.Serializable;

public class Aos implements Serializable 
{
	private String hospital_id;
	private String doctor_name;
	private String evaluate_date;
	private String evaluate_starttime;
	private String evaluate_endtime;
	private long evaluate_millisecond;
	private String pracletter_starttime;
	private String pracletter_endtime;
	private long pracletter_millisecond;
	private String pracmath_starttime;
	private String pracmath_endtime;
	private long pracmath_millisecond;
	private String pracboth_starttime;
	private String pracboth_endtime;
	private long pracboth_millisecond;
	private String testboth_starttime;
	private String testboth_endtime;
	private long testboth_millisecond;
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
	public long getEvaluate_millisecond() 
	{
		return evaluate_millisecond;
	}
	public void setEvaluate_millisecond(long evaluate_millisecond) 
	{
		this.evaluate_millisecond = evaluate_millisecond;
	}
	public String getPracletter_starttime() 
	{
		return pracletter_starttime;
	}
	public void setPracletter_starttime(String pracletter_starttime) 
	{
		this.pracletter_starttime = pracletter_starttime;
	}
	public String getPracletter_endtime() 
	{
		return pracletter_endtime;
	}
	public void setPracletter_endtime(String pracletter_endtime) 
	{
		this.pracletter_endtime = pracletter_endtime;
	}
	public long getPracletter_millisecond() 
	{
		return pracletter_millisecond;
	}
	public void setPracletter_millisecond(long pracletter_millisecond) 
	{
		this.pracletter_millisecond = pracletter_millisecond;
	}
	public String getPracmath_starttime() 
	{
		return pracmath_starttime;
	}
	public void setPracmath_starttime(String pracmath_starttime) 
	{
		this.pracmath_starttime = pracmath_starttime;
	}
	public String getPracmath_endtime() 
	{
		return pracmath_endtime;
	}
	public void setPracmath_endtime(String pracmath_endtime) 
	{
		this.pracmath_endtime = pracmath_endtime;
	}
	public long getPracmath_millisecond() 
	{
		return pracmath_millisecond;
	}
	public void setPracmath_millisecond(long pracmath_millisecond) 
	{
		this.pracmath_millisecond = pracmath_millisecond;
	}
	public String getPracboth_starttime() 
	{
		return pracboth_starttime;
	}
	public void setPracboth_starttime(String pracboth_starttime) 
	{
		this.pracboth_starttime = pracboth_starttime;
	}
	public String getPracboth_endtime() 
	{
		return pracboth_endtime;
	}
	public void setPracboth_endtime(String pracboth_endtime) 
	{
		this.pracboth_endtime = pracboth_endtime;
	}
	public long getPracboth_millisecond() 
	{
		return pracboth_millisecond;
	}
	public void setPracboth_millisecond(long pracboth_millisecond) 
	{
		this.pracboth_millisecond = pracboth_millisecond;
	}
	public String getTestboth_starttime() 
	{
		return testboth_starttime;
	}
	public void setTestboth_starttime(String testboth_starttime) 
	{
		this.testboth_starttime = testboth_starttime;
	}
	public String getTestboth_endtime() 
	{
		return testboth_endtime;
	}
	public void setTestboth_endtime(String testboth_endtime) 
	{
		this.testboth_endtime = testboth_endtime;
	}
	public long getTestboth_millisecond() 
	{
		return testboth_millisecond;
	}
	public void setTestboth_millisecond(long testboth_millisecond) 
	{
		this.testboth_millisecond = testboth_millisecond;
	}
}
