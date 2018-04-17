package GraphClasses;

import David.ProjectExceptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

import static GraphClasses.SaveAndLoad.*;

public class Main {

    public static void main(String[] args) throws ProjectExceptions.AccountException, IOException {

        SaveAndLoad SL = new SaveAndLoad();
        SocialGraph graph = new SocialGraph();























        try {
            graph.addNewAccount("ta","ta");
        } catch (ProjectExceptions.AccountException e) {
            e.printStackTrace();
        }

        graph.addNewAccount("david","david");


        SL.save(graph);


    }

    public int x(){return 5;}





}
