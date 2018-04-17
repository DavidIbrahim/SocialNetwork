package David;

import java.io.*;

import GraphClasses.Main;
import GraphClasses.SocialGraph;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/* import java.io.InputStreamReader; */


public class SaveAndLoadData {

    public static void save(SocialGraph graph, String fileName) throws IOException {
        Writer writer = new FileWriter("res/"+fileName+".json");
        Gson gson = new GsonBuilder().create();
        gson.toJson(graph, writer);

        writer.close();



    }


    public static SocialGraph load( String fileName) throws FileNotFoundException {

        Gson gson = new GsonBuilder().create();
        Reader reader = new InputStreamReader(new FileInputStream("res/"+fileName+".json"));

        SocialGraph graph= gson.fromJson(reader, SocialGraph.class);
        return graph;

    }




}
