package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.library.multimotors.MultiDcMotor;

class Launcher {
    // Intake
    private DcMotor launcher1;
    private DcMotor launcher2;

    private MultiDcMotor launcherMotors;

    public void init(HardwareMap hardwareMap) {
        // Init intake motors
        launcher1 = hardwareMap.get(DcMotor.class, "l1");
        launcher2 = hardwareMap.get(DcMotor.class, "l2");

        launcher1.setDirection(DcMotorSimple.Direction.FORWARD);
        launcher2.setDirection(DcMotorSimple.Direction.REVERSE);

        launcherMotors = new MultiDcMotor(launcher1, launcher2);

        launcherMotors.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void in() {
        launcherMotors.setPower(1);
    }

    public void out() {
        launcherMotors.setPower(-1);
    }

    public void stop() {
        launcherMotors.setPower(0);
    }
}
