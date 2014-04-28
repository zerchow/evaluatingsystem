package com.zhou.util;

import java.io.Serializable;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class FinalUtil implements Serializable 
{
	//https://github.com/zerchow/evaluatingsystem.git
	//性别常量
	public static final String MALE = "男";
	public static final String FEMALE = "女";
	//EditText检查的Toast提示
	public static final String NAME_NO_NULL = "姓名不能为空";
	public static final String ID_NO_NULL = "ID不能为空";
	public static final String DIAGNOSE_NO_NULL = "诊断不能为空";
	//请求码、结果码
	public static final int ADD_REQUEST_CODE = 0x45;
	public static final int ADD_RESULT_CODE = 0x46;
	//数据库插入失败提示
	public static final String ALREADY_EXIST = "已存在，插入失败";
	//传输过程的消息
	public static final int BACKUPING = 0x200;
	//传输失败的消息
	public static final int BACKUPERROR = 0x201;
	//传输完成果的消息
	public static final int BACKUPDONE = 0x202;
	public static AlertDialog.Builder getDialog(Context context,
			String content,boolean hasCancel)
	{
		AlertDialog.Builder dialog = 
			new AlertDialog.Builder(context)
			.setTitle(content);
		if(hasCancel)
			dialog.setNegativeButton("否", new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
				}
			});
		dialog.setCancelable(false);
		return dialog;
	}
	//汉诺塔消息
	public static final int MOVEMSG = 0x203;//当前步数消息
	public static final int TIMEMSG = 0x204;//所做次数消息
	public static final int PROMSG = 0x205;//关卡消息
	public static final int TARGETMSG = 0x206;//目标步数消息
	public static final int ENDMSG = 0x207;//结束消息
	public static final int TIMETOOMUCHMSG = 0x208;//次数太多消息
	public static final int SUCCESSMSG = 0x209;//成功消息
	public static final int FAILMSG = 0x210;//成功但步数太多的消息
	public static final int FORMALTESTMSG = 0x211;//正式测试的消息
	//连线测验消息
	public static final int STARTWRONGMSG = 0x212;//起点错误消息
	public static final int LINERIGHTMSG = 0x213;//连接正确消息
	public static final int LINEWRONGMSG = 0x214;//连接错误消息
	public static final int LINECOMLETEDMSG = 0x215;//连接完成消息
	//连线测验游戏类型
	public static final int SIMPLEA = 0;
	public static final int SIMPLEB = 1;
	//威斯康星卡片分类消息
	public static final int WISTOOMUCHTOTAL = 0x216;//总次数太多消息
	public static final int WISTOOMUCHCIRCUIT = 0x217;//单个循环太多消息
	public static final int WISNEXT = 0x218;//下一个消息
	public static final int WISCORRECT = 0x219;//正确消息
	public static final int WISWRONG = 0x220;//错误消息
	//威斯康星游戏类型
	public static final int WISCOLOR = 0;//颜色
	public static final int WISFORM = 1;//类型
	public static final int WISNUMBER = 2;//数量
	//威斯康星形状
	public static final int WISTRIANGLE = 0;//三角形
	public static final int WISSTAR = 1;//五角星
	public static final int WISCROSS = 2;//十字架
	public static final int WISCIRCLE = 3;//圆形
	//gonogo消息
	public static final int CUEDWHITEMSG = 0x221;//白屏消息
	public static final int CUEDFIXATIONMSG = 0x222;//十字架消息
	public static final int CUEDFRAMEMSG = 0x223;//空框消息
	public static final int CUEDCOLORFRAMEMSG = 0x224;//填充块消息
	public static final int CUEDSTART = 0x225;//开始消息
	public static final int CUEDCORRECT = 0x226;//正确
	public static final int CUEDWRONG = 0x227;//错误
	//gonogo方向
	public static final int CUEDVERTICAL = 0;//垂直
	public static final int CUEDHORIZONTAL = 1;//水平
	//gonogo颜色
	public static final int CUEDBLUE = 0;//蓝色
	public static final int CUEDGREEN = 1;//绿色
	//字色干扰消息
	public static final int INTERFERENCESTART = 0x228;//开始消息
	public static final int INTERFERENCESTIMULUS = 0x229;//设置刺激
	public static final int INTERFERENCECORRECT = 0x230;//正确
	public static final int INTERFERENCEWRONG = 0x231;//错误
	public static final int INTERFERENCETIMEOUT = 0x232;//超时
	//Aospan类型
	public static final int PRACLETTER = 0;//字母练习
	public static final int PRACMATH = 1;//算术式练习
	public static final int PRACBOTH = 2;//字母和算术式练习
	public static final int TESTBOTH = 3;//字母和算术式测试
	//Aospan消息
	public static final int PRACLETTERMSG = 0x233;//练习字母消息
	public static final int PRACMATHMSG = 0x234;//练习算术式消息
	public static final int PRACBOTHMSG = 0x235;//练习字母和算术式消息
	public static final int TESTBOTHMSG = 0x236;//测试字母和算术式消息 
	public static final int AOSPANSTARTMSG = 0x237;//开始消息
	public static final int AOSPANSETTEXT = 0x238;//设置textview
	public static final int AOSPANSETMATH = 0x239;//设置算术式
	public static final int AOSPANUSERSELECT = 0x240;//开始用户选择
	public static final int AOSPANSETNEXTLETTER = 0x241;//设置下一个字母
	public static final int AOSPANSETNEXTMATH = 0x242;//设置下一个算术式
	public static final int AOSPANJUSTFORTEST = 0x243;
	//请求码、结果码
	public static final int AOSPAN_REQUEST = 0x47;
	public static final int AOSPAN_RESULT = 0x48;
	//数字广度类型
	public static final int DS_FORWARD = 0;
	public static final int DS_BACKWARD = 1;
	//数字广度消息
	public static final int DS_SHOWTIP = 0x244;//红点消息
	public static final int DS_SHOWDIGIT = 0x245;//数字消息
	public static final int DS_START = 0x246;//开始消息
	public static final int DS_FORWARDPRAC = 0x247;//正向练习
	public static final int DS_BACKWARDPRAC = 0x248;//反向练习
	public static final int DS_FORWARDTEST = 0x249;//正向测试
	public static final int DS_BACKWARDTEST = 0x250;//反向测试
	public static final int DS_USERINPUT = 0x251;//用户输入
	public static final int DS_CORRECT = 0x252;//正确
	public static final int DS_ERROR = 0x253;//错误
	public static final int DS_FORWARDEND = 0x254;
	public static final int DS_BACKWARDEND = 0x255;
	//请求码、结果码
	public static final int MMSE_REQUEST_CODE = 0x49;
	public static final int MMSE_RESULT_CODE = 0x50;
	public static final int HANOI_REQUEST_CODE = 0x51;
	public static final int HANOI_RESULT_CODE = 0x52;
	public static final int LINE_REQUEST_CODE = 0x53;
	public static final int LINE_RESULT_CODE = 0x54;
	public static final int WISCONSIN_REQUEST_CODE = 0x55;
	public static final int WISCONSIN_RESULT_CODE = 0x56;
	public static final int CUED_REQUEST_CODE = 0x57;
	public static final int CUED_RESULT_CODE = 0x58;
	public static final int WORD_REQUEST_CODE = 0x59;
	public static final int WORD_RESULT_CODE = 0x60;
	public static final int AOSPAN_REQUEST_CODE = 0x61;
	public static final int AOSPAN_RESULT_CODE = 0x62;
	public static final int DSV_REQUEST_CODE = 0x63;
	public static final int DSV_RESULT_CODE = 0x64;
	public static final int DSA_REQUEST_CODE = 0x65;
	public static final int DSA_RESULT_CODE = 0x66;
	//
	public static String getCurrentTimeString()
	{
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ calendar.get(Calendar.MINUTE) + ":"
				+ calendar.get(Calendar.SECOND);
	}
	public static String getCurrentDateString()
	{
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DATE);
	}
	//
	public static boolean hasNetwork(Context context)
	{
		ConnectivityManager con = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = con.getActiveNetworkInfo();
		if(info != null || info.isAvailable())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean isWifi(Context context)
	{
		ConnectivityManager con = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifi = con.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).getState();
		if(wifi == State.CONNECTED)
			return true;
		else
			return false;
	}
}
