package com.zhou.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.zhou.program.TowerOfHanoiActivity;
import com.zhou.util.FinalUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TowerOfHanoiView extends View 
{
	//measure -> layout -> draw
	private Context context;
	//规定移动步数，1次练习，12次测试
	private final int[] targetMoves = new int[]{2,2,2,3,3,4,4,4,4,5,5,5,5};
	//小球颜色
	private final int R = Color.RED;
	private final int G = Color.GREEN;
	private final int B = Color.BLUE;
	//问题数据
	private final int[][] DATAS = {
			{this.G,this.B},{},{this.R},
			{},{this.B,this.G},{this.R},
			{this.G},{this.R},{this.B},
			{this.G,this.B},{this.R},{},
			{this.G},{this.R,this.B},{},
			{this.R,this.G},{this.B},{},
			{this.B},{this.R,this.G},{},
			{this.R,this.B},{},{this.G},
			{},{this.R,this.B},{this.G},
			{this.R,this.G,this.B},{},{},
			{this.R,this.B,this.G},{},{},
			{this.B,this.G},{this.R},{},
			{this.B},{this.R},{this.G}
	};
	//柱子总数
	private final int PILLAR_NUM = 3;
	//柱子
	private List<Stack<Integer>> pillars;
	//手指移动位置
	private float finger_x;
	private float finger_y;
	//小球移动的起点和终点（第几根柱子）
	private int fromPillar;
	private int toPillar;
	//是否处于移动状态
	private boolean isMoving;
	//整体宽高
	private int width;
	private int height;
	//柱子顶点坐标
	private Point[] tops;
	//柱子顶部和底部的坐标，用于画线
	private float[] lines;
	//小球半径
	private int r ;
	//小球圆心，3,2,1
	private Point[][] circles;
	//柱子画笔
	private Paint pillarPaint;
	//小球画笔
	private Paint circlePaint;
	//手指移动画笔
	private Paint fingerPaint;
	//手指移动外围画笔
	private Paint fingerPaint_out;
	//当前关卡
	private static int currentProblem;
	//当前移动步数
	private int currentMoves;
	//超出步数
	private int diffMoves;
	//所做次数
	private int currentTimes;
	//最大次数
	private final int MAXTIME = 3;
	//总分
	private static int totalScore;
	//当前关卡分数
	private int currentScore;
	//是否只供浏览
	private boolean isScanOnly;
	//是否正式测试
	private static boolean isFormalTest;
	//
	private boolean isFirstTouch = true;
	//初始化块
	{
		this.tops = new Point[]{
			new Point(),new Point(),new Point()
		};
		this.circles = new Point[][]{
			new Point[]{
					new Point(),new Point(),new Point()
			},
			new Point[]{
					new Point(),new Point()
			},
			new Point[]{
					new Point()
			}
		};
		this.pillars = new ArrayList<Stack<Integer>>();
		for(int i = 0; i < this.PILLAR_NUM; i ++)
			this.pillars.add(new Stack<Integer>());
		this.pillars.get(0).push(this.G);
		this.pillars.get(0).push(this.R);
		this.pillars.get(1).push(this.B);
		this.pillarPaint = new Paint();
		this.pillarPaint.setColor(Color.BLACK);
		this.pillarPaint.setStrokeWidth(5.0f);
		this.circlePaint = new Paint();
		this.fingerPaint = new Paint();
		this.fingerPaint_out = new Paint();
		this.fingerPaint_out.setStyle(Paint.Style.STROKE);
		this.isMoving = false;
		this.fromPillar = -1;
		this.toPillar = -1;
		this.currentMoves = 0;
		TowerOfHanoiView.currentProblem = 0;
		this.diffMoves = this.currentMoves - this.targetMoves[TowerOfHanoiView.currentProblem];
		this.currentTimes = 1;
		TowerOfHanoiView.totalScore = 0;
		this.currentScore = 0;
		this.isScanOnly = false;
		TowerOfHanoiView.isFormalTest = false;
	}
	//初始化函数（重新开始）
	public void init()
	{
		for(int i = 0; i < this.pillars.size(); i ++)
			this.pillars.get(i).clear();
		this.pillars.get(0).push(this.G);
		this.pillars.get(0).push(this.R);
		this.pillars.get(1).push(this.B);
		this.isMoving = false;
		this.fromPillar = -1;
		this.toPillar = -1;
		this.currentMoves = 0;
		this.currentScore = 0;
		if(this.currentTimes == this.MAXTIME)
		{
			//最后一关的时候不在变成1
			//39 / 3 - 1 = 12
			this.currentTimes = TowerOfHanoiView.currentProblem + 1 == this.DATAS.length / 3 ?
					this.currentTimes : 1;
			this.sendEmptyMessage(FinalUtil.TIMETOOMUCHMSG);
		}
		else
		{
			this.currentTimes ++;
		}
		this.isScanOnly = false;
		this.sendMessage(FinalUtil.MOVEMSG,
				"moves",this.currentMoves);
		this.sendMessage(FinalUtil.TIMEMSG,
				"times",this.currentTimes);
		this.invalidate();
	}
	//下一关
	public void initNext()
	{
		//比重新开始多了计分判断
		if(TowerOfHanoiView.isFormalTest)
			this.scoring();
		for(int i = 0; i < this.pillars.size(); i ++)
			this.pillars.get(i).clear();
		this.pillars.get(0).push(this.G);
		this.pillars.get(0).push(this.R);
		this.pillars.get(1).push(this.B);
		this.isMoving = false;
		this.fromPillar = -1;
		this.toPillar = -1;
		this.currentMoves = 0;
		this.currentScore = 0;
		this.currentTimes = 1;
		this.isScanOnly = false;
		this.sendMessage(FinalUtil.MOVEMSG,
				"moves",this.currentMoves);
		this.sendMessage(FinalUtil.TIMEMSG,
				"times",this.currentTimes);
		this.invalidate();
	}
	//计分
	private void scoring()
	{
		this.diffMoves = this.currentMoves - this.targetMoves[TowerOfHanoiView.currentProblem];
		if(this.diffMoves == 0)
		{
			//做对了的情况下才计分
			//最多做三次
			//第一次，3 - (1 - 1) = 3分
			//第二次，3 - (2 - 1) = 2分
			//第三次，3 - (3 - 1) = 1分
			this.currentScore = this.MAXTIME - 
					(this.currentTimes - 1);
		}//做错为0分
		TowerOfHanoiView.totalScore += this.currentScore;
	}
	//设置只供浏览
	public void initScan()
	{
		this.pillarPaint.setStrokeWidth(0.0f);
		this.isScanOnly = true;
		//第几组3数组对
		int temp = TowerOfHanoiView.currentProblem * this.PILLAR_NUM
				% this.DATAS.length;
		for(int i = 0; i < this.pillars.size(); i ++)
		{
			this.pillars.get(i).clear();
			for(int j = 0; j < this.DATAS[i + temp].length; j ++)
				this.pillars.get(i).push(this.DATAS[i + temp][j]);
		}
		this.sendMessage(FinalUtil.PROMSG, 
				"problem", TowerOfHanoiView.currentProblem);
		this.sendMessage(FinalUtil.TARGETMSG, 
				"target", this.targetMoves[TowerOfHanoiView.currentProblem]);
		this.invalidate();
	}
	public void scanNext()
	{
		if(! this.isScanOnly)
			return;
		if(TowerOfHanoiView.currentProblem + 1 == this.DATAS.length / 3)
		{
			//最后一关
			this.sendMessage(FinalUtil.ENDMSG, 
					"end",TowerOfHanoiView.totalScore);
			return;
		}
		else
		{
			TowerOfHanoiView.currentProblem ++;
		}
		if(TowerOfHanoiView.currentProblem == 1)
		{
			TowerOfHanoiView.isFormalTest = true;
			this.sendEmptyMessage(FinalUtil.FORMALTESTMSG);
			this.isFirstTouch = true;
		}
		int temp = TowerOfHanoiView.currentProblem * this.PILLAR_NUM
				% this.DATAS.length;
		for(int i = 0; i < this.pillars.size(); i ++)
		{
			this.pillars.get(i).clear();
			for(int j = 0; j < this.DATAS[i + temp].length; j ++)
				this.pillars.get(i).push(this.DATAS[i + temp][j]);
		}
		this.sendMessage(FinalUtil.PROMSG, 
				"problem", TowerOfHanoiView.currentProblem);
		this.sendMessage(FinalUtil.TARGETMSG, 
				"target", this.targetMoves[TowerOfHanoiView.currentProblem]);
		this.invalidate();
	}
	//暂时保存数据
	public Bundle save()
	{
		Bundle bundle = new Bundle();
		if(! this.isScanOnly)
		{
			bundle.putInt("currentMoves", this.currentMoves);
			bundle.putInt("currentTimes", this.currentTimes);
			bundle.putInt("currentProblem",TowerOfHanoiView.currentProblem);
			bundle.putInt("totalSocre", TowerOfHanoiView.totalScore);
		}
		bundle.putBoolean("isScanOnly", this.isScanOnly);
		bundle.putSerializable("pillar", 
				(Serializable) this.pillars);
		return bundle;
	}
	//恢复数据
	@SuppressWarnings("unchecked")
	public void resume(Bundle bundle)
	{
		this.isScanOnly = bundle.getBoolean("isScanOnly");
		if(! this.isScanOnly)
		{
			this.currentMoves = bundle.getInt("currentMoves");
			this.currentTimes = bundle.getInt("currentTimes");
			TowerOfHanoiView.currentProblem = bundle.getInt("currentProblem");
			TowerOfHanoiView.totalScore = bundle.getInt("totalSocre");
			this.sendMessage(FinalUtil.MOVEMSG,
					"moves",this.currentMoves);
			this.sendMessage(FinalUtil.TIMEMSG,
					"times",this.currentMoves);
		}
		else
		{
			this.initScan();
		}
		this.pillars = (List<Stack<Integer>>) bundle.getSerializable("pillar");
		this.invalidate();
	}
	//构造函数
	public TowerOfHanoiView(Context context)
	{
		super(context);
		this.context = context;
		if(! this.isScanOnly)
		{
			this.sendMessage(FinalUtil.MOVEMSG,
					"moves",this.currentMoves);
			this.sendMessage(FinalUtil.TIMEMSG,
					"times",this.currentTimes);
		}
	}
	//构造函数
	public TowerOfHanoiView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		this.context = context;
		if(! this.isScanOnly)
		{
			this.sendMessage(FinalUtil.MOVEMSG,
					"moves",this.currentMoves);
			this.sendMessage(FinalUtil.TIMEMSG,
					"times",this.currentTimes);
		}
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) 
	{
		// TODO 自动生成的方法存根
		super.onLayout(changed, left, top, right, bottom);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		// TODO 自动生成的方法存根
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.width = this.getMeasuredWidth();
		this.height = this.getMeasuredHeight();

		/*this.tops = new Point[]{
				new Point(this.width / 4, this.height / 4),
				new Point(this.width / 2, this.height / 2),
				new Point(this.width * 3 / 4, this.height * 3 / 4)
		};*/
		/*this.circles = new Point[][]{
		new Point[]{
				new Point(this.tops[0].x,this.height * 7 / 8),
				new Point(this.tops[0].x,this.height * 5 / 8),
				new Point(this.tops[0].x,this.height * 3 / 8)
		},
		new Point[]{
				new Point(this.tops[1].x,this.height * 7 / 8),
				new Point(this.tops[1].x,this.height * 5 / 8)
		},
		new Point[]{
				new Point(this.tops[2].x,this.height * 7 / 8)
		}
		};*/
		for(int i = 0; i < this.tops.length; i ++)
		{
			this.tops[i].x = this.width * (i + 1) / 4;
			this.tops[i].y = this.height * (i + 1) / 4;
		}
		this.lines = new float[]{
				this.tops[0].x,this.tops[0].y,this.tops[0].x,this.height,
				this.tops[1].x,this.tops[1].y,this.tops[1].x,this.height,
				this.tops[2].x,this.tops[2].y,this.tops[2].x,this.height
		};
		this.r = (this.width > this.height ? this.height :
			this.width) / 4 - 20;
		this.r /= 2;
		
		for(int i = 0; i < this.circles.length; i ++)
		{
			for(int j = 0; j < this.circles[i].length; j ++)
			{
				this.circles[i][j].x = this.tops[i].x;
				this.circles[i][j].y = this.height *
						(7 - j * 2) / 8;
			}
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		if(this.isScanOnly)
			return false;
		this.finger_x = event.getX();
		this.finger_y = event.getY();
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			this.isMoving = this.isFingerTouch(finger_x, finger_y);
			if(this.isMoving && this.isFirstTouch)
			{
				this.isFirstTouch = false;
				this.sendEmptyMessage(FinalUtil.FIRSTMSG);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			if(this.isMoving &&
					this.isFingerArrive(this.finger_x,this.finger_y))
			{
				this.transferCircle();
			}
			this.isMoving = false;
			break;
		}
		invalidate();
		return true;
	}
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		
		canvas.drawLines(lines, this.pillarPaint);
		for(int i = 0; i < this.pillars.size(); i ++)
		{
			for(int j = 0; j < this.pillars.get(i).size();
					j ++)
			{
				this.circlePaint.setColor(this.pillars.get(i).get(j));
				canvas.drawCircle(this.circles[i][j].x,
						this.circles[i][j].y,
						this.r,
						this.circlePaint);
			}
		}
		
		if(isMoving)
		{
			canvas.drawCircle(this.finger_x, this.finger_y, 
					this.r + 10, 
					this.fingerPaint_out);
			canvas.drawCircle(this.finger_x, this.finger_y, 
					this.r, 
					this.fingerPaint);
		}
	}
	//是否点击了柱子顶部的小球
	private boolean isFingerTouch(float x, float y)
	{
		for(int i = 0; i < this.pillars.size(); i ++)
		{
			Stack<Integer> stack = this.pillars.get(i);
			if(! stack.empty())
			{
				Point temp = this.circles[i][stack.size() - 1];
				if(Math.pow(Math.abs(x - temp.x),2) +
						Math.pow(Math.abs(y - temp.y), 2) <=
						Math.pow(this.r, 2))
				{
					this.fromPillar = this.toPillar = i;
					this.fingerPaint.setColor(stack.pop());
					this.fingerPaint_out.setColor(
							this.fingerPaint.getColor());
					return true;
				}
			}
		}
		return false;
	}
	//是否把小球移动到了柱子顶部
	private boolean isFingerArrive(float x, float y)
	{
		for(int i = 0; i < this.tops.length; i ++)
		{
			if((this.circles[i].length > this.pillars.get(i).size())&&
					y >= this.tops[i].y - this.r &&
					Math.abs(x - this.tops[i].x) <= this.r)
			{
				this.toPillar = i;
				return true;
			}
		}
		this.pillars.get(this.fromPillar).push(
				this.fingerPaint.getColor());
		return false;
	}
	//小球的转移
	private void transferCircle()
	{
		this.pillars.get(this.toPillar).push(this.fingerPaint.getColor());
		if(this.fromPillar == this.toPillar)
			;//小球还是位置不变
		else
			//标注为一次移动
			this.sendMessage(FinalUtil.MOVEMSG,"moves",++ this.currentMoves);
		this.fromPillar = this.toPillar = -1;
		if(this.currentMoves == this.targetMoves[TowerOfHanoiView.currentProblem])
		{
			if(this.moveSuccess())
			{
				//规定步数移动正确
				this.sendMessage(FinalUtil.SUCCESSMSG,
						"problem",TowerOfHanoiView.currentProblem);
			}
		}
		else if(this.currentMoves > this.targetMoves[TowerOfHanoiView.currentProblem])
		{
			if(this.moveSuccess())
			{
				//移动步数正确，但是步数超出
				this.sendEmptyMessage(FinalUtil.FAILMSG);
			}
		}
	}
	//发送消息
	private void sendMessage(int what,String key,int data)
	{
		Message msg = new Message();
		msg.what = what;
		Bundle bundle = new Bundle();
		bundle.putInt(key, data);
		msg.setData(bundle);
		((TowerOfHanoiActivity)this.context)
		.handler.sendMessage(msg);
	}
	//发送空消息
	private void sendEmptyMessage(int what)
	{
		((TowerOfHanoiActivity)this.context)
		.handler.sendEmptyMessage(what);
	}
	//判断是否已经匹配
	private boolean moveSuccess()
	{
		int temp = TowerOfHanoiView.currentProblem * this.PILLAR_NUM
				% this.DATAS.length;
		for(int i = 0; i < this.pillars.size(); i ++)
		{
			if(this.pillars.get(i).size() != 
					this.DATAS[temp + i].length)
			{
				//跟目标柱子数目不相等，肯定错误
				return false;
			}
			for(int j = 0; j < this.pillars.get(i).size(); j ++)
			{
				if(this.pillars.get(i).get(j) != 
						this.DATAS[temp + i][j])
				{
					//数目相当，但是颜色不对，也是错误的
					return false;
				}
			}
		}
		return true;
	}
}
