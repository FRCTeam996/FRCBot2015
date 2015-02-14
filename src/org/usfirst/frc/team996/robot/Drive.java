package org.usfirst.frc.team996.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class Drive {	
	
	//Chassis
	RobotDrive chassis;
	Talon leftMotorControl, rightMotorControl;
	final boolean ELIMINATE_DEADBAND = false; //Eliminate deadband in talons
	final boolean SQUARED_INPUTS = true; // Square inputs for more accuracy
	final double DEFAULT_LEFT_OFFSET = 0.0;
	final double DEFAULT_RIGHT_OFFSET = 0.0;
	final double ROBOT_EXPIRATION_TIME = 0.1;
	double leftMotorOffset,rightMotorOffset;
	
	//6-Bar	
	
	public Drive(){
		//Chassis
		try{
			//Declare Speed Controllers
			leftMotorControl = new Talon(PWM.LEFT_TALON_PWM_PIN);
			rightMotorControl = new Talon(PWM.RIGHT_TALON_PWM_PIN);
		}catch(Exception e){
			System.out.println("[!] Error with talons\nTry Checking PWM Channels");
		}

		//If Enabled eliminates deadband in the middle of the range
		leftMotorControl.enableDeadbandElimination(ELIMINATE_DEADBAND);
		rightMotorControl.enableDeadbandElimination(ELIMINATE_DEADBAND);

		//Init chassis
		chassis = new RobotDrive(leftMotorControl, rightMotorControl);

		//Sets expiration time in the event that the robot disconnects from driver station.
		chassis.setExpiration(ROBOT_EXPIRATION_TIME);
	}
	
	public void initTeleOp(){
		//Set Safety On
		chassis.setSafetyEnabled(true);
	}
	
	public void teleOp(double x, double y){
		//Flag to determine whether or not user is able to drive
		boolean canDrive = true;

		//Get Sensors input

		//Change delay, canDrive, and talon offsets based on sensors' results

		//Drive
		if(canDrive){

			//Set Offsets
			leftMotorOffset = DEFAULT_LEFT_OFFSET;
			rightMotorOffset = DEFAULT_RIGHT_OFFSET;

			//Drive
			arcadeDrive(x, y, SQUARED_INPUTS);
		}
	}
	
	//Function based off of RobotDrive.arcadeDrive() customized to adjust based off of offset
		public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
			// local variables to hold the computed PWM values for the motors
			double leftMotorSpeed;
			double rightMotorSpeed;

			moveValue = limit(moveValue);
			rotateValue = limit(rotateValue);

			if (squaredInputs) {
				// square the inputs (while preserving the sign) to increase fine control while permitting full power
				if (moveValue >= 0.0) {
					moveValue = (moveValue * moveValue);
				} else {
					moveValue = -(moveValue * moveValue);
				}
				if (rotateValue >= 0.0) {
					rotateValue = (rotateValue * rotateValue);
				} else {
					rotateValue = -(rotateValue * rotateValue);
				}
			}

			if (moveValue > 0.0) {
				if (rotateValue > 0.0) {
					leftMotorSpeed = moveValue - rotateValue;
					rightMotorSpeed = Math.max(moveValue, rotateValue);
				} else {
					leftMotorSpeed = Math.max(moveValue, -rotateValue);
					rightMotorSpeed = moveValue + rotateValue;
				}
			} else {
				if (rotateValue > 0.0) {
					leftMotorSpeed = -Math.max(-moveValue, rotateValue);
					rightMotorSpeed = moveValue + rotateValue;
				} else {
					leftMotorSpeed = moveValue - rotateValue;
					rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
				}
			}

			//Drive with offset
			chassis.setLeftRightMotorOutputs(leftMotorSpeed - leftMotorOffset, rightMotorSpeed - rightMotorOffset);
		}
		
		//Grabbed the RobotDrive.limit(double num) from the RobotDrive class for use in this.arcadeDrive()
		protected static double limit(double num) {
			if (num > 1.0) {
				return 1.0;
			}
			if (num < -1.0) {
				return -1.0;
			}
			return num;
		}

}
