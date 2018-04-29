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

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<SPost> getPosts() {
        return posts;
    }



    public SAccount(String name, String password) {
        this.name = name;
        this.password = password;
        friends = new ArrayList<>();
        posts = new ArrayList<>();
    }

    public void addNewPost(String post) {
        posts.add(new SPost(post, this));
    }

    public void addNewFriend(SAccount newFriend) throws  ProjectExceptions.AddFriendException {
        if(!friends.contains(newFriend.name)) {
            friends.add(newFriend.name);
            newFriend.friends.add(this.name);
        }
        else {
            throw new ProjectExceptions.AddFriendException(ProjectExceptions.MyExceptionCodes.ALREADY_FRIENDS);
        }
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "SAccount{" +
                "name='" + name + '\'' +
                ", friends=" + friends.size() +

                ", posts=" + posts.size() +
                '}';
    }

    public ArrayList <String> getSuggestedFriends (SocialGraph All) throws AccountException, SuggestedFriendsException {
        ArrayList <String > suggestedFriendsNames = new ArrayList<String>();
        ArrayList <SuggestedFriend> suggestedFriends = new ArrayList<SuggestedFriend>();
        HashMap< String, Integer> Detected = new HashMap();
        Queue <String> Q = new LinkedList<String>();
        if(this.getFriends().size()==(All.getNumberOfAccounts()-1)){
            throw new SuggestedFriendsException(MyExceptionCodes.NO_SUGGESTED_FRIENDS); // user is already friend with all graph.
        }
        /////////////////////Adding All friends of friends to queue Q.
        for(int i=0;i<friends.size();i++){
            Q.addAll(All.getAccount(friends.get(i)).getFriends());
        }
        while ((!Q.isEmpty())&&(Detected.size()<10)){
            String currentUser = Q.poll();
            if(!friends.contains(currentUser)&&(this.name!=currentUser)){ ///making sure the friend of friend isn't already a friend or the user himself.
                Detected.put(currentUser,0);
                for(int i=0;i<All.getAccount(currentUser).getFriends().size();i++){
                    String friendOfCurrentUser = new String(All.getAccount(currentUser).getFriends().get(i));
                    if(friends.contains(friendOfCurrentUser)){
                        Detected.put(currentUser,Detected.get(currentUser)+1); //// incrementing mutual friends for current user.
                    }
                    else {
                        Q.add(friendOfCurrentUser);
                    }
                }
            }
    }
    if(Detected.size()==0){ ///// the user has no friends at all
       ArrayList <String> allUsers = new ArrayList<>(All.getAllAccounts());
       for(int i=0;i<allUsers.size();i++){
           suggestedFriendsNames.add(allUsers.get(i));
           if(i==10)
               break;
       }
    }
    else {
        for (Map.Entry<String, Integer> entry : Detected.entrySet()) {
            SuggestedFriend S = new SuggestedFriend(entry.getKey(), entry.getValue());
            suggestedFriends.add(S);
        }
        suggestedFriendsNames = sortSuggested(suggestedFriends);
    }
        return suggestedFriendsNames; //// suggested friends will be maximum 10 , it could be less.
    }
    public ArrayList<String> sortSuggested (ArrayList<SuggestedFriend> S) {
            ProbabilityCompare probabilityCompare = new ProbabilityCompare();
            Collections.sort(S, probabilityCompare);
            ArrayList<String> sortedSuggested = new ArrayList<String>();
            for (int i = 0; i < S.size(); i++) {
                sortedSuggested.add(S.get(i).getName());
            }
            return sortedSuggested;
        }

    class ProbabilityCompare implements Comparator<SuggestedFriend> {
        @Override
        public int compare (SuggestedFriend S1 , SuggestedFriend S2){
            if(S1.getProbability()<S2.getProbability()) return 1;
            else if (S1.getProbability()>S2.getProbability()) return -1;
            else return 0;
        }
    }



    }
