package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.library.multimotors.MultiDcMotor;

public class Launcher {
    // Intake
    private DcMotor launcher1;
    private DcMotor launcher2;

    private MultiDcMotor launcherMotors;

    private final static double TOP = 0.9;
    private final static double MIDDLE = 0.8;
    private final static double BOTTOM = 0.7;
    private final static double POWER = 1.0;

    public void init(HardwareMap hardwareMap) {
        // Init intake motors
        launcher1 = hardwareMap.get(DcMotor.class, "l1");
        launcher2 = hardwareMap.get(DcMotor.class, "l2");

        launcher1.setDirection(DcMotorSimple.Direction.FORWARD);
        launcher2.setDirection(DcMotorSimple.Direction.REVERSE);

        launcherMotors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launcherMotors.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        launcherMotors = new MultiDcMotor(launcher1, launcher2);

        launcherMotors.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    public void reverse() {
        launcherMotors.setPower(-1);
    }

    public void top() { launcherMotors.setPower(TOP); }

    public void middle() {
        launcherMotors.setPower(MIDDLE);
    }

    public void bottom() {
        launcherMotors.setPower(BOTTOM);
    }

    public void power() {
        launcherMotors.setPower(POWER);
    }

    public void stop() {
        launcherMotors.setPower(0);
    }
}
