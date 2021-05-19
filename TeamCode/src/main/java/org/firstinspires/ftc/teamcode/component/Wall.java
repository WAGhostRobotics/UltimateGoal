package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Wall {
    // Foundation
    private Servo wall;

    public final static double UP = 0.00;
    public final static double DOWN = 0.46;

    public void init(HardwareMap hardwareMap) {
        // Foundation
        wall = hardwareMap.get(Servo.class, "wall");
        wall.setDirection(Servo.Direction.FORWARD);

        up();
    }

    public void up() {
        wall.setPosition(UP);
    }

    public void down() {
        wall.setPosition(DOWN);
    }

}
