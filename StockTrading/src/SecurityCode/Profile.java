/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SecurityCode;

/**
 *
 * @author Vikrant
 */
public abstract class Profile {

    void createProfile(){
        getProfileInfo();
        getInitialcash();
        if (mutualFundCompany()){
            getMutualFundInfo();
        }
        else
        {
            //getInitialCashInfo();
        }
    }
    public abstract void getProfileInfo();
    public abstract void getInitialcash();
    public abstract boolean mutualFundCompany();
    public abstract void getMutualFundInfo(); // hook operation
   

    //public abstract void updateProfile();
    //public abstract void createAccount();
    


}
