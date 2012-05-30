/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SecurityCode;

import OrderState.LimitedOrder;
import OrderState.MarketOrder;
import OrderState.MutualFundOrder;
import OrderState.Order;
import Requestmanager.Manager;
import SecurityCode.Account;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import SecurityDecorator.Security;
import SecurityDecorator.Stock;
import SecurityDecorator.MutualFund;

/**
 *
 * @author Vikrant
 */
public class Portfolio {
    public static ArrayList<Stock> st;
	public static ArrayList<MutualFund> mf;
	ArrayList<Order> orderlist;
	public static ArrayList<String> UserName=new ArrayList<String>();
    public static ArrayList<String> Pwd=new ArrayList<String>();
    public ArrayList<Account> acc=new ArrayList<Account>();
    public long uid;
    float amount;
    public String userName;
    public String password;
    //public Order or;
    Security sec;
    
    public Portfolio(){
    	
    }
    
    public Portfolio(int id, String portfolioName,String portfolioPwd){
    	uid=id;
    	userName= portfolioName;
    	UserName.add(userName);
        password = portfolioPwd;
        Pwd.add(password);
		listacc();
    }
    
        public void createProfile(){

    }

       public void enterAcc(){

    }
       
	public void generateInvestorMenu() {
		// TODO Auto-generated method stub
			System.out.println("Welcome user "+ UserName.get((int)uid-1)+" ");
			System.out.println("*****************************************");
			System.out.println("     ********Investor Menu*********");
			System.out.println("*****************************************");
			System.out.println("Enter your Choice:");
			System.out.println("1. List your Accounts");
			System.out.println("2. Create An order");
			System.out.println("3. View your order status");
			System.out.println("4. View All Security listings");
			System.out.println("5. View My Security listings");
			System.out.println("6. Clock Change");
			System.out.println("7. Exit");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String q="";
			try {
				q = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			switch(Integer.parseInt(q)){
			case 1:
				listMyAcc();
				break;
			case 2:
				try {
					create_order();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				orderHistory();
				System.out.println("order history finish");
				break;
			case 4:
				show_listings();
				break;
			case 5:
				show_My_listings();
				break;
			case 6:
				System.out.println("clock change range(100-2400):");
				BufferedReader pr = new BufferedReader(new InputStreamReader(System.in));
				try {
					Manager.clock=Integer.parseInt(pr.readLine());
					for(Order o: acc.get(0).orderList){
						if(!o.getStatus().equals(Order.FULFILLED))
						o.processingOrder();
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 7:
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice try again!!");
				generateInvestorMenu();
			}
	}
	
	public void show_listings(){
		System.out.println("\t*************Stock listings*********\n");
		for(int i=0; i<Manager.st.size(); i++)
			Manager.st.get(i).display();
		System.out.println("\n\t*************Mutual Fund listings*********\n");
		for(int j=0; j<Manager.mf.size(); j++)
			Manager.mf.get(j).display();
		generateInvestorMenu();
	}

	public void show_My_listings(){
		for(int i=0;i<acc.size();i++){
		System.out.println("\t*************Account No."+(i+1)+"*********\n");
		System.out.println("\t*************Stock listings*********\n");
		ArrayList<Stock> stkList = acc.get(i).getStockList();
		ArrayList<MutualFund> mfList = acc.get(i).getMfList();
		for(int j=0; j<stkList.size(); j++)
			stkList.get(j).display();
		System.out.println("\n\t*************Mutual Fund listings*********\n");
		for(int k=0; k<mfList.size(); k++)
			mfList.get(k).display();
		}
		generateInvestorMenu();
	}
	
	private void listMyAcc() {

		for(int i=0;i<acc.size();i++){
			System.out.println("Account no "+i+":"+acc.get(i).accId);
			System.out.println("User ID : "+acc.get(i).uid);
			System.out.println("e-mail : "+acc.get(i).email);
			System.out.println("Account name: "+acc.get(i).accname);
			System.out.println("Cash in Account: "+acc.get(i).availableCash);
			System.out.println("\n");
		
		}
		generateInvestorMenu();
				
		
		
	}

	private void create_order() throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Place your order for-- \n1. Limit Order \n2. Market Order \n3. Mutual Fund Order\n");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String orderType= br.readLine();
		System.out.println("Give your order-- \n1. Buy \n2. Sell\n");
		br=new BufferedReader(new InputStreamReader(System.in));
		String buy_sell= br.readLine();
		switch(Integer.parseInt(orderType)){
		case 1:
			enterdet(buy_sell,orderType);
			break;
		case 2:
			enterdet(buy_sell,orderType);
			break;
		case 3:
			enterdet(buy_sell,orderType);
			break;			
		}

		generateInvestorMenu();
	}
	
	
	void orderHistory(){
		orderlist = acc.get(0).orderList;
		for(int i=0;i<orderlist.size();i++){
			Order o = orderlist.get(i);
			if(o instanceof LimitedOrder){
			System.out.println("Order Type:"+o.getOrderType());
			System.out.println("Stock Name:"+((Stock)o.getSecurity()).getStocksymbol());
			System.out.println("Stock Share Number:"+o.getShare_Number());
			System.out.println("Price Set:"+((LimitedOrder)o).getPriceSet());
			System.out.println("Sell/Buy:"+((LimitedOrder)o).getSeller_Or_buyer());
			System.out.println("Status:"+((LimitedOrder)o).getStatus()+"\n");
			}
			else if(o instanceof MarketOrder){
				System.out.println("Order Type:"+o.getOrderType());
				System.out.println("Stock Name:"+((Stock)o.getSecurity()).getStocksymbol());
				System.out.println("Stock Share Number:"+o.getShare_Number());
				System.out.println("Sell/Buy:"+((MarketOrder)o).getSeller_Or_buyer());
				System.out.println("Status:"+((MarketOrder)o).getStatus()+"\n");
			}
			else if(o instanceof MutualFundOrder){
				System.out.println("Order Type:"+o.getOrderType());
				System.out.println("Mutual Fund Name:"+((MutualFund)o.getSecurity()).getMFname());
				System.out.println("Share Number:"+o.getShare_Number());
				System.out.println("Price Set:"+((MutualFundOrder)o).getPriceSet());
				System.out.println("Sell/Buy:"+((MutualFundOrder)o).getSeller_Or_buyer());
				System.out.println("Status:"+((MutualFundOrder)o).getStatus()+"\n");
			}
		}
		System.out.println("**************************");
		generateInvestorMenu();
	}

	void listacc(){
		Account a1=new Account(1, "Acc1", "x@y.c", 1, 500000);
		Account a2=new Account(2, "Acc2", "t@w.c", 2, 600000);
		acc.add(a1);
		acc.add(a2);
	}

	public ArrayList<Account> getAccList() {
		return acc;
	}

	public void setAccList(ArrayList<Account> acc) {
		this.acc = acc;
	}
	
	void enterdet(String buy_sell,String orderType) throws IOException{
		
		if (orderType.equals("1")){
			
			orderType = Order.LIMITED_ORDER;
		
		if(buy_sell.equals("1")){
		String securityNum,nb_Stock,price,accId;
		System.out.println("Enter Stock Id: ");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		securityNum= br.readLine();
		System.out.println("Enter Number of Stocks: ");
		br=new BufferedReader(new InputStreamReader(System.in));
		nb_Stock= br.readLine();
		System.out.println("Enter Price for stock: ");
		br=new BufferedReader(new InputStreamReader(System.in));
		price= br.readLine();
		System.out.println("Which account do you want to use for order? Enter Account Id: ");
		br=new BufferedReader(new InputStreamReader(System.in));
        accId= br.readLine();
		double temp = amount + ((amount)*0.2);
		if(acc.get(Integer.parseInt(accId)-1).cashacc>=temp)
			acc.get(Integer.parseInt(accId)-1).createOrder(Order.BUYER,orderType,securityNum,Integer.parseInt(nb_Stock),uid,acc.get(Integer.parseInt(accId)-1),Double.parseDouble(price),new Date());
		else{
			System.out.println("not enough cash to create order!");
		}
		
		}
		if(buy_sell.equals("2")){
			String securityNum,nb_Stock,price,accId;
			System.out.println("Enter Stock Id: ");
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			securityNum= br.readLine();
			System.out.println("Enter Number of Stocks: ");
			br=new BufferedReader(new InputStreamReader(System.in));
			nb_Stock= br.readLine();
			System.out.println("Enter Price for stock: ");
			br=new BufferedReader(new InputStreamReader(System.in));
			price= br.readLine();
			System.out.println("Which account do you want to use for order? Enter Account Id: ");
			br=new BufferedReader(new InputStreamReader(System.in));
	        accId= br.readLine();
	        ArrayList<Stock> stkList = acc.get(Integer.parseInt(accId)-1).getStockList();
	        Stock stock = Manager.st.get(Integer.parseInt(securityNum)-1);
	        boolean ableToCreate=false;
	        for(Stock s:stkList){
	        	if(s.equals(stock)){
	        		if(s.getnumber()>=Integer.parseInt(nb_Stock)){
	    				acc.get(Integer.parseInt(accId)-1).createOrder(Order.SELLER,orderType,securityNum,Integer.parseInt(nb_Stock),uid,acc.get(Integer.parseInt(accId)-1),Double.parseDouble(price),new Date());
	    				ableToCreate = true;
	        		}
	        	}
	        }
			if(!ableToCreate){
				System.out.println("not enough stock to create order!");
			}
			
			}
		}
		else if (orderType.equals("2")){
			
			orderType = Order.MARKET_ORDER;
		
		if(buy_sell.equals("1")){
		String securityNum,nb_Stock,accId;
		System.out.println("Enter Stock Id: ");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		securityNum= br.readLine();
		System.out.println("Enter Number of Stocks: ");
		br=new BufferedReader(new InputStreamReader(System.in));
		nb_Stock= br.readLine();
		System.out.println("Which account do you want to use for order? Enter Account Id: ");
		br=new BufferedReader(new InputStreamReader(System.in));
        accId= br.readLine();
		double temp = amount + ((amount)*0.2);
		if(acc.get(Integer.parseInt(accId)-1).cashacc>=temp)
			acc.get(Integer.parseInt(accId)-1).createOrder(Order.BUYER,orderType,securityNum,Integer.parseInt(nb_Stock),uid,acc.get(Integer.parseInt(accId)-1),0,new Date());
		else{
			System.out.println("not enough cash to create order!");
		}
		
		}
		else if(buy_sell.equals("2")){
			String securityNum,nb_Stock,accId;
			System.out.println("Enter Stock Id: ");
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			securityNum= br.readLine();
			System.out.println("Enter Number of Stocks: ");
			br=new BufferedReader(new InputStreamReader(System.in));
			nb_Stock= br.readLine();
			System.out.println("Which account do you want to use for order? Enter Account Id: ");
			br=new BufferedReader(new InputStreamReader(System.in));
	        accId= br.readLine();
	        ArrayList<Stock> stkList = acc.get(Integer.parseInt(accId)-1).getStockList();
	        Stock stock = Manager.st.get(Integer.parseInt(securityNum)-1);
	        boolean ableToCreate=false;
	        for(Stock s:stkList){
	        	if(s.equals(stock)){
	        		if(s.getnumber()>=Integer.parseInt(nb_Stock)){
	    				acc.get(Integer.parseInt(accId)-1).createOrder(Order.SELLER,orderType,securityNum,Integer.parseInt(nb_Stock),uid,acc.get(Integer.parseInt(accId)-1),0,new Date());
	    				ableToCreate = true;
	        		}
	        	}
	        }
			if(!ableToCreate){
				System.out.println("not enough stock to create order!");
			}
			
			}
		}
		else if (orderType.equals("3")){
			
			orderType = Order.MUTUAL_FUND_ORDER;
		
		if(buy_sell.equals("1")){
		String securityNum,nb_Stock,price,accId;
		System.out.println("Enter Mutual Fund Id: ");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		securityNum= br.readLine();
		System.out.println("Enter Number of Mutual Fund: ");
		br=new BufferedReader(new InputStreamReader(System.in));
		nb_Stock= br.readLine();
		System.out.println("Which account do you want to use for order? Enter Account Id: ");
		br=new BufferedReader(new InputStreamReader(System.in));
        accId= br.readLine();
		double temp = amount + ((amount)*0.2);
		if(acc.get(Integer.parseInt(accId)-1).cashacc>=temp)
			acc.get(Integer.parseInt(accId)-1).createOrder(Order.BUYER,orderType,securityNum,Integer.parseInt(nb_Stock),uid,acc.get(Integer.parseInt(accId)-1),(Manager.mf.get(Integer.parseInt(securityNum)-1).getPrice()),new Date());
		else{
			System.out.println("not enough cash to create order!");
		}
		
		}
		if(buy_sell.equals("2")){
			String securityNum,nb_Stock,price,accId;
			System.out.println("Enter Mutual Fund Id: ");
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			securityNum= br.readLine();
			System.out.println("Enter Number of Mutual Fund: ");
			br=new BufferedReader(new InputStreamReader(System.in));
			nb_Stock= br.readLine();
			System.out.println("Which account do you want to use for order? Enter Account Id: ");
			br=new BufferedReader(new InputStreamReader(System.in));
	        accId= br.readLine();
	        ArrayList<MutualFund> mfList = acc.get(Integer.parseInt(accId)-1).getMfList();
	        MutualFund mf = Manager.mf.get(Integer.parseInt(securityNum)-1);
	        boolean ableToCreate=false;
	        for(MutualFund m:mfList){
	        	if(m.equals(mf)){
	        		if(m.getnumber()>=Integer.parseInt(nb_Stock)){
	    				acc.get(Integer.parseInt(accId)-1).createOrder(Order.SELLER,orderType,securityNum,Integer.parseInt(nb_Stock),uid,acc.get(Integer.parseInt(accId)-1),(Manager.mf.get(Integer.parseInt(securityNum)-1).getPrice()),new Date());
	    				ableToCreate = true;
	        		}
	        	}
	        }
			if(!ableToCreate){
				System.out.println("not enough mutual fund to create order!");
			}
			
			}
		}
		
		
	}
	
	
}


