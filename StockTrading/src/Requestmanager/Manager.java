package Requestmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import messagebridge.Message;

import OrderState.*;
import SecurityCode.Account;
import SecurityCode.Portfolio;
import SecurityDecorator.MutualFund;
import SecurityDecorator.Security;
import SecurityDecorator.Stock;

public class Manager{
	public static ArrayList<Stock> st = new ArrayList<Stock>();
	public static ArrayList<MutualFund> mf = new ArrayList<MutualFund>();
	public static ArrayList<Portfolio> PF = new ArrayList<Portfolio>();
	public static int clock=900;
	MutualFundOrder Mfo;
	public Manager(){
		addUsers();
		listings();
		display();
	}
	public static ArrayList<Stock> getlists(){
		return st;
	}
	
	void addUsers(){
		Portfolio temp1 = new Portfolio(1,"vishal","vish");
		Portfolio temp2 = new Portfolio(2,"sud","sud");
		PF.add(temp1);
		PF.add(temp2);
	}
	public void display(){
		System.out.println("\tWelcome to Stock Trading System\n");
		System.out.println("1. Login");
		System.out.println("2. View Security listings");
		System.out.println("3. Create new User");
		System.out.println("4. Exit");
		System.out.println("5. Change Clock");
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		try {
			String s=br.readLine();
			switch(Integer.parseInt(s)){
			case 1:
				login();
				break;
			case 2:
				show_listings();
				break;
			case 3:
				create_user();
				break;
			case 4:
				System.exit(0);
				break;
			case 5:
				BufferedReader cr= new BufferedReader(new InputStreamReader(System.in));
				clock = Integer.parseInt(cr.readLine());
				break;
			default:
				System.out.println("Please try again!!\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		display();
	}
	
	public void login(){
		   	 String choice = generateMainMenu();
		     switch(Integer.parseInt(choice)){
		     	case 1:
				    getcredentials();  
		     		break;
		     	case 2:
		     		generateMutualFundCompMenu();
		     		break;
		     	case 3:
		     		generateStockCompMenu();
		     		break;
		     	case 4:
		     		display();
		     		break;
		     	case 5:
		     		System.exit(0);
		     		break;
		     	default:
		     		login();
		     }
	}
	
	public String generateMainMenu(){
				System.out.println("\n    WELCOME TO STOCK TRADING SYSTEM!");
				System.out.println("***************************************");
				System.out.println("***************Main Menu***************");
				System.out.println("***************************************");
				System.out.println("please choose your role:");
				System.out.println("1. Investor");
				System.out.println("2. Mutual Fund Company");
				System.out.println("3. Stock Company");
				System.out.println("4. Go back to main menu");
				System.out.println("5. Exit");
				String reply ="";
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				try {
					reply = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return reply;
	}
	

	public void create_user() throws IOException{
				String UN, PWD;
				System.out.println("**************Enter your details*************");
				System.out.println("*********************************************");
				System.out.println("1. Enter your desired user name: ");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				UN=br.readLine();
				System.out.println("2. Enter desired password: ");
				br = new BufferedReader(new InputStreamReader(System.in));
				PWD=br.readLine();
				Portfolio temp = new Portfolio(PF.size()+1,UN,PWD);
				PF.add(temp);
				login();
	}
			
	public void getcredentials(){
				System.out.println("....please enter your username:");
				String un,pwd ="";
				int i;
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				try {
					un = br.readLine();
					System.out.println("Enter your password: ");
					pwd=br.readLine();
					
					for(i=0;i<PF.size();i++){
						if((PF.get(i).userName.equals(un) ) && (PF.get(i).password.equals(pwd))){
							PF.get(i).generateInvestorMenu();
							break;
						}									
					}
					//If username or password invalid
					if (i==PF.size()){
						System.out.println("Forget username or password? please try again!");						
						login();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	}
	
	public void generateStockCompMenu(){
   		String q="";
   		System.out.println("***************************************");
   		System.out.println("**********Stock Company Menu***********");
   		System.out.println("***************************************");
   		System.out.println("please choose your role:");
   		System.out.println("1. Add a IPO");
   		System.out.println("2. Show all listings");
   		System.out.println("3. Go back to main menu");
   		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   		try {
   			q = br.readLine();
   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   		switch(Integer.parseInt(q)){
   		case 1:
   			add_NewStock();
   			break;
   		case 2:
   			show_listings();
   			break;
   		case 3:
   			login();
   			break;
   		default:
   			System.out.println("Invalid choice try again!!");
   		}
   	}

    public void generateMutualFundCompMenu(){
		System.out.println("*****************************************");
		System.out.println("********Mutual Fund Company Menu*********");
		System.out.println("*****************************************");
		System.out.println("please choose your role:");
		System.out.println("1. Add a new mutual Fund");
		System.out.println("2. Show all listings");
		System.out.println("3. Go back to main menu");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String q="";
		try {
			q = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch(Integer.parseInt(q)){
		case 1:
			add_NewMF();
			break;
		case 2:
			show_listings();
			break;
		case 3:
			login();
			break;
		default:
			System.out.println("Invalid choice try again!!");
		}
		generateMutualFundCompMenu();
	}
    
	public void show_listings(){
		System.out.println("\t*************Stock listings*********\n");
		for(int i=0; i<st.size(); i++)
			st.get(i).display();
		System.out.println("\n\t*************Mutual Fund listings*********\n");
		for(int j=0; j<mf.size(); j++)
			mf.get(j).display();
	}
	
	void add_NewMF(){
		System.out.println("Please enter the new mutual fund name: ");
		BufferedReader br1,br3;
		boolean d=false;
		br1= new BufferedReader(new InputStreamReader(System.in));
		ArrayList<Stock> stk=new ArrayList<Stock>();
		int id=mf.size()+1;
		Hashtable<Stock,Integer> stockList = new Hashtable<Stock,Integer>();
		String mf_name,nb_st = null,nb_mf,stock_id,rep;
		try {
			mf_name = br1.readLine();
		   System.out.println("and number of mutual funds:");
		    br3=new BufferedReader(new InputStreamReader(System.in));
			nb_mf = br3.readLine();
			System.out.println("What Stocks are contained in the mutual fund, Enter id's: ");
			while(!d){
				System.out.println("\nEnterID: ");
				stock_id=br3.readLine();
				if(Integer.parseInt(stock_id)>st.size()+1){
					System.out.println("Invalid Stock!!");
				}else{
				for(int i=0;i<st.size();i++){
					if(st.get(i).getStockid()==Integer.parseInt(stock_id)){
						stk.add(st.get(i));
						System.out.println("Adding: ");
						st.get(i).display();
					}
				}
				System.out.println("\nEnter Number of stocks: ");
				nb_st=br3.readLine();
				}
				stockList.put(st.get(Integer.parseInt(stock_id)-1), Integer.parseInt(nb_st));
				System.out.println("Do you want to add more Stocks in mutualfund(y/n)? ");
				rep=br1.readLine();
				if(rep.equals("n")){
					d=true;
				} else d=false;
			}
			MutualFund mutualfund=new MutualFund(id, mf_name,Long.parseLong(nb_mf),stockList);
			mf.add(mutualfund);
					
			//MutualFund mutualfund= new MutualFund(id, mf_name, Double.parseDouble(curr_price), Long.parseLong(nb_mf), stk);
			//mf.add(mutualfund);
			System.out.println("Mutual Fund Created from function");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	void add_NewStock(){
		System.out.println("Please enter the new stock name: ");
		BufferedReader br1,br2,br3;
		br1= new BufferedReader(new InputStreamReader(System.in));
		String x,y,z;
		try {
			x = br1.readLine();
		    System.out.println("and its current price:");
		    br2=new BufferedReader(new InputStreamReader(System.in));
			y = br2.readLine();
			System.out.println("and number of stocks:");
		    br3=new BufferedReader(new InputStreamReader(System.in));
			z = br3.readLine();
			Stock stock= new Stock(st.size()+1, x, Double.parseDouble(y), Long.parseLong(z));
			st.add(stock);
			System.out.println("Stock Created from function");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
			
    public void listings(){
		Hashtable<Stock,Integer> stockList_1 = new Hashtable<Stock,Integer>();
		Hashtable<Stock,Integer> stockList_2 = new Hashtable<Stock,Integer>();
		Stock stock_1 = new Stock(1, "AAPL", 376.72, 500000);
		st.add(stock_1);
		Stock stock_2 = new Stock(2, "YHOO", 14.77, 600000);
		st.add(stock_2);
		//Mutual Fund Creation
		stockList_1.put(stock_1, 100);
		stockList_1.put(stock_2, 50);
		MutualFund mutualfund=new MutualFund(1, "American Century Heritage A",150,stockList_1);
		mf.add(mutualfund);
		stockList_2.put(stock_1, 150);
		stockList_2.put(stock_2, 200);
		mutualfund=new MutualFund(2, "Delaware Smid CAP Growth C",350,stockList_2);
		mf.add(mutualfund);
	}
	
}