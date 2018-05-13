package David;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeSet;

import GraphClasses.Main;
import GraphClasses.SAccount;
import GraphClasses.SPost;
import GraphClasses.SocialGraph;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/* import java.io.InputStreamReader; */


public class SaveAndLoadData {

    public static void saveInJason(SocialGraph graph, String fileName) throws IOException {
        Writer writer = new FileWriter(fileName);
        Gson gson = new GsonBuilder().create();
        gson.toJson(graph, writer);

        writer.close();



    }


    public static SocialGraph loadFromJason(String fileName) throws FileNotFoundException {

        Gson gson = new GsonBuilder().create();
        Reader reader = new InputStreamReader(new FileInputStream(fileName));

        SocialGraph graph= gson.fromJson(reader, SocialGraph.class);
        return graph;

    }

    public static void SaveInExcel(SocialGraph graph,String fileDirectory) throws IOException, ProjectExceptions.AccountException {
        FileWriter out = new FileWriter(fileDirectory+"/AccountsData.csv");
        StringBuilder sb = new StringBuilder();
        sb.append("Name,PassWord,Number of Friends,NoOfPosts,Influencing Value");
        sb.append("\n");

            for (String accountName: graph.getAllAccounts()
                    ) {
                sb.append(accountName+",");
                sb.append(graph.getAccount(accountName).getPassword()+",");
                sb.append(graph.getAccount(accountName).getFriends().size()+",");
                sb.append(graph.getAccount(accountName).getPosts().size()+",");
                sb.append(graph.getAccount(accountName).getAccountInfluencingValue()+",");
                sb.append("\n");
            }


        out.write(sb.toString());
        out.close();

        sb = new StringBuilder();
        FileWriter out2 = new FileWriter(fileDirectory+"/PostsData.csv");
        int numberOfPosts = 0;
        TreeSet<String> hashTags = new TreeSet<>();
        sb.append("Post,PostOwner,Hashtag,No Of Comments,No Of likes\n");
        for (String accountName: graph.getAllAccounts()
             ) {
            SAccount account = graph.getAccount(accountName);

            for (SPost post: account.getPosts()
                 ) {
                numberOfPosts++;

                String sPost = post.getPost();
                sb.append(sPost+",");
                sb.append(post.getPostOwner()+",");
                if(sPost.contains("#")) {
                    int  positionOfHashtag = sPost.indexOf("#");
                    int endOfHashtag = sPost.indexOf(" ",positionOfHashtag);
                    if(endOfHashtag == -1)
                        endOfHashtag = sPost.length();

                    String hashTag =sPost.substring(positionOfHashtag,endOfHashtag);
                    hashTags.add(hashTag);
                    sb.append(hashTag+",");
                }
                else {
                    sb.append(",");
                }
                sb.append(post.getComments().size()+",");
                sb.append(post.getLikes().size()+"\n");

            }
        }
        sb.append("\n");
        sb.append("\n");
        sb.append("Hash Tag"+",");
        sb.append("Number Of Repetations"+"\n");
        numberOfPosts ++;      // cuz the cells of hashtags starts from 2nd row
        for(String hashtag ; (hashtag= hashTags.pollFirst()) != null;){
            sb.append(hashtag+",");
            String x= "\"=COUNTIF(C2:c"+numberOfPosts+",\"\""+hashtag+"\""+"\")\"";
            sb.append(x );
            sb.append("\n");
        }


        out2.write(sb.toString());
        out2.close();

    }




}
