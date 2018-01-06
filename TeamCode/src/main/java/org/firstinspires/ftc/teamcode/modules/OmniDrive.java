package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.GameStick;

/*
 * Class for Manual Car Drive
 *
 * Manages main drive functions
 *
 * Pass in 2 DcMotors, Gamepad
 */

public class OmniDrive {

	private DcMotor 	motor1;
	private DcMotor 	motor2;
    private DcMotor 	motor3;
    private DcMotor 	motor4;
    private GameStick   left;
    private GameStick   right;


    private Gamepad gamepad;
    //private

    public void init(DcMotor m1, DcMotor m2, DcMotor m3, DcMotor m4, Gamepad aGamepad)
    {

        motor1 = m1;
        motor2 = m2;
        motor3 = m3;
        motor4 = m4;
        gamepad = aGamepad;


        left        = new GameStick(gamepad, GameStick.Label.Left);
        right       = new GameStick(gamepad, GameStick.Label.Right);
        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor2.setDirection(DcMotor.Direction.REVERSE);

    }

    public void update(Telemetry telemetry)
    {
        double sx= right.x();
        double sy=-right.y();
        double mot1steer;
        double mot2steer;
        double mot3steer;
        double mot4steer;
        final double MINTHRUST = 0.1;
        double thrust = 0;
        final double MINJOY = 0.1;
        double direction = directioncalc(sx,sy); // note, -180 to 180
        double directionindegrees = direction *180/ Math.PI;
        thrust = Math.sqrt(sx*sx+sy*sy);
        double oct = -1;

        /*
        int whichangle = -1;

        thrust = thrustcalc(sx,sy);
        if (thrust > minthrust) {
            double currentsmallestanglediff = 360;
            for (int i = 0; i<360; i+= 45){
                double anglediffrence = Math.abs(i-directionindegrees);
                if (anglediffrence<currentsmallestanglediff){
                    whichangle = i/45;
                    currentsmallestanglediff = anglediffrence;
                }
            }

            switch () {
                case 1:
            }
            */
            //two approaches above (loop, probably better) and below (ifelse tree)
        mot1steer = 0;
        mot2steer = 0;
        mot3steer = 0;
        mot4steer = 0;


        if (thrust > MINTHRUST) {

            if (directionindegrees > -22.5 && directionindegrees <= 22.5) { // oct 1
                mot1steer = -1;
                mot2steer = 1;
                mot3steer = -1;
                mot4steer = 1;
                oct = 1;
            }
            if (directionindegrees > 22.5 && directionindegrees <= 67.5) {
                mot1steer = 0;
                mot2steer = 1;
                mot3steer = 0;
                mot4steer = 1;
                oct = 2;
            }
            if (directionindegrees > 67.5 && directionindegrees <= 112.5) { // straigt forward (oct 3)
                mot1steer = 1;
                mot2steer = 1;
                mot3steer = 1;
                mot4steer = 1;
                oct = 3;
            }
            if (directionindegrees > 112.5 && directionindegrees <= 157.5) {
                mot1steer = 1;
                mot2steer = 0;
                mot3steer = 1;
                mot4steer = 0;
                oct = 4;
            }
            if (directionindegrees > 157.5 || directionindegrees <= -157.5) { // oct 5
                mot1steer = 1;
                mot2steer = -1;
                mot3steer = 1;
                mot4steer = -1;
                oct = 5;
            }
            if (directionindegrees > -157.5 && directionindegrees <= -112.5) {
                mot1steer = 0;
                mot2steer = -1;
                mot3steer = 0;
                mot4steer = -1;
                oct = 6;
            }
            if (directionindegrees > -112.5 && directionindegrees <= -67.6) {
                mot1steer = -1;
                mot2steer = -1;
                mot3steer = -1;
                mot4steer = -1;
                oct = 7;
            }
            if (directionindegrees > -67.5 && directionindegrees <= -22.5) {
                mot1steer = -1;
                mot2steer = 0;
                mot3steer = -1;
                mot4steer = 0;
                oct = 8;
            }
        }




        motor1.setPower(thrust * mot1steer);
        motor2.setPower(thrust * mot2steer);
        motor3.setPower(thrust * mot3steer);
        motor4.setPower(thrust * mot4steer);

        telemetry.addData("Power 1: ", String.format("%.2f", mot1steer*thrust));
        telemetry.addData("Power 2: ", String.format("%.2f", mot2steer*thrust));
        telemetry.addData("Power 3: ", String.format("%.2f", mot3steer*thrust));
        telemetry.addData("Power 4: ", String.format("%.2f", mot4steer*thrust));
        //telemetry.addData("thrust ", String.format("%.2f", thrust));
        telemetry.addData("octant: ", String.format("%.2f", oct));
        telemetry.addData("steering angle: ", String.format("%.2f", directionindegrees));

    }

    public double directioncalc (double x, double y){
        return Math.atan2(y,x);
    }

    public double thrustcalc(double x, double y){
        return Math.sqrt(x*x + y+y);
    }
}

/* this verson is less effecent but probobaly works
if (directionindegrees >337.5 && directionindegrees <= 22.5){ // oct 1
                mot1steer = -1;
                mot2steer = 1;
                mot3steer = -1;
                mot4steer = 1;
            }
            if (directionindegrees >22.5 && directionindegrees <= 67.5){
                mot1steer = 0;
                mot2steer = 1;
                mot3steer = 0;
                mot4steer = 1;
            }
            if (directionindegrees >67.5 && directionindegrees <= 112.5){ // straigt forward (oct 3)
                mot1steer = 1;
                mot2steer = 1;
                mot3steer = 1;
                mot4steer = 1;
            }
            if (directionindegrees >112.5 && directionindegrees <= 157.5){
                mot1steer = 1;
                mot2steer = 0;
                mot3steer = 1;
                mot4steer = 0;
            }
            if (directionindegrees >157.5 && directionindegrees <= 202.5){ // oct 5
                mot1steer = 1;
                mot2steer = -1;
                mot3steer = 1;
                mot4steer = -1;
            }
            if (directionindegrees >202.5 && directionindegrees <= 247.5){
                mot1steer = 0;
                mot2steer = -1;
                mot3steer = 0;
                mot4steer = -1;
            }
            if (directionindegrees >247.5 && directionindegrees <= 292.5){
                mot1steer = -1;
                mot2steer = -1;
                mot3steer = -1;
                mot4steer = -1;
            }
            if (directionindegrees >292.5 && directionindegrees <= 337.5){
                mot1steer = -1;
                mot2steer = 0;
                mot3steer = -1;
                mot4steer = 0;
            }
 */