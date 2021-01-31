package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.core.Mary;
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

    // Wobble Claw variables
    private boolean up = false;

    // Set default DriveType
    DriveStyle.DriveType type = DriveStyle.DriveType.MECANUMARCADE;

    @Override
    public void runOpMode() throws InterruptedException {

        // Send diagnostics to user
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        // Initialize the robot hardware
        Mary.init(hardwareMap);

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
            DriveStyle.driveWithType(Mary.driveMotors, gamepad2, type);

            // Wobble Goal Claw


            // Conveyor Belt on/off in/out
            if (gamepad1.a || gamepad2.a) {
                Mary.intake.in();
            } else if (gamepad1.b || gamepad2.b) {
                Mary.intake.out();
            } else {
                Mary.intake.stop();
            }

            // Launcher
            // Launcher Wheels
            if (gamepad1.dpad_up || gamepad2.dpad_up) { // top power
                Mary.launcher.power(1, 0.9);
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) { // middle power
                Mary.launcher.power(0.9, 0.8);
            } else {
                Mary.launcher.stop();
            }
            // Launcher Mover

            if(gamepad1.y || gamepad2.y) {
                setTime = System.currentTimeMillis();
                hasRun = false;
                forward = false;

                Mary.launcher.shoot(1);
            }

            // Wobble Claw
            if(gamepad1.left_bumper||gamepad2.left_bumper){
                Mary.claw.grab();
            }

            if(gamepad1.right_bumper||gamepad2.right_bumper){
                Mary.claw.release();
            }

            if(gamepad1.dpad_right||gamepad2.dpad_right){
                Mary.claw.out();
                up = false;
            }

            if(gamepad1.dpad_left|| gamepad2.dpad_left){
                Mary.claw.in();
                up = false;
            }

            if((gamepad1.left_stick_button|| gamepad2.left_stick_button) && !up){
                Mary.claw.up();
                up = true;
            }

            if(!hasRun && System.currentTimeMillis() - setTime > 2000) {
                setTime = System.currentTimeMillis();
                Mary.launcher.shoot(-1);
                forward = true;
            }

            if(forward && !hasRun && System.currentTimeMillis() - setTime > 2000) {
                setTime = System.currentTimeMillis();
                Mary.launcher.shoot(0);
                hasRun = true;
            }

            // Send diagnostics to user
            telemetry.addData("Status", "Running");
        }
    }
}