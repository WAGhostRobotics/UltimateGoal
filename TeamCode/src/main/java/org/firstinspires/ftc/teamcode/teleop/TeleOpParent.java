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
    private long clawtime = 0;
    private boolean moving = false;
    private ClawMovement movement = ClawMovement.NONE;

    // Set default DriveType
    DriveStyle.DriveType type = DriveStyle.DriveType.MECANUMARCADE;
    double slow = 1.0;
    Boolean kid = false;

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
            DriveStyle.driveWithType(Mary.driveMotors, gamepad2, type, slow);

            // Wobble Goal Claw


            // Conveyor Belt on/off in/out
            if (gamepad1.a || gamepad2.a && !kid) {
                Mary.intake.in();
            } else if (gamepad1.b || gamepad2.b && !kid) {
                Mary.intake.out();
            } else {
                Mary.intake.stop();
            }

            // Launcher
            // Launcher Wheels
            if (gamepad1.dpad_up || gamepad2.dpad_up && !kid) { // top power
                Mary.launcher.power(0.9, 0.9);
                Mary.intake.inBelt();
            } else if (gamepad1.dpad_down || gamepad2.dpad_down && !kid) { // middle power
                Mary.launcher.power(0.85, 0.85);
                Mary.intake.inBelt();
            } else {
                Mary.launcher.stop();
                Mary.intake.stop();
            }
            // Launcher Mover

            if(gamepad1.y || gamepad2.y && !kid) {
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
            if(gamepad1.left_bumper||gamepad2.left_bumper && !kid){
                Mary.claw.grab();
            }

            if(gamepad1.right_bumper||gamepad2.right_bumper && !kid){
                Mary.claw.release();
            }

            if(gamepad1.dpad_right||gamepad2.dpad_right && !kid){
                movement = ClawMovement.OUT;
                clawtime = System.currentTimeMillis();
                up = false;
            }

            if(gamepad1.dpad_left|| gamepad2.dpad_left && !kid){
                movement = ClawMovement.IN;
                clawtime = System.currentTimeMillis();
                up = false;
            }

            if((gamepad1.left_stick_button|| gamepad2.left_stick_button && !kid) && !up){
                movement = ClawMovement.UP;
                clawtime = System.currentTimeMillis();
                up = true;
            }

            if(movement != ClawMovement.NONE) {
                if ((movement == ClawMovement.IN && Mary.claw.getPosition() <= Mary.claw.IN) ||
                        (movement == ClawMovement.OUT && Mary.claw.getPosition() == Mary.claw.OUT) ||
                        (movement == ClawMovement.UP && Mary.claw.getPosition() == Mary.claw.UP))  {
                        movement = ClawMovement.NONE;
                }
            }

            if(System.currentTimeMillis() - clawtime > 10) {
                if(movement == ClawMovement.IN) {
                    Mary.claw.move(false);
                } else if (movement == ClawMovement.OUT) {
                    Mary.claw.move(true);
                } else if (movement == ClawMovement.UP) {
                    if(Mary.claw.getPosition() < Mary.claw.UP) {
                         Mary.claw.move(true);
                    } else {
                        Mary.claw.move(false);
                    }
                }
                clawtime = System.currentTimeMillis();
            }

            // Send diagnostics to user
            telemetry.addData("Status", "Running");
        }
    }

    private enum ClawMovement {
        UP,
        IN,
        OUT,
        NONE
    }
}