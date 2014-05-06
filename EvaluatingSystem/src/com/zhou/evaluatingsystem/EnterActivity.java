/**
 * 
 */
package com.zhou.evaluatingsystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.zhou.socket.BackupSocketThread;
import com.zhou.socket.SynchronizationSocketThread;
import com.zhou.util.FinalUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author ZHOU
 *
 */
public class EnterActivity extends Activity 
{
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		// TODO 自动生成的方法存根
		setContentView(R.layout.enter_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	//
	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		// TODO 自动生成的方法存根
		super.onConfigurationChanged(newConfig);
	}
	//
	public void enterToPatient(View view)
	{
		Intent intent = new Intent(this,MainActivity.class);
		this.startActivity(intent);
	}
	private boolean hasNetwork()
	{
		if(!FinalUtil.hasNetwork(this))
		{
			FinalUtil.getQuickDialog(this, "无网络接入").create().show();
			return false;
		}
		if(!FinalUtil.isWifi(this))
		{
			FinalUtil.getQuickDialog(this, "无WiFi接入").create().show();
			return false;
		}
		return true;
	}
	public void backupAndSynchronization(View v)
	{
		if(! this.hasNetwork())
			return;
		//
		final int id = v.getId();
		//
		View view = getLayoutInflater().inflate(
				R.layout.ip_layout,null);
		final EditText ip_name_et = (EditText)
				view.findViewById(R.id.ip_name_et);
		ip_name_et.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		ip_name_et.addTextChangedListener
		(new TextWatcher() 
		{
			int beforeChange;
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) 
			{
				this.beforeChange = s.length();
			}
			@Override
			public void afterTextChanged(Editable s) 
			{
				String text = s.toString();
				int length = text.length();
				//允许删除
				if(this.beforeChange - length == 1)
				{
					return;
				}
				//过滤字符
				if("1234567890.".indexOf(
						text.substring(length - 1,length)) < 0)
				{
					ip_name_et.setText(text.substring(0, length - 1));
					ip_name_et.setSelection(length - 1);
					return;
				}
				String[] result = text.split("\\.");
				if(length == 0)
				{
					//空字符无需判断
					return;
				}
				else if(length == 1)
				{
					if(text.equals("."))
					{
						//第一个字符不为点
						ip_name_et.setText("");
						return;
					}
				}
				else
				{
					//不允许连续输入两个点
					//总共不超过3个点
					if(text.charAt(length - 1) == '.')
					{
						if(text.charAt(length - 2) == '.'
								||result.length == 4)
						{
							ip_name_et.setText(text.substring(0, length - 1));
							ip_name_et.setSelection(length - 1);
							return;
						}
					}
				}
				//不允许0开头
				if(Integer.valueOf(result[result.length - 1]) == 0)
				{
					if(result.length == 1)
					{
						ip_name_et.setText("");
						return;
					}
					else
					{
						int lastDot = text.lastIndexOf(".");
						if(length - 1 - lastDot == 2)
						{
							ip_name_et.setText(text.substring(0,
									length - 1));
							ip_name_et.setSelection(length - 1);
							return;
						}
						else if(length - 1 - lastDot == 1 && result.length != 4)
						{
							ip_name_et.setText(text + ".");
							ip_name_et.setSelection(length + 1);
						}
					}
				}
				//超过255，设为最大255
				boolean shouldChange = false;
				StringBuilder builder = new StringBuilder();
				for(int i = 0; i < result.length; i ++)
				{
					int value = Integer.valueOf(result[i]);
					if(value > 255)
					{
						result[i] = "255";
						shouldChange = true;
					}
					builder.append(result[i]);
					if(i <= 2)
						builder.append(".");
				}
				if(shouldChange)
				{
					ip_name_et.setText(builder.toString());
					ip_name_et.setSelection(builder.length());
					return;
				}
				//还有缺点，可以把光标移到前面，随便添加0
			}//end of function
		});
		Button ip_submit = (Button)
				view.findViewById(R.id.ip_name_submit);
		Button ip_cancel = (Button)
				view.findViewById(R.id.ip_name_cancel);
		AlertDialog.Builder subdialog = 
				FinalUtil.getDialog(this, "请输入服务器IP地址", false);
		subdialog.setView(view);
		final AlertDialog showDialog = subdialog.create();
		ip_submit.setOnClickListener(
		new OnClickListener() 
		{
			public void onClick(View v) 
			{
				if(TextUtils.isEmpty(
						ip_name_et.getText()))
				{
					return;
				}
				else
				{
					if(id == R.id.synchronization_btn)
					{
						SynchronizationSocketThread synchronization = 
								new SynchronizationSocketThread(EnterActivity.this,
										ip_name_et.getText().toString());
						synchronization.start();
					}
					else if(id == R.id.backup_btn)
					{
						BackupSocketThread backup = new BackupSocketThread(
								EnterActivity.this,ip_name_et.getText().toString());
						backup.start();
					}
					showDialog.dismiss();
				}
			}
		});
		ip_cancel.setOnClickListener(
		new OnClickListener() 
		{
			public void onClick(View v) 
			{
				showDialog.dismiss();
			}
		});
		showDialog.show();
	}
	public void exitSystem(View view)
	{
		this.dealExit();
	}
	private void dealExit()
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
	@Override
	public void onBackPressed() 
	{
		this.dealExit();
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
	public Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) 
		{
			Bundle bundle = msg.getData();
			String str = "";
			switch(msg.what)
			{
			case FinalUtil.BACKUP_FEEDBACK:
				str = bundle.getString("backup");
				Toast.makeText(EnterActivity.this, str,
						Toast.LENGTH_LONG).show();
				break;
			case FinalUtil.SYNCHRONIZATION_FEEDBACK:
				str = bundle.getString("synchronization");
				Toast.makeText(EnterActivity.this, str,
						Toast.LENGTH_LONG).show();
				break;
			}
		}
	};
}
