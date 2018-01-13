package org.firstinspires.ftc.teamcode.modules;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.robotcore.external.Telemetry;

//import org.firstinspires.ftc.teamcode.modules.LineFollower;

/**
 * Created by greenteam on 1/12/17.
 * Main Drive Opmode
 * */


public class MecanumDrive {

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
    private Gamepad gamepad;

    private int          MOTORCOUNT = 4;
    private double        power[] = new double[MOTORCOUNT];

    public void setup(DcMotor m1, DcMotor m2, DcMotor m3, DcMotor m4 , Gamepad aGamepad){
        motor1 = m1;
        motor2 = m2;
        motor3 = m3;
        motor4 = m4;

        gamepad = aGamepad;

        buttonA      = new GameButton(gamepad, GameButton.Label.a);
        buttonB      = new GameButton(gamepad, GameButton.Label.b);
        buttonX      = new GameButton(gamepad, GameButton.Label.x);
        solver = new MecanumSolver();
    }

    public void update(Telemetry telemetry) {
        buttonA.Update();
        buttonB.Update();
        buttonX.Update();
        power[0] = 0;
        power[1] = 0;
        power[2] = 0;
        power[3] = 0;

        yJoyVal = gamepad.left_stick_y; //+y is toward the user, -y is push away from user
        xJoyVal = gamepad.left_stick_x;

        //if the trigger buttons are on, then we spin in stead of drive.
        //to be added.

        Matrix W = solver.solve(-yJoyVal, -xJoyVal, 0);

        power[0] = W.element(0,0);
        power[1] = W.element(1,0);
        power[2] = W.element(2,0);
        power[3] = W.element(3,0);

        motor1.setPower(power[0]);
        motor2.setPower(power[1]);
        motor3.setPower(power[2]);
        motor4.setPower(power[3]);

        //telemetry.addData("Motor#: ", String.format("%d", currentButton));
        /*  telemetry.addData("yJoy: ", String.format("%1.2f", yJoyVal));
        telemetry.addData("xJoy: ", String.format("%1.2f", xJoyVal));
        telemetry.addData("Power 1: ", String.format("%1.2f", power[0]));
        telemetry.addData("Power 2: ", String.format("%1.2f", power[1]));
        telemetry.addData("Power 3: ", String.format("%1.2f", power[2]));
        telemetry.addData("Power 4: ", String.format("%1.2f", power[3]));
        */
    }
}
