package David;

import GraphClasses.SAccount;
import GraphClasses.SocialGraph;

public class QueryExecutor {
    private static final String newAccountCommand = "createAccount";
    private SAccount sAccount ;

    public QueryExecutor() {
    }
    public static void executeQuery(SocialGraph graph, String query){
        if(query.contains(newAccountCommand)){
            addAccQuery(graph,query.substring(newAccountCommand.length()+1));
        }
    }

    private static void addAccQuery(SocialGraph graph, String substring) {
        String[] namePass = substring.split(" ");
        try {
            graph.addNewAccount(namePass[0],namePass[1]);
        } catch (ProjectExceptions.AccountException e) {
            System.out.println(e.getMessage()+", couldn't create new account");
        }
    }



    public static void main(String[]args){
        SocialGraph graph = new SocialGraph();
        executeQuery(graph,"createAccount Ahmed 5678");
        executeQuery(graph,"createAccount Ahmed 5678");




    }
}
