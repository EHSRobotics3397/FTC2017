package org.firstinspires.ftc.teamcode.modules;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.GameStick;

/**
 * Created by greenteam on 2/2/18.
 */

public class RelicExtender {
    private Gamepad gamepad;
    private GameButton  exx;
    private GameButton  whyy;
    private DcMotor 	aimMotor;
    private DcMotor 	extendMotor;
    private GameStick leftStick;

    public void setup(DcMotor aimM, DcMotor extM, Gamepad aGamepad)
    {

        aimMotor = aimM;
        aimMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        extendMotor = extM;
        gamepad = aGamepad;
        exx = new GameButton(aGamepad, GameButton.Label.x);
        whyy = new GameButton(aGamepad, GameButton.Label.y);
        leftStick = new GameStick(gamepad, GameStick.Label.Left);

    }

    public void update(Telemetry telemetry)
    {
        whyy.Update();
        exx.Update();
        float aimThrust = 0;
        float extendThrust = 0;
        final float MINTRIGGER = 0.01f;
        float sy =-leftStick.y();
        final float MINJOY = 0.1f;
        final float THRUSTFACTOR = 0.6f;

        if (gamepad.left_trigger > MINTRIGGER){
            aimThrust = gamepad.left_trigger;
        }

        if(gamepad.right_trigger > MINTRIGGER){
            aimThrust = -gamepad.right_trigger;
        }

        if (sy > MINJOY || sy < -MINJOY){
             extendThrust = sy;
        }

        aimMotor.setPower(aimThrust * THRUSTFACTOR);
        extendMotor.setPower(extendThrust * THRUSTFACTOR);
    }
}
