package GraphClasses;

import David.ProjectExceptions;

import java.util.ArrayList;

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


}
