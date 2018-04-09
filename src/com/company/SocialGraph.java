package com.company;

import java.util.ArrayList;

public class SocialGraph {
    private ArrayList<SAccount> allTheAccounts;

    public SocialGraph() {
        allTheAccounts = new ArrayList<>();
    }
    public void addNewAccount(String name,String password){
        allTheAccounts.add(new SAccount(name,password));
    }
    public SAccount getAccount(String name,String password) {
        for (SAccount account:
             allTheAccounts) {
            if(account.getName().equals(name)
                    && account.getPassword().equals(password)){
                return account;
            }
        }
        //Didn't found the account
        return null;
    }

}
