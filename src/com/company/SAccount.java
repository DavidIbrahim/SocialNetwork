package com.company;

import java.util.ArrayList;

public class SAccount {
    private ArrayList<SAccount> friends;
    private String name;
    private String password;
    private ArrayList<SPost> posts;

    public SAccount(String name, String password) {
        this.name = name;
        this.password = password;
        friends = new ArrayList<>();
        posts = new ArrayList<>();
    }

    public void addNewPost(String post){
        posts.add(new SPost(post,this));
    }
    public void addNewFriend(SAccount newFriend) {
        friends.add(newFriend);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
