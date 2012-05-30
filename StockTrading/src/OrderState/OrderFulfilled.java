package OrderState;

import java.util.TreeMap;

import Requestmanager.Manager;
import SecurityDecorator.MutualFund;
import SecurityDecorator.Stock;

public class OrderFulfilled implements OrderState {
	private Order order;

	public OrderFulfilled(Order o) {
		this.order = o;
	}

	@Override
	public String processingOrder() {
		// TODO Auto-generated method stub
		String msg = null;
		if (order.orderType.equals(Order.MARKET_ORDER)) {
			MarketOrder marketOrder = (MarketOrder) order;
			// Market order will not have CANCEL state
			if (marketOrder.getSeller_Or_buyer().equals(Order.SELLER)) {
				Stock stock = marketOrder.getStock();
				for(int i=0;i<Manager.st.size();i++){
					Stock s = Manager.st.get(i);
					if(s.equals(stock)){
						if (s.getnumber() >= marketOrder.getShare_Number()) {
							s.setnumber(s.getnumber()
									+ marketOrder.getShare_Number());
							Manager.st.set(i, s);
							marketOrder.setPriceSet(s.getPrice());
					}
				}
				}
				// do transaction by notify account
				marketOrder.notifyAccount(Order.SELLER);
				marketOrder.notifyMessenger();
				marketOrder.setStatus(Order.FULFILLED);
				msg = "order has been fulfilled....";

				// setState and status
			} else if(marketOrder.getSeller_Or_buyer().equals(Order.BUYER)){
				Stock stock = marketOrder.getStock();
					// do transaction by notify account
					for(int i=0;i<Manager.st.size();i++){
						Stock s = Manager.st.get(i);
						if(s.equals(stock)){
							if (s.getnumber() >= marketOrder.getShare_Number()) {
								s.setnumber(s.getnumber()
										- marketOrder.getShare_Number());
								Manager.st.set(i, s);
								marketOrder.setPriceSet(s.getPrice());
						}
					}
					}
					marketOrder.notifyAccount(Order.BUYER);
					marketOrder.notifyMessenger();
					marketOrder.setStatus(Order.FULFILLED);

				// setState and status
				msg = "order has been fulfilled....";
			}
		}
		if (order.orderType.equals(Order.LIMITED_ORDER)) {
			LimitedOrder limitedOrder = (LimitedOrder) order;
			limitedOrder.notifyAccount(order.getSeller_Or_buyer());
			limitedOrder.notifyMessenger();
			limitedOrder.notifySecurity();
			System.out.println("");
			limitedOrder.setStatus(Order.FULFILLED);
			msg = "order has been fulfilled....";
		}
		if (order.orderType.equals(Order.MUTUAL_FUND_ORDER)) {
			MutualFundOrder mutualFundOrder = (MutualFundOrder) order;
			// update stock price and MF price
			if (mutualFundOrder.getSeller_Or_buyer().equals(Order.SELLER)) {
				// do transaction by notify account
				MutualFund mf = (MutualFund) mutualFundOrder.getSecurity();
				for(int i=0;i<Manager.mf.size();i++){
					MutualFund mf_From_Market = Manager.mf.get(i);
					if(mf_From_Market.equals(mf)){
						mf_From_Market.updateStockPrice();
						mf_From_Market.setPrice(mf_From_Market.caculatePrice());
						mf_From_Market.setnumber(mf_From_Market.getnumber()+mutualFundOrder.getShare_Number());
				        mutualFundOrder.setPriceSet(mf_From_Market.getPrice());
				        Manager.mf.set(i, mf_From_Market);
					}
				}
				mutualFundOrder.notifyAccount(Order.SELLER);
				mutualFundOrder.notifyMessenger();
				mutualFundOrder.setStatus(Order.FULFILLED);
				msg = "order has been fulfilled....";
			} else if (mutualFundOrder.getSeller_Or_buyer().equals(Order.BUYER)) {
				MutualFund mf = (MutualFund) mutualFundOrder.getSecurity();
				for(int i=0;i<Manager.mf.size();i++){
					MutualFund mf_From_Market = Manager.mf.get(i);
					if(mf_From_Market.equals(mf)){
						mf_From_Market.updateStockPrice();
						mf_From_Market.setPrice(mf_From_Market.caculatePrice());
				        mutualFundOrder.setPriceSet(mf_From_Market.getPrice());
						mf_From_Market.setnumber(mf_From_Market.getnumber()-mutualFundOrder.getShare_Number());
				        Manager.mf.set(i, mf_From_Market);
					}
				}
				mutualFundOrder.notifyAccount(Order.BUYER);
				mutualFundOrder.notifyMessenger();
				mutualFundOrder.setStatus(Order.FULFILLED);
				msg = "order has been fulfilled....";
			}
		}
		return msg;
	}

}
