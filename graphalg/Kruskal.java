/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;

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
    //get all edges
    //order edges based on length
    WUGraph minSpanTree = new WUGraph();
    //iterate through edges
    //check if u can get to v
    //if not, add the edge
    return minSpanTree;
  }

}
