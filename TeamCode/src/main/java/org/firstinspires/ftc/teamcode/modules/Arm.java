package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.Telemetry;


/**
 * Created by Willem Hunt on 1/27/2017.
 * Controls the motion of the lift
 * Pass 2 DcMotors, Gamepad
 */

public class Arm {

    public enum State {HOMING, MANUAL, AUTO}

    private String      liftStatus;
    private DcMotor     motor;
    private GameButton  x;
    private GameButton  y;
    private GameButton  a;
    private Gamepad gamePad;

    private int         encoderStart    = 0;
    private int         encoderVal      = encoderStart;
    private int relativeEncoder = encoderStart;

    private boolean retractSwitchClosed;
    private boolean extendSwitchClosed;
    private boolean creepMode;

    private DigitalChannel retractSensor;
    private DigitalChannel extendSensor;
    private State state;
    private String stateName;
    private String armAngle;
    private String gravityComp;

    private static final float HOME_SPEED = 0.15f;
    private final float ENCODERCOUNT_PER_RAD = (float)(Math.PI/12.0);
    private final double TICKS_PER_REV_NEV60 = 1680.0;
    private final float GEAR_REDUCTION = 3;
    private final double RAD_PER_TICK = 2*Math.PI/(TICKS_PER_REV_NEV60*GEAR_REDUCTION);

    private final float VERTICAL_OFFSET = -1680f;
    private final float K_COMPENSATE = 0.19f;


    // will need to add the retractSensor.
    public void setup(DcMotor aMotor, DigitalChannel limitSwitch1, DigitalChannel limitSwitch2,Gamepad aGamePad){
        motor  = aMotor;
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        retractSensor = limitSwitch2;
        extendSensor = limitSwitch1;

        gamePad = aGamePad;
        y = new GameButton(gamePad, GameButton.Label.y);
        x = new GameButton(gamePad, GameButton.Label.x);
        a = new GameButton(gamePad, GameButton.Label.a);
        SetState(State.MANUAL); // set this to homing wehn we need to use it. this is just for testing as of now
        creepMode = false;
    }

    public void SetState(State newState) {
        state = newState;
    }

    public void update(Telemetry telemetry){
        encoderVal = motor.getCurrentPosition();
        telemetry.addData("Encoder Position", encoderVal);

        retractSwitchClosed = !retractSensor.getState(); // normally open
        extendSwitchClosed = !extendSensor.getState();
        if (retractSwitchClosed) {
            encoderStart = encoderVal;
        }
        relativeEncoder = encoderVal - encoderStart;

        switch (state){
            case HOMING: HomingMode();
                stateName = "Homing";
                break;
            case MANUAL: ManualMode();
                stateName = "Manual";
                break;
            case AUTO: AutoMode();
                stateName = "Auto";
                break;
            default :
                stateName = "Unknown";
                break;
        }
        float angle = encoderCountsToAngle(relativeEncoder);
        armAngle = String.format("%.2f", Math.toDegrees(angle));

        telemetry.addData("Arm position: ", String.format("%d", relativeEncoder));
        telemetry.addData("Arm state: ", stateName);
        telemetry.addData("Home switch: ", retractSwitchClosed ? "closed" : "open");
        telemetry.addData("Extend switch: ", extendSwitchClosed ? "closed" : "open");
        telemetry.addData("Arm mode: ", creepMode ? "Creepmode" : "Normalmode");
        telemetry.addData("Arm angle: ", armAngle);
        telemetry.addData("liftSTAT: ", liftStatus);
        telemetry.addData("Gravty come: ", gravityComp);
    }

    private void HomingMode() {
        if (retractSwitchClosed) {
            armIdle();
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            SetState(State.MANUAL);
        }
        else {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setPower(HOME_SPEED);
        }
    }

    private void ManualMode() {
        x.Update();
        y.Update();
        a.Update();
        if (a.Press()){
            creepMode = !creepMode;
        }
        float stickDeflection = -gamePad.right_stick_y;
        //float speed = 0.2f * stickDeflection;

        float speed = 0.0f;
        if (Math.abs(stickDeflection) > 0.02f) {
            speed = 0.24f * Math.signum(stickDeflection);
        }

        float minSpeed = 0.01f;
        if (Math.abs(speed) > minSpeed) {
            if (speed > 0)
                armExtend(-speed);
            else
                armRetract(speed);
        }
        else
            armIdle();

        /*
        if(!x.IsDown() && !y.IsDown()){
            armIdle();
        }else if(x.IsDown()){
            armRetract(HOME_SPEED);

        }else if(y.IsDown()){
            armExtend(HOME_SPEED);
        }
        */
    }

    private void AutoMode() {
    }

    private void armIdle(){
        motor.setPower(0.0);
        liftStatus = "Idle";
    }

    private void armExtend(float speed){
        boolean noSwitch = false; // for testing
        gravityComp = String.format("%.2f", K_COMPENSATE*Math.sin((double)encoderCountsToAngle(relativeEncoder)));
        if (noSwitch || !extendSwitchClosed)
            //motor.setPower(speed+(ARM_WEIGHT*encoderCountsToAngle(relativeEncoder)));
            motor.setPower(speed-(K_COMPENSATE*Math.sin((double)encoderCountsToAngle(relativeEncoder))));
        else
            motor.setPower(0.0);
        liftStatus = "Extend";
    }

    private void armRetract(float speed){
        //swithch is on Rev controller 2, port D01
        boolean noSwitch = false; // for testing
        gravityComp = String.format("%.2f", K_COMPENSATE*Math.sin((double)encoderCountsToAngle(relativeEncoder)));
        if (noSwitch || !retractSwitchClosed)
            //motor.setPower(-speed+(ARM_WEIGHT*encoderCountsToAngle(relativeEncoder)));
            motor.setPower(-speed-(K_COMPENSATE*Math.sin((double)encoderCountsToAngle(relativeEncoder))));
        else
            motor.setPower(0.0); // when not testing this needs to be 0.0
        liftStatus = "Retract";
    }

    private float encoderCountsToAngle(int encoderpos){
        float angle = (float) (((encoderpos-VERTICAL_OFFSET)*RAD_PER_TICK));

        return angle;
    }
}
