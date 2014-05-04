/**
 * 
 */
package com.zhou.evaluatingsystem;

import com.zhou.model.Acoustic;
import com.zhou.model.Aos;
import com.zhou.model.Cued;
import com.zhou.model.Hanoi;
import com.zhou.model.Line;
import com.zhou.model.Mmse;
import com.zhou.model.Visual;
import com.zhou.model.Wisconsin;
import com.zhou.model.Word;
import com.zhou.program.AospanActivity;
import com.zhou.program.CuedGoNoGoActivity;
import com.zhou.program.DigitSpanAcousticActivity;
import com.zhou.program.DigitSpanVisualActivity;
import com.zhou.program.LineContactActivity;
import com.zhou.program.MmseActivity;
import com.zhou.program.TowerOfHanoiActivity;
import com.zhou.program.WisconsinActivity;
import com.zhou.program.WordColorInterferenceActivity;
import com.zhou.sqlite.EvalSysDatabaseHelper;
import com.zhou.util.FinalUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author ZHOU
 *
 */
public class EvaluationActivity extends Activity 
{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	private EvalSysDatabaseHelper dbHelper;
	//
	private TextView doctor_name_tv;
	private TextView patient_name_tv;
	private TextView patient_id_tv;
	private TextView patient_gender_tv;
	private TextView patient_birth_tv;
	private TextView patient_diagnose_tv;
	//
	private Button ad_test_mmse;
	private Button frontal_test_towerofhanoi;
	private Button frontal_test_linecontact;
	private Button frontal_test_wisconsin;
	private Button frontal_test_cued;
	private Button frontal_test_wordcolor;
	private Button memory_test_aospan;
	private Button memory_test_digitspanvisual;
	private Button memory_test_digitspanacoustic;
	private Class<?>[] programs = new Class<?>[]{
			MmseActivity.class,
			TowerOfHanoiActivity.class,
			LineContactActivity.class,
			WisconsinActivity.class,
			CuedGoNoGoActivity.class,
			WordColorInterferenceActivity.class,
			AospanActivity.class,
			DigitSpanVisualActivity.class,
			DigitSpanAcousticActivity.class
	};
	private int[] request_codes = new int[]{
		FinalUtil.MMSE_REQUEST_CODE,
		FinalUtil.HANOI_REQUEST_CODE,
		FinalUtil.LINE_REQUEST_CODE,
		FinalUtil.WISCONSIN_REQUEST_CODE,
		FinalUtil.CUED_REQUEST_CODE,
		FinalUtil.WORD_REQUEST_CODE,
		FinalUtil.AOSPAN_REQUEST_CODE,
		FinalUtil.DSV_REQUEST_CODE,
		FinalUtil.DSA_REQUEST_CODE
	};
	private int[] result_codes = new int[]{
			FinalUtil.MMSE_RESULT_CODE,
			FinalUtil.HANOI_RESULT_CODE,
			FinalUtil.LINE_RESULT_CODE,
			FinalUtil.WISCONSIN_RESULT_CODE,
			FinalUtil.CUED_RESULT_CODE,
			FinalUtil.WORD_RESULT_CODE,
			FinalUtil.AOSPAN_RESULT_CODE,
			FinalUtil.DSV_RESULT_CODE,
			FinalUtil.DSA_RESULT_CODE
		};
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.evaluation_layout);
		//将ActionBar上应用图标转换成可点击的按钮
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		//初始化
		this.dbHelper = new EvalSysDatabaseHelper(this);
		//
		this.doctor_name_tv = (TextView)findViewById(R.id.doctor_name_tv);
		this.patient_name_tv = (TextView)findViewById(R.id.patient_name_tv);
		this.patient_id_tv = (TextView)findViewById(R.id.patient_id_tv);
		this.patient_gender_tv = (TextView)findViewById(R.id.patient_gender_tv);
		this.patient_birth_tv = (TextView)findViewById(R.id.patient_birth_tv);
		this.patient_diagnose_tv = (TextView)findViewById(R.id.patient_diagnose_tv);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		this.doctor_name_tv.setText(bundle.getString("doctorname"));
		String id = bundle.getString("id");
		Cursor cursor = this.dbHelper.queryPatient(id);
		if(cursor.moveToFirst())
		{
			this.patient_name_tv.setText(cursor.getString(cursor.getColumnIndex("name")));
			this.patient_id_tv.setText(id);
			this.patient_gender_tv.setText(cursor.getString(cursor.getColumnIndex("gender")));
			this.patient_birth_tv.setText(cursor.getString(cursor.getColumnIndex("birth")));
			this.patient_diagnose_tv.setText(cursor.getString(cursor.getColumnIndex("diagnose")));
		}
		cursor.close();
		//
		this.ad_test_mmse = (Button)findViewById(R.id.ad_test_mmse);
		this.ad_test_mmse.setOnClickListener(
			new ProgramsClickListener(0));
		//
		this.frontal_test_towerofhanoi = (Button)
				findViewById(R.id.frontal_test_towerofhanoi);
		this.frontal_test_towerofhanoi.setOnClickListener(
			new ProgramsClickListener(1));
		//
		this.frontal_test_linecontact = (Button)
				findViewById(R.id.frontal_test_linecontact);
		this.frontal_test_linecontact.setOnClickListener(
			new ProgramsClickListener(2));
		//
		this.frontal_test_wisconsin = (Button)
				findViewById(R.id.frontal_test_wisconsin);
		this.frontal_test_wisconsin.setOnClickListener(
			new ProgramsClickListener(3));
		//
		this.frontal_test_cued = (Button)
				findViewById(R.id.frontal_test_cued);
		this.frontal_test_cued.setOnClickListener(
			new ProgramsClickListener(4));
		//
		this.frontal_test_wordcolor = (Button)
				findViewById(R.id.frontal_test_wordcolor);
		this.frontal_test_wordcolor.setOnClickListener(
			new ProgramsClickListener(5));
		//
		this.memory_test_aospan = (Button)
				findViewById(R.id.memory_test_aospan);
		this.memory_test_aospan.setOnClickListener(
			new ProgramsClickListener(6));
		//
		this.memory_test_digitspanvisual = (Button)
				findViewById(R.id.memory_test_digitspanvisual);
		this.memory_test_digitspanvisual.setOnClickListener(
			new ProgramsClickListener(7));
		//
		this.memory_test_digitspanacoustic = (Button)
				findViewById(R.id.memory_test_digitspanacoustic);
		this.memory_test_digitspanacoustic.setOnClickListener(
			new ProgramsClickListener(8));
	}
	private class ProgramsClickListener implements OnClickListener 
	{
		private int num;
		public ProgramsClickListener(int num)
		{
			this.num = num;
		}
		@Override
		public void onClick(View view) 
		{
			Intent intent = new Intent(EvaluationActivity.this, 
					programs[this.num]);
			EvaluationActivity.this.startActivityForResult(intent, request_codes[this.num]);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == FinalUtil.MMSE_REQUEST_CODE && 
				resultCode == FinalUtil.MMSE_RESULT_CODE)
		{
			Mmse mmse = (Mmse)data.getExtras().getSerializable("mmse");
			mmse.setHospital_id(this.patient_id_tv.getText().toString());
			mmse.setDoctor_name(this.doctor_name_tv.getText().toString());
			if(this.dbHelper.insertMmse(mmse))
			{
				
			}
		}
		else if(requestCode == FinalUtil.HANOI_REQUEST_CODE && 
				resultCode == FinalUtil.HANOI_RESULT_CODE)
		{
			Hanoi hanoi = (Hanoi)data.getExtras().getSerializable("hanoi");
			hanoi.setHospital_id(this.patient_id_tv.getText().toString());
			hanoi.setDoctor_name(this.doctor_name_tv.getText().toString());
			if(this.dbHelper.insertHanoi(hanoi))
			{
				
			}
		}
		else if(requestCode == FinalUtil.LINE_REQUEST_CODE && 
				resultCode == FinalUtil.LINE_RESULT_CODE)
		{
			Line line = (Line)data.getExtras().getSerializable("line");
			line.setHospital_id(this.patient_id_tv.getText().toString());
			line.setDoctor_name(this.doctor_name_tv.getText().toString());
			if(this.dbHelper.insertLine(line))
			{
				
			}
		}
		else if(requestCode == FinalUtil.WISCONSIN_REQUEST_CODE &&
				resultCode == FinalUtil.WISCONSIN_RESULT_CODE)
		{
			Wisconsin wisconsin = (Wisconsin)
					data.getExtras().getSerializable("wisconsin");
			wisconsin.setHospital_id(this.patient_id_tv.getText().toString());
			wisconsin.setDoctor_name(this.doctor_name_tv.getText().toString());
			if(this.dbHelper.insertWisconsin(wisconsin))
			{
				
			}
		}
		else if(requestCode == FinalUtil.CUED_REQUEST_CODE &&
				resultCode == FinalUtil.CUED_RESULT_CODE)
		{
			Cued cued = (Cued)data.getExtras().getSerializable("cued");
			cued.setHospital_id(this.patient_id_tv.getText().toString());
			cued.setDoctor_name(this.doctor_name_tv.getText().toString());
			if(this.dbHelper.insertCued(cued))
			{
				
			}
		}
		else if(requestCode == FinalUtil.WORD_REQUEST_CODE &&
				resultCode == FinalUtil.WORD_RESULT_CODE)
		{
			Word word = (Word)data.getExtras().getSerializable("word");
			word.setHospital_id(this.patient_id_tv.getText().toString());
			word.setDoctor_name(this.doctor_name_tv.getText().toString());
			if(this.dbHelper.insertWord(word))
			{
				
			}
		}
		else if(requestCode == FinalUtil.AOSPAN_REQUEST_CODE &&
				resultCode == FinalUtil.AOSPAN_RESULT_CODE)
		{
			Aos aos = (Aos)data.getExtras().getSerializable("aospan");
			aos.setHospital_id(this.patient_id_tv.getText().toString());
			aos.setDoctor_name(this.doctor_name_tv.getText().toString());
			if(this.dbHelper.insertAospan(aos))
			{
				
			}
		}
		else if(requestCode == FinalUtil.DSV_REQUEST_CODE &&
				resultCode == FinalUtil.DSV_RESULT_CODE)
		{
			Visual visual = (Visual)data.getExtras().getSerializable("visual");
			visual.setHospital_id(this.patient_id_tv.getText().toString());
			visual.setDoctor_name(this.doctor_name_tv.getText().toString());
			if(this.dbHelper.insertVisual(visual))
			{
				
			}
		}
		else if(requestCode == FinalUtil.DSA_REQUEST_CODE &&
				resultCode == FinalUtil.DSA_RESULT_CODE)
		{
			Acoustic acoustic = (Acoustic)data.getExtras().getSerializable("acoustic");
			acoustic.setHospital_id(this.patient_id_tv.getText().toString());
			acoustic.setDoctor_name(this.doctor_name_tv.getText().toString());
			if(this.dbHelper.insertAcoustic(acoustic))
			{
				            
			}
		}
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
	@Override
	protected void onDestroy() 
	{
		// TODO 自动生成的方法存根
		if(this.dbHelper != null)
		{
			this.dbHelper.close();
		}
		super.onDestroy();
	}
}
