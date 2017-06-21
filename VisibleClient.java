package Ex5;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.util.GregorianCalendar;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

class MyOptionPane implements Runnable{
	
	private VisibleClient client;
	
	public MyOptionPane(VisibleClient c){
		this.client = c;
	}

	@Override
	public void run() {
		if(client.optionFlag == 1){
			JOptionPane.showMessageDialog(null, "有活动啦！", "闹钟", JOptionPane.PLAIN_MESSAGE);
		}
	}
}

class Alarm implements Runnable{
	
	private VisibleClient client;
	String pause ;
	
	public Alarm(VisibleClient v) {
		this.client = v;
	}
	
	@Override
	public void run() {
		
		while(true){
			
			try {
				counter();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			addAlarm();
			
			myAlarm();
		}
		
	}


	/**
	 * 
	 */
	public synchronized void myAlarm() {
		if(client.sbCom.toString().contains(";")){
			
			String [] str = client.sbCom.toString().split(";");
			for(int i=0; i<str.length; i++){
				String [] s = str[i].toString().split("m");
				String [] s0 = s[0].toString().split("-");
				String [] s1 = s[1].toString().split(":");
				if((Integer.parseInt(s0[0])==client.year) && (Integer.parseInt(s0[1])==client.month) && (Integer.parseInt(s0[2])==client.date) && 
						(Integer.parseInt(s1[0])==client.hour) && (Integer.parseInt(s1[1])==client.minute)
						&& (Integer.parseInt(s1[2])==client.second) ){
//					JOptionPane.showMessageDialog(null, "有活动啦！", "闹钟", JOptionPane.PLAIN_MESSAGE);
					client.optionFlag = 1;
					new Thread (new MyOptionPane(client)).start();													//		why??????????????????????????????????????
				}
			}
		}
	}

	/**
	 * 
	 */
	public void addAlarm() {
		
		client.btnNewButton.addMouseListener(new MouseAdapter() {													//		是不是相当于开了一个线程？？？？？？？？？？？？
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if((pause==null) || !(pause.equals(client.textField.getText()+""+client.textField_1.getText()))){
					pause = client.textField.getText()+""+client.textField_1.getText();
					if(client.textField.getText().contains("-") && client.textField_1.getText().contains(":")){
						if( (client.textField.getText().split("-").length == 3) && (client.textField_1.getText().split(":").length == 3) ){
							String [] inputA = client.textField.getText().split("-");
							String [] inputB = client.textField_1.getText().split(":");
							if((Integer.parseInt(inputA[0])>2000)&&(Integer.parseInt(inputA[0])<2100) && (Integer.parseInt(inputA[1])>0)&&
									(Integer.parseInt(inputA[1])<13) && (Integer.parseInt(inputA[2])>0)&&(Integer.parseInt(inputA[2])<32)&&
									(Integer.parseInt(inputB[0])<25)&&((Integer.parseInt(inputB[0])>=0)) && (Integer.parseInt(inputB[1])<60)&&
									(Integer.parseInt(inputB[1])>=0) && (Integer.parseInt(inputB[2])>=0)&&(Integer.parseInt(inputB[2])<60)
									){
								
								client.sb.append("活动:\t");
								client.sb.append(client.textField_2.getText());
								client.sb.append("\n时间:\t");
								client.sb.append(client.textField.getText());
								client.sb.append("\t");
								client.sb.append(client.textField_1.getText());
								client.sb.append("\n\n");
								client.textArea.setText(client.sb.toString());
								client.sbCom.append(client.textField.getText());
								client.sbCom.append("m");
								client.sbCom.append(client.textField_1.getText());
								client.sbCom.append(";");
							}
							else
								JOptionPane.showMessageDialog(null, "输入格式有误！请按以下格式输入：\r\n\t日期：\t2017-5-25\r\n\t时间：\t14:2:32", "错误", JOptionPane.ERROR_MESSAGE);
						}
						else
							JOptionPane.showMessageDialog(null, "输入格式有误！请按以下格式输入：\r\n\t日期：\t2017-5-25\r\n\t时间：\t14:2:32", "错误", JOptionPane.ERROR_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null, "输入格式有误！请按以下格式输入：\r\n\t日期：\t2017-5-25\r\n\t时间：\t14:2:32", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	

	public synchronized void counter() throws InterruptedException {
		client.contentPane.updateUI();
		client.label_3.setText(timer());
		Thread.sleep(1000);
	}

	public String timer() {
		client.second = (client.second == 59)? 0 : client.second+1 ;
		if(client.second == 0){
			client.minute = (client.minute == 59)?  0 : client.minute+1;
			if(client.minute == 0){
				client.hour = (client.hour == 23)? 0 : client.hour+1;
			}
		}
		
		client.timeStr = new StringBuilder();
		client.timeStr.append(String.format("%2d",client.hour)+":");
		client.timeStr.append((client.minute<10)?"0"+client.minute:client.minute);
		client.timeStr.append(":");
		client.timeStr.append((client.second<10)?"0"+client.second:client.second);
		return client.timeStr.toString();
	}
	
}

class TimeModify implements Runnable{

	private VisibleClient client;
	
	public TimeModify(VisibleClient v) {
		this.client = v;
	}
	
	@Override
	public void run() {
		client.button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(client.comboBox.getSelectedItem().toString().equals("时"))
					client.hour++;
				else if(client.comboBox.getSelectedItem().toString().equals("分"))
					client.minute++;
				else
					client.second++;
			}
		});
		client.button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(client.comboBox.getSelectedItem().toString().equals("时"))
					client.hour--;
				else if(client.comboBox.getSelectedItem().toString().equals("分"))
					client.minute--;
				else
					client.second--;
			}
		});
	}
}


public class VisibleClient {

	public JPanel contentPane;
	public JTextField textField;
	public JTextField textField_1;
	public JTextField textField_2;
	public JFrame frame;
	public JLabel lblNewLabel;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel lblNewLabel_1;
	public JLabel label_3;
	public JLabel label_4;
	public JButton button;
	public JButton button_1;
	public JComboBox comboBox;
	public JButton btnNewButton;
	public JTextArea textArea;
	public JScrollPane scrollPane_1;
	
	GregorianCalendar gc = new GregorianCalendar();
	public int year = gc.get(GregorianCalendar.YEAR);
	public int month = gc.get(GregorianCalendar.MONTH)+1;
	public int date = gc.get(GregorianCalendar.DATE);
	public int hour = gc.get(GregorianCalendar.HOUR);
	public int minute = gc.get(GregorianCalendar.MINUTE);
	public int second = gc.get(GregorianCalendar.SECOND);
	
	public int optionFlag = 0;

	public StringBuilder timeStr;
	
	StringBuilder sb = new StringBuilder();
	StringBuilder sbCom = new StringBuilder();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		VisibleClient client = new VisibleClient();
		new Thread (new TimeModify(client)).start();
//		new Thread (new MyOptionPane(client)).start();
		new Thread (new Client(client)).start();
		new Thread (new Alarm(client)).start();
	}

	/**
	 * Create the frame.
	 */
	public VisibleClient() {
		
		frame = new JFrame("VisibleClient");
		frame.setTitle("闹钟");
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 664, 622);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("\u65F6\u95F4");
		lblNewLabel.setBounds(132, 97, 96, 45);
		lblNewLabel.setFont(new Font("等线", Font.BOLD, 25));
		contentPane.add(lblNewLabel);
		
		label = new JLabel("\u65E5\u671F");
		label.setBounds(132, 34, 96, 45);
		label.setFont(new Font("等线", Font.BOLD, 25));
		contentPane.add(label);
		
		label_1 = new JLabel("\u95F9\u949F\u65E5\u671F");
		label_1.setBounds(110, 181, 118, 45);
		label_1.setFont(new Font("等线", Font.BOLD, 25));
		contentPane.add(label_1);
		
		label_2 = new JLabel("\u95F9\u949F\u65F6\u95F4");
		label_2.setBounds(110, 248, 118, 45);
		label_2.setFont(new Font("等线", Font.BOLD, 25));
		contentPane.add(label_2);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(241, 29, 170, 60);
		lblNewLabel_1.setFont(new Font("等线", Font.BOLD, 31));
		contentPane.add(lblNewLabel_1);
		
		label_3 = new JLabel("");
		label_3.setBounds(241, 96, 170, 60);
		label_3.setFont(new Font("等线", Font.BOLD, 31));
		contentPane.add(label_3);
		
		textField = new JTextField();
		textField.setBounds(241, 177, 170, 50);
		textField.setFont(new Font("等线", Font.BOLD, 32));
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(241, 248, 170, 50);
		textField_1.setFont(new Font("等线", Font.BOLD, 32));
		textField_1.setColumns(10);
		contentPane.add(textField_1);
		
		label_4 = new JLabel("\u5DF2\u8BBE\u5B9A\u7684\u95F9\u949F");
		label_4.setBounds(62, 418, 184, 45);
		label_4.setFont(new Font("等线", Font.BOLD, 25));
		contentPane.add(label_4);
		
		comboBox = new JComboBox();
		comboBox.setBounds(435, 49, 68, 21);
		comboBox.addItem("时");
		comboBox.addItem("分");
		comboBox.addItem("秒");
		contentPane.add(comboBox);

		button = new JButton("+");
		button.setBounds(435, 97, 68, 32);
		contentPane.add(button);
		
		button_1 = new JButton("-");
		button_1.setBounds(435, 127, 68, 29);
		contentPane.add(button_1);
		
		btnNewButton = new JButton("\u786E\u8BA4\u8F93\u5165");
		btnNewButton.setBounds(435, 322, 96, 45);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(241, 415, 268, 116);
		contentPane.add(scrollPane_1);
		
		textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		
//		GregorianCalendar gc = new GregorianCalendar();
		lblNewLabel_1.setText(year+"-"+month+"-"+date);
		
		JLabel label_5 = new JLabel("\u5E72\u5565");
		label_5.setFont(new Font("等线", Font.BOLD, 25));
		label_5.setBounds(110, 327, 118, 45);
		contentPane.add(label_5);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("等线", Font.BOLD, 32));
		textField_2.setColumns(10);
		textField_2.setBounds(241, 322, 170, 50);
		contentPane.add(textField_2);
	}

}
