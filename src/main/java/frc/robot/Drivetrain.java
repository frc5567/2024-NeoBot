package frc.robot;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain {
    private CANSparkMax m_leftLeader;
    private CANSparkMax m_rightLeader;
    private CANSparkMax m_leftFollower;
    private CANSparkMax m_rightFollower;

    private DifferentialDrive m_drive;

    /**
     * The variable that keeps track of current drivetrain direction.
     * True is initial direction (forward). False is reversed control.
     */
    private boolean m_isDrivetrainForward;

    /**
     * Main constructor for the drivetrain class
     * @param pidgey IMU pigeon instance
     */
    public Drivetrain() {
        m_leftLeader = new CANSparkMax(RobotMap.DrivetrainConstants.LEFT_LEADER_CAN_ID, CANSparkLowLevel.MotorType.kBrushless);
        m_rightLeader = new CANSparkMax (RobotMap.DrivetrainConstants.RIGHT_LEADER_CAN_ID, CANSparkLowLevel.MotorType.kBrushless);
        m_leftFollower = new CANSparkMax(RobotMap.DrivetrainConstants.LEFT_FOLLOWER_CAN_ID, CANSparkLowLevel.MotorType.kBrushless);
        m_rightFollower = new CANSparkMax(RobotMap.DrivetrainConstants.RIGHT_FOLLOWER_CAN_ID, CANSparkLowLevel.MotorType.kBrushless);
        
        m_leftFollower.follow(m_leftLeader);
        m_rightFollower.follow(m_rightLeader);
        
        m_drive = new DifferentialDrive(m_leftLeader, m_rightLeader);

        m_isDrivetrainForward = true;
    }

    /**
     * Init function to intialize the Drivetrain in its entirety
     */
    public void initDrivetrain() {
 
        m_leftLeader.setInverted(false);
        m_leftFollower.setInverted(false);
        
        m_rightLeader.setInverted(true);
        m_rightFollower.setInverted(true);

        m_isDrivetrainForward = true;
    }

    /**
     * Method that makes the drivetrain move forward/backwards and turn.
     * @param speed Value between -1 and 1 for robot speed.
     * @param turn Value between -1 and 1 for turning
     */
    public void arcadeDrive(double speed, double turn) {
        if(m_isDrivetrainForward == true) {
            m_drive.arcadeDrive(speed, turn);
        }
        else {
            m_drive.arcadeDrive(-speed, turn);
        }
    }

    /**
     * Method used to set motor directions while driving.
     * 
     * @param desiredDirection true represents the initial direction, false represents the reversed controls.
     */
    public void setDesiredDirection(PilotController.DesiredDirection desiredDirection) {

        if(desiredDirection == PilotController.DesiredDirection.Initial) {
            m_isDrivetrainForward = true;
        }
        else if(desiredDirection == PilotController.DesiredDirection.Reversed) {
            m_isDrivetrainForward = false;
        }
       
    }
}
