package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.core.Kevin;
import org.firstinspires.ftc.teamcode.library.multimotors.MultiDcMotor;


public class Launcher {
    // Launcher
    private DcMotor launcher1;
    private DcMotor launcher2;
    private CRServo mover;
    private MultiDcMotor launcherMotors;

    private final static double POWER = 1.0;
    private final static int TIME = 2000;


    public void init(HardwareMap hardwareMap) {
        // Init intake motors
        launcher1 = hardwareMap.get(DcMotor.class, "l1");
        launcher2 = hardwareMap.get(DcMotor.class, "l2");
        mover = hardwareMap.get(CRServo.class, "m");


        launcher1.setDirection(DcMotorSimple.Direction.REVERSE);
        launcher2.setDirection(DcMotorSimple.Direction.FORWARD);

        launcherMotors = new MultiDcMotor(launcher1, launcher2);

        launcherMotors.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void power(double power) {
        launcherMotors.setPower(power);
        Kevin.sleep(1000);
        mover.setPower(1);
        Kevin.sleep(TIME);
        mover.setPower(-1);
        Kevin.sleep(TIME);
        mover.setPower(0);
        launcherMotors.setPower(0);
    }

    public void power() {
        launcherMotors.setPower(POWER);
    }

    public void stop() {
        launcherMotors.setPower(0);
    }

}

