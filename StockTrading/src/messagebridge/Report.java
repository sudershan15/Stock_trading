package messagebridge;

import SecurityCode.Portfolio;

public class Report extends Message{

	public Report(DesignFormat d) {
		super(d);
		// TODO Auto-generated constructor stub
	}

	public void generate_message(Portfolio p) {
		// TODO Auto-generated method stub
		generate_report(p);
	}

	@Override
	public void send_message() {
		// TODO Auto-generated method stub
		send_report();
	}

	@Override
	public void generate_message() {
		// TODO Auto-generated method stub
		//generate_message();
	}

	
}