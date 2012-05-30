/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SecurityCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.String;


/*
 * @author Vikrant
 */


public  class  Investor extends Profile{

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String ContactNumber;
            boolean isMFC;

    @Override
    public void getProfileInfo() {

        try{
        System.out.println("First Name : ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        firstName= br.readLine();

        System.out.println("Last Name : ");
        br = new BufferedReader(new InputStreamReader(System.in));
        lastName= br.readLine();

        System.out.println("email : ");
        br = new BufferedReader(new InputStreamReader(System.in));
        email= br.readLine();

        System.out.println("Contact Number : ");
        br = new BufferedReader(new InputStreamReader(System.in));
        ContactNumber= br.readLine();
        
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void getInitialcash() {

    }
        
    

    
    public boolean mutualFundCompany() {
    String str="";
     try{
        System.out.println("Are you a Mutual fund Company ? Y/N ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         str = br.readLine();
     }catch(Exception e){
         System.out.println(e);
     }
     if (str.toLowerCase().startsWith("y")){ return true;}
     else { return false; }

    }

    
    public void getMutualFundInfo() {

        System.out.println("Investor will not have mutual fund info");
       
    }

   

   /* public Investor(String firstName,String lastName,String email,String password,String ContactNumber){

        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
        this.ContactNumber=ContactNumber;
        

    }
    */
}