package com.zhou.socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class BackupSocketThread extends Thread 
{
	private final String IP = "";
	private final int PORT = 0;
	private final int BUFFER_LEN = 1024;
	private byte[] buffer = new byte[this.BUFFER_LEN];
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
			out = socket.getOutputStream();
			in = new FileInputStream(new File(
					"/data/data/com.zhou.evaluatingsystem/databases/evalsys.db3"));
			int length = 0;
			while((length = in.read(this.buffer,0,this.BUFFER_LEN))
					> 0)
			{
				out.write(buffer);
			}
		}
		catch(SocketTimeoutException timeout_e)
		{
			//连接超时
		}
		catch(UnknownHostException host_e)
		{
			//无法识别主机的名称和IP地址
		}
		catch(ConnectException connect_e)
		{
			//没有服务器进程监听指定的端口，或者服务器进程拒绝连接（已到达连接上限数）
		}
		catch(Exception e)
		{
			//其他
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
}
