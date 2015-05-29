package org.usfirst.frc.team996.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class Drive {

	//Robot
	final double ROBOT_EXPIRATION_TIME = 1.0;
	final boolean TELEOP_SAFETY_ENABLED = true; 

	//Chassis
	RobotDrive chassis;
	Talon leftChassisControl, rightChassisControl;
	final boolean ELIMINATE_CHASSIS_DEADBAND = false; //Eliminate deadband in talons
	final boolean CHASSIS_SQUARED_INPUTS = true; // Square inputs for more accuracy
	final double CHASSIS_DEFAULT_LEFT_OFFSET = 0.0;
	final double CHASSIS_DEFAULT_RIGHT_OFFSET = 0.0;
	double CHASSIS_SCALE = 0.4; //Multiplier for speed just in case it needs to be adjusted
	double leftMotorOffset,rightMotorOffset;
	final double FLIP_CHASSIS_X = -1.0; //If negative will flip the sides
	final double FLIP_CHASSIS_Y = 1.0; //If negative will flip the sides
	
	//Speed
	boolean triggerSlow = false;
	

	//6-Bar	
	

	public Drive(){

		//Chassis
		try{
			//Declare Speed Controllers
			leftChassisControl = new Talon(RobotMap.LEFT_TALON_PWM_PIN);
			rightChassisControl = new Talon(RobotMap.RIGHT_TALON_PWM_PIN);
		}catch(Exception e){
			
		}

		//If Enabled eliminates deadband in the middle of the range
		leftChassisControl.enableDeadbandElimination(ELIMINATE_CHASSIS_DEADBAND);
		rightChassisControl.enableDeadbandElimination(ELIMINATE_CHASSIS_DEADBAND);

		//Init chassis
		chassis = new RobotDrive(leftChassisControl, rightChassisControl);

		//6-Bar


		//Sets expiration time in the event that the robot disconnects from driver station.
		chassis.setExpiration(ROBOT_EXPIRATION_TIME);
	}

	public void initTeleOp(){
		//Set Safety On
		chassis.setSafetyEnabled(TELEOP_SAFETY_ENABLED);
	}

	public void teleOp(double driveX, double driveY){

		//Flag to determine whether or not user is able to drive and lift
		boolean canDrive = true;
		boolean canLift = true;

		//Get Sensors input

		//Change delay, canDrive, and talon offsets based on sensors' results

		//Drive
		if(canDrive){

			//Drive
			arcadeDriveChassis(driveY * FLIP_CHASSIS_Y, driveX * FLIP_CHASSIS_X, CHASSIS_SQUARED_INPUTS);
		}
		if(canLift){

			//Lift
			
		}
		
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

		//Drive with offset
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
