/**
 * 
 */
package com.zhou.program;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.zhou.dao.Aos;
import com.zhou.evaluatingsystem.R;
import com.zhou.logic.Aospan;
import com.zhou.util.FinalUtil;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author ZHOU
 *
 */
public class AospanActivity extends Activity 
{
	//开始前的等待时间
	private int PreLetterInterval = 2200;
	//字母学习时间
	private final int LetterInterval = 1000;
	//数学学习时间
	private final int MathInterval = 5000;
	//
	private final String help1 = 
			"在这个实验中你将在屏幕上看到几个字母，请你记住它们的顺序。\n"
			+ "并且还有对你而言比较简单的数学题。\n"
			+ "在接下来的几分钟，会有一些练习让你熟悉如何做这个实验。\n"
			+ "我们将字母实验开始练习。";
	private final String help2 = 
			"这个实验的做法是：请记住屏幕上出现的字母的顺序。\n"
			+ "2-3个字母出现后，你就会看到一个由12个字母组成的字母表。\n"
			+ "你要做的就是从这12个字母表中找出你记得的字母的顺序。\n"
			+ "请点击你所选择的字母，\n"
			+ "你选择的字母会出现在屏幕下方。";
	private final String help3 = 
			"当你选择了你认为正确的字母顺序，\n"
			+ "请点击 确定按钮，\n"
			+ "如果你选择了错误的字母，请点击 清除 键。\n"
			+ "如果你忘记了字母的顺序，请点击 标记 键以标记你所忘记的字母。\n"
			+ " 记住，你所看到的字母顺序是很重要，它将关乎你这次实验的成绩。\n"
			+ "你果你忘记了顺序请按 标记 键。\n"
			+ "你还有什么不明白的地方吗？\n"
			+ "如果你准备好了，请继续。";
	private final String help4 = 
			"接下来你将做的是数学实验部分。\n"
			+ "有一道数学题会出现在屏幕上，例如:\n"
			+ "(2 * 1) + 1 = ?\n"
			+ "看到这个题，你能尽快的计算出答案。\n"
			+ "没错，上面这道题的答案就是3。\n"
			+ "如果你计算的答案也是3，请点击鼠标。\n"
			+ "请继续。";
	private final String help5 = 
			"在下一个屏幕上你将看到 正确 的按钮和 错误 的按钮\n"
			+ "如果给出的答案是上一题的正确答案，请点击 正确 按钮。\n"
			+ "如果给出的答案是错误的，请点击 错误 按钮。\n"
			+ "举个例子，当你看到这个数学题\n"
			+ "(2 * 2) + 1 = ?\n"
			+ "如果给出的答案是5，\n"
			+ "点击 正确 按钮，5是这道题目的正确答案。\n"
			+ "如果你看的答案是6，\n"
			+ "请点击 错误 按钮，因为这道题的答案是5不是6.\n"
			+ "电脑会告诉你你的答案是否正确。\n"
			+ "请继续。";
	private final String help6 = 
			"从现在起你要开始同时完成两个部分的内容，\n"
			+ "在接下来的实验中你将要完成数学题的部分。\n"
			+ "当你完成数学题的部分,屏幕上就会出现字母的信号，尝试记住这些字母的顺序。\n"
			+ "在你做的数学部分，我们的电脑有一个平均的计算时间。\n"
			+ "如果你的计算时间超过我们的平均时间，\n"
			+ "电脑会自动跳到下一个，\n"
			+ "因此跳过的部分我们会按错误计算。尽可能快的完成这些题目，\n"
			+ "请继续。";
	private final String help7 = 
			"当一个字母信号消失后，就会出现下一道数学题。\n"
			+ "数学题之后又会出现字母信号。\n"
			+ "当数学题与字母信号都完成后，\n"
			+ "屏幕会出现一个字母表，\n"
			+ "尽你最大的努力选择出正确的字母顺序。 尽可能快而准确的完成数学题。\n"
			+ "当你确定了你的数学题答案，请点击继续。\n"
			+ "电脑不会告诉你你的数学题答案是否正确。\n"
			+ "一系列试题完成后，电脑会给出你所有答案的正确率。\n"
			+ "你还有什么问题吗？请继续。";
	private final String help8 = 
			"这只是一个简单的实践题，"
			+ "正式的题目跟实践题是类似的。"
			+ "首先你要先完成一道数学题，然后再记住之后出现的字母顺序。"
			+ "当你看到字母表示，请点击选择你要的字母。"
			+ "当你忘记了字母的顺序，请点击标记键记录你所忘记的字母。"
			+ "接下来会有很多序列的数学题和字母信号，请尽你所能的完成他们。"
			+ "你还有什么不明白的吗？请开始测试。";
	private final String help9 = 
			"谢谢你的配合！";
	//
	private TextView prac_test_tv;
	private TextView aospantip_tv;
	private LinearLayout judge_ll;
	//
	private int autoNum = 0;
	private int totalNum;
	//
	private String userSelect;
	private String targetLetter;
	private String tempTargetLetter;
	//
	private boolean isFirstPracMath = true;
	private String targetMath;
	private String targetMath_ans;
	private boolean targetMath_judge;
	private int targetCorrect = 0;
	private int targetTotal = 0;
	//
	private boolean isFirstPracBoth = true;
	private ArrayList<String> targetMathList;
	private ArrayList<String> targetMath_ansList;
	private ArrayList<Boolean> targetMath_judgeList;
	private boolean isSolving;
	private boolean isUserCanSolving;
	//
	private boolean isFirstTestBoth = true;
	//
	private Aospan aospan;
	//
	private GetDataThread getDataThread;
	//
	private int msgType;
	//
	private int OSPAN_ABSOLUTE_SCORE = 0;
	private int OSPAN_TOTAL_CORRECT = 0;
	private int MATH_TOTAL_ERRORS = 0;
	private int MATH_SPEED_ERRORS = 0;
	private int MATH_ACCURACY_ERRORS = 0;
	//
	private boolean kill = false;
	//
	private Aos aos = new Aos();
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.aospan_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		//初始化
		this.prac_test_tv = (TextView)findViewById(R.id.prac_test_tv);
		this.judge_ll = (LinearLayout)findViewById(R.id.judge_ll);
		this.aospantip_tv = (TextView)findViewById(R.id.aospantip_tv);
		this.userSelect = null;
		this.aospan = new Aospan(this);
		//
		this.getDataThread = new GetDataThread();
		this.getDataThread.start();
 		//对话框
		FinalUtil.getDialog(this, "欢迎", false)
		.setMessage(this.help1)
		.setPositiveButton("确定",
		new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which)
			{
				FinalUtil.getDialog(AospanActivity.this,
						"欢迎",false)
				.setMessage(help2)
				.setPositiveButton("确定",
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						FinalUtil.getDialog(AospanActivity.this,
								"欢迎",false)
						.setMessage(help3)
						.setPositiveButton("点击开始实践部分",
						new DialogInterface.OnClickListener() 
						{
							public void onClick(DialogInterface dialog, int which) 
							{
								aos.setEvaluate_date(FinalUtil.getCurrentDateString());
								aos.setEvaluate_starttime(FinalUtil.getCurrentTimeString());
								start();
							}
						}).create().show();
					}
				}).create().show();
			}
		}).create().show();
	}
	//开始
	private void start()
	{
		while(this.getDataThread.isAlive())
		{}
		this.handler.sendEmptyMessage(FinalUtil.AOSPANSTARTMSG);
	}
	//启动用户选择
	private void startUserSelect()
	{
		Intent intent = new Intent(
				this,AospanSolverActivity.class);
		this.startActivityForResult(intent,
				FinalUtil.AOSPAN_REQUEST);
	}
	//
	private class GetDataThread extends Thread
	{
		public void run()
		{
			AospanActivity.this.aospan.init();
		}
	}
	//
	private void setTip()
	{
		String tip = this.autoNum + " / " + this.totalNum;
		this.aospantip_tv.setText(tip);
	}
	//
	private void processMathMsg(Bundle bundle)
	{
		totalNum = bundle.getInt("total");
		targetMath = bundle.getString("pracmath");
		targetMath_ans = bundle.getString("pracmath_ans");
		targetMath_judge = bundle.getBoolean("pracmath_judge");
		isUserCanSolving = true;
		if(isFirstPracMath)
		{
			autoNum = 1;
			targetTotal = 0;
			targetCorrect = 0;
			FinalUtil.getDialog(AospanActivity.this,
					"欢迎",false)
			.setMessage(help4)
			.setPositiveButton("确定",
			new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					FinalUtil.getDialog(AospanActivity.this,
							"欢迎",false)
					.setMessage(help5)
					.setPositiveButton("确定",
					new DialogInterface.OnClickListener() 
					{
						public void onClick(DialogInterface dialog, int which) 
						{
							setTip();
							showMath();
						}
					}).create().show();
				}
			}).create().show();
			isFirstPracMath = false;
			targetTotal ++;
		}
		else
		{
			autoNum ++;
			setTip();
			showMath();
			targetTotal ++;
		}
	}
	private void processPracBothMsg(Bundle bundle)
	{
		totalNum = bundle.getInt("total");
		targetLetter = bundle.getString("pracbothletter");
		tempTargetLetter = new String(targetLetter);
		targetMathList = bundle.getStringArrayList("pracbothmath");
		targetMath_ansList = bundle.getStringArrayList("pracbothmath_ans");
		targetMath_judgeList = (ArrayList<Boolean>) bundle.getSerializable("pracbothmath_judge");
		if(isFirstPracBoth)
		{
			autoNum = 1;
			FinalUtil.getDialog(AospanActivity.this,
					"结果",false)
			.setMessage("你所做的数学实验" + targetTotal
					+ "个" + targetCorrect + "个是正确的。\n"
					+ "你的正确率是"
					+ (int)((float)targetCorrect / targetTotal * 100)
					+ "%")
			.setPositiveButton("确定",
			new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					FinalUtil.getDialog(AospanActivity.this,
							"欢迎",false)
					.setMessage(help6)
					.setPositiveButton("确定",
					new DialogInterface.OnClickListener() 
					{
						public void onClick(DialogInterface dialog, int which) 
						{
							FinalUtil.getDialog(AospanActivity.this,
									"欢迎",false)
							.setMessage(help7)
							.setPositiveButton("确定",
							new DialogInterface.OnClickListener() 
							{
								public void onClick(DialogInterface dialog, int which) 
								{
									showPracBoth();
									setTip();
								}
							}).create().show();
						}
					}).create().show();
				}
			}).create().show();
			targetTotal = 0;
			targetCorrect = 0;
			isFirstPracBoth = false;
		}
		else
		{
			autoNum ++;
			setTip();
			targetTotal = 0;
			targetCorrect = 0;
			showPracBoth();
		}
	}
	private void processTestBothMsg(Bundle bundle)
	{
		totalNum = bundle.getInt("total");
		targetLetter = bundle.getString("testbothletter");
		tempTargetLetter = new String(targetLetter);
		targetMathList = bundle.getStringArrayList("testbothmath");
		targetMath_ansList = bundle.getStringArrayList("testbothmath_ans");
		targetMath_judgeList = (ArrayList<Boolean>) bundle.getSerializable("testbothmath_judge");
		if(isFirstTestBoth)
		{
			autoNum = 1;
			FinalUtil.getDialog(AospanActivity.this,
					"欢迎",false)
			.setMessage(help8)
			.setPositiveButton("确定",
			new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					setTip();
					showPracBoth();
				}
			}).create().show();
			targetTotal = 0;
			targetCorrect = 0;
			isFirstTestBoth = false;
		}
		else
		{
			autoNum ++;
			setTip();
			targetTotal = 0;
			targetCorrect = 0;
			showPracBoth();
		}
	}
	//消息处理类
	public Handler handler = new Handler()
	{
		public void handleMessage(Message msg) 
		{
			super.handleMessage(msg);
			if(kill)
			{
				return;
			}
			Bundle bundle = msg.getData();
			switch(msg.what)
			{
			case FinalUtil.PRACLETTERMSG:
				totalNum = bundle.getInt("total");
				autoNum ++;
				setTip();
				msgType = FinalUtil.PRACLETTERMSG;
				targetLetter = bundle.getString("pracletter");
				new ShowThread().start();
				break;
			case FinalUtil.PRACMATHMSG:
				msgType = FinalUtil.PRACMATHMSG;
				processMathMsg(bundle);
				break;
			case FinalUtil.PRACBOTHMSG:
				msgType = FinalUtil.PRACBOTHMSG;
				processPracBothMsg(bundle);
				break;
			case FinalUtil.TESTBOTHMSG:
				msgType = FinalUtil.TESTBOTHMSG;
				processTestBothMsg(bundle);
				break;
			case FinalUtil.AOSPANSETTEXT:
				showLetter(
						bundle.getString("text"));
				break;
			case FinalUtil.AOSPANSETMATH:
				showMath();
				break;
			case FinalUtil.AOSPANUSERSELECT:
				startUserSelect();
				break;
			case FinalUtil.AOSPANSETNEXTLETTER:
				showNextLetter();
				break;
			case FinalUtil.AOSPANSETNEXTMATH:
				showPracBoth();
				break;
			case FinalUtil.AOSPANSTARTMSG:
				if(aospan.hasNextItem())
				{
					aospan.getNextItem();
				}
				else
				{
					String end = "OSPAN Absolute Score: " + OSPAN_ABSOLUTE_SCORE + 
							"\nOSPAN Total Correct: " + OSPAN_TOTAL_CORRECT + 
							"\nMath Total Errors: " + MATH_TOTAL_ERRORS + 
							"\nMath Speed Errors: " + MATH_SPEED_ERRORS + 
							"\nMath Accuracy Errors: " + MATH_ACCURACY_ERRORS;
					FinalUtil.getDialog(AospanActivity.this,
							help9,false)
					.setMessage(end)
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
		}
	};
	//
	private void stopTheGame()
	{
		this.aos.setEvaluate_endtime(FinalUtil.getCurrentTimeString());
		this.aos.setOspan_absoluate_score(OSPAN_ABSOLUTE_SCORE);
		this.aos.setOspan_total_correct(OSPAN_TOTAL_CORRECT);
		this.aos.setMath_accuracy_errors(MATH_ACCURACY_ERRORS);
		this.aos.setMath_speed_errors(MATH_SPEED_ERRORS);
		this.aos.setMath_total_errors(MATH_TOTAL_ERRORS);
		Intent intent = this.getIntent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("aospan", this.aos);
		intent.putExtras(bundle);
		this.setResult(FinalUtil.AOSPAN_RESULT_CODE, intent);
		this.finish();
	}
	//
	private void showMath()
	{
		this.prac_test_tv.setText(targetMath + 
				"\n" + 
				"当你完成这道题，请点击继续");
		this.prac_test_tv.setClickable(true);
	}
	//
	private void showLetter(String letter)
	{
		this.prac_test_tv.setText(letter);
		this.prac_test_tv.setClickable(false);
	}
	//
	private void showPracBoth()
	{
		this.targetMath = this.targetMathList.remove(0);
		this.targetMath_ans = this.targetMath_ansList.remove(0);
		this.targetMath_judge = this.targetMath_judgeList.remove(0);
		this.isSolving = false;
		this.targetTotal ++;
		showMath();
		new ShowThread().start();
	}
	//
	private void showNextLetter()
	{
		new ShowHelper().start();
	}
	//
	private class ShowThread extends Thread
	{
		private void processPracLetter()
		{
			try
			{
				Message msg;
				Bundle bundle;
				msg = new Message();
				msg.what = FinalUtil.AOSPANSETTEXT;
				bundle = new Bundle();
				bundle.putString("text", "");
				msg.setData(bundle);
				handler.sendMessage(msg);
				Thread.sleep(PreLetterInterval);
				for(int i = 0; i < targetLetter.length(); i ++)
				{
					msg = new Message();
					bundle = new Bundle();
					bundle.putString("text",
							targetLetter.substring(
									i, i + 1));
					msg.what = FinalUtil.AOSPANSETTEXT;
					msg.setData(bundle);
					handler.sendMessage(msg);
					Thread.sleep(LetterInterval);
				}
				handler.sendEmptyMessage(FinalUtil.AOSPANUSERSELECT);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		private void processPracBoth()
		{
			try
			{
				isUserCanSolving = true;
				for(int i = 0; i < 25; i ++)
				{
					Thread.sleep(200);
					if(isSolving)
						break;
				}
				isUserCanSolving = false;
				if(! isSolving)
				{
					MATH_SPEED_ERRORS ++;
					MATH_TOTAL_ERRORS ++;
					handler.sendEmptyMessage(FinalUtil.AOSPANSETNEXTLETTER);
				}
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		public void run()
		{
			switch(msgType)
			{
			case FinalUtil.PRACLETTERMSG:
				this.processPracLetter();
				break;
			case FinalUtil.PRACBOTHMSG:
				this.processPracBoth();
				break;
			case FinalUtil.TESTBOTHMSG:
				this.processPracBoth();
				break;
			}
		}
	}
	private class ShowHelper extends Thread
	{
		public void run()
		{
			try
			{
				Message msg;
				Bundle bundle;
				msg = new Message();
				msg.what = FinalUtil.AOSPANSETTEXT;
				bundle = new Bundle();
				bundle.putString("text", "");
				msg.setData(bundle);
				handler.sendMessage(msg);
				Thread.sleep(1200);
				
				msg = new Message();
				bundle = new Bundle();
				bundle.putString("text",
						tempTargetLetter.substring(0, 1));
				msg.what = FinalUtil.AOSPANSETTEXT;
				msg.setData(bundle);
				handler.sendMessage(msg);
				Thread.sleep(LetterInterval);
				if(tempTargetLetter.length() == 1)
				{
					handler.sendEmptyMessage(FinalUtil.AOSPANUSERSELECT);
				}
				else
				{
					tempTargetLetter = tempTargetLetter.substring(1);
					handler.sendEmptyMessage(FinalUtil.AOSPANSETNEXTMATH);
				}
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == FinalUtil.AOSPAN_REQUEST && 
				resultCode == FinalUtil.AOSPAN_RESULT)
		{
			Bundle bundle = data.getExtras();
			this.userSelect = bundle.getString("userSelect");
			this.findDiff();
		}
	}
	//处理字符串的异同
	private void findDiff()
	{
		int length = this.targetLetter.length();
		int correct = 0;
		for(int i = 0; i < length; i ++)
		{
			if(i >= userSelect.length())
				continue;
			if(this.targetLetter.charAt(i) == 
					this.userSelect.charAt(i))
			{
				correct ++;
			}
		}
		String tip = "你所做的" + length +
				"个中" + correct +
				"个是正确的顺序";
		if(this.msgType == FinalUtil.PRACBOTHMSG ||
				this.msgType == FinalUtil.TESTBOTHMSG)
		{
			tip += "\n" + "你所做的数学实验" + targetTotal
					+ "个" + targetCorrect + "个是正确的。\n"
					+ "你的正确率是" + targetCorrect / targetTotal
					+ "%";
			if(this.msgType == FinalUtil.TESTBOTHMSG)
			{
				this.OSPAN_TOTAL_CORRECT += correct;
				if(correct == length)
					this.OSPAN_ABSOLUTE_SCORE += correct;
			}
		}
		Toast.makeText(this,tip,
				Toast.LENGTH_SHORT).show();
		this.handler.sendEmptyMessage(
				FinalUtil.AOSPANSTARTMSG);
		this.targetLetter = null;
		this.userSelect = null;
	}
	//用户点击事件
	public void clickMath(View view)
	{
		if(! this.isUserCanSolving)
		{
			return;
		}
		this.isSolving = true;
		this.prac_test_tv.setText(targetMath_ans);
		this.judge_ll.setVisibility(View.VISIBLE);
		this.prac_test_tv.setClickable(false);
	}
	public void clickYes(View view)
	{
		this.judge_ll.setVisibility(View.GONE);
		if(this.targetMath_judge == true)
		{
			if(this.msgType == FinalUtil.PRACBOTHMSG)
			{
				this.showNextLetter();
			}
			else if(this.msgType == FinalUtil.TESTBOTHMSG)
			{
				this.showNextLetter();
			}
			else
			{
				this.targetCorrect ++;
				Toast.makeText(this,"正确",
								Toast.LENGTH_SHORT).show();
				this.handler.sendEmptyMessage(FinalUtil.AOSPANSTARTMSG);
			}
		}
		else
		{
			if(this.msgType == FinalUtil.PRACBOTHMSG)
			{
				this.showNextLetter();
			}
			else if(this.msgType == FinalUtil.TESTBOTHMSG)
			{
				this.MATH_ACCURACY_ERRORS ++;
				this.MATH_TOTAL_ERRORS ++;
				this.showNextLetter();
			}
			else
			{
				Toast.makeText(this,"错误",
						Toast.LENGTH_SHORT).show();
				this.handler.sendEmptyMessage(FinalUtil.AOSPANSTARTMSG);
			}
		}
	}
	public void clickNo(View view)
	{
		this.judge_ll.setVisibility(View.GONE);
		if(this.targetMath_judge == false)
		{
			if(this.msgType == FinalUtil.PRACBOTHMSG)
			{
				this.showNextLetter();
			}
			else if(this.msgType == FinalUtil.TESTBOTHMSG)
			{
				this.showNextLetter();
			}
			else
			{
				this.targetCorrect ++;
				Toast.makeText(this,"正确",
								Toast.LENGTH_SHORT).show();
				this.handler.sendEmptyMessage(FinalUtil.AOSPANSTARTMSG);
			}
		}
		else
		{
			if(this.msgType == FinalUtil.PRACBOTHMSG)
			{
				this.showNextLetter();
			}
			else if(this.msgType == FinalUtil.TESTBOTHMSG)
			{
				this.MATH_ACCURACY_ERRORS ++;
				this.MATH_TOTAL_ERRORS ++;
				this.showNextLetter();
			}
			else
			{
				Toast.makeText(this,"错误",
						Toast.LENGTH_SHORT).show();
				this.handler.sendEmptyMessage(FinalUtil.AOSPANSTARTMSG);
			}
		}
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
	@Override
	public void onBackPressed() 
	{
		this.kill = true;
		this.finish();
		super.onBackPressed();
	}
}
