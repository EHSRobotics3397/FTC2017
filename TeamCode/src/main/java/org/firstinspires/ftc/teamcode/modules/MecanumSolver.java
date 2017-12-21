package org.firstinspires.ftc.teamcode.modules;/*
 * Computes individual motor speed based
 * on desired linear and rotational velocities
 */

public class MecanumSolver {
    private Matrix R;
    private Matrix V;
    private Matrix W;
    private Matrix Wscaled;

    private double [][] _v = {{0, 0, 0}};

    //motor coordinates
    double x1,x2,x3,x4,y1,y2,y3,y4;

    double wheelRadius = 1.0;

    public MecanumSolver() {
        x1= 1; y1=1;
        x2=-1; y2=1;
        x3=-1; y3=-1;
        x4= 1; y4=-1;

        double[][] _r = {
            {1, -1, -x1-y1},
            {1,  1,  x1-y1},
            {1, -1, -x3-y3},
            {1,  1,  x4-y4}};
        R = new Matrix(_r);
        V = new Matrix(_v);
    }

    /*
     * Compute matrix W, which contains the motor speeds.
     * vx = linear velocity X direction (forward)
     * vy = linear velocity Y direction (left)
     * w = rotational velocity of robot (counter clockwise, in radians)
     */
    public Matrix solve(double vx, double vy, double w) {
        _v[0][0] = vx;
        _v[0][1] = vy;
        _v[0][2] = w;

        V = new Matrix(_v);

        W = R.times(V.transpose());
        W = W.times(1/wheelRadius);

	double scale = W.maxAbs();
        Matrix Wscaled = W;
        if(scale!=0){
            Wscaled.times(1.0/scale);
        }

	
        return Wscaled;
    }
}
