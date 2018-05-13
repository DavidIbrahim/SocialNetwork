package David;

import GraphClasses.SAccount;
import GraphClasses.SPost;
import GraphClasses.SocialGraph;
import David.ProjectExceptions.*;
import Rameez.Visualization;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class QueryExecutor {
     static final String newAccountCommand = "createAccount";
     static final String loginCommand = "login";
     static final String logoutCommand = "logout";
     static final String showMyFriendsCommand = "showMyFriends";
     static final String addFriendCommand = "addFriend";
     static final String makePostCommand = "post";
     static final String loginErrorMsg = "You need to login first";
     static final String showPostsCommand = "showPosts";
     static final String openPostCommand = "openPost";
     static final String makeCommentCommand = "makeComment";
     static final String suggestFriendsCommand = "suggestFriends";
     static final String followAccountCommand="followAccount";
     static final String showFollowedAccountsCommand = "showFollowedAccounts";
     static final String influencingAccountsCommand = "findInfluencingAccounts";
     static final String exitCommand = "exit";  // it saves data of the graph in currentGraphData directory
     static final String loadFromFileCommand = "loadFile";
     static final String visualizeGraphCommand = "visualizeGraph";
     static final String makeLikeCommand ="likePost";



    private static SAccount currentAccount;
    private static ArrayList<SPost> currentPosts;
    private static SPost currentPost;

    private static boolean exit = false;


    public static String executeQuery(SocialGraph graph, String query) throws AccountException, IOException {
        String reply = "";
        //create Account
        if (query.contains(newAccountCommand)) {
            reply = addAccQuery(graph, query.substring(newAccountCommand.length() + 1));
            currentPosts=null;
            currentPost = null;

        }

        else if (query.equals(visualizeGraphCommand)) {
            Visualization.visualizeSocialGraph(graph, true);
        }

        else if (query.equals(makeLikeCommand)) {
            if(currentPost == null)
                return "Error : you need to open Post first";
            currentPost.addLike(currentAccount);
        }


        else if (query.equals(exitCommand)){
            SaveAndLoadData.saveInJason(graph,"res/CurrentGraphData/savedData.json");
            SaveAndLoadData.SaveInExcel(graph,"res/CurrentGraphData");
            exit = true;
        }



        //login
        else if (query.substring(0, loginCommand.length()).equals(loginCommand)) {
            reply = loginQuery(graph, query.substring(loginCommand.length() + 1));
        }

        //logout
        else if (query.substring(0, logoutCommand.length()).equals(logoutCommand)) {
            if (currentAccount == null)
                reply = "Error : No account is logged in";
            else {
                currentAccount = null;
                currentPost = null;
                currentPosts = null;
            }
        }

        //show my friend

        else if (query.contains(showMyFriendsCommand)) {
            reply = showFriends();
        }

        else if(query.contains(showFollowedAccountsCommand))
        {
            reply=showFollowedPeople();
        }



        else if(query.substring(0,loadFromFileCommand.length()).equals(loadFromFileCommand)){
            reply = executeQueryFile(graph,query.substring(loadFromFileCommand.length()+1));
        }
        //make post
        else if (query.substring(0, makePostCommand.length()).equals(makePostCommand)) {
            reply = makePost(query.substring(makePostCommand.length() + 1));
        }



        else if (query.substring(0, showPostsCommand.length()).equals(showPostsCommand)) {
            reply = showPosts(graph, query);
        } else if (query.substring(0, openPostCommand.length()).equals(openPostCommand)) {
            reply = openPost(graph, query.substring(openPostCommand.length() + 1));
        }
        //add friend;
        else if (query.substring(0, addFriendCommand.length()).equals(addFriendCommand)) {
            reply = addFriend(graph, query.substring(addFriendCommand.length() + 1));
        }
        //make comment
        else if(query.substring(0,followAccountCommand.length()).equals(followAccountCommand))
        {
            reply=followAccount(graph,query.substring(followAccountCommand.length()+1));
        }
        else if (query.substring(0, makeCommentCommand.length()).equals(makeCommentCommand)) {
            reply = makeComment(query.substring(makeCommentCommand.length() + 1));
        }
        else if (query.substring(0, suggestFriendsCommand.length()).equals(suggestFriendsCommand)) {

            if(query.trim().length()==suggestFriendsCommand.length()){
                reply = suggestFriends(5,graph);
            }
            else {
                String noOfFriendsStr = query.substring(suggestFriendsCommand.length() + 1);
                reply = suggestFriends(Integer.parseInt(noOfFriendsStr.trim()),graph);
            }
        }

        else if (query.substring(0, influencingAccountsCommand.length()).equals(influencingAccountsCommand)) {

            if(query.trim().length()==influencingAccountsCommand.length()){
                reply=findInfluencingAccounts(10,graph);
            }
            else {
                String noOfFriendsStr = query.substring(influencingAccountsCommand.length() + 1);
                reply = findInfluencingAccounts(Integer.parseInt(noOfFriendsStr.trim()),graph);
            }

        }
        return reply;
    }


    private static String suggestFriends(int numberOfAccountsToSuggest,SocialGraph graph) throws AccountException {
        if(currentAccount==null){
            return loginErrorMsg;
        }
        else {
            try {
                ArrayList<String> suggestedAccounts = (ArrayList<String>) currentAccount.getSuggestedFriends(graph,numberOfAccountsToSuggest);

                return "Suggested: "+suggestedAccounts.toString();

            }  catch (SuggestedFriendsException e) {
                return "Error : "+e.getMessage();
            }
        }

    }
    private static String findInfluencingAccounts(int numOfInfluencingAccounts,SocialGraph graph)
    {
        return graph.findInfluencingPeople(numOfInfluencingAccounts).toString();
    }


    private static String openPost(SocialGraph graph, String substring) {
        if(isNumeric(substring)){
            int postNumber = Integer.parseInt(substring);
            if(postNumber<=     currentPosts.size()) {
                currentPost = currentPosts.get(postNumber - 1);
                return currentPost.toString();
            }
            else{
                return "Error: Post No. " + postNumber + " doesn't exist";
            }

        }
        else{
            for (SPost post:currentPosts
                 ) {
                if(post.getPost().equals(substring)){
                    currentPost = post;
                    return post.toString();
                }

            }
            return "Error: Couldn't Find the post";
        }

    }


    private static String showPosts(SocialGraph graph, String query) {

        currentPost = null;
        if (currentAccount == null) return loginErrorMsg;
        else {
            SAccount account;
            if (query.trim().length() == showPostsCommand.length())
                account = currentAccount;
            else {
                String name = query.substring(showPostsCommand.length() + 1);
                try {
                    account = graph.getAccount(name);
                } catch (AccountException e) {
                    return e.getMessage();
                }
            }
            currentPosts = account.getPosts();
            if(currentPosts.size()==0) return "Error : There's No posts in "+account.getName()+"'s account";
            StringBuilder stringBuilder = new StringBuilder();
            int counter = 1;
            for (SPost post :
                    account.getPosts()) {
                stringBuilder.append(counter+"- "+post.getPost()+'\n');
                counter++;
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);

            return stringBuilder.toString();

        }

    }
    private static String makeComment(String substring) {
        if (currentPost == null) {
            return "Error : you need to open a post first to make a comment";
        } else {
            currentPost.addComment(currentAccount,substring);
            return "";
        }
    }

    private static String makePost(String substring) {
        if (currentAccount == null) {
            return loginErrorMsg;
        } else {
            SocialGraph.addNewPost(currentAccount,substring);
            return "";
        }
    }

    private static String addFriend(SocialGraph graph, String substring) {
        String reply = "";
        if (currentAccount == null)
            reply = loginErrorMsg;
        else {

            try {
                currentAccount.addNewFriend(graph.getAccount(substring));
            } catch (AccountException e) {
                reply = "Error: "+e.getMessage();
            } catch (AddFriendException e) {
                reply = "Error: "+substring+ " is "+e.getMessage();
            }
        }
        return reply;
    }

    private static String followAccount(SocialGraph graph,String substring)
    {
        String reply = "";
        if (currentAccount == null)
            reply = loginErrorMsg;
        else {

            try {

                currentAccount.followSomeone(graph.getAccount(substring));
            } catch (AccountException e) {
                reply = "Error: "+e.getMessage();
            } catch (FollowSomeoneException e) {
                reply = "Error: "+substring+ " is "+e.getMessage();
            }
        }
        return reply;
    }

    private static String showFriends() {
        String reply ;
        currentPost = null;
        currentPosts = null;
        if (currentAccount == null)
            reply = loginErrorMsg;
        else {
            reply ="Friends: "+ currentAccount.getFriends().toString();
        }
        return reply;
    }

    private static String showFollowedPeople()
    {
        currentPost = null;
        String reply ;
        if (currentAccount == null)
            reply = loginErrorMsg;
        else {
            reply ="Followed People: "+ currentAccount.getFollowedPeople().toString();
        }
        return reply;
    }
    private static String loginQuery(SocialGraph graph, String substring) {
        String[] namePass = substring.split(" ");
        String reply = "";
        if(currentAccount==null) {
            try {
                currentAccount = graph.getAccount(namePass[0], namePass[1]);
                currentPosts = currentAccount.getPosts();
            } catch (AccountException e) {
                reply = e.getMessage() + ", couldn't login";
                currentPosts = null;
                currentAccount = null;
                currentPost = null;
            }
        }
        else{
            reply = "Error : you need to logout first to use login command";
        }
        return reply;
    }

    private static String addAccQuery(SocialGraph graph, String substring) {
        String reply = "";
        String[] namePass = substring.split(" ");
        try {
            graph.addNewAccount(namePass[0], namePass[1]);
        } catch (AccountException e) {
            reply = e.getMessage() + ", couldn't create new account";
        }

        return reply;
    }

    private static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

     static String executeQueryFile  (SocialGraph graph, String  queryFile) throws AccountException {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileReader randomTesting = new FileReader(queryFile);
            BufferedReader br = new BufferedReader(randomTesting);

            for (String line; (line = br.readLine()) != null; ) {
                String reply = executeQuery(graph, line);
                if (!reply.equals(""))
                    stringBuilder.append(reply+'\n');
            }



            br.close();
            randomTesting.close();
        } catch (FileNotFoundException e) {
            System.out.println("couldn't open  file");
        } catch (IOException e) {
            System.out.println("IO error");
        }


        return stringBuilder.toString();
    }
    public static void main(String[] args) throws AccountException, IOException {
        SocialGraph graph = null;
        try {
            graph = SaveAndLoadData.loadFromJason("res/CurrentGraphData/savedData.json");
        } catch (FileNotFoundException e) {
        }


        if(graph == null) graph = new SocialGraph();
        while (!exit) {
            try {
                String reply = executeQuery(graph, new Scanner(System.in).nextLine());
                if(reply.length()>1)
                    System.out.println(reply);

            }
            catch(Exception e){
                System.out.println("Error : wrong command");
            }

        }

    }
}
