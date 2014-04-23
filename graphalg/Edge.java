package graphalg;

public class Edge {
  protected Object u;
  protected Object v;
  protected int weight;

  public Edge()
  {
    this(null, null, 0);
  }

  public Edge(Object u, Object v, int weight)
  {
    this.u = u;
    this.v = v;
    this.weight = weight;
  }

  public Object getU()
  {
    return u;
  }

  public Object getV()
  {
    return v;
  }

  public int getWeight()
  {
    return weight;
  }
}
