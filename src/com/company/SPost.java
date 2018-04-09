package com.company;

import java.util.ArrayList;

public class SPost {
    private String post;
    private SAccount postOwner;
    private ArrayList<SAccount> likers;
    private ArrayList<SComment> comments;

    public SPost(String post, SAccount postOwner) {
        this.post = post;
        this.postOwner = postOwner;
        likers = new ArrayList<>();
        comments = new ArrayList<>();
    }
    public void addLike(SAccount account){
        likers.add(account);
    }
    public void addComment(SAccount account,String comment){
        comments.add(new SComment(account,comment));
    }
}
