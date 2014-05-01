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

import android.os.Bundle;
import android.os.Message;

import com.zhou.evaluatingsystem.EnterActivity;
import com.zhou.util.FinalUtil;

public class SynchronizationSocketThread extends Thread 
{
	public SynchronizationSocketThread(EnterActivity context,String IP)
	{
		this.context = context;
		this.IP = IP;
	}
	private String IP = null;
	private EnterActivity context;
	private final int PORT = 3000;
	private final int BUFFER_LEN = 1024;
	private byte[] buffer = new byte[this.BUFFER_LEN];
	public void run()
	{
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		FileOutputStream fout = null;
		try
		{
			socket = new Socket();
			socket.connect(new InetSocketAddress(this.IP, this.PORT),
					8000);
			socket.setSoTimeout(8000);
			this.sendMessage("连接成功");
			out = socket.getOutputStream();
			in = socket.getInputStream();
			out.write("synchronization".getBytes());
			FinalUtil.deleteFile(FinalUtil.serverDBFile);
			fout = new FileOutputStream(new File(FinalUtil.serverDBFile));
			byte[] buffer = new byte[1024];
			int length = 0;
			while((length = in.read(buffer, 0, 1024)) > 0)
			{
				fout.write(buffer,0,length);
			}
			this.sendMessage("同步成功");
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
