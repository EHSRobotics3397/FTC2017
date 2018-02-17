package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.GameButton;
import org.firstinspires.ftc.teamcode.modules.MecanumDrive;
import org.firstinspires.ftc.teamcode.modules.Arm;
import org.firstinspires.ftc.teamcode.modules.Gripper;
//import org.firstinspires.ftc.teamcode.modules.ColorSensing;
import org.firstinspires.ftc.teamcode.modules.RelicExtender;

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
    private DcMotor      motorAim;
    private DcMotor      motorExtender;
    //private ColorSensor sensorColor;
    //private DistanceSensor sensorDistance;

    private Servo        servoLeft;
    private Servo        servoRight;


    private DigitalChannel armLimitSwitch1;
    private DigitalChannel armLimitSwitch2;

    private GameButton   buttonA;
    private GameButton   buttonB;
    private GameButton   buttonX;
    private GameButton   left_Bumper;
    private GameButton   right_Bumper;


    private double yJoyVal;
    private double xJoyVal;
    private MecanumDrive driver;
    private Arm liftArm;
    private Gripper gripper;
    //private ColorSensing cSensor;
    private RelicExtender relicExt;
    //int currentButton = 0;

    private int          MOTORCOUNT = 4;
    private double        power[] = new double[MOTORCOUNT];

    @Override
    public void init(){

        driver = new MecanumDrive();
        liftArm = new Arm();
        gripper = new Gripper();
        //cSensor = new ColorSensing();
        relicExt = new RelicExtender();


        motor1       = hardwareMap.dcMotor.get("motor1");
        motor2       = hardwareMap.dcMotor.get("motor2");
        motor3       = hardwareMap.dcMotor.get("motor3");
        motor4       = hardwareMap.dcMotor.get("motor4");
        motorLift    = hardwareMap.dcMotor.get("motorLift");
        motorAim       = hardwareMap.dcMotor.get("relicAimMotor");
        motorExtender       = hardwareMap.dcMotor.get("relicExtendMotor");

        servoLeft    = hardwareMap.servo.get("servoLeft");
        servoRight   = hardwareMap.servo.get("servoRight");
        armLimitSwitch1 = hardwareMap.digitalChannel.get("armLimitSwitch1");
        armLimitSwitch1.setMode(DigitalChannel.Mode.INPUT);
        armLimitSwitch2 = hardwareMap.digitalChannel.get("armLimitSwitch2");
        armLimitSwitch2.setMode(DigitalChannel.Mode.INPUT);

        //sensorColor = hardwareMap.get(com.qualcomm.robotcore.hardware.ColorSensor.class, "revColorSensor");
        //sensorDistance = hardwareMap.get(DistanceSensor.class, "revColorSensor");

        motor3.setDirection(DcMotor.Direction.REVERSE);
        motor4.setDirection(DcMotor.Direction.REVERSE);

        buttonA      = new GameButton(gamepad1, GameButton.Label.a);
        buttonB      = new GameButton(gamepad1, GameButton.Label.b);
        buttonX      = new GameButton(gamepad1, GameButton.Label.x);
        left_Bumper  = new GameButton(gamepad1, GameButton.Label.LBumper);
        right_Bumper  = new GameButton(gamepad1, GameButton.Label.RBumper);



        driver.setup(motor1, motor2, motor3, motor4, gamepad1);
        gripper.setup(servoLeft, servoRight, gamepad2);
        liftArm.setup(motorLift, armLimitSwitch1, armLimitSwitch2, gamepad2);
        //cSensor.setup(sensorColor,sensorDistance);
        relicExt.setup(motorAim, motorExtender, gamepad2);
    }

    @Override
    public void loop() {
        driver.update(telemetry);
        liftArm.update(telemetry);
        gripper.update(telemetry);
        //cSensor.update(telemetry);
        relicExt.update(telemetry);
    }
}
