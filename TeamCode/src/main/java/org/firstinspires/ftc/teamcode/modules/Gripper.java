package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;


/**
 * Created by Willem Hunt on 1/27/2017.
 * Controls the motion of the lift
 * Pass 2 DcMotors, Gamepad
 */

public class Gripper {

    private String      gripperStatus;
    private GameButton  a;
    private GameButton  b;
    private Gamepad gamePad;
    private float servoPosition;
    private Servo   leftServo;
    private Servo   rightServo;
    private static final double GRIPPER_HOME = 0.0; //left and right will be different
    private static final double GRIPPER_OPEN = 1.0;
    private static final double GRIPPER_CLOSE = 0.0;


    public void setup(Servo aLeftServo, Servo aRightServo, Gamepad pad){
        rightServo = aRightServo;
        leftServo = aLeftServo;
        gamePad = pad;
        a = new GameButton(gamePad, GameButton.Label.a);
        b = new GameButton(gamePad, GameButton.Label.b);

        servoPosition = 0.0f;
    }

    public void update(Telemetry telemetry){

        a.Update();
        b.Update();

        if (a.Press())
            openGripper();
        else if (b.Press())
            closeGripper();
        telemetry.addData("Lift",  ": " + gripperStatus);

        servoPosition = gamePad.left_stick_x;
        leftServo.setPosition(servoPosition);
        rightServo.setPosition(-servoPosition);
        telemetry.addData("Servo ", String.format("%.2f", servoPosition));
    }

    private void openGripper(){
        leftServo.setPosition(GRIPPER_OPEN);
        rightServo.setPosition(-GRIPPER_OPEN);
        gripperStatus = "OPEN";
    }

    private void closeGripper(){
        leftServo.setPosition(GRIPPER_CLOSE);
        rightServo.setPosition(-GRIPPER_CLOSE);
        gripperStatus = "CLOSE";
    }

    /*
         openPosition = 0.0;
         closePosition = 0.0;
         servoPosition = gamepad.stickleft.x;
         leftServo.setPosition(x);
         rightServo.setPosition(-x);
         telementry:  + servoPosition
     */




}
