/**
 * 
 */
package com.zhou.evaluatingsystem;

import com.zhou.sqlite.EvalSysDatabaseHelper;
import com.zhou.view.SpecialListView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.MenuItem;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

/**
 * @author ZHOU
 *
 */
public class CheckResultActivity extends Activity 
{
	private SpecialListView type_1_1_list;
	private SpecialListView type_2_1_list;
	private SpecialListView type_2_2_list;
	private SpecialListView type_2_3_list;
	private SpecialListView type_2_4_list;
	private SpecialListView type_2_5_list;
	private SpecialListView type_3_1_list;
	private SpecialListView type_3_2_list;
	private SpecialListView type_3_3_list;
	//
	private EvalSysDatabaseHelper dbHelper;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		// TODO 自动生成的方法存根
		this.setContentView(R.layout.checkresult_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		//初始化
		this.type_1_1_list = (SpecialListView)
				findViewById(R.id.type_1_1_list);
		this.type_2_1_list = (SpecialListView)
				findViewById(R.id.type_2_1_list);
		this.type_2_2_list = (SpecialListView)
				findViewById(R.id.type_2_2_list);
		this.type_2_3_list = (SpecialListView)
				findViewById(R.id.type_2_3_list);
		this.type_2_4_list = (SpecialListView)
				findViewById(R.id.type_2_4_list);
		this.type_2_5_list = (SpecialListView)
				findViewById(R.id.type_2_5_list);
		this.type_3_1_list = (SpecialListView)
				findViewById(R.id.type_3_1_list);
		this.type_3_2_list = (SpecialListView)
				findViewById(R.id.type_3_2_list);
		this.type_3_3_list = (SpecialListView)
				findViewById(R.id.type_3_3_list);
		//
		this.dbHelper = new EvalSysDatabaseHelper(this);
		//
		this.type_1_1_list.setAdapter(this.getType11Adapter());
		this.type_2_1_list.setAdapter(this.getType21Adatper());
		this.type_2_2_list.setAdapter(this.getType22Adapter());
		this.type_2_3_list.setAdapter(this.getType23Adapter());
		this.type_2_4_list.setAdapter(this.getType24Adapter());
		this.type_2_5_list.setAdapter(this.getType25Adapter());
		this.type_3_1_list.setAdapter(this.getType31Adapter());
		this.type_3_2_list.setAdapter(this.getType32Adapter());
		this.type_3_3_list.setAdapter(this.getType33Adapter());
	}
	private SimpleCursorAdapter getType11Adapter()
	{
		return new SimpleCursorAdapter(this,
				R.layout.type_1_1_layout,
				this.dbHelper.queryAllMmse(),
				new String[]{
			"doctor_name","evaluate_date",
			"evaluate_starttime","evaluate_endtime",
			"score","awareness_state"
		},new int[]{
			R.id.type_1_1_doctor,
			R.id.type_1_1_eval_date,
			R.id.type_1_1_eval_start,
			R.id.type_1_1_eval_end,
			R.id.type_1_1_score,
			R.id.type_1_1_state
		},CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	}
	private SimpleCursorAdapter getType21Adatper()
	{
		return new SimpleCursorAdapter(this,
				R.layout.type_2_1_layout,
				this.dbHelper.queryAllHanoi(),
				new String[]{
			"doctor_name","evaluate_date",
			"evaluate_starttime","evaluate_endtime",
			"score"
		},new int[]{
			R.id.type_2_1_doctor,
			R.id.type_2_1_eval_date,
			R.id.type_2_1_eval_start,
			R.id.type_2_1_eval_end,
			R.id.type_2_1_score
		},CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	}
	private SimpleCursorAdapter getType22Adapter()
	{
		return new SimpleCursorAdapter(this,
				R.layout.type_2_2_layout,
				this.dbHelper.queryAllLine(),
				new String[]{
			"doctor_name","evaluate_date",
			"evaluate_starttime","evaluate_endtime",
			"simple_a_error","simple_b_error"
		},new int[]{
			R.id.type_2_2_doctor,
			R.id.type_2_2_eval_date,
			R.id.type_2_2_eval_start,
			R.id.type_2_2_eval_end,
			R.id.type_2_2_simpleaerror,
			R.id.type_2_2_simpleberror
		},CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	}
	private SimpleCursorAdapter getType23Adapter()
	{
		return new SimpleCursorAdapter(this,
				R.layout.type_2_3_layout,
				this.dbHelper.queryAllWisconsin(),
				new String[]{
			"doctor_name","evaluate_date",
			"evaluate_starttime","evaluate_endtime",
			"color1","form1","number1",
			"color2","form2","number2"
		},new int[]{
			R.id.type_2_3_doctor,
			R.id.type_2_3_eval_date,
			R.id.type_2_3_eval_start,
			R.id.type_2_3_eval_end,
			R.id.type_2_3_color1,
			R.id.type_2_3_form1,
			R.id.type_2_3_number1,
			R.id.type_2_3_color2,
			R.id.type_2_3_form2,
			R.id.type_2_3_number2
		},CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	}
	private SimpleCursorAdapter getType24Adapter()
	{
		return new SimpleCursorAdapter(this,
				R.layout.type_2_4_layout,
				this.dbHelper.queryAllCued(),
				new String[]{
			"doctor_name","evaluate_date",
			"evaluate_starttime","evaluate_endtime",
			"correct","error"
		},new int[]{
			R.id.type_2_4_doctor,
			R.id.type_2_4_eval_date,
			R.id.type_2_4_eval_start,
			R.id.type_2_4_eval_end,
			R.id.type_2_4_correct,
			R.id.type_2_4_error
		},CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	}
	private SimpleCursorAdapter getType25Adapter()
	{
		return new SimpleCursorAdapter(this,
				R.layout.type_2_5_layout,
				this.dbHelper.queryAllWord(),
				new String[]{
			"doctor_name","evaluate_date",
			"evaluate_starttime","evaluate_endtime",
			"correct","error"
		},new int[]{
			R.id.type_2_5_doctor,
			R.id.type_2_5_eval_date,
			R.id.type_2_5_eval_start,
			R.id.type_2_5_eval_end,
			R.id.type_2_5_correct,
			R.id.type_2_5_error
		},CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	}
	private SimpleCursorAdapter getType31Adapter()
	{
		return new SimpleCursorAdapter(this,
				R.layout.type_3_1_layout,
				this.dbHelper.queryAllAospan(),
				new String[]{
			"doctor_name","evaluate_date",
			"evaluate_starttime","evaluate_endtime",
			"ospan_absolute_score","ospan_total_correct",
			"math_total_errors","math_speed_errors",
			"math_accuracy_errors"
		},new int[]{
			R.id.type_3_1_doctor,
			R.id.type_3_1_eval_date,
			R.id.type_3_1_eval_start,
			R.id.type_3_1_eval_end,
			R.id.type_3_1_ospanabsolute,
			R.id.type_3_1_ospancorrect,
			R.id.type_3_1_matherror,
			R.id.type_3_1_mathspeed,
			R.id.type_3_1_mathaccuracy
		},CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	}
	private SimpleCursorAdapter getType32Adapter()
	{
		return new SimpleCursorAdapter(this,
				R.layout.type_3_2_layout,
				this.dbHelper.queryAllVisual(),
				new String[]{
			"doctor_name","evaluate_date",
			"evaluate_starttime","evaluate_endtime",
			"forward_TE_ML","forward_TE_TT",
			"forward_ML","forward_MS",
			"backward_TE_ML","backward_TE_TT",
			"backward_ML","backward_MS"
		},new int[]{
			R.id.type_3_2_doctor,
			R.id.type_3_2_eval_date,
			R.id.type_3_2_eval_start,
			R.id.type_3_2_eval_end,
			R.id.type_3_2_fteml,
			R.id.type_3_2_ftett,
			R.id.type_3_2_fml,
			R.id.type_3_2_fms,
			R.id.type_3_2_bteml,
			R.id.type_3_2_btett,
			R.id.type_3_2_bml,
			R.id.type_3_2_bms
		},CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	}
	private SimpleCursorAdapter getType33Adapter()
	{
		return new SimpleCursorAdapter(this,
				R.layout.type_3_3_layout,
				this.dbHelper.queryAllAcoustic(),
				new String[]{
			"doctor_name","evaluate_date",
			"evaluate_starttime","evaluate_endtime",
			"forward_TE_ML","forward_TE_TT",
			"forward_ML","forward_MS",
			"backward_TE_ML","backward_TE_TT",
			"backward_ML","backward_MS"
		},new int[]{
			R.id.type_3_3_doctor,
			R.id.type_3_3_eval_date,
			R.id.type_3_3_eval_start,
			R.id.type_3_3_eval_end,
			R.id.type_3_3_fteml,
			R.id.type_3_3_ftett,
			R.id.type_3_3_fml,
			R.id.type_3_3_fms,
			R.id.type_3_3_bteml,
			R.id.type_3_3_btett,
			R.id.type_3_3_bml,
			R.id.type_3_3_bms
		},CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
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
