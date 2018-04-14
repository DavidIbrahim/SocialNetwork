package David;

public enum MyExceptionCodes {
    WRONG_PASSWORD(0, "The password is wrong"),
    NO_ACCOUNT(1, "There is no such a name"),
    ACCOUNT_EXIST(2,"The account already exists");


    private final int id;
    private final String msg;

    MyExceptionCodes(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public int getId() {
        return this.id;
    }

    public String getMsg() {
        return this.msg;
    }
}
