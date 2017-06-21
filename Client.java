package Ex5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client implements Runnable {

	private VisibleClient client;
	
	public Client(VisibleClient c){
		this.client = c;
	}

	@Override
	public void run() {
		try {
			this.acquisition();
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	

	/**
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public void acquisition() throws IOException, UnknownHostException {
		MulticastSocket socket = new MulticastSocket(4446);
		// 创建4446端口的广播套接字
		InetAddress address = InetAddress.getByName("230.0.0.1");
		// 得到230.0.0.1的地址信息
		socket.joinGroup(address);
		// 使用joinGroup()将广播套接字绑定到地址上
		DatagramPacket packet;

		for (int i = 0; i < 5; i++) {
			byte[] buf = new byte[256];
			// 创建缓冲区
			packet = new DatagramPacket(buf, buf.length);
			// 创建接收数据报
			socket.receive(packet); // 接收
			String received = new String(packet.getData(),packet.getOffset(),packet.getLength());
			// 由接收到的数据报得到字节数组，并由此构造一个String对象
			System.out.println(received);
			// 打印得到的字符串
			
			client.sb.append(received);
			client.textArea.setText(client.sb.toString());
			
			String [] t = getKeyWord(received, "time");
			System.out.println(Arrays.toString(t));
			
			for(int j=0; j<(t.length/2); j++){
				t[2*j] = t[2*j].replace(" ", "m");
				if( ! client.sbCom.toString().contains(t[2*j])){
					client.sbCom.append(t[2*j]);
					client.sbCom.append(";");
				}
			}
			
			
			
		} // 接收5次
		socket.leaveGroup(address);
		// 把广播套接字从地址上解除绑定
		socket.close(); // 关闭广播套接字
	}
	
	public static String [] getKeyWord(String str, String type) {
		
		if(type.equals("activity")){
			Pattern pattern = Pattern.compile("[活][动][:]\t[\u4e00-\u9fa5]{1,10}");
			Matcher matcher = pattern.matcher(str);  
			StringBuilder sb = new StringBuilder();

			while(matcher.find()){  
				sb.append(matcher.group().toString());
				sb.append("\n");
			}  
			return sb.toString().split("\n");
		}
		else if(type.equals("time")){
			Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}[ ][0-9]{1,2}[:][0-9]{1,2}[:][0-9]{1,2}");
			Matcher matcher = pattern.matcher(str);  
			StringBuilder sb = new StringBuilder();

			while(matcher.find()){  
				sb.append(matcher.group().toString());
				sb.append("\n");
			}  
			return sb.toString().split("\n");
		}
		else if(type.equals("location")){
			Pattern pattern = Pattern.compile("[地][点][:]\t[\u4e00-\u9fa5]{1,10}[0-9]{0,10}");
			Matcher matcher = pattern.matcher(str);  
			StringBuilder sb = new StringBuilder();

			while(matcher.find()){  
				sb.append(matcher.group().toString());
				sb.append("\n");
			}  
			return sb.toString().split("\n");
		}
		String [] s = null;
		return s;
	}

	

}
