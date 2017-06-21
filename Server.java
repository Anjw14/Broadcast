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
			// ����������
			
			int count = 0;
			while(count<10)
			{
				if(notice.sign == true)
				{
					buf = notice.str.getBytes();
					// ��Stringת�����ֽ����飬�Ա㴫��send it
					InetAddress group = InetAddress.getByName("230.0.0.1");
					// �õ�230.0.0.1�ĵ�ַ��Ϣ
					DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
					// ���ݻ��������㲥��ַ���Ͷ˿ںŴ���DatagramPacket����
					count++;
					socket.send(packet); // ���͸�Packet
//					System.out.println(count);
					notice.sign = false;
					Thread.sleep(3000);
				}
			}
			socket.close(); // �رչ㲥�׽ӿ�
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	
}
