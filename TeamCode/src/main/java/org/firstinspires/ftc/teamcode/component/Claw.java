package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    // Foundation
    private Servo claw;
    private Servo lift;

    private final static double GRAB = 0.55;
    private final static double RELEASE = 0.0;
    private final static double UP = 0.9;
    private final static double DOWN = 0.0;

    public void init(HardwareMap hardwareMap) {
        // Foundation
        claw = hardwareMap.get(Servo.class, "claw");
        claw.setDirection(Servo.Direction.FORWARD);
        lift = hardwareMap.get(Servo.class, "lift");
        lift.setDirection(Servo.Direction.FORWARD);
    }

    public void grab() {
        claw.setPosition(GRAB);
    }

    public void release() {
        claw.setPosition(RELEASE);
    }

    public void up() { lift.setPosition(UP); }

    public void down() { lift.setPosition(DOWN); }
}
