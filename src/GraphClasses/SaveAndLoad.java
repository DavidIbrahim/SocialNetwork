package GraphClasses;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/* import java.io.InputStreamReader; */
import java.io.Reader;


public class SaveAndLoad {

    public static void save(SocialGraph graph) throws IOException {
        System.out.println("saving");
        Writer writer = new FileWriter("Out.json");
        Gson gson = new GsonBuilder().create();
        gson.toJson(graph, writer);

        writer.close();

        System.out.println("saved");


    }


    public static void load(SocialGraph graph, Reader reader) {
        System.out.println("loading");
        Gson gson = new GsonBuilder().create();

        graph= gson.fromJson(reader, SocialGraph.class);
        System.out.println("loaded");

    }




}
