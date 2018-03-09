package man;

import robocode.*;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class Target{
	
	
	//Variables to store the information about our enemy locally 
	AdvancedRobot myRobot;
	Vector2 velocity;
	double distance;
	private double heading;
	private double bearing;
	Coordinates position;
	
	public Target(AdvancedRobot myRobot) {		
		super();
		this.myRobot = myRobot;
	}

	//Updates the enemy's information 
	
	void update(ScannedRobotEvent e) {
	
		bearing = e.getBearingRadians();
		distance = e.getDistance();
		heading = e.getHeadingRadians();
		double velocity = e.getVelocity();
		this.position = getPosition();
		
		//This stores the velocity divided between the two axes
		this.velocity = new Vector2(velocity*Math.sin(heading),velocity*Math.cos(heading));
		
	}
	
	//Calculates the enemy's current position based on 
	//its distance from us and the angle between us
	public Coordinates getPosition() {
		double radarHeading = Utils.normalRelativeAngle(myRobot.getHeadingRadians() + bearing);
	
		
		double x = (Math.sin(radarHeading)*distance) + myRobot.getX();
		double y = (Math.cos(radarHeading)*distance) + myRobot.getY();
		
		return new Coordinates(x,y);
		
	}
	
	//Calculates the enemy's future position based on
	//its current movement and position
	
	public Coordinates getFuturePosition(double time) {
		
		double x = position.x + (velocity.x * time);
		double y = position.y + (velocity.y * time);
		
		myRobot.out.println("time:" + time);
		myRobot.out.println("Future x:" + x);
		myRobot.out.println("Future y:" + y);
		
		return new Coordinates(x,y);
		
	}
	
}