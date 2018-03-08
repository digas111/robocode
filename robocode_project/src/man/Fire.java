package man;

import robocode.AdvancedRobot;

public class Fire extends AdvancedRobot{
	
	billbot myRobot;
	Target enemyRobot;
	
	double timeToEnemy;
	double halfInterval;
	

	public Fire(billbot myRobot, Target enemyRobot) {
		super();
		// TODO Auto-generated constructor stub
		
		this.myRobot = myRobot;
		this.enemyRobot = enemyRobot;
		this.timeToEnemy = enemyRobot.distance * myRobot.bullet_velocity;
		this.halfInterval = timeToEnemy/2;
	}
	
	double getFireInfo() {
		
		double timeToImpact = runTimeInterval();
		
		//
		
		
		
		
		
	}
	
	
	double testPoints() {
		
		Coordinates test;
		
		for (double i=timeToEnemy - halfInterval; i <= timeToEnemy + halfInterval;i++) {
			
			test = getPoint(i);
			
			if(test.x!=-1 && test.y!=-1) {
				return i;
			}
			
		}
		
		return -1;
		
	}
	
	
	
	Coordinates getPoint(double time) {
		
		//We want to intercept a circle (representing the bullet)
		//with a straight line (representing the enemy robot)
		//to represent the line we need two points
		//so we consider that the enemy maintains its direction and velocity
		//and get a future point
		
		Coordinates future = enemyRobot.getFuturePosition(timeToEnemy);
		
		//and the current position
		
		Coordinates now = enemyRobot.position;
		
		//I'm not going to explain why this formula works as it is a standard circle-line intersection formula
		//This are the variables we need:
		
		//The difference (in x) of the two points
		
		double differenceX = future.x - now.x;
		
		//The difference (in y) of the two points
		
		double differenceY = future.y - now.y;
		
		//The overall difference
		
		double differenceOverall = Math.sqrt(differenceX*differenceX + differenceY*differenceY);
		
		//Calculate the matrix determinant
		
		double D = now.x*future.y - future.x*now.y;
		
		//Calculate the radius based on the time given
		
		double radius = myRobot.bullet_velocity * time;
		
		//Now lets pre calculate parts of the equations to make it simpler
		
		double differenceOverallElevated2 = differenceOverall*differenceOverall;
		
		double DElevated2 = D*D;
		
		double radiusElevated2 = radius*radius;
		
		double inSqrtValue = radiusElevated2*differenceOverallElevated2 - DElevated2;
		
		//
		
		double x1, y1, x2, y2;
		
		if (inSqrtValue<0) {
			return new Coordinates(-1,-1);
		}
		
		else {
			
			double indicator;
			
			if(differenceY<0) {
				indicator = -1;
			}
			
			else {
				indicator = 1;
			}
			
			x1 = (D*differenceY + (indicator*differenceX*Math.sqrt(inSqrtValue)))/differenceOverallElevated2;
			x2 = (D*differenceY - (indicator*differenceX*Math.sqrt(inSqrtValue)))/differenceOverallElevated2;
			
			y1 = ((-1*D)*differenceX + (Math.abs(differenceY)*Math.sqrt(inSqrtValue)))/differenceOverallElevated2;
			y2 = ((-1*D)*differenceX - (Math.abs(differenceY)*Math.sqrt(inSqrtValue)))/differenceOverallElevated2;
		}
		
		//get the best point out of the two above
		
		if (inSqrtValue==0) {
			return new Coordinates(x1,y1);
		}
		
		else {
			//get the distance from the two points to my robot and choose the one with less distance
			
			Coordinates p1 = new Coordinates(x1,y1);
			Coordinates p2 = new Coordinates(x2,y2);
			Coordinates pMyRobot = new Coordinates(getX(),getY());
			
			double distance1 = new DistancePointToPoint(p1, pMyRobot).distance;
			double distance2 = new DistancePointToPoint(p2, pMyRobot).distance;
			
			if(distance1 > distance2) {
				return p1;	
			}
			return p2;
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	 
	
}