package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 *  Gripper - 2 paddle control using servos
 *  The gripper has multiple positions mapped to buttons and
 *  manual adjustment using the left joystick on the driver game pad.
 */

public class RelicGrabber {

    private Gamepad gamePad;
    private GameButton  dPadUp;
    private GameButton  dPadDown;

    private GameButton  dPadLeft;
    private GameButton  dPadRight;


    private Servo gripServo;

    public static int HOME = 0;
    public static int OPEN = 1;
    public static int CLOSE= 2;
    public static int MANUAL=3;

    private String[] statusName = new String[] {"HOME", "OPEN", "CLOSE", "MANUAL"};

    private double servoPosition;
    private String gripperStatus;
    private double[] gripper_stops = new double[]  {0.17, 0.82, 0.95};

    private double moveIncrement = 0.002;

    boolean firstUpdate = true;

    public void setup(Servo aRightServo, Gamepad pad){
        gripServo = aRightServo;

        gamePad = pad;
        dPadUp = new GameButton(gamePad, GameButton.Label.dpadUp);
        dPadDown = new GameButton(gamePad, GameButton.Label.dpadDown);
        dPadLeft = new GameButton(gamePad, GameButton.Label.dpadLeft);
        dPadRight = new GameButton(gamePad, GameButton.Label.dpadRight);

    }

    public void goHome() {
        servoPosition = gripper_stops[HOME];
        positionGripper(servoPosition);
    }

    public void update(Telemetry telemetry){

        dPadDown.Update();
        dPadUp.Update();

        //button map:
        // D-Pad down: OPEN,  D-Pad up: CLOSE,  D-Pad left/right : MANUAL

        if (dPadDown.Press()) {
            servoPosition = gripper_stops[HOME];
            positionGripper(servoPosition);
            gripperStatus = statusName[HOME];
        }

        else if (dPadUp.Press()) {
            servoPosition = gripper_stops[OPEN];
            positionGripper(servoPosition);
            gripperStatus = statusName[OPEN];
        }
        else {
            if (dPadLeft.IsDown()) {
                servoPosition += moveIncrement;
                positionGripper(servoPosition);
                gripperStatus = statusName[MANUAL];
            }
            else if (dPadRight.IsDown()){
                servoPosition -= moveIncrement;
                positionGripper(servoPosition);
                gripperStatus = statusName[MANUAL];
            }
        }

        telemetry.addData("Gripper: ", gripperStatus);
        double servoPosition = gripServo.getPosition();
        telemetry.addData("Servo L: ", String.format("%.2f", servoPosition));

    }

    private void positionGripper(double index) {

        gripServo.setPosition(index);
    }
}
