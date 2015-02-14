package org.usfirst.frc.team996.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class Drive {	
	
	//Drive
	final double ROBOT_EXPIRATION_TIME = 0.1;
	final boolean TELEOP_SAFETY_ENABLED = true;
	
	//Chassis
	RobotDrive chassis;
	Talon leftChassisControl, rightChassisControl;
	final boolean ELIMINATE_CHASSIS_DEADBAND = false; //Eliminate deadband in talons
	final boolean CHASSIS_SQUARED_INPUTS = true; // Square inputs for more accuracy
	final double CHASSIS_DEFAULT_LEFT_OFFSET = 0.0; 
	final double CHASSIS_DEFAULT_RIGHT_OFFSET = 0.0;
	final double CHASSIS_SCALE = 1.0; //Multiplier for speed just in case it needs to be adjusted
	
	//6-Bar
	RobotDrive lift;
	Talon leftLiftControl, rightLiftControl;
	final boolean ELIMINATE_LIFT_DEADBAND = false; //Eliminate deadband in talons
	final boolean LIFT_SQUARED_INPUTS = true; // Square inputs for more accuracy
	final double LIFT_DEFAULT_LEFT_OFFSET = 0.0;
	final double LIFT_DEFAULT_RIGHT_OFFSET = 0.0;
	final double LIFT_SCALE = 1.0; //Multiplier for speed, this will probably need to be adjusted
	
	
	public Drive(){
		
		//Chassis
		try{
			//Declare Speed Controllers
			leftChassisControl = new Talon(PWM.LEFT_TALON_PWM_PIN);
			rightChassisControl = new Talon(PWM.RIGHT_TALON_PWM_PIN);
		}catch(Exception e){
			System.out.println("[!] Error with talons for chassis\nTry Checking PWM Channels");
		}
		
		//If Enabled eliminates deadband in the middle of the range
		leftChassisControl.enableDeadbandElimination(ELIMINATE_CHASSIS_DEADBAND);
		rightChassisControl.enableDeadbandElimination(ELIMINATE_CHASSIS_DEADBAND);
		
		//Init chassis
		chassis = new RobotDrive(leftChassisControl, rightChassisControl);

		//6-Bar
		try{
			//Declare Speed Controllers
			leftLiftControl = new Talon(PWM.LEFT_6BAR_TALON_PWM_PIN);
			rightLiftControl = new Talon(PWM.RIGHT_6BAR_TALON_PWM_PIN);
		}catch(Exception e){
			System.out.println("[!] Error with talons for 6-Bar\nTry Checking PWM Channels");
		}

		//If Enabled eliminates deadband in the middle of the range
		leftLiftControl.enableDeadbandElimination(ELIMINATE_LIFT_DEADBAND);
		rightLiftControl.enableDeadbandElimination(ELIMINATE_LIFT_DEADBAND);

		//Init lift
		lift = new RobotDrive(leftLiftControl, rightLiftControl);

		//Sets expiration time in the event that the robot disconnects from driver station.
		chassis.setExpiration(ROBOT_EXPIRATION_TIME);
		lift.setExpiration(ROBOT_EXPIRATION_TIME);
	}
	
	public void initTeleOp(){
		//Set Safety On
		chassis.setSafetyEnabled(TELEOP_SAFETY_ENABLED);
		lift.setSafetyEnabled(TELEOP_SAFETY_ENABLED);
	}
	
	public void teleOp(double chassisX, double chassisY, double liftX, double liftY){

		//Flag to determine whether or not user is able to drive
		boolean canDriveChassis = true;
		boolean canDriveLift = true;
		
		
		//Get Sensors input

		//Change delay, canDrive, and talon offsets based on sensors' results

		//Drive
		if(canDriveChassis){
			//Drive
			arcadeDriveChassis(chassisX, chassisY, CHASSIS_SQUARED_INPUTS);
		}
		if(canDriveLift){
			arcadeDriveLift(liftX, liftY, LIFT_SQUARED_INPUTS);
		}
	}

	//Function based off of RobotDrive.arcadeDrive() customized to adjust based off of offset
	public void arcadeDriveLift(double moveValue, double rotateValue, boolean squaredInputs) {
		// local variables to hold the computed PWM values for the motors
		double leftMotorSpeed;
		double rightMotorSpeed;
		double leftMotorOffset;
		double rightMotorOffset;

		//Set Offsets
		leftMotorOffset = LIFT_DEFAULT_LEFT_OFFSET;
		rightMotorOffset = LIFT_DEFAULT_RIGHT_OFFSET;

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

		//Drive based off of inputs factoring in offset and scale
		double leftSpeed = (leftMotorSpeed - leftMotorOffset) * LIFT_SCALE;
		double rightSpeed = (rightMotorSpeed - rightMotorOffset) * LIFT_SCALE;
		lift.setLeftRightMotorOutputs(leftSpeed, rightSpeed);
	}

	//Function based off of RobotDrive.arcadeDrive() customized to adjust based off of offset
	public void arcadeDriveChassis(double moveValue, double rotateValue, boolean squaredInputs) {
		// local variables to hold the computed PWM values for the motors
		double leftMotorSpeed;
		double rightMotorSpeed;
		double leftMotorOffset;
		double rightMotorOffset;

		//Set Offsets
		leftMotorOffset = CHASSIS_DEFAULT_LEFT_OFFSET;
		rightMotorOffset = CHASSIS_DEFAULT_RIGHT_OFFSET;

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

		//Drive based off of inputs factoring in offset and scale
		double leftSpeed = (leftMotorSpeed - leftMotorOffset) * CHASSIS_SCALE;
		double rightSpeed = (rightMotorSpeed - rightMotorOffset) * CHASSIS_SCALE;
		chassis.setLeftRightMotorOutputs(leftSpeed, rightSpeed);
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
