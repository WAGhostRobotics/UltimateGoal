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
    private boolean hasRun2 = true;
    private long servotime = 0;
    private long ringtime = 0;

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
                Mary.launcher.power(1, 1);
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) { // middle power
                Mary.launcher.power(0.35, 0.35);
            } else {
                Mary.launcher.stop();
            }
            // Launcher Mover

            if(gamepad1.y || gamepad2.y) {
                servotime = System.currentTimeMillis();
                Mary.launcher.TeleShoot(1);
                hasRun = false;

            }

            if(!hasRun && System.currentTimeMillis() - servotime > 200) {
                Mary.launcher.TeleShoot(0);
                Mary.intake.in();
                ringtime = System.currentTimeMillis();
                hasRun = true;
                hasRun2 = false;
            }

            if(!hasRun2 && System.currentTimeMillis() - ringtime > 100) {
                Mary.intake.stop();
                hasRun2 = true;
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

            // Send diagnostics to user
            telemetry.addData("Status", "Running");
        }
    }
}