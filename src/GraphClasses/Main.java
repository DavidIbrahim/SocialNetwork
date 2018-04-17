package GraphClasses;

import David.ProjectExceptions;

import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;

import static GraphClasses.SaveAndLoad.*;

public class Main {

    public static void main(String[] args) throws ProjectExceptions.AccountException {

	    System.out.println("Mina");

        SocialGraph graph = new SocialGraph();

        try {
            graph.addNewAccount("mina","mina");
        } catch (ProjectExceptions.AccountException e) {
            e.printStackTrace();
        }

        graph.addNewAccount("david","david");


            SaveAndLoad SL = new SaveAndLoad();
                SL.Save(graph);




        }

    public int x(){return 5;}





}
