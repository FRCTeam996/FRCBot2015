package org.usfirst.frc.team996.robot;

/*
 * Seperating the drive class and claw class from the others left pwm constants all over the place.
 * This was my quick way of reconsolidating them so they are easier to acces when they need to be adjusted.
 * 
 */

public class PWM {
	
	//Chassis
	final static int LEFT_TALON_PWM_PIN = 0; //ID 1
	final static int RIGHT_TALON_PWM_PIN = 1; //ID 2
	
	//6-Bar
	final static int LEFT_6BAR_TALON_PWM_PIN = 2; //ID 3
	final static int RIGHT_6BAR_TALON_PWM_PIN = 3; //ID 4
	
	//Camera Servos for Axis Camera
	final static int CAMERA_Y_AXIS_PWM_PIN = 6; //ID 5
	final static int CAMERA_X_AXIS_PWM_PIN = 7; //ID 6
	
	//Sensors
	final static int LEFT_ENCODER_A = 4;
	final static int LEFT_ENCODER_B = 5;
	final static int RIGHT_ENCODER_A = 6;
	final static int RIGHT_ENCODER_B = 7;

}
