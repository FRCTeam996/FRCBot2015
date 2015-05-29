package org.usfirst.frc.team996.robot.auto;

import org.usfirst.frc.team996.robot.Drive;

import edu.wpi.first.wpilibj.Timer;

public class Auto {
	
	private final Drive DRIVE;
	private final boolean SQUARE_CHASSIS_INPUTS = false;
	
	public Auto(Drive drive){
		this.DRIVE = drive;
	}
	
	public boolean drive(int dir, double speed){
		
		//Forward
		if(dir == 0){
			DRIVE.arcadeDriveChassis(-1.0*speed, 0.0, SQUARE_CHASSIS_INPUTS);
		}
		//Left
		
		//Back
		
		//Right
		
		return true;
	}
	
	public void stop(){
		drive(0,0.0);
	}

}
