package GraphClasses;

import David.ProjectExceptions;
import Rameez.SuggestedFriend;
import David.ProjectExceptions.MyExceptionCodes;
import David.ProjectExceptions.SuggestedFriendsException;
import David.ProjectExceptions.AccountException;


import java.util.*;

public class SAccount {

    private String name;
    private String password;
    private ArrayList<String> friends;
    private ArrayList<SPost> posts;
    private int numOfFollowers;
    private ArrayList<String> follwedAccounts;
    private static final int followersWeight=2;
    private static final int friendsWeight=1;
    private static final double likesWeight=0.1;
    private double accountInfluencingValue;

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<SPost> getPosts() {
        return posts;
    }



    protected SAccount(String name, String password) {
        this.name = name;
        this.password = password;
        friends = new ArrayList<>();
        posts = new ArrayList<>();
        numOfFollowers=0;
        follwedAccounts=new ArrayList<>();
    }

    protected void addNewPost(String post) {
        posts.add(new SPost(post, this));
    }

    public void addNewFriend(SAccount newFriend) throws  ProjectExceptions.AddFriendException {
        if(!friends.contains(newFriend.name)) {
            friends.add(newFriend.name);
            newFriend.friends.add(this.name);
            newFriend.accountInfluencingValue+=(double)friendsWeight;
            accountInfluencingValue+=(double)friendsWeight;
        }
        else {
            throw new ProjectExceptions.AddFriendException(ProjectExceptions.MyExceptionCodes.ALREADY_FRIENDS);
        }
    }

    public void followSomeone(SAccount newAccount) throws ProjectExceptions.FollowSomeoneException
    {
        if(!follwedAccounts.contains(newAccount.name))
        {
            follwedAccounts.add(newAccount.name);
            newAccount.numOfFollowers++;
            newAccount.accountInfluencingValue+=(double)followersWeight;
        }
        else
        {
            throw new ProjectExceptions.FollowSomeoneException(ProjectExceptions.MyExceptionCodes.ALREADY_FOLLWED);
        }
    }
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public double getAccountInfluencingValue() {
        return accountInfluencingValue;
    }

    public void setAccountInfluencingValue(double accountInfluencingValue) {
        this.accountInfluencingValue = accountInfluencingValue;
    }
    public int getNumOfFolowers()
    {
        return numOfFollowers;
    }

    @Override
    public String toString() {
        return "SAccount{" +
                "name='" + name + '\'' +
                ", friends=" + friends.size() +

                ", posts=" + posts.size() +
                '}';
    }

    public List <String> getSuggestedFriends (SocialGraph All , int numberOfSuggested) throws AccountException, SuggestedFriendsException {
        ArrayList <String > suggestedFriendsNames = new ArrayList<>();
        ArrayList <SuggestedFriend> suggestedFriends = new ArrayList<>();
        HashMap< String, Integer> Detected = new HashMap();
        Queue <String> q = new LinkedList<>();
        if(this.getFriends().size()==(All.getNumberOfAccounts()-1)){
            throw new SuggestedFriendsException(MyExceptionCodes.NO_SUGGESTED_FRIENDS); // user is already friend with all graph.
        }
        /////////////////////Adding All friends of friends to queue q.
        for(int i=0;i<friends.size();i++){
            q.addAll(All.getAccount(friends.get(i)).getFriends());
        }
        while ((!q.isEmpty())&&Detected.size()<900){
            String currentUser = q.poll();
            if(!friends.contains(currentUser)&&(!this.name.equals(currentUser))){ ///making sure the friend of friend isn't already a friend or the user himself.
                Detected.put(currentUser,0);
                for(int i=0;i<All.getAccount(currentUser).getFriends().size();i++){
                    String friendOfCurrentUser = All.getAccount(currentUser).getFriends().get(i);
                    if(friends.contains(friendOfCurrentUser)){
                        Detected.put(currentUser,Detected.get(currentUser)+1); //// incrementing mutual friends for current user.
                    }
                    else {
                        q.add(friendOfCurrentUser);
                    }
                }
            }
    }
    if(Detected.size()==0){ ///// the user has no friends at all
       ArrayList <String> allUsers = new ArrayList<>(All.getAllAccounts());
       for(int i=0;i<allUsers.size();i++){
           suggestedFriendsNames.add(allUsers.get(i));
           if(i==numberOfSuggested)
               break;
       }
    }
    else {
        for (Map.Entry<String, Integer> entry : Detected.entrySet()) {
            SuggestedFriend s = new SuggestedFriend(entry.getKey(), entry.getValue());
            suggestedFriends.add(s);
        }
        suggestedFriendsNames = sortSuggested(suggestedFriends);

    }
        ArrayList<String> subSuggestedFriendsNames = new ArrayList<>();
        int range;
        if(suggestedFriendsNames.size()>10)
            range=10;
        else
            range=suggestedFriendsNames.size();
        for(int i=0;i<range;i++){
            subSuggestedFriendsNames.add(suggestedFriendsNames.get(i));
        }
        return subSuggestedFriendsNames; //// suggested friends will be maximum 10 , it could be less.
    }
    private ArrayList<String> sortSuggested (ArrayList<SuggestedFriend> S) {

            Collections.sort(S, (S1, S2) -> {
                if(S1.getProbability()<S2.getProbability()) return 1;
                else if (S1.getProbability()>S2.getProbability()) return -1;
                return 0;
            });
            ArrayList<String> sortedSuggested = new ArrayList<>();
        for (SuggestedFriend value : S) {
            sortedSuggested.add(value.getName());
        }
            return sortedSuggested;
        }



}
