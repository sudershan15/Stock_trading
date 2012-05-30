package OrderState;
import Requestmanager.Manager;


public class OrderWaiting implements OrderState{
    private Order order;
    
    public OrderWaiting(Order o){
    	this.order = o;
    }

	@Override
	public String processingOrder() {
		// TODO Auto-generated method stub
		String msg = "";
		
		if(order.orderType.equals(Order.LIMITED_ORDER)){
		LimitedOrder limitedOrder = (LimitedOrder)order;
		long currentTime = Manager.clock;
		if(currentTime == 1600){
			boolean isExpired;
			isExpired=limitedOrder.isExpired();//check if it is Expired
			if(isExpired){
				limitedOrder.setState(limitedOrder.getOrderInactiveState());
				limitedOrder.setStatus(Order.EXPIRED);
				msg = "Limited Order is expired....";
				System.out.println(msg);
				limitedOrder.processingOrder();
			}
			else if(!isExpired){
				System.out.println("Limited Order is still valid....");
			}
		}
	}

	return null;
	}

}
