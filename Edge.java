public class Edge implements Comparable<Edge> {
  private int v1;
  private int v2;
  public int id;
  private static int number = 0;

  public Edge(int v1, int v2) {
    this.v1 = v1;
    this.v2 = v2;
    id = number++;
  }

  public void setV1(int a) {
    v1 = a;
  }

  public void setV2(int a) {
    v2 = a;
  }

  public int getV1() {
    return v1;
  }

  public int getV2() {
    return v2;
  }

  @Override
  public int compareTo(Edge e) {
    if ((e.v1 == this.v1 && e.v2 == this.v2)
        || (e.v1 == this.v2 && e.v2 == this.v1)) {
      return 0;
    } else {
      return 1;
    }

  }
}