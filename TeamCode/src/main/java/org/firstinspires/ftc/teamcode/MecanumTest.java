package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.modules.GameButton;
//import org.firstinspires.ftc.teamcode.modules.LineFollower;

/**
 * Created by greenteam on 1/12/17.
 * Main Drive Opmode
 * */

@TeleOp(name = "MecanumTest", group = "Drive")
public class MecanumTest extends OpMode {

    private DcMotor      motor1;
    private DcMotor      motor2;
    private DcMotor      motor3;
    private DcMotor      motor4;
    private GameButton   buttonA;
    private GameButton   buttonB;
    private GameButton   buttonX;

    int currentButton = 0;
    private Float        joyDeflection;
    private int          MOTORCOUNT = 4;
    private float        power[] = new float[MOTORCOUNT];

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


        msg = "released";
    }

    public void motorReset(){
        for (int i = 0; i < MOTORCOUNT; i++)
        {
            power[i] = 0.0f;
        }
    }

    @Override
    public void loop() {
        buttonA.Update();
        buttonB.Update();
        buttonX.Update();

        joyDeflection = -gamepad1.left_stick_y;

        if (buttonA.Press()) {
            currentButton++;
            if (currentButton > MOTORCOUNT - 1)
                currentButton = 0;
        }

        if (buttonB.Press())
        {
            power[currentButton] = joyDeflection;
        }

        if (buttonX.IsDown())
        {
            motor1.setPower(power[0]);
            motor2.setPower(power[1]);
            motor3.setPower(power[2]);
            motor4.setPower(power[3]);
        }
        else
        {
            motor1.setPower(0);
            motor2.setPower(0);
            motor3.setPower(0);
            motor4.setPower(0);
        }

        telemetry.addData("Motor#: ", String.format("%d", currentButton));
        telemetry.addData("JoyDeflection: ", String.format("%1.2f", joyDeflection));
        telemetry.addData("Power 1: ", String.format("%1.2f", power[0]));
        telemetry.addData("Power 2: ", String.format("%1.2f", power[1]));
        telemetry.addData("Power 3: ", String.format("%1.2f", power[2]));
        telemetry.addData("Power 4: ", String.format("%1.2f", power[3]));
    }
}
