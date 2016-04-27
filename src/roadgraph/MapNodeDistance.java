package roadgraph;

public class MapNodeDistance implements Comparable<MapNodeDistance>  {

	private MapNode vertex; 
	private double distance;
	
	
	public MapNodeDistance(MapNode vertex, double distance) {		
		this.vertex = vertex;
		this.distance = distance;
	}
	
	public MapNode getVertex() {
		return vertex;
	}
	public void setVertex(MapNode vertex) {
		this.vertex = vertex;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	@Override
	public int compareTo(MapNodeDistance obj2) {
		return Double.compare(this.distance, obj2.distance);
	}

	@Override
	public String toString() {
		return "MapNodeDistance [vertex=" + vertex + ", distance=" + distance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((vertex == null) ? 0 : vertex.hashCode());
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
		MapNodeDistance other = (MapNodeDistance) obj;
		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
			return false;
		if (vertex == null) {
			if (other.vertex != null)
				return false;
		} else if (!vertex.equals(other.vertex))
			return false;
		return true;
	} 
	
}
