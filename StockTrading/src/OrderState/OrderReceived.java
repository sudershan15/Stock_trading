package OrderState;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import Requestmanager.Manager;


public class OrderReceived implements OrderState{
    private Order order;
    
    public OrderReceived(Order o){
    	this.order = o;
    }

	@Override
	public String processingOrder() {
		// TODO Auto-generated method stub
		String msg=null;
		if(order.orderType.equals(Order.LIMITED_ORDER)){
			LimitedOrder limitedOrder = (LimitedOrder)order;

			//check if order is cancelled
			if(limitedOrder.getStatus().equals(Order.CANCELLED)){
				limitedOrder.setState(limitedOrder.getOrderInactiveState());
				msg="Limited Order has been cancelled....";
			}

			//according to sell/buy order, put into the right queue
			if(limitedOrder.seller_Or_buyer.equals(Order.SELLER)){

				//put in Queue
				Queue queue = limitedOrder.getStock().getQ();
				TreeMap<Double,Order> seller_Queue = queue.getSellerOrderQueue();
				synchronized(queue){		
				seller_Queue.put(limitedOrder.getPriceSet(),limitedOrder);
				queue.notifyAll();				
				}
				msg="Limited Order is put into seller queue...";
				
				//set State
				limitedOrder.setState(limitedOrder.getOrderWaiting());
				limitedOrder.setStatus(Order.PENDING);
				limitedOrder.processingOrder();
			}
			else if(limitedOrder.seller_Or_buyer.equals(Order.BUYER)){

				//put in Queue
				Queue queue = limitedOrder.getStock().getQ();
				TreeMap<Double,Order> buyer_Queue = queue.getBuyerOrderQueue();
				synchronized(queue){		
				buyer_Queue.put(limitedOrder.getPriceSet(),limitedOrder);
				queue.notifyAll();
				}
				msg="Limited Order is put into buyer queue...";
				
				//set State
				limitedOrder.setState(limitedOrder.getOrderWaiting());
				limitedOrder.setStatus(Order.PENDING);
				limitedOrder.processingOrder();
			}
									
		}
		else if(order.orderType.equals(Order.MUTUAL_FUND_ORDER)){
			MutualFundOrder mutualFundOrder = (MutualFundOrder)order;
			msg = "Mutual Fund Order is waiting for price update at 4pm....";
			
			if(Manager.clock == 1600){
				mutualFundOrder.setState(mutualFundOrder.getOrderFulfilledState());
				msg = "Mutual Fund Order is going to be fulfilled....";
				mutualFundOrder.processingOrder();
			}
			
		}
	
	    return msg;
	
	}
    
}
