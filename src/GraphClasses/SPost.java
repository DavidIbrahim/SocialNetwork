package GraphClasses;

import java.util.ArrayList;

public class SPost {
    private String post;
    private String postOwner;
    private ArrayList<String> likes;
    private ArrayList<SComment> comments;

    public SPost(String post, SAccount postOwner) {
        this.post = post;
        this.postOwner = postOwner.getName();
        likes = new ArrayList<>();
        comments = new ArrayList<>();
    }
    public void addLike(SAccount account){
        likes.add(account.getName());
    }
    public void addComment(SAccount account,String comment){
        comments.add(new SComment(account,comment));
    }
}
