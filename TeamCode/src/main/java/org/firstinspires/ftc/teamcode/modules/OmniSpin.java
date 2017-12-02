package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.GameStick;

/*
 * Class for Omnidrive with only spinning
 *
 * Manages main drive functions
 *
 * Pass in 2 DcMotors, Gamepad
 */

public class OmniSpin {

	private DcMotor 	motor1;
	private DcMotor 	motor2;
    private DcMotor 	motor3;
    private DcMotor 	motor4;
    private Gamepad gamepad;

    public void setup(DcMotor m1, DcMotor m2, DcMotor m3, DcMotor m4, Gamepad aGamepad)
    {
        motor1 = m1;
        motor2 = m2;
        motor3 = m3;
        motor4 = m4;
        gamepad = aGamepad;

        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor2.setDirection(DcMotor.Direction.REVERSE);
    }

    public void update(Telemetry telemetry)
    {
        double mot1steer = 0.0;
        double mot2steer = 0.0;
        double mot3steer = 0.0;
        double mot4steer = 0.0;

        double thrust = 0;
        final float MINTRIGGER = 0.01f;

        if (gamepad.left_trigger > MINTRIGGER)
        {
            thrust = gamepad.left_trigger;
            mot1steer=1;
            mot2steer=1;
            mot3steer=-1;
            mot4steer=-1;
        }
        else if (gamepad.right_trigger > MINTRIGGER)
        {
            thrust = gamepad.right_trigger;
            mot1steer=-1;
            mot2steer=-1;
            mot3steer=1;
            mot4steer=1;
        }

        motor1.setPower(thrust * mot1steer);
        motor2.setPower(thrust * mot2steer);
        motor3.setPower(thrust * mot3steer);
        motor4.setPower(thrust * mot4steer);

        telemetry.addData("Power 1: ", String.format("%.2f", mot1steer*thrust));
        telemetry.addData("Power 2: ", String.format("%.2f", mot2steer*thrust));
        telemetry.addData("Power 3: ", String.format("%.2f", mot3steer*thrust));
        telemetry.addData("Power 4: ", String.format("%.2f", mot4steer*thrust));
    }
}
