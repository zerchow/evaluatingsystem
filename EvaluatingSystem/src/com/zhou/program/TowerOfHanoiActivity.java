/**
 * 
 */
package com.zhou.program;

import java.util.Calendar;

import com.zhou.dao.Hanoi;
import com.zhou.evaluatingsystem.R;
import com.zhou.util.FinalUtil;
import com.zhou.view.TowerOfHanoiView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

/**
 * @author ZHOU
 *
 */
public class TowerOfHanoiActivity extends Activity 
{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private TowerOfHanoiView mainHanoi;
	private TowerOfHanoiView scanonlyHanoi;
	private TextSwitcher move_tv;
	private TextSwitcher targetmove_tv;
	private TextSwitcher problem_tv;
	private TextSwitcher time_tv;
	private Hanoi hanoi = new Hanoi();
	//
	private Calendar startTime = null;
	private Calendar endTime = null;
	private Calendar pracStartTime = null;
	private Calendar pracEndTime = null;
	private Calendar testStartTime = null;
	private Calendar testEndTime = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.towerofhanoi_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		//初始化
		this.mainHanoi = (TowerOfHanoiView)findViewById(R.id.mainHanoi);
		this.scanonlyHanoi = (TowerOfHanoiView)findViewById(R.id.scanonlyHanoi);
		
		this.move_tv = (TextSwitcher)findViewById(R.id.move_tv);
		this.targetmove_tv = (TextSwitcher)findViewById(R.id.targetmove_tv);
		this.problem_tv = (TextSwitcher)findViewById(R.id.problem_tv);
		this.time_tv = (TextSwitcher)findViewById(R.id.time_tv);
		//设置ViewFactory
		ViewSwitcher.ViewFactory factory = 
				new ViewSwitcher.ViewFactory() 
		{
			public View makeView() 
			{
				TextView view = new TextView(TowerOfHanoiActivity.this);
				ViewGroup.LayoutParams params = 
						new ViewGroup.LayoutParams(
								ViewGroup.LayoutParams.MATCH_PARENT,
								ViewGroup.LayoutParams.WRAP_CONTENT);
				view.setGravity(Gravity.CENTER_HORIZONTAL);
				return view;
			}
		};
		this.move_tv.setFactory(factory);
		this.targetmove_tv.setFactory(factory);
		this.problem_tv.setFactory(factory);
		this.time_tv.setFactory(factory);
		
		if(savedInstanceState == null)
		{
			this.scanonlyHanoi.initScan();
			//
			this.startTime = Calendar.getInstance();
			//
			//欢迎界面
			FinalUtil.getDialog(this, "欢迎", false)
			.setView(getLayoutInflater().inflate(R.layout.hanoi_intro_1, null))
			.setPositiveButton("确定",
			new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					FinalUtil.getDialog(TowerOfHanoiActivity.this, "欢迎", false)
					.setView(getLayoutInflater().inflate(R.layout.hanoi_intro_2, null))
					.setPositiveButton("确定",
					new DialogInterface.OnClickListener() 
					{
						public void onClick(DialogInterface dialog, int which) 
						{
							hanoi.setEvaluate_date(FinalUtil.getCurrentDateString());
							hanoi.setEvaluate_starttime(FinalUtil.getCurrentTimeString());
						}
					}).create().show();
				}
			}).create().show();
		}
	}
	//按钮事件，表示重新开始
	public void reset(View view)
	{
		this.mainHanoi.init();
	}
	//按钮事件，表示下一个
	public void next(View view)
	{
		this.mainHanoi.initNext();
		this.scanonlyHanoi.scanNext();
	}
	//消息处理类
	public Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) 
		{
			super.handleMessage(msg);
			switch(msg.what)
			{
			case FinalUtil.FIRSTMSG:
				if(pracStartTime == null)
					pracStartTime = Calendar.getInstance();
				else if(testStartTime == null)
					testStartTime = Calendar.getInstance();
				break;
			case FinalUtil.MOVEMSG:
				//更新当前步数
				int moves = msg.getData().getInt("moves");
				move_tv.setText("当前步数：" + moves);
				break;
			case FinalUtil.TIMEMSG:
				//更新所做次数
				int times = msg.getData().getInt("times");
				time_tv.setText("关卡所做次数：" + times);
				break;
			case FinalUtil.PROMSG:
				//更新关卡
				int problem = msg.getData().getInt("problem");
				problem_tv.setText("关卡：" + problem);
				break;
			case FinalUtil.TARGETMSG:
				//更新目标步数
				int target = msg.getData().getInt("target");
				targetmove_tv.setText("目标步数：" + target);
				break;
			case FinalUtil.ENDMSG:
				//游戏已结束
				//totalmillisecond,totalstarttime,totalendtime,date
				//score,starttime1,endtime1,starttime2,endtime2,millisecond1,millisecond2
				testEndTime = endTime = Calendar.getInstance();
				//
				hanoi.setEvaluate_endtime(FinalUtil.getCurrentTimeString());
				int totalSocre = msg.getData().getInt("end");
				hanoi.setScore(totalSocre);
				FinalUtil.getDialog(TowerOfHanoiActivity.this, "结束", false)
				.setMessage("你的分数为：" + totalSocre)
				.setPositiveButton("确定",
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						Intent intent = getIntent();
						Bundle bundle = new Bundle();
						bundle.putSerializable("hanoi", hanoi);
						intent.putExtras(bundle);
						setResult(FinalUtil.HANOI_RESULT_CODE,intent);
						finish();
					}
				}).create().show();
				break;
			case FinalUtil.TIMETOOMUCHMSG:
				//次数超过3，进行下一关
				FinalUtil.getDialog(TowerOfHanoiActivity.this, "结束", false)
				.setMessage("关卡进行超过3次，进行下一关")
				.setPositiveButton("确定",
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						scanonlyHanoi.scanNext();
					}
				}).create().show();
				break;
			case FinalUtil.SUCCESSMSG:
				//成功过关，步数符合要求
				if(msg.getData().getInt("problem") == 12)
				{
					mainHanoi.initNext();
					scanonlyHanoi.scanNext();
				}
				else
				{
					FinalUtil.getDialog(TowerOfHanoiActivity.this, "结束", false)
					.setMessage("成功过关，进行下一关")
					.setPositiveButton("确定",
					new DialogInterface.OnClickListener() 
					{
						public void onClick(DialogInterface dialog, int which) 
						{
							mainHanoi.initNext();
							scanonlyHanoi.scanNext();
						}
					}).create().show();
				}
				break;
			case FinalUtil.FAILMSG:
				//过关，但是步数太多，重来，累计次数
				FinalUtil.getDialog(TowerOfHanoiActivity.this, "结束", false)
				.setMessage("步数太多，重新来过")
				.setPositiveButton("确定",
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						mainHanoi.init();
					}
				}).create().show();
				break;
			case FinalUtil.FORMALTESTMSG:
				//实践已结束，正式测试开始
				pracEndTime = Calendar.getInstance();
				View view = getLayoutInflater().inflate(
						R.layout.hanoi_intro_2,null);
				((TextView)
						view.findViewById(R.id.hanoi_intro_2_tv))
						.setText(R.string.hanoi_intro_5);
				FinalUtil.getDialog(TowerOfHanoiActivity.this, "欢迎", false)
				.setView(view)
				.setPositiveButton("确定",
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
					}
				}).create().show();
				break;
			}

		}
	};
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
	protected void onRestoreInstanceState(Bundle savedInstanceState) 
	{
		// TODO 自动生成的方法存根
		this.mainHanoi.resume(
				savedInstanceState.getBundle("main_bundle"));
		this.scanonlyHanoi.resume(
				savedInstanceState.getBundle("scan_bundle"));
		super.onRestoreInstanceState(savedInstanceState);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		// TODO 自动生成的方法存根
		Bundle main_bundle = this.mainHanoi.save();
		Bundle scan_bundle = this.scanonlyHanoi.save();
		outState.putBundle("main_bundle", main_bundle);
		outState.putBundle("scan_bundle", scan_bundle);
		super.onSaveInstanceState(outState);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		// TODO 自动生成的方法存根
		super.onConfigurationChanged(newConfig);
	}
}
