package org.usfirst.frc.team996.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

/**
 * Robot Ver. 0.3
 * 
 * This Version:
 * 
 * Camera Code Added
 * Dashboard for camera started
 * 
 * To-Do:
 * 
 * Camera Processing
 * 6-Bar Code
 * Claw Code
 * Encoders
 * Ultra-Sonic Sensors
 * Do Software and Sensor Documetation and Turn into John
 * Autonomous Mode 1 (Green Bin on Yellow Bin and Drive out)
 * Autonomous Mode 2 (Try to get two yellow totes)
 * Autonomous Mode 3? (Grab Other Teams' Green and Yellow) or (Try and get all 3 Yellow Bins)
 * 
 * Known Bugs:
 * 
 * Slight pull when driving ( Will be fixed by encoders later on )
 * 
 */

public class Robot extends SampleRobot {

	//Final Vars
	final double DEFAULT_DELAY = 0.005; //Delay for the main loop
	final double ROBOT_EXPIRATION_TIME = 0.1;

	//Axis Camera Settings
	final double CAMERA_Y_DEADZONE = 0.01;
	final double CAMERA_X_DEADZONE = 0.01;
	final double CAMERA_Y_SCALE = 1.0;
	final double CAMERA_X_SCALE = 1.0;

	//PWM Channels
	final int LEFT_TALON_PWM_PIN = 0;
	final int RIGHT_TALON_PWM_PIN = 1;

	final int LEFT_ENCODER_A = 2;
	final int LEFT_ENCODER_B = 3; //Need both A and B?
	final int RIGHT_ENCODER_A = 4;
	final int RIGHT_ENCODER_B = 5;

	final int CAMERA_Y_AXIS_PWM_PIN = 8;
	final int CAMERA_X_AXIS_PWM_PIN = 9;

	//Chassis
	RobotDrive chassis;
	Talon leftMotorControl, rightMotorControl;
	final boolean ELIMINATE_DEADBAND = false; //Eliminate deadband in talons
	final boolean SQUARED_INPUTS = true; // Square inputs for more accuracy
	final double DEFAULT_LEFT_OFFSET = 0.0;
	final double DEFAULT_RIGHT_OFFSET = 0.0;
	double leftMotorOffset,rightMotorOffset;

	//Driver Station
	DriverStation DriverStationLCD;
	Joystick stick;
	Joystick cStick;

	//Axis Camera
	Camera camera;
	Servo cameraYServo,cameraXServo;
	int session;
	Image frame;
	NIVision.RawData colorTable;
	CameraServer server;
	public static int cam = 0;

	//Sensors
	AnalogInput soundIn;
	Encoder leftMotorEncoder,rightMotorEncoder;
	final EncodingType ENCODING_TYPE = Encoder.EncodingType.k4X;
	final boolean REVERSE_ENCODERS = false;

	public Robot() {

		//Chassis
		try{
			//Declare Speed Controllers
			leftMotorControl = new Talon(LEFT_TALON_PWM_PIN);
			rightMotorControl = new Talon(RIGHT_TALON_PWM_PIN);
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

		//Driver Station
		stick = new Joystick(0);
		cStick = new Joystick(1);

		try{
			cameraYServo = new Servo(CAMERA_Y_AXIS_PWM_PIN);
			cameraXServo = new Servo(CAMERA_X_AXIS_PWM_PIN);
		}catch(Exception e){
			System.out.println("[!] Error with Axis Camera servos\nTry checking pwm channels.");
		}

		//Sensors
		soundIn = new AnalogInput(2);
		leftMotorEncoder = new Encoder(LEFT_ENCODER_A, LEFT_ENCODER_B, REVERSE_ENCODERS, ENCODING_TYPE);
		rightMotorEncoder = new Encoder(RIGHT_ENCODER_A, RIGHT_ENCODER_B, REVERSE_ENCODERS, ENCODING_TYPE);

		//Camera Server
		/*
		server = CameraServer.getInstance();
		server.setQuality(50);
		server.startAutomaticCapture("cam1");*/


	}

	public void robotInit() {
		camera = new Camera();
	}


	//Test Autonomous
	public void autonomous() {

	}

	//Tele-OP mode
	public void operatorControl() {

		//Set Safety On
		chassis.setSafetyEnabled(true);

		while(isOperatorControl() && isEnabled()) {
			camera.CameraLoop();

			double delay = DEFAULT_DELAY;

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
				arcadeDrive(stick.getX(), stick.getY(), SQUARED_INPUTS);
			}

			//Move Axis Camera
			if(Math.abs(cStick.getY()) > CAMERA_Y_DEADZONE){
				cameraYServo.setAngle(cameraYServo.getAngle() + cStick.getY() * CAMERA_Y_SCALE);	
			}
			if(Math.abs(cStick.getX()) > CAMERA_X_DEADZONE){
				cameraXServo.setAngle(cameraXServo.getAngle() + cStick.getX() * CAMERA_X_SCALE);
			}
			
			Timer.delay(delay);				// wait for a motor update time
		}
		
		camera.CameraStop();
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

	//Pre-competition test
	public void test() {

	}

	

}