/**
 * 
 */
package com.zhou.program;

import java.io.IOException;
import java.util.Calendar;

import com.zhou.evaluatingsystem.R;
import com.zhou.logic.DigitSpan;
import com.zhou.model.Acoustic;
import com.zhou.util.FinalUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Audio.Media;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author ZHOU
 *
 */
public class DigitSpanAcousticActivity extends Activity 
{
	private String help1 = 
			"下面的是一个有关数字广度的测试 "
			+ "\n此数字广度是正确回忆听到的一系列数字个数。 "
			+ "\n此数字广度包括顺式与倒序两个部分。"
			+ "\n举例:"
			+ "\n呈现出的数字是：1 7 2 4 9"
			+ "\n顺背: 	17249"
			+ "\n倒背:	94271"
			+ "\n为了让你熟悉这个测试，我们有一个实践练习部分。";
	private String help2 = 
			"下面介绍是让你熟悉顺式数字广度评估的方法："
			+ "\n(1) 首先你会在屏幕的中心看到一个红色圆点。"
			+ "\n(2) 红色圆点之后会听到一串从1-9的数字。"
			+ "\n(3) 当数字结束后又会出现一个红色圆点。"
			+ "\n(4) 接着屏幕中心会出现一个文本框："
			+ "\n请输入你听到的数字序列的顺序, 例如：你听到的是（3、6、2），请在文本框中输入362。"
			+ "\n实践部分不计入总分，准备好后请点击继续。";
	private String help3 = 
			"这只是个练习，正式测试即将开始。";
	private String help4 = 
			"\n顺式试验即将开始。";
	private String help5 = 
			"\n顺背试验即将开始。";
	private String help6 = 
			"你已经完成了顺序部分数字广度。";
	private String help7 = 
			"下面介绍是让你熟悉倒序数字广度评估的方法："
			+ "\n(1) 首先你会在屏幕的中心看到一个红色圆点。"
			+ "\n(2) 红色圆点之后会听到一串从1-9的数字。"
			+ "\n(3) 当数字结束后又会出现一个红色圆点。"
			+ "\n(4) 接着屏幕中心会出现一个文本框："
			+ "\n请输入你听到的数字序列的倒序, 例如：你听到的是（3、6、2），请在文本框中输入263。"
			+ "\n实践部分不计入总分，准备好后请点击继续。";
	private String help8 = 
			"你已经完成了倒序部分数字广度。";
	private String help9 = 
			"谢谢你的参与";
	private TextView digitshow_tv;
	private DigitSpan digitSpan;
	private String digitSequence;
	private int currentType;
	private DsvshowThread dsvshowThread;
	private final int[] ACOUSTICRESOURCE = 
		{
			R.raw.one,R.raw.two,R.raw.three,
			R.raw.four,R.raw.five,R.raw.six,
			R.raw.seven,R.raw.eight,R.raw.nine
		};
	//
	private boolean kill = false;
	//
	private Acoustic acoustic = new Acoustic();
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
	//
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.digitspan_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		//初始化
		this.digitshow_tv = (TextView)findViewById(R.id.digitshow_tv);
		this.digitSpan = new DigitSpan(this);
		//
		this.startTime = Calendar.getInstance();
		//
		FinalUtil.getDialog(this, "欢迎", false)
		.setMessage(this.help1)
		.setPositiveButton("确定",
		new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				FinalUtil.getDialog(DigitSpanAcousticActivity.this, "欢迎", false)
				.setMessage(help2)
				.setPositiveButton("确定",
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						start();
					}
				}).create().show();
			}
		}).create().show();
	}
	//开始获得下一个字串
	private void start()
	{
		this.handler.sendEmptyMessage(FinalUtil.DS_START);
	}
	//显示用户输入对话框
	private void showUserInput()
	{
		String tip = "";
		if(this.currentType == FinalUtil.DS_FORWARD)
		{
			tip = "\n请在此输入你听到的数字序列的顺序\n";
		}
		else if(this.currentType == FinalUtil.DS_BACKWARD)
		{
			tip = "\n请在此输入你听到的数字序列的倒序\n";
		}
		View view = this.getLayoutInflater().inflate(
				R.layout.digitspansolver_layout,null);
		((TextView)view.findViewById(R.id.dsv_response_tv))
		.setText(tip);
		final EditText et = (EditText)view.findViewById(R.id.dsv_response_et);
		et.setFilters(new InputFilter[]{
				new InputFilter.LengthFilter(
						this.digitSequence.length())
		});
		AlertDialog.Builder dialog = 
				FinalUtil.getDialog(this, "输入", false);
		dialog.setView(view);
		final AlertDialog showDialog = dialog.create();
		Button bt = (Button)view.findViewById(R.id.dsv_submit_bt);
		bt.setOnClickListener(new OnClickListener()
		{
			public void onClick(View view)
			{
				if(TextUtils.isEmpty(et.getText()))
				{
					return;
				}
				else
				{
					showDialog.dismiss();
					digitSpan.submitUserInput(
							et.getText().toString());
				}
			}
		});
		showDialog.show();
	}
	//练习结束，开始试验
	private void startTest()
	{
		String tip = "";
		if(this.currentType == FinalUtil.DS_FORWARD)
		{
			this.endTime1 = Calendar.getInstance();
			tip = this.help3 + this.help4;
		}
		else if(this.currentType == FinalUtil.DS_BACKWARD)
		{
			this.endTime3 = Calendar.getInstance();
			tip = this.help3 + this.help5;
		}
		FinalUtil.getDialog(this, "欢迎", false)
		.setMessage(tip)
		.setPositiveButton("确定",
		new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				start();
			}
		}).create().show();
	}
	//消息处理类
	public Handler handler = new Handler()
	{
		public void handleMessage(Message msg) 
		{
			if(kill)
				return;
			super.handleMessage(msg);
			Bundle bundle = msg.getData();
			switch(msg.what)
			{
			case FinalUtil.DS_SHOWTIP:
				digitshow_tv.setTextColor(Color.RED);
				digitshow_tv.setText("●");
				break;
			case FinalUtil.DS_SHOWDIGIT:
				digitshow_tv.setTextColor(Color.BLACK);
				digitshow_tv.setText(bundle.getString("show"));
				break;
			case FinalUtil.DS_START:
				digitSpan.getSequence();
				break;
			case FinalUtil.DS_FORWARDPRAC:
				if(startTime1 == null)
					startTime1 = Calendar.getInstance();
				currentType = bundle.getInt("type");
				digitSequence = bundle.getString("sequence");
				dsvshowThread = new DsvshowThread(digitSequence);
				dsvshowThread.start();
				break;
			case FinalUtil.DS_BACKWARDPRAC:
				if(startTime3 == null)
					startTime3 = Calendar.getInstance();
				currentType = bundle.getInt("type");
				digitSequence = bundle.getString("sequence");
				dsvshowThread = new DsvshowThread(digitSequence);
				dsvshowThread.start();
				break;
			case FinalUtil.DS_FORWARDTEST:
				if(startTime2 == null)
					startTime2 = Calendar.getInstance();
				currentType = bundle.getInt("type");
				digitSequence = bundle.getString("sequence");
				dsvshowThread = new DsvshowThread(digitSequence);
				dsvshowThread.start();
				break;
			case FinalUtil.DS_BACKWARDTEST:
				if(startTime4 == null)
					startTime4 = Calendar.getInstance();
				currentType = bundle.getInt("type");
				digitSequence = bundle.getString("sequence");
				dsvshowThread = new DsvshowThread(digitSequence);
				dsvshowThread.start();
				break;
			case FinalUtil.DS_USERINPUT:
				showUserInput();
				break;
			case FinalUtil.DS_CORRECT:
				Toast.makeText(DigitSpanAcousticActivity.this,
						"你的答案是正确的。",
						Toast.LENGTH_LONG).show();
				startTest();
				break;
			case FinalUtil.DS_ERROR:
				Toast.makeText(DigitSpanAcousticActivity.this,
						"你的答案是错误的。",
						Toast.LENGTH_LONG).show();
				startTest();
				break;
			case FinalUtil.DS_FORWARDEND:
				endTime2 = Calendar.getInstance();
				int fteml = bundle.getInt("fteml");
				int fml = bundle.getInt("fml");
				FinalUtil.getDialog(DigitSpanAcousticActivity.this,
						"欢迎",false)
				.setMessage(help6
						+ "\n顺式数字广度评估结果:"
						+ "\n回忆正确的个数:" + fml
						+ "\n如果两个连续序列都错误时，测试结束以正确回忆最多的数为最终成绩:" + fteml)
				.setPositiveButton("确定",
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						FinalUtil.getDialog(DigitSpanAcousticActivity.this,
								"欢迎",false)
						.setMessage(help7)
						.setPositiveButton("确定",
						new DialogInterface.OnClickListener() 
						{
							public void onClick(DialogInterface dialog, int which) 
							{
								start();
							}
						}).create().show();
					}
				}).create().show();
				break;
			case FinalUtil.DS_BACKWARDEND:
				endTime = endTime4 = Calendar.getInstance();
				int bteml = bundle.getInt("bteml");
				int bml = bundle.getInt("bml");
				FinalUtil.getDialog(DigitSpanAcousticActivity.this,
						help9,false)
				.setMessage(help8
						+ "\n倒序数字广度评估结果:"
						+ "\n回忆正确的个数:" + bml
						+ "\n如果两个连续序列都错误时，测试结束以正确回忆最多的数为最终成绩:" + bteml)
				.setPositiveButton("确定",
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						showResult();
					}
				}).create().show();
				break;
			}
		}
	};
	//
	private void showResult()
	{
		float[] result = this.digitSpan.getResult();
		/*String show = "forward: " + result[0] + "," + result[1] + ","
				+ result[2] + "," + result[3] + "\nbackward: "
				+ result[4] + "," + result[5] + "," + result[6] + ","
				+ result[7];
		FinalUtil.getDialog(this, "结果", false)
		.setMessage(show)
		.setPositiveButton("确定",
		new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				finish();
			}
		}).create().show();*/
		//totalmillisecond,totalstarttime,totalendtime,date
		//starttime1,endtime1,starttime2,endtime2,
		//starttime3,endtime3,starttime4,endtime4,
		//millisecond1,millisecond2,millisecond3,millisecond4
		this.acoustic.setEvaluate_date(FinalUtil.getCurrentDateString(startTime));
		this.acoustic.setEvaluate_starttime(FinalUtil.getCurrentTimeString(startTime));
		this.acoustic.setEvaluate_endtime(FinalUtil.getCurrentTimeString(endTime));
		this.acoustic.setEvaluate_millisecond(FinalUtil.getTimeDiff(startTime, endTime));
		this.acoustic.setPracf_starttime(FinalUtil.getCurrentTimeString(startTime1));
		this.acoustic.setPracf_endtimd(FinalUtil.getCurrentTimeString(endTime1));
		this.acoustic.setPracf_millisecond(FinalUtil.getTimeDiff(startTime1, endTime1));
		this.acoustic.setTestf_starttime(FinalUtil.getCurrentTimeString(startTime2));
		this.acoustic.setTestf_endtime(FinalUtil.getCurrentTimeString(endTime2));
		this.acoustic.setTestf_millisecond(FinalUtil.getTimeDiff(startTime2, endTime2));
		this.acoustic.setPracb_starttime(FinalUtil.getCurrentTimeString(startTime3));
		this.acoustic.setPracb_endtime(FinalUtil.getCurrentTimeString(endTime3));
		this.acoustic.setPracb_millisecond(FinalUtil.getTimeDiff(startTime3, endTime3));
		this.acoustic.setTestb_starttime(FinalUtil.getCurrentTimeString(startTime4));
		this.acoustic.setTestb_endtime(FinalUtil.getCurrentTimeString(endTime4));
		this.acoustic.setTestb_millisecond(FinalUtil.getTimeDiff(startTime4, endTime4));
		//
		this.acoustic.setForward_TE_ML(result[0]);
		this.acoustic.setForward_TE_TT(result[1]);
		this.acoustic.setForward_ML(result[2]);
		this.acoustic.setForward_MS(result[3]);
		this.acoustic.setBackward_TE_ML(result[4]);
		this.acoustic.setBackward_TE_TT(result[5]);
		this.acoustic.setBackward_ML(result[6]);
		this.acoustic.setBackward_MS(result[7]);
		Intent intent = this.getIntent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("acoustic", this.acoustic);
		intent.putExtras(bundle);
		this.setResult(FinalUtil.DSV_RESULT_CODE, intent);
		this.finish();
	}
	//
	@Override
	public void onBackPressed() 
	{
		this.kill = true;
		this.finish();
		super.onBackPressed();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if(item.getItemId() == android.R.id.home)
		{
			this.kill = true;
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onDestroy() 
	{
		// TODO 自动生成的方法存根
		this.kill = true;
		super.onDestroy();
	}
	//
	private MediaPlayer getMP(int r_id)
	{
		final MediaPlayer player = MediaPlayer.create(this, r_id);
		//播放完成释放资源
		player.setOnCompletionListener(
		new OnCompletionListener() 
		{
			public void onCompletion(MediaPlayer mp) 
			{
				player.release();
			}
		});
		//播放错误释放资源
		player.setOnErrorListener(
		new OnErrorListener() 
		{
			public boolean onError(MediaPlayer mp, int what, int extra) 
			{
				player.release();
				return false;
			}
		});
		try 
		{
			player.prepare();
		} 
		catch (IllegalStateException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return player;
	}
	private class DsvshowThread extends Thread
	{
		private String toShow;
		public DsvshowThread(String toShow)
		{
			this.toShow = toShow;
		}
		public void run()
		{
			if(kill)
				return;
			try
			{
				handler.sendEmptyMessage(FinalUtil.DS_SHOWTIP);
				for(int i = 0; i < this.toShow.length() &&
						! kill; i ++)
					
				{
					String temp = this.toShow.substring(i, i + 1);
					Thread.sleep(1100);
					/*Message msg = new Message();
					msg.what = FinalUtil.DS_SHOWDIGIT;
					Bundle bundle = new Bundle();
					bundle.putString("show", temp);
					msg.setData(bundle);
					handler.sendMessage(msg);*/
					getMP(ACOUSTICRESOURCE
							[Integer.valueOf(temp) - 1]).start();
				}
				if(kill)
					return;
				Thread.sleep(1100);
				handler.sendEmptyMessage(FinalUtil.DS_SHOWTIP);
				Thread.sleep(1100);
				handler.sendEmptyMessage(FinalUtil.DS_USERINPUT);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
