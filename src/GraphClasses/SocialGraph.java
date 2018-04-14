package GraphClasses;

import David.MyExceptionCodes;

import David.ProjectExceptions.*;
import java.util.HashMap;

public class SocialGraph {
    private HashMap<String,SAccount> allTheAccounts;

    public SocialGraph() {
        allTheAccounts = new HashMap<>();
    }

    public void addNewAccount(String name,String password) throws AccountException {

        if(allTheAccounts.containsKey(name)){
            throw new AccountException(MyExceptionCodes.ACCOUNT_EXIST);
        }

        allTheAccounts.put(name,new SAccount(name,password));
    }




    public SAccount getAccount(String name,String password) throws AccountException {

        if(allTheAccounts.containsKey(name)){
            SAccount account = allTheAccounts.get(name);
            if(account.getPassword().equals(password))
                return account;
            else{
                throw new AccountException( MyExceptionCodes.WRONG_PASSWORD);

            }
        }
        return null;
    }

}
