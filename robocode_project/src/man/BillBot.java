package man;

import robocode.*;
import robocode.util.Utils;
import java.lang.Math.*;
import java.awt.geom.*;
import java.util.*;

/*
 * Software Architecture 17/18
 * FCUP - Porto/Portugal
 * 
 * Made by
 *   Armando Martins - up201504230
 *         &
 *   Diogo Ribeiro - up201504115
 * 
 */


public class BillBot extends AdvancedRobot{
	
	boolean forward = false; // Is 1 if is moving forward
	boolean wallFlag = false; // Is 1 if the bot is close to a wall
	
	double fire_power=1; //Defines the fire power
	double bullet_velocity=20-3*fire_power; //Saves the bullet velocity
	
	//Here we define a Target witch is used to store our enemy's information
	Target dust = new Target(this);
	
	
	int i=1; //This is a police to know witch way to turn
	int j=0;
	
	public void run() {
		
		//This allows us to turn all body parts independently
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		
		//Turn the radar to find the enemy
		turnRadarRightRadians(Double.POSITIVE_INFINITY);
		
		//Detect if we spawn near a wall
		if (getX() < 70 || getY() < 70 || getBattleFieldWidth() - getX() < 70 || getBattleFieldHeight() - getY() < 70) 
			wallFlag = true;
		else 
			wallFlag = false;

		//To assure that we never stop
		setAhead(Double.POSITIVE_INFINITY);
		forward = true;  
		
		while(true) {

			//checks if we are near a wall
			wallChecker();

			scan();
            execute();
			scan();
			
			//If we lose the enemy bot this turns the radar to the its last heading 
			if (i==1) {
				turnRadarRightRadians(Double.POSITIVE_INFINITY);
			}
			
			else if (i==0) {
				turnRadarLeftRadians(Double.POSITIVE_INFINITY);
			}
			
		}
	}
	
 public void wallChecker(){

	 	//Checks if BillBot is 60px or less from a wall
	 	
		if (getX() > 60 && getY() > 60 && getBattleFieldWidth() - getX() > 60 && getBattleFieldHeight() - getY() > 60 && wallFlag) {
				wallFlag = false;
			}
		if (getX() <= 60 || getY() <= 60 || getBattleFieldWidth() - getX() <= 60 || getBattleFieldHeight() - getY() <= 60) {
					if(!wallFlag){
					reverse(); //Reverses the BillBot's speed
					wallFlag = true;
 				}
		}
    }

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		
		//Calculates the exact angle to enemy (independent from BillBot's heading)
		double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
		
	    double radarTurn =
	            // Absolute bearing to target
	            absoluteBearing
	            // Subtract current radar heading to get turn required
	            - getRadarHeadingRadians();

	    turnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));
	    
	    
	    //This assures that if we have 50 or more health we get closer to the enemy
	    //If we have lower health than 50 we move away
	    if(getEnergy() >= 50){
			if (forward){
				setTurnRight(Utils.normalRelativeAngleDegrees(e.getBearing() + 60));
			} 
			else {
				setTurnRight(Utils.normalRelativeAngleDegrees(e.getBearing() + 70));
			}
		}
		else{
			if (forward){
				setTurnRight(Utils.normalRelativeAngleDegrees(e.getBearing() + 100));
			} 
			else {
				setTurnRight(Utils.normalRelativeAngleDegrees(e.getBearing() + 120));
			}
		}
	
    	    //Updates the enemy's information on the Target class
	    dust.update(e);
	    
	    //Debug purposes
	    out.println(dust.position.toString());
	    
	    //Turns the gun to the enemy's calculated future position
	    double gunTurn = getFireAngle(dust.getFuturePosition(getTimeToEnemy())) - getGunHeadingRadians();
	    
	    turnGunRightRadians(Utils.normalRelativeAngle(gunTurn));
	        
	    //Fire!!
	        setFire(fire_power);
		
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {

		reverse();
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		
		//Debug purposes
		out.println("ai!");
	
			reverse();
		
	}
	
	public void onHitRobot(HitRobotEvent e) {

			reverse();
		
	}
	
	public double getFireAngle(Coordinates enemy) {
		
		//Given a position e returns the angle between BillBot and that position
		//(This ignores our heading)
		
		
		//If the enemy is above or beneath BillBot we know the angle by comparing only the y positions
		
		double xSide = enemy.x - getX();
		double ySide = enemy.y - getY();
		double xSideAbs = Math.abs(xSide);
		double ySideAbs = Math.abs(ySide);
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
	
	public double getTimeToEnemy() {
		return dust.distance / bullet_velocity;
	}
	
public void reverse() {
	//This is the function we use to reverse our movement
	//Basically if we are moving forward we start moving backwards and vice versa 
	
		if (forward) {
			setAhead(Double.NEGATIVE_INFINITY);
			forward = false;
		} else {
			setAhead(Double.POSITIVE_INFINITY);
			forward = true;
		}
	}


}