package GraphClasses;

import David.ProjectExceptions;
import David.QueryExecutor;
import David.SaveAndLoadData;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static David.QueryExecutor.executeQueryFile;

public class Main {

    public static void main(String[] args) throws ProjectExceptions.AccountException, IOException, ProjectExceptions.FollowSomeoneException, ProjectExceptions.AddFriendException {

/*
        //Creating a graph and saving it in file named output
        SocialGraph graph = new SocialGraph();
        graph.addNewAccount("Rameez","1234444");
        graph.addNewAccount("youssef","123");
        SaveAndLoadData.save(graph,"output");


        // loading the output file  in a graph object

        SocialGraph graph2 = SaveAndLoadData.load("output");
        System.out.println(graph2);
*/

       /*how to test using the graph Test1 and open the excel file to filter the graph and know results*/


       SocialGraph graph = new SocialGraph();
        QueryExecutor.executeQueryFile(graph,"res/Test1/createAccTest1.txt");
        QueryExecutor.executeQueryFile(graph,"res/Test1/addFriendsTest1.txt");
        System.out.println("Number of accounts in the graph = "+graph.getNumberOfAccounts());
        System.out.println(QueryExecutor.executeQueryFile(graph,"res/Test1/randomTest1.txt"));



























        /*-------------- Talaat Test -----------------------------------------------------------*/
//        SocialGraph talaatGraph=new SocialGraph();
//        HashMap<String,Double> map=new HashMap<>();
//        map.put("Mina",2.5);
//        map.put("David",5.0);
//        map.put("rizk",1.0);
//        map.put("ahmed",-1.0);
//        map.put("d",50.2);
//        /*
//        for(Map.Entry me:map.entrySet())
//        {
//            System.out.println("key="+me.getKey().toString()+" value="+(Double)me.getValue());
//        }
//        map=talaatGraph.sortByValues(map);
//        for(Map.Entry me:map.entrySet())
//        {
//            System.out.println("key="+me.getKey().toString()+" value="+(Double)me.getValue());
//        }
//*/
//        talaatGraph.addNewAccount("Mina","123");
//        talaatGraph.addNewAccount("Talaat","123");
//        talaatGraph.addNewAccount("David","123");
//        talaatGraph.addNewAccount("rizk","123");
//        talaatGraph.getAccount("rizk").followSomeone(talaatGraph.getAccount("Mina"));
//        talaatGraph.getAccount("David").followSomeone(talaatGraph.getAccount("Mina"));
//        talaatGraph.getAccount("Talaat").followSomeone(talaatGraph.getAccount("Mina"));
//        talaatGraph.getAccount("rizk").followSomeone(talaatGraph.getAccount("David"));
//        talaatGraph.getAccount("rizk").followSomeone(talaatGraph.getAccount("Talaat"));
//        talaatGraph.getAccount("rizk").addNewFriend(talaatGraph.getAccount("Talaat"));
//        ArrayList<String> influencingPeople=new ArrayList<>();
//        influencingPeople= talaatGraph.findInfluencingPeople(2);
//        for(int i=0;i<influencingPeople.size();i++)
//        {
//            System.out.println(influencingPeople.get(i)+" influencing value="+talaatGraph.getAccount(influencingPeople.get(i)).getAccountInfluencingValue());
//        }

        /*-------------------Talaat Test Ends-----------------------------------------------*/
    }







}
