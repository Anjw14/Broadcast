package Ex5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Server implements Runnable
{
	private Notice notice;
	
	Server(Notice n)
	{
		this.notice = n;
	}
	
	public void run(){
		try {
			DatagramSocket socket = new DatagramSocket(4445);
			byte[] buf = new byte[256];
			// 创建缓冲区
			
			int count = 0;
			while(count<10)
			{
				if(notice.sign == true)
				{
					buf = notice.str.getBytes();
					// 把String转换成字节数组，以便传送send it
					InetAddress group = InetAddress.getByName("230.0.0.1");
					// 得到230.0.0.1的地址信息
					DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
					// 根据缓冲区，广播地址，和端口号创建DatagramPacket对象
					count++;
					socket.send(packet); // 发送该Packet
//					System.out.println(count);
					notice.sign = false;
					Thread.sleep(3000);
				}
			}
			socket.close(); // 关闭广播套接口
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	
}
