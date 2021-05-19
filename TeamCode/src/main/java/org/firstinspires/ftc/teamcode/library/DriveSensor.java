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
    private boolean secondTime = false;

    public DriveSensor(ArrayList<DcMotor> motors) {
        this.motors = motors;
//        this.sensors = sensors;
//        this.imu = imu;
    }

    public void move(Sensor direction, ReferenceDirection reference, double distance, double power) {
        switch(direction) {
            case FRONT:
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
            case BACK:
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

    // specifically makes robot move backwards and turn counterclockwise
    public void moveAndTurn(double degrees, double power) {
        double curr_heading = Mary.imu.getHeading();
        double new_heading = curr_heading + (degrees);

        while(Math.abs(Mary.imu.getHeading() - new_heading) > 8) {
            if(Mary.imu.getHeading() - new_heading < 0) {
                DriveStyle.MecanumArcade(motors, power, 0,-1,-1);
            }
            if(Mary.imu.getHeading() - new_heading > 0) {
                DriveStyle.MecanumArcade(motors, power, 0,1,1);
            }
        }
        DriveStyle.MecanumArcade(motors, 0, 0, 0 ,0);
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

    public void straighten(int heading) {
// Left positive
//right negative
        final double POWER = 0.08;
        switch(heading) {
            case 0:
                while(Math.abs(Mary.imu.getHeading()) > 0.5) {
                    if(Mary.imu.getHeading() < 0) {
                        DriveStyle.MecanumArcade(motors, -POWER, 0,0,1);
                    } else {
                        DriveStyle.MecanumArcade(motors, POWER, 1,0,1);
                    }
                }
                break;
            case 180:
                while(Math.abs(Math.abs(Mary.imu.getHeading()) - 180) > 0.5) {
                    if(Mary.imu.getHeading() < 0) {
                        DriveStyle.MecanumArcade(Mary.driveMotors, POWER, 0, 0 ,1);
                    } else {
                        DriveStyle.MecanumArcade(Mary.driveMotors, -POWER, 0, 0 ,1);
                    }
                }
                break;
        }

        DriveStyle.MecanumArcade(motors, 0, 0, 0 ,0);

//        if(!secondTime) {
//            straighten(heading);
//            secondTime = true;
//        } else {
//            secondTime = false;
//
//        }
    }

    public void straighten(int heading, double power) {
// Left positive
//right negative
        switch(heading) {
            case 0:
                while (Math.abs(Mary.imu.getHeading()) > 3) {//5
                    if (Mary.imu.getHeading() < 0) {
                        DriveStyle.MecanumArcade(motors, -power, 0, 0, 1);
                    } else {
                        DriveStyle.MecanumArcade(motors, power, 1, 0, 1);
                    }
                }
                break;
            case 180:
                while (Math.abs(Math.abs(Mary.imu.getHeading()) - 180) > 3) {//5
                    if (Mary.imu.getHeading() < 0) {
                        DriveStyle.MecanumArcade(Mary.driveMotors, power, 0, 0, 1);
                    } else {
                        DriveStyle.MecanumArcade(Mary.driveMotors, -power, 0, 0, 1);
                    }
                }
                break;
            case 90:
                while(Mary.imu.getHeading() - 90 > 3) {
                if(Mary.imu.getHeading() < 90 &&  Mary.imu.getHeading() > -90) {
                    DriveStyle.MecanumArcade(Mary.driveMotors, power, 0, 0 ,1);
                } else {
                    DriveStyle.MecanumArcade(Mary.driveMotors, -power, 0, 0 ,1);
                }
                break;
                }
        }

        DriveStyle.MecanumArcade(motors, 0, 0, 0 ,0);
    }

    public enum Sensor {
        FRONT,
        BACK,
        LEFT,
        RIGHT
    }

    public enum Direction {
        FORWARD,
        BACKWARD
    }

    public enum ReferenceDirection {
        TOWARDS,
        AWAY
    }
}
