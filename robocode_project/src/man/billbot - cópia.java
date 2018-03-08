package man;

import robocode.*;
import robocode.util.Utils;
import java.lang.Math.*;
import java.awt.geom.*;
import java.util.*;

//**






class Coordinates {
	double x;
	double y;
	
	Coordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}
}


public class billbot extends AdvancedRobot{
		
	target dust = new target();
	
	/**
	 *  ROBOT SETTINGS
	 */
	
	double fire_power=1;
	double bullet_velocity=20-3*fire_power;
	
	/**
	 * run: Theboss's default behavior
	 */
	int i=1;
	int j=0;
	
	public void run() {
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		
		// Initialization of the robot should be put here
		
		
		turnRadarRightRadians(Double.POSITIVE_INFINITY);
//		turnGunLeftRadians(Double.POSITIVE_INFINITY);
//		rotate();
		// Robot main loop
		while(true) {
			// Replace the next 4 lines with any behavior you would like
			
//			setAhead(100);
//			//turnGunRight(360);
//			//turnGunRight(360);
//			setTurnLeft(45);
			execute();
//			//turnGunRight(360);
//			turnRadarRight(100);
			scan();
			if (i==1) {
				turnRadarRightRadians(Double.POSITIVE_INFINITY);
			
			}
			
			else if (i==0) {
				turnRadarLeftRadians(Double.POSITIVE_INFINITY);
			}
			
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		
		double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
		

		
//		fire(10);
	    double radarTurn =
	            // Absolute bearing to target
	            absoluteBearing
	            // Subtract current radar heading to get turn required
	            - getRadarHeadingRadians();
//	    double gunTurn = absoluteBearing - getGunHeadingRadians();
	    
	    turnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));
	    
	    dust.update(e);
	    
//	    for (int i=0; i<waves.size(); i++) {
//	    		WaveBullet currentWave = (WaveBullet)waves.get(i);
//	    		if (currentWave.checkHit(dust.x, dust.y, getTime())) {
//	    			waves.remove(currentWave);
//	    			i--;
//	    		}
//	    }
//	    
//	    if (dust.velocity != 0) {
//	    		if (Math.sin(dust.heading - absoluteBearing)*dust.velocity<0) {
//	    			direction = -1;
//	    		}
//	    		else {
//	    			direction =1;
//	    		}
//	    }
//	    
//	    int[] currentStats = stats;
//	    
//	    WaveBullet newWave = new WaveBullet(getX(), getY(), absoluteBearing, fire_power, direction, getTime(), currentStats);
//	    
//	    int bestindex = 15;
//	    for (int i=0;i<31;i++) {
//	    		if (currentStats[bestindex] < currentStats[i]) {
//	    			bestindex = i;
//	    		}
//	    }
//	    
//	    double guessfactor = (double)(bestindex - (stats.length - 1) /2) / ((stats.length -1 )/2);
//	    double angleOffset = direction * guessfactor * newWave.maxEscapeAngle();
//	    double gunAdjust = Utils.normalRelativeAngle(absoluteBearing - getGunHeadingRadians() + angleOffset);
//	    setTurnGunRightRadians(gunAdjust);
//	    
//	    if (getGunHeat() == 0 && gunAdjust < Math.atan2(9, e.getDistance()) && setFireBullet(fire_power) != null) {
//	    		waves.add(newWave);
//	    }
//	    
	    
	    
	    
	    
	    
	    Coordinates toKill = dust.getHitPoint(dust.getImpactTime());
	    
	    double gunTurn = (getFireAngle(getX(), getY(), toKill.x, toKill.y)) - getGunHeadingRadians();
		out.println("---------------------------");
	    
	     
//	        turnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));
	        turnGunRightRadians(Utils.normalRelativeAngle(gunTurn));
	        
	        setFire(fire_power);
		
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
//	    if(e.getBearing() < -90  || e.getBearing() > 90 )
//		ahead(300);
//	    else
//		back(300);
		
	}
	
	public void rotate() {
		while (true) {
			turnRadarRightRadians(Math.PI/4);
			turnRightRadians(Math.PI/6);
		}
		
	}
	
	public double getFireAngle(double x_mybot, double y_mybot, double x_enemy, double y_enemy) {
		
		//If the enemy is above or beneath me we know the angle by comparing only the y positions
		
		
		double xSide = x_enemy-x_mybot;
		double ySide = y_enemy-y_mybot;
		double xSideAbs = Math.abs(x_mybot-x_enemy);
		double ySideAbs = Math.abs(y_mybot-y_enemy);
		double distRobotToRobot = Math.sqrt(xSideAbs*xSideAbs + ySideAbs*ySideAbs);
		
		double angle = Math.asin(xSide/distRobotToRobot);
		
		if (xSide>0 && ySide>0) { //if enemy is in the lower left compared to billbot
			return angle;
		}
		else if (xSide < 0 && ySide > 0) {
			return angle + 2*Math.PI;
		}
		else if (xSide > 0 && ySide < 0) {
			return Math.PI - angle;
		}
		
		return Math.PI - angle;
		
	}
	

	
	/**
	 * 
	 * 
	 * Information about the enemy robot
	 * 
	 *
	 */

	class target{

		private double bearing;
		private double distance;
		private double heading;
		private double velocity;
		private double xVelocity;
		private double yVelocity;
		private double x;
		private double y;
		private double futureX;
		private double futureY;
		
		
		public double getBearing() {
			return bearing;
		}
		
		public double getDistance() {
			return distance;
		}
		
		public double getHeading() {
			return heading;
		}
		
		public double getVelocity() {
			return velocity;
		}
		
		public double getEnemyX() {
			return x;
		}
		
		public double getEnemyY() {
			return y;
		}
		
		public double getFutureX() {
			return futureX;
		}
		
		public double getFutureY() {
			return futureY;
		}
		
		void update(ScannedRobotEvent e) {
		
			bearing = e.getBearingRadians();
			distance = e.getDistance();
			heading = e.getHeadingRadians();
			velocity = e.getVelocity();
			x = calculateX();
			y = calculateY();
			
			xVelocity = velocity*Math.sin(heading);
			yVelocity = velocity*Math.cos(heading);
			
			out.println("xVelocity:" + xVelocity);
			out.println("yVelocity:" + yVelocity);
			out.println("heading" + heading);
			
		}
		
		public double calculateX() {
			
			double radarHeading = Utils.normalAbsoluteAngle(getHeadingRadians() + bearing);
			out.println("radarHeading" + radarHeading);
			
				double xEnemy =  (Math.sin(radarHeading)*distance) + getX();

//				out.println("my_x:\t" + x_mybot);
//				out.println("x:\t" + xEnemy);
//				out.println("dist:" + dist);
//				out.println("---------------------------");
				return xEnemy;
			
		}
		
		public double calculateY() {
			
			
			double radarHeading = Utils.normalAbsoluteAngle(getHeadingRadians() + bearing);
			out.println("radarHeading" + radarHeading);
			
				double yEnemy =  (Math.cos(radarHeading)*distance) + getY();

//				out.println("my_y:\t" + y_mybot);
//				out.println("y:\t" + yEnemy);
//				out.println("dist:" + dist);
//				out.println("---------------------------");
				return yEnemy;
			
		}
		
		protected Coordinates getHitPoint(double time) {
			
			double x = dust.x + (dust.xVelocity * time);
			double y = dust.y + (dust.yVelocity * time);
			
			out.println("time:" + time);
			out.println("Future x:" + x);
			out.println("Future y:" + y);
			
			return new Coordinates(x,y);
			
		}
		
		
		private double getImpactTime() {
			//double time = (getX() + getY() - dust.x - dust.y)/(dust.xVelocity + dust.yVelocity - bullet_velocity);
			
			double b = (-dust.xVelocity) - dust.yVelocity;
			double a = Math.abs(bullet_velocity*bullet_velocity);
			double c = -dust.x+getX()-dust.y+getY();
			double d = b*b - 4*a*c;
			
//			double time1 = ((-dust.xVelocity) - dust.yVelocity + 
//					Math.sqrt((Math.abs(dust.xVelocity + dust.yVelocity)*(dust.xVelocity + dust.yVelocity))-4*Math.abs(bullet_velocity*bullet_velocity)*(-dust.x+getX()-dust.y+getY()))/2*Math.abs(bullet_velocity*bullet_velocity));
//			
//			double time2 = ((-dust.xVelocity) - dust.yVelocity - 
//					Math.sqrt((Math.abs(dust.xVelocity + dust.yVelocity)*
//							(dust.xVelocity + dust.yVelocity))
//							-4*Math.abs(bullet_velocity*bullet_velocity)*
//							(-dust.x+getX()-dust.y+getY()))/2*Math.abs(bullet_velocity*bullet_velocity));
			
			out.println("b:" + b);
			out.println("a:" + a);
			out.println("c:" + c);
			out.println("d:" + d);
			
			double time1 = (-b+Math.sqrt(d)/2*a);
			double time2 = (-b-Math.sqrt(d)/2*a);
			
			
			
			
			if (time1 >= 0 && time2>=0) {
				return Math.min(time1, time2);
			}
			
			else if (time2<0) {
				return time1;
			}
			
			
			return time2;
		}
		
		
		
		
//		private double f(double time) {
//			
//			Coordinates targetPosition = getHitPoint(time);
//			double dx = (targetPosition.x - getX());
//			double dy = (targetPosition.y - getY());
//			
//			return Math.sqrt(dx*dx + dy*dy) - bullet_velocity * time;
//		}
//		
//		private double getHitTime(double t0, double t1, double accuracy) {
//			double x = t1;
//			double lastX = t0;
//			double lastfx = f(x);
//			
//			for (int i=0;i<20 && Math.abs(x-lastX) >= accuracy;i++) {
//				double fX = f(x);
//				
//				if ((fX-lastfx) == 0.0) break;
//				
//				double nextX = - fX*(x-lastX)/(fX - lastfx);
//				lastX = x;
//				x = nextX;
//				lastfx = fX;
//			}
//			return x;
//		}
		
	}
	
//	public class WaveBullet {
//		private double startX, startY, startBearing, power;
//		private long fireTime;
//		private int direction;
//		private int[] returnSegment;
//		
//		public WaveBullet(double x, double y, double bearing, double power, int derection, long time, int[] segment) {
//			startX = x;
//			startY = y;
//			startBearing = bearing;
//			this.power = power;
//			this.direction = direction;
//			fireTime = time;
//			returnSegment = segment;
//		}
//		public double maxEscapeAngle() {
//			return Math.asin(8/bullet_velocity);
//		}
//		
//		public boolean checkHit(double enemyX, double enemyY, long currentTime) {
//			if (Point2D.distance(startX, startY, enemyX, enemyY) <= (currentTime - fireTime) * bullet_velocity) {
//				double desiredDirection = Math.atan2(enemyX - startX,  enemyY - startY);
//				double angleOffset = Utils.normalRelativeAngle(desiredDirection - startBearing);
//				double guessFactor = Math.max(-1,  Math.min(1, angleOffset/maxEscapeAngle()))*direction;
//				int index = (int) Math.round((returnSegment.length - 1) / 2*(guessFactor+1));
//				returnSegment[index]++;
//				return true;
//			}
//			return false;
//		}
//		
//	}
	
}



