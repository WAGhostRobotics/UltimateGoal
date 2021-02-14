package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.component.Claw;
import org.firstinspires.ftc.teamcode.component.Intake;
import org.firstinspires.ftc.teamcode.component.Launcher;
import org.firstinspires.ftc.teamcode.component.Sensors;

import java.util.ArrayList;

public class Mary {
    public static HardwareMap hardwareMap;

    // DriveStyle motors
    private static DcMotor dFrontLeft;
    private static DcMotor dFrontRight;
    private static DcMotor dBackLeft;
    private static DcMotor dBackRight;

    // Intake and Conveyor Belt
    public static Intake intake;

    // Wobble Goal Claw
    public static Claw claw;

    // Launcher
    public static Launcher launcher;

    // Sensors
    public static Sensors sensors;

    // Motor array [in order: lf, lr, rf, rr]
    public static ArrayList<DcMotor> driveMotors = new ArrayList<>();

    /*
    // Capstone
    public static Servo capstone;
    public static final double HOLD = 0.1;
    public static final double LIBERATE = 0.9;
    */

    // Gyro
    public static BNO055IMU imu;

    public static void init(HardwareMap hwMap) {
        // Assign HardwareMap
        hardwareMap = hwMap;

        // Assign motor information
        dFrontLeft = hardwareMap.get(DcMotor.class, "lf");
        dFrontRight = hardwareMap.get(DcMotor.class, "rf");
        dBackLeft = hardwareMap.get(DcMotor.class, "lr");
        dBackRight = hardwareMap.get(DcMotor.class, "rr");

        // Adjust motor directions - this decides which side of the robot is "front"
        // Flip the values to change the direction the robot "faces"
        // The motors turn counterclockwise looking at them head on for FORWARD; set the right ones to reverse for correct operation
        dFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        dFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        dBackLeft.setDirection(DcMotorSimple.Direction.FORWARD); // This is super sketchy... idk why its backwards?
        dBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Adjust motor stopping behavior; "BRAKE" locks the motor shaft, while "FLOAT" just stops applying power
        dFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        // THIS MAY OR MAY NOT WORK
//        // set to use encoders (can use for specific speed or distance)
        dFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Adds the motors to a motor array for easier reference
        // The order here must match the order used in {@link DriveStyle}
        driveMotors.add(dFrontLeft);
        driveMotors.add(dBackLeft);
        driveMotors.add(dFrontRight);
        driveMotors.add(dBackRight);

        // Intake and Conveyor Belt
        intake = new Intake();

        // Wobble Claw
        claw = new Claw();

        // Launcher
        launcher = new Launcher();

        // Sensors
        sensors = new Sensors();

        intake.init(hardwareMap);
        launcher.init(hardwareMap);
        claw.init(hardwareMap);
        sensors.init();

        // Gyro
        imu = hardwareMap.get(BNO055IMU.class, "imu");
    }

    /**
     * Sleeps for the given amount of milliseconds, or until the thread is interrupted. This is
     * simple shorthand for the operating-system-provided {@link Thread#sleep(long) sleep()} method.
     *
     * @param milliseconds amount of time to sleep, in milliseconds
     * @see Thread#sleep(long)
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}