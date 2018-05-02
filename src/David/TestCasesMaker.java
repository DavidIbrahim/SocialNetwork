package David;

import GraphClasses.SocialGraph;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class TestCasesMaker {
    private static ArrayList<ArrayList<String>>accountsData;
    private static int numberOfAccounts = 1000;
    private static int maxCommonNumberOfFriends = numberOfAccounts/60 -2;
    private static int maxRareNumberOfFriends = 1000/16;
    private static int maxNumberOfFriendsMade = 0;




    private  static void createAccounts(String accountsFilesNames,String  outputFileName) throws IOException {
        FileReader accounts = new FileReader(accountsFilesNames);
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

    private static void createMakePostsCommand(String outputFilePath
            , SocialGraph graph, ArrayList<String> famousPeople, ArrayList<String> posts) throws IOException, ProjectExceptions.AccountException {


        ArrayList<String> allAcounts = graph.getAllAccounts();
        Random randomGenerator = new Random();
        FileWriter out = new FileWriter(outputFilePath);
        for (String post: posts
             ) {
            int randomNo = randomGenerator.nextInt(allAcounts.size()*2);
            String accountName = null;
            if(randomNo>=allAcounts.size()){
                accountName = famousPeople.get(randomNo %famousPeople.size());
            }
            else {
                accountName = allAcounts.get(randomNo);
            }
            out.write(QueryExecutor.loginCommand +" "+accountName+" "+graph.getAccount(accountName).getPassword()+'\n');
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

            int numberOfFriends = randomGenerator.nextInt(maxCommonNumberOfFriends+1);
            if(numberOfFriends> maxCommonNumberOfFriends-2 &&randomGenerator.nextInt()>maxCommonNumberOfFriends-5){
                numberOfFriends = maxRareNumberOfFriends;
            }
            indicesOfAddedFriends.add(i);
            ArrayList<String> currentAccount = accountsData.get(i);
            out.write("login "+currentAccount.get(0)+" "+currentAccount.get(1)+"\n");

            while (numberOfFriends>indicesOfAddedFriends.size()-1){
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
     /*   accountsData = new ArrayList<>(numberOfAccounts);
        createAccounts("res/boysNames.txt","res/Test1/createAccTest1.txt");
        createAddFriendsCommand("res/Test1/addFriendsTest1.txt");
        creatCSVfiles("res/Test1/firendsTableTest1.csv");
*/


     ArrayList<String> posts = makePosts("res/Tests Data/posts.txt","res/Tests Data/hashTags.txt");
        System.out.println(posts.size());
        SocialGraph graph =  new SocialGraph();
        QueryExecutor.executeQueryFile(graph,"res/Test1/createAccTest1.txt");
        QueryExecutor.executeQueryFile(graph,"res/Test1/addFriendsTest1.txt");
        QueryExecutor.executeQueryFile(graph,"res/Test1/makePostsCommandTest1.txt");
        SaveAndLoadData.SaveInExcel(graph,"res/Test1/");

       /* ArrayList<String> famousPeopleTest1 = new ArrayList<>(Arrays.asList(new String[]{"Dalton", "Tristen", "Zackary",
                "Alvaro", "Samson", "Jaxton", "Judson", "Steve", "Harrison", "Javier", "Maximus",
                "Thatcher", "Braxton", "Bruce", "Fabian", "Marco",
                "Noah", "Walker", "Emory", "Kamren", "Moshe", "Orlando", "Paul",
                "Shawn", "Skyler", "Sylas", "Bowen", "Brock", "Clark", "Colt", "Damari", "Damon", "Joshua",
                "Malcolm", "Marlon", "Riaan", "Trent", "Uriel", "Zaire", "Connor", "Derrick", "Duke",
                "Isaac", "Jadon", "Lawson","Leandro",
                "Seamus", "Van", "Ayaan", "Boston", "Camilo", "Henry", "Lyric",
                "Jamir", "Kristian", "Bruno", "Christopher", "Forrest",
                "Gage", "Kamdyn", "Layton", "Michael", "Milo", "Titus", "Jackson", "Hayden"
        }));

        createMakePostsCommand("res/Test1/makePostsCommandTest1.txt",graph,famousPeopleTest1,posts);*/
    }



}
