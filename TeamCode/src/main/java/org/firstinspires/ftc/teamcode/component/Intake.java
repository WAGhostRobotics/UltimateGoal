package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

class Intake {
    // Intake
    private DcMotor intake;

    public void init(HardwareMap hardwareMap) {
        // Init intake motors
        intake = hardwareMap.get(DcMotor.class, "intake");

        intake.setDirection(DcMotorSimple.Direction.FORWARD);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void in() {
        intake.setPower(1);
    }

    public void out() {
        intake.setPower(-1);
    }

    public void stop() {
        intake.setPower(0);
    }
}
