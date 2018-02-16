package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 *  Gripper - 2 paddle control using servos
 *  The gripper has multiple positions mapped to buttons and
 *  manual adjustment using the left joystick on the driver game pad.
 */

public class Gripper {

    private Gamepad gamePad;
    private GameButton  buttonA;
    private GameButton  buttonB;
    private GameButton  buttonX;
    private GameButton  buttonY;

    private Servo leftServo;
    private Servo rightServo;

    public static int HOME = 0;
    public static int OPEN = 1;
    public static int CLOSE= 2;
    public static int MANUAL=3;

    private String[] statusName = new String[] {"HOME", "OPEN", "CLOSE", "MANUAL"};

    private double servoPosition;
    private String gripperStatus;
    private double[] gripper_stops = new double[]  {0.08, 0.82, 0.95}; //tuned on 1/27/18

    private double moveIncrement = 0.002;
    double stickDeadZone = 0.08;

    boolean firstUpdate = true;

    public void setup(Servo aLeftServo, Servo aRightServo, Gamepad pad){
        rightServo = aRightServo;
        leftServo = aLeftServo;
        gamePad = pad;
        buttonA = new GameButton(gamePad, GameButton.Label.a);
        buttonB = new GameButton(gamePad, GameButton.Label.b);
        buttonX = new GameButton(gamePad, GameButton.Label.x);
        buttonY = new GameButton(gamePad, GameButton.Label.y);
    }

    public void goHome() {
        servoPosition = gripper_stops[HOME];
        positionGripper(servoPosition);
    }

    public void update(Telemetry telemetry){
        buttonA.Update();
        buttonB.Update();
        buttonX.Update();
        buttonY.Update();

        //button map:
        // X: OPEN,  Y:HOME  B:CLOSE

        if (buttonX.Press()) {
            servoPosition = gripper_stops[HOME];
            positionGripper(servoPosition);
            gripperStatus = statusName[HOME];
        }
        else if (buttonY.Press()) {
            servoPosition = gripper_stops[OPEN];
            positionGripper(servoPosition);
            gripperStatus = statusName[OPEN];
        }
        else if (buttonB.Press()) {
            servoPosition = gripper_stops[CLOSE];
            positionGripper(servoPosition);
            gripperStatus = statusName[CLOSE];
        }
        else {
            double x = gamePad.left_stick_x;
            if (Math.abs(x) > stickDeadZone) {
                servoPosition += moveIncrement * x;
                positionGripper(servoPosition);
                gripperStatus = statusName[MANUAL];
            }
        }
        telemetry.addData("Gripper: ", gripperStatus);
        double servoPositionL = leftServo.getPosition();
        double servoPositionR = rightServo.getPosition();
        telemetry.addData("Servo L: ", String.format("%.2f", 1.0-servoPositionL));
        telemetry.addData("Servo T: ", String.format("%.2f", servoPositionR));
    }

    private void positionGripper(double index) {
        leftServo.setPosition(1.0-index); //flipped range (1.0 to 0.0);
        rightServo.setPosition(index);
    }
}
