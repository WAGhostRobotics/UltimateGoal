package org.firstinspires.ftc.teamcode.component;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Claw {
    // Foundation
    private Servo claw;
    private Servo lift;

    private final static double GRAB = 0.04;
    private final static double RELEASE = 0.25;
    private final static double IN = 0.40;
    private final static double OUT= 1.00;
    private final static double UP = 0.70;
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
        long time = System.currentTimeMillis();
        while(lift.getPosition() != IN) {
            if(System.currentTimeMillis() - time > INTERVAL) {
                time = System.currentTimeMillis();
                lift.setPosition(lift.getPosition() - 0.01);
                if (lift.getPosition() < IN) {
                    lift.setPosition(IN);
                }
            }
        }
    }

    public void out() {
        long time = System.currentTimeMillis();
        while(lift.getPosition() != OUT) {
            if(System.currentTimeMillis() - time > INTERVAL) {
                time = System.currentTimeMillis();
                lift.setPosition(lift.getPosition() + 0.01);
                if (lift.getPosition() > OUT) {
                    lift.setPosition(OUT);
                }
            }
        }
    }

    public void up() {
//        long time = System.currentTimeMillis();
//        while(lift.getPosition() != UP) {
//            if(System.currentTimeMillis() - time > INTERVAL) {
//                time = System.currentTimeMillis();
//                if (lift.getPosition() < UP) {
//                    lift.setPosition(lift.getPosition() + 0.01);
//                }
//                if (lift.getPosition() > UP) {
//                    lift.setPosition(lift.getPosition() - 0.01);
//                }
//            }
//        }
        lift.setPosition(UP);
    }
}
