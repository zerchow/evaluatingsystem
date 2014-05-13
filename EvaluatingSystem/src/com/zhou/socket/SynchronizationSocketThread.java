package com.zhou.socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;

import com.zhou.evaluatingsystem.EnterActivity;
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
import com.zhou.sqlite.EvalSysDatabaseHelper;
import com.zhou.util.FinalUtil;

public class SynchronizationSocketThread extends Thread 
{
	public SynchronizationSocketThread(EnterActivity context,
			String IP,String hospital_id)
	{
		this.context = context;
		this.IP = IP;
		this.hospital_id = hospital_id;
	}
	private String IP = null;
	private EnterActivity context;
	private String hospital_id;
	private final int PORT = 3000;
	private final int BUFFER_LEN = 1024;
	private byte[] buffer = new byte[this.BUFFER_LEN];
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private void synchronization()
	{
		File file = new File(FinalUtil.serverDBFile);
		if(!file.exists())
		{
			this.sendMessage("同步失败");
		}
		else if(file.length() == 0)
		{
			this.sendMessage("同步失败");
		}
		else
		{
			SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(file, null);
			Cursor cursor = db.rawQuery(
					"select * from patient",null);
			if(cursor.getCount() == 0)
			{
				this.sendMessage("服务器不存在该患者");
			}
			else
			{
				//开始解析
				EvalSysDatabaseHelper dbHelper = new EvalSysDatabaseHelper(this.context);
				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
				{
					Patient patient = new Patient();
					patient.set_id(cursor.getString(cursor.getColumnIndex("_id")));
					patient.setBirth(cursor.getString(cursor.getColumnIndex("birth")));
					patient.setDiagnose(cursor.getString(cursor.getColumnIndex("diagnose")));
					patient.setGender(cursor.getString(cursor.getColumnIndex("gender")));
					patient.setName(cursor.getString(cursor.getColumnIndex("name")));
					dbHelper.insertPatient(patient);
				}
				cursor.close();
				cursor = db.rawQuery("select * from mmse",null);
				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
				{
					Mmse mmse = new Mmse();
					mmse.setAwareness_state(cursor.getString(cursor.getColumnIndex("awareness_state")));
					mmse.setDoctor_name(cursor.getString(cursor.getColumnIndex("doctor_name")));
					mmse.setEvaluate_date(cursor.getString(cursor.getColumnIndex("evaluate_date")));
					mmse.setEvaluate_endtime(cursor.getString(cursor.getColumnIndex("evaluate_endtime")));
					mmse.setEvaluate_millisecond(cursor.getLong(cursor.getColumnIndex("evaluate_millisecond")));
					mmse.setEvaluate_starttime(cursor.getString(cursor.getColumnIndex("evaluate_starttime")));
					mmse.setHospital_id(cursor.getString(cursor.getColumnIndex("hospital_id")));
					mmse.setScore(cursor.getInt(cursor.getColumnIndex("score")));
					dbHelper.insertMmse(mmse);
				}
				cursor.close();
				cursor = db.rawQuery("select * from hanoi", null);
				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
				{
					Hanoi hanoi = new Hanoi();
					hanoi.setDoctor_name(cursor.getString(cursor.getColumnIndex("doctor_name")));
					hanoi.setEvaluate_date(cursor.getString(cursor.getColumnIndex("evaluate_date")));
					hanoi.setEvaluate_endtime(cursor.getString(cursor.getColumnIndex("evaluate_endtime")));
					hanoi.setEvaluate_millisecond(cursor.getLong(cursor.getColumnIndex("evaluate_millisecond")));
					hanoi.setEvaluate_starttime(cursor.getString(cursor.getColumnIndex("evaluate_starttime")));
					hanoi.setHospital_id(cursor.getString(cursor.getColumnIndex("hospital_id")));
					hanoi.setPrac_endtime(cursor.getString(cursor.getColumnIndex("prac_endtime")));
					hanoi.setPrac_millisecond(cursor.getLong(cursor.getColumnIndex("prac_millisecond")));
					hanoi.setPrac_starttime(cursor.getString(cursor.getColumnIndex("prac_starttime")));
					hanoi.setScore(cursor.getInt(cursor.getColumnIndex("score")));
					hanoi.setTest_endtime(cursor.getString(cursor.getColumnIndex("test_endtime")));
					hanoi.setTest_millisecond(cursor.getLong(cursor.getColumnIndex("test_millisecond")));
					hanoi.setTest_starttime(cursor.getString(cursor.getColumnIndex("test_starttime")));
				}
				cursor.close();
				cursor = db.rawQuery("select * from line", null);
				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
				{
					Line line = new Line();
					line.setDoctor_name(cursor.getString(cursor.getColumnIndex("doctor_name")));
					line.setEvaluate_date(cursor.getString(cursor.getColumnIndex("evaluate_date")));
					line.setEvaluate_endtime(cursor.getString(cursor.getColumnIndex("evaluate_endtime")));
					line.setEvaluate_millisecond(cursor.getLong(cursor.getColumnIndex("evaluate_millisecond")));
					line.setEvaluate_starttime(cursor.getString(cursor.getColumnIndex("evaluate_starttime")));
					line.setHospital_id(cursor.getString(cursor.getColumnIndex("hospital_id")));
					line.setPrac_endtime(cursor.getString(cursor.getColumnIndex("prac_endtime")));
					line.setPrac_millisecond(cursor.getLong(cursor.getColumnIndex("prac_millisecond")));
					line.setPrac_starttime(cursor.getString(cursor.getColumnIndex("prac_starttime")));
					line.setSimple_a_error(cursor.getInt(cursor.getColumnIndex("simple_a_error")));
					line.setSimple_b_error(cursor.getInt(cursor.getColumnIndex("simple_b_error")));
					line.setTest_endtime(cursor.getString(cursor.getColumnIndex("test_endtime")));
					line.setTest_millisecond(cursor.getLong(cursor.getColumnIndex("test_millisecond")));
					line.setTest_starttime(cursor.getString(cursor.getColumnIndex("test_starttime")));
					dbHelper.insertLine(line);
				}
				cursor.close();
				cursor = db.rawQuery("select * from wisconsin", null);
				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
				{
					Wisconsin wisconsin = new Wisconsin();
					wisconsin.setColor1(cursor.getString(cursor.getColumnIndex("color1")));
					wisconsin.setColor1endtime(cursor.getString(cursor.getColumnIndex("color1endtime")));
					wisconsin.setColor1millisecond(cursor.getLong(cursor.getColumnIndex("color1millisecond")));
					wisconsin.setColor1starttime(cursor.getString(cursor.getColumnIndex("color1starttime")));
					wisconsin.setColor2(cursor.getString(cursor.getColumnIndex("color2")));
					wisconsin.setColor2endtime(cursor.getString(cursor.getColumnIndex("color2endtime")));
					wisconsin.setColor2millisecond(cursor.getLong(cursor.getColumnIndex("color2millisecond")));
					wisconsin.setColor2starttime(cursor.getString(cursor.getColumnIndex("color2starttime")));
					wisconsin.setDoctor_name(cursor.getString(cursor.getColumnIndex("doctor_name")));
					wisconsin.setEvaluate_date(cursor.getString(cursor.getColumnIndex("evaluate_date")));
					wisconsin.setEvaluate_endtime(cursor.getString(cursor.getColumnIndex("evaluate_endtime")));
					wisconsin.setEvaluate_millisecond(cursor.getLong(cursor.getColumnIndex("evaluate_millisecond")));
					wisconsin.setEvaluate_starttime(cursor.getString(cursor.getColumnIndex("evaluate_starttime")));
					wisconsin.setForm1(cursor.getString(cursor.getColumnIndex("form1")));
					wisconsin.setForm1endtime(cursor.getString(cursor.getColumnIndex("form1endtime")));
					wisconsin.setForm1millisecond(cursor.getLong(cursor.getColumnIndex("form1millisecond")));
					wisconsin.setForm1starttime(cursor.getString(cursor.getColumnIndex("form1starttime")));
					wisconsin.setForm2(cursor.getString(cursor.getColumnIndex("form2")));
					wisconsin.setForm2endtime(cursor.getString(cursor.getColumnIndex("form2endtime")));
					wisconsin.setForm2millisecond(cursor.getLong(cursor.getColumnIndex("form2millisecond")));
					wisconsin.setForm2starttime(cursor.getString(cursor.getColumnIndex("form2starttime")));
					wisconsin.setHospital_id(cursor.getString(cursor.getColumnIndex("hospital_id")));
					wisconsin.setNumber1(cursor.getString(cursor.getColumnIndex("number1")));
					wisconsin.setNumber1endtime(cursor.getString(cursor.getColumnIndex("number1endtime")));
					wisconsin.setNumber1millisecond(cursor.getLong(cursor.getColumnIndex("number1millisecond")));
					wisconsin.setNumber1starttime(cursor.getString(cursor.getColumnIndex("number1starttime")));
					wisconsin.setNumber2(cursor.getString(cursor.getColumnIndex("number2")));
					wisconsin.setNumber2endtime(cursor.getString(cursor.getColumnIndex("number2endtime")));
					wisconsin.setNumber2millisecond(cursor.getLong(cursor.getColumnIndex("number2millisecond")));
					wisconsin.setNumber2starttime(cursor.getString(cursor.getColumnIndex("number2starttime")));
					dbHelper.insertWisconsin(wisconsin);
				}
				cursor.close();
				cursor = db.rawQuery("select * from cued", null);
				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
				{
					Cued cued = new Cued();
					cued.setCorrect(cursor.getInt(cursor.getColumnIndex("correct")));
					cued.setDoctor_name(cursor.getString(cursor.getColumnIndex("doctor_name")));
					cued.setEndtime(cursor.getString(cursor.getColumnIndex("endtime")));
					cued.setError(cursor.getInt(cursor.getColumnIndex("error")));
					cued.setEvaluate_date(cursor.getString(cursor.getColumnIndex("evaluate_date")));
					cued.setEvaluate_millisecond(cursor.getLong(cursor.getColumnIndex("evaluate_millisecond")));
					cued.setEvaluate_endtime(cursor.getString(cursor.getColumnIndex("evaluate_endtime")));
					cued.setEvaluate_starttime(cursor.getString(cursor.getColumnIndex("evaluate_starttime")));
					cued.setHospital_id(cursor.getString(cursor.getColumnIndex("hospital_id")));
					cued.setMillisecond(cursor.getLong(cursor.getColumnIndex("millisecond")));
					cued.setStarttime(cursor.getString(cursor.getColumnIndex("starttime")));
					dbHelper.insertCued(cued);
				}
				cursor.close();
				cursor = db.rawQuery("select * from word", null);
				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
				{
					Word word = new Word();
					word.setCorrect(cursor.getInt(cursor.getColumnIndex("correct")));
					word.setDoctor_name(cursor.getString(cursor.getColumnIndex("doctor_name")));
					word.setEndtime(cursor.getString(cursor.getColumnIndex("endtime")));
					word.setError(cursor.getInt(cursor.getColumnIndex("error")));
					word.setEvaluate_date(cursor.getString(cursor.getColumnIndex("evaluate_date")));
					word.setEvaluate_endtime(cursor.getString(cursor.getColumnIndex("evaluate_endtime")));
					word.setEvaluate_millisecond(cursor.getLong(cursor.getColumnIndex("evaluate_millisecond")));
					word.setEvaluate_starttime(cursor.getString(cursor.getColumnIndex("evaluate_starttime")));
					word.setHospital_id(cursor.getString(cursor.getColumnIndex("hospital_id")));
					word.setMillisecond(cursor.getLong(cursor.getColumnIndex("millisecond")));
					word.setStarttime(cursor.getString(cursor.getColumnIndex("starttime")));
					dbHelper.insertWord(word);
				}
				cursor.close();
				cursor = db.rawQuery("select * from aospan", null);
				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
				{
					Aos aos = new Aos();
					aos.setDoctor_name(cursor.getString(cursor.getColumnIndex("doctor_name")));
					aos.setEvaluate_date(cursor.getString(cursor.getColumnIndex("evaluate_date")));
					aos.setEvaluate_endtime(cursor.getString(cursor.getColumnIndex("evaluate_endtime")));
					aos.setEvaluate_millisecond(cursor.getLong(cursor.getColumnIndex("evaluate_millisecond")));
					aos.setEvaluate_starttime(cursor.getString(cursor.getColumnIndex("evaluate_starttime")));
					aos.setHospital_id(cursor.getString(cursor.getColumnIndex("hospital_id")));
					aos.setMath_accuracy_errors(cursor.getInt(cursor.getColumnIndex("math_accurary_errors")));
					aos.setMath_speed_errors(cursor.getInt(cursor.getColumnIndex("math_speed_errors")));
					aos.setMath_total_errors(cursor.getInt(cursor.getColumnIndex("math_total_errors")));
					aos.setOspan_absoluate_score(cursor.getInt(cursor.getColumnIndex("ospan_absolute_score")));
					aos.setOspan_total_correct(cursor.getInt(cursor.getColumnIndex("ospan_total_correct")));
					aos.setPracboth_endtime(cursor.getString(cursor.getColumnIndex("pracboth_endtime")));
					aos.setPracboth_millisecond(cursor.getLong(cursor.getColumnIndex("pracboth_millisecond")));
					aos.setPracboth_starttime(cursor.getString(cursor.getColumnIndex("pracboth_starttime")));
					aos.setPracletter_endtime(cursor.getString(cursor.getColumnIndex("pracletter_endtime")));
					aos.setPracletter_millisecond(cursor.getLong(cursor.getColumnIndex("pracletter_millisecond")));
					aos.setPracletter_starttime(cursor.getString(cursor.getColumnIndex("pracletter_starttime")));
					aos.setPracmath_endtime(cursor.getString(cursor.getColumnIndex("pracmath_endtime")));
					aos.setPracmath_millisecond(cursor.getLong(cursor.getColumnIndex("pracmath_millisecond")));
					aos.setPracmath_starttime(cursor.getString(cursor.getColumnIndex("pracmath_starttime")));
					aos.setTestboth_endtime(cursor.getString(cursor.getColumnIndex("testboth_endtime")));
					aos.setTestboth_millisecond(cursor.getLong(cursor.getColumnIndex("testboth_millisecond")));
					aos.setTestboth_starttime(cursor.getString(cursor.getColumnIndex("testboth_starttime")));
					dbHelper.insertAospan(aos);
				}
				cursor.close();
				cursor = db.rawQuery("select * from dsvisual", null);
				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
				{
					Visual visual = new Visual();
					visual.setBackward_ML(cursor.getFloat(cursor.getColumnIndex("backward_ML")));
					visual.setBackward_MS(cursor.getFloat(cursor.getColumnIndex("backward_MS")));
					visual.setBackward_TE_ML(cursor.getFloat(cursor.getColumnIndex("backward_TE_ML")));
					visual.setBackward_TE_TT(cursor.getFloat(cursor.getColumnIndex("backward_TE_TT")));
					visual.setDoctor_name(cursor.getString(cursor.getColumnIndex("doctor_name")));
					visual.setEvaluate_date(cursor.getString(cursor.getColumnIndex("evaluate_date")));
					visual.setEvaluate_endtime(cursor.getString(cursor.getColumnIndex("evaluate_endtime")));
					visual.setEvaluate_millisecond(cursor.getLong(cursor.getColumnIndex("evaluate_millisecond")));
					visual.setEvaluate_starttime(cursor.getString(cursor.getColumnIndex("evaluate_starttime")));
					visual.setForward_ML(cursor.getFloat(cursor.getColumnIndex("forward_ML")));
					visual.setForward_MS(cursor.getFloat(cursor.getColumnIndex("forward_MS")));
					visual.setForward_TE_ML(cursor.getFloat(cursor.getColumnIndex("forward_TE_ML")));
					visual.setForward_TE_TT(cursor.getFloat(cursor.getColumnIndex("forward_TE_TT")));
					visual.setHospital_id(cursor.getString(cursor.getColumnIndex("hospital_id")));
					visual.setPracb_endtime(cursor.getString(cursor.getColumnIndex("pracb_endtime")));
					visual.setPracb_millisecond(cursor.getLong(cursor.getColumnIndex("pracb_millisecond")));
					visual.setPracb_starttime(cursor.getString(cursor.getColumnIndex("pracb_starttime")));
					visual.setPracf_endtimd(cursor.getString(cursor.getColumnIndex("pracf_endtime")));
					visual.setPracf_millisecond(cursor.getLong(cursor.getColumnIndex("pracf_millisecond")));
					visual.setPracf_starttime(cursor.getString(cursor.getColumnIndex("pracf_starttime")));
					visual.setTestb_endtime(cursor.getString(cursor.getColumnIndex("testb_endtime")));
					visual.setTestb_millisecond(cursor.getLong(cursor.getColumnIndex("testb_millisecond")));
					visual.setTestb_starttime(cursor.getString(cursor.getColumnIndex("testb_starttime")));
					visual.setTestf_endtime(cursor.getString(cursor.getColumnIndex("testf_endtime")));
					visual.setTestf_millisecond(cursor.getLong(cursor.getColumnIndex("testf_millisecond")));
					visual.setTestf_starttime(cursor.getString(cursor.getColumnIndex("testf_starttime")));
					dbHelper.insertVisual(visual);
				}
				cursor.close();
				cursor = db.rawQuery("select * from dsacoustic", null);
				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
				{
					Acoustic acoustic = new Acoustic();
					acoustic.setBackward_ML(cursor.getFloat(cursor.getColumnIndex("backward_ML")));
					acoustic.setBackward_MS(cursor.getFloat(cursor.getColumnIndex("backward_MS")));
					acoustic.setBackward_TE_ML(cursor.getFloat(cursor.getColumnIndex("backward_TE_ML")));
					acoustic.setBackward_TE_TT(cursor.getFloat(cursor.getColumnIndex("backward_TE_TT")));
					acoustic.setDoctor_name(cursor.getString(cursor.getColumnIndex("doctor_name")));
					acoustic.setEvaluate_date(cursor.getString(cursor.getColumnIndex("evaluate_date")));
					acoustic.setEvaluate_endtime(cursor.getString(cursor.getColumnIndex("evaluate_endtime")));
					acoustic.setEvaluate_millisecond(cursor.getLong(cursor.getColumnIndex("evaluate_millisecond")));
					acoustic.setEvaluate_starttime(cursor.getString(cursor.getColumnIndex("evaluate_starttime")));
					acoustic.setForward_ML(cursor.getFloat(cursor.getColumnIndex("forward_ML")));
					acoustic.setForward_MS(cursor.getFloat(cursor.getColumnIndex("forward_MS")));
					acoustic.setForward_TE_ML(cursor.getFloat(cursor.getColumnIndex("forward_TE_ML")));
					acoustic.setForward_TE_TT(cursor.getFloat(cursor.getColumnIndex("forward_TE_TT")));
					acoustic.setHospital_id(cursor.getString(cursor.getColumnIndex("hospital_id")));
					acoustic.setPracb_endtime(cursor.getString(cursor.getColumnIndex("pracb_endtime")));
					acoustic.setPracb_millisecond(cursor.getLong(cursor.getColumnIndex("pracb_millisecond")));
					acoustic.setPracb_starttime(cursor.getString(cursor.getColumnIndex("pracb_starttime")));
					acoustic.setPracf_endtimd(cursor.getString(cursor.getColumnIndex("pracf_endtime")));
					acoustic.setPracf_millisecond(cursor.getLong(cursor.getColumnIndex("pracf_millisecond")));
					acoustic.setPracf_starttime(cursor.getString(cursor.getColumnIndex("pracf_starttime")));
					acoustic.setTestb_endtime(cursor.getString(cursor.getColumnIndex("testb_endtime")));
					acoustic.setTestb_millisecond(cursor.getLong(cursor.getColumnIndex("testb_millisecond")));
					acoustic.setTestb_starttime(cursor.getString(cursor.getColumnIndex("testb_starttime")));
					acoustic.setTestf_endtime(cursor.getString(cursor.getColumnIndex("testf_endtime")));
					acoustic.setTestf_millisecond(cursor.getLong(cursor.getColumnIndex("testf_millisecond")));
					acoustic.setTestf_starttime(cursor.getString(cursor.getColumnIndex("testf_starttime")));
					dbHelper.insertAcoustic(acoustic);
				}
				this.sendMessage("同步成功");
				this.preferences = this.context.getSharedPreferences(
						FinalUtil.PREFERENCENAME,this.context.MODE_PRIVATE);
				this.editor = this.preferences.edit();
				this.editor.putString("ipinfo", this.IP);
				this.editor.commit();
			}
		}
	}
	public void run()
	{
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		FileOutputStream fout = null;
		String request = "id," + this.hospital_id;
		try
		{
			socket = new Socket();
			socket.connect(new InetSocketAddress(this.IP, this.PORT),
					8000);
			socket.setSoTimeout(8000);
			this.sendMessage("连接成功");
			out = socket.getOutputStream();
			in = socket.getInputStream();
			out.write(request.getBytes());
			FinalUtil.deleteFile(FinalUtil.serverDBFile);
			fout = new FileOutputStream(new File(FinalUtil.serverDBFile));
			byte[] buffer = new byte[1024];
			int length = 0;
			while((length = in.read(buffer, 0, 1024)) > 0)
			{
				fout.write(buffer,0,length);
			}
			//
			this.synchronization();
			//
		}
		catch(SocketTimeoutException timeout_e)
		{
			if(socket.isConnected())
			{
				//同步超时
				this.sendMessage("同步超时");
			}
			else
			{
				//连接超时
				this.sendMessage("连接超时");
			}
		}
		catch(UnknownHostException host_e)
		{
			//无法识别主机的名称和IP地址
			this.sendMessage("无法识别主机的名称和IP地址");
		}
		catch(ConnectException connect_e)
		{
			//没有服务器进程监听指定的端口，或者服务器进程拒绝连接（已到达连接上限数）
			this.sendMessage("没有服务器进程监听指定的端口，或者服务器进程拒绝连接（已到达连接上限数）");
		}
		catch(Exception e)
		{
			//其他
			this.sendMessage("其他错误");
		}
		finally
		{
			try 
			{
				if(socket != null)
				{
					out.flush();
					out.close();
					in.close();
					fout.close();
					socket.close();
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}//end of run
	private void sendMessage(String str)
	{
		Message msg = new Message();
		msg.what = FinalUtil.SYNCHRONIZATION_FEEDBACK;
		Bundle bundle = new Bundle();
		bundle.putString("synchronization", str);
		msg.setData(bundle);
		this.context.handler.sendMessage(msg);
	}
}
