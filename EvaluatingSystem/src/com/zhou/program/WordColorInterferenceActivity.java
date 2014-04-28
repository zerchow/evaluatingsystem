/**
 * 
 */
package com.zhou.program;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.zhou.dao.Word;
import com.zhou.evaluatingsystem.R;
import com.zhou.util.FinalUtil;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author ZHOU
 *
 */
public class WordColorInterferenceActivity extends Activity 
{
	private TextView interference_tv;
	private Button choose1_tv;
	private Button choose2_tv;
	private Button choose3_tv;
	private Button choose4_tv;
	private LinearLayout choose_ll;
	private String[] chooses;
	private List<String> choose_list;
	private final int[] colors = new int[]
	{
		Color.RED,
		Color.GREEN,
		Color.BLUE,
		Color.YELLOW
	};
	private final String help1 = "欢迎来到字色干扰实验\n"
			+ "你的任务是确定是否出现在屏幕上的字是黄色、绿色、红色或蓝色。\n"
			+ "选择这个词的颜色(不是这个词的含义)。\n"
			+ "你将有25试验。\n";
	private final String help2 = "测试结束，谢谢你的配合";
	//最大次数
	private final int MAXTIME = 25;
	//当前次数
	private int currentTime = 0;
	//线程控制
	private boolean stop;
	//测验控制
	private boolean kill = false;
	//当前颜色
	private int currentColor;
	//当前线程
	private Interference currentThread;
	//
	private long startTime;
	private long endTime;
	private int correctNums;
	private int wrongNums;
	//用户是否可以操作
	private boolean isUserCanDo;
	//
	private Word word = new Word();
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.wordcolorinterference_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		//初始化
		this.interference_tv = (TextView)
				findViewById(R.id.interference_tv);
		this.choose1_tv = (Button)
				findViewById(R.id.choose1_tv);
		this.choose2_tv = (Button)
				findViewById(R.id.choose2_tv);
		this.choose3_tv = (Button)
				findViewById(R.id.choose3_tv);
		this.choose4_tv = (Button)
				findViewById(R.id.choose4_tv);
		this.chooses = this.getResources()
				.getStringArray(R.array.interference);
		this.choose_list = new ArrayList<String>();
		for(int i = 0; i < this.chooses.length; i ++)
		{
			this.choose_list.add(this.chooses[i]);
		}
		this.choose_ll = (LinearLayout)findViewById(
				R.id.choose_ll);
		this.stop = false;
		this.startTime = System.currentTimeMillis();
		this.correctNums = this.wrongNums = 0;
		this.isUserCanDo = false;
		//
		FinalUtil.getDialog(this, "欢迎", false)
		.setMessage(this.help1)
		.setPositiveButton("确定",
		new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				word.setEvaluate_date(FinalUtil.getCurrentDateString());
				word.setEvaluate_starttime(FinalUtil.getCurrentTimeString());
				start();
			}
		}).create().show();
	}
	//开始逻辑
	private void start()
	{
		this.handler.sendEmptyMessage(FinalUtil.INTERFERENCESTART);
	}
	//打乱
	private void shuffle()
	{
		Collections.shuffle(this.choose_list,
				new Random(System.currentTimeMillis()));
	}
	//随机获取
	private String getRandomString()
	{
		Random random = new Random(System.currentTimeMillis());
		return this.choose_list.get(
				random.nextInt(this.choose_list.size()));
	}
	private int getRandomColor()
	{
		Random random = new Random(System.currentTimeMillis());
		int tempColor;
		do
		{
			tempColor = this.colors[random.nextInt(
					this.colors.length)];
		}
		while(tempColor == this.currentColor);
		return tempColor;
	}
	//用户选择
	public void choose(View view)
	{
		if(this.isUserCanDo)
		{
			String text = ((TextView)view).getText().toString();
			if(getColor(text) == this.currentColor)
				this.handler.sendEmptyMessage(
						FinalUtil.INTERFERENCECORRECT);
			else
				this.handler.sendEmptyMessage(
						FinalUtil.INTERFERENCEWRONG);
			this.stop = true;
			this.isUserCanDo = false;
		}
	}
	//判断颜色
	private int getColor(String string)
	{
		if(string.equals("红色"))
			return Color.RED;
		else if(string.equals("绿色"))
			return Color.GREEN;
		else if(string.equals("蓝色"))
			return Color.BLUE;
		else
			return Color.YELLOW;
	}
	//任务线程
	class Interference extends Thread
	{
		public void run() 
		{
			try 
			{
				Thread.sleep(500);
			} 
			catch (InterruptedException e) 
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			isUserCanDo = true;
			handler.sendEmptyMessage(FinalUtil.INTERFERENCESTIMULUS);
			while(!stop)
			{}
			WordColorInterferenceActivity.this.start();
		}
	}
	//消息处理类
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg) 
		{
			if(kill)
				return;
			switch(msg.what)
			{
			case FinalUtil.INTERFERENCESTART:
				currentTime ++;
				stop = false;
				while(currentThread != null &&
						currentThread.isAlive())
				{
					
				}
				currentThread = new Interference();
				currentThread.start();
				break;
			case FinalUtil.INTERFERENCESTIMULUS:
				shuffle();
				String string = getRandomString();
				currentColor = getRandomColor();
				interference_tv.setText(string);
				interference_tv.setTextColor(currentColor);
				for(int i = 0; i < choose_ll.getChildCount(); i ++)
				{
					((TextView)choose_ll.getChildAt(i))
					.setText(choose_list.get(i));
				}
				break;
			case FinalUtil.INTERFERENCECORRECT:
				correctNums ++;
				Toast.makeText(WordColorInterferenceActivity.this,
						"正确",Toast.LENGTH_SHORT).show();
				if(currentTime == MAXTIME)
				{
					stop = true;
					endTime = System.currentTimeMillis();
					String tip = "用时：" + (endTime - startTime) + 
							"ms\n正确：" + correctNums + 
							"\n错误：" + wrongNums;
					FinalUtil.getDialog(WordColorInterferenceActivity.this,
							"结束",false)
					.setMessage(tip)
					.setPositiveButton("确定",
					new DialogInterface.OnClickListener() 
					{
						public void onClick(DialogInterface dialog, int which) 
						{
							stopTheGame();
						}
					}).create().show();
				}
				break;
			case FinalUtil.INTERFERENCEWRONG:
				wrongNums ++;
				Toast.makeText(WordColorInterferenceActivity.this,
						"错误",Toast.LENGTH_SHORT).show();
				if(currentTime == MAXTIME)
				{
					stop = true;
					endTime = System.currentTimeMillis();
					String tip = "用时：" + (endTime - startTime) + 
							"ms\n正确：" + correctNums + 
							"\n错误：" + wrongNums;
					FinalUtil.getDialog(WordColorInterferenceActivity.this,
							"结束",false)
					.setMessage(tip)
					.setPositiveButton("确定",
					new DialogInterface.OnClickListener() 
					{
						public void onClick(DialogInterface dialog, int which) 
						{
							stopTheGame();
						}
					}).create().show();
				}
				break;
			}
			super.handleMessage(msg);
		}
	};
	private void stopTheGame()
	{
		this.word.setEvaluate_endtime(FinalUtil.getCurrentTimeString());
		this.word.setCorrect(correctNums);
		this.word.setError(wrongNums);
		Intent intent = this.getIntent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("word", this.word);
		intent.putExtras(bundle);
		this.setResult(FinalUtil.WORD_RESULT_CODE, intent);
		this.finish();
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
	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		// TODO 自动生成的方法存根
		super.onConfigurationChanged(newConfig);
	}
}