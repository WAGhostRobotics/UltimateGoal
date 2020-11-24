package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    // Intake
    private DcMotor intake;
    private DcMotor belt;

    public void init(HardwareMap hardwareMap) {
        // Init intake motors
        intake = hardwareMap.get(DcMotor.class, "in");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        belt = hardwareMap.get(DcMotor.class, "cn");
        belt.setDirection(DcMotorSimple.Direction.REVERSE);
        belt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void in() {
        intake.setPower(1);
        belt.setPower(1);
    }

    public void out() {
        intake.setPower(-1);
        belt.setPower(-1);
    }

    public void stop() {
        intake.setPower(0);
        belt.setPower(0);
    }
}
