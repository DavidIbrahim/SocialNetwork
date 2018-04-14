package GraphClasses;

import java.util.ArrayList;

public class SPost {
    private String post;
    private SAccount postOwner;
    private ArrayList<SAccount> likes;
    private ArrayList<SComment> comments;

    public SPost(String post, SAccount postOwner) {
        this.post = post;
        this.postOwner = postOwner;
        likes = new ArrayList<>();
        comments = new ArrayList<>();
    }
    public void addLike(SAccount account){
        likes.add(account);
    }
    public void addComment(SAccount account,String comment){
        comments.add(new SComment(account,comment));
    }
}
