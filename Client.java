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
		// ����4446�˿ڵĹ㲥�׽���
		InetAddress address = InetAddress.getByName("230.0.0.1");
		// �õ�230.0.0.1�ĵ�ַ��Ϣ
		socket.joinGroup(address);
		// ʹ��joinGroup()���㲥�׽��ְ󶨵���ַ��
		DatagramPacket packet;

		for (int i = 0; i < 5; i++) {
			byte[] buf = new byte[256];
			// ����������
			packet = new DatagramPacket(buf, buf.length);
			// �����������ݱ�
			socket.receive(packet); // ����
			String received = new String(packet.getData(),packet.getOffset(),packet.getLength());
			// �ɽ��յ������ݱ��õ��ֽ����飬���ɴ˹���һ��String����
			System.out.println(received);
			// ��ӡ�õ����ַ���
			
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
			
			
			
		} // ����5��
		socket.leaveGroup(address);
		// �ѹ㲥�׽��ִӵ�ַ�Ͻ����
		socket.close(); // �رչ㲥�׽���
	}
	
	public static String [] getKeyWord(String str, String type) {
		
		if(type.equals("activity")){
			Pattern pattern = Pattern.compile("[��][��][:]\t[\u4e00-\u9fa5]{1,10}");
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
			Pattern pattern = Pattern.compile("[��][��][:]\t[\u4e00-\u9fa5]{1,10}[0-9]{0,10}");
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
