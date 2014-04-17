package com.zhou.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.zhou.util.FinalUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CuedGoNoGoView extends View 
{
	//
	private final int[] SOA = 
		{
			100,100,100,100,100,200,200,200,200,200,300,300,300,300,300,400,400,400,400,400,500,500,500,500,500,
			100,100,100,100,100,200,200,200,200,200,300,300,300,300,300,400,400,400,400,400,500,500,500,500,500,
			100,100,100,100,100,200,200,200,200,200,300,300,300,300,300,400,400,400,400,400,500,500,500,500,500,
			100,100,100,100,100,200,200,200,200,200,300,300,300,300,300,400,400,400,400,400,500,500,500,500,500,
			100,100,100,100,100,200,200,200,200,200,300,300,300,300,300,400,400,400,400,400,500,500,500,500,500,
			100,100,100,100,100,200,200,200,200,200,300,300,300,300,300,400,400,400,400,400,500,500,500,500,500,
			100,100,100,100,100,200,200,200,200,200,300,300,300,300,300,400,400,400,400,400,500,500,500,500,500,
			100,100,100,100,100,200,200,200,200,200,300,300,300,300,300,400,400,400,400,400,500,500,500,500,500,
			100,100,100,100,100,200,200,200,200,200,300,300,300,300,300,400,400,400,400,400,500,500,500,500,500,
			100,100,100,100,100,200,200,200,200,200,300,300,300,300,300,400,400,400,400,400,500,500,500,500,500
		};
	private final int[] CUE = 
		{
			FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,
			FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,
			FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,
			FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,
			FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,FinalUtil.CUEDVERTICAL,
			FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,
			FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,
			FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,
			FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,
			FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL,FinalUtil.CUEDHORIZONTAL
		};
	private final int[] TARGET = 
		{
			FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,
			FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,
			FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,
			FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,
			FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,
			FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,
			FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,
			FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,
			FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,
			FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDBLUE,FinalUtil.CUEDGREEN
		};
	private final boolean[] CORRECTRESP = 
		{
			true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,
			true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,
			true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,
			true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,
			true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,true,true,true,true,false,
			false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,
			false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,
			false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,
			false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,
			false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,false,false,false,false,true,false,false,false,false,true
		};
	private List<Integer> soalist;
	private List<Integer> cuelist;
	private List<Integer> targetlist;
	private List<Boolean> resplist;
	//
	private boolean isUserCanDo;
	private boolean isUserDo;
	private int width;
	private int height;
	private Rect verticalRect;
	private Rect horizontalRect;
	private float[] fixationLines;
	private Paint framePaint;
	private Paint colorPaint;
	//
	private int currentCue;
	private int currentTarget;
	private boolean currentResp;
	private Rect currentFrame;
	//
	private boolean isFixation;
	private boolean isFrame;
	private boolean isColorFrame;
	//
	{
		this.soalist = new ArrayList<Integer>();
		this.cuelist = new ArrayList<Integer>();
		this.targetlist = new ArrayList<Integer>();
		this.resplist = new ArrayList<Boolean>();
		for(int i = 0; i < this.SOA.length; i ++)
		{
			this.soalist.add(this.SOA[i]);
			this.cuelist.add(this.CUE[i]);
			this.targetlist.add(this.TARGET[i]);
			this.resplist.add(this.CORRECTRESP[i]);
		}
		//
		this.verticalRect = new Rect();
		this.horizontalRect = new Rect();
		this.fixationLines = new float[8];
		this.framePaint = new Paint();
		this.framePaint.setStyle(Paint.Style.STROKE);
		this.framePaint.setStrokeWidth(6);
		this.colorPaint = new Paint();
		this.isUserCanDo = false;
		this.isUserDo = false;
		//
		this.isFixation = false;
		this.isFrame = false;
		this.isColorFrame = false;
	}
	//
	private Context context;
	public CuedGoNoGoView(Context context)
	{
		super(context);
		this.context = context;
	}
	public CuedGoNoGoView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		this.context = context;
	}
	public void setWhileScreen() 
	{
		this.isFixation = false;
		this.isFrame = false;
		this.isColorFrame = false;
		this.invalidate();
	}
	public void setFixation() 
	{
		this.isFixation = true;
		this.isFrame = false;
		this.isColorFrame = false;
		this.invalidate();
	}
	public void setFrame() 
	{
		this.isFixation = false;
		this.isFrame = true;
		this.isColorFrame = false;
		this.invalidate();
	}
	public void setColorFrame() 
	{
		this.isFixation = false;
		this.isFrame = true;
		this.isColorFrame = true;
		this.invalidate();
	}
	public boolean hasNext() 
	{
		return ! this.soalist.isEmpty();
	}
	public int getNext() 
	{
		Random random = new Random(System.currentTimeMillis());
		int index = random.nextInt(this.soalist.size());
		this.currentCue = this.cuelist.remove(index);
		this.currentTarget = this.targetlist.remove(index);
		this.currentResp = this.resplist.remove(index);
		if(this.currentCue == FinalUtil.CUEDVERTICAL)
		{
			this.currentFrame = this.verticalRect;
		}
		else if(this.currentCue == FinalUtil.CUEDHORIZONTAL)
		{
			this.currentFrame = this.horizontalRect;
		}
		if(this.currentTarget == FinalUtil.CUEDBLUE)
		{
			this.colorPaint.setColor(Color.BLUE);
		}
		else if(this.currentTarget == FinalUtil.CUEDGREEN)
		{
			this.colorPaint.setColor(Color.GREEN);
		}
		return this.soalist.remove(index);
	}
	public void createUser() 
	{
		this.isUserCanDo = true;
	}
	public void cancelUser() 
	{
		this.isUserCanDo = false;
	}
	public boolean result() 
	{
		return this.isUserDo == this.currentResp;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		if(this.isUserCanDo == false)
		{
			return true;
		}
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			this.isUserDo = true;
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return true;
	}
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		if(this.isFixation)
		{
			canvas.drawLines(this.fixationLines,
					this.framePaint);
		}
		if(this.isFrame)
		{
			canvas.drawRect(this.currentFrame,
					this.framePaint);
		}
		if(this.isColorFrame)
		{
			canvas.drawRect(this.currentFrame,
					this.colorPaint);
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.width = this.getMeasuredWidth();
		this.height = this.getMeasuredHeight();
		//
		int center_x = this.width / 2;
		int center_y = this.height / 2;
		int short_length = ((this.width > this.height) ?
				this.height : this.width) / 3;
		int long_length = ((this.width > this.height) ? 
				this.height : this.width) - 20;
		//
		int top = center_y - short_length / 2;
		int bottom = center_y + short_length / 2;
		int left = center_x - short_length / 2;
		int right = center_x + short_length / 2;
		int toptop = center_y - long_length / 2;
		int bottombottom = center_y + long_length / 2;
		int leftleft = center_x - long_length / 2;
		int rightright = center_x + long_length / 2;
		//十字架坐标
		this.fixationLines[0] = left;this.fixationLines[1] = center_y;
		this.fixationLines[2] = right;this.fixationLines[3] = center_y;
		this.fixationLines[4] = center_x;this.fixationLines[5] = top;
		this.fixationLines[6] = center_x;this.fixationLines[7] = bottom;
		//垂直空框
		this.verticalRect.left = left;
		this.verticalRect.top = toptop;
		this.verticalRect.right = right;
		this.verticalRect.bottom = bottombottom;
		//水平空框
		this.horizontalRect.left = leftleft;
		this.horizontalRect.top = top;
		this.horizontalRect.right = rightright;
		this.horizontalRect.bottom = bottom;
	}
}
