package GraphClasses;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/* import java.io.InputStreamReader; */
import java.io.Reader;


public class SaveAndLoad {


    public static  void Load (  SocialGraph x , Reader reader    )
    {
            System.out.println("loading");
            Gson gson = new GsonBuilder().create();
            x = gson.fromJson(reader, SocialGraph.class);
            System.out.println("loaded");
    }



    public static void save(SocialGraph graph) throws IOException {
        System.out.println("saving");
        Writer writer = new FileWriter("Output.json");
        Gson gson = new GsonBuilder().create();
        gson.toJson(graph, writer);

        writer.close();

        System.out.println("saved");


    }








}
