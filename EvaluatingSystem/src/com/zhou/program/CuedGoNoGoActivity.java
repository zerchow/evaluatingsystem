/**
 * 
 */
package com.zhou.program;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.zhou.evaluatingsystem.R;
import com.zhou.model.Cued;
import com.zhou.util.FinalUtil;
import com.zhou.view.CuedGoNoGoView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * @author ZHOU
 *
 */
public class CuedGoNoGoActivity extends Activity 
{
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private CuedGoNoGoView cued;
	private CuedThread cuedThread;
	private boolean stop;
	private boolean kill = false;
	private boolean userTouch;
	private final int cueWhiteFixation = 800;
	private final int cueWhite = 500;
	private final int targetTimeout = 1000;
	private final int itiDuration = 700;
	private final String help1 = 
			"欢迎来到GoNoGo！\n"
			+ "每次测验开始，会有一个空心白色矩形出现在屏幕上\n"
			+ "随后，会变成绿色或者蓝色\n"
			+ "当变成绿色的时候，点击屏幕\n"
			+ "当变成蓝色的时候，不要操作";
	private final String help2 = 
			"你已经完成了卡片分类任务。\n"
			+ "谢谢你的配合！";
	private Cued cuedDao = new Cued();
	private int correct = 0;
	private int error = 0;
	//
	private Calendar startTime = null;
	private Calendar endTime = null;
	private Calendar startTime1 = null;
	private Calendar endTime1 = null;
	//
	public void setUserTouch()
	{
		this.userTouch = true;
	}
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg) 
		{
			if(kill)
				return;
			switch(msg.what)
			{
			case FinalUtil.CUEDCORRECT:
				//正确
				correct ++;
				Toast.makeText(CuedGoNoGoActivity.this,
						"正确",Toast.LENGTH_SHORT).show();
				break;
			case FinalUtil.CUEDWRONG:
				//错误
				error ++;
				Toast.makeText(CuedGoNoGoActivity.this,
						"错误",Toast.LENGTH_SHORT).show();
				break;
			case FinalUtil.CUEDWHITEMSG:
				//白屏
				cued.setWhileScreen();
				break;
			case FinalUtil.CUEDFIXATIONMSG:
				//十字架
				cued.setFixation();
				break;
			case FinalUtil.CUEDFRAMEMSG:
				//空框
				cued.setFrame();
				break;
			case FinalUtil.CUEDCOLORFRAMEMSG:
				//填充框
				cued.setColorFrame();
				break;
			case FinalUtil.CUEDSTART:
				//开始
				if(!stop && cued.hasNext())
				{
					userTouch = false;
					cuedThread = 
							new CuedThread(cued.getNext());
					cuedThread.start();
				}
				else if(!stop)
				{
					//totalmillisecond,totalstarttime,totalendtime,date
					//starttime,endtime,millisecond
					endTime = endTime1 = Calendar.getInstance();
					//
					FinalUtil.getDialog(CuedGoNoGoActivity.this,
							"结束",false)
					.setMessage(help2)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() 
					{
						public void onClick(DialogInterface dialog, int which) 
						{
							cuedDao.setEvaluate_endtime(FinalUtil.getCurrentTimeString());
							cuedDao.setCorrect(correct);
							cuedDao.setError(error);
							Intent intent = getIntent();
							Bundle bundle = new Bundle();
							bundle.putSerializable("cued", cuedDao);
							intent.putExtras(bundle);
							setResult(FinalUtil.CUED_RESULT_CODE, intent);
							finish();
						}
					}).create().show();
				}
				else
				{
					finish();
				}
				break;
			}
			super.handleMessage(msg);
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.cuedgonogo_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		//初始化
		this.cued = (CuedGoNoGoView)findViewById(R.id.cued);
		
		//
		this.startTime = Calendar.getInstance();
		//
		FinalUtil.getDialog(this, "欢迎", false)
		.setMessage(this.help1)
		.setPositiveButton("开始",
		new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				stop = false;
				cuedDao.setEvaluate_date(FinalUtil.getCurrentDateString());
				cuedDao.setEvaluate_starttime(FinalUtil.getCurrentTimeString());
				start();
			}
		}).create().show();
	}
	//开始逻辑
	private void start()
	{
		//500毫秒后
		try 
		{
			Thread.sleep(500);
			//
			this.startTime1 = Calendar.getInstance();
			//
			this.handler.sendEmptyMessage(FinalUtil.CUEDSTART);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	//新的任务线程
	class CuedThread extends Thread
	{
		private int soa;
		public CuedThread(int soa)
		{
			this.soa = soa;
		}
		public void run() 
		{
			if(stop)
				return;
			super.run();
			try 
			{
				handler.sendEmptyMessage(FinalUtil.CUEDFIXATIONMSG);
				Thread.sleep(cueWhiteFixation);
				handler.sendEmptyMessage(FinalUtil.CUEDWHITEMSG);
				Thread.sleep(cueWhite);
				handler.sendEmptyMessage(FinalUtil.CUEDFRAMEMSG);
				Thread.sleep(soa);
				handler.sendEmptyMessage(FinalUtil.CUEDCOLORFRAMEMSG);
				cued.createUser();
				for(int i = 0; i < 5; i ++)
				{
					Thread.sleep(targetTimeout / 5);
					if(userTouch)
						break;
				}
				cued.cancelUser();
				handler.sendEmptyMessage(FinalUtil.CUEDWHITEMSG);
				if(cued.result())
				{
					handler.sendEmptyMessage(FinalUtil.CUEDCORRECT);
				}
				else
				{
					handler.sendEmptyMessage(FinalUtil.CUEDWRONG);
				}
				Thread.sleep(itiDuration);
				handler.sendEmptyMessage(FinalUtil.CUEDSTART);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void onDestroy() 
	{
		// TODO 自动生成的方法存根
		this.kill = true;
		this.stop = true;
		super.onDestroy();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if(item.getItemId() == android.R.id.home)
		{
			this.kill = true;
			this.stop = true;
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onBackPressed() 
	{
		this.kill = true;
		this.stop = true;
		this.finish();
		super.onBackPressed();
	}
}
