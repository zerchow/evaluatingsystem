package com.zhou.logic;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.os.Message;

import com.zhou.program.AospanActivity;
import com.zhou.util.FinalUtil;

public class Aospan 
{
	//sequence
	private final int[] PracLetterSize = 
		{2,2,3,3};
	//sequence
	private final int[] PracBothSize = 
		{2,2,2};
	//noreplace
	private final int[] TestBothSize = 
		{3,3,3,4,4,4,5,5,5,6,6,6,7,7,7};
	//
	private final String[] Letters = 
		{"F","P","Q","J","H","K","T","S","N","R","Y","L"};
	//sequence
	private final String[] PracMaths = 
		{
			"(1*2) + 1 = ?","(1/1) - 1 = ?",
			"(7*3) - 3 = ?","(4*3) + 4 = ?",
			"(3/3) + 2 = ?","(2*6) - 4 = ?",
			"(8*9) - 8 = ?","(4*5) - 5 = ?",
			"(4*2) + 6 = ?","(4/4) + 7 = ?",
			"(8*2) - 8 = ?","(2*9) - 9 = ?",
			"(8/2) + 9 = ?","(3*8) - 1 = ?",
			"(6/3) + 1 = ?"
		};
	private final String[] PracMathAns = 
		{
			"3","2","18","16","1",
			"6","64","11","14","12",
			"2","9","7","23","3"
		};
	private final boolean[] PracMathJudge = 
		{
			true,false,true,true,false,
			false,true,false,true,false,
			false,true,false,true,true
		};
	//
	private final String[] MathOpt1 = 
		{
			"(1/1)", "(2/1)", "(2/2)", "(3/1)", "(3/3)", "(4/1)", "(4/2)", "(4/4)", 
			"(5/1)", "(5/5)", "(6/1)", "(6/2)", "(6/3)", "(6/6)", "(7/1)", "(7/7)",
			"(8/1)", "(8/2)", "(8/4)", "(8/8)", "(9/1)", "(9/3)", "(9/9)", "(1*2)", 
			"(1*3)", "(2*2)", "(1*4)", "(1*5)", "(3*2)", "(2*3)", "(1*6)", "(1*7)", 
			"(4*2)", "(2*4)", "(1*8)", "(3*3)", "(1*9)", "(5*2)", "(2*5)", "(6*2)", 
			"(4*3)", "(3*4)", "(2*6)", "(7*2)", "(2*7)", "(5*3)", "(3*5)", "(8*2)"
		};
	private final int[] MathOpt1Result = 
		{
			1,2,1,3,1,4,2,1,
			5,1,6,3,2,1,7,1,
			8,4,2,1,9,3,1,2,
			3,4,4,5,6,6,6,7,
			8,8,8,9,9,10,10,12,
			12,12,12,14,14,15,15,16
		};
	/*private final int[] MathDifficulty = 
		{
			1, 1, 1, 1, 1, 1, 1, 1, 
			1, 1, 1, 1, 1, 1, 1, 1, 
			1, 1, 1, 1, 1, 1, 1, 1, 
			1, 1, 1, 1, 1, 1, 1, 1, 
			1, 1, 1, 1, 1, 2, 2, 2, 
			2, 2, 2, 2, 2, 2, 2, 2
		};*/
	private final int[] MathOpt2 = 
		{
			1, 2, 3, 4, 5, 6, 7, 8, 9, 
			1, 2, 3, 4, 5, 6, 7, 8, 9
		};
	private final String[] MathSign = 
		{
			"+", "+", "+", "+", "+", "+", "+", "+", "+", 
			"-", "-", "-", "-", "-", "-", "-", "-", "-"
		};
	private final boolean[] MathJudge = 
		{
			true,true,true,true,true,
			false,false,false,false,false
		};
	private final int[] MathRand = 
		{
			1,2,3,4,5,6,7,8,9,
			-1,-2,-3,-4,-5,-6,-7,-8,-9
		};
	//
	private ArrayList<String> pracLetterList;
	//
	private ArrayList<String> pracMathList;
	private ArrayList<String> pracMath_ansList;
	private ArrayList<Boolean> pracMath_judgeList;
	//
	private ArrayList<String> pracBothLetterList;
	private ArrayList<String> pracBothMathList;
	private ArrayList<String> pracBothMath_ansList;
	private ArrayList<Boolean> pracBothMath_judgeList;
	//
	private ArrayList<String> testBothLetterList;
	private ArrayList<String> testBothMathList;
	private ArrayList<String> testBothMath_ansList;
	private ArrayList<Boolean> testBothMath_judgeList;
	//
	{
		//
		this.pracLetterList = new ArrayList<String>();
		//
		this.pracMathList = new ArrayList<String>();
		this.pracMath_ansList = new ArrayList<String>();
		this.pracMath_judgeList = new ArrayList<Boolean>();
		//
		this.pracBothLetterList = new ArrayList<String>();
		this.pracBothMathList = new ArrayList<String>();
		this.pracBothMath_ansList = new ArrayList<String>();
		this.pracBothMath_judgeList = new ArrayList<Boolean>();
		//
		this.testBothLetterList = new ArrayList<String>();
		this.testBothMathList = new ArrayList<String>();
		this.testBothMath_ansList = new ArrayList<String>();
		this.testBothMath_judgeList = new ArrayList<Boolean>();
	}
	//
	private AospanActivity context;
	public Aospan(AospanActivity context)
	{
		this.context = context;
	}
	private void sendMessage(int what,Bundle bundle)
	{
		Message msg = new Message();
		msg.what = what;
		msg.setData(bundle);
		this.context.handler.sendMessage(msg);
	}
	//
	private ArrayList<String> getLetterList()
	{
		ArrayList<String> letterList = new ArrayList<String>();
		for(int i = 0; i < this.Letters.length; i ++)
		{
			letterList.add(this.Letters[i]);
		}
		return letterList;
	}
	private String getRandomLetter(int size)
	{
		Random random = new Random(System.currentTimeMillis());
		ArrayList<String> tempList = this.getLetterList();
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < size; i ++)
		{
			str.append(tempList.remove(random.nextInt(
					tempList.size())));
		}
		return str.toString();
	}
	//
	private ArrayList<String> getMathOpt1List()
	{
		ArrayList<String> mathOpt1List = new ArrayList<String>();
		for(int i = 0; i < this.MathOpt1.length; i ++)
		{
			mathOpt1List.add(this.MathOpt1[i]);
		}
		return mathOpt1List;
	}
	private ArrayList<Integer> getMathOpt1ResultList()
	{
		ArrayList<Integer> mathOpt1ResultList = 
				new ArrayList<Integer>();
		for(int i = 0; i < this.MathOpt1Result.length; i ++)
		{
			mathOpt1ResultList.add(this.MathOpt1Result[i]);
		}
		return mathOpt1ResultList;
	}
	private ArrayList<Integer> getMathOpt2List()
	{
		ArrayList<Integer> mathOpt2List = new ArrayList<Integer>();
		for(int i = 0; i < this.MathOpt2.length; i ++)
		{
			mathOpt2List.add(this.MathOpt2[i]);
		}
		return mathOpt2List;
	}
	private ArrayList<String> getMathSignList()
	{
		ArrayList<String> mathSignList = new ArrayList<String>();
		for(int i = 0; i < this.MathSign.length; i ++)
		{
			mathSignList.add(this.MathSign[i]);
		}
		return mathSignList;
	}
	private ArrayList<Boolean> getMathJudgeList()
	{
		ArrayList<Boolean> mathJudgeList = new ArrayList<Boolean>();
		for(int i = 0; i < this.MathJudge.length; i ++)
		{
			mathJudgeList.add(this.MathJudge[i]);
		}
		return mathJudgeList;
	}
	private ArrayList<Integer> getMathRandList()
	{
		ArrayList<Integer> mathRandList = new ArrayList<Integer>();
		for(int i = 0; i < this.MathRand.length; i ++)
		{
			mathRandList.add(this.MathRand[i]);
		}
		return mathRandList;
	}
	private class Math
	{
		public String math;//算术式
		public String math_ans;//算术式答案
		public boolean math_judge;//算术式答案的正确与否
	}
	private ArrayList<Math> getRandomMath(int size)
	{
		ArrayList<String> op1 = this.getMathOpt1List();
		ArrayList<Integer> op1_r = this.getMathOpt1ResultList();
		ArrayList<Integer> op2 = this.getMathOpt2List();
		ArrayList<String> sign = this.getMathSignList();
		ArrayList<Boolean> judge = this.getMathJudgeList();
		ArrayList<Integer> rand = this.getMathRandList();
		//
		ArrayList<Math> maths = new ArrayList<Math>();
		Random random = new Random(System.currentTimeMillis());
		for(int i = 0; i < size; i ++)
		{
			Math math = new Math();
			//
			int index = random.nextInt(op1.size());
			String str1 = op1.remove(index);
			int val1 = op1_r.remove(index);
			index = random.nextInt(op2.size());
			int str2 = op2.remove(index);
			String mark = sign.remove(index);
			boolean j = judge.remove(random.nextInt(judge.size()));
			int r = rand.remove(random.nextInt(rand.size()));
			int result = mark.equals("+") ? (val1 + str2) : (val1 - str2);
			int val2 = mark.equals("+") ? str2 : (-1) * str2;
			while(result < 0)
			{
				val2 += 3;
				str2 = java.lang.Math.abs(val2);
				if(val2 > 0)
					mark = "+";
				result = mark.equals("+") ? (val1 + str2) : (val1 - str2);
			}
			if(j == false)
			{
				int preresult = result;
				result += r;
				while(result < 0 || result == preresult)
				{
					r += 2;
					result = preresult;
					result += r;
				}
			}
			math.math = str1 + " " + mark + " " + str2 + " = ?";
			math.math_ans = result + "";
			math.math_judge = j;
			//
			maths.add(math);
		}
		return maths;
	}
	//
	public void init() 
	{
		//
		ArrayList<String> tempList;
		//
		tempList = this.getLetterList();
		for(int i = 0; i < this.PracLetterSize.length; i ++)
		{
			StringBuilder str = new StringBuilder();
			for(int j = 0; j < this.PracLetterSize[i]; j ++)
			{
				str.append(tempList.remove(0));
			}
			this.pracLetterList.add(str.toString());
		}
		//
		for(int i = 0; i < this.PracMaths.length; i ++)
		{
			this.pracMathList.add(this.PracMaths[i]);
			this.pracMath_ansList.add(this.PracMathAns[i]);
			this.pracMath_judgeList.add(this.PracMathJudge[i]);
		}
		//
		for(int i = 0; i < this.PracBothSize.length; i ++)
		{
			this.pracBothLetterList.add(this.getRandomLetter(this.PracBothSize[i]));
			ArrayList<Math> maths = this.getRandomMath(this.PracBothSize[i]);
			for(Math math : maths)
			{
				this.pracBothMathList.add(math.math);
				this.pracBothMath_ansList.add(math.math_ans);
				this.pracBothMath_judgeList.add(math.math_judge);
			}
		}
		//
		ArrayList<Integer> testbothsize = new ArrayList<Integer>();
		for(int i = 0; i < this.TestBothSize.length; i ++)
		{
			testbothsize.add(this.TestBothSize[i]);
		}
		Random random = new Random(System.currentTimeMillis());
		while(! testbothsize.isEmpty())
		{
			int index = testbothsize.remove(
					random.nextInt(testbothsize.size()));
			this.testBothLetterList.add(this.getRandomLetter(index));
			ArrayList<Math> maths = this.getRandomMath(index);
			for(Math math : maths)
			{
				this.testBothMathList.add(math.math);
				this.testBothMath_ansList.add(math.math_ans);
				this.testBothMath_judgeList.add(math.math_judge);
			}
		}
	}
	public boolean hasNextItem() 
	{
		return ! this.testBothLetterList.isEmpty();
	}
	public void getNextItem() 
	{
		Bundle bundle = new Bundle();
		if(! this.pracLetterList.isEmpty())
		{
			bundle.putString("pracletter",
					this.pracLetterList.remove(0));
			bundle.putInt("total", this.PracLetterSize.length);
			this.sendMessage(FinalUtil.PRACLETTERMSG, bundle);
		}
		else if(! this.pracMathList.isEmpty())
		{
			bundle.putInt("total", this.PracMaths.length);
			bundle.putString("pracmath", this.pracMathList.remove(0));
			bundle.putString("pracmath_ans", this.pracMath_ansList.remove(0));
			bundle.putBoolean("pracmath_judge", this.pracMath_judgeList.remove(0));
			this.sendMessage(FinalUtil.PRACMATHMSG, bundle);
		}
		else if(! this.pracBothLetterList.isEmpty())
		{
			bundle.putInt("total", this.PracBothSize.length);
			String temp = this.pracBothLetterList.remove(0);
			bundle.putString("pracbothletter", temp);
			ArrayList<String> maths = new ArrayList<String>();
			ArrayList<String> ans = new ArrayList<String>();
			ArrayList<Boolean> judges = new ArrayList<Boolean>();
			for(int i = 0; i < temp.length(); i ++)
			{
				maths.add(this.pracBothMathList.remove(0));
				ans.add(this.pracBothMath_ansList.remove(0));
				judges.add(this.pracBothMath_judgeList.remove(0));
			}
			bundle.putStringArrayList("pracbothmath", maths);
			bundle.putStringArrayList("pracbothmath_ans", ans);
			bundle.putSerializable("pracbothmath_judge", judges);
			this.sendMessage(FinalUtil.PRACBOTHMSG, bundle);
		}
		else if(! this.testBothLetterList.isEmpty())
		{
			bundle.putInt("total", this.TestBothSize.length);
			String temp = this.testBothLetterList.remove(0);
			bundle.putString("testbothletter", temp);
			ArrayList<String> maths = new ArrayList<String>();
			ArrayList<String> ans = new ArrayList<String>();
			ArrayList<Boolean> judges = new ArrayList<Boolean>();
			for(int i = 0; i < temp.length(); i ++)
			{
				maths.add(this.testBothMathList.remove(0));
				ans.add(this.testBothMath_ansList.remove(0));
				judges.add(this.testBothMath_judgeList.remove(0));
			}
			bundle.putStringArrayList("testbothmath", maths);
			bundle.putStringArrayList("testbothmath_ans", ans);
			bundle.putSerializable("testbothmath_judge", judges);
			this.sendMessage(FinalUtil.TESTBOTHMSG, bundle);
		}
	}
}
