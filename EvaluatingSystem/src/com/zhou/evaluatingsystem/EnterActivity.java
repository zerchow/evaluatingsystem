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

import com.zhou.util.FinalUtil;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
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
	public void exit_system(View view)
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
}
