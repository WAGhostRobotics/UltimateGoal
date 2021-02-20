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
                switch(reference) {
                    case TOWARDS:
                        while(sensors.getFront() > distance) {
                            DriveStyle.MecanumArcade(motors, power, 0, 1, 0);
                        }
                        break;
                    case AWAY:
                        while(sensors.getFront() < distance) {
                            DriveStyle.MecanumArcade(motors, power, 0, -1, 0);
                        }
                        break;
                }
                break;
            case BACKWARD:
                switch(reference) {
                    case TOWARDS:
                        while(sensors.getBack() > distance) {
                            DriveStyle.MecanumArcade(motors, power, 0, -1, 0);
                        }
                        break;
                    case AWAY:
                        while(sensors.getBack() < distance) {
                            DriveStyle.MecanumArcade(motors, power, 0, 1, 0);
                        }
                        break;
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

    public void around(double y) {
        while(sensors.getBack() > y)
        DriveStyle.MecanumArcade(motors, 1, -1, -1, 0);
    }

    public void turn(int degrees, double power) {
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
