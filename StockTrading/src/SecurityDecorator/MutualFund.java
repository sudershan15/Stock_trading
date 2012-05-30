package SecurityDecorator;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import Requestmanager.Manager;

public class MutualFund extends Security{
	
	long MFid;
	public Hashtable<Stock,Integer> stkList = new Hashtable<Stock,Integer>();
	String MFname;


	public MutualFund(long id, String name, long number, Hashtable<Stock,Integer> stockList){
		MFid=id;
		MFname=name;
		setnumber(number);
		stkList=stockList;
		setPrice(caculatePrice());
	}
	
public double caculatePrice() {
	double totalPrice=0,mfPrice=0;
	int totalShareNum=0,stkShareNum=0;
	Set<Stock> stockList_Set = stkList.keySet();
	Iterator<Stock> stock_Iterator = stockList_Set.iterator();
    while (stock_Iterator.hasNext()) {
        Stock stk = stock_Iterator.next();
        stkShareNum = stkList.get(stk);
        totalPrice += stkShareNum * stk.getPrice();
        totalShareNum +=stkShareNum;
      }
    mfPrice = totalPrice/totalShareNum;
    //System.out.print("mutual fund avg price: "+mfPrice+"\n");
		return mfPrice;
	}

public void updateStockPrice() {
	int stkShareNum=0;
	Hashtable<Stock,Integer> newStkList = new Hashtable<Stock,Integer>();
	
	Set<Stock> stockList_Set = stkList.keySet();
	Iterator<Stock> stock_Iterator = stockList_Set.iterator();
    while (stock_Iterator.hasNext()) {
        Stock stk = stock_Iterator.next();
        stkShareNum = stkList.get(stk);
        for(Stock s:Manager.st){
        	if(s.equals(stk)){
        		stk.setPrice(s.getPrice());
        		newStkList.put(stk, stkShareNum);
        	}
        }
      }
    stkList = newStkList;
	}


public Hashtable<Stock, Integer> getStkList() {
	return stkList;
}

public void setStkList(Hashtable<Stock, Integer> stkList) {
	this.stkList = stkList;
}

public void display() {
	// TODO Auto-generated method stub
 	System.out.println(MFid+ ". Mutual Fund: "+MFname+"  Price: "+getPrice()+"   Number of mutual funds: "+getnumber());
}
	
public long getMFid() {
	return MFid;
}

public void setMFid(long mFid) {
	MFid = mFid;
}

	public String getMFname() {
		return MFname;
	}

	public void setMFname(String mFname) {
		MFname = mFname;
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MutualFund){
			MutualFund mf = (MutualFund)obj;
			if(MFid==mf.getMFid()&&MFname.equals(mf.getMFname()))
				return true;
			return false;
		}
		return false;
	}
}