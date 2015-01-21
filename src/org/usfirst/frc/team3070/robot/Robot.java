package org.usfirst.frc.team3070.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	CANTalon motorLeft, motorRight;
	Joystick xbox;
	AnalogInput ultrasonic;
	Encoder encoderLeft, encoderRight; 
	PIDController PIDLeft, PIDRight;
	Gyro gyro;
	double p, i, d;
	LiveWindow display;
	
	public void robotInit() {
		motorLeft = new CANTalon(1);
		motorRight = new CANTalon(2);
		xbox = new Joystick(1);
		ultrasonic = new AnalogInput(0);
		
		encoderLeft = new Encoder(1,2);
		encoderRight = new Encoder(3,4);
		
//		encoderLeft.setReverseDirection(true);
		
		gyro = new Gyro(1);
		
		p = .02;
		i = .000;
		d = 0.0000;
		
//		PIDLeft = new PIDController(2,0,0, .15,encoderLeft,motorLeft);
//		PIDRight = new PIDController(2,0,0, .15,encoderRight,motorRight);
		
		PIDLeft = new PIDController(p, i, d, gyro, motorLeft);
		PIDRight = new PIDController(p, i, d, gyro, motorRight);
		
		gyro.reset();
	}
	
	public void teleOpInit() {
		display = new LiveWindow();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		PIDLeft.enable();
		PIDRight.enable();
		
		PIDLeft.setSetpoint(0);
		PIDRight.setSetpoint(0);
		
		if (ultrasonic.getVoltage() > .22) {
			motorLeft.set(.75);
			motorRight.set(-.75);
		} else {
			motorLeft.set(0);
			motorRight.set(0);
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		if (xbox.getRawButton(1)) {
			PIDLeft.enable();
			PIDRight.enable();
			PIDLeft.setSetpoint(90);
			PIDRight.setSetpoint(90);
		}
		
//		motorLeft.set(-xbox.getRawAxis(1));
//		motorRight.set(xbox.getRawAxis(5));
		
		//System.out.println(ultrasonic.getVoltage());
		//System.out.println("left: " + encoderLeft.getRate());
		//System.out.println("right: " + encoderRight.getRate());
	}
	
	public void disableInit() {
		gyro.reset();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {

	}

}
