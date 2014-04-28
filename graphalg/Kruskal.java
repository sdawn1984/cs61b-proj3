/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import dict.*;
import sorting.*;
import list.*;


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
    LinkedQueue edges = new LinkedQueue();
    WUGraph minSpanTree = new WUGraph();
    for (Object vertex : vertices)
    {
      minSpanTree.addVertex(vertex);
    }

    //get all edges. use getNeighbors and add each Edge to a LinkedQueue
    Object[] currentNeighborList;
    int[] currentWeightList;
    for (int counter = 0; counter < vertices.length; counter++) {
    	Neighbors current = g.getNeighbors(vertices[counter]);
    	currentNeighborList = current.neighborList;
    	currentWeightList = current.weightList;
      for (int i = 0; i < currentWeightList.length; i++)
      {
        Edge xEdge = new Edge(vertices[counter], currentNeighborList[i], currentWeightList[i]);
        edges.enqueue(xEdge);
      }
    }
    ListSorts.quickSort(edges);

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
    while (!edges.isEmpty())
    {
      Edge e = new Edge();
      try {
        e = (Edge)edges.dequeue();
      } catch (QueueEmptyException exception) {
        System.err.println(exception);
      }
      Object u = e.getU();
      Object v = e.getV();
      int uInt = (int)verticesToIntegers.find(u).value();
      int vInt = (int)verticesToIntegers.find(v).value();
      // check if u can get to v
      if (set.find(uInt) != set.find(vInt))
      {
        set.union(set.find(uInt), set.find(vInt));
        minSpanTree.addEdge(u, v, e.getWeight());
      }
    }
    return minSpanTree;
  }
}
