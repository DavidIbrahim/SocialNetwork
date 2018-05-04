package David;

import GraphClasses.SocialGraph;
import Rameez.Visualization;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class TestCasesMaker {
    private static ArrayList<ArrayList<String>>accountsData = new ArrayList<>();
    private static int numberOfAccounts ;
    private static int averageNoOfFriends;
    private static int minimumFamousPeopleNoOfFriends;
    private static int maxNumberOfFriendsMade = 0;
    private static int totalNoOfPosts ;
    private static final String NAMES_FILE = "res/Tests Data/boysNames.txt";


    public static SocialGraph  creatTest(String directoryPath, int numberOfAccounts,
                          int averageNumberOfFriends, int totalNumberOfPosts,int minimumFamousPeopleNoOfFriends) throws IOException, ProjectExceptions.AccountException {
        TestCasesMaker.numberOfAccounts = numberOfAccounts;
        TestCasesMaker.averageNoOfFriends=averageNumberOfFriends;
        TestCasesMaker.totalNoOfPosts = totalNumberOfPosts;
        TestCasesMaker.minimumFamousPeopleNoOfFriends = minimumFamousPeopleNoOfFriends;
        createAccounts(directoryPath +"/createAcc.txt");
        createAddFriendsCommand(directoryPath+"/addFriends.txt");
        createMakePostsCommand(directoryPath+"/makePosts.txt");
        creatCSVfiles(directoryPath+"/friendsTable.csv");
        SocialGraph graph = new SocialGraph();
        QueryExecutor.executeQueryFile(graph,directoryPath+"/createAcc.txt");
        QueryExecutor.executeQueryFile(graph,directoryPath+"/addFriends.txt");
        QueryExecutor.executeQueryFile(graph,directoryPath+"/makePosts.txt");
        SaveAndLoadData.SaveInExcel(graph,directoryPath);
        Visualization.visualizeSocialGraph(graph,true);

        return graph;



    }


    private  static void createAccounts(String  outputFileName) throws IOException {
        FileReader accounts = new FileReader(NAMES_FILE);
        FileWriter out = new FileWriter(outputFileName);
        BufferedReader br = new BufferedReader(accounts);
        Random random = new Random();


        for (String line; (line = br.readLine()) != null; ) {
            String  password = String.valueOf(random.nextInt(99999));
            ArrayList<String> temp = new ArrayList<>();
            temp.add(line);
            temp.add(password);
            temp.add("0");
            accountsData.add(temp);
            out.write("createAccount "+line+" "+password+'\n');
            if(accountsData.size()==numberOfAccounts)break;
        }

        out.close();
        br.close();
        accounts.close();

    }

    private static ArrayList<String> makePosts(String postsFilePath, String hashtagsFilePath) throws IOException {
        ArrayList<String> posts = new ArrayList<>();
        FileReader postsReader = new FileReader(postsFilePath);
        BufferedReader br = new BufferedReader(postsReader);

        for(String line ; (line = br.readLine())!=null;) {
            posts.add(line);
            if(posts.size()==totalNoOfPosts) break;

        }
        postsReader.close();
        br.close();

        ArrayList<String> hashTags = new ArrayList<>( );

        FileReader hashTagsReader = new FileReader(hashtagsFilePath);
        br = new BufferedReader(hashTagsReader);
        for(String line ; (line = br.readLine())!=null;) {
            hashTags.add(line);

        }
        br.close();
        hashTagsReader.close();

        ArrayList<String> completePosts = new ArrayList<>();
        Random random = new Random();

        for (String post:
             posts) {
            if(random.nextInt(5)>3) {
                completePosts.add(post + " " + hashTags.get(random.nextInt(hashTags.size())));
            }
            else   completePosts.add(post);

        }



        return completePosts;
    }

    private static void createMakePostsCommand(String outputFilePath) throws IOException {

        ArrayList<String> posts = makePosts("res/Tests Data/posts.txt","res/Tests Data/hashTags.txt");

        Random randomGenerator = new Random();
        FileWriter out = new FileWriter(outputFilePath);
        for (String post: posts
             ) {
            int randomNo = randomGenerator.nextInt(numberOfAccounts);
            String accountName = accountsData.get(randomNo).get(0);

            out.write(QueryExecutor.loginCommand +" "+accountName+" "+ accountsData.get(randomNo).get(1)+"\n");
            out.write(QueryExecutor.makePostCommand+" "+post+'\n');
            out.write(QueryExecutor.logoutCommand+'\n');
        }



        out.close();
    }
    private static void createAddFriendsCommand(String outputFileName) throws IOException {

        Random randomGenerator = new Random();
        FileWriter out = new FileWriter(outputFileName);
        for (int i = 0; i <numberOfAccounts ; i++) {

            ArrayList<Integer> indicesOfAddedFriends = new ArrayList<>();

            int numberOfFriends;
            if(randomGenerator.nextInt(100)>= 90){  // 10 % are famous
                numberOfFriends = minimumFamousPeopleNoOfFriends;
            }
            else{
                numberOfFriends = (int) (randomGenerator.nextInt(averageNoOfFriends +1)/1.5);
            }
            indicesOfAddedFriends.add(i);
            ArrayList<String> currentAccount = accountsData.get(i);
            out.write("login "+currentAccount.get(0)+" "+currentAccount.get(1)+"\n");

            while (numberOfFriends>indicesOfAddedFriends.size()-1&& currentAccount.size()<numberOfAccounts-3){
                int randomFriendIndex = randomGenerator.nextInt(numberOfAccounts);
                ArrayList<String> randomFriend = accountsData.get(randomFriendIndex);
                if(!indicesOfAddedFriends.contains(randomFriendIndex)){
                    if(!randomFriend.contains(currentAccount.get(0))) {
                        out.write("addFriend " + accountsData.get(randomFriendIndex).get(0) + '\n');
                        indicesOfAddedFriends.add(randomFriendIndex);
                        randomFriend.add(currentAccount.get(0));
                        currentAccount.add(randomFriend.get(0));
                        int numberOfcurrentAccountFriends = Integer.valueOf(currentAccount.get(2));
                        numberOfcurrentAccountFriends++;
                        currentAccount.set(2, String.valueOf(numberOfcurrentAccountFriends));
                        int numberOfrandomAccountFriends = Integer.valueOf(randomFriend.get(2));
                        numberOfrandomAccountFriends++;
                        randomFriend.set(2, String.valueOf(numberOfrandomAccountFriends));
                        if(numberOfcurrentAccountFriends>maxNumberOfFriendsMade || numberOfrandomAccountFriends>maxNumberOfFriendsMade)
                            maxNumberOfFriendsMade = Math.max(numberOfcurrentAccountFriends,numberOfrandomAccountFriends);

                    }
                }

            }
            out.write("logout\n");

        }
        out.close();
    }

    private static void creatCSVfiles(String fileName) throws IOException {
        FileWriter out = new FileWriter(fileName);
        StringBuilder sb = new StringBuilder();
        sb.append("Name,PassWord,Number of Friends");
        for (int i = 1; i <maxNumberOfFriendsMade+1 ; i++) {
            sb.append(",Friend"+i);
        }
        sb.append("\n");
        for (ArrayList<String> acc: accountsData
             ) {
            for (String str: acc
                 ) {
                sb.append(str+',');
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("\n");

        }



        out.write(sb.toString());
        out.close();
    }

    public static void main(String[]args) throws IOException, ProjectExceptions.AccountException {

        creatTest("res/Test2",20,2  ,10,5);
    }



}
