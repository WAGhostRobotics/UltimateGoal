package org.firstinspires.ftc.teamcode.library;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.core.Mary;

import java.util.ArrayList;

import static org.firstinspires.ftc.teamcode.core.Mary.sensors;

//import org.firstinspires.ftc.teamcode.component.IMU;

public class DriveSensor {

    private ArrayList<DcMotor> motors;
//    private Sensors sensors;
//    private IMU imu;

    public DriveSensor(ArrayList<DcMotor> motors) {
        this.motors = motors;
//        this.sensors = sensors;
//        this.imu = imu;
    }

    public void move(MoveDirection direction, ReferenceDirection reference, int distance, double power) {
        switch(direction) {
            case FORWARD:
                while(sensors.getFrontRight() > distance && sensors.getFrontLeft() > distance) {
                    double motorPower = power;
                    double turn = 0;
                    DriveStyle.MecanumArcade(motors, motorPower, 0, 1, turn);
                }
                break;
            case BACKWARD:
                while(sensors.getFrontRight() < distance && sensors.getFrontLeft() < distance) {
                    double motorPower = power;
                    DriveStyle.MecanumArcade(motors, motorPower, 0, -1, 0);
                }
                break;
            case RIGHT:
                switch(reference) {
                    case TOWARDS:
                        while(sensors.getRight() > distance) {
                            double motorPower = power;
                            DriveStyle.MecanumArcade(motors, motorPower, 1, 0, 0);
                        }
                        break;
                    case AWAY:
                        while(sensors.getRight() < distance) {
                            double motorPower = power;
                            DriveStyle.MecanumArcade(motors, motorPower, -1, 0, 0);
                        }
                        break;
                }
                break;
            case LEFT:
                switch(reference) {
                    case TOWARDS:
                        while(sensors.getLeft() > distance) {
                            double motorPower = power;
                            DriveStyle.MecanumArcade(motors, motorPower, -1, 0, 0);
                        }
                        break;
                    case AWAY:
                        while(sensors.getLeft() < distance) {
                            double motorPower = power;
                            DriveStyle.MecanumArcade(motors, motorPower, 1, 0, 0);
                        }
                        break;
                }
                break;
        }
        DriveStyle.MecanumArcade(motors, 0, 0, 0 ,0);
    }

    public void diagonalForwardLeft(double x, double y) {
        double x_on = 1;
        double y_on = 0.6;
        while((sensors.getFrontRight() > y && sensors.getFrontLeft() > y) || sensors.getLeft() > x) {


            if(sensors.getFrontRight() < y || sensors.getFrontLeft() < y) {
                y_on = 0;
            }

            if(sensors.getLeft() < x) {
                x_on = 0;
                y_on = 1;
            }

            DriveStyle.MecanumArcade(motors, 1, -x_on, y_on, 0);
        }

        DriveStyle.MecanumArcade(motors, 0, 0, 0, 0);
    }

    /*
    public void move(MoveDirection forwardbackward, int distance1, double power1, MoveDirection leftright, ReferenceDirection reference, int distance2, double power2) {
        switch(forwardbackward) {
            case FORWARD:
                switch(leftright) {
                    case LEFT:
                        switch(reference) {
                            case TOWARDS:
                                while((sensors.getFrontLeft() > distance2 && sensors.getFrontRight() < distance1) ||
                                            sensors.getLeft() > distance2) {
                                    if(!(sensors.getFrontLeft() > distance2 && sensors.getFrontRight() > distance1)){
                                        power1 = 0;
                                    }
                                    if(!(sensors.getLeft() < distance2)) {
                                        power2 = 0;
                                    }
                                    if(power1 == 0 && power2 == 0) {
                                        DriveStyle.MecanumArcade(motors, 0, 0, 0, 0);
                                        return;
                                    }
                                    DriveStyle.MecanumArcade(motors, 1, -power2, power1, 0);
                                }
                                break;
                            case AWAY:
                                while((sensors.getFrontLeft() > distance2 && sensors.getFrontRight() < distance1) ||
                                        sensors.getLeft() < distance2) {
                                    if(!(sensors.getFrontLeft() > distance2 && sensors.getFrontRight() > distance1)){
                                        power1 = 0;
                                    }
                                    if(!(sensors.getLeft() > distance2)) {
                                        power2 = 0;
                                    }
                                    if(power1 == 0 && power2 == 0) {
                                        DriveStyle.MecanumArcade(motors, 0, 0, 0, 0);
                                        return;
                                    }
                                    DriveStyle.MecanumArcade(motors, 1, power2, power1, 0);
                                }
                                break;
                        }
                        break;
                    case RIGHT:
                        switch(reference) {
                            case TOWARDS:
                                while((sensors.getFrontLeft() > distance2 && sensors.getFrontRight() < distance1) ||
                                        sensors.getRight() > distance2) {
                                    if(!(sensors.getFrontLeft() > distance2 && sensors.getFrontRight() > distance1)){
                                        power1 = 0;
                                    }
                                    if(!(sensors.getRight() < distance2)) {
                                        power2 = 0;
                                    }
                                    if(power1 == 0 && power2 == 0) {
                                        DriveStyle.MecanumArcade(motors, 0, 0, 0, 0);
                                        return;
                                    }
                                    DriveStyle.MecanumArcade(motors, 1, power2, power1, 0);
                                }
                                break;
                            case AWAY:
                                while((sensors.getFrontLeft() > distance2 && sensors.getFrontRight() < distance1) ||
                                        sensors.getRight() < distance2) {
                                    if(!(sensors.getFrontLeft() > distance2 && sensors.getFrontRight() > distance1)){
                                        power1 = 0;
                                    }
                                    if(!(sensors.getRight() > distance2)) {
                                        power2 = 0;
                                    }
                                    if(power1 == 0 && power2 == 0) {
                                        DriveStyle.MecanumArcade(motors, 0, 0, 0, 0);
                                        return;
                                    }
                                    DriveStyle.MecanumArcade(motors, 1, -power2, power1, 0);
                                }
                                break;
                        }
                        break;
                }
                break;
            case BACKWARD:
                switch(leftright) {
                    case LEFT:
                        switch(reference) {
                            case TOWARDS:
                                while((sensors.getFrontLeft() < distance2 && sensors.getFrontRight() < distance1) ||
                                        sensors.getLeft() > distance2) {
                                    if(!(sensors.getFrontLeft() > distance2 && sensors.getFrontRight() > distance1)){
                                        power1 = 0;
                                    }
                                    if(!(sensors.getLeft() < distance2)) {
                                        power2 = 0;
                                    }
                                    if(power1 == 0 && power2 == 0) {
                                        DriveStyle.MecanumArcade(motors, 0, 0, 0, 0);
                                        return;
                                    }
                                    DriveStyle.MecanumArcade(motors, 1, -power2, -power1, 0);
                                }
                                break;
                            case AWAY:
                                while((sensors.getFrontLeft() < distance2 && sensors.getFrontRight() < distance1) ||
                                        sensors.getLeft() < distance2) {
                                    if(!(sensors.getFrontLeft() > distance2 && sensors.getFrontRight() > distance1)){
                                        power1 = 0;
                                    }
                                    if(!(sensors.getLeft() > distance2)) {
                                        power2 = 0;
                                    }
                                    if(power1 == 0 && power2 == 0) {
                                        DriveStyle.MecanumArcade(motors, 0, 0, 0, 0);
                                        return;
                                    }
                                    DriveStyle.MecanumArcade(motors, 1, power2, -power1, 0);
                                }
                                break;
                        }
                        break;
                    case RIGHT:
                        switch(reference) {
                            case TOWARDS:
                                while((sensors.getFrontLeft() < distance2 && sensors.getFrontRight() < distance1) ||
                                        sensors.getRight() > distance2) {
                                    if(!(sensors.getFrontLeft() > distance2 && sensors.getFrontRight() > distance1)){
                                        power1 = 0;
                                    }
                                    if(!(sensors.getRight() < distance2)) {
                                        power2 = 0;
                                    }
                                    if(power1 == 0 && power2 == 0) {
                                        DriveStyle.MecanumArcade(motors, 0, 0, 0, 0);
                                        return;
                                    }
                                    DriveStyle.MecanumArcade(motors, 1, power2, -power1, 0);
                                }
                                break;
                            case AWAY:
                                while((sensors.getFrontLeft() < distance2 && sensors.getFrontRight() < distance1) ||
                                        sensors.getRight() < distance2) {
                                    if(!(sensors.getFrontLeft() > distance2 && sensors.getFrontRight() > distance1)){
                                        power1 = 0;
                                    }
                                    if(!(sensors.getRight() > distance2)) {
                                        power2 = 0;
                                    }
                                    if(power1 == 0 && power2 == 0) {
                                        DriveStyle.MecanumArcade(motors, 0, 0, 0, 0);
                                        return;
                                    }
                                    DriveStyle.MecanumArcade(motors, 1, -power2, -power1, 0);
                                }
                                break;
                        }
                        break;
                }
                break;
        }
    }
     */

    public void turn(TurnDirection direction, int degrees) {
        double curr_heading = Mary.imu.getHeading();
        double new_heading = curr_heading + (degrees);

        while(Math.abs(Mary.imu.getHeading() - new_heading) > 5) {
            if(Mary.imu.getHeading() - new_heading < 0) {
                DriveStyle.MecanumArcade(motors, -1, 0,0,1);
            } else {
                DriveStyle.MecanumArcade(motors, 1, 0,0,1);
            }
        }
        DriveStyle.MecanumArcade(motors, 0, 0, 0 ,0);
    }

    public void turn(TurnDirection direction, int degrees, double power) {
        double curr_heading = Mary.imu.getHeading();
        double new_heading = curr_heading + (degrees);

        while(Math.abs(Mary.imu.getHeading() - new_heading) > 8) {
            if(Mary.imu.getHeading() - new_heading < 0) {
                DriveStyle.MecanumArcade(motors, -power, 0,0,1);
            } else {
                DriveStyle.MecanumArcade(motors, power, 0,0,1);
            }
        }
        DriveStyle.MecanumArcade(motors, 0, 0, 0 ,0);
    }

    public void straighten(double heading) {
        double inc = 2;

        if(heading < 0)  {
            heading += inc;
        } else if(heading > 0) {
            heading -= inc;
        }

        while(Math.abs(Mary.imu.getHeading() - heading) > 2) {
            if(Mary.imu.getHeading() - heading < 0) {
                DriveStyle.MecanumArcade(motors, -0.5, 0,0,1);
            } else {
                DriveStyle.MecanumArcade(motors, 0.5, 1,0,1);
            }
        }
        DriveStyle.MecanumArcade(motors, 0, 0, 0 ,0);
    }

    public void straighten(double heading, double power) {
        double inc = 2;

        if(heading < 0)  {
            heading += inc;
        } else if(heading > 0) {
            heading -= inc;
        }

        while(Math.abs(Mary.imu.getHeading() - heading) > 2) {
            if(Mary.imu.getHeading() - heading < 0) {
                DriveStyle.MecanumArcade(motors, -power, 0,0,1);
            } else {
                DriveStyle.MecanumArcade(motors, power, 1,0,1);
            }
        }
        DriveStyle.MecanumArcade(motors, 0, 0, 0 ,0);
    }

    public enum MoveDirection {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT
    }

    public enum TurnDirection {
        RIGHT,
        LEFT
    }

    public enum ReferenceDirection {
        TOWARDS,
        AWAY
    }
}
