package com.zhou.sqlite;

import com.zhou.dao.Acoustic;
import com.zhou.dao.Aos;
import com.zhou.dao.Cued;
import com.zhou.dao.Hanoi;
import com.zhou.dao.Line;
import com.zhou.dao.Mmse;
import com.zhou.dao.Patient;
import com.zhou.dao.Visual;
import com.zhou.dao.Wisconsin;
import com.zhou.dao.Word;

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
			+ "_id integer primary key autoincrement,"
			+ "name,"
			+ "gender,"
			+ "birth,"
			+ "hospital_id unique,"
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
			+ "score,"
			+ "awareness_state,"
			+ "foreign key(hospital_id) references patient(hospital_id) on delete cascade on update cascade"
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
			+ "score,"
			+ "foreign key(hospital_id) references patient(hospital_id) on delete cascade on update cascade"
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
			+ "simple_a_error,"
			+ "simple_b_error,"
			+ "foreign key(hospital_id) references patient(hospital_id) on delete cascade on update cascade"
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
			+ "color1,"
			+ "form1,"
			+ "number1,"
			+ "color2,"
			+ "form2,"
			+ "number2,"
			+ "foreign key(hospital_id) references patient(hospital_id) on delete cascade on update cascade"
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
			+ "correct,"
			+ "error,"
			+ "foreign key(hospital_id) references patient(hospital_id) on delete cascade on update cascade"
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
			+ "correct,"
			+ "error,"
			+ "foreign key(hospital_id) references patient(hospital_id) on delete cascade on update cascade"
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
			+ "ospan_absolute_score,"
			+ "ospan_total_correct,"
			+ "math_total_errors,"
			+ "math_speed_errors,"
			+ "math_accuracy_errors,"
			+ "foreign key(hospital_id) references patient(hospital_id) on delete cascade on update cascade"
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
			+ "forward_TE_ML,"
			+ "forward_TE_TT,"
			+ "forward_ML,"
			+ "forward_MS,"
			+ "backward_TE_ML,"
			+ "backward_TE_TT,"
			+ "backward_ML,"
			+ "backward_MS,"
			+ "foreign key(hospital_id) references patient(hospital_id) on delete cascade on update cascade"
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
			+ "forward_TE_ML,"
			+ "forward_TE_TT,"
			+ "forward_ML,"
			+ "forward_MS,"
			+ "backward_TE_ML,"
			+ "backward_TE_TT,"
			+ "backward_ML,"
			+ "backward_MS,"
			+ "foreign key(hospital_id) references patient(hospital_id) on delete cascade on update cascade"
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
						"hospital_id = ?",
						new String[]{
						hospital_id
				},null,null,"hospital_id");
	}
	public Cursor queryAllPatient()
	{
		return this.getReadableDatabase()
				.query("patient",
						null,null,null,null,null,"hospital_id");
	}
	public boolean insertPatient(Patient patient)
	{
		if(this.queryPatient(patient.getHospital_id()).getCount() != 0)
		{
			return false;
		}
		ContentValues values = new ContentValues();
		values.put("name", patient.getName());
		values.put("gender", patient.getGender());
		values.put("birth", patient.getBirth());
		values.put("hospital_id",patient.getHospital_id());
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
				"hospital_id = ?",
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
		values.put("color1", wisconsin.getColor1());
		values.put("form1", wisconsin.getForm1());
		values.put("number1", wisconsin.getNumber1());
		values.put("color2", wisconsin.getColor2());
		values.put("form2", wisconsin.getForm2());
		values.put("number2", wisconsin.getNumber2());
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
