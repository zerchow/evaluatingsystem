package com.zhou.dao;

import java.io.Serializable;

public class Visual implements Serializable 
{
	private String hospital_id;
	private String doctor_name;
	private String evaluate_date;
	private String evaluate_starttime;
	private String evaluate_endtime;
	private float forward_TE_ML;
	private float forward_TE_TT;
	private float forward_ML;
	private float forward_MS;
	private float backward_TE_ML;
	private float backward_TE_TT;
	private float backward_ML;
	private float backward_MS;
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
	public float getForward_TE_ML() 
	{
		return forward_TE_ML;
	}
	public void setForward_TE_ML(float forward_TE_ML) 
	{
		this.forward_TE_ML = forward_TE_ML;
	}
	public float getForward_TE_TT() 
	{
		return forward_TE_TT;
	}
	public void setForward_TE_TT(float forward_TE_TT) 
	{
		this.forward_TE_TT = forward_TE_TT;
	}
	public float getForward_ML() 
	{
		return forward_ML;
	}
	public void setForward_ML(float forward_ML) 
	{
		this.forward_ML = forward_ML;
	}
	public float getForward_MS() 
	{
		return forward_MS;
	}
	public void setForward_MS(float forward_MS) 
	{
		this.forward_MS = forward_MS;
	}
	public float getBackward_TE_ML() 
	{
		return backward_TE_ML;
	}
	public void setBackward_TE_ML(float backward_TE_ML) 
	{
		this.backward_TE_ML = backward_TE_ML;
	}
	public float getBackward_TE_TT() 
	{
		return backward_TE_TT;
	}
	public void setBackward_TE_TT(float backward_TE_TT) 
	{
		this.backward_TE_TT = backward_TE_TT;
	}
	public float getBackward_ML() 
	{
		return backward_ML;
	}
	public void setBackward_ML(float backward_ML) 
	{
		this.backward_ML = backward_ML;
	}
	public float getBackward_MS() 
	{
		return backward_MS;
	}
	public void setBackward_MS(float backward_MS) 
	{
		this.backward_MS = backward_MS;
	}
}
