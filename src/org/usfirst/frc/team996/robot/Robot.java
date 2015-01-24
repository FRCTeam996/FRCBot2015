package org.usfirst.frc.team996.robot;


import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.vision.AxisCamera;

/**
 * Robot Ver. 0.1a
 * 
 * This Version:
 * Robot Chassis
 * Begining of Camera Vision
 * Axis Camera Control with servos
 * 
 * Known Bugs:
 * Camera Jerks when tele-OP started
 * 
 */

public class Robot extends SampleRobot {
	
	//Final Vars
	final double DEFAULT_DELAY = 0.005; //Delay for the main loop
	final double ROBOT_EXPIRATION_TIME = 0.1;
	
	//Axis Camera Settings
	final double CAMERA_Y_DEADZONE = 0.01;
	final double CAMERA_X_DEADZONE = 0.01;
	final String AXIS_CAM_IP = "10.9.96.11";
	
	//PWM Channels
	final int LEFT_TALON_PWM_PIN = 0;
	final int RIGHT_TALON_PWM_PIN = 1;
	final int CAMERA_Y_AXIS_PWM_PIN = 8;
	final int CAMERA_X_AXIS_PWM_PIN = 9;
	
	//Chassis
    RobotDrive myRobot;
    Talon leftMotorControl, rightMotorControl;
    
    //Driver Station
    DriverStation DriverStationLCD;
    Joystick stick;
    Joystick cStick;
    
    //Axis Camera
    AxisCamera axisCam;
    Servo cameraYServo;
    Servo cameraXServo;
    Image image;
    int session;
    
    //Sensors
    AnalogInput soundIn;
    

    public Robot() {
    	
    	//Chassis
    	try{
    		leftMotorControl = new Talon(LEFT_TALON_PWM_PIN);
    		rightMotorControl = new Talon(RIGHT_TALON_PWM_PIN);
    	}catch(Exception e){
    		System.out.println("Error with chassis talons");
    	}
        myRobot = new RobotDrive(leftMotorControl, rightMotorControl);
        myRobot.setExpiration(ROBOT_EXPIRATION_TIME);
        
        //Driver Station
        stick = new Joystick(0);
        cStick = new Joystick(1);
        
        //Axis Camera
        axisCam = new AxisCamera(AXIS_CAM_IP);
        axisCam.writeResolution(AxisCamera.Resolution.k320x240);
        axisCam.writeBrightness(0);
        try{
        	cameraYServo = new Servo(CAMERA_Y_AXIS_PWM_PIN);
        	cameraXServo = new Servo(CAMERA_X_AXIS_PWM_PIN);
        }catch(Exception e){
        	System.out.println("[!] Error with Axis Camera servos\nTry checking pwm channels.");
        }
        
        //Sensors
        soundIn = new AnalogInput(2);
        
        //Computer Vision
        /*
        image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        session = NIVision.IMAQdxOpenCamera("cam0",NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session)
        */
    }

    public void autonomous() {
        //TBD
    }
    
    public void operatorControl() {
    	
    	//Set Safety On
        myRobot.setSafetyEnabled(true);
        
        //Driving Loop
        while (isOperatorControl() && isEnabled()) {
        	
        	//Set "delay" to the default value
        	//This delay can be changed later based off of conditions
        	double delay = DEFAULT_DELAY;
        	
        	//Flag to determine whether or not user is able to drive
        	boolean driveFlag = true;
        	
        	//Get Sensors input
        	
        	//User drive robot
        	if(driveFlag){
        		myRobot.arcadeDrive(stick);
        	}
        	
        	//Move Axis Camera
        	if(Math.abs(cStick.getY()) > CAMERA_Y_DEADZONE){
        		cameraYServo.setAngle(cameraYServo.getAngle() + cStick.getY());	
        	}
        	if(Math.abs(cStick.getX()) > CAMERA_X_DEADZONE){
        		cameraXServo.setAngle(cameraXServo.getAngle() + cStick.getX());
        	}
        	
        	//Wait for x secs
        	Timer.delay(delay);
        	
        }
    }

    //Test function tbd
    public void test() {
    	
    }
    
}