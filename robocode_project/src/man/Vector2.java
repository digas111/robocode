package man;

class Vector2{
	public double x;
	public double y;
	
	public Vector2(double x, double y) 
	{
		super();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "x="+x+" y="+y;
	}
}