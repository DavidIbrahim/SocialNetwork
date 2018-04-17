package GraphClasses;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/* import java.io.InputStreamReader; */
import java.io.Reader;


public class SaveAndLoad {

    public static  void Save  ( SocialGraph x     )
           {
               Writer writer = null;
               try {
                   writer = new FileWriter("SocialGraph.json");
               } catch (IOException e) {
                   e.printStackTrace();
               }
               System.out.println("saving");
            Gson gson = new GsonBuilder().create();
            gson.toJson(x, writer);
            System.out.println("saved");


    }



    public static  void Load (  SocialGraph x , Reader reader    )
    {
            System.out.println("loading");
            Gson gson = new GsonBuilder().create();
            x = gson.fromJson(reader, SocialGraph.class);
            System.out.println("loaded");
    }

}
