package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.GameButton;
import org.firstinspires.ftc.teamcode.modules.Matrix;
import org.firstinspires.ftc.teamcode.modules.MecanumSolver;
import org.firstinspires.ftc.teamcode.modules.MecanumDrive;
import org.firstinspires.ftc.teamcode.modules.Lift;
import org.firstinspires.ftc.teamcode.modules.Gripper;

//import org.firstinspires.ftc.teamcode.modules.LineFollower;

/**
 * Created by greenteam on 1/12/17.
 * Main Drive Opmode
 * */

@TeleOp(name = "MecanumDriveOp", group = "Drive")
public class MecanumDriveOp extends OpMode {

    private DcMotor      motor1;
    private DcMotor      motor2;
    private DcMotor      motor3;
    private DcMotor      motor4;
    private DcMotor      motorLift;

    private Servo        servoLeft;
    private Servo        servoRight;

    private GameButton   buttonA;
    private GameButton   buttonB;
    private GameButton   buttonX;

    private double yJoyVal;
    private double xJoyVal;
    private MecanumDrive driver;
    private Lift liftArm;
    private Gripper gripper;
    //int currentButton = 0;

    private int          MOTORCOUNT = 4;
    private double        power[] = new double[MOTORCOUNT];

    private String msg;

    @Override
    public void init(){

        driver = new MecanumDrive();
        liftArm = new Lift();
        gripper = new Gripper();

        motor1       = hardwareMap.dcMotor.get("motor1");
        motor2       = hardwareMap.dcMotor.get("motor2");
        motor3       = hardwareMap.dcMotor.get("motor3");
        motor4       = hardwareMap.dcMotor.get("motor4");
        motorLift    = hardwareMap.dcMotor.get("motorLift");
        servoLeft    = hardwareMap.servo.get("servoLeft");
        servoRight   = hardwareMap.servo.get("servoRight");

        motor3.setDirection(DcMotor.Direction.REVERSE);
        motor4.setDirection(DcMotor.Direction.REVERSE);

        buttonA      = new GameButton(gamepad1, GameButton.Label.a);
        buttonB      = new GameButton(gamepad1, GameButton.Label.b);
        buttonX      = new GameButton(gamepad1, GameButton.Label.x);

        motorLift.setDirection(DcMotor.Direction.REVERSE);

        msg = "released";

        driver.setup(motor1, motor2, motor3, motor4, gamepad1);
        gripper.setup(servoLeft, servoRight, gamepad2);
        liftArm.setup(motorLift, gamepad2);

    }

    @Override
    public void loop() {
        driver.update(telemetry);
        liftArm.update(telemetry);
        gripper.update(telemetry);
    }
}
