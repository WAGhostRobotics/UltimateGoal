package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.core.Kevin;
import org.firstinspires.ftc.teamcode.library.multimotors.MultiDcMotor;


public class Launcher {
    // Intake
    private DcMotor launcher1;
    private DcMotor launcher2;
    private Servo mover;
    private MultiDcMotor launcherMotors;

    private final static double TOP = 0.9;
    private final static double MIDDLE = 0.8;
    private final static double BOTTOM = 0.7;
    private final static double POWER = 1.0;

    public void init(HardwareMap hardwareMap) {
        // Init intake motors
        launcher1 = hardwareMap.get(DcMotor.class, "l1");
        launcher2 = hardwareMap.get(DcMotor.class, "l2");
        mover = hardwareMap.get(Servo.class, "m");


        launcher1.setDirection(DcMotorSimple.Direction.FORWARD);
        launcher2.setDirection(DcMotorSimple.Direction.REVERSE);

//        launcherMotors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        launcherMotors.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        launcherMotors = new MultiDcMotor(launcher1, launcher2);

        launcherMotors.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    }

    public void reverse() {
        launcherMotors.setPower(-1);
        Kevin.sleep(1000);
        double position = mover.getPosition();
        mover.setPosition(position + 0.1);
        Kevin.sleep(100);
        launcherMotors.setPower(0);
    }

    public void top() {
        if(true) {
            throw new IllegalArgumentException("GOSH DARN IT WORK");
        }
        launcher1.setPower(0.9);
        launcher2.setPower(0.9);
        Kevin.sleep(1000);
        double position = mover.getPosition();
        mover.setPosition(position + 0.1);
        Kevin.sleep(100);
        launcherMotors.setPower(0);
    }

    public void middle() {
        if(true) {
            throw new IllegalArgumentException("GOSH DARN IT WORK");
        }
        launcher1.setPower(0.8);
        launcher2.setPower(0.8);
        Kevin.sleep(1000);
        double position = mover.getPosition();
        mover.setPosition(position + 0.1);
        Kevin.sleep(100);
        mover.setPosition(position);
        launcherMotors.setPower(0);
    }

    public void bottom() {
        launcherMotors.setPower(0.7);
        Kevin.sleep(1000);
        double position = mover.getPosition();
        mover.setPosition(position + 0.1);
        Kevin.sleep(100);
        launcherMotors.setPower(0);
    }

    public void power() {
        launcherMotors.setPower(POWER);
    }

    public void stop() {
        launcherMotors.setPower(0);
    }


}

