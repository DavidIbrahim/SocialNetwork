package GraphClasses;

public class SComment {
    public String getCommentMaker() {
        return commentMaker;
    }

    public String getComment() {
        return comment;
    }

    private String commentMaker;
    private String comment;

    public SComment(SAccount commentMaker, String comment) {
        this.commentMaker = commentMaker.getName();
        this.comment = comment;
    }
}
