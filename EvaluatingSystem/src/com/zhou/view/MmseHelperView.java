package com.zhou.view;


import com.zhou.evaluatingsystem.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MmseHelperView extends View 
{
	private Context context;
	private Paint paint;
	private Canvas cacheCanvas;
	private Bitmap cacheBimap;
	private Bitmap showBitmap;
	private int width;
	private int height;
	private float preX;
	private float preY;
	private float x;
	private float y;
	private Path path;
	//
	{
		this.paint = new Paint();
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setStrokeWidth(6);
		this.cacheCanvas = new Canvas();
		this.path = new Path();
	}
	public MmseHelperView(Context context)
	{
		super(context);
		this.context = context;
		this.showBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.mmsepic);
	}
	public MmseHelperView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		this.context = context;
		this.showBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.mmsepic);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		// TODO 自动生成的方法存根
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.width = this.getMeasuredWidth();
		this.height = this.getMeasuredHeight();
		this.cacheBimap = Bitmap.createBitmap(
				this.width,this.height,Config.ARGB_8888);
		this.cacheCanvas.setBitmap(this.cacheBimap);
		this.cacheCanvas.drawColor(Color.WHITE);
		this.cacheCanvas.drawBitmap(this.showBitmap, 0, 0,
				this.paint);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		// TODO 自动生成的方法存根
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			this.preX = this.x = event.getX();
			this.preY = this.y = event.getY();
			this.path.moveTo(this.x, this.y);
			break;
		case MotionEvent.ACTION_MOVE:
			this.preX = this.x;
			this.preY = this.y;
			this.x = event.getX();
			this.y = event.getY();
			this.path.quadTo(this.preX, this.preY, this.x, this.y);
			/*this.cacheCanvas.drawLine(this.preX,this.preY,
					x,y,this.paint);*/
			this.cacheCanvas.drawPath(this.path, this.paint);
			break;
		case MotionEvent.ACTION_UP:
			this.path.reset();
			break;
		}
		this.invalidate();
		return true;
	}
	@Override
	protected void onDraw(Canvas canvas) 
	{
		// TODO 自动生成的方法存根
		super.onDraw(canvas);
		canvas.drawBitmap(this.cacheBimap,0,0,this.paint);
	}
	
}
