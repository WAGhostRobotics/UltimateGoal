package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.core.Sonic;
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

    // Set default DriveType
    DriveStyle.DriveType type = DriveStyle.DriveType.MECANUMARCADE;

    @Override
    public void runOpMode() throws InterruptedException {

        // Send diagnostics to user
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        // Initialize the robot hardware
        Sonic.init(hardwareMap);

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
            DriveStyle.driveWithType(Sonic.driveMotors, gamepad1, type);

            // Winch control (g2.du/dd)
            if (gamepad2.dpad_down && !buttonWasPressed) {
                desiredPosition = Range.clip(desiredPosition - WINCH_INCREMENT, WINCH_MIN, WINCH_MAX);
//                Kevin.linearSlides.setPosition(desiredPosition);
                buttonWasPressed = true;
            } else if (gamepad2.dpad_up && !buttonWasPressed) {
                desiredPosition = Range.clip(desiredPosition + WINCH_INCREMENT, WINCH_MIN, WINCH_MAX);
//                Kevin.linearSlides.setPosition(desiredPosition);
                buttonWasPressed = true;
            }

            if (buttonWasPressed && !gamepad2.dpad_up && !gamepad2.dpad_down) {
                buttonWasPressed = false;
            }

            // Extender control (g2.dl/dr)
            if (gamepad2.dpad_right) {
//                Kevin.extender.extend();
            } else if (gamepad2.dpad_left) {
//                Kevin.extender.retract();
            }

            // Claw grabbing (g2.x/a)
            if (gamepad2.x) {
//                Kevin.extender.closeClaw();
            } else if (gamepad2.a) {
//                Kevin.extender.openClaw();
            }

            // Intake (g2.b/y)
            if (gamepad2.b) {
//                Kevin.intake.in();
            } else if (gamepad2.y) {
//                Kevin.intake.out();
            } else {
//                Kevin.intake.stop();
            }

            // Foundation (g1.lb/rb)
            if (gamepad1.left_bumper) {
//                Kevin.foundationGrabber.grab();
            } else if (gamepad1.right_bumper) {
//                Kevin.foundationGrabber.release();
            }

            // Send diagnostics to user
            telemetry.addData("Status", "Running");
        }
    }
}