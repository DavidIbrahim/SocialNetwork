package David;

public class ProjectExceptions {

    public static class AccountException extends Exception{

        public AccountException(MyExceptionCodes code) {

            super(code.getMsg());

        }

    }

}

