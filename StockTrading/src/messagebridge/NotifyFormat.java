package messagebridge;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import SecurityCode.Account;
import SecurityCode.Portfolio;

public class NotifyFormat extends DesignFormat{

	String header,body, notification,report;
	Account acc;
	
	@Override
	public void generate_notification(String msg) {
		// TODO Auto-generated method stub
		header="This is a notification generated on ";
		body=msg;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		notification= header+dateFormat.format(cal.getTime())+" : \n"+body;
	}

	@Override
	public void send_notification() {
		// TODO Auto-generated method stub
		System.out.println(notification);
	}

	@SuppressWarnings("null")
	@Override
	public void generate_report(Portfolio p) {
		// TODO Auto-generated method stub
		header= "\t\t--x Order Status x--\n";
		long[] accid = null;
		for(int i=0;i<=p.acc.size();i++){
			accid[i]=p.acc.get(i).getAccId();
			System.out.println("Account: "+accid[i]);
		}
		String accTotalInfo="";
		String accInfo = acc.toString();
		{
		accTotalInfo += accInfo;
		}
		
		report=accTotalInfo;
		
	}

	@Override
	public void send_report() {
		// TODO Auto-generated method stub
		System.out.println(report);
	}
	
}