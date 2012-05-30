package messagebridge;

public class Notification extends Message{

	String _msg;
	
	public Notification(DesignFormat d, String msg1) {
		super(d);
		_msg=msg1;
	}

	@Override
	public void generate_message() {
		// TODO Auto-generated method stub
		generate_notification(_msg);
	}

	@Override
	public void send_message() {
		// TODO Auto-generated method stub
		send_notification();
	}
	
}