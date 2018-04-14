package GraphClasses;

public class SComment {
    private SAccount commentMaker;
    private String comment;

    public SComment(SAccount commentMaker, String comment) {
        this.commentMaker = commentMaker;
        this.comment = comment;
    }
}
