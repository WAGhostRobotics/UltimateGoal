package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Claw {
    // Foundation
    private Servo claw;
    private Servo lift;

    private final static double GRAB = 0.04;
    private final static double RELEASE = 0.25;
    public final static double IN = 0.35;
    public final static double OUT= 1.00;
    public final static double UP = 0.65;
    private final static double INTERVAL = 30;

    public void init(HardwareMap hardwareMap) {
        // Foundation
        claw = hardwareMap.get(Servo.class, "claw");
        claw.setDirection(Servo.Direction.FORWARD);
        lift = hardwareMap.get(Servo.class, "lift");
        lift.setDirection(Servo.Direction.FORWARD);

        lift.setPosition(0);
        in();
    }

    public void grab() {
        claw.setPosition(GRAB);
    }

    public void release() {
        claw.setPosition(RELEASE);
    }

    public void in() {
        lift.setPosition(IN);
    }

    public void out() {
        lift.setPosition(OUT);
    }

    public void up() {
        lift.setPosition(UP);
    }

    public void move(boolean pos) {
        if(pos) {
            lift.setPosition(lift.getPosition() + 0.01);
        } else {
            lift.setPosition(lift.getPosition() - 0.01);
        }
    }

    public double getPosition() {
        return lift.getPosition();
    }
}
