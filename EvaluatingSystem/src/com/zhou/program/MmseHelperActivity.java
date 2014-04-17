/**
 * 
 */
package com.zhou.program;

import com.zhou.evaluatingsystem.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * @author ZHOU
 *
 */
public class MmseHelperActivity extends Activity 
{
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		// TODO 自动生成的方法存根
		this.setContentView(R.layout.mmsehelper_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		// TODO 自动生成的方法存根
		super.onConfigurationChanged(newConfig);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if(item.getItemId() == android.R.id.home)
		{
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
