/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SecurityCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.TreeMap;

import OrderState.LimitedOrder;
import OrderState.MarketOrder;
import OrderState.MutualFundOrder;
import OrderState.Order;
import OrderState.Queue;
import OrderState.StockOrder;
import Requestmanager.Manager;
import SecurityDecorator.MutualFund;
import SecurityDecorator.Security;
import SecurityDecorator.Stock;

/**
 * 
 * @author Vikrant
 */
public class Account {
	public long uid;
	String orderid;
	public String accname;
	public long cashacc;
	protected String email;
	protected long accId;
	protected double availableCash;
	protected ArrayList<Stock> stockList=new ArrayList<Stock>();
	protected ArrayList<MutualFund> mfList = new ArrayList<MutualFund>();
	public static ArrayList<Order> orderList = new ArrayList<Order>();;

	// public ArrayList<Account> acc= new ArrayList<Account>();

	public Account(int userid, String name, String email, long accId,
			double availableCash) {
		super();
		uid = userid;
		accname = name;
		this.email = email;
		this.accId = accId;
		this.availableCash = availableCash;
		// this.stockList=stockList;
	}

	public boolean doTransaction(String transType, Order order) {
		boolean result = false;
		if (transType.equals(Order.SELLER)) {
			if (order instanceof LimitedOrder) {
				StockOrder stockOrder = (StockOrder) order;
				Stock stock = stockOrder.getStock();
				for (Stock s : stockList) {
					if (s.equals(stock)) {
						s.setnumber(s.getnumber()
								- stockOrder.getShare_Number());
						availableCash += stockOrder.getPriceSet()
								* stockOrder.getShare_Number();
						result = true;
						return result;
					}
				}
			}else if (order instanceof MarketOrder) {
				MarketOrder marketOrder = (MarketOrder) order;
				Stock stock = marketOrder.getStock();
				for (int i=0;i<stockList.size();i++) {
					Stock s = stockList.get(i);
					if (s.equals(stock)) {
						s.setnumber(s.getnumber()
								- marketOrder.getShare_Number());
						availableCash += marketOrder.getPriceSet()
								* marketOrder.getShare_Number();
						result = true;
						stockList.set(i, s);
						return result;
					}
				}
			}else if (order instanceof MutualFundOrder) {
				MutualFundOrder mfOrder = (MutualFundOrder) order;
				MutualFund mf_From_Order = mfOrder.getMf();
				for (int i=0;i<mfList.size();i++) {
					MutualFund mf = mfList.get(i);
					if (mf.equals(mf_From_Order)) {
						mf.setnumber(mf.getnumber() - mfOrder.getShare_Number());
						availableCash += mfOrder.getPriceSet()
								* mfOrder.getShare_Number();
						mfList.set(i, mf);
						result = true;
						return true;
					}
				}
			}
		} else if (transType.equals(Order.BUYER)) {
			if (order instanceof LimitedOrder) {
				StockOrder stockOrder = (StockOrder) order;
				Stock stock = stockOrder.getStock();
				for (Stock s : stockList) {
					if (s.equals(stock)) {
						s.setnumber(s.getnumber()
								+ stockOrder.getShare_Number());
						availableCash -= stockOrder.getPriceSet()
								* stockOrder.getShare_Number();
						result = true;
						return result;
					}
				}
				Stock new_Stock = new Stock(stockList.size()+1,stock.getStocksymbol(),stockOrder.getPriceSet(),stockOrder.getShare_Number());
				stockList.add(new_Stock);
				availableCash -= stockOrder.getPriceSet()
						* stockOrder.getShare_Number();
				result = true;
			} else if (order instanceof MarketOrder) {
				MarketOrder marketOrder = (MarketOrder) order;
				Stock stock = marketOrder.getStock();
				for (int i=0;i<stockList.size();i++) {
					Stock s = stockList.get(i);
					if (s.equals(stock)) {
						s.setnumber(s.getnumber()
								- marketOrder.getShare_Number());
						availableCash += marketOrder.getPriceSet()
								* marketOrder.getShare_Number();
						result = true;
						stockList.set(i, s);
						return result;
					}
				}
				Stock new_Stock = new Stock(stockList.size()+1,stock.getStocksymbol(),stock.getPrice(),marketOrder.getShare_Number());
				stockList.add(new_Stock);
				availableCash -= marketOrder.getPriceSet()
						* marketOrder.getShare_Number();

			} else if (order instanceof MutualFundOrder) {
				double mfTotalPrice = 0;
				MutualFundOrder mfOrder = (MutualFundOrder) order;
				MutualFund mf_From_Order = mfOrder.getMf();
				for (int i=0;i<mfList.size();i++) {
					MutualFund mf = mfList.get(i);
					if (mf.equals(mf_From_Order)) {
						mf.setnumber(mf.getnumber() + mfOrder.getShare_Number());
						mfTotalPrice = mfOrder.getPriceSet()
								* mfOrder.getShare_Number();
						// System.out.println(mfOrder.getPriceSet()+" "+mfOrder.getShare_Number());
						if (availableCash < mfTotalPrice) {
							System.out
									.println("not enough cash to afford current mutual fund price....");
							result = false;
							return result;
						} else {
							availableCash -= mfTotalPrice;
							mfList.set(i,mf);
						}
						result = true;
						return true;
					}
				}
				mfTotalPrice = mfOrder.getPriceSet()
						* mfOrder.getShare_Number();
				if (availableCash < mfTotalPrice) {
					System.out
							.println("not enough cash to afford current mutual fund price....");
					result = false;
					return result;
				} else { 
					MutualFund new_MF = new MutualFund(mfList.size()+1,mf_From_Order.getMFname(),mfOrder.getShare_Number(),mf_From_Order.getStkList());
					mfList.add(new_MF);
					availableCash -= mfTotalPrice;
				}
				result = true;
			}
		}
		return result;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getAccId() {
		return accId;
	}

	public void setAccId(long accId) {
		this.accId = accId;
	}

	public double getAvailableCash() {
		return availableCash;
	}

	public void setAvailableCash(double availableCash) {
		this.availableCash = availableCash;
	}

	public ArrayList<Stock> getStockList() {
		return stockList;
	}

	public void setStockList(ArrayList<Stock> stockList) {
		this.stockList = stockList;
	}

	public ArrayList<MutualFund> getMfList() {
		return mfList;
	}

	public void setMfList(ArrayList<MutualFund> mfList) {
		this.mfList = mfList;
	}

	public static ArrayList<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(ArrayList<Order> orderList) {
		this.orderList = orderList;
	}

	public void createOrder(String buy_sell,String orderType,
			String securityNum, int i, long uid2, Account account, double d, Date date) {
		

		if(orderType.equals(Order.LIMITED_ORDER)){
		if (buy_sell.equals(Order.SELLER)) {
			LimitedOrder limitedOrder = new LimitedOrder(Order.SELLER,
					Order.LIMITED_ORDER, Manager.st.get(Integer.parseInt(securityNum)-1), i, uid2, this,
					d, new Date());
			orderList.add(limitedOrder);
			limitedOrder.processingOrder();
		} else {
			LimitedOrder limitedOrder = new LimitedOrder(Order.BUYER,
					Order.LIMITED_ORDER, Manager.st.get(Integer.parseInt(securityNum)-1), i, uid2, this,
					d, new Date());
			orderList.add(limitedOrder);
			limitedOrder.processingOrder();
		}
		}else if(orderType.equals(Order.MUTUAL_FUND_ORDER)){
			if (buy_sell.equals(Order.SELLER)) {
			MutualFundOrder mfOrder = new MutualFundOrder(Order.SELLER,
					Order.MUTUAL_FUND_ORDER, Manager.mf.get(Integer.parseInt(securityNum)-1), i, uid2, this);
			orderList.add(mfOrder);
			mfOrder.processingOrder();
			}else{
				MutualFundOrder mfOrder = new MutualFundOrder(Order.BUYER,
						Order.MUTUAL_FUND_ORDER, Manager.mf.get(Integer.parseInt(securityNum)-1), i, uid2, this);
				orderList.add(mfOrder);
				mfOrder.processingOrder();
			}
		}  
		else if(orderType.equals(Order.MARKET_ORDER)){
			if (buy_sell.equals(Order.SELLER)) {
			MarketOrder marketOrder = new MarketOrder(Order.SELLER,
					Order.MARKET_ORDER, Manager.st.get(Integer.parseInt(securityNum)-1), i, uid2, this);
			orderList.add(marketOrder);
			}else if (buy_sell.equals(Order.BUYER)) {
				MarketOrder marketOrder = new MarketOrder(Order.BUYER,
						Order.MARKET_ORDER, Manager.st.get(Integer.parseInt(securityNum)-1), i, uid2, this);
				orderList.add(marketOrder);
			}
		}

/*		
		LimitedOrder limitedOrder_1 = new LimitedOrder(Order.SELLER,
				Order.PENDING, Order.LIMITED_ORDER, stockList.get(0), 20, 1,
				this, 12.50, new Date());
		LimitedOrder limitedOrder_2 = new LimitedOrder(Order.SELLER,
				Order.PENDING, Order.LIMITED_ORDER, stockList.get(0), 10, 2,
				this, 13.50, new Date());
		LimitedOrder limitedOrder_3 = new LimitedOrder(Order.SELLER,
				Order.PENDING, Order.LIMITED_ORDER, stockList.get(0), 30, 3,
				this, 14.50, new Date());
		LimitedOrder limitedOrder_4 = new LimitedOrder(Order.BUYER,
				Order.PENDING, Order.LIMITED_ORDER, stockList.get(0), 20, 1,
				this, 15.50, new Date());
		LimitedOrder limitedOrder_5 = new LimitedOrder(Order.BUYER,
				Order.PENDING, Order.LIMITED_ORDER, stockList.get(0), 20, 2,
				this, 14.50, new Date());
		LimitedOrder limitedOrder_6 = new LimitedOrder(Order.BUYER,
				Order.PENDING, Order.LIMITED_ORDER, stockList.get(0), 10, 1,
				this, 17.50, new Date());

		Security security_1 = Manager.mf.get(0);
		MutualFundOrder mfOrder_1 = new MutualFundOrder(Order.SELLER,*/
		/*
		 * Queue queue = stockList.get(0).getQ(); TreeMap<Double,Order>
		 * sellQfor0 = stockList.get(0).getQ().getSellerOrderQueue();
		 * TreeMap<Double,Order> buyQfor0 =
		 * stockList.get(0).getQ().getBuyerOrderQueue(); synchronized(queue){
		 * sellQfor0.put(limitedOrder_1.getPriceSet(), limitedOrder_1);
		 * sellQfor0.put(limitedOrder_2.getPriceSet(), limitedOrder_2);
		 * sellQfor0.put(limitedOrder_3.getPriceSet(), limitedOrder_3);
		 * buyQfor0.put(limitedOrder_4.getPriceSet(), limitedOrder_4);
		 * buyQfor0.put(limitedOrder_5.getPriceSet(), limitedOrder_5);
		 * buyQfor0.put(limitedOrder_6.getPriceSet(), limitedOrder_6);
		 * queue.notifyAll();}
		 */
/*
		orderList.add(mfOrder_1);
		orderList.add(limitedOrder_1);
		orderList.add(limitedOrder_2);
		orderList.add(limitedOrder_3);
		orderList.add(limitedOrder_4);
		orderList.add(limitedOrder_5);
		orderList.add(limitedOrder_6);
		// orderList.add(marketOrder_1);

		limitedOrder_1.processingOrder();
		limitedOrder_2.processingOrder();
		limitedOrder_3.processingOrder();
		limitedOrder_4.processingOrder();
		limitedOrder_5.processingOrder();
		limitedOrder_6.processingOrder();
		mfOrder_1.processingOrder(); */
		// marketOrder_1.processingOrder();

	}
}
