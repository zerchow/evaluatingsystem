/**
 * 
 */
package com.zhou.evaluatingsystem;

import com.zhou.sqlite.EvalSysDatabaseHelper;
import com.zhou.util.FinalUtil;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * @author ZHOU
 *
 */
public class PatientDetailActivity extends Activity 
{
	private EvalSysDatabaseHelper dbHelper;
	
	private TextView detail_name;
	private TextView detail_id;
	private TextView detail_gender;
	private TextView detail_birth;
	private TextView detail_diagnose;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		// TODO 自动生成的方法存根
		setContentView(R.layout.patient_detail_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		//初始化
		this.dbHelper = new EvalSysDatabaseHelper(this);
		this.detail_name = (TextView)findViewById(R.id.detail_name);
		this.detail_id = (TextView)findViewById(R.id.detail_id);
		this.detail_gender = (TextView)findViewById(R.id.detail_gender);
		this.detail_birth = (TextView)findViewById(R.id.detail_birth);
		this.detail_diagnose = (TextView)findViewById(R.id.detail_diagnose);
		//
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		String id = bundle.getString("id");
		Cursor cursor = this.dbHelper.queryPatient(id);
		if(cursor.moveToFirst())
		{
			this.detail_name.setText(cursor.getString(cursor.getColumnIndex("name")));
			this.detail_id.setText(id);
			this.detail_gender.setText(cursor.getString(cursor.getColumnIndex("gender")));
			this.detail_birth.setText(cursor.getString(cursor.getColumnIndex("birth")));
			this.detail_diagnose.setText(cursor.getString(cursor.getColumnIndex("diagnose")));
		}
		cursor.close();
	}
	@Override
	public void onBackPressed() 
	{
		this.finish();
	}
	//
	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		super.onConfigurationChanged(newConfig);
	}
	//
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if(item.getItemId() == android.R.id.home)
		{
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
