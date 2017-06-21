package Ex5;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

class Notice
{
	public String[] startTime = null;
	public String[] endTime = null;
	public String[] location = null;
	public String[] activity = null;
	
	public boolean sign = false;
	public String str = "";
	
	public void flush() throws IOException
	{
		this.startTime = getClassName("announcement.properties", "startTime");
		this.endTime = getClassName("announcement.properties", "endTime");
		this.location = getClassName("announcement.properties", "location");
		this.activity = getClassName("announcement.properties", "activity");
		
		str = myToString();
		this.sign = true;
		System.out.println(str);
	}

	/**
	 * 
	 */
	public String myToString() 
	{
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<this.startTime.length; i++)
		{
			sb.append("活动"+":\t"+this.activity[i]+"\n时间:\t"+this.startTime[i]+" -- "+this.endTime[i]+"\t地点:\t"+this.location[i]+"\n");
		}
		return sb.toString();
	}
	
	public String[] getClassName(String propName , String announceType) throws IOException 
	{
		Properties prop = new Properties();  
		prop.load(MainServer.class.getResourceAsStream(propName)); 
		return prop.getProperty(announceType).split(";");
	}
}


class Poll implements Runnable
{
	private Notice notice;
	
	Poll(Notice n)
	{
		this.notice = n;
	}
	
	public void run()
	{
		try {
			
			notice.flush();

			while(true)
			{
					Thread.sleep(30000);
					
					if( isUpdated() )
					{
						notice.flush();
					}
					else
					{
						System.out.println("无改动。");
					}
					
			}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public boolean isUpdated() throws IOException {
		return !(	Arrays.toString(notice.startTime).equals(Arrays.toString(notice.getClassName("announcement.properties", "startTime")))
		&& 	Arrays.toString(notice.endTime).equals(Arrays.toString(notice.getClassName("announcement.properties", "endTime")))
		&& 	Arrays.toString(notice.location).equals(Arrays.toString(notice.getClassName("announcement.properties", "location")))
		&& 	Arrays.toString(notice.activity).equals(Arrays.toString(notice.getClassName("announcement.properties", "activity"))));
	}
}


public class MainServer {
	public static void main(String[] args) throws IOException {
		Notice notice = new Notice();
		new Thread(new Poll(notice)).start();
		new Thread(new Server(notice)).start();		
	}

}

