package man;

//This class is used so store vectors with two axes
//ex: velocity

class Vector2 {
	public double x;
	public double y;
	
	public Vector2(double x, double y) 
	{
		super();
		this.x = x;
		this.y = y;
	}
	
	
	//If we need to print it
	@Override
	public String toString() {
		return "x="+x+" y="+y;
	}
}