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
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
    RobotDrive myRobot;
    DriverStation DriverStationLCD;
    AnalogInput soundIn;
    RobotDrive cDrive;
    Joystick stick;
    Joystick cStick;
    Servo xAxis;
    Servo yAxis;
    AxisCamera axisCam;
    DigitalInput di;
    Image image;
    final double ySens = 2;
    final double xSens = 2;
    int session;

    public Robot() {
        myRobot = new RobotDrive(new Talon(0), new Talon(1));
        
        myRobot.setExpiration(0.1);
        //cDrive = new RobotDrive(8,9);
        stick = new Joystick(0);
        cStick = new Joystick(1);
        axisCam = new AxisCamera("10.9.96.11");
        axisCam.writeResolution(AxisCamera.Resolution.k320x240);
        axisCam.writeBrightness(0);    
        soundIn = new AnalogInput(2);
        yAxis = new Servo(8);
        xAxis = new Servo(9);
        di = new DigitalInput(0);
        image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        session = NIVision.IMAQdxOpenCamera("cam0",NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        
    }

    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {
    	System.out.println("Auto done");
        myRobot.setSafetyEnabled(false);
        
    }

    /**
     * Runs the motors with arcade steering.
     */
    boolean tripping = true;
    
    public void operatorControl() {
        myRobot.setSafetyEnabled(true);
        int old = 0;
        while (isOperatorControl() && isEnabled()) {
        
            myRobot.arcadeDrive(stick); // drive with arcade style (use right stick)
            int newv = soundIn.getValue();
            if(newv > old){
            	myRobot.drive(1.0,0.0);
            }else{
            	myRobot.drive(0.0, 0.0);
            }
            old = newv;
            Timer.delay(0.05);
            xAxis.setAngle(xAxis.getAngle() + cStick.getX());
            yAxis.setAngle(yAxis.getAngle() + cStick.getY());
            
        }
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
}