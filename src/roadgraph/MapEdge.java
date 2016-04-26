package roadgraph;

import geography.GeographicPoint;

public class MapEdge {
	
	private GeographicPoint start; 
	private GeographicPoint end;
	private String streetName;
	private String streetType;
	private double distance;
	
		
	public MapEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length) {
		this.start = from;
		this.end = to;
		this.streetName = roadName;
		this.streetType = roadType;
		this.distance = length;
	}

	public GeographicPoint getStart() {
		return start;
	}
	public void setStart(GeographicPoint start) {
		this.start = start;
	}
	public GeographicPoint getEnd() {
		return end;
	}
	public void setEnd(GeographicPoint end) {
		this.end = end;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getStreetType() {
		return streetType;
	}

	public void setStreetType(String streetType) {
		this.streetType = streetType;
	}

	@Override
	public String toString() {
		return "MapEdge [start=" + start + ", end=" + end + ", streetName=" + streetName + ", streetType=" + streetType
				+ ", distance=" + distance + "]";
	}	
					
}
