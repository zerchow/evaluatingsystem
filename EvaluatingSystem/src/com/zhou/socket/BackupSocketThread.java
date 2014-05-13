package com.zhou.socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.zhou.evaluatingsystem.EnterActivity;
import com.zhou.util.FinalUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

public class BackupSocketThread extends Thread 
{
	public BackupSocketThread(EnterActivity context,String IP)
	{
		this.context = context;
		this.IP = IP;
	}
	private String IP = null;
	private EnterActivity context;
	private final int PORT = 3000;
	private final int BUFFER_LEN = 1024;
	private byte[] buffer = new byte[this.BUFFER_LEN];
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	public void run()
	{
		Socket socket = null;
		OutputStream out = null;
		FileInputStream in = null;
		try
		{
			socket = new Socket();
			socket.connect(new InetSocketAddress(this.IP, this.PORT),
					8000);
			this.sendMessage("连接成功");
			out = socket.getOutputStream();
			in = new FileInputStream(new File(
					"/data/data/com.zhou.evaluatingsystem/databases/evalsys.db3"));
			int length = 0;
			while((length = in.read(this.buffer,0,this.BUFFER_LEN))
					> 0)
			{
				out.write(buffer);
			}
			this.sendMessage("备份成功");
			this.preferences = this.context.getSharedPreferences(
					FinalUtil.PREFERENCENAME,this.context.MODE_PRIVATE);
			this.editor = this.preferences.edit();
			this.editor.putString("ipinfo", this.IP);
			this.editor.commit();
		}
		catch(SocketTimeoutException timeout_e)
		{
			//连接超时
			this.sendMessage("连接超时");
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
		msg.what = FinalUtil.BACKUP_FEEDBACK;
		Bundle bundle = new Bundle();
		bundle.putString("backup", str);
		msg.setData(bundle);
		this.context.handler.sendMessage(msg);
	}
}
