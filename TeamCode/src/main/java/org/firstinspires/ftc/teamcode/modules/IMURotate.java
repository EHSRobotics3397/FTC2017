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
import java.util.Locale;

/**
 * Created by greenteam on 2/17/18.
 */

public class IMURotate {
    BNO055IMU               imu;
    Position                globalPosition;
    Orientation             lastAngles = new Orientation();
    double                  globalAngle, correction;
    boolean                 aButton, bButton, touched;

    public void setup(BNO055IMU anImu, Telemetry telemetry){
        imu = anImu;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".

        telemetry.update();

        imu.initialize(parameters);

        // make sure the imu gyro and accerometer are calibrated before continuing.
        while(  !imu.isGyroCalibrated() &&
                !imu.isAccelerometerCalibrated())
        {
            try {
                Thread.sleep(50);
                Thread.yield();
            }
            catch (InterruptedException e) {}

        }

        telemetry.addData("Mode", "waiting for start");
        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        telemetry.update();
    }

    public double getAngle(){
        return globalAngle;
    }

    public void update(Telemetry telemetry) {
        UpdateGlobalAngle();
        UpdateGlobalPosition();

        /*


        telemetry.addData("IMU heading", lastAngles.firstAngle);
        telemetry.addData("Global heading", globalAngle);
        String sPosition = String.format(Locale.US, "(%3.2f, %3.2f, %3.2f)", globalPosition.x, globalPosition.y, globalPosition.z);
        telemetry.addData("Position: ", sPosition);
        */
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


