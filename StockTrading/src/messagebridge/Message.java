

package messagebridge;

import SecurityCode.Portfolio;

public abstract class Message{
	protected DesignFormat _d;
		
	public abstract void generate_message();
	public abstract void send_message();
	
	public Message(DesignFormat d){
		_d=d;
	}
	
	protected void generate_notification(String msg){
		_d.generate_notification(msg);
	}
	
	protected void send_notification(){
		_d.send_notification();
	}
	
	protected void generate_report(Portfolio p){
		_d.generate_report(p);
	}
	
	protected void send_report(){
		_d.send_report();
	}
}