/**
 * 
 */
package com.zhou.program;

import java.util.Stack;

import com.zhou.evaluatingsystem.R;
import com.zhou.util.FinalUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author ZHOU
 *
 */
public class AospanSolverActivity extends Activity 
{
	private TextView select_tv;
	private Stack<String> selected;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.aospansolver_layout);
		// TODO 自动生成的方法存根
		this.select_tv = (TextView)findViewById(R.id.select_tv);
		this.selected = new Stack<String>();
	}
	public void userClick(View view)
	{
		Button button = (Button)view;
		if(button.getId() == R.id.clear_bt)
		{
			if(this.selected.isEmpty())
				return;
			this.selected.pop();
		}
		else if(button.getId() == R.id.mark_bt)
		{
			this.selected.push("_");
		}
		else
		{
			this.selected.push(button.getText().toString());
		}
		this.setSelected();
	}
	public void submit(View view)
	{
		Intent intent = this.getIntent();
		Bundle bundle = new Bundle();
		bundle.putString("userSelect", this.select_tv.getText().toString());
		intent.putExtras(bundle);
		this.setResult(FinalUtil.AOSPAN_RESULT, intent);
		this.finish();
	}
	private void setSelected()
	{
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < this.selected.size(); i ++)
		{
			str.append(this.selected.get(i));
		}
		this.select_tv.setText(str.toString());
	}
	@Override
	public void onBackPressed() 
	{
		Intent intent = this.getIntent();
		Bundle bundle = new Bundle();
		bundle.putString("userSelect", this.select_tv.getText().toString());
		intent.putExtras(bundle);
		this.setResult(FinalUtil.AOSPAN_RESULT, intent);
		this.finish();
	}
}
