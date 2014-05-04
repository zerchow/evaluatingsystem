package com.zhou.model;
import java.io.Serializable;

public class Patient implements Serializable 
{
	private String name;
	private String gender;
	private String birth;
	private String _id;
	private String diagnose;
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getGender() 
	{
		return gender;
	}
	public void setGender(String gender) 
	{
		this.gender = gender;
	}
	public String getBirth() 
	{
		return birth;
	}
	public void setBirth(String birth) 
	{
		this.birth = birth;
	}
	public String get_id() 
	{
		return _id;
	}
	public void set_id(String _id) 
	{
		this._id = _id;
	}
	public String getDiagnose() 
	{
		return diagnose;
	}
	public void setDiagnose(String diagnose) 
	{
		this.diagnose = diagnose;
	}
}
