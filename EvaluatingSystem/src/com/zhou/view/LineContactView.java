package com.zhou.view;

import java.util.Arrays;

import com.zhou.evaluatingsystem.R;
import com.zhou.program.LineContactActivity;
import com.zhou.util.FinalUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class LineContactView extends View 
{
	private Context context;
	//总体宽高
	private int width;
	private int height;
	//占宽高比例
	private final float[][] simpleA = new float[][]
	{
		//数字1 - 5
		new float[]{0.56f,0.28f},//开始
		new float[]{0.87f,0.58f},
		new float[]{0.55f,0.88f},
		new float[]{0.37f,0.18f},
		new float[]{0.14f,0.5f},//结束
		//圆圈
		new float[]{0.34f,0.53f},
		new float[]{0.81f,0.87f},
		//星星
		new float[]{0.17f,0.87f},
		//竖条
		new float[]{0.83f,0.30f},
		//横条
		new float[]{0.62f,0.59f},
		//螺旋
		new float[]{0.14f,0.25f}
	};
	private Bitmap[] simpleA_bitmap;
	private final float[][] simpleB = new float[][]
	{
		new float[]{0.52f,0.24f},//1
		new float[]{0.84f,0.25f},//A
		new float[]{0.88f,0.54f},//2
		new float[]{0.64f,0.35f},//B
		new float[]{0.77f,0.71f},//3
		new float[]{0.54f,0.71f},//C
		new float[]{0.37f,0.22f},//4
		new float[]{0.34f,0.62f},//D
		new float[]{0.14f,0.61f}//5
	};
	//
	private final Bitmap[] symbol_bitmap = 
	{
		BitmapFactory.decodeResource(
				this.getResources(),R.drawable.star),
		BitmapFactory.decodeResource(
				this.getResources(),R.drawable.shutiao),
		BitmapFactory.decodeResource(
				this.getResources(),R.drawable.hengtiao),
		BitmapFactory.decodeResource(
				this.getResources(),R.drawable.luoxuan)
	};
	//圆圈画笔
	private Paint circlePaint;
	//汉字画笔
	private Paint textPaint;
	//圆圈内容画笔
	private Paint contentPaint;
	//选中圆圈画笔
	private Paint circleSelectPaint;
	//具体坐标，根据simple数组计算出来
	private Point[] simpleA_points;
	private Point[] simpleB_points;
	//连接判定数组
	private boolean[] simpleA_flags;
	private boolean[] simpleB_flags;
	//是否正在画
	private boolean isGaming;
	//手指坐标
	private float finger_x;
	private float finger_y;
	//线的起点
	private float begin_x;
	private float begin_y;
	//起始点
	private int startPoint;
	//终点
	private int endPoint;
	//当前选中点
	private int currentPoint;
	//下一个选中点
	private int nextPoint;
	//半径
	private int simpleA_r;
	private int simpleB_r;
	//起点终点
	private final int simpleA_start = 0;
	private final int simpleA_end = 4;
	private final int simpleB_start = 0;
	private final int simpleB_end = this.simpleB.length - 1;
	//错误次数
	private int errorTimes;
	//当前进行的关卡
	private int currentGame;
	//当前的点集
	private Point[] currentGamePoints;
	//当前的判定
	private boolean[] currentGameFlags;
	//当前的半径
	private int currentGameR;
	//是否点了空白部分
	private boolean isOtherPoint;
	//初始化
	{
		this.simpleA_points = new Point[this.simpleA.length];
		for(int i = 0; i < this.simpleA.length; i ++)
		{
			this.simpleA_points[i] = new Point();
		}
		this.simpleB_points = new Point[this.simpleB.length];
		for(int i = 0; i < this.simpleB.length; i ++)
		{
			this.simpleB_points[i] = new Point();
		}
		this.simpleA_flags = new boolean[this.simpleA.length];
		this.simpleB_flags = new boolean[this.simpleB.length];
		this.simpleA_bitmap = new Bitmap[4];
		this.circlePaint = new Paint();
		this.circlePaint.setColor(Color.BLACK);
		this.circlePaint.setStyle(Paint.Style.STROKE);
		this.circlePaint.setStrokeWidth(5);
		this.textPaint = new Paint();
		this.textPaint.setTextSize(20);
		this.textPaint.setColor(Color.BLACK);
		this.contentPaint = new Paint();
		this.contentPaint.setColor(Color.BLACK);
		this.circleSelectPaint = new Paint();
		this.circleSelectPaint.setColor(Color.RED);
		this.circleSelectPaint.setStyle(Paint.Style.STROKE);
		this.circleSelectPaint.setStrokeWidth(5);
		this.isGaming = false;
		this.currentPoint = 0;
		this.nextPoint = 0;
		this.errorTimes = 0;
		this.currentGame = FinalUtil.SIMPLEA;
		this.isOtherPoint = false;
	}
	//设置游戏类型
	public void setGame(int game)
	{
		this.currentGame = game;
		this.currentPoint = this.nextPoint = 0;
		//判断
		if(this.currentGame == FinalUtil.SIMPLEA)
		{
			this.currentGamePoints = this.simpleA_points;
			this.currentGameR = this.simpleA_r;
			this.currentGameFlags = this.simpleA_flags;
			this.startPoint = this.simpleA_start;
			this.endPoint = this.simpleA_end;
		}
		else if(this.currentGame == FinalUtil.SIMPLEB)
		{
			this.currentGamePoints = this.simpleB_points;
			this.currentGameR = this.simpleB_r;
			this.currentGameFlags = this.simpleB_flags;
			this.startPoint = this.simpleB_start;
			this.endPoint = this.simpleB_end;
		}
		this.contentPaint.setTextSize(this.currentGameR);
		this.invalidate();
	}
	//保存游戏
	public Bundle save()
	{
		Bundle bundle = new Bundle();
		bundle.putInt("currentGame", this.currentGame);
		bundle.putInt("currentPoint", this.currentPoint);
		bundle.putInt("nextPoint", this.nextPoint);
		bundle.putInt("errorTimes", this.errorTimes);
		bundle.putInt("startPoint", this.startPoint);
		bundle.putInt("endPoint", this.endPoint);
		bundle.putInt("currentGameR",this.currentGameR);
		bundle.putBoolean("isGame", this.isGaming);
		bundle.putSerializable("currentGamePoints", this.currentGamePoints);
		for(int i = 0; i < this.currentGameFlags.length; i ++)
		{
			bundle.putBoolean("" + i, this.currentGameFlags[i]);
		}
		return bundle;
	}
	//恢复游戏
	public void resume(Bundle bundle)
	{
		this.currentGame = bundle.getInt("currentGame");
		this.currentPoint = bundle.getInt("currentPoint");
		this.nextPoint = bundle.getInt("nextPoint");
		this.errorTimes = bundle.getInt("errorTimes");
		this.startPoint = bundle.getInt("startPoint");
		this.endPoint = bundle.getInt("endPoint");
		this.currentGameR = bundle.getInt("currentGameR");
		this.isGaming = bundle.getBoolean("isGame");
		this.currentGamePoints = (Point[])
				bundle.getSerializable("currentGamePoints");
		if(this.currentGame == FinalUtil.SIMPLEA)
		{
			this.currentGameFlags = this.simpleA_flags;
		}
		else if(this.currentGame == FinalUtil.SIMPLEB)
		{
			this.currentGameFlags = this.simpleB_flags;
		}
		for(int i = 0; i < this.currentGameFlags.length; i ++)
		{
			this.currentGameFlags[i] = bundle
					.getBoolean("" + i);
		}
		//this.currentGameFlags = bundle.getBooleanArray("currentGameFlags");
		this.invalidate();
	}
	//构造函数
	public LineContactView(Context context)
	{
		super(context);
		this.context = context;
	}
	//构造函数
	public LineContactView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		this.context = context;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		// TODO 自动生成的方法存根
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//获取宽高
		this.width = this.getMeasuredWidth();
		this.height = this.getMeasuredHeight();
		//处理simpleA
		for(int i = 0; i < this.simpleA.length; i ++)
		{
			this.simpleA_points[i].x = (int)(this.width * 
					this.simpleA[i][0]);
			this.simpleA_points[i].y = (int)(this.height *
					this.simpleA[i][1]);
		}
		this.simpleA_r = (this.width > this.height ?
				this.height : this.width) / 12;
		for(int i = 0; i < this.simpleA_bitmap.length; i ++)
		{
			this.simpleA_bitmap[i] = Bitmap.createScaledBitmap(
					this.symbol_bitmap[i],this.simpleA_r,
					this.simpleA_r,true);
		}
		//处理simpleB
		for(int i = 0; i < this.simpleB.length; i ++)
		{
			this.simpleB_points[i].x = (int)(this.width *
					this.simpleB[i][0]);
			this.simpleB_points[i].y = (int)(this.height *
					this.simpleB[i][1]);
		}
		this.simpleB_r = (this.width > this.height ?
				this.height : this.width) / 12;
		//判断
		if(this.currentGame == FinalUtil.SIMPLEA)
		{
			this.currentGamePoints = this.simpleA_points;
			this.currentGameR = this.simpleA_r;
			this.currentGameFlags = this.simpleA_flags;
			this.startPoint = this.simpleA_start;
			this.endPoint = this.simpleA_end;
		}
		else if(this.currentGame == FinalUtil.SIMPLEB)
		{
			this.currentGamePoints = this.simpleB_points;
			this.currentGameR = this.simpleB_r;
			this.currentGameFlags = this.simpleB_flags;
			this.startPoint = this.simpleB_start;
			this.endPoint = this.simpleB_end;
		}
		this.contentPaint.setTextSize(this.currentGameR);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		this.finger_x = event.getX();
		this.finger_y = event.getY();
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			//第一个点
			if(this.nextPoint == this.startPoint)
			{
				this.isGaming = true;
				//点击正确
				if(this.isArriveFirst(this.finger_x, this.finger_y))
				{
					this.currentGameFlags[this.startPoint] = true;
					this.sendEmptyMessage(FinalUtil.LINERIGHTMSG);
					this.currentPoint = this.startPoint;
					this.nextPoint ++;
				}
				//点击错误
				else
				{
					this.errorTimes ++;
					this.sendEmptyMessage(FinalUtil.STARTWRONGMSG);
				}
			}
			//下一个点
			else if(this.isGaming)
			{
				int touchWhat = 
						this.arrivePoint(this.finger_x, 
								this.finger_y);
				//点击的确实是一个点
				if(touchWhat != -1)
				{
					//点击正确
					if(touchWhat == this.nextPoint)
					{
						this.currentGameFlags[touchWhat] = true;
						//已经是最后一个点了
						if(this.nextPoint == this.endPoint)
						{
							this.sendMessage(FinalUtil.LINECOMLETEDMSG,
									"errorTimes",
									this.errorTimes);
							this.isGaming = false;
							this.errorTimes = 0;
						}
						//还有后续的点
						else
						{
							this.nextPoint ++;
							this.currentPoint ++;
							this.sendEmptyMessage(FinalUtil.LINERIGHTMSG);
						}
					}
					//这是已经点过的点的最后一个
					else if(touchWhat == this.currentPoint)
						break;
					//错误的点
					else
					{
						this.errorTimes ++;
						this.sendMessage(FinalUtil.LINEWRONGMSG,"next",
								this.nextPoint);
					}
				}
				//点的是空白部分
				else
				{
					this.isOtherPoint = true;
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		this.invalidate();
		return true;
	}
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		canvas.drawColor(Color.WHITE);
		//画圆
		for(int i = 0; i < this.currentGamePoints.length; i ++)
		{
			canvas.drawCircle(this.currentGamePoints[i].x,
					this.currentGamePoints[i].y,
					this.currentGameR,
					this.currentGameFlags[i] ?
							this.circleSelectPaint :
								this.circlePaint);
		}
		//写字
		canvas.drawText("开始",
				this.currentGamePoints[this.startPoint].x - this.currentGameR / 2,
				this.currentGamePoints[this.startPoint].y - this.currentGameR - 2,
				this.textPaint);
		canvas.drawText("结束",
				this.currentGamePoints[this.endPoint].x - this.currentGameR / 2,
				this.currentGamePoints[this.endPoint].y + this.currentGameR + 20,
				this.textPaint);
		//连线、填充圆圈内的东西
		if(this.currentGame == FinalUtil.SIMPLEA)
		{
			for(int i = 7; i < this.currentGamePoints.length; i ++)
			{
				canvas.drawBitmap(this.symbol_bitmap[i - 7],
						this.currentGamePoints[i].x - this.currentGameR,
						this.currentGamePoints[i].y - this.currentGameR,
						this.circlePaint);
			}
			for(int i = this.startPoint; i <= this.endPoint; i ++)
			{
				if(i >0 && this.currentGameFlags[i])
				{
					canvas.drawLine(
							this.currentGamePoints[i - 1].x,
							this.currentGamePoints[i - 1].y,
							this.currentGamePoints[i].x,
							this.currentGamePoints[i].y,
							this.circleSelectPaint);
				}
				canvas.drawText((i + 1) + "",
						this.currentGamePoints[i].x - this.currentGameR / 3,
						this.currentGamePoints[i].y + this.currentGameR / 3,
						this.contentPaint);
			}
		}
		else if(this.currentGame == FinalUtil.SIMPLEB)
		{
			for(int i = this.startPoint; i <= this.endPoint; i ++)
			{
				if(i >0 && this.currentGameFlags[i])
				{
					canvas.drawLine(
							this.currentGamePoints[i - 1].x,
							this.currentGamePoints[i - 1].y,
							this.currentGamePoints[i].x,
							this.currentGamePoints[i].y,
							this.circleSelectPaint);
				}	
				if(i % 2 == 0)
				{
					canvas.drawText((i / 2 + 1) + "",
							this.currentGamePoints[i].x - this.currentGameR / 3,
							this.currentGamePoints[i].y + this.currentGameR / 3,
							this.contentPaint);
				}
				else if(i % 2 == 1)
				{
					canvas.drawText((char)(i / 2 + 'A') + "",
							this.currentGamePoints[i].x - this.currentGameR / 3,
							this.currentGamePoints[i].y + this.currentGameR / 3,
							this.contentPaint);
				}
			}
		}
		if(this.isGaming == true && this.isOtherPoint == true)
		{
			canvas.drawLine(this.currentGamePoints[this.currentPoint].x,
					this.currentGamePoints[this.currentPoint].y,
					this.finger_x,this.finger_y,this.circleSelectPaint);
			this.isOtherPoint = false;
		}
	}
	//判断是否点到了第一个
	private boolean isArriveFirst(float x, float y)
	{
		if(Math.pow(Math.abs(x - this.currentGamePoints[0].x), 2) +
				Math.pow(Math.abs(y - this.currentGamePoints[0].y), 2)
				<= Math.pow(this.currentGameR, 2))
		{
			this.begin_x = this.currentGamePoints[0].x;
			this.begin_y = this.currentGamePoints[0].y;
			return true;
		}
		return false;
	}
	//判断点了那个点
	private int arrivePoint(float x, float y)
	{
		for(int i = 1; i < this.currentGamePoints.length; i ++)
		{
			if(Math.pow(Math.abs(x - this.currentGamePoints[i].x), 2) +
				Math.pow(Math.abs(y - this.currentGamePoints[i].y), 2)
				<= Math.pow(this.currentGameR, 2))
			{
				return i;
			}
		}
		return -1;
	}
	//发消息
	private void sendMessage(int what,String key,int data)
	{
		Message msg = new Message();
		msg.what = what;
		Bundle bundle = new Bundle();
		bundle.putInt(key, data);
		msg.setData(bundle);
		((LineContactActivity)this.context)
		.handler.sendMessage(msg);
	}
	//发空消息
	private void sendEmptyMessage(int what)
	{
		((LineContactActivity)this.context)
		.handler.sendEmptyMessage(what);
	}
}
