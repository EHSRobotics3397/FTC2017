package org.firstinspires.ftc.teamcode.modules;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

/**
 * Created by greenteam on 1/27/18.
 */

public class ColorSensing {
    private ColorSensor sensorColor;
    private DistanceSensor sensorDistance;

    public void setup(ColorSensor aSensor, DistanceSensor aDistanceSensor)
    {
        sensorColor = aSensor;
        sensorDistance = aDistanceSensor;
    }

    public void update(Telemetry telemetry)
    {
        final double SCALE_FACTOR = 255;
        float hsvValues[] = {0F, 0F, 0F};

        int alph = (int)(sensorColor.alpha() * SCALE_FACTOR);
        int red = (int)(sensorColor.red() * SCALE_FACTOR);
        int green = (int)(sensorColor.green() * SCALE_FACTOR);
        int blue = (int)(sensorColor.blue() * SCALE_FACTOR);

        Color.RGBToHSV((red), (green), (blue), hsvValues);

        telemetry.addData("Distance (cm)",
                String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));
        telemetry.addData("Alpha", alph);
        telemetry.addData("Red  ", red);
        telemetry.addData("Green", green);
        telemetry.addData("Blue ", blue);
        telemetry.addData("Hue", hsvValues[0]);

    }
}
