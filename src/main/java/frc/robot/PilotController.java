package frc.robot;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Encapsulates the pilot controller inputs
 */
public class PilotController {
    private XboxController m_controller;

    private SlewRateLimiter m_accelFilter;

    public enum DesiredDirection {
        Initial, 
        Reversed,
        NoChange
    };

    /**
     * Constructor for the pilot controller. Instantiates the xbox controller.
     */
    public PilotController() {
        m_controller = new XboxController(RobotMap.PilotControllerConstants.XBOX_CONTROLLER_USB_PORT);
        m_accelFilter = new SlewRateLimiter(RobotMap.PilotControllerConstants.ACCEL_SLEW_RATE);
    }

    public double getDriverSpeed() {
        double turn = m_controller.getRightTriggerAxis() - m_controller.getLeftTriggerAxis();
        turn = m_accelFilter.calculate(turn);
        return adjustForDeadband(turn);
    }
    
    public double getDriverTurn() {
        //Adjusting for a deadband to compensate for controller stick drift.
        double turnInput = -m_controller.getLeftX();
        double squaredTurnInput = turnInput * turnInput;
        squaredTurnInput = Math.copySign(squaredTurnInput, turnInput);

        double scaledTurnInput = (squaredTurnInput * RobotMap.PilotControllerConstants.TURN_SCALER);

        return adjustForDeadband(scaledTurnInput);
    }

    public boolean getLauncherButton() {
        boolean launcherInput = m_controller.getAButton();
        return launcherInput;
    }

    public DesiredDirection getPilotChangeControls() {
        DesiredDirection desiredDirection = DesiredDirection.NoChange;
        
        if(m_controller.getRightBumper()) {
            desiredDirection = DesiredDirection.Initial;
        }
        else if (m_controller.getLeftBumper()){
            desiredDirection = DesiredDirection.Reversed;
        }

       return desiredDirection;
    }
    
    /**
     * Deadband method for stick.
     * @param stickInput takes a value from -1 to 1.
     * @return input value adjusted for deadband. It is a double with a value between -1 and 1.
     */
    private double adjustForDeadband(double stickInput) {
        double retVal = 0;
        double deadband = RobotMap.PilotControllerConstants.STICK_DEADBAND;
    
        //take absolute value for simplification.
        double absStickInput = Math.abs(stickInput);

        //if the stick input is outside the deadband, adjust.
        if (!(absStickInput < deadband)) {
            retVal = (absStickInput / (1.0 - deadband));
            retVal = Math.copySign(retVal, stickInput);
        }

        return retVal;
    }    
}
