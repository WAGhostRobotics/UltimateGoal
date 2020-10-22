package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.teleop.TeleOpParent;

import java.util.ArrayList;

public class DriveAuto {

    private ArrayList<DcMotor> motors;

    public DriveAuto(ArrayList<DcMotor> motors) {
        this.motors = motors;
    }

    public void move(MoveDirection direction, double power, double seconds) {
        switch (direction) {
            case FORWARD:
                DriveStyle.MecanumArcade(motors, power, 0, 1, 0);
                break;
            case BACKWARD:
                DriveStyle.MecanumArcade(motors, power, 0, -1, 0);
                break;
            case RIGHT:
                DriveStyle.MecanumArcade(motors, power, 1, 0, 0);
                break;
            case LEFT:
                DriveStyle.MecanumArcade(motors, power, -1, 0, 0);
                break;
        }
        new TeleOpParent().sleep((int) (seconds * 1000)); //TODO: deal with sleep call
        DriveStyle.stop(motors);
    }

    public void turn(TurnDirection direction, double power, double heading, Gyro gyro) {
        gyro.resetAngle();
        double currentHeading = gyro.getAngle();
        switch (direction) {
            case LEFT:
                DriveStyle.MecanumArcade(motors, power, 0, 0, -1);
                break;
            case RIGHT:
                DriveStyle.MecanumArcade(motors, power, 0, 0, 1);
                break;
        }
        while ((direction == TurnDirection.LEFT ? currentHeading < heading : currentHeading > -heading) && !new TeleOpParent().isStopRequested()) {
            currentHeading = gyro.getAngle();
        }
        DriveStyle.stop(motors);
    }

    public void turn(TurnDirection direction, double power, double seconds) {
        switch (direction) {
            case LEFT:
                DriveStyle.MecanumArcade(motors, power, 0, 0, -1);
                break;
            case RIGHT:
                DriveStyle.MecanumArcade(motors, power, 0, 0, 1);
                break;
        }
        new TeleOpParent().sleep((int) (seconds * 1000));
        DriveStyle.stop(motors);
    }

    public void stop(double seconds) {
        DriveStyle.stop(motors);
        new TeleOpParent().sleep((int) (seconds * 1000));
    }

    public enum MoveDirection {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT
    }

    public enum TurnDirection {
        LEFT,
        RIGHT
    }
}