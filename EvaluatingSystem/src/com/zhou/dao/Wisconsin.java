package com.zhou.dao;

import java.io.Serializable;

public class Wisconsin implements Serializable 
{
	private String hospital_id;
	private String doctor_name;
	private String evaluate_date;
	private String evaluate_starttime;
	private String evaluate_endtime;
	private String color1;
	private String form1;
	private String number1;
	private String color2;
	private String form2;
	private String number2;
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
	public String getColor1() 
	{
		return color1;
	}
	public void setColor1(String color1) 
	{
		this.color1 = color1;
	}
	public String getForm1() 
	{
		return form1;
	}
	public void setForm1(String form1) 
	{
		this.form1 = form1;
	}
	public String getNumber1() 
	{
		return number1;
	}
	public void setNumber1(String number1) 
	{
		this.number1 = number1;
	}
	public String getColor2() 
	{
		return color2;
	}
	public void setColor2(String color2) 
	{
		this.color2 = color2;
	}
	public String getForm2() 
	{
		return form2;
	}
	public void setForm2(String form2) 
	{
		this.form2 = form2;
	}
	public String getNumber2() 
	{
		return number2;
	}
	public void setNumber2(String number2) 
	{
		this.number2 = number2;
	}
}
