package GraphClasses;

public class SComment {


    private String commentMaker;
    private String comment;


    public String getCommentMaker() {
        return commentMaker;
    }

    public String getComment() {
        return comment;
    }


    public SComment(SAccount commentMaker, String comment) {
        this.commentMaker = commentMaker.getName();
        this.comment = comment;
    }


}
