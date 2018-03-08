package man;

import robocode.*;
import robocode.ScannedRobotEvent;

public class Target extends AdvancedRobot{
	
	AdvancedRobot myRobot;
	private Vector2 velocity;
	double distance;
	private double heading;
	private double bearing;
	Coordinates position;
	
	/*private double velocity;
	private double xVelocity;
	private double yVelocity;//*/
	//private Coordinates position;
	//private Coordinates futurePosition;
	
	public Target(AdvancedRobot myRobot) {		
		super();
		this.myRobot = myRobot;
	}

	void update(ScannedRobotEvent e) {
	
		bearing = e.getBearingRadians();
		distance = e.getDistance();
		heading = e.getHeadingRadians();
		double velocity = e.getVelocity();
		this.position = getPosition();
		
		this.velocity = new Vector2(velocity*Math.sin(heading),velocity*Math.cos(heading));
		
//		out.println(this.velocity.toString());
//		out.println("heading" + heading);
		
	}
	
	//double radarHeading, double billbotX, double billbotY, double distance
	public Coordinates getPosition() {
		double radarHeading = Math.abs(myRobot.getHeadingRadians() + bearing);
	
		
		double x = (Math.sin(radarHeading)*distance) + myRobot.getX();
		double y = (Math.cos(radarHeading)*distance) + myRobot.getY();
		
		return new Coordinates(x,y);
		
	}
	
	public Coordinates getFuturePosition(double time) {
		
		double x = position.x + (velocity.x * time);
		double y = position.y + (velocity.y * time);
		
//		out.println("time:" + time);
//		out.println("Future x:" + x);
//		out.println("Future y:" + y);
		
		return new Coordinates(x,y);
		
	}
}