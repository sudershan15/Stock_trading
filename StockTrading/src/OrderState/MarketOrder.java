package OrderState;

import SecurityCode.Account;
import SecurityDecorator.Security;
import SecurityDecorator.Stock;

public class MarketOrder extends StockOrder{

	public MarketOrder(String sell_buy, String type,Security security,int shareN,long profileId,Account acc){
		super(sell_buy,type,security,shareN,profileId,acc);
		orderState = orderFulfilled;
		orderState.processingOrder();
	}
	
	@Override
	public boolean equals(Object obj){
		boolean result = super.equals(obj);
		return result;
	}
	
	

}
