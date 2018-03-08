package man;
class Coordinates {
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "x="+x+" y="+y;
	}

	double x;
	double y;
	
	Coordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}
}