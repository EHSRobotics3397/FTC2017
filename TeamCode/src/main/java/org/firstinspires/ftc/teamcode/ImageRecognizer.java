/**
 * Vuforia - Image Recognition
 * Created by Green Team - Feb 3, 2018
 *
 * Detect VU Marks
 * Should be converted into a Module for use with other systems.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

@TeleOp(name = "ImageRecognition", group = "Vision")
public class ImageRecognizer extends LinearOpMode {

    VuforiaLocalizer vuforiaLocalizer;
    VuforiaLocalizer.Parameters parameters;
    VuforiaTrackables visionTargets;

    public enum ImageID  {
        LEFT(0), CENTER(1), RIGHT(2), NONE(3);
        private final int value;
        String[] names = new String[] {"LEFT", "CENTER", "RIGHT", "NONE"};
        private ImageID(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }

        public String getName() {
            return names[value];
        }
    }

    //String[] ImageNames =

    //this key belongs to Essex Robotics
    public static final String VUFORIA_KEY = "ARDXCjL/////AAAAGXuBMxMI5EhrvrvaZoqpzmpfBmB1WDZJn56wtltNERZooZAfHDBUmdq10DYuq/f7VYSyV7pEtBGzGANIJJgM+ci+Kc/GrLyuKzoHPdV6VJAozfHadE2vFpBl5HnYUotKhCTC6ocsnEFZ9M1WaFh2KKSXXLOnQiPRWgYTq4o+KcUaY5Ki9BtcbnjSodBmcmW4lS/Qz6qfgdlHA/Dhm/XtLgtW7OhUwxyPg0i3ZKsQ8FyWNGkFZxd7yvo3p+AtzHb86o6hueWdP1mn1jZlcrp5IQILYBc4h11bngmmtUQ8EMsvaTLIDPivzSNjbrnlJ5WZNa2di5mr8tdTgByxmM3cadzS0U9VKG5KB6TCq2pcJvE9";

    //simply checks for recognition of image A,B,C
    public void runOpMode() throws InterruptedException {

        setupVuforia();
        waitForStart();

        VuforiaTrackable templateLeft, templateCenter, templateRight, relicTemplate;

        try {
            visionTargets.activate();
            relicTemplate = visionTargets.get(0);

            while(opModeIsActive()) {

                RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
                ImageID detectedImage;
                if (vuMark == RelicRecoveryVuMark.LEFT) {
                    detectedImage = ImageID.LEFT;
                }
                else if (vuMark == RelicRecoveryVuMark.CENTER) {
                    detectedImage = ImageID.CENTER;
                }
                else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                    detectedImage = ImageID.RIGHT;
                }
                else {
                    detectedImage = ImageID.NONE;
                }

                String imageName = detectedImage.getName();
                telemetry.addData("Image Detected: ", imageName);
                telemetry.update();
                idle();
            }

        }
        catch (Exception ex){
            StackTraceElement[] trace = ex.getStackTrace();

            String s0 = ex.getMessage() + ": " + trace[0].getMethodName() + trace[0].getLineNumber();
            telemetry.addData("Exception: ", s0);
            telemetry.update();
        }
    }

    private void setupVuforia() {
        //remove R.id.cameraMonitorViewId) to turn off display - saves batter power for robot controller phone
        parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);

        //can choose the back camera or the front camera on the phone
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforiaLocalizer = ClassFactory.createVuforiaLocalizer(parameters);

        //only loads 1 trackable with ability to find 3 embedded images
        visionTargets = vuforiaLocalizer.loadTrackablesFromAsset("RelicVuMark"); //2017-18
    }
}
