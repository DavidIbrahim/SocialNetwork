package GraphClasses;

import David.ProjectExceptions;
import David.ProjectExceptions.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class SocialGraph {
    private HashMap<String, SAccount> allTheAccounts;

    public SocialGraph() {
        allTheAccounts = new HashMap<>();
    }

    public void addNewAccount(String name, String password) throws AccountException {

        if (allTheAccounts.containsKey(name)) {
            throw new AccountException(ProjectExceptions.MyExceptionCodes.ACCOUNT_EXIST);
        }

        allTheAccounts.put(name, new SAccount(name, password));
    }


    public SAccount getAccount(String name, String password ) throws AccountException {

        if (allTheAccounts.containsKey(name)) {
            SAccount account = allTheAccounts.get(name);
            if (account.getPassword().equals(password))
                return account;
            else {
                throw new AccountException(ProjectExceptions.MyExceptionCodes.WRONG_PASSWORD);

            }
        }
        else {
            throw new AccountException(ProjectExceptions.MyExceptionCodes.NO_ACCOUNT);
        }

    }


    public SAccount getAccount(String name) throws AccountException {

        if (allTheAccounts.containsKey(name)) {
            SAccount account = allTheAccounts.get(name);
            return account;
        }
        else {
            throw new AccountException(ProjectExceptions.MyExceptionCodes.NO_ACCOUNT);
        }
    }

    public int getNumberOfAccounts(){
        return allTheAccounts.size();
    }


    @Override
    public String toString() {
        String s = "SocialGraph{" + '\n';
        for (String name: allTheAccounts.keySet()
             ) {
            s += name+'\n';

        }
        s += "Number Of Accounts = " + allTheAccounts.size()+'\n';
        s+= '}';
        return s;

    }
    public ArrayList<String>  getAllAccounts (){
        String [] names = new String[0];
        Set <String> set = allTheAccounts.keySet();
        names = set.toArray(new String[0]);
        return new ArrayList<String>(Arrays.asList(names));
    }
}
