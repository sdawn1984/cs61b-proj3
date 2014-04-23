/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import dict.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g)
  {
    Object[] vertices = g.getVertices();
    Edge[] edges = new Edge[g.edgeCount()];
    WUGraph minSpanTree = new WUGraph();
    for (Object vertex : vertices)
    {
      minSpanTree.addVertex(vertex);
    }

    //get all edges. use getNeighbors and add each Edge to a HashTable. Check if
      //the Edge exists [O(1) since its a HashTable], don't add it. Otherwise, add
      //the Edge.

    //order edges based on length. Use compareTo to compare the weights of the Edge
      //and order accordingly.

    //map the vertices to integers
    DisjointSets set = new DisjointSets(vertices.length);
    HashTableChained verticesToIntegers = new HashTableChained(vertices.length);
    for (int v = 0; v < vertices.length; v++)
    {
      verticesToIntegers.insert(vertices[v], new Integer(v));
    }
    //iterate through edges
    for (Edge e : edges)
    {
      Object u = e.getU();
      Object v = e.getV();
      int uInt = (int)verticesToIntegers.find(u).value();
      int vInt = (int)verticesToIntegers.find(v).value();
      //check if u can get to v
      if (set.find(uInt) != set.find(vInt))
      {
        set.union(uInt, vInt);
        //if not, add the edge
        minSpanTree.addEdge(u, v, e.getWeight());
      }
    }
    return minSpanTree;
  }
}
