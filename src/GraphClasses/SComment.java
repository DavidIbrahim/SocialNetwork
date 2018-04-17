package GraphClasses;

public class SComment {
    private String commentMaker;
    private String comment;

    public SComment(SAccount commentMaker, String comment) {
        this.commentMaker = commentMaker.getName();
        this.comment = comment;
    }
}
