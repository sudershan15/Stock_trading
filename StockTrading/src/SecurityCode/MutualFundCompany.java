/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SecurityCode;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.String;

/**
 *
 * @author Vikrant
 */
public class MutualFundCompany  extends Profile{


    private String name;
    private String email;
    private String password;
    private String address;
    private String numMF;

/*
   public MutualFundCompany(String name,String email,String password,String address){
       
       this.name=name;
       this.email=email;
       this.password=password;
       this.address=address;
       
       
   }
   */


    public void getProfileInfo(){

        try{
        System.out.println("First Name : ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        name= br.readLine();


        System.out.println("email : ");
        br = new BufferedReader(new InputStreamReader(System.in));
        email= br.readLine();

        System.out.println("Adress : ");
        br = new BufferedReader(new InputStreamReader(System.in));
        address= br.readLine();

        }
        catch(Exception e){
            System.out.println(e);
        }


    }

    public void getInitialcash(){

    }
    public boolean mutualFundCompany(){

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
    public void getMutualFundInfo(){

        try{
        System.out.println("How many Mutual Funds do you want to list initially ? Y/N ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        numMF  = (br.readLine());
     }catch(Exception e){
         System.out.println(e);
     }


    }
    public void updateProfile(){

    }
    public void createAccount(){

    }

}
