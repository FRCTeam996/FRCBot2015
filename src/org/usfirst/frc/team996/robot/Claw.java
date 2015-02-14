package org.usfirst.frc.team996.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

public class Claw {
	
	//No idea if it is a double or single solenoid so... time to code for both!
	boolean isDouble = false;
	
	//Solenoid
	Solenoid solenoid;
	DoubleSolenoid dSolenoid;
	boolean actuate = false;
	final int SOLENOID_PORT_1 = 1;
	final int SOLENOID_PORT_2 = 2;
	
	public Claw(){
		
		//Single
		solenoid = new Solenoid(SOLENOID_PORT_1);
		
		//Double
		dSolenoid = new DoubleSolenoid(SOLENOID_PORT_1, SOLENOID_PORT_2);
	}
	
	//Will be used later to determine whether or not enable or disable solenoid
	public boolean clawUpdate(){
		
		return false;
	}
	
	//Enables Solenoid
	public boolean enableSolenoid(){
		if(isDouble){
			dSolenoid.set(DoubleSolenoid.Value.kForward);
			return true;
		}else{
			solenoid.set(true);
			return true;
		}
	}
	
	//Disables Solenoid
	public boolean disableSolenoid(){
		if(isDouble){
			dSolenoid.set(DoubleSolenoid.Value.kReverse);
			return true;
		}else{
			solenoid.set(false);
			return true;
		}
	}

}
