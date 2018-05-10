package GraphClasses;

import David.ProjectExceptions;
import David.ProjectExceptions.*;
import javafx.util.Pair;


import java.util.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class SocialGraph {
    private HashMap<String, SAccount> allTheAccounts;
    private static HashMap<String, ArrayList<SPost>> hashTagsMap=new HashMap<>();


    public SocialGraph() {
        allTheAccounts = new HashMap<>();
    }

    public void addNewAccount(String name, String password) throws AccountException {

        if (allTheAccounts.containsKey(name)) {
            throw new AccountException(ProjectExceptions.MyExceptionCodes.ACCOUNT_EXIST);
        }

        allTheAccounts.put(name, new SAccount(name, password));
    }




    public SAccount getAccount(String name, String password) throws AccountException {

        if (allTheAccounts.containsKey(name)) {
            SAccount account = allTheAccounts.get(name);
            if (account.getPassword().equals(password))
                return account;
            else {
                throw new AccountException(ProjectExceptions.MyExceptionCodes.WRONG_PASSWORD);

            }
        } else {
            throw new AccountException(ProjectExceptions.MyExceptionCodes.NO_ACCOUNT);
        }

    }


    public SAccount getAccount(String name) throws AccountException {

        if (allTheAccounts.containsKey(name)) {
            return allTheAccounts.get(name);
        } else {
            throw new AccountException(ProjectExceptions.MyExceptionCodes.NO_ACCOUNT);
        }
    }

    public int getNumberOfAccounts() {
        return allTheAccounts.size();
    }


    @Override
    public String toString() {
        String s = "SocialGraph{" + '\n';
        for (String name : allTheAccounts.keySet()
                ) {
            s += name + '\n';

        }
        s += "Number Of Accounts = " + allTheAccounts.size() + '\n';
        s += '}';
        return s;

    }

    public ArrayList<String> getAllAccounts() {
        String[] names;
        Set<String> set = allTheAccounts.keySet();
        names = set.toArray(new String[0]);
        return new ArrayList<String>(Arrays.asList(names));
    }

    public static HashMap sortByValues(HashMap map) {
       // List list = new LinkedList(map.entrySet());
        ArrayList<Pair<String,Double>> list= new ArrayList(map.entrySet());
        // Defined Custom Comparator here
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue())
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        // Here I am copying the sorted list in HashMap
        // using LinkedHashMap to preserve the insertion order
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    public ArrayList<String> findInfluencingPeople(int wantedNumber) {
        //ArrayList<SAccount> influencingPeople=new ArrayList<>(wantedNumber);
        HashMap<String, Double> influencingPeople = new HashMap<>();
        int iterator = 0;
        for (Map.Entry me : allTheAccounts.entrySet()) {
            if (iterator < wantedNumber) {
                //String s=(String)me.getKey();
                influencingPeople.put(me.getKey().toString(), ((SAccount) me.getValue()).getAccountInfluencingValue());
                iterator++;
            } else {
                influencingPeople = sortByValues(influencingPeople);
                String s = "";
                double d = 0;
                int temp = 0;
                boolean replaceLast = false;
                for (Map.Entry me1 : influencingPeople.entrySet()) {
                    temp++;
                    if (((SAccount) me.getValue()).getAccountInfluencingValue() > (Double) me1.getValue() && !replaceLast) {
                        s = me.getKey().toString();
                        d = ((SAccount) me.getValue()).getAccountInfluencingValue();
                        replaceLast = true;
                    }
                    if (replaceLast && temp == iterator) {
                        influencingPeople.remove(me1.getKey().toString());
                        influencingPeople.put(s, d);
                    }
                }
                replaceLast = false;

            }
        }
        influencingPeople = sortByValues(influencingPeople);
        return new ArrayList<String>(influencingPeople.keySet());


    }


    //hashtag functionality
    public static void  addNewPost(SAccount account,String post){
        account.addNewPost(post);
        createHashTag(new SPost (post,account));

    }

    public static ArrayList<SPost> hashTagSearch(String key){
        return hashTagsMap.get(key); ///returns null if arraylist is empty
    }
    private static void createHashTag (SPost post){
        String postContent = post.getPost();
        int postLength = postContent.length();

        for(int i=0; i<postLength-1; i++){ //dont care if the last char is #
            if(postContent.charAt(i)=='#'){
                int spaceIndex = postContent.indexOf(' ',i); //int indexOf(int ch, int fromIndex)
                String key = new String();
                if(spaceIndex>0) {
                    key = postContent.substring(i, spaceIndex); //String substring(int beginIndex, int endIndex)
                    i = spaceIndex;
                }
                else key = postContent.substring(i);
                addHashTag(key,post);

            }
        }
    }
    private static void addHashTag(String key, SPost post){
        if(hashTagsMap==null || !hashTagsMap.containsKey(key)) {
            hashTagsMap.put(key, new ArrayList<>());
        }
        if(!hashTagsMap.get(key).contains(post)) {
            hashTagsMap.get(key).add(post);
            //System.out.println(key + ": " + post.getPost());
        }

    }
}

