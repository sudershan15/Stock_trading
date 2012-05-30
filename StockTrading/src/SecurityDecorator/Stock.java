package SecurityDecorator;

import OrderState.Queue;

public class Stock extends Security{

	long Stockid;
	String Stocksymbol;
	Queue q;
	
	public Stock(long id, String Stsymb, double Price, long number){
		Stockid=id;
		Stocksymbol=Stsymb;
		setPrice(Price);
		setnumber(number);
		q=new Queue(Stockid);
	}
	

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Stock){
			Stock s = (Stock)obj;
			if(Stockid==s.getStockid()&& Stocksymbol.equals(s.getStocksymbol()))
				return true;
			return false;
		}
		return false;
	}

	public String getStocksymbol() {
		return Stocksymbol;
	}

	public Stock getstock(int stock_id){
		return Stock.this;
	}

	public void setStocksymbol(String stocksymbol) {
		Stocksymbol = stocksymbol;
	}


	
	public long getStockid() {
		return Stockid;
	}



	public void setStockid(long stockid) {
		Stockid = stockid;
	}



	public Queue getQ() {
		return q;
	}

	public void setQ(Queue q) {
		this.q = q;
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		System.out.println(Stockid+". Stock: "+ Stocksymbol+"  Price: "+getPrice()+"  Number of Stocks: "+getnumber());
	}
	
	
	
}