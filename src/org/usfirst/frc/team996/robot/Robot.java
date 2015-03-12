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
 * Started Seperating Code into different classes
 * 
 *  
 * To-Do:
 * 
 * Drive Class
 * Claw Class
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
	

	//Axis Camera Settings
	final double CAMERA_Y_DEADZONE = 0.01;
	final double CAMERA_X_DEADZONE = 0.01;
	final double CAMERA_Y_SCALE = 1.0;
	final double CAMERA_X_SCALE = 1.0;

	//PWM Channels
	final int LEFT_ENCODER_A = 2;
	final int LEFT_ENCODER_B = 3; //Need both A and B?
	final int RIGHT_ENCODER_A = 4;
	final int RIGHT_ENCODER_B = 5;

	final int CAMERA_Y_AXIS_PWM_PIN = 8;
	final int CAMERA_X_AXIS_PWM_PIN = 9;


	Drive drive;

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
		drive = new Drive();
	}


	//Test Autonomous
	public void autonomous() {

	}

	//Tele-OP mode
	public void operatorControl() {

		drive.initTeleOp();
		
		while(isOperatorControl() && isEnabled()) {
			
			//Loop Camera
			camera.CameraLoop();
			
			//Drive Bot
			drive.teleOp(stick.getX(), stick.getY());
			
			double delay = DEFAULT_DELAY;
			
			//Move Axis Camera
			if(Math.abs(cStick.getY()) > CAMERA_Y_DEADZONE){
				cameraYServo.setAngle(cameraYServo.getAngle() + cStick.getY() * CAMERA_Y_SCALE);	
			}
			if(Math.abs(cStick.getX()) > CAMERA_X_DEADZONE){
				cameraXServo.setAngle(cameraXServo.getAngle() + cStick.getX() * CAMERA_X_SCALE);
			}
			
			Timer.delay(delay);
		}
		camera.CameraStop();
	}

	//Pre-competition test
	public void test() {

	}

	

}