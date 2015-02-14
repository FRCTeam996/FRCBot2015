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
	
	//Drive vars
	Drive drive;
	final int FLIPPED_DRIVE_X = 1;
	final int FLIPPED_DRIVE_Y = -1;
	final int FLIPPED_LIFT_X = 1;
	final int FLIPPED_LIFT_Y = 1;

	//Driver Station
	DriverStation DriverStationLCD;
	Joystick stick;
	Joystick cStick;

	//Camera camera;
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

		//Sensors
		soundIn = new AnalogInput(2);
		leftMotorEncoder = new Encoder(PWM.LEFT_ENCODER_A, PWM.LEFT_ENCODER_B, REVERSE_ENCODERS, ENCODING_TYPE);
		rightMotorEncoder = new Encoder(PWM.RIGHT_ENCODER_A, PWM.RIGHT_ENCODER_B, REVERSE_ENCODERS, ENCODING_TYPE);

	}

	public void robotInit() {
		//camera = new Camera();
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
			//camera.CameraLoop();
			
			//Drive Bot
			drive.teleOp( FLIPPED_DRIVE_Y * stick.getY(), FLIPPED_DRIVE_X  *stick.getX(), FLIPPED_LIFT_X * cStick.getX(), FLIPPED_LIFT_Y * cStick.getY());
			
			double delay = DEFAULT_DELAY;
			
			Timer.delay(0.005);
		}
		//camera.CameraStop();
	}

	//Competition test
	public void test() {

	}

	

}