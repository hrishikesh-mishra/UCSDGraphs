package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;
public class MapNode {
	
	private GeographicPoint location;
	private List<MapEdge> edges = new ArrayList();
			
	public MapNode(GeographicPoint location) {		
		this.location = location;		
	}
	
	public MapNode(GeographicPoint location, List<MapEdge> edges) {	
		this.location = location;
		this.edges = edges;
	}
		
	public GeographicPoint getLocation() {
		return location;
	}
	public void setLocation(GeographicPoint location) {
		this.location = location;
	}
	public List<MapEdge> getEdges() {
		return edges;
	}
	 
	public void addEdge(MapEdge edge){
		this.edges.add(edge);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;		
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapNode other = (MapNode) obj;		
		if (location == null) {
			if (other.location != null)
				return false;		
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MapNode [location=" + location + ", edges=" + edges + "]";
	}
}
