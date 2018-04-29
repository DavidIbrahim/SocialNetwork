package GraphClasses;

import David.ProjectExceptions;

import java.util.ArrayList;

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



    public SAccount(String name, String password) {
        this.name = name;
        this.password = password;
        friends = new ArrayList<>();
        posts = new ArrayList<>();
        numOfFollowers=0;
        follwedAccounts=new ArrayList<>();
    }

    public void addNewPost(String post) {
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

    @Override
    public String toString() {
        return "SAccount{" +
                "name='" + name + '\'' +
                ", friends=" + friends.size() +

                ", posts=" + posts.size() +
                '}';
    }


    public int getNumOfFollowers()
    {
        return numOfFollowers;
    }
}
