package com.zhou.model;

import java.io.Serializable;

public class Visual implements Serializable 
{
	private String hospital_id;
	private String doctor_name;
	private String evaluate_date;
	private String evaluate_starttime;
	private String evaluate_endtime;
	private long evaluate_millisecond;
	private String pracf_starttime;
	private String pracf_endtimd;
	private long pracf_millisecond;
	private String testf_starttime;
	private String testf_endtime;
	private long testf_millisecond;
	private String pracb_starttime;
	private String pracb_endtime;
	private long pracb_millisecond;
	private String testb_starttime;
	private String testb_endtime;
	private long testb_millisecond;
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
	public long getEvaluate_millisecond() 
	{
		return evaluate_millisecond;
	}
	public void setEvaluate_millisecond(long evaluate_millisecond) 
	{
		this.evaluate_millisecond = evaluate_millisecond;
	}
	public String getPracf_starttime() 
	{
		return pracf_starttime;
	}
	public void setPracf_starttime(String pracf_starttime) 
	{
		this.pracf_starttime = pracf_starttime;
	}
	public String getPracf_endtimd() 
	{
		return pracf_endtimd;
	}
	public void setPracf_endtimd(String pracf_endtimd) 
	{
		this.pracf_endtimd = pracf_endtimd;
	}
	public long getPracf_millisecond() 
	{
		return pracf_millisecond;
	}
	public void setPracf_millisecond(long pracf_millisecond) 
	{
		this.pracf_millisecond = pracf_millisecond;
	}
	public String getTestf_starttime() 
	{
		return testf_starttime;
	}
	public void setTestf_starttime(String testf_starttime) 
	{
		this.testf_starttime = testf_starttime;
	}
	public String getTestf_endtime() 
	{
		return testf_endtime;
	}
	public void setTestf_endtime(String testf_endtime) 
	{
		this.testf_endtime = testf_endtime;
	}
	public long getTestf_millisecond() 
	{
		return testf_millisecond;
	}
	public void setTestf_millisecond(long testf_millisecond) 
	{
		this.testf_millisecond = testf_millisecond;
	}
	public String getPracb_starttime() 
	{
		return pracb_starttime;
	}
	public void setPracb_starttime(String pracb_starttime) 
	{
		this.pracb_starttime = pracb_starttime;
	}
	public String getPracb_endtime() 
	{
		return pracb_endtime;
	}
	public void setPracb_endtime(String pracb_endtime) 
	{
		this.pracb_endtime = pracb_endtime;
	}
	public long getPracb_millisecond() 
	{
		return pracb_millisecond;
	}
	public void setPracb_millisecond(long pracb_millisecond) 
	{
		this.pracb_millisecond = pracb_millisecond;
	}
	public String getTestb_starttime() 
	{
		return testb_starttime;
	}
	public void setTestb_starttime(String testb_starttime) 
	{
		this.testb_starttime = testb_starttime;
	}
	public String getTestb_endtime() 
	{
		return testb_endtime;
	}
	public void setTestb_endtime(String testb_endtime) 
	{
		this.testb_endtime = testb_endtime;
	}
	public long getTestb_millisecond() 
	{
		return testb_millisecond;
	}
	public void setTestb_millisecond(long testb_millisecond) 
	{
		this.testb_millisecond = testb_millisecond;
	}
}
