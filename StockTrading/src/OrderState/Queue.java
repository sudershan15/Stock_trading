package OrderState;
import java.util.ArrayList;
import java.util.TreeMap;

import Requestmanager.Manager;


public class Queue implements Runnable{

	private long stockId;
	private TreeMap<Double,Order> sellerOrderQueue;
	private TreeMap<Double,Order> buyerOrderQueue;
	
	public Queue(long stockId){
		this.stockId = stockId;
		sellerOrderQueue = new TreeMap<Double,Order>();
		buyerOrderQueue = new TreeMap<Double,Order>();
		Thread runner = new Thread(this);
		runner.start();
	}
	
	public void run() {
		for (;;)
			try {
				//in case new order insert into queue while getting order from queue
						synchronized(this){
						if(sellerOrderQueue.isEmpty()||buyerOrderQueue.isEmpty()){
						    //System.out.println("at least one Q is empty right now,wait for order to be inserted....");
							if(Manager.clock<800||Manager.clock>=1600)
							    System.out.println("Market is closed, all matching has stopped and all queues have been cleared....");
						wait();
						}
						
							if (Manager.clock >= 800 && Manager.clock < 1600) {
					System.out.println("******************************");
					//System.out.println("matching start.....");
					
					LimitedOrder sellerOrder,buyerOrder;
                    // highest price in seller queue and lowest price in buyer queue
					sellerOrder = (LimitedOrder) sellerOrderQueue.get(sellerOrderQueue.lastKey());					
					buyerOrder = (LimitedOrder) buyerOrderQueue.get(buyerOrderQueue.firstKey());
						

					int seller_Share_Num, buyer_Share_Num;
					seller_Share_Num = sellerOrder.getShare_Number();
					System.out.println("seller Q....lowest price order...share num...."+ seller_Share_Num);
					buyer_Share_Num = buyerOrder.getShare_Number();
					System.out.println("buyer Q....highest price order...share num...."	+ buyer_Share_Num);


					if (sellerOrder.getPriceSet() == buyerOrder.getPriceSet()) {
						if (seller_Share_Num == buyer_Share_Num) {
							System.out.println("order matched.....");
							// change order state and delete from queue
							sellerOrder.setState(sellerOrder
									.getOrderFulfilledState());
							sellerOrder = (LimitedOrder) sellerOrderQueue
									.remove(sellerOrder.getPriceSet());

							buyerOrder.setState(buyerOrder
									.getOrderFulfilledState());
							buyerOrder = (LimitedOrder) buyerOrderQueue
									.remove(buyerOrder.getPriceSet());
							
							
							// update order status in acc, update stock price,
							// generate message for both buyer & seller
							sellerOrder.processingOrder();
							buyerOrder.processingOrder();
							Thread.sleep(1000);
						}
						if (seller_Share_Num < buyer_Share_Num) {
							sellerOrder.setState(sellerOrder
									.getOrderFulfilledState());
							sellerOrder = (LimitedOrder) sellerOrderQueue
									.remove(sellerOrder.getPriceSet());

							// buyer Order splitted
							int left_Buyer_Order_Share_Num = buyer_Share_Num- seller_Share_Num;
							LimitedOrder matched_BuyerOrder = new LimitedOrder(Order.BUYER,Order.LIMITED_ORDER,
									buyerOrder.getSecurity(), seller_Share_Num,
									buyerOrder.getProfileId(),
									buyerOrder.getAccount(),
									buyerOrder.getPriceSet(),
									buyerOrder.getLimitDate());
							buyerOrder.setShare_Number(left_Buyer_Order_Share_Num);
							buyerOrder.setStatus(Order.SPLITTED);
							// put splitted buyer order back into buyer Queue
							buyerOrderQueue.put(buyerOrder.getPriceSet(), buyerOrder);
							System.out.println("buyer order is slpitted....");
							matched_BuyerOrder.setState(matched_BuyerOrder.getOrderFulfilledState());
							
							// update order status in acc, update stock price,
							// generate message for both buyer & seller
							sellerOrder.processingOrder();
							matched_BuyerOrder.processingOrder();
							Thread.sleep(1000);

						}
						if (seller_Share_Num > buyer_Share_Num) {
							buyerOrder.setState(buyerOrder.getOrderFulfilledState());
							buyerOrder = (LimitedOrder) buyerOrderQueue.remove(buyerOrder.getPriceSet());

							// seller Order splitted
							int left_Seller_Order_Share_Num = seller_Share_Num- buyer_Share_Num;
							LimitedOrder matched_SellerOrder = new LimitedOrder(Order.SELLER, 
									Order.LIMITED_ORDER,
									sellerOrder.getSecurity(), buyer_Share_Num,
									sellerOrder.getProfileId(),
									sellerOrder.getAccount(),
									sellerOrder.getPriceSet(),
									sellerOrder.getLimitDate());
							sellerOrder.setShare_Number(left_Seller_Order_Share_Num);
							sellerOrder.setStatus(Order.SPLITTED);
							// put back into buyer Queue
							sellerOrderQueue.put(sellerOrder.getPriceSet(), sellerOrder);
							matched_SellerOrder.setState(matched_SellerOrder.getOrderFulfilledState());
							
					        buyerOrder.processingOrder();
							matched_SellerOrder.processingOrder();
							Thread.sleep(1000);
						}
					}
					else{
						System.out.println("no matching in the current queue, waiting for new order to be inserted...");
						wait();
					}
					System.out.println("******************************");
				} }
			} catch (Exception x) {
			}
				
	}
	
	public long getSecurityId() {
		return stockId;
	}
	public void setSecurityId(long securityId) {
		this.stockId = securityId;
	}
	public TreeMap<Double,Order> getSellerOrderQueue() {
		return sellerOrderQueue;
	}
	public void setSellerOrderQueue(TreeMap<Double,Order> sellOrder) {
		sellerOrderQueue = sellOrder;
	}
	public TreeMap<Double,Order> getBuyerOrderQueue() {
		return buyerOrderQueue;
	}
	public void setBuyerOrderQueue(TreeMap<Double,Order> buyOrder) {
		buyerOrderQueue = buyOrder;
	}
	
	public void deleteCancelledOrder(){
		
	}

	//add order into queue at 8am when market is open
	public void fillQueue(ArrayList<Order> orderList){
		for(Order o:orderList){
			if(o instanceof LimitedOrder){
				if(o.getStatus().equals(Order.PENDING)||o.getStatus().equals(Order.SPLITTED)){
					if(o.getSeller_Or_buyer().equals(Order.BUYER)){
						buyerOrderQueue.put(((LimitedOrder)o).getPriceSet(), o);
					}
					else if(o.getSeller_Or_buyer().equals(Order.SELLER)){
						sellerOrderQueue.put(((LimitedOrder)o).getPriceSet(), o);
					}
				}
			}
		}
	}
	
	//clear queue at 4pm when market is close
	public void refineQueue(){
		System.out.println("refining queue....");
		buyerOrderQueue.clear();
		sellerOrderQueue.clear();
	}
}
