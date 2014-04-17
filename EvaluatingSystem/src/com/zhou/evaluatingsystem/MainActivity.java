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

import com.zhou.dao.Patient;
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
	private Button patient_info_in;
	private EvalSysDatabaseHelper dbHelper;
	private String[] selector = new String[]{
			"开始评估","查询评估结果","删除"
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
		this.patient_info_in = (Button)
				findViewById(R.id.patient_info_in);
		this.patient_manage = (ListView)
				findViewById(R.id.patient_manage);
		//为控件指定监听器
		this.patient_info_in.setOnClickListener(
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
		});
		//为控件指定适配器
		this.patient_manage.setAdapter(this.getAdapter());
		this.patient_manage.setOnItemClickListener(
				new PatientInfoItemClickListener());
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
				((SimpleCursorAdapter)patient_manage.getAdapter())
				.changeCursor(dbHelper.queryAllPatient());
			}
			else
			{
				Toast.makeText(this,FinalUtil.ALREADY_EXIST,
						Toast.LENGTH_LONG).show();
			}
		}
	}
	private SimpleCursorAdapter getAdapter()
	{
		return new SimpleCursorAdapter(
				this,R.layout.patientinfo_cell,
				this.dbHelper.queryAllPatient(),
				new String[]{
					"hospital_id","name","gender","diagnose"
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
			switch(msg.what)
			{
			case FinalUtil.BACKUPING:
				break;
			case FinalUtil.BACKUPERROR:
				break;
			case FinalUtil.BACKUPDONE:
				break;
			}
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
	public void onBackPressed() 
	{
		// TODO 自动生成的方法存根
		FinalUtil.getDialog(this, "结束", true)
		.setMessage("确定要退出程序吗？")
		.setPositiveButton("是",
		new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				finish();
			}
		}).create().show();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if(item.getItemId() == android.R.id.home)
		{
			FinalUtil.getDialog(this, "结束", true)
			.setMessage("确定要退出程序吗？")
			.setPositiveButton("是",
			new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					finish();
				}
			}).create().show();
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
						startEvaluation(hospital_id);
					}
					else if(pos == 1)
					{
						checkResult(hospital_id);
					}
					else if(pos == 2)
					{
						deletePatient(hospital_id);
					}
				}
			});
			builder.create().show();
		}
	}
	//
	private void deletePatient(final String id)
	{
		FinalUtil.getDialog(this,"确定要删除吗？",true)
		.setPositiveButton("是",
				new DialogInterface.OnClickListener() 
		{		
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				if(dbHelper.deletePatient(id))
				{
					//patient_manage.setAdapter(getAdapter());
					((SimpleCursorAdapter)patient_manage.getAdapter())
					.changeCursor(dbHelper.queryAllPatient());
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
	//
	private class SocketThread extends Thread
	{
		private final String IP = "";
		private final int PORT = 0;
		private final int BUFFER_LEN = 1024;
		private byte[] buffer = new byte[this.BUFFER_LEN];
		public void run()
		{
			Socket socket = null;
			OutputStream out = null;
			FileInputStream in = null;
			try
			{
				socket = new Socket(this.IP, this.PORT);
				out = socket.getOutputStream();
				in = new FileInputStream(new File(
						"/data/data/com.zhou.evaluatingsystem/databases/evalsys.db3"));
				int length = 0;
				while((length = in.read(this.buffer,0,this.BUFFER_LEN))
						> 0)
				{
					out.write(buffer);
				}
				handler.sendEmptyMessage(FinalUtil.BACKUPDONE);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				handler.sendEmptyMessage(FinalUtil.BACKUPERROR);
			}
			finally
			{
				try 
				{
					if(socket != null)
					{
						out.flush();
						out.close();
						in.close();
						socket.close();
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}//end of run
	}//end of class
}