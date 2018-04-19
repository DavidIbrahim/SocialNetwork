package David;

import java.io.*;
import java.util.ArrayList;
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

    public static void main(String[]args) throws IOException {
        accountsData = new ArrayList<>(numberOfAccounts);
        createAccounts("res/boysNames.txt","res/Test1/createAccTest1.txt");
        createAddFriendsCommand("res/Test1/addFriendsTest1.txt");
        creatCSVfiles("res/Test1/firendsTableTest1.csv");



    }




}
