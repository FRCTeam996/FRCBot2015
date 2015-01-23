package org.usfirst.frc.team996.robot;


import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
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
 * 
 */

public class Robot extends SampleRobot {
	
	//Final Vars
	final double DEFAULT_DELAY = 0.05;
	
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
    		leftMotorControl = new Talon(0);
    		rightMotorControl = new Talon(1);
    	}catch(Exception e){
    		System.out.println("Error with chassis talons");
    	}
        myRobot = new RobotDrive(leftMotorControl, rightMotorControl);
        //Need to set expiration here?
        
        //Driver Station
        stick = new Joystick(0);
        cStick = new Joystick(1);
        
        //Axis Camera
        axisCam = new AxisCamera("10.9.96.11");
        axisCam.writeResolution(AxisCamera.Resolution.k320x240);
        axisCam.writeBrightness(0);
        try{
        	cameraYServo = new Servo(8);
        	cameraXServo = new Servo(9);
        }catch(Exception e){
        	System.out.println("[!] Error with Axis Camera servos\nTry checking pwm channels.");
        }
        
        //Sensors
        soundIn = new AnalogInput(2);
        
        //Computer Vision
        image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        session = NIVision.IMAQdxOpenCamera("cam0",NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
    }

    public void autonomous() {
        //TBD
    }
    
    public void operatorControl() {
    	
    	//Set Safety On
        myRobot.setSafetyEnabled(true);
        
        //Driving Loop
        int old = 0;
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
        	
        	//Wait for x secs
        	Timer.delay(delay);
        	
        }
    }

    //Test function tbd
    public void test() {
    	
    }
    
}