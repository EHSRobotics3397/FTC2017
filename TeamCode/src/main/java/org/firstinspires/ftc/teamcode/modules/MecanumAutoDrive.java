package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//import org.firstinspires.ftc.teamcode.modules.LineFollower;

/**
 * Created by greenteam on 1/12/17.
 * Main Drive Opmode
 * */


public class MecanumAutoDrive {

    public enum State {IDLE, DRIVING, TURNING, COMPLETED, FAILED };
    private State  state;
    private String stateName;
    private String failReason;

    private DcMotor      motor1;
    private DcMotor      motor2;
    private DcMotor      motor3;
    private DcMotor      motor4;

    private MecanumSolver solver;

    float targetTime = 0.0f;
    float startAngle = 0.0f;
    float targetAngleDiff = 0.0f;
    float targetPower = 0.0f;
    float currentAngle = 0.0f;

    long    startTime;

    private int          MOTORCOUNT = 4;
    private double        power[] = new double[MOTORCOUNT];

    public void setup(DcMotor m1, DcMotor m2, DcMotor m3, DcMotor m4){
        motor1 = m1;
        motor2 = m2;
        motor3 = m3;
        motor4 = m4;

        solver = new MecanumSolver();

        ChangeState(State.IDLE);
    }



    public void update(Telemetry telemetry, float imuDirection) {
        targetPower = 0; // for testing

        power[0] = 0;
        power[1] = 0;
        power[2] = 0;
        power[3] = 0;

        currentAngle = imuDirection;
        if (state == State.IDLE){
            RunIdle();
            stateName = "IDLE";
        }
        else if (state == State.DRIVING){
            RunDrive();
            stateName = "DRIVING";
        }
        else if (state == State.TURNING){
            RunTurn();
            stateName = "TURNING";
        }
        else if (state == State.FAILED) {
            StopMotors();
            stateName = "FAILED";
        }

        motor1.setPower(power[0]);
        motor2.setPower(power[1]);
        motor3.setPower(power[2]);
        motor4.setPower(power[3]);


        telemetry.addData("Driver State:", stateName);
    }


    public State getState() {
        return state;
    }

    public String FailReason() {
        return failReason;
    }

    private void ChangeState(State newState) {
        state       = newState;

        startTime   = System.currentTimeMillis();
    }

    private float ElapsedTimeInState() {
        long elapsedTime  = System.currentTimeMillis() - startTime;
        return ((float) elapsedTime) / 1000.0f; // time in secs
    }

    public void Reset() {
        failReason          = "N/A";
        ChangeState(State.IDLE);
    }

    public void Drive(float duration, float power) {
        targetTime = duration * 1000; //changes from sec to millsec
        targetPower = power;
        ChangeState(State.DRIVING);
    }

    public void Turn(float duration, float power) { // negitive angle if you want to turn the other direction\
        startAngle = currentAngle;
        //targetAngleDiff = angle; // change to time
        targetTime = duration * 1000;
        targetPower = power;
        ChangeState(State.TURNING);
    }

    //============================  state methods
    private void RunIdle() {
        //nada
    }

    private void RunDrive() {
        if (System.currentTimeMillis() - startTime > targetTime)
            ChangeState(State.COMPLETED);
        else {
            power[0] = targetPower;
            power[1] = targetPower;
            power[2] = targetPower;
            power[3] = targetPower;
            //set the motor power
        }

    }

    private void RunTurn() {
        float angleTraveled = WrapOneEighty(currentAngle - startAngle);
        float epsilon = 5.0f;
        //this need more work.
        //360degree problem

        //if ((Math.abs(currentAngle - startAngle) - targetAngleDiff) < epsilon)
        if (System.currentTimeMillis() - startTime > targetTime)
            ChangeState(State.COMPLETED);
        else {

            power[0] = -targetPower;
            power[1] = -targetPower;
            power[2] = targetPower;
            power[3] = targetPower;
            // if(targetAngle-currentAngle < 0.0) : motors in one direct
            // else motors in other direction.
            //set the motor power
        }
    }

    private float WrapOneEighty (float currentAng){
        if (currentAng > 180){
            currentAng -= 360;
        }
        if (currentAng < -180){
            currentAng += 360;
        }
        return currentAng;
    }

    private void StopMotors() {
        targetPower = 0;
    }



}

 /*
        power[0] = 0;
        power[1] = 0;
        power[2] = 0;
        power[3] = 0;


        Matrix W = solver.solve(-yJoyVal, -xJoyVal, 0);

        power[0] = W.element(0,0);
        power[1] = W.element(1,0);
        power[2] = W.element(2,0);
        power[3] = W.element(3,0);

        motor1.setPower(power[0]);
        motor2.setPower(power[1]);
        motor3.setPower(power[2]);
        motor4.setPower(power[3]);
         */
