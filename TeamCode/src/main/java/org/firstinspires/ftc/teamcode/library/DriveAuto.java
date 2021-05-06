package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.teleop.TeleOpParent;

import java.util.ArrayList;

public class DriveAuto {

    private ArrayList<DcMotor> motors;

    public DriveAuto(ArrayList<DcMotor> motors) {
        this.motors = motors;
    }

    public void moveToPosition(MoveDirection direction, double power, int ticks) { // THIS IS STUPID DO NOT USE
        for(int i = 0; i < 4; i++) {
            motors.get(i).setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motors.get(i).setTargetPosition(ticks);
        }


        for(int i = 0; i < 4; i++) {
            motors.get(i).setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        for(int i = 0; i < 4; i++) {
            motors.get(0).setPower(power);
        }

        while(motors.get(0).isBusy() || motors.get(1).isBusy() || motors.get(2).isBusy()  || motors.get(3).isBusy()) {
            // wait!
        }

        for(int i = 0; i < 4; i++) {
            motors.get(0).setPower(0);
        }

        for(int i = 0; i < 4; i++) {
            motors.get(i).setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
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