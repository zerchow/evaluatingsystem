package com.zhou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class SpecialListView extends ListView 
{
	public SpecialListView(Context context)
	{
		super(context);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) 
	{
		// TODO 自动生成的方法存根
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,    
                MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	public SpecialListView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		// TODO 自动生成的构造函数存根
	}
}
