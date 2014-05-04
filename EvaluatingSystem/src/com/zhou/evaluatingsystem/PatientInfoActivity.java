/**
 * 
 */
package com.zhou.evaluatingsystem;

import java.util.Calendar;

import com.zhou.model.Patient;
import com.zhou.util.FinalUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

/**
 * @author ZHOU
 *
 */
public class PatientInfoActivity extends Activity 
{
	private EditText patient_name_et;
	private EditText patient_id_et;
	private RadioButton patient_male_rb;
	private DatePicker patient_birth_dp;
	private EditText patient_diagnose_et;
	private Button patient_info_submit;
	private Patient patient = new Patient();
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.patientinfo_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		//初始化控件
		this.patient_name_et = (EditText)
				findViewById(R.id.patient_name_et);
		this.patient_id_et = (EditText)
				findViewById(R.id.patient_id_et);
		this.patient_male_rb = (RadioButton)
				findViewById(R.id.patient_male_rb);
		this.patient_birth_dp = (DatePicker)
				findViewById(R.id.patient_birth_dp);
		this.patient_diagnose_et = (EditText)
				findViewById(R.id.patient_diagnose_et);
		this.patient_info_submit = (Button)
				findViewById(R.id.patient_info_submit);
		//初始化变量
		//为控件指定监听器
		this.patient_info_submit.setOnClickListener(
		new OnClickListener() 
		{
			public void onClick(View v) 
			{
				if(!validate())
				{
					return;
				}
				Intent intent = getIntent();
				Bundle bundle = new Bundle();
				bundle.putSerializable(
						"new_patient",patient);
				intent.putExtras(bundle);
				setResult(FinalUtil.ADD_RESULT_CODE, intent);
				finish();
			}
		});
	}
	private boolean validate()
	{
		String patient_name = this.patient_name_et.getText().toString().trim();
		String patient_id = this.patient_id_et.getText().toString().trim();
		String patient_gender = this.patient_male_rb.isChecked() ? 
				FinalUtil.MALE : FinalUtil.FEMALE;
		String birth_year = String.valueOf(
				this.patient_birth_dp.getYear());
		String birth_month = String.valueOf(
				this.patient_birth_dp.getMonth() + 1);
		String birth_day = String.valueOf(
				this.patient_birth_dp.getDayOfMonth());
		String patient_diagnose = this.patient_diagnose_et.getText().toString().trim();
		if(TextUtils.isEmpty(patient_name))
		{
			Toast.makeText(this, FinalUtil.NAME_NO_NULL,
					Toast.LENGTH_LONG).show();
			return false;
		}
		if(TextUtils.isEmpty(patient_id))
		{
			Toast.makeText(this, FinalUtil.ID_NO_NULL,
					Toast.LENGTH_LONG).show();
			return false;
		}
		if(TextUtils.isEmpty(patient_diagnose))
		{
			Toast.makeText(this, FinalUtil.DIAGNOSE_NO_NULL,
					Toast.LENGTH_LONG).show();
			return false;
		}
		//验证合法时
		this.patient.setName(patient_name);
		this.patient.set_id(patient_id);
		this.patient.setGender(patient_gender);
		this.patient.setDiagnose(patient_diagnose);
		this.patient.setBirth(birth_year + "-"
				+ birth_month + "-"
				+ birth_day);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if(item.getItemId() == android.R.id.home)
		{
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		// TODO 自动生成的方法存根
		super.onConfigurationChanged(newConfig);
	}
	
}
