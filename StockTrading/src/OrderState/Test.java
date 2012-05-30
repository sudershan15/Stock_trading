package OrderState;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.TreeMap;

import Requestmanager.Manager;
import SecurityCode.Account;
import SecurityCode.Portfolio;
import SecurityDecorator.MutualFund;
import SecurityDecorator.Security;
import SecurityDecorator.Stock;

public class Test {
public static void main(String[] args){
    Test test = new Test();
    //MutualFund mf=new MutualFund();
	Manager.clock=1600;
	test.initialQ();
	
}

public void initialQ(){
	//stList[0] = new Stock();
	//System.out.println("ST--------->>> "+ st);
	Stock st = new Stock(1,"AAPL",14.50,100);
	Queue queue = st.getQ();
	Portfolio temp1 = new Portfolio(1,"vishal","vish");
    ArrayList<Account> accList = temp1.getAccList();
    Account a1=new Account(1, "Acc1", "x@y.c", 1, 500000);
	Account a2=new Account(2, "Acc2", "t@w.c", 2, 600000);
	ArrayList<Stock> stockList = new ArrayList<Stock>();
	stockList.add(st);
	a1.setStockList(stockList);
	a2.setStockList(stockList);
	
	Date data_1 = new Date();
    
	Hashtable<Stock,Integer> stockList_1 = new Hashtable<Stock,Integer>();
	stockList_1.put(st, 100);
	Stock stock_2 = new Stock(2, "YHOO", 14.77, 500);
	stockList_1.put(stock_2, 50);
	MutualFund mutualfund=new MutualFund(1, "American Century Heritage A",1000,stockList_1);

	
	LimitedOrder limitedOrder_1 = new LimitedOrder(Order.SELLER,Order.LIMITED_ORDER,st,20,1,a1,12.50,new Date());
	LimitedOrder limitedOrder_2 = new LimitedOrder(Order.SELLER,Order.LIMITED_ORDER,st,10,2,a1,13.50,new Date());
	LimitedOrder limitedOrder_3 = new LimitedOrder(Order.SELLER,Order.LIMITED_ORDER,st,30,3,a1,14.50,new Date());
	LimitedOrder limitedOrder_4 = new LimitedOrder(Order.BUYER,Order.LIMITED_ORDER,st,20,1,a2,15.50,new Date());
	LimitedOrder limitedOrder_5 = new LimitedOrder(Order.BUYER,Order.LIMITED_ORDER,st,20,2,a2,14.50,new Date());
	LimitedOrder limitedOrder_6 = new LimitedOrder(Order.BUYER,Order.LIMITED_ORDER,st,10,1,a2,17.50,new Date());
	
	limitedOrder_1.processingOrder();
	limitedOrder_2.processingOrder();
	limitedOrder_3.processingOrder();
	limitedOrder_4.processingOrder();
	limitedOrder_5.processingOrder();
	limitedOrder_6.processingOrder();

	Security security_1 = mutualfund;
	MutualFundOrder mfOrder_1 = new MutualFundOrder(Order.SELLER,Order.MUTUAL_FUND_ORDER,security_1,20,1,a1);
	mfOrder_1.processingOrder();
	
	/*
	TreeMap<Double,Order> sellQ = st.getQ().getSellerOrderQueue();
	sellQ.put(limitedOrder_1.getPriceSet(), limitedOrder_1);
	sellQ.put(limitedOrder_2.getPriceSet(), limitedOrder_2);
	sellQ.put(limitedOrder_3.getPriceSet(), limitedOrder_3);
	
	TreeMap<Double,Order> buyQ = st.getQ().getBuyerOrderQueue();
	buyQ.put(limitedOrder_4.getPriceSet(), limitedOrder_4);
	buyQ.put(limitedOrder_5.getPriceSet(), limitedOrder_5);
	buyQ.put(limitedOrder_6.getPriceSet(), limitedOrder_6);*/
}

}
