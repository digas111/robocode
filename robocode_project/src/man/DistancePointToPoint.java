package man;
class DistancePointToPoint {
	
	Coordinates p1;
	Coordinates p2;
	
	double distance;
	
	public DistancePointToPoint(Coordinates p1, Coordinates p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		
		this.distance = getDistance();
		
	}
	
	double getDistance() {
		
		double distanceX = p1.x - p2.x;
		double distanceY = p1.y - p2.y;
		
		double distance = Math.sqrt(distanceX*distanceX + distanceY*distanceY);
		
		return distance;
	}
	
	

	
	
	
	
	
	
	
	
}