/**
 * 
 */
package com.zhou.program;


import java.util.Calendar;

import com.zhou.evaluatingsystem.R;
import com.zhou.model.Line;
import com.zhou.util.FinalUtil;
import com.zhou.view.LineContactView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author ZHOU
 *
 */
public class LineContactActivity extends Activity 
{
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private TextView help_tv;
	private TextView feedback_tv;
	private LineContactView mainLinecontact;
	private int simpleAErrorTime = 0;
	private int simpleBErrorTime = 0;
	private int currentGame = FinalUtil.SIMPLEA;
	private final String help_1 =
			"任务说明\n"
			+ "在屏幕上的这些数字。用手指点击，从数字1开始连线，先是1连到2，2连到3，3连到4,以此类推。按顺序，直到连线结束";
	private final String help_2 = 
			"任务说明"
			+ "在这个屏幕上有一些数字和字母。 用手指点击， 从数字1开始连线，由1 连到A,接着 A连到 2, 2 连到B, B 连到3, 3 连到C, 以此类推。按顺序，直到连线结束。记住，首先是数字然后连字母，接着再连数字然后连接字母，以此类推。";
	private Line line = new Line();
	//
	private Calendar startTime = null;
	private Calendar endTime = null;
	private Calendar aStartTime = null;
	private Calendar aEndTime = null;
	private Calendar bStartTime = null;
	private Calendar bEndTime = null;
	//
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		// TODO 自动生成的方法存根
		this.setContentView(R.layout.linecontact_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		this.help_tv = (TextView)findViewById(R.id.help_tv);
		this.help_tv.setText(this.help_1);
		this.feedback_tv = (TextView)
				findViewById(R.id.feedback_tv);
		this.mainLinecontact = (LineContactView)
				findViewById(R.id.mainLinecontact);
		//
		this.startTime = Calendar.getInstance();
		//
		this.line.setEvaluate_date(FinalUtil.getCurrentDateString());
		this.line.setEvaluate_starttime(FinalUtil.getCurrentTimeString());
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) 
	{
		// TODO 自动生成的方法存根
		this.mainLinecontact.resume(
				savedInstanceState.getBundle("main_bundle"));
		this.help_tv.setText(
				savedInstanceState.getString("help"));
		this.feedback_tv.setText(
				savedInstanceState.getString("feedback"));
		this.currentGame = 
				savedInstanceState.getInt("currentGame");
		super.onRestoreInstanceState(savedInstanceState);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		// TODO 自动生成的方法存根
		Bundle main_bundle = this.mainLinecontact.save();
		outState.putBundle("main_bundle", main_bundle);
		outState.putString("help", this.help_tv.getText().toString());
		outState.putString("feedback", this.feedback_tv.getText().toString());
		outState.putInt("currentGame", this.currentGame);
		super.onSaveInstanceState(outState);
	}
	//
	private void dealTime()
	{
		if(this.aStartTime == null &&
				this.currentGame == FinalUtil.SIMPLEA)
			this.aStartTime = Calendar.getInstance();
		else if(this.bStartTime == null &&
				this.currentGame == FinalUtil.SIMPLEB)
			this.bStartTime = Calendar.getInstance();
	}
	//消息处理类
	public Handler handler = new Handler()
	{
		public void handleMessage(Message msg) 
		{
			switch(msg.what)
			{
			case FinalUtil.STARTWRONGMSG:
				dealTime();
				feedback_tv.setText("请从起点开始点击");
				Toast.makeText(LineContactActivity.this,
						"请从起点开始点击",Toast.LENGTH_LONG).show();
				break;
			case FinalUtil.LINERIGHTMSG:
				dealTime();
				feedback_tv.setText("");
				break;
			case FinalUtil.LINEWRONGMSG:
				dealTime();
				int next = msg.getData().getInt("next");
				String jump = "";
				if(currentGame == FinalUtil.SIMPLEA)
				{
					jump += (next + 1);
				}
				else if(currentGame == FinalUtil.SIMPLEB)
				{
					if(next % 2 == 0)
						jump += (next / 2) + 1;
					else if(next % 2 == 1)
						jump += (char)(next / 2 + 'A');
				}
				feedback_tv.setText("跳过了" + jump);
				Toast.makeText(LineContactActivity.this,
						"跳过了" + jump,Toast.LENGTH_LONG).show();
				break;
			case FinalUtil.LINECOMLETEDMSG:
				//totalmillisecond,totalstarttime,totalendtime,date
				//starttime1,endtime1,starttime2,endtime2,millisecond1,millisecond2
				if(aEndTime == null &&
						currentGame == FinalUtil.SIMPLEA)
					aEndTime = Calendar.getInstance();
				else if(bEndTime == null &&
						currentGame == FinalUtil.SIMPLEB)
					endTime = bEndTime = Calendar.getInstance();
				//
				final int errorTimes = msg.getData().getInt(
						"errorTimes");
				String feedback = "很好，你完成了这组测验" +
						(errorTimes == 0 ? "" :
							"\n错了" + errorTimes + "次");
				feedback_tv.setText(feedback);
				FinalUtil.getDialog(LineContactActivity.this,
						feedback,false)
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						if(currentGame == FinalUtil.SIMPLEA)
						{
							mainLinecontact.setGame(FinalUtil.SIMPLEB);
							currentGame = FinalUtil.SIMPLEB;
							help_tv.setText(help_2);
							feedback_tv.setText("");
							simpleAErrorTime = errorTimes;
						}
						else
						{
							simpleBErrorTime = errorTimes;
							line.setEvaluate_endtime(FinalUtil.getCurrentTimeString());
							line.setSimple_a_error(simpleAErrorTime);
							line.setSimple_b_error(simpleBErrorTime);
							Intent intent = getIntent();
							Bundle bundle = new Bundle();
							bundle.putSerializable("line", line);
							intent.putExtras(bundle);
							setResult(FinalUtil.LINE_RESULT_CODE, intent);
							finish();
						}
					}
				}).create().show();
				break;
			}
			super.handleMessage(msg);
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
	public void onConfigurationChanged(Configuration newConfig) 
	{
		// TODO 自动生成的方法存根
		super.onConfigurationChanged(newConfig);
	}
}
