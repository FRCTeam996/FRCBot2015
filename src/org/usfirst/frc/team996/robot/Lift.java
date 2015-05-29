package org.usfirst.frc.team996.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

//frc00996

public class Lift{
	
	boolean isDouble = true;
	
	//Solenoid
	Solenoid solenoidA;
	Solenoid solenoidB;
	DoubleSolenoid dSolenoid;
	boolean actuate = false;
	final int SOLENOID_PORT_1 = 2;
	final int SOLENOID_PORT_2 = 3;
	
	//Toggle
	boolean toggleClaw = false;
	long lastPulled = 0;
	double delaySeconds = 1.0;
	
	public Lift(){
		
		//"Single" 
		solenoidA = new Solenoid(SOLENOID_PORT_1);
		solenoidB = new Solenoid(SOLENOID_PORT_2);
		
		//On start-up actuate
		solenoidA.set(false);
		solenoidB.set(true);
		
		//Double 
		//dSolenoid = new DoubleSolenoid(SOLENOID_PORT_1, SOLENOID_PORT_2);
	}
	
	public void teleOp(boolean trigPulled){
			if(trigPulled){
				toggleClaw = !toggleClaw;
			}
			if(toggleClaw){
				enableSolenoid();
				Timer.delay(0.5);
			}else{
				disableSolenoid();
				Timer.delay(0.5);
			}
	}
	
	public boolean clawUpdate(){
		
		return false;
	}
	
	public boolean enableSolenoid(){
		try{
			solenoidA.set(true);
			solenoidB.set(false);
			return true;
		}catch(Exception e){
			return false;
		}
			//3 power
	}
	
	public boolean disableSolenoid(){
		try{
			solenoidA.set(false);
			solenoidB.set(true);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}

}
