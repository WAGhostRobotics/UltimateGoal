package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

class Claw {
    // Foundation
    private Servo claw;

    private final static double DOWN = 0.55;
    private final static double UP = 0.0;

    public void init(HardwareMap hardwareMap) {
        // Foundation
        claw = hardwareMap.get(Servo.class, "claw");
        claw.setDirection(Servo.Direction.FORWARD);
    }

    public void down() {
        claw.setPosition(DOWN);
    }

    public void up() {
        claw.setPosition(UP);
    }
}
