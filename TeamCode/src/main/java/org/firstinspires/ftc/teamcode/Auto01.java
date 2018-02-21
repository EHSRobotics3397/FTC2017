package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.modules.IMURotate;
import org.firstinspires.ftc.teamcode.modules.MecanumDrive;

/**
 * Created by greenteam on 2/8/2017
 * Autonomous Drive1 (Test)
 */

@Autonomous(name = "Auto MK1", group = "Drive")

public class Auto01 extends OpMode {

    private DcMotor     collectorMotor;
    private float WAIT_FOR_HARVESTER = 2.0f;

    private enum State {IDLE, DELAYED, DRIVE1, DRIVE2, COMPLETED, FAILED };
    private String stateName;
    private String failReason;
    private long startTime;

    private float delayTime = 0.0f; //secs

    private State state;
    private State nextState;

    private DcMotor      motor1;
    private DcMotor      motor2;
    private DcMotor      motor3;
    private DcMotor      motor4;

    private BNO055IMU imu;

    //Modules
    private MecanumDrive driver;
    private IMURotate rotationFinder;

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


        if(gamepad1.a) {
            delayTime = 1.0f;
        }

        ChangeState(State.IDLE);
        Display();
    }

    @Override
    public void loop() {
        rotationFinder.update(telemetry);
        float imuDirection = (float) rotationFinder.getAngle();

        switch(state) {
            case IDLE:
                stateName = "IDLE";
                nextState = State.DRIVE1;
                Idle();
                break;
            case DRIVE1:
                stateName = "DRIVE1";
                nextState = State.DRIVE2;
                Drive(6.0f, 0.40f);
                break;
            case DRIVE2:
                stateName = "DRIVE2";
                nextState = State.COMPLETED;
                Drive(24.0f, 1.0f);
                break;
            case FAILED:
                stateName = "FAILED";
                nextState = State.FAILED;
                Failed();
                break;
            case COMPLETED:
                stateName = "COMPLETED";
                Completed();
                break;
        }
        driver.update(telemetry, imuDirection);
        Display();


    }

    private void Display() {

        telemetry.addData("Auto v001 State: ", stateName);
        telemetry.addData("Time: ",String.format("%3.2f s", ElapsedTimeInState()));
    }

    private void ChangeState(State newState) {
        state = newState;
        startTime = System.currentTimeMillis();
    }

    private float ElapsedTimeInState() {
        long elapsedTime  = System.currentTimeMillis() - startTime;
        return ((float) elapsedTime)/1000.0f; // time in secs
    }

    //--------- functions handling processoing during State
    private void Idle() {
        telemetry.addData("Delayed start: ", String.format("%2.1f", delayTime));
        if (ElapsedTimeInState() > delayTime)
            ChangeState(nextState);
    }

    private void Completed() {
        Display();
    }

    //this can be used to drive straight, turn, spin or backup
    //each driving segments should have it's own state (DRIVE1, DRIVE2..) and
    //matching function here. The DriveAuto class will take care of the driving.
    //This is just used to issue the command and check for completion.
    private void Drive(float duration, float power) {
        if (driver.getState() == DriveAuto.State.IDLE)
            driver.Drive(duration, power); //negative power for back
        else if (driver.getState() == DriveAuto.State.FAILED) {
            failReason = driver.FailReason();
            ChangeState(State.FAILED);
        }
        else if (driver.getState() == DriveAuto.State.COMPLETED){
            driver.Reset();
            ChangeState(nextState);
        }
    }

    private void Turn(float angle, float power) {
        if (driver.getState() == DriveAuto.State.IDLE)
            driver.Turn(angle, power);
        else if (driver.getState() == DriveAuto.State.FAILED) {
            failReason = driver.FailReason();
            ChangeState(State.FAILED);
        }
        else if (driver.getState() == DriveAuto.State.COMPLETED){
            driver.Reset();
            ChangeState(nextState);
        }
    }

    private void Failed() {
        telemetry.addData("Auto Failed: ", failReason);
    }
}
