package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.OmniDrive;
import org.firstinspires.ftc.teamcode.modules.OmniSpin;

//import org.firstinspires.ftc.teamcode.modules.LineFollower;

/**
 * Created by greenteam on 1/12/17.
 * Main Drive Opmode
 * */

@TeleOp(name = "OmniSpin", group = "Drive")
public class OmniOp2 extends OpMode {

    private DcMotor      motor1;
    private DcMotor      motor2;
    private DcMotor      motor3;
    private DcMotor      motor4;

    private OmniSpin omniSpin;
    //private MotorTest motorTest;

    @Override
    public void init(){

        motor1       = hardwareMap.dcMotor.get("motor1");
        motor2       = hardwareMap.dcMotor.get("motor2");
        motor3       = hardwareMap.dcMotor.get("motor3");
        motor4       = hardwareMap.dcMotor.get("motor4");

        omniSpin  = new OmniSpin();
        omniSpin.setup(motor1, motor2, motor3, motor4, gamepad1);
        //motorTest  = new MotorTest();
        //motorTest.setup(motor1, motor2, motor3, motor4, gamepad1);

    }

    @Override
    public void loop() {
        omniSpin.update(telemetry);
    }
}
