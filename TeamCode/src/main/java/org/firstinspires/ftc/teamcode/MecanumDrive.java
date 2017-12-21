package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.GameButton;
import org.firstinspires.ftc.teamcode.modules.Matrix;
import org.firstinspires.ftc.teamcode.modules.MecanumSolver;

//import org.firstinspires.ftc.teamcode.modules.LineFollower;

/**
 * Created by greenteam on 1/12/17.
 * Main Drive Opmode
 * */

@TeleOp(name = "MecanumDrive", group = "Drive")
public class MecanumDrive extends OpMode {

    private DcMotor      motor1;
    private DcMotor      motor2;
    private DcMotor      motor3;
    private DcMotor      motor4;
    private GameButton   buttonA;
    private GameButton   buttonB;
    private GameButton   buttonX;
    private MecanumSolver solver;
    private double yJoyVal;
    private double xJoyVal;
    //int currentButton = 0;

    private int          MOTORCOUNT = 4;
    private double        power[] = new double[MOTORCOUNT];

    private String msg;

    @Override
    public void init(){

        motor1       = hardwareMap.dcMotor.get("motor1");
        motor2       = hardwareMap.dcMotor.get("motor2");
        motor3       = hardwareMap.dcMotor.get("motor3");
        motor4       = hardwareMap.dcMotor.get("motor4");

        motor3.setDirection(DcMotor.Direction.REVERSE);
        motor4.setDirection(DcMotor.Direction.REVERSE);

        buttonA      = new GameButton(gamepad1, GameButton.Label.a);
        buttonB      = new GameButton(gamepad1, GameButton.Label.b);
        buttonX      = new GameButton(gamepad1, GameButton.Label.x);

        solver = new MecanumSolver();

        msg = "released";
    }

    @Override
    public void loop() {
        buttonA.Update();
        buttonB.Update();
        buttonX.Update();
        power[0] = 0;
        power[1] = 0;
        power[2] = 0;
        power[3] = 0;


        yJoyVal = -gamepad1.left_stick_y;
        xJoyVal = gamepad1.left_stick_x;


        Matrix W = solver.solve(yJoyVal, -xJoyVal, 0);

        power[0] = W.element(0,0);
        power[1] = W.element(1,0);
        power[2] = W.element(2,0);
        power[3] = W.element(3,0);


        motor1.setPower(power[0]);
        motor2.setPower(power[1]);
        motor3.setPower(power[2]);
        motor4.setPower(power[3]);


        //telemetry.addData("Motor#: ", String.format("%d", currentButton));
        telemetry.addData("yJoy: ", String.format("%1.2f", yJoyVal));
        telemetry.addData("xJoy: ", String.format("%1.2f", xJoyVal));
        telemetry.addData("Power 1: ", String.format("%1.2f", power[0]));
        telemetry.addData("Power 2: ", String.format("%1.2f", power[1]));
        telemetry.addData("Power 3: ", String.format("%1.2f", power[2]));
        telemetry.addData("Power 4: ", String.format("%1.2f", power[3]));
    }
}
