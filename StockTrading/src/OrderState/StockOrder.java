package OrderState;

import messagebridge.DesignFormat;
import messagebridge.Notification;
import messagebridge.NotifyFormat;
import messagebridge.Report;
import Requestmanager.Manager;
import SecurityCode.Account;
import SecurityDecorator.Security;
import SecurityDecorator.Stock;



public class StockOrder extends Order{

	protected OrderState orderMatching;
    protected Stock stock;
	protected double priceSet;
	DesignFormat message;
	Notification notification;
	Report report;
	
	public StockOrder(String sell_buy, String type,Security security,int shareN,long profileId,Account acc){
		super(sell_buy,type,security,shareN,profileId,acc);

		
			orderState = orderReceived;
            status = Order.PENDING;
            
            stock = (Stock)security;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			if (obj instanceof StockOrder) {
				StockOrder stockOrder = (StockOrder) obj;
				if (stock.equals(stockOrder.getStock())&& priceSet == stockOrder.getPriceSet()) {
					return true;
				}
				return false;
			}
		}
		return false;
	}
	
	public OrderState getOrderMatchingState(){
		return orderMatching;
	}
	
	public double getPriceSet() {
		return priceSet;
	}

	public void setPriceSet(double priceSet) {
		this.priceSet = priceSet;
	}
 
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock s) {
		this.stock = s;
	}


	@Override
	public void notifyAccount(String type) {
		// TODO Auto-generated method stub
		String msg="";
		boolean trans_Result;
		trans_Result = account.doTransaction(type,this);
		if(trans_Result){
			status = Order.FULFILLED;
			msg=" transaction has been made...";
		}
		else
			msg = "error during transaction in account..."; 
	}


	@Override
	public void notifyMessenger() {
		// TODO Auto-generated method stub
		message=new NotifyFormat();
		notification=new Notification(message,"Order Fulfilled");
		notification.generate_message();
		notification.send_message();
	}


	@Override
	public void notifySecurity() {
		// TODO Auto-generated method stub
		for(Stock s:Manager.st){
			if(s.equals(stock)){
				s.setPrice(stock.getPrice());
			}
		}
	}

}
