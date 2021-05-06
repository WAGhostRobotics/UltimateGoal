package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.core.Mary;
import org.firstinspires.ftc.teamcode.library.multimotors.MultiDcMotor;

import static org.firstinspires.ftc.teamcode.core.Mary.hardwareMap;


public class Launcher {
    // Launcher
    private DcMotor launcher1;
    private DcMotor launcher2;
    private Servo mover;
    private MultiDcMotor launcherMotors;

    private final static double POWER = 1.0;
    private final static int TIME = 1000;
    private final static int TIME2 = 2000;


    public void init(HardwareMap hardwareMap) {
        // Init intake motors
        launcher1 = hardwareMap.get(DcMotor.class, "l1");
        launcher2 = hardwareMap.get(DcMotor.class, "l2");
        mover = hardwareMap.get(Servo.class, "m");

//        launcher1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        launcher2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        launcher1.setDirection(DcMotorSimple.Direction.FORWARD);
        launcher2.setDirection(DcMotorSimple.Direction.FORWARD);
        mover.setDirection(Servo.Direction.REVERSE);

        launcherMotors = new MultiDcMotor(launcher1, launcher2);

//        launcherMotors.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void power(double power1, double power2) {
        launcher1.setPower(power1);
        launcher2.setPower(power2);
    }

    public double getVoltage() {
        return hardwareMap.voltageSensor.get("l1").getVoltage();
    }

    public void shoot() {
        mover.setPosition(1);
        Mary.sleep(200);
        mover.setPosition(0.0);
        Mary.intake.in();
        Mary.intake.stop();
    }

    public void TeleShoot(double pos) {
        mover.setPosition(pos);
    }

    public void stop() {
        launcher1.setPower(0);
        launcher2.setPower(0);
    }

    public double[] getSpeed() {
        double[] speed = new double[2];
        speed[0] = launcher1.getPower();
        speed[1] = launcher2.getPower();
        return speed;
    }
}

