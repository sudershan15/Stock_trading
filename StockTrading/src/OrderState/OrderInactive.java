package OrderState;

public class OrderInactive implements OrderState{
    private Order order;
    
    public OrderInactive(Order o){
    	this.order = o;
    }

	@Override
	public String processingOrder() {
		// TODO Auto-generated method stub
		String msg = "Order is expired....";
		System.out.println(msg);
		return msg;
	}


    
}
