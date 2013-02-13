import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.TreeSet;

public class Karger {

  public static void mainLoop(Graph g) {

    while (g.size() > 2) {
      Random random = new Random();
      random.setSeed(new Date().getTime());

      int size = g.getEdges().size();
      int n = random.nextInt(size);
      int v1 = g.getEdges().get(n).getV1();
      int v2 = g.getEdges().get(n).getV2();

      ArrayList<Edge> equalEdges = g.findEqualEdges(g.getEdges().get(n));
      for (Edge e : equalEdges) {
        g.getEdges().remove(e);
      }

      ArrayList<Edge> adjucentEdges = g.findEdges(v2);

      reconnectEdges(g, n, v1, v2, adjucentEdges);

    }

  }

  public static void reconnectEdges(Graph g, int n, int v1, int v2,
      ArrayList<Edge> edges) {

    for (Edge e : edges) {
      if (e.getV1() == v2) {
        e.setV1(v1);
      }
      if (e.getV2() == v2) {
        e.setV2(v1);
      }
      if (e.getV1() == e.getV2()) {
        g.getEdges().remove(e);
      }
    }
  }

  public static void main(String[] args) throws IOException {
    double min = Double.MAX_VALUE;
    for (int i = 0; i < 1000; i++) {
      double d = repeat();
      if (d < min) {
        min = d;
      }
    }
    System.out.println("Min cut is " + min);

  }

  public static double repeat() throws IOException {

    BufferedReader br = null;
    double sum = Double.MAX_VALUE;
    br = new BufferedReader(new FileReader("e:\\home\\kargerMinCut.txt"));
    Graph g = new Graph();

    for (int i = 0; i < 40000; i++) {
      initGraph(br, g);
      mainLoop(g);
      if (g.getEdges().size() / 2 < sum) {
        sum = g.getEdges().size() / 2;
      }
    }
    br.close();
    return sum;
  }

  private static void initGraph(BufferedReader br, Graph g) throws IOException {
    String line;
    while ((line = br.readLine()) != null) {
      String[] vertices = line.split("\\s+");
      int v1 = Integer.parseInt(vertices[0]);
      for (int i = 1; i < vertices.length; i++) {
        Edge e1 = new Edge(v1, Integer.parseInt(vertices[i]));
        if (!g.getEdges().contains(e1)) {
          g.addEdge(e1);
        }
      }
    }
  }
}

class Graph {
  private ArrayList<Edge> edges;

  public ArrayList<Edge> findEdges(int v1) {
    ArrayList<Edge> r = new ArrayList<Edge>();
    for (Edge e : edges) {
      if (e.getV1() == v1 || e.getV2() == v1) {
        r.add(e);
      }
    }
    return r;
  }

  public ArrayList<Edge> findEqualEdges(Edge e) {
    ArrayList<Edge> r = new ArrayList<Edge>();
    for (Edge edge : edges) {
      if ((e.getV1() == edge.getV1() && e.getV2() == edge.getV2())
          || (e.getV1() == edge.getV2() && e.getV2() == edge.getV1())) {

        r.add(e);

      }
    }
    return r;
  }

  public Graph(ArrayList<Edge> e) {
    this.edges = e;
  }

  public Graph() {
    this.edges = new ArrayList<Edge>();
  }

  public ArrayList<Edge> getEdges() {
    return edges;
  }

  public void addEdge(Edge e1) {
    edges.add(e1);
  }

  public int size() {
    TreeSet<Integer> verts = new TreeSet<Integer>();
    for (Edge e : edges) {
      verts.add(e.getV1());
      verts.add(e.getV2());
    }
    return verts.size();
  }

}