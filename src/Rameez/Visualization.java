package Rameez;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.JFrame;

import David.ProjectExceptions;
import GraphClasses.SocialGraph;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.apache.commons.collections15.Transformer;

public class Visualization {

    private static BasicVisualizationServer visualize (SocialGraph graph ,ArrayList<String> path) throws ProjectExceptions.AccountException {
        Graph<String, Integer> g = new SparseGraph<>();
        Layout<String, Integer> layout = new FRLayout2<>(g); // sets the layout of the drawn graph
        layout.setSize(new Dimension(1000, 700));// sets the initial size of the space
        ArrayList<String> users = new ArrayList<>(graph.getAllAccounts());
        int totalNumEdges = 1;
        for (int i = 0; i < users.size(); i++) {
            ArrayList<String> Friends = new ArrayList<>(graph.getAccount(users.get(i)).getFriends());
            int numberOfEdges = Friends.size();
            for (int j = 0; j < numberOfEdges; j++) {
                if(path.contains(users.get(i))){
                    if(path.indexOf(users.get(i))<path.size()-1){
                        if(Friends.get(j).equals(path.get(path.indexOf(users.get(i))+1))){
                            g.addEdge(-totalNumEdges, users.get(i), Friends.get(j));
                            }
                    }
                }
                else
                    g.addEdge(totalNumEdges, users.get(i), Friends.get(j));
                totalNumEdges = g.getEdgeCount()+1;

            }
        }
        BasicVisualizationServer<String, Integer> vv =
                new BasicVisualizationServer<>(layout);
        return vv;

    }
    public static BasicVisualizationServer visualize(SocialGraph graph) throws ProjectExceptions.AccountException{
        ArrayList<String> s = new ArrayList<>();
        return visualize(graph, s);
    }
    public static void visualizeSocialGraph(SocialGraph graph) throws ProjectExceptions.AccountException {
        visualizeSocialGraph(graph, false);
    }


    public static void visualizeSocialGraph(SocialGraph graph, boolean showNames) throws ProjectExceptions.AccountException {
        int width, height;

        if (showNames) {
            width = 40;
            height = 40;
        } else {
            width = 20;
            height = 20;
        }

        BasicVisualizationServer<String, Integer> vv = visualize(graph);
        vv.setPreferredSize(new Dimension(1000, 1000)); //Sets the viewing area size
        Transformer<String, Paint> vertexPaint = i -> Color.GREEN;
        Transformer<String, Shape> vertexSize = s -> {
            Ellipse2D circle = new Ellipse2D.Double(-15, -15, width, height);
            return circle;
        };
        Transformer<Integer, Paint> edgePaint = i -> Color.GRAY;
        if (showNames) {
            vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
            vv.getRenderContext().setVertexShapeTransformer(vertexSize);
            vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        }
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

        // vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

        // vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
        // vv.getRenderContext().setEdgeStrokeTransformer(edgeStroke);
        JFrame frame = new JFrame("Social Network");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }

    public static void drawShortestPath(SocialGraph graph , boolean showNames ,ArrayList<String> path) throws ProjectExceptions.AccountException {
        int width, height;

        if (showNames) {
            width = 40;
            height = 40;
        } else {
            width = 20;
            height = 20;
        }

        BasicVisualizationServer<String, Integer> vv = visualize(graph,path);
        vv.setPreferredSize(new Dimension(1000, 1000)); //Sets the viewing area size
        Transformer<String, Paint> vertexPaint = i -> Color.GREEN;
        Transformer<Integer, Paint> edgePaint = i -> {

            if (i<0){
                return Color.RED;
            }
            else {
                return Color.DARK_GRAY;
            }
        };

        Transformer<String, Shape> vertexSize = s -> {
            Ellipse2D circle = new Ellipse2D.Double(-15, -15, width, height);
            return circle;
        };
        if (showNames) {
            vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
            vv.getRenderContext().setVertexShapeTransformer(vertexSize);
            vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        }
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

        // vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

        vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
        // vv.getRenderContext().setEdgeStrokeTransformer(edgeStroke);
        JFrame frame = new JFrame("ShortestPath");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);


    }
}