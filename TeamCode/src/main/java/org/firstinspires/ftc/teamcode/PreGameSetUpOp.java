package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import java.lang.Thread;

import org.firstinspires.ftc.teamcode.modules.Arm;
import org.firstinspires.ftc.teamcode.modules.GameButton;
import org.firstinspires.ftc.teamcode.modules.Gripper;


//import org.firstinspires.ftc.teamcode.modules.ColorSensing;

//import org.firstinspires.ftc.teamcode.modules.LineFollower;



@TeleOp(name = "PreGameSetUpOp", group = "Drive")
public class PreGameSetUpOp extends OpMode {

    private DcMotor      motorLift;

    private Servo        servoLeft;
    private Servo        servoRight;

    private DigitalChannel armLimitSwitch1;
    private DigitalChannel armLimitSwitch2;

    private Arm liftArm;
    private Gripper gripper;

    @Override
    public void init(){

        liftArm = new Arm();
        gripper = new Gripper();

        motorLift    = hardwareMap.dcMotor.get("motorLift");

        servoLeft    = hardwareMap.servo.get("servoLeft");
        servoRight   = hardwareMap.servo.get("servoRight");
        armLimitSwitch1 = hardwareMap.digitalChannel.get("armLimitSwitch1");
        armLimitSwitch1.setMode(DigitalChannel.Mode.INPUT);
        armLimitSwitch2 = hardwareMap.digitalChannel.get("armLimitSwitch2");
        armLimitSwitch2.setMode(DigitalChannel.Mode.INPUT);

        gripper.setup(servoLeft, servoRight, gamepad2);
        liftArm.setup(motorLift, armLimitSwitch1, armLimitSwitch2, gamepad2);

        gripper.goHome();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {

        }
        liftArm.SetState(Arm.State.HOMING);
    }

    @Override
    public void loop() {
        liftArm.update(telemetry);
        gripper.update(telemetry);

    }
}
