package OrderState;

import messagebridge.DesignFormat;
import messagebridge.Notification;
import messagebridge.NotifyFormat;
import messagebridge.Report;
import Requestmanager.Manager;
import SecurityCode.Account;
import SecurityDecorator.MutualFund;
import SecurityDecorator.Security;

public class MutualFundOrder extends Order{
	protected MutualFund mf;
	protected double priceSet;
	DesignFormat message;
	Notification notification;
	Report report;
	
	public MutualFundOrder(String sell_buy,String type,Security security,int shareN,long profileId,Account acc){
		super(sell_buy,type,security,shareN,profileId,acc);

			orderState = orderReceived;
            status = Order.PENDING;
            
            mf = (MutualFund)security;
}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof MutualFundOrder){
			MutualFundOrder mfOrder = (MutualFundOrder)obj;
				if(mf.equals(mfOrder.getMf())&&priceSet==mfOrder.getPriceSet()){
					return true;
				}
				return false;
			}
			return false;
	}

	
	public MutualFund getMf() {
		return mf;
	}

	public void setMf(MutualFund mf) {
		this.mf = mf;
	}

	public double getPriceSet() {
		return priceSet;
	}

	public void setPriceSet(double priceSet) {
		this.priceSet = priceSet;
	}

	//for observer pattern
	public void setPrice(double p){}
	public double getPrice(){return 1.00;}

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

}
