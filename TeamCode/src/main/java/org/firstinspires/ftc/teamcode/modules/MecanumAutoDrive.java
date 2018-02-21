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
    }



    public void update(Telemetry telemetry, float imuDirection) {
        currentAngle = imuDirection;
        if (state == State.IDLE)
            RunIdle();
        else if (state == State.DRIVING)
            RunDrive();
        else if (state == State.TURNING)
            RunTurn();
        else if (state == State.FAILED) {
            StopMotors();
        }

    }


    public State getState() {
        return state;
    }

    public String FailReason() {
        return failReason;
    }

    private void Display() {
        telemetry.addData("State:", stateName);
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
        targetTime = duration;
        targetPower = power;
        ChangeState(State.DRIVING);
    }

    public void Turn(float angle, float power) { // negitive angle if you want to turn the other direction\
        startAngle = currentAngle;
        targetAngleDiff = angle;
        targetPower = power;
        ChangeState(State.DRIVING);
    }

    //============================  state methods
    private void RunIdle() {
        //nada
    }

    private void RunDrive() {
        if (System.currentTimeMillis() - startTime > targetTime)
            ChangeState(State.COMPLETED);
        else {
            //set the motor power
        }

    }

    private void RunTurn() {
        float epsilon = 5.0f;
        //this need more work.
        //360degree problem
        if ((Math.abs(currentAngle - startAngle) - targetAngleDiff) < epsilon)
            ChangeState(State.COMPLETED);
        else {
            // if(targetAngle-currentAngle < 0.0) : motors in one direct
            // else motors in other direction.
            //set the motor power
        }
    }

    private void StopMotors() {
        //nada
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
