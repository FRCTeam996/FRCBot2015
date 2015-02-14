package org.usfirst.frc.team996.robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * Robot Ver. 0.5d
 * 
 * This Version:
 * 
 * Added Basic Pushing Autonomous
 * 
 * To-Do:
 * 
 * Do Software and Sensor Documetation and Turn into John
 * Rough Out Sensors
 * 
 * Known Bugs:
 *
 * Slight pull when driving ( Will be fixed by encoders later on ) (Need encoders wired to do this...)
 * 
 */

public class Robot extends SampleRobot {

	//Autonomous
	int AUTO_MODE = 1; //Push a bin forwards X secs at Y speed
	
	//Autonomous 1
	final double AUTO_1_PUSH_TIME = 3; //X secs (Better to go under then over)
	final double AUTO_1_PUSH_SPEED = 0.25; //Y Speed
	
	//Tele-OP
	final double DEFAULT_DELAY = 0.005; //Delay for the main loop
	
	//Drive
	Drive drive;
	final int FLIPPED_DRIVE_X = 1;
	final int FLIPPED_DRIVE_Y = 1;
	final int FLIPPED_LIFT_X = 1;
	final int FLIPPED_LIFT_Y = 1;

	//Driver Station
	Joystick stick;
	Joystick cStick;
	final int JOYSTICK_CHASSIS = 0;
	final int JOYSTICK_6BAR = 1;

	//Sensors
	Ultrasonic ultraSonicCluster;
	Encoder leftMotorEncoder,rightMotorEncoder;
	final EncodingType ENCODING_TYPE = Encoder.EncodingType.k4X;
	final boolean REVERSE_ENCODERS = false;

	public Robot() {

		//Driver Station
		stick = new Joystick(JOYSTICK_CHASSIS);
		cStick = new Joystick(JOYSTICK_6BAR);

		//Sensors
		ultraSonicCluster = new Ultrasonic(Analog.ULTRASONIC_PING, Analog.ULTRASONIC_IN);
		leftMotorEncoder = new Encoder(PWM.LEFT_ENCODER_A, PWM.LEFT_ENCODER_B, REVERSE_ENCODERS, ENCODING_TYPE);
		rightMotorEncoder = new Encoder(PWM.RIGHT_ENCODER_A, PWM.RIGHT_ENCODER_B, REVERSE_ENCODERS, ENCODING_TYPE);

	}

	public void robotInit() {
		//camera = new Camera();
		drive = new Drive();
	}

	//Needs to be tested
	public void autonomous() {
		if(AUTO_MODE == 1){
			drive.driveStraight(AUTO_1_PUSH_SPEED, AUTO_1_PUSH_TIME);
		}
	}

	//Tele-OP mode
	public void operatorControl() {

		drive.initTeleOp();
		
		while(isOperatorControl() && isEnabled()) {
			
			//Loop Camera
			//camera.CameraLoop();
			
			//Drive Bot
			drive.teleOp( FLIPPED_DRIVE_X * stick.getX(), FLIPPED_DRIVE_Y  *stick.getY(), FLIPPED_LIFT_X * cStick.getX(), FLIPPED_LIFT_Y * cStick.getY());
			
			//Calculate Delay (WIP)
			double delay = DEFAULT_DELAY;
			
			Timer.delay(delay);
		}
		//camera.CameraStop();
	}

	//Competition test
	public void test() {
		
	}

}