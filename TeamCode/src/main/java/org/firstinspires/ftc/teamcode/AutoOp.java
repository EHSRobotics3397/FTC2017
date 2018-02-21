package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

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
import org.firstinspires.ftc.teamcode.modules.IMURotate;

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
public class AutoOp extends OpMode {

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
    private IMURotate rotationFinder;
    private BNO055IMU imu;

    @Override
    public void init(){

        driver = new MecanumDrive();
        rotationFinder = new IMURotate();
        motor1       = hardwareMap.dcMotor.get("motor1");
        motor2       = hardwareMap.dcMotor.get("motor2");
        motor3       = hardwareMap.dcMotor.get("motor3");
        motor4       = hardwareMap.dcMotor.get("motor4");

        motor3.setDirection(DcMotor.Direction.REVERSE);
        motor4.setDirection(DcMotor.Direction.REVERSE);

        driver.setup(motor1, motor2, motor3, motor4, gamepad1);
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        rotationFinder.setup(imu, telemetry);

        telemetry.addData("Mode", "calibrating...");

        //driver.setImuModel(rotationFinder);
    }

    @Override
    public void loop(){
        rotationFinder.update(telemetry);
        driver.update(telemetry);

        //step1:
        //drive.DriveForward(1.5, 0.8);

        //step2
        //drive.Turn(90.0);

        //st3p

        /*
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

        * */


    }



}
