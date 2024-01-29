package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * This class encapsulates the indexing mechanism!
 */
public class Indexer {
    // The motorcontroller that controls the indexer motor.
    private WPI_TalonSRX m_indexMotor; 


    // The sensor that tells the indexer when to stop or go.    
    private DigitalInput m_indexSensor;
         
}
