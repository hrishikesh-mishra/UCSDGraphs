/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 2
	
	private Map<GeographicPoint, MapNode> vertices = new HashMap();
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{	
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		return vertices.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		Set<GeographicPoint> vertices = new HashSet();
		for(MapNode node : this.vertices.values() )
			vertices.add(node.getLocation());
		
		return vertices; 
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		int totalEdge = 0;
		for(MapNode node: this.vertices.values())
			totalEdge += node.getEdges().size();
						
		return totalEdge;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		if(isVertexExists(location)) 
			return false;
		
		 vertices.put(location, new MapNode(location));
		 return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		if(!isVertexExists(from) || !isVertexExists(to))
			throw new IllegalArgumentException("from or to vertices are created before.");
		
		this.vertices.get(from).addEdge(new MapEdge(from, to, roadName, roadType, length));
		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		/** In case start or goal is null. **/
		if(Objects.isNull(start) || Objects.isNull(goal))
			throw new  IllegalArgumentException("Start and goal should not be null");
		
		/** In case start or goal vertex doesn't exist **/
		if(!isVertexExists(start) || !isVertexExists(goal))
			throw new  IllegalArgumentException("Start or goal doesn't exist.");
			
		Map<GeographicPoint, GeographicPoint> parentMap = new HashMap();
		
		/** Calling actual BFS search algo **/
		bfsSearch(start, goal, nodeSearched, parentMap);
		
		/** In case no route exists.**/
		if(parentMap.isEmpty()){
			System.out.println("No route found.");
			return null;
		}
		
		return createPath(start, goal, parentMap);
	}
	
	
	/**
	 * BFS search algorithm 
	 * @param start
	 * @param goal
	 * @param nodeSearched
	 * @param parentMap
	 */
	private void bfsSearch(GeographicPoint start, GeographicPoint goal, Consumer<GeographicPoint> nodeSearched, 
		       Map<GeographicPoint, GeographicPoint> parentMap)
	{
		
		Queue<MapNode> queue = new LinkedList<>(); 
		Set<GeographicPoint> visitedLocation = new HashSet();		
		boolean isFound = false;		
		queue.add(vertices.get(start));
		visitedLocation.add(start);
		
		while(!queue.isEmpty()){
			MapNode currentNode = queue.remove();
			
			if(currentNode.getLocation().equals(goal)) {								
				isFound = true;
				break; 
			}
			
			for(MapEdge edge: currentNode.getEdges()){				
				if(!visitedLocation.contains(edge.getEnd())){					
					visitedLocation.add(edge.getEnd());
					queue.add(vertices.get(edge.getEnd()));
					parentMap.put(edge.getEnd(), currentNode.getLocation());					
					// Hook for visualization.  See writeup.
					//nodeSearched.accept(edge.getEnd());					
				}				
			}				
		}
		
		/** In case path is not found **/
		if(!isFound){		 
			parentMap.clear();
		} 
	}
	
	/**
	 * Create path.
	 * @param start
	 * @param goal
	 * @param parentMap
	 * @return
	 */
	private List<GeographicPoint> createPath(GeographicPoint start, GeographicPoint goal, Map<GeographicPoint, GeographicPoint> parentMap){		 
		LinkedList<GeographicPoint> path = new LinkedList();		
		GeographicPoint curr = goal;
		while(!curr.equals(start)){
			path.addFirst(curr);			
			curr = parentMap.get(curr);							
		}
		return path; 
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/**
	 * Check vertex exists or not. 
	 * @param location
	 * @return
	 */
	private boolean isVertexExists(GeographicPoint location)
	{	 			
		return vertices.containsKey(location);
	}
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		
		/** Get path between [1.0, 1.0] to [8.0,  -1.0] **/
		List<GeographicPoint> path1 = theMap.bfs(new GeographicPoint(1.0, 1.0), new GeographicPoint(8.0, -1.0));
		System.out.println("Get path between [1.0, 1.0] to [8.0, -1.0] : ");
		for(GeographicPoint location: path1){
			System.out.println(location);	
		}
		
		/** Get path between [1.0, 1.0] to [6.5,  0.0] **/
		List<GeographicPoint> path2 = theMap.bfs(new GeographicPoint(1.0, 1.0), new GeographicPoint(6.5, 0.0));
		System.out.println("\nGet path between [1.0, 1.0] to [6.5,  0.0] : ");
		for(GeographicPoint location: path2){
			System.out.println(location);	
		}
		
		/** Get path between [4.0, 0.0] to [6.5,  0.0] **/
		List<GeographicPoint> path3 = theMap.bfs(new GeographicPoint(4.0, 0.0), new GeographicPoint(6.5, 0.0));
		System.out.println("\nGet path between [4.0, 0.0] to [6.5,  0.0] : ");
		for(GeographicPoint location: path3){
			System.out.println(location);	
		}
		// You can use this method for testing.  
		
		/* Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
