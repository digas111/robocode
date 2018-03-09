package man;

//This class is used to store points in a 2 dimensional space
class Coordinates {
	
	double x;
	double y;
	
	Coordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	//If we need to print it
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "x="+x+" y="+y;
	}
}