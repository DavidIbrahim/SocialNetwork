package GraphClasses;

import David.ProjectExceptions;
import David.SaveAndLoadData;

import java.io.*;

import static David.QueryExecutor.executeQueryFile;

public class Main {

    public static void main(String[] args) throws ProjectExceptions.AccountException, IOException {


       /* //Creating a graph and saving it in file named output
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
        executeQueryFile(graph,"res/Test1/createAccTest1.txt");
        executeQueryFile(graph,"res/Test1/addFriendsTest1.txt");
        System.out.println("Number of accounts in the graph = "+graph.getNumberOfAccounts());



    }







}
