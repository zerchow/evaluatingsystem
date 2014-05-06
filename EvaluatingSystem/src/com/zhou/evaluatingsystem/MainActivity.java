package com.zhou.evaluatingsystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhou.model.Patient;
import com.zhou.sqlite.EvalSysDatabaseHelper;
import com.zhou.util.FinalUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	private ListView patient_manage;
	//private Button patient_info_in;
	private TextView patient_num;
	private EvalSysDatabaseHelper dbHelper;
	private String[] selector = new String[]{
			"查看患者详细信息","开始进行评估测验","查询患者评估结果","删除患者及其记录"
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		//初始化变量
		this.dbHelper = new EvalSysDatabaseHelper(this);
		//初始化控件
		/*this.patient_info_in = (Button)
				findViewById(R.id.patient_info_in);*/
		this.patient_num = (TextView)
				findViewById(R.id.patien_num);
		this.patient_manage = (ListView)
				findViewById(R.id.patient_manage);
		//为控件指定监听器
		/*this.patient_info_in.setOnClickListener(
		new OnClickListener() 
		{
			public void onClick(View v) 
			{
				Intent intent = new Intent(
						MainActivity.this,
						PatientInfoActivity.class);
				startActivityForResult(intent, 
						FinalUtil.ADD_REQUEST_CODE);
			}
		});*/
		//为控件指定适配器
		this.patient_manage.setAdapter(this.getAdapter());
		this.patient_manage.setOnItemClickListener(
				new PatientInfoItemClickListener());
	}
	public void addPatient(View view)
	{
		Intent intent = new Intent(this,
				PatientInfoActivity.class);
		startActivityForResult(intent, 
				FinalUtil.ADD_REQUEST_CODE);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) 
	{
		// TODO 自动生成的方法存根
		//super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == FinalUtil.ADD_REQUEST_CODE &&
				resultCode == FinalUtil.ADD_RESULT_CODE)
		{
			Bundle bundle = intent.getExtras();
			Patient patient = (Patient)bundle.getSerializable(
					"new_patient");
			if(this.dbHelper.insertPatient(patient))
			{
				//this.patient_manage.setAdapter(this.getAdapter());
				Cursor cursor = this.dbHelper.queryAllPatient();
				this.patient_num.setText(cursor.getCount() + "");
				((SimpleCursorAdapter)patient_manage.getAdapter())
				.changeCursor(cursor);
			}
			else
			{
				Toast.makeText(this,FinalUtil.ALREADY_EXIST,
						Toast.LENGTH_LONG).show();
			}
		}
	}
	@Override
	protected void onResume() 
	{
		Cursor cursor = this.dbHelper.queryAllPatient();
		this.patient_num.setText(cursor.getCount() + "");
		((SimpleCursorAdapter)patient_manage.getAdapter())
		.changeCursor(cursor);
		// TODO 自动生成的方法存根
		super.onResume();
	}
	private SimpleCursorAdapter getAdapter()
	{
		Cursor cursor = this.dbHelper.queryAllPatient();
		this.patient_num.setText(cursor.getCount() + "");
		return new SimpleCursorAdapter(
				this,R.layout.patientinfo_cell,
				cursor,
				new String[]{
					"_id","name","gender","diagnose"
				},
				new int[]{
					R.id.patientinfo_cell_id,
					R.id.patientinfo_cell_name,
					R.id.patientinfo_cell_gender,
					R.id.patientinfo_cell_diagnose
				},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	}
	public Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) 
		{
		}
	};
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		//关闭数据库
		if(this.dbHelper != null)
			this.dbHelper.close();
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
	//
	private class PatientInfoItemClickListener implements OnItemClickListener 
	{
		private int selected = -1;
		@Override
		public void onItemClick(AdapterView<?> parent, 
				View view, int position, long id) 
		{
			final String hospital_id = 
					((TextView)view.findViewById(R.id.patientinfo_cell_id))
					.getText().toString();
			AlertDialog.Builder builder =
					new AlertDialog.Builder(MainActivity.this)
			.setTitle("请选择操作")
			.setItems(selector,
			new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog,int pos)
				{
					if(pos == 0)
					{
						patientDetail(hospital_id);
					}
				    else if(pos == 1)
					{
						startEvaluation(hospital_id);
					}
					else if(pos == 2)
					{
						checkResult(hospital_id);
					}
					else if(pos == 3)
					{
						deletePatient(hospital_id);
					}
				}
			});
			builder.create().show();
		}
	}
	private void patientDetail(String id)
	{
		Intent intent = new Intent(this, PatientDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("id", id);
		intent.putExtras(bundle);
		this.startActivity(intent);
	}
	//
	private void deletePatient(final String id)
	{
		FinalUtil.getDialog(this,"确定要删除吗？",true)
		.setMessage("删除患者信息以及评估记录！")
		.setPositiveButton("是",
				new DialogInterface.OnClickListener() 
		{		
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				if(dbHelper.deletePatient(id))
				{
					//patient_manage.setAdapter(getAdapter());
					Cursor cursor = dbHelper.queryAllPatient();
					patient_num.setText(cursor.getCount() + "");
					((SimpleCursorAdapter)patient_manage.getAdapter())
					.changeCursor(cursor);
					Toast.makeText(MainActivity.this,
							FinalUtil.DELETE_SUCCESS,
							Toast.LENGTH_LONG).show();
				}
				else
				{
					Toast.makeText(MainActivity.this,
							FinalUtil.DELETE_FAIL,
							Toast.LENGTH_LONG).show();
				}
			}
		}).create().show();
	}
	//
	private void checkResult(String hospital_id)
	{
		Intent intent = new Intent(this,
				CheckResultActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("id", hospital_id);
		intent.putExtras(bundle);
		this.startActivity(intent);
	}
	//
	private void startEvaluation(final String id)
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
							MainActivity.this,EvaluationActivity.class);
					Bundle datas = new Bundle();
					datas.putString("doctorname", doctor_name_et.getText().toString());
					datas.putString("id", id);
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
	//
	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		// TODO 自动生成的方法存根
		super.onConfigurationChanged(newConfig);
	}
}