package org.firstinspires.ftc.teamcode.modules;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.GameStick;

/*
 * Class for Manual Car Drive
 *
 * Manages main drive functions
 *
 * Pass in 2 DcMotors, Gamepad
 */

public class MotorTest {

	private DcMotor 	motor1;
	private DcMotor 	motor2;
    private DcMotor 	motor3;
    private DcMotor 	motor4;
    private DcMotor     activeMotor;
    private GameStick   left;
    private GameStick   right;
    private GameButton  motorSwitchButton;
    private int activeMotorNum;

    public void setup(DcMotor m1, DcMotor m2, DcMotor m3, DcMotor m4, Gamepad gamepad)
        {
            motorSwitchButton = new GameButton(gamepad, GameButton.Label.a);
            motor1 = m1;
            motor2 = m2;
            motor3 = m3;
            motor4 = m4;
            left        = new GameStick(gamepad, GameStick.Label.Left);
            right       = new GameStick(gamepad, GameStick.Label.Right);
            activeMotor = motor1;
            activeMotorNum = 1;
            motor1.setDirection(DcMotor.Direction.REVERSE);
            motor2.setDirection(DcMotor.Direction.REVERSE);

//        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
// int motorTestNum = 0;
	    }

    public void update(Telemetry telemetry)
    {
        motorSwitchButton.Update();
        float speed = -left.y();
        activeMotor.setPower(speed);

        if(motorSwitchButton.Press()) {
            activeMotorNum++;
            if (activeMotorNum > 4)
                activeMotorNum = 1;
            switch (activeMotorNum)
            {
                case 1 :
                    activeMotor = motor1;
                    break;
                case 2 :
                    activeMotor = motor2;
                    break;
                case 3 :
                    activeMotor = motor3;
                    break;
                case 4 :
                    activeMotor = motor4;
                    break;
                default :
                    activeMotor = motor1;
            }
        }



    }
/*
	public void update(Telemetry telemetry)
    {
		double scaleFactor    = 1.0;
		double spinThreshold  = 0.2;

		double rightSteer     = 1.0 - (right.x() + 1.0) / 2.0;
        double leftSteer      = (right.x() + 1.0) / 2.0;
		double thrust         = -left.y();

        double motorPower;
        double leftPower;
        if(Math.abs(thrust) > spinThreshold)
        {

			rightPower  = thrust * rightSteer / scaleFactor;
			leftPower   = thrust * leftSteer  / scaleFactor;

        }
        else
        {
            rightPower  = -right.x() / scaleFactor;
			leftPower   = right.x()  / scaleFactor;
        }

        leftPower   = Range.clip(leftPower, -1, 1);
        rightPower  = Range.clip(rightPower, -1, 1);

        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);

        int leftEncoder     = leftMotor.getCurrentPosition();
        int rightEncoder    = rightMotor.getCurrentPosition();

        telemetry.addData("Power L: ", String.format("%.2f", leftPower));
        telemetry.addData("Power R: ", String.format("%.2f", rightPower));

        telemetry.addData("Drive Encoder L: ", String.format("%d", leftEncoder) + "  R: " + String.format("%d", rightEncoder));
	}
	*/
}

		
