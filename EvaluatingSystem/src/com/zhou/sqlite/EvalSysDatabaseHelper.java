package com.zhou.sqlite;

import com.zhou.model.Acoustic;
import com.zhou.model.Aos;
import com.zhou.model.Cued;
import com.zhou.model.Hanoi;
import com.zhou.model.Line;
import com.zhou.model.Mmse;
import com.zhou.model.Patient;
import com.zhou.model.Visual;
import com.zhou.model.Wisconsin;
import com.zhou.model.Word;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EvalSysDatabaseHelper extends SQLiteOpenHelper 
{
	private static final String DB_NAME = "evalsys.db3";
	private final String CREATE_PATIENT = 
			"create table patient"
			+ "("
			+ "_id primary key,"
			+ "name,"
			+ "gender,"
			+ "birth,"
			+ "diagnose"
			+ ")";
	private final String CREATE_MMSE = 
			"create table mmse"
			+ "("
			+ "_id integer primary key autoincrement,"
			+ "hospital_id,"
			+ "doctor_name,"
			+ "evaluate_date,"
			+ "evaluate_starttime,"
			+ "evaluate_endtime,"
			+ "evaluate_millisecond,"
			+ "score,"
			+ "awareness_state,"
			+ "foreign key(hospital_id) references patient(_id) on delete cascade on update cascade"
			+ ")";
	private final String CREATE_HANOI = 
			"create table hanoi"
			+ "("
			+ "_id integer primary key autoincrement,"
			+ "hospital_id,"
			+ "doctor_name,"
			+ "evaluate_date,"
			+ "evaluate_starttime,"
			+ "evaluate_endtime,"
			+ "evaluate_millisecond,"
			+ "prac_starttime,"
			+ "prac_endtime,"
			+ "prac_millisecond,"
			+ "test_starttime,"
			+ "test_endtime,"
			+ "test_millisecond,"
			+ "score,"
			+ "foreign key(hospital_id) references patient(_id) on delete cascade on update cascade"
			+ ")";
	private final String CREATE_LINE = 
			"create table line"
			+ "("
			+ "_id integer primary key autoincrement,"
			+ "hospital_id,"
			+ "doctor_name,"
			+ "evaluate_date,"
			+ "evaluate_starttime,"
			+ "evaluate_endtime,"
			+ "evaluate_millisecond,"
			+ "prac_starttime,"
			+ "prac_endtime,"
			+ "prac_millisecond,"
			+ "test_starttime,"
			+ "test_endtime,"
			+ "test_millisecond,"
			+ "simple_a_error,"
			+ "simple_b_error,"
			+ "foreign key(hospital_id) references patient(_id) on delete cascade on update cascade"
			+ ")";
	private final String CREATE_WISCONSIN = 
			"create table wisconsin"
			+ "("
			+ "_id integer primary key autoincrement,"
			+ "hospital_id,"
			+ "doctor_name,"
			+ "evaluate_date,"
			+ "evaluate_starttime,"
			+ "evaluate_endtime,"
			+ "evaluate_millisecond,"
			+ "color1,"
			+ "color1starttime,"
			+ "color1endtime,"
			+ "color1millisecond,"
			+ "form1,"
			+ "form1starttime,"
			+ "form1endtime,"
			+ "form1millisecond,"
			+ "number1,"
			+ "number1starttime,"
			+ "number1endtime,"
			+ "number1millisecond,"
			+ "color2,"
			+ "color2starttime,"
			+ "color2endtime,"
			+ "color2millisecond,"
			+ "form2,"
			+ "form2starttime,"
			+ "form2endtime,"
			+ "form2millisecond,"
			+ "number2,"
			+ "number2starttime,"
			+ "number2endtime,"
			+ "number2millisecond,"
			+ "foreign key(hospital_id) references patient(_id) on delete cascade on update cascade"
			+ ")";
	private final String CREATE_CUED = 
			"create table cued"
			+ "("
			+ "_id integer primary key autoincrement,"
			+ "hospital_id,"
			+ "doctor_name,"
			+ "evaluate_date,"
			+ "evaluate_starttime,"
			+ "evaluate_endtime,"
			+ "evaluate_millisecond,"
			+ "starttime,"
			+ "endtime,"
			+ "millisecond,"
			+ "correct,"
			+ "error,"
			+ "foreign key(hospital_id) references patient(_id) on delete cascade on update cascade"
			+ ")";
	private final String CREATE_WORDCOLOR = 
			"create table word"
			+ "("
			+ "_id integer primary key autoincrement,"
			+ "hospital_id,"
			+ "doctor_name,"
			+ "evaluate_date,"
			+ "evaluate_starttime,"
			+ "evaluate_endtime,"
			+ "evaluate_millisecond,"
			+ "starttime,"
			+ "endtime,"
			+ "millisecond,"
			+ "correct,"
			+ "error,"
			+ "foreign key(hospital_id) references patient(_id) on delete cascade on update cascade"
			+ ")";
	private final String CREATE_AOSPAN = 
			"create table aospan"
			+ "("
			+ "_id integer primary key autoincrement,"
			+ "hospital_id,"
			+ "doctor_name,"
			+ "evaluate_date,"
			+ "evaluate_starttime,"
			+ "evaluate_endtime,"
			+ "evaluate_millisecond,"
			+ "pracletter_starttime,"
			+ "pracletter_endtime,"
			+ "pracletter_millisecond,"
			+ "pracmath_starttime,"
			+ "pracmath_endtime,"
			+ "pracmath_millisecond,"
			+ "pracboth_starttime,"
			+ "pracboth_endtime,"
			+ "pracboth_millisecond,"
			+ "testboth_starttime,"
			+ "testboth_endtime,"
			+ "testboth_millisecond,"
			+ "ospan_absolute_score,"
			+ "ospan_total_correct,"
			+ "math_total_errors,"
			+ "math_speed_errors,"
			+ "math_accuracy_errors,"
			+ "foreign key(hospital_id) references patient(_id) on delete cascade on update cascade"
			+ ")";
	private final String CREATE_DSVISUAL = 
			"create table dsvisual"
			+ "("
			+ "_id integer primary key autoincrement,"
			+ "hospital_id,"
			+ "doctor_name,"
			+ "evaluate_date,"
			+ "evaluate_starttime,"
			+ "evaluate_endtime,"
			+ "evaluate_millisecond,"
			+ "pracf_starttime,"
			+ "pracf_endtime,"
			+ "pracf_millisecond,"
			+ "testf_starttime,"
			+ "testf_endtime,"
			+ "testf_millisecond,"
			+ "pracb_starttime,"
			+ "pracb_endtime,"
			+ "pracb_millisecond,"
			+ "testb_starttime,"
			+ "testb_endtime,"
			+ "testb_millisecond,"
			+ "forward_TE_ML,"
			+ "forward_TE_TT,"
			+ "forward_ML,"
			+ "forward_MS,"
			+ "backward_TE_ML,"
			+ "backward_TE_TT,"
			+ "backward_ML,"
			+ "backward_MS,"
			+ "foreign key(hospital_id) references patient(_id) on delete cascade on update cascade"
			+ ")";
	private final String CREATE_DSACOUSTIC = 
			"create table dsacoustic"
			+ "("
			+ "_id integer primary key autoincrement,"
			+ "hospital_id,"
			+ "doctor_name,"
			+ "evaluate_date,"
			+ "evaluate_starttime,"
			+ "evaluate_endtime,"
			+ "evaluate_millisecond,"
			+ "pracf_starttime,"
			+ "pracf_endtime,"
			+ "pracf_millisecond,"
			+ "testf_starttime,"
			+ "testf_endtime,"
			+ "testf_millisecond,"
			+ "pracb_starttime,"
			+ "pracb_endtime,"
			+ "pracb_millisecond,"
			+ "testb_starttime,"
			+ "testb_endtime,"
			+ "testb_millisecond,"
			+ "forward_TE_ML,"
			+ "forward_TE_TT,"
			+ "forward_ML,"
			+ "forward_MS,"
			+ "backward_TE_ML,"
			+ "backward_TE_TT,"
			+ "backward_ML,"
			+ "backward_MS,"
			+ "foreign key(hospital_id) references patient(_id) on delete cascade on update cascade"
			+ ")";
	public EvalSysDatabaseHelper(Context context)
	{
		super(context,DB_NAME,null,1);
	}
	/*
	 * 
	 */
	public Cursor queryPatient(String hospital_id)
	{
		return this.getReadableDatabase()
				.query("patient",null,
						"_id = ?",
						new String[]{
						hospital_id
				},null,null,"_id");
	}
	public Cursor queryAllPatient()
	{
		return this.getReadableDatabase()
				.query("patient",
						null,null,null,null,null,"_id");
	}
	public boolean insertPatient(Patient patient)
	{
		if(this.queryPatient(patient.get_id()).getCount() != 0)
		{
			return false;
		}
		ContentValues values = new ContentValues();
		values.put("name", patient.getName());
		values.put("gender", patient.getGender());
		values.put("birth", patient.getBirth());
		values.put("_id",patient.get_id());
		values.put("diagnose", patient.getDiagnose());
		if(this.getWritableDatabase().insert(
				"patient",null,values) == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	public boolean deletePatient(String hospital_id)
	{
		if(this.queryPatient(hospital_id).getCount() == 0)
		{
			return false;
		}
		if(this.getWritableDatabase().delete("patient",
				"_id = ?",
				new String[]{
				hospital_id}) == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/*
	 * 
	 */
	public Cursor queryAllMmse()
	{
		return this.getReadableDatabase()
				.query("mmse",
						null,null,null,null,null,"hospital_id");
	}
	public boolean insertMmse(Mmse mmse)
	{
		ContentValues values = new ContentValues();
		values.put("hospital_id", mmse.getHospital_id());
		values.put("doctor_name", mmse.getDoctor_name());
		values.put("evaluate_date", mmse.getEvaluate_date());
		values.put("evaluate_starttime", mmse.getEvaluate_starttime());
		values.put("evaluate_endtime", mmse.getEvaluate_endtime());
		values.put("evaluate_millisecond", mmse.getEvaluate_millisecond());
		values.put("score", mmse.getScore());
		values.put("awareness_state", mmse.getAwareness_state());
		if(this.getWritableDatabase().insert(
				"mmse",null,values) == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/*
	 * 
	 */
	public Cursor queryAllHanoi()
	{
		return this.getReadableDatabase()
				.query("hanoi",
						null,null,null,null,null,"hospital_id");
	}
	public boolean insertHanoi(Hanoi hanoi)
	{
		ContentValues values = new ContentValues();
		values.put("hospital_id", hanoi.getHospital_id());
		values.put("doctor_name", hanoi.getDoctor_name());
		values.put("evaluate_date", hanoi.getEvaluate_date());
		values.put("evaluate_starttime", hanoi.getEvaluate_starttime());
		values.put("evaluate_endtime", hanoi.getEvaluate_endtime());
		values.put("evaluate_millisecond", hanoi.getEvaluate_millisecond());
		values.put("prac_starttime", hanoi.getPrac_starttime());
		values.put("prac_endtime", hanoi.getPrac_endtime());
		values.put("prac_millisecond", hanoi.getPrac_millisecond());
		values.put("test_starttime", hanoi.getTest_starttime());
		values.put("test_endtime", hanoi.getTest_endtime());
		values.put("test_millisecond", hanoi.getTest_millisecond());
		values.put("score", hanoi.getScore());
		if(this.getWritableDatabase().insert(
				"hanoi",null,values) == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/*
	 * 
	 */
	public Cursor queryAllLine()
	{
		return this.getReadableDatabase()
				.query("line",
						null,null,null,null,null,"hospital_id");
	}
	public boolean insertLine(Line line)
	{
		ContentValues values = new ContentValues();
		values.put("hospital_id", line.getHospital_id());
		values.put("doctor_name", line.getDoctor_name());
		values.put("evaluate_date", line.getEvaluate_date());
		values.put("evaluate_starttime", line.getEvaluate_starttime());
		values.put("evaluate_endtime", line.getEvaluate_endtime());
		values.put("evaluate_millisecond", line.getEvaluate_millisecond());
		values.put("prac_starttime", line.getPrac_starttime());
		values.put("prac_endtime", line.getPrac_endtime());
		values.put("prac_millisecond", line.getPrac_millisecond());
		values.put("test_starttime", line.getTest_starttime());
		values.put("test_endtime", line.getTest_endtime());
		values.put("test_millisecond", line.getTest_millisecond());
		values.put("simple_a_error", line.getSimple_a_error());
		values.put("simple_b_error", line.getSimple_b_error());
		if(this.getWritableDatabase().insert(
				"line",null,values) == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/*
	 * 
	 */
	public Cursor queryAllWisconsin()
	{
		return this.getReadableDatabase()
				.query("wisconsin",
						null,null,null,null,null,"hospital_id");
	}
	public boolean insertWisconsin(Wisconsin wisconsin)
	{
		ContentValues values = new ContentValues();
		values.put("hospital_id", wisconsin.getHospital_id());
		values.put("doctor_name", wisconsin.getDoctor_name());
		values.put("evaluate_date", wisconsin.getEvaluate_date());
		values.put("evaluate_starttime", wisconsin.getEvaluate_starttime());
		values.put("evaluate_endtime", wisconsin.getEvaluate_endtime());
		values.put("evaluate_millisecond", wisconsin.getEvaluate_millisecond());
		values.put("color1", wisconsin.getColor1());
		values.put("color1starttime", wisconsin.getColor1starttime());
		values.put("color1endtime", wisconsin.getColor1endtime());
		values.put("color1millisecond", wisconsin.getColor1millisecond());
		values.put("form1", wisconsin.getForm1());
		values.put("form1starttime", wisconsin.getForm1starttime());
		values.put("form1endtime", wisconsin.getForm1endtime());
		values.put("form1millisecond", wisconsin.getForm1millisecond());
		values.put("number1", wisconsin.getNumber1());
		values.put("number1starttime", wisconsin.getNumber1starttime());
		values.put("number1endtime", wisconsin.getNumber1endtime());
		values.put("number1millisecond", wisconsin.getNumber1millisecond());
		values.put("color2", wisconsin.getColor2());
		values.put("color2starttime", wisconsin.getColor2starttime());
		values.put("color2endtime", wisconsin.getColor2endtime());
		values.put("color2millisecond", wisconsin.getColor2millisecond());
		values.put("form2", wisconsin.getForm2());
		values.put("form2starttime", wisconsin.getForm2starttime());
		values.put("form2endtime", wisconsin.getForm2endtime());
		values.put("form2millisecond", wisconsin.getForm2millisecond());
		values.put("number2", wisconsin.getNumber2());
		values.put("number2starttime", wisconsin.getNumber2starttime());
		values.put("number2endtime", wisconsin.getNumber2endtime());
		values.put("number2millisecond", wisconsin.getNumber2millisecond());
		if(this.getWritableDatabase().insert(
				"wisconsin",null,values) == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/*
	 * 
	 */
	public Cursor queryAllCued()
	{
		return this.getReadableDatabase()
				.query("cued",
						null,null,null,null,null,"hospital_id");
	}
	public boolean insertCued(Cued cued)
	{
		ContentValues values = new ContentValues();
		values.put("hospital_id", cued.getHospital_id());
		values.put("doctor_name", cued.getDoctor_name());
		values.put("evaluate_date", cued.getEvaluate_date());
		values.put("evaluate_starttime", cued.getEvaluate_starttime());
		values.put("evaluate_endtime", cued.getEvaluate_endtime());
		values.put("evaluate_millisecond", cued.getEvaluate_millisecond());
		values.put("starttime", cued.getStarttime());
		values.put("endtime", cued.getEndtime());
		values.put("millisecond", cued.getMillisecond());
		values.put("correct", cued.getCorrect());
		values.put("error", cued.getError());
		if(this.getWritableDatabase().insert(
				"cued",null,values) == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/*
	 * 
	 */
	public Cursor queryAllWord()
	{
		return this.getReadableDatabase()
				.query("word",
						null,null,null,null,null,"hospital_id");
	}
	public boolean insertWord(Word word)
	{
		ContentValues values = new ContentValues();
		values.put("hospital_id", word.getHospital_id());
		values.put("doctor_name", word.getDoctor_name());
		values.put("evaluate_date", word.getEvaluate_date());
		values.put("evaluate_starttime", word.getEvaluate_starttime());
		values.put("evaluate_endtime", word.getEvaluate_endtime());
		values.put("evaluate_millisecond", word.getEvaluate_millisecond());
		values.put("starttime", word.getStarttime());
		values.put("endtime", word.getEndtime());
		values.put("millisecond", word.getMillisecond());
		values.put("correct", word.getCorrect());
		values.put("error", word.getError());
		if(this.getWritableDatabase().insert(
				"word",null,values) == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/*
	 * 
	 */
	public Cursor queryAllAospan()
	{
		return this.getReadableDatabase()
				.query("aospan",
						null,null,null,null,null,"hospital_id");
	}
	public boolean insertAospan(Aos aos)
	{
		ContentValues values = new ContentValues();
		values.put("hospital_id", aos.getHospital_id());
		values.put("doctor_name", aos.getDoctor_name());
		values.put("evaluate_date", aos.getEvaluate_date());
		values.put("evaluate_starttime", aos.getEvaluate_starttime());
		values.put("evaluate_endtime", aos.getEvaluate_endtime());
		values.put("evaluate_millisecond", aos.getEvaluate_millisecond());
		values.put("pracletter_starttime", aos.getPracletter_starttime());
		values.put("pracletter_endtime", aos.getPracletter_endtime());
		values.put("pracletter_millisecond", aos.getPracletter_millisecond());
		values.put("pracmath_starttime", aos.getPracmath_starttime());
		values.put("pracmath_endtime", aos.getPracmath_endtime());
		values.put("pracmath_millisecond", aos.getPracmath_millisecond());
		values.put("pracboth_starttime", aos.getPracboth_starttime());
		values.put("pracboth_endtime", aos.getPracboth_endtime());
		values.put("pracboth_millisecond", aos.getPracboth_millisecond());
		values.put("testboth_starttime", aos.getTestboth_starttime());
		values.put("testboth_endtime", aos.getTestboth_endtime());
		values.put("testboth_millisecond", aos.getTestboth_millisecond());
		values.put("ospan_absoluate_score", aos.getOspan_absoluate_score());
		values.put("ospan_total_correct", aos.getOspan_total_correct());
		values.put("math_total_errors", aos.getMath_total_errors());
		values.put("math_speed_errors", aos.getMath_speed_errors());
		values.put("math_accuracy_errors", aos.getMath_accuracy_errors());
		if(this.getWritableDatabase().insert(
				"aospan",null,values) == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/*
	 * 
	 */
	public Cursor queryAllVisual()
	{
		return this.getReadableDatabase()
				.query("dsvisual",
						null,null,null,null,null,"hospital_id");
	}
	public boolean insertVisual(Visual visual)
	{
		ContentValues values = new ContentValues();
		values.put("hospital_id", visual.getHospital_id());
		values.put("doctor_name", visual.getDoctor_name());
		values.put("evaluate_date", visual.getEvaluate_date());
		values.put("evaluate_starttime", visual.getEvaluate_starttime());
		values.put("evaluate_endtime", visual.getEvaluate_endtime());
		values.put("evaluate_millisecond", visual.getEvaluate_millisecond());
		values.put("pracf_starttime", visual.getPracf_starttime());
		values.put("pracf_endtime", visual.getPracf_endtimd());
		values.put("pracf_millisecond", visual.getPracf_millisecond());
		values.put("testf_starttime", visual.getTestf_starttime());
		values.put("testf_endtime", visual.getTestf_endtime());
		values.put("testf_millisecond", visual.getTestf_millisecond());
		values.put("pracb_starttime", visual.getPracb_starttime());
		values.put("pracb_endtime", visual.getPracb_endtime());
		values.put("pracb_millisecond", visual.getPracb_millisecond());
		values.put("testb_starttime", visual.getTestb_starttime());
		values.put("testb_endtime", visual.getTestb_endtime());
		values.put("testb_millisecond", visual.getTestb_millisecond());
		values.put("forward_TE_ML", visual.getForward_TE_ML());
		values.put("forward_TE_TT", visual.getForward_TE_TT());
		values.put("forward_ML", visual.getForward_ML());
		values.put("forward_MS", visual.getForward_MS());
		values.put("backward_TE_ML", visual.getBackward_TE_ML());
		values.put("backward_TE_TT", visual.getBackward_TE_TT());
		values.put("backward_ML", visual.getBackward_ML());
		values.put("backward_MS", visual.getBackward_MS());
		if(this.getWritableDatabase().insert(
				"dsvisual",null,values) == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/*
	 * 
	 */
	public Cursor queryAllAcoustic()
	{
		return this.getReadableDatabase()
				.query("dsacoustic",
						null,null,null,null,null,"hospital_id");
	}
	public boolean insertAcoustic(Acoustic acoustic)
	{
		ContentValues values = new ContentValues();
		values.put("hospital_id", acoustic.getHospital_id());
		values.put("doctor_name", acoustic.getDoctor_name());
		values.put("evaluate_date", acoustic.getEvaluate_date());
		values.put("evaluate_starttime", acoustic.getEvaluate_starttime());
		values.put("evaluate_endtime", acoustic.getEvaluate_endtime());
		values.put("evaluate_millisecond", acoustic.getEvaluate_millisecond());
		values.put("pracf_starttime", acoustic.getPracf_starttime());
		values.put("pracf_endtime", acoustic.getPracf_endtimd());
		values.put("pracf_millisecond", acoustic.getPracf_millisecond());
		values.put("testf_starttime", acoustic.getTestf_starttime());
		values.put("testf_endtime", acoustic.getTestf_endtime());
		values.put("testf_millisecond", acoustic.getTestf_millisecond());
		values.put("pracb_starttime", acoustic.getPracb_starttime());
		values.put("pracb_endtime", acoustic.getPracb_endtime());
		values.put("pracb_millisecond",acoustic.getPracb_millisecond());
		values.put("testb_starttime", acoustic.getTestb_starttime());
		values.put("testb_endtime", acoustic.getTestb_endtime());
		values.put("testb_millisecond", acoustic.getTestb_millisecond());
		values.put("forward_TE_ML", acoustic.getForward_TE_ML());
		values.put("forward_TE_TT", acoustic.getForward_TE_TT());
		values.put("forward_ML", acoustic.getForward_ML());
		values.put("forward_MS", acoustic.getForward_MS());
		values.put("backward_TE_ML", acoustic.getBackward_TE_ML());
		values.put("backward_TE_TT", acoustic.getBackward_TE_TT());
		values.put("backward_ML", acoustic.getBackward_ML());
		values.put("backward_MS", acoustic.getBackward_MS());
		if(this.getWritableDatabase().insert(
				"dsacoustic",null,values) == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL("PRAGMA FOREIGN_KEYS=ON;");
		db.execSQL(this.CREATE_PATIENT);
		db.execSQL(this.CREATE_MMSE);
		db.execSQL(this.CREATE_HANOI);
		db.execSQL(this.CREATE_LINE);
		db.execSQL(this.CREATE_WISCONSIN);
		db.execSQL(this.CREATE_CUED);
		db.execSQL(this.CREATE_WORDCOLOR);
		db.execSQL(this.CREATE_AOSPAN);
		db.execSQL(this.CREATE_DSVISUAL);
		db.execSQL(this.CREATE_DSACOUSTIC);
	}
	@Override
	public void onOpen(SQLiteDatabase db) 
	{
		super.onOpen(db);
		if(!db.isReadOnly())
		{
			db.execSQL("PRAGMA FOREIGN_KEYS=ON;");
		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO 自动生成的方法存根
	}

}
