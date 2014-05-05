/**
 * 
 */
package com.zhou.program;

import java.util.Calendar;

import com.zhou.evaluatingsystem.R;
import com.zhou.model.Mmse;
import com.zhou.util.FinalUtil;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * @author ZHOU
 *
 */
public class MmseActivity extends Activity 
{
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private RatingBar mmse_pro_01;
	private RatingBar mmse_pro_02;
	private RatingBar mmse_pro_03;
	private RatingBar mmse_pro_04;
	private RatingBar mmse_pro_05;
	private RatingBar mmse_pro_06;
	private RatingBar mmse_pro_07;
	private RatingBar mmse_pro_08;
	private RatingBar mmse_pro_09;
	private RatingBar mmse_pro_10;
	private RatingBar mmse_pro_11;
	private Spinner mmse_pro_12;
	private Button mmse_pro_submit;
	private String[] mmse_choose;
	//
	private Calendar startTime = null;
	private Calendar endTime = null;
	//
	private Mmse mmse = new Mmse();
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mmse_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		//初始化
		this.mmse_pro_01 = (RatingBar)findViewById(
				R.id.mmse_pro_01);
		this.mmse_pro_02 = (RatingBar)findViewById(
				R.id.mmse_pro_02);
		this.mmse_pro_03 = (RatingBar)findViewById(
				R.id.mmse_pro_03);
		this.mmse_pro_04 = (RatingBar)findViewById(
				R.id.mmse_pro_04);
		this.mmse_pro_05 = (RatingBar)findViewById(
				R.id.mmse_pro_05);
		this.mmse_pro_06 = (RatingBar)findViewById(
				R.id.mmse_pro_06);
		this.mmse_pro_07 = (RatingBar)findViewById(
				R.id.mmse_pro_07);
		this.mmse_pro_08 = (RatingBar)findViewById(
				R.id.mmse_pro_08);
		this.mmse_pro_09 = (RatingBar)findViewById(
				R.id.mmse_pro_09);
		this.mmse_pro_10 = (RatingBar)findViewById(
				R.id.mmse_pro_10);
		this.mmse_pro_11 = (RatingBar)findViewById(
				R.id.mmse_pro_11);
		this.mmse_pro_12 = (Spinner)findViewById(
				R.id.mmse_pro_12);
		this.mmse_pro_submit = (Button)findViewById(
				R.id.mmse_pro_submit);
		this.mmse_choose = this.getResources().getStringArray(
				R.array.mmse_choose);
		//绑定监听器
		this.mmse_pro_submit.setOnClickListener(
				new submitListener());
		//
		this.startTime = Calendar.getInstance();
	}
	class submitListener implements OnClickListener
	{
		public void onClick(View view)
		{
			float score = 0.0f;
			score += mmse_pro_01.getRating();
			score += mmse_pro_02.getRating();
			score += mmse_pro_03.getRating();
			score += mmse_pro_04.getRating();
			score += mmse_pro_05.getRating();
			score += mmse_pro_06.getRating();
			score += mmse_pro_07.getRating();
			score += mmse_pro_08.getRating();
			score += mmse_pro_09.getRating();
			score += mmse_pro_10.getRating();
			score += mmse_pro_11.getRating();
			//totalmillisecond,totalstarttime,totalendtime,date
			endTime = Calendar.getInstance();
			mmse.setEvaluate_date(FinalUtil.getCurrentDateString(startTime));
			mmse.setEvaluate_starttime(FinalUtil.getCurrentTimeString(startTime));
			mmse.setEvaluate_endtime(FinalUtil.getCurrentTimeString(endTime));
			mmse.setEvaluate_millisecond(FinalUtil.getTimeDiff(startTime, endTime));
			//
			String awareness_state = mmse_choose[
					mmse_pro_12.getSelectedItemPosition()];
			mmse.setAwareness_state(awareness_state);
			mmse.setScore((int)score);
			FinalUtil.getDialog(MmseActivity.this,
					"结果",false)
			.setMessage("分数：" + (int)score + "，" + awareness_state)
			.setPositiveButton("确定",
			new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					Intent intent = getIntent();
					Bundle bundle = new Bundle();
					bundle.putSerializable("mmse", mmse);
					intent.putExtras(bundle);
					setResult(FinalUtil.MMSE_RESULT_CODE, intent);
					finish();
				}
			}).create().show();
		}
	}
	public void onstartdrawing(View view)
	{
		Intent intent = new Intent(this,MmseHelperActivity.class);
		this.startActivity(intent);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if(item.getItemId() == android.R.id.home)
		{
			/*Intent intent = new Intent(this,MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);*/
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		// TODO 自动生成的方法存根
		super.onConfigurationChanged(newConfig);
	}
}
