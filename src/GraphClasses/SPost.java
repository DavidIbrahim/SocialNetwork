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
    public String getPost() {
        return post;
    }

    public String getPostOwner() {
        return postOwner;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public ArrayList<SComment> getComments() {
        return comments;
    }


    public void addLike(SAccount account){
        likes.add(account.getName());
    }

    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append(" "+postOwner+": "+post );
        for (SComment comment:
             comments) {
            sb.append('\n'+ "      "+comment.getCommentMaker()+": "+comment.getComment());
        }
        return sb.toString();
    }

    public void addComment(SAccount account, String comment){
        comments.add(new SComment(account,comment));
    }
}
