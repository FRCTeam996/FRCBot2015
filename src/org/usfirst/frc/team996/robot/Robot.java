package org.usfirst.frc.team996.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/*
 * To-Do:
 * Convert Claw to Lift
 * Delete old Lift
 * 
 * Extras:
 * Ultra-Sonic Sensors
 * Re-add and improve object detection
 */


public class Robot extends SampleRobot {

	//Robot
	final double DEFAULT_DELAY = 0.01; //Delay for the main loop
	Drive drive;

	//Driver Station
	DriverStation DriverStationLCD;
	Joystick stick;
	
	//6-Bar
	Lift lift;

	//Sensors

	public Robot() {

		//Driver Station
		stick = new Joystick(0);

		//Sensors
		
		//Camera Server
		
		//Claw

	}

	public void robotInit() {

		drive = new Drive();
		lift = new Lift();
	}

	final double binSecs = 0.6;
	final double binSpeed = 0.15;
	final double zoneSecs = 2.5;
	final double zoneSpeed = 0.33;
	
	final double turnDelay = 1.0;
	final double turnRate = 1.0;
	final double turnDir = 1.0;
	
	final double driveSpeed = -0.2;
	final double driveDelay = 2.5;
	int autoNum = 2;

	//Test Autonomous
	public void autonomous() {

		boolean stopped = false;
		
		while(isAutonomous() && isEnabled() && !stopped){
			
			//Try to output the MatchTime to the dashboard
			try{
				SmartDashboard.putInt("Timer (secs):", (int)Timer.getMatchTime());
				SmartDashboard.putDouble("T-Value", Timer.getMatchTime());
				SmartDashboard.putBoolean("Stopped:", stopped);
			}catch(Exception e){
				
			}

		}
		
	}

	//Tele-OP mode
	public void operatorControl() {

		drive.initTeleOp();
		
		while(isOperatorControl() && isEnabled()) {
			
			//Loop Camera if not on server
			
			//Drive Robot
			drive.teleOp(stick.getX(), stick.getY());
			
			//Claw
			lift.teleOp(stick.getTrigger());
			
			Timer.delay(DEFAULT_DELAY);
		}
	}

	//Pre-competition test
	public void test() {
		
	}

}