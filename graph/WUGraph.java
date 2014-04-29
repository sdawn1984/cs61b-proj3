/* WUGraph.java */

package graph;

import dict.*;
import set.*;
import list.*;

/**
 * The WUGraph class represents a weighted, undirected graph. Self-edges are   
 * permitted.
 */

public class WUGraph {

	private HashTableChained aList;
	private HashTableChained eList;
	private DList keys;
	private int edges;

	/**
	 * WUGraph() constructs a graph having no vertices or edges.
	 * 
	 * Running time: O(1).
	 */
	public WUGraph() {
		this.aList = new HashTableChained();
		this.eList = new HashTableChained();
		this.keys = new DList();
		this.edges = 0;
	}

	/**
	 * vertexCount() returns the number of vertices in the graph.
	 * 
	 * Running time: O(1).
	 */
	public int vertexCount() {
		return this.aList.size();
	}

	/**
	 * edgeCount() returns the total number of edges in the graph.
	 * 
	 * Running time: O(1).
	 */
	public int edgeCount() {
		return this.eList.size();
	}

	/**
	 * getVertices() returns an array containing all the objects that serve as
	 * vertices of the graph. The array's length is exactly equal to the number
	 * of vertices. If the graph has no vertices, the array has length zero.
	 * 
	 * (NOTE: Do not return any internal data structure you use to represent
	 * vertices! Return only the same objects that were provided by the calling
	 * application in calls to addVertex().)
	 * 
	 * Running time: O(|V|).
	 */
	public Object[] getVertices() {

		Object[] vertices = new Object[this.keys.length()];

		try {
			DListNode first = (DListNode) this.keys.front();
			int counter = 0;
			
			//for each Vertex in the vertex DList, copy each item's reference to an array
			while (first != null) {

				try {
					vertices[counter] = ((VertexPair) first.item()).object1;

					first = (DListNode) first.next();
					counter++;
				} catch (Exception e) {
					break;
				}
			}
		} catch (Exception e) {
		}

		return vertices;
	}

	/**
	 * addVertex() adds a vertex (with no incident edges) to the graph. The
	 * vertex's "name" is the object provided as the parameter "vertex". If this
	 * object is already a vertex of the graph, the graph is unchanged.
	 * 
	 * Running time: O(1).
	 */
	public void addVertex(Object vertex) {

		/*if the vertex doesn't exist, push vertex on to vertex DList and add reference to
		vertex hashtable*/
		if (this.aList.find(vertex) == null) {
			this.aList.insert(vertex,
					this.keys.insertBack(new VertexPair(vertex, new DList())));
		}
	}

	/**
	 * removeVertex() removes a vertex from the graph. All edges incident on the
	 * deleted vertex are removed as well. If the parameter "vertex" does not
	 * represent a vertex of the graph, the graph is unchanged.
	 * 
	 * Running time: O(d), where d is the degree of "vertex".
	 */
	public void removeVertex(Object vertex) {
		
		//remove vertex from vertex hashtable
		Entry entry = this.aList.remove(vertex);
		if (entry != null) {
			DListNode node = (DListNode) entry.value();
			DList list = (DList) ((VertexPair) node.item()).object2;
			try {
				DListNode first = (DListNode) list.front();
				
				/*for each vertex connected to the vertex being removed, remove edge 
				from hashtable, and remove all references to vertex from other adjacency lists*/   
				while (first != null) {

					try {
						
						//find edge in hashtable
						VertexPair key = new VertexPair(vertex, first.item());
						Entry e = this.eList.find(key);
						if (e != null) {
							
							//find references to dlistnodes in the vertex adjacency lists
							VertexPair pair = (VertexPair) ((VertexPair) e
									.value()).object1;
							boolean skipped = false;
							
							//remove dlistnodes from adjancency lists
							if (((DListNode) pair.object1).item().equals(
									first.item())) {
								if (((DListNode) pair.object2).item().equals(
										first.item())) {
									first = (DListNode) first.next();
									skipped = true;
								}
								((DListNode) pair.object2).remove();
							} else {
								((DListNode) pair.object1).remove();
							}

							if (!skipped) {
								first = (DListNode) first.next();
							}
							
							//remove edge from edge hashtable
							this.eList.remove(key);
						} else {
							first = (DListNode) first.next();
						}
					} catch (Exception e) {
						break;
					}
				}
				
				//remove node form vertex dlist
				node.remove();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * isVertex() returns true if the parameter "vertex" represents a vertex of
	 * the graph.
	 * 
	 * Running time: O(1).
	 */
	public boolean isVertex(Object vertex) {
		return this.aList.find(vertex) != null;
	}

	/**
	 * degree() returns the degree of a vertex. Self-edges add only one to the
	 * degree of a vertex. If the parameter "vertex" doesn't represent a vertex
	 * of the graph, zero is returned.
	 * 
	 * Running time: O(1).
	 */
	public int degree(Object vertex) {
		Entry e = this.aList.find(vertex);

		if (e != null) {
			
			//return length of adjacency list for vertex dlistnode in vertex dlist
			DList list = (DList) ((VertexPair) ((DListNode) e.value()).item()).object2;
			return list.length();
		}

		return 0;
	}

	/**
	 * getNeighbors() returns a new Neighbors object referencing two arrays. The
	 * Neighbors.neighborList array contains each object that is connected to
	 * the input object by an edge. The Neighbors.weightList array contains the
	 * weights of the corresponding edges. The length of both arrays is equal to
	 * the number of edges incident on the input vertex. If the vertex has
	 * degree zero, or if the parameter "vertex" does not represent a vertex of
	 * the graph, null is returned (instead of a Neighbors object).
	 * 
	 * The returned Neighbors object, and the two arrays, are both newly
	 * created. No previously existing Neighbors object or array is changed.
	 * 
	 * (NOTE: In the neighborList array, do not return any internal data
	 * structure you use to represent vertices! Return only the same objects
	 * that were provided by the calling application in calls to addVertex().)
	 * 
	 * Running time: O(d), where d is the degree of "vertex".
	 */
	public Neighbors getNeighbors(Object vertex) {
		Neighbors one = null;
		Entry entry = this.aList.find(vertex);

		if (entry != null) {
			
			//find vertex adjancency list in vertex DList
			DList neighbors = (DList) ((VertexPair) ((DListNode) entry.value())
					.item()).object2;
			one = new Neighbors();
			Object[] neighborList;
			int[] weights;

			neighborList = new Object[neighbors.length()];
			weights = new int[neighborList.length];

			if (neighborList.length == 0) {
				return null;
			}

			try {
				
				//for each vertex in vertex adjacency list, copy object to array
				DListNode first = (DListNode) neighbors.front();
				for (int i = 0; i < neighborList.length; i++) {
					neighborList[i] = first.item();
					
					//find weight of edge in edge hashtable and copy to array
					VertexPair key = new VertexPair(vertex, first.item());
					weights[i] = (Integer) ((VertexPair) this.eList.find(key)
							.value()).object2;

					first = (DListNode) first.next();
				}
			} catch (Exception e) {
			}

			one.neighborList = neighborList;
			one.weightList = weights;
		}

		return one;
	}

	/**
	 * addEdge() adds an edge (u, v) to the graph. If either of the parameters u
	 * and v does not represent a vertex of the graph, the graph is unchanged.
	 * The edge is assigned a weight of "weight". If the graph already contains
	 * edge (u, v), the weight is updated to reflect the new value. Self-edges
	 * (where u == v) are allowed.
	 * 
	 * Running time: O(1).
	 */
	public void addEdge(Object u, Object v, int weight) {

		// check if vertices exist
		if (this.aList.find(u) != null && this.aList.find(v) != null) {

			// check if edge exists
			if (this.eList.find(new VertexPair(u, v)) != null) {
				((VertexPair) this.eList.find(new VertexPair(u, v)).value()).object2 = weight;
			} else {
				
				//insert vertices to back of vertex adjacency list
				VertexPair pair = new VertexPair(u, v);
				DListNode first = (DListNode) ((DList) ((VertexPair) ((DListNode) this.aList
						.find(u).value()).item()).object2).insertBack(v);
				DListNode sec = first;
				
				//insert reference to vertex 1 to adjacency list of vertex 2 if vertex 1 is not vertex 2
				if (u != v) {
					sec = (DListNode) ((DList) ((VertexPair) ((DListNode) this.aList
							.find(v).value()).item()).object2).insertBack(u);
				}

				//add edge to edge hashtable
				VertexPair option = new VertexPair(new VertexPair(first, sec),
						weight);
				this.eList.insert(pair, option);
				this.edges++;
			}
		}
	}

	/**
	 * removeEdge() removes an edge (u, v) from the graph. If either of the
	 * parameters u and v does not represent a vertex of the graph, the graph is
	 * unchanged. If (u, v) is not an edge of the graph, the graph is unchanged.
	 * 
	 * Running time: O(1).
	 */
	public void removeEdge(Object u, Object v) {
		
		//find edge in edge hashtable
		if (this.eList.find(new VertexPair(u, v)) != null) {
			VertexPair pair = (VertexPair) this.eList.remove(
					new VertexPair(u, v)).value();
			
			//find each vertex dlistnodes and remove each other's references from each other's adjacency list
			VertexPair nodes = (VertexPair) pair.object1;

			((DListNode) nodes.object1).remove();
			if (nodes.object1 != nodes.object2) {
				((DListNode) nodes.object2).remove();
			}
		}
	}

	/**
	 * isEdge() returns true if (u, v) is an edge of the graph. Returns false if
	 * (u, v) is not an edge (including the case where either of the parameters
	 * u and v does not represent a vertex of the graph).
	 * 
	 * Running time: O(1).
	 */
	public boolean isEdge(Object u, Object v) {
		return this.eList.find(new VertexPair(u, v)) != null;
	}

	/**
	 * weight() returns the weight of (u, v). Returns zero if (u, v) is not an
	 * edge (including the case where either of the parameters u and v does not
	 * represent a vertex of the graph).
	 * 
	 * (NOTE: A well-behaved application should try to avoid calling this method
	 * for an edge that is not in the graph, and should certainly not treat the
	 * result as if it actually represents an edge with weight zero. However,
	 * some sort of default response is necessary for missing edges, so we
	 * return zero. An exception would be more appropriate, but also more
	 * annoying.)
	 * 
	 * Running time: O(1).
	 */
	public int weight(Object u, Object v) {
		Entry entry = this.eList.find(new VertexPair(u, v));
		if (entry != null) {
			VertexPair pair = (VertexPair) entry.value();
			return (Integer) pair.object2;
		}

		return 0;
	}
}
