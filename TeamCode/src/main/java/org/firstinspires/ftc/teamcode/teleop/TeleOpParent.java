package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.core.Kevin;
import org.firstinspires.ftc.teamcode.library.DriveStyle;

public class TeleOpParent extends LinearOpMode {

    // Defined global constants
    // Min and max values for the winch
    private static final double WINCH_MIN = 0.0;
    private static final double WINCH_MAX = 0.6;

    // Winch variables/constants
    private double desiredPosition = 0.0;
    private boolean buttonWasPressed = false;

    private static double WINCH_INCREMENT = 0.05;

    // Launcher mover variables
    private boolean hasRun = true;
    private long setTime = 0;
    private boolean forward = false;

    // Set default DriveType
    DriveStyle.DriveType type = DriveStyle.DriveType.MECANUMARCADE;

    @Override
    public void runOpMode() throws InterruptedException {

        // Send diagnostics to user
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        // Initialize the robot hardware
        Kevin.init(hardwareMap);

        // Send diagnostics to user
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // "Gear" the drivetrain when the winches are up
            /*
            if (desiredPosition > 0.1) {
                gamepad1.left_stick_x /= 2; gamepad1.left_stick_y /= 2; gamepad1.right_stick_x /= 2;
            }
            */

            // Drivie using set drivemode (g1.ls/rs, g1.lb/rb)
            DriveStyle.driveWithType(Kevin.driveMotors, gamepad1, type);

            // Conveyor Belt on/off in/out
            if (gamepad1.a || gamepad2.a) {
                Kevin.intake.in();
            } else if (gamepad1.b || gamepad2.b) {
                Kevin.intake.out();
            } else {
                Kevin.intake.stop();
            }

            // Launcher
            // Launcher Wheels
            if (gamepad1.dpad_up || gamepad2.dpad_up) { // top power
                Kevin.launcher.power(1);
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) { // middle power
                Kevin.launcher.power(0.9);
            } else {
                Kevin.launcher.power(0.0);
            }
            // Launcher Mover

            if(gamepad1.y || gamepad2.y) {
                setTime = System.currentTimeMillis();
                hasRun = false;
                forward = false;

                Kevin.launcher.shoot(1);
            }

            if(!hasRun && System.currentTimeMillis() - setTime > 2000) {
                setTime = System.currentTimeMillis();
                Kevin.launcher.shoot(-1);
                forward = true;
            }

            if(forward && !hasRun && System.currentTimeMillis() - setTime > 2000) {
                setTime = System.currentTimeMillis();
                Kevin.launcher.shoot(0);
                hasRun = true;
            }

            // Send diagnostics to user
            telemetry.addData("Status", "Running");
        }
    }
}