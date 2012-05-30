/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SecurityCode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import OrderState.LimitedOrder;
import OrderState.MutualFundOrder;
import OrderState.Order;
import Requestmanager.Manager;
import SecurityDecorator.Stock;
/**
 *
 * @author Vikrant
 */
public class Main{

    /**
     * @param args the command line argumentsan
     */
   public static void main(String[] args) {
        // TODO code application logic here
       //MutualFundCompany MFC = new MutualFundCompany();
       //MFC.createProfile();

       //System.out.println("Test run successful");
       
       String str = "";

       System.out.println("********************Welcome to Security Trading System*************");

       System.out.println("Please Enter Your Chice.. \n");
       System.out.println("1.Create a new Portfolio \n");
       System.out.println("2.Login with existing portfolio \n");
       try{
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         str = br.readLine();
     }catch(Exception e){
         System.out.println(e);
     }
     if (str.equals("1")){
       System.out.println("Please Enter Your Chice.. \n");
       System.out.println("1.You are an Investor. \n");
       System.out.println("2.You are a Mutua Fund Company \n");
       System.out.println("3.You are a Clock \n");
       try{
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         str = br.readLine();
         }catch(Exception e){
             System.out.println(e);
             }
       if (str.equals("1")){

           Investor inv = new Investor();
           inv.createProfile();
           System.out.println("\n******Profile Created***\n");
           System.out.println("Please create an account since your's is a new portfolio. Ready to create an accoun.. ?? Y/N : ");


           System.out.println("Test run successful");
       }
       else if (str.equals("2")){
    	   
       }
       else if (str.equals("3")){
           System.out.println("\n******Clock*****\n");
           System.out.println("\nplease input the current time(format as 100 to 2400):\n");
           BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
           try {
			String clock_Str = br.readLine();
			Manager.clock=Integer.parseInt(clock_Str);
			if(Manager.clock==1600){
				//process M.F. & check isExpried()
				for(Portfolio p:Manager.PF){
					for(Account acc:p.getAccList()){
						for(Order o: acc.getOrderList()){
							if(o instanceof MutualFundOrder){
								MutualFundOrder mfOrder = (MutualFundOrder)o;
								mfOrder.processingOrder();
							}
							if(o instanceof LimitedOrder){
								boolean isExpired = ((LimitedOrder) o).isExpired();
								if(isExpired){
									o.setStatus(Order.EXPIRED);
								}
							}
						}
					}
				}
				//refine Q
				for(Stock s:Manager.st){
					s.getQ().refineQueue();
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}              	   
       }


     }
     else if (str.equals("2")){
    	 Manager manager = new Manager();
     }

    }

}
