package man;

import robocode.*;
import robocode.util.Utils;
import java.lang.Math.*;
import java.awt.geom.*;
import java.util.*;

//**
public class BillBot extends AdvancedRobot{
	
	/**
	 *  ROBOT SETTINGS
	 */
	
	double fire_power=1;
	
	/**
	 *
	 */
	
	/**
	 *  ROBOT INFO
	 */
	
	double bullet_velocity=20-3*fire_power;
	
	/**
	 *  
	 */
	Target dust;
	Fire kill;	

	
	boolean firstRun = true;
	
	/**
	 * run: Theboss's default behavior
	 */
	int i=1;
	int j=0;
	
	public void run() {
		if(firstRun) {
			dust = new Target(this);
			
			firstRun = false;
		}
		
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		
		turnRadarRightRadians(Double.POSITIVE_INFINITY);

		// Robot main loop
		while(true) {

			execute();
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
		
	    double radarTurn =
	            // Absolute bearing to target
	            absoluteBearing
	            // Subtract current radar heading to get turn required
	            - getRadarHeadingRadians();
	    
	    
	    
	    setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));
	    
	    dust.update(e);
	    if(kill==null) {
	    	kill = new Fire(this, dust);
	    }
	    kill.getFireInfo();
	    
	    fire_power = kill.firePower;
	    bullet_velocity = kill.bulletVelocity;
	    
	    
	    out.println(dust.position.toString());
	    
	    
	    
	    double gunTurn = kill.fireAngle - getGunHeadingRadians();
	    
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
	
	

	
	public double getTimeToEnemy() {
		return dust.distance / bullet_velocity;
	}
	
//	public double getFireTime() {
//		
//		double time = getTimeToEnemy() + 10;
//		Coordinates futurePosition = dust.getFuturePosition(time);
//		
//		double distanceX = futurePosition.x - getX();
//		double distanceY = futurePosition.y - getY();
//		
//		
//	}



}