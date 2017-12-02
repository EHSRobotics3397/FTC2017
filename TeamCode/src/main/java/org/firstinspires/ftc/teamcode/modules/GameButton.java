package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by greenteam on 12/10/16.
 */

public class GameButton  {

    private boolean state;
    private boolean lastState;
    private boolean press;
    private float analogValue;
    private boolean release;
    private Gamepad pad;

    public enum Label{
        a,b,x,y,
        RBumper, LBumper,
        dpadUp, dpadDown, dpadLeft, dpadRight,
        analogLeft, analogRight, RTrigger, LTrigger
    };

    Label buttonLabel;

    public GameButton(Gamepad somePad, Label someLabel) {
        pad         = somePad;
        buttonLabel = someLabel;
        state       = false;
        lastState   = false;
        analogValue = 0.0f;
    }

    public void Update() {

        if (buttonLabel == Label.a){
            state = pad.a;
        }
        else if (buttonLabel == Label.b){
            state = pad.b;
        }
        else if (buttonLabel == Label.x){
            state = pad.x;
        }
        else if (buttonLabel == Label.y){
            state = pad.y;
        }
        else if (buttonLabel == Label.RBumper) {
            state = pad.right_bumper;
        }
        else if (buttonLabel == Label.LBumper) {
            state = pad.left_bumper;
        }
        else if (buttonLabel == Label.dpadRight) {
            state = pad.dpad_right;
        }
        else if (buttonLabel == Label.dpadLeft) {
            state = pad.dpad_left;
        }
        else if (buttonLabel == Label.dpadUp) {
            state = pad.dpad_up;
        }
        else if (buttonLabel == Label.dpadDown) {
            state = pad.dpad_down;
        }
        else if (buttonLabel == Label.analogRight){
            analogValue = pad.right_stick_y;
        }
        else if (buttonLabel == Label.analogLeft){
            analogValue = pad.left_stick_x;
        }
        else if (buttonLabel == Label.LTrigger){
            analogValue = pad.left_trigger;
        }
        else if (buttonLabel == Label.RTrigger){
            analogValue = pad.right_trigger;
        }

        press     = state && !lastState;
        release   = !state && lastState;
        lastState = state;
    }


    public boolean Press() {
        return press;
    }

    public boolean Release() {
        return release;
    }

    public boolean IsDown() { return state;}

    public boolean IsUp() { return !state;}

    public float analogRead(){return analogValue;}
}
