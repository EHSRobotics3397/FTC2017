package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.lang.Thread;

/**
 * Created by greenteam on 2/17/18.
 */

public class IMURotate {
    BNO055IMU               imu;
    Position                globalPosition;
    Orientation             lastAngles = new Orientation();
    double                  globalAngle, correction;
    boolean                 aButton, bButton, touched;

    public void setup(Servo aRightServo, Gamepad pad){

    }

    public double currentAngle(){
        
        return 0.0;
    }

    public void update(Telemetry telemetry) throws InterruptedException {

    }

    /**
     * Resets the cumulative angle tracking to zero.
     */
    private void ResetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        globalAngle = 0;
    }

    /**
     * Get current cumulative angle rotation from last reset.
     * @return Angle in degrees. + = left, - = right.
     */
    private void UpdateGlobalAngle()
    {
        //global angle is updated by the change in angle.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        //handle roll through +180, -180
        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;
    }

    private void UpdateGlobalPosition() {
        globalPosition = imu.getPosition();
    }

}


