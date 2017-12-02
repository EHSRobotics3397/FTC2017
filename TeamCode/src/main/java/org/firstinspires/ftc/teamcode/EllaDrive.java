package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;


/**
 * Created by greenteam on 11/18/17.
 */
@TeleOp(name = "EllaDrive", group = "Drive")
public class EllaDrive extends OpMode {
    private DcMotor      motor1;
    private GameStick   left;
    @Override
    public void init(){

        motor1       = hardwareMap.dcMotor.get("motor1");
        left        = new GameStick(gamepad1, GameStick.Label.Left);

    }

    @Override
    public void loop(){
        float speed = -left.y();
        motor1.setPower(speed);
    }
}
