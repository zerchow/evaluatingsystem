package com.zhou.logic;

import java.util.Calendar;

import com.zhou.evaluatingsystem.R;
import com.zhou.program.WisconsinActivity;
import com.zhou.util.FinalUtil;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

public class WisconsinCardSorting 
{
	//测试图片数组
	private final int[] TESTPICS = new int[]
	{
		R.drawable.greentriangle1,
		R.drawable.redcross4,
		R.drawable.bluetriangle2,
		R.drawable.redcircle1,
		R.drawable.greenstar4,
		R.drawable.yellowcross1,
		R.drawable.bluetriangle4,
		R.drawable.redcircle3,
		R.drawable.greencross4,
		R.drawable.yellowcircle2
	};
	private final int[] TEST_COLORS = new int[]
	{
		Color.GREEN,Color.RED,Color.BLUE,Color.RED,Color.GREEN,
		Color.YELLOW,Color.BLUE,Color.RED,Color.GREEN,Color.YELLOW
	};
	private final int[] TEST_FORMS = new int[]
	{
		FinalUtil.WISTRIANGLE,FinalUtil.WISCROSS,
		FinalUtil.WISTRIANGLE,FinalUtil.WISCIRCLE,
		FinalUtil.WISSTAR,FinalUtil.WISCROSS,
		FinalUtil.WISTRIANGLE,FinalUtil.WISCIRCLE,
		FinalUtil.WISCROSS,FinalUtil.WISCIRCLE
	};
	private final int[] TEST_NUMS = new int[]
	{
		1,4,2,1,4,
		1,4,3,4,2
	};
	//选项基本信息
	private final int[] CHOOSE_COLORS = new int[]
	{
		Color.RED,Color.GREEN,Color.YELLOW,Color.BLUE
	};
	private final int[] CHOOSE_FORMS = new int[]
	{
		FinalUtil.WISTRIANGLE,
		FinalUtil.WISSTAR,
		FinalUtil.WISCROSS,
		FinalUtil.WISCIRCLE
	};
	//最多的次数
	private final int MAXTIMES = 128;
	//颜色、类型、数量最多各两次
	private final int EACHCURCUIT = 2;
	//连胜次数，过关条件
	private final int CORRECTSTREAKTHRESHOLD = 4;
	//当前游戏类型
	private int currentGame;
	//当前进行到的关卡
	private int currentProblem;
	//当前连胜的次数
	private int currentCorrectStreak;
	//颜色进行的次数
	private int colorTimes;
	//类型进行的次数
	private int formTimes;
	//数量进行的次数
	private int numberTimes;
	//游戏是否进行中
	private boolean isGaming;
	//总的选择数
	private int totalTimes;
	//
	private String[] colors;
	private String[] forms;
	private String[] numbers;
	//
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
	//Activity上下文
	private WisconsinActivity context;
	//构造函数
	public WisconsinCardSorting(Context context)
	{
		this.context = (WisconsinActivity)context;
	}
	//第一次运行的初始化
	public int init()
	{
		this.colors = new String[2];
		this.forms = new String[2];
		this.numbers = new String[2];
		for(int i = 0; i < 2; i ++)
		{
			this.colors[i] = new String("");
			this.forms[i] = new String("");
			this.numbers[i] = new String("");
		}
		this.currentGame = FinalUtil.WISCOLOR;
		this.currentProblem = 0;
		this.currentCorrectStreak = 0;
		this.colorTimes = 0;
		this.formTimes = 0;
		this.numberTimes = 0;
		this.isGaming = false;
		this.totalTimes = 0;
		return this.TESTPICS[this.currentProblem];
	}
	//保存
	public Bundle save()
	{
		Bundle bundle = new Bundle();
		bundle.putInt("currentGame", this.currentGame);
		bundle.putInt("currentProblem", this.currentProblem);
		bundle.putInt("currentCorrectStreak",
				this.currentCorrectStreak);
		bundle.putInt("colorTimes", this.colorTimes);
		bundle.putInt("formTimes", this.formTimes);
		bundle.putInt("numberTimes", this.numberTimes);
		bundle.putInt("totalTimes", this.totalTimes);
		bundle.putBoolean("isGaming", this.isGaming);
		return bundle;
	}
	//恢复
	public int resume(Bundle bundle)
	{
		this.currentGame = bundle.getInt("currentGame");
		this.currentProblem = bundle.getInt("currentProblem");
		this.currentCorrectStreak = bundle.getInt(
				"currentCorrectStreak");
		this.colorTimes = bundle.getInt("colorTimes");
		this.formTimes = bundle.getInt("formTimes");
		this.numberTimes = bundle.getInt("numberTimes");
		this.totalTimes = bundle.getInt("totalTimes");
		this.isGaming = bundle.getBoolean("isGaming");
		return this.TESTPICS[this.currentProblem];
	}
	//选择
	public void choose(int choose_presented)
	{
		int choose = choose_presented - 1;
		//三种游戏模式
		switch(this.currentGame)
		{
		//颜色
		case FinalUtil.WISCOLOR:
			if(! this.isGaming)
			{
				//累积颜色的次数
				this.colorTimes ++;
				this.isGaming = true;
				if(this.startTime1 == null)
					this.startTime1 = Calendar.getInstance();
				else if(this.startTime4 == null)
					this.startTime4 = Calendar.getInstance();
			}
			if(this.CHOOSE_COLORS[choose] == 
				this.TEST_COLORS[this.currentProblem])
			{
				this.colors[this.colorTimes - 1] += 1;
				//颜色选对
				this.sendEmptyMessage(FinalUtil.WISCORRECT);
				this.currentCorrectStreak ++;
				if(this.currentCorrectStreak ==
						this.CORRECTSTREAKTHRESHOLD)
				{
					//连击达到4次的话，进行类型
					this.currentGame = FinalUtil.WISFORM;
					this.currentProblem = 0;
					this.currentCorrectStreak = 0;
					this.isGaming = false;
					if(this.endTime1 == null)
						this.endTime1 = Calendar.getInstance();
					else if(this.endTime4 == null)
						this.endTime4 = Calendar.getInstance();
				}
				else
				{
					//连击还在4次以内
					this.currentProblem = (this.currentProblem
							+ 1) % this.TESTPICS.length;
				}
			}
			else 
			{
				this.colors[this.colorTimes - 1] += 0;
				//颜色选错
				this.sendEmptyMessage(FinalUtil.WISWRONG);
				this.currentCorrectStreak = 0;
				this.currentProblem = (this.currentProblem
						+ 1) % this.TESTPICS.length;
			}
			break;
		//类型
		case FinalUtil.WISFORM:
			if(! this.isGaming)
			{
				//累积类型的次数
				this.formTimes ++;
				this.isGaming = true;
				if(this.startTime2 == null)
					this.startTime2 = Calendar.getInstance();
				else if(this.startTime5 == null)
					this.startTime5 = Calendar.getInstance();
			}
			if(this.CHOOSE_FORMS[choose] == 
				this.TEST_FORMS[this.currentProblem])
			{
				this.forms[this.formTimes - 1] += 1;
				//类型选对
				this.sendEmptyMessage(FinalUtil.WISCORRECT);
				this.currentCorrectStreak ++;
				if(this.currentCorrectStreak == 
						this.CORRECTSTREAKTHRESHOLD)
				{
					//连击达到4次的话， 进行数量
					this.currentGame = FinalUtil.WISNUMBER;
					this.currentProblem = 0;
					this.currentCorrectStreak = 0;
					this.isGaming = false;
					if(this.endTime2 == null)
						this.endTime2 = Calendar.getInstance();
					else if(this.endTime5 == null)
						this.endTime5 = Calendar.getInstance();
				}
				else
				{
					//连击还在4次以内
					this.currentProblem = (this.currentProblem
							+ 1) % this.TESTPICS.length;
				}
			}
			else
			{
				this.forms[this.formTimes - 1] += 0;
				//类型选错
				this.sendEmptyMessage(FinalUtil.WISWRONG);
				this.currentCorrectStreak = 0;
				this.currentProblem = (this.currentProblem
						+ 1) % this.TESTPICS.length;
			}
			break;
		//数量
		case FinalUtil.WISNUMBER:
			if(! this.isGaming)
			{
				//累积数量的次数
				this.numberTimes ++;
				this.isGaming = true;
				if(this.startTime3 == null)
					this.startTime3 = Calendar.getInstance();
				else if(this.startTime6 == null)
					this.startTime6 = Calendar.getInstance();
			}
			if(choose_presented == 
				this.TEST_NUMS[this.currentProblem])
			{
				this.numbers[this.numberTimes - 1] += 1;
				//数量选对
				this.sendEmptyMessage(FinalUtil.WISCORRECT);
				this.currentCorrectStreak ++;
				if(this.currentCorrectStreak == 
						this.CORRECTSTREAKTHRESHOLD)
				{
					//连击达到4次的话， 进行颜色
					this.currentGame = FinalUtil.WISCOLOR;
					this.currentProblem = 0;
					this.currentCorrectStreak = 0;
					this.isGaming = false;
					if(this.endTime3 == null)
						this.endTime3 = Calendar.getInstance();
					else if(this.endTime6 == null)
						this.endTime6 = Calendar.getInstance();
				}
				else
				{
					//连击还在4次以内
					this.currentProblem = (this.currentProblem
							+ 1) % this.TESTPICS.length;
				}
			}
			else
			{
				this.numbers[this.numberTimes - 1] += 0;
				//数量选错
				this.sendEmptyMessage(FinalUtil.WISWRONG);
				this.currentCorrectStreak = 0;
				this.currentProblem = (this.currentProblem
						+ 1) % this.TESTPICS.length;
			}
			break;
		}
		
		this.totalTimes ++;
		if(this.totalTimes == this.MAXTIMES)
		{
			//选择已达到最高上限
			//应该退出
			this.sendEmptyMessage(FinalUtil.WISTOOMUCHTOTAL);
			return;
		}
		if(!this.isGaming &&
				this.numberTimes ==  this.EACHCURCUIT)
		{
			//每个最多进行2次
			//应该结束
			this.sendEmptyMessage(
					FinalUtil.WISTOOMUCHCIRCUIT);
			return;
		}
		this.sendMessage(FinalUtil.WISNEXT, 
				"next",
				this.TESTPICS[this.currentProblem]);
	}
	//
	
	//发送消息
	private void sendMessage(int what,String key,int data)
	{
		Message msg = new Message();
		msg.what = what;
		Bundle bundle = new Bundle();
		bundle.putInt(key, data);
		msg.setData(bundle);
		this.context.handle.sendMessage(msg);
	}
	public Calendar getStartTime1() 
	{
		return startTime1;
	}
	public Calendar getEndTime1() 
	{
		return endTime1;
	}
	public Calendar getStartTime2() 
	{
		return startTime2;
	}
	public Calendar getEndTime2() 
	{
		return endTime2;
	}
	public Calendar getStartTime3() 
	{
		return startTime3;
	}
	public Calendar getEndTime3() 
	{
		return endTime3;
	}
	public Calendar getStartTime4() 
	{
		return startTime4;
	}
	public Calendar getEndTime4() 
	{
		return endTime4;
	}
	public Calendar getStartTime5() 
	{
		return startTime5;
	}
	public Calendar getEndTime5() 
	{
		return endTime5;
	}
	public Calendar getStartTime6() 
	{
		return startTime6;
	}
	public Calendar getEndTime6() 
	{
		return endTime6;
	}
	//发送空消息
	private void sendEmptyMessage(int what)
	{
		this.context.handle.sendEmptyMessage(what);
	}
	public String[] getColors() 
	{
		return colors;
	}
	public String[] getForms() 
	{
		return forms;
	}
	public String[] getNumbers() 
	{
		return numbers;
	}
}
