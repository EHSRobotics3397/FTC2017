package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.Arm;
import org.firstinspires.ftc.teamcode.modules.Gripper;
import org.firstinspires.ftc.teamcode.modules.GameButton;

import org.firstinspires.ftc.teamcode.modules.MecanumDrive;
import org.firstinspires.ftc.teamcode.modules.RelicExtender;

import java.lang.Thread;

//import org.firstinspires.ftc.teamcode.modules.ColorSensing;

//import org.firstinspires.ftc.teamcode.modules.LineFollower;

/**
 * Created by greenteam on 1/12/17.
 * Main Drive Opmode
 * */

@TeleOp(name = "AutoOp", group = "Drive")
public class AutoOp extends LinearOpMode {

    private DcMotor      motor1;
    private DcMotor      motor2;
    private DcMotor      motor3;
    private DcMotor      motor4;

    private DcMotor      motorLift;

    private Servo        servoLeft;
    private Servo        servoRight;

    private DigitalChannel armLimitSwitch1;
    private DigitalChannel armLimitSwitch2;

    private Arm liftArm;
    private Gripper gripper;
    private RelicExtender relicExt;
    private MecanumDrive driver;


    public void runOpMode(){
        driver = new MecanumDrive();
        motor1       = hardwareMap.dcMotor.get("motor1");
        motor2       = hardwareMap.dcMotor.get("motor2");
        motor3       = hardwareMap.dcMotor.get("motor3");
        motor4       = hardwareMap.dcMotor.get("motor4");
        driver.setup(motor1, motor2, motor3, motor4, gamepad1);
        motor3.setDirection(DcMotor.Direction.REVERSE);
        motor4.setDirection(DcMotor.Direction.REVERSE);


        waitForStart();

        motor1.setPower(-1.0);
        motor2.setPower(-1.0);
        motor3.setPower(1.0);
        motor4.setPower(1.0);
        try {Thread.sleep(2000);} catch (InterruptedException e) {}
        motor1.setPower(0.0);
        motor2.setPower(0.0);
        motor3.setPower(0.0);
        motor4.setPower(0.0);
        try {Thread.sleep(2000);} catch (InterruptedException e) {}
        motor1.setPower(1.0);
        motor2.setPower(1.0);
        motor3.setPower(1.0);
        motor4.setPower(1.0);
        try {Thread.sleep(1000);} catch (InterruptedException e) {}
        motor1.setPower(0.0);
        motor2.setPower(0.0);
        motor3.setPower(0.0);
        motor4.setPower(0.0);


    }



}
