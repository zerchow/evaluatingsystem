/**
 * 
 */
package com.zhou.program;

import java.util.Calendar;

import com.zhou.dao.Wisconsin;
import com.zhou.evaluatingsystem.R;
import com.zhou.logic.WisconsinCardSorting;
import com.zhou.util.FinalUtil;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.Toast;

/**
 * @author ZHOU
 *
 */
public class WisconsinActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private final String help1 = 
			"你即将开始卡片分类任务，\n"
			+ "屏幕上会出现一系列卡片，\n"
			+ "用手指点击你所选的卡片。";
	private final String help2 = 
			"你的任务是将上面的一张卡片正确的归类到下面四张卡片中的一张。\n"
			+ "如果你归类正确，电脑会提示你“正确”。如果你归类错误，电脑会提示你“错误”。\n"
			+ "你的目的就是要正确的将上面的卡片进行分类。";
	private final String help3 = 
			"你已经完成了卡片分类任务。\n"
			+ "谢谢你的配合！";
	private ImageView response_iv;
	private ImageView presented1_iv;
	private ImageView presented2_iv;
	private ImageView presented3_iv;
	private ImageView presented4_iv;
	@SuppressWarnings("deprecation")
	private SlidingDrawer tip;
	private WisconsinCardSorting wisconsin;
	private Wisconsin wis = new Wisconsin();
	//
	private Calendar startTime = null;
	private Calendar endTime = null;
	private Calendar startTime1 = null;
	private Calendar endTime1 = null;
	private Calendar startTime2 = null;
	private Calendar endTime2 = null;
	private Calendar startTime3 = null;
	private Calendar endTime3 = null;
	private Calendar startTime4 = null;
	private Calendar endTime4 = null;
	private Calendar startTime5 = null;
	private Calendar endTime5 = null;
	private Calendar startTime6 = null;
	private Calendar endTime6 = null;
	//
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		// TODO 自动生成的方法存根
		this.setContentView(R.layout.wisconsin_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		this.response_iv = (ImageView)findViewById(R.id.response_iv);
		this.presented1_iv = (ImageView)findViewById(R.id.presented1_iv);
		this.presented2_iv = (ImageView)findViewById(R.id.presented2_iv);
		this.presented3_iv = (ImageView)findViewById(R.id.presented3_iv);
		this.presented4_iv = (ImageView)findViewById(R.id.presented4_iv);
		this.tip = (SlidingDrawer)findViewById(R.id.tip_sd);
		this.wisconsin = new WisconsinCardSorting(this);
		
		LinearLayout tip_content_ll = (LinearLayout)
				findViewById(R.id.tip_content_ll);
		tip_content_ll.setOnTouchListener(
				new OnTouchListener() 
		{
			public boolean onTouch(View v, MotionEvent event) 
			{
				return true;
			}
		});
		if(savedInstanceState == null)
		{
			this.startTime = Calendar.getInstance();
			FinalUtil.getDialog(this, "欢迎", false)
			.setMessage(this.help1)
			.setPositiveButton("确定",
					new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					FinalUtil.getDialog(WisconsinActivity.this,
							"欢迎",false)
					.setMessage(help2)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() 
					{
						public void onClick(DialogInterface dialog, int which) 
						{
							wis.setEvaluate_date(FinalUtil.getCurrentDateString());
							wis.setEvaluate_starttime(FinalUtil.getCurrentTimeString());
							response_iv.setImageResource(
									wisconsin.init());
						}
					}).create().show();
				}
			}).create().show();
		}
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) 
	{
		// TODO 自动生成的方法存根
		this.response_iv.setImageResource(
				this.wisconsin.resume(
				savedInstanceState.getBundle("wisconsin")));
		super.onRestoreInstanceState(savedInstanceState);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		// TODO 自动生成的方法存根
		Bundle bundle = this.wisconsin.save();
		outState.putBundle("wisconsin", bundle);
		super.onSaveInstanceState(outState);
	}
	public void onPresented1(View view)
	{
		this.wisconsin.choose(1);
	}
	public void onPresented2(View view)
	{
		this.wisconsin.choose(2);
	}
	public void onPresented3(View view)
	{
		this.wisconsin.choose(3);
	}
	public void onPresented4(View view)
	{
		this.wisconsin.choose(4);
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
	public Handler handle = new Handler()
	{
		public void handleMessage(Message msg) 
		{
			Bundle bundle = msg.getData();
			switch(msg.what)
			{
			case FinalUtil.WISTOOMUCHTOTAL:
				FinalUtil.getDialog(WisconsinActivity.this,
						"结束",false)
				.setMessage(help3)
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						stopTheGame();
					}
				}).create().show();
				break;
			case FinalUtil.WISTOOMUCHCIRCUIT:
				FinalUtil.getDialog(WisconsinActivity.this,
						"结束",false)
				.setMessage(help3)
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						stopTheGame();
					}
				}).create().show();
				break;
			case FinalUtil.WISNEXT:
				response_iv.setImageResource(
						bundle.getInt("next"));
				break;
			case FinalUtil.WISCORRECT:
				Toast.makeText(WisconsinActivity.this,
						"正确",Toast.LENGTH_LONG).show();
				break;
			case FinalUtil.WISWRONG:
				Toast.makeText(WisconsinActivity.this,
						"错误",Toast.LENGTH_LONG).show();
				break;
			}
			super.handleMessage(msg);
		}
	};
	private void stopTheGame()
	{
		//totalmillisecond,totalstarttime,totalendtime,date
		//starttime1,endtime1,starttime2,endtime2,starttime3,endtime3,
		//starttime4,endtime4,starttime5,endtime5,starttime6,endtime6,
		//millisecond1,millisecond2,millisecond3,millisecond4,
		//millisecond5,millisecond6
		this.endTime = Calendar.getInstance();
		this.startTime1 = this.wisconsin.getStartTime1();
		this.endTime1 = this.wisconsin.getEndTime1();
		this.startTime2 = this.wisconsin.getStartTime2();
		this.endTime2 = this.wisconsin.getEndTime2();
		this.startTime3 = this.wisconsin.getStartTime3();
		this.endTime3 = this.wisconsin.getEndTime3();
		this.startTime4 = this.wisconsin.getStartTime4();
		this.endTime4 = this.wisconsin.getEndTime4();
		this.startTime5 = this.wisconsin.getStartTime5();
		this.endTime5 = this.wisconsin.getEndTime5();
		this.startTime6 = this.wisconsin.getStartTime6();
		this.endTime6 = this.wisconsin.getEndTime6();
		//
		String[] colors = this.wisconsin.getColors();
		String[] forms = this.wisconsin.getForms();
		String[] numbers = this.wisconsin.getNumbers();
		this.wis.setEvaluate_endtime(FinalUtil.getCurrentTimeString());
		this.wis.setColor1(colors[0]);
		this.wis.setColor2(colors[1]);
		this.wis.setForm1(forms[0]);
		this.wis.setForm2(forms[1]);
		this.wis.setNumber1(numbers[0]);
		this.wis.setNumber2(numbers[1]);
		Intent intent = this.getIntent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("wisconsin", this.wis);
		intent.putExtras(bundle);
		this.setResult(FinalUtil.WISCONSIN_RESULT_CODE, intent);
		this.finish();
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		// TODO 自动生成的方法存根
		super.onConfigurationChanged(newConfig);
	}
}
