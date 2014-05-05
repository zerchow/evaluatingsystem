/**
 * 
 */
package com.zhou.evaluatingsystem;

import com.zhou.sqlite.EvalSysDatabaseHelper;
import com.zhou.util.FinalUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
	//
	private String getId()
	{
		return this.detail_id.getText().toString();
	}
	//
	public void allResult(View view)
	{
	}
	//
	public void resultTable(View view)
	{
		Intent intent = new Intent(this,
				CheckResultActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("id", this.getId());
		intent.putExtras(bundle);
		this.startActivity(intent);
	}
	//
	public void modifyInfo(View view)
	{
		
	}
	//
	public void evaluateNow(View v)
	{
		View view = getLayoutInflater().inflate(
				R.layout.doctorinfo_layout,null);
		final EditText doctor_name_et = (EditText)
				view.findViewById(R.id.doctor_name_et);
		Button doctorname_submit = (Button)
				view.findViewById(R.id.doctorname_submit);
		Button doctorname_cancel = (Button)
				view.findViewById(R.id.doctorname_cancel);
		AlertDialog.Builder subdialog = 
				FinalUtil.getDialog(this, "请输入医生信息", false);
		subdialog.setView(view);
		final AlertDialog showDialog = subdialog.create();
		doctorname_submit.setOnClickListener(
		new OnClickListener() 
		{
			public void onClick(View v) 
			{
				if(TextUtils.isEmpty(
						doctor_name_et.getText()))
				{
					return;
				}
				else
				{
					Intent intent = new Intent(
							PatientDetailActivity.this,EvaluationActivity.class);
					Bundle datas = new Bundle();
					datas.putString("doctorname", doctor_name_et.getText().toString());
					datas.putString("id", getId());
					intent.putExtras(datas);
					startActivity(intent);
					showDialog.dismiss();
				}
			}
		});
		doctorname_cancel.setOnClickListener(
		new OnClickListener() 
		{
			public void onClick(View v) 
			{
				showDialog.dismiss();
			}
		});
		showDialog.show();
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
	//
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		//关闭数据库
		if(this.dbHelper != null)
			this.dbHelper.close();
	}
}
