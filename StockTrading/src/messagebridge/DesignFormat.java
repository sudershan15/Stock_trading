package messagebridge;

import SecurityCode.Portfolio;

public abstract class DesignFormat {

	abstract public void generate_notification(String msg);
	abstract public void send_notification();
	abstract public void generate_report(Portfolio p);
	abstract public void send_report();
	
}
