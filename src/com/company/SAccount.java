package com.company;

import java.util.ArrayList;

public class SAccount {
    private ArrayList<SAccount> friends;
    String name;
    String password;

    public SAccount(String name, String password) {
        this.name = name;
        this.password = password;
    }

}
