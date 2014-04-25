package graphalg;

public class Edge implements Comparable {
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

  //compares solely based on weight
  public int compareTo(Object o)
  {
    Edge e = (Edge)o;
    if (((Edge)e).getWeight() < getWeight())
      return 1;
    else if (((Edge)e).getWeight() > getWeight())
      return -1;
    else if (e.getU().equals(getU()) && e.getV().equals(getV()) && e.getWeight() == getWeight())
      return 0;
    return -1;
  }

  public int hashCode()
  {
    int vertices = Math.abs(u.hashCode() - v.hashCode() - weight);
    return vertices;
  }

  public boolean equals(Edge e)
  {
    return (e.getU().equals(getU()) && e.getV().equals(getV()) && e.getWeight() == getWeight());
  }

  public String toString()
  {
    return getU().toString() + " " + getV().toString() + " " + getWeight();
  }
}
