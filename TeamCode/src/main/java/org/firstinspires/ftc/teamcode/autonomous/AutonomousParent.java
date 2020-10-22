package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.core.CVLinearOpMode;
import org.firstinspires.ftc.teamcode.core.Kevin;
import org.firstinspires.ftc.teamcode.library.DriveAuto;
import org.firstinspires.ftc.teamcode.library.DriveStyle;
import org.firstinspires.ftc.teamcode.library.MecanumTrigMath;

public class AutonomousParent extends CVLinearOpMode {

    // Environment variables for sub-classes (defaults to blue foundation)
    StartLocation startLocation = StartLocation.FOUNDATION;
    TeamColor teamColor = TeamColor.BLUE;

    private DriveAuto drivetrain = new DriveAuto(Kevin.driveMotors);

    @Override
    public void runOpMode() throws InterruptedException {

        // Send diagnostics to user
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        Kevin.init(hardwareMap);

        vuforiaInit();
        vuforiaActivate();


        // Send diagnostics to user
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // waitForStart();

        while (!isStarted() && !isStopRequested()) {
            vuforiaScan();
            telemetry.addData("last location?: ", retrieveTranslation());
        }

//        vuforiaDeactivate();

        switch (startLocation) {
            case SKYSTONE:
                moveTowardsSkystone(true);
                sleep(1000);
                getSkystone();

                break;
            case FOUNDATION:
                moveRobotTowardsFoundation();

                sleep(300);

                grabFoundation();

                sleep(500);

                moveFoundationBack();

                sleep(300);

                releaseFoundation();

                sleep(300);

                parkOnLine(LinePosition.WALL);

                break;

            case DEPOT:
                getBlock();
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 0.8, 2.7);
                drivetrain.turn(teamColor == TeamColor.BLUE ? DriveAuto.TurnDirection.RIGHT : DriveAuto.TurnDirection.LEFT,
                        0.5, 1.25);
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 0.5, 0.25);
                dropBlock();
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 0.5, 0.25);
                drivetrain.turn(teamColor == TeamColor.RED ? DriveAuto.TurnDirection.RIGHT : DriveAuto.TurnDirection.LEFT,
                        0.5, 1.25);
                drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 0.5, 1.5);
                break;

            case PARKONLY:
                parkOnLine(LinePosition.WALL);

        }
    }

    void moveTowardsSkystone(boolean notTheasMethod) {
        vuforiaActivate();
        vuforiaScan2ElectricBoogaloo();

        final double DESIRED_X = 6; // in inches
        final double DESIRED_Y = 0; // in inches

        final double MARGIN_OF_ERROR = 0.5; // in inches

        double x = lastLocation != null ? lastLocation.getTranslation().get(0) / mmPerInch : DESIRED_X;
        double y = lastLocation != null ? lastLocation.getTranslation().get(1) / mmPerInch : DESIRED_Y;

        // Align on y-axis
        while (Math.abs(y - DESIRED_Y) > MARGIN_OF_ERROR && opModeIsActive()) {
            double motorPower = Range.clip(Math.abs(y - DESIRED_Y) / 6, 0.25, 1);
            if (y - DESIRED_Y > MARGIN_OF_ERROR) {
                DriveStyle.MecanumArcade(Kevin.driveMotors, motorPower, 0, -1, 0);
            } else if (DESIRED_Y - y > MARGIN_OF_ERROR) {
                DriveStyle.MecanumArcade(Kevin.driveMotors, motorPower, 0, 1, 0);
            } else {
                DriveStyle.stop(Kevin.driveMotors);
            }

            vuforiaScan2ElectricBoogaloo();
            y = lastLocation.getTranslation() != null ? lastLocation.getTranslation().get(1) / mmPerInch : y;

            telemetry.addData("Y-pos", y);
            telemetry.update();
        }

        telemetry.addData("Y-pos", "Aligned");
        telemetry.update();

        // Align on x-axis
        while (Math.abs(x - DESIRED_X) > MARGIN_OF_ERROR && opModeIsActive()) {
            double motorPower = Range.clip(Math.abs(x - DESIRED_X) / 6, 0.1, 1);
            if (x - DESIRED_X > MARGIN_OF_ERROR) {
                DriveStyle.MecanumArcade(Kevin.driveMotors, motorPower, -1, 0, 0);
            } else if (DESIRED_X - x > MARGIN_OF_ERROR) {
                DriveStyle.MecanumArcade(Kevin.driveMotors, motorPower, 1, 0, 0);
            } else {
                DriveStyle.stop(Kevin.driveMotors);
            }

            vuforiaScan2ElectricBoogaloo();
            x = lastLocation.getTranslation() != null ? lastLocation.getTranslation().get(0) / mmPerInch : x;

            telemetry.addData("X-pos", x);
            telemetry.update();
        }

        telemetry.addData("X-pos", "Aligned");
        telemetry.update();

        vuforiaDeactivate();
    }

    void moveTowardsSkystone() {
        boolean isWrongPosition = true;
        vuforiaActivate();

        // y-distance
        double ypos = lastLocation.getTranslation().get(1) * mmPerInch;


        while (isWrongPosition && !isStopRequested()) {
            vuforiaScan2ElectricBoogaloo();
            double y = lastLocation.getTranslation().get(1) / mmPerInch;
            telemetry.addData("y value: ", y);
            if (lastLocation.getTranslation() != null) {
                if (ypos < 0) {
//                    DriveStyle.MecanumTank(Kevin.driveMotors, 0.5, -1, -1, 0, 0);
                    drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 0.5, 0.1);

                    if (y * mmPerInch > -2) {
                        isWrongPosition = false;
                        continue;
                    }
                } else {
//                    DriveStyle.MecanumTank(Kevin.driveMotors, 0.5, 1, 1, 0, 0);
                    drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 0.5, 0.1);

                    if (y < 2) {
                        isWrongPosition = false;
                        continue;
                    }
                }
                vuforiaScan();
            }

        }
        isWrongPosition = true;
        sleep(400);

        // x-distance
        while (isWrongPosition && !isStopRequested()) {
            vuforiaScan();
            double x = lastLocation.getTranslation().get(0) * mmPerInch;
            telemetry.addData("x value: ", x);
            if (lastLocation.getTranslation() != null) {
                if (x > -4) {
                    isWrongPosition = false;
                    break;
                }
                if (teamColor == TeamColor.BLUE) {
//                    DriveStyle.MecanumTank(Kevin.driveMotors, 0.5, 0, 0, 0, 1);
                    drivetrain.move(DriveAuto.MoveDirection.RIGHT, 0.8, 0.1);
                } else {
//                    DriveStyle.MecanumTank(Kevin.driveMotors, 0.5, 0, 0, 1, 0);
                    drivetrain.move(DriveAuto.MoveDirection.LEFT, 0.8, 0.1);
                }
                vuforiaScan();
            }
        }

//        if (teamColor == TeamColor.BLUE) {
//            drivetrain.move(DriveAuto.MoveDirection.RIGHT, 0.8, 2);
//        } else {
//            drivetrain.move(DriveAuto.MoveDirection.LEFT, 0.8, 2);
//        }

//        DriveStyle.stop(Kevin.driveMotors);

        vuforiaDeactivate();
    }

    void getSkystone() {
        drivetrain.move(DriveAuto.MoveDirection.FORWARD, 0.5, 1);
        sleep(200);
        drivetrain.move(DriveAuto.MoveDirection.RIGHT, 0.5, 1);
        sleep(200);
        Kevin.intake.in();
        drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 0.5, 1.5);
        sleep(200);
        Kevin.intake.stop();
        sleep(200);
        drivetrain.move(DriveAuto.MoveDirection.FORWARD, 0.5, 1.5);
    }

    void getBlock() {
        // Strafe to the blocks
        drivetrain.move(teamColor == TeamColor.BLUE ? DriveAuto.MoveDirection.RIGHT : DriveAuto.MoveDirection.LEFT, 0.8, 2.1);

        // Take in the block
        Kevin.intake.in();
        drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 0.3, 1 );
        Kevin.intake.stop();

        // no
        drivetrain.move(teamColor == TeamColor.RED ? DriveAuto.MoveDirection.RIGHT : DriveAuto.MoveDirection.LEFT, 0.5, 1.5);
    }

    void dropBlock() {
        // Turn on the intake
        Kevin.intake.out();
        telemetry.addData("Intake", "Out");
        telemetry.update();

        // Move the claw
        Kevin.extender.closeClaw();
        telemetry.addData("Claw", "Close");
        telemetry.update();
        sleep(1000);

        Kevin.extender.extend();
        telemetry.addData("Extender", "Extended");
        telemetry.update();
        sleep(2000);

        // Move the claw
        Kevin.extender.openClaw();
        telemetry.addData("Claw", "Open");
        telemetry.update();
        sleep(1000);

        Kevin.extender.retract();
        telemetry.addData("Extender", "Retracted");
        telemetry.update();
        sleep(2000);

        Kevin.intake.stop();
        telemetry.addData("Intake", "Stopped");
        telemetry.update();
    }

    void moveRobotTowardsFoundation() {
        drivetrain.move(teamColor == TeamColor.BLUE ? DriveAuto.MoveDirection.LEFT : DriveAuto.MoveDirection.RIGHT,
                0.6, 1);
        drivetrain.move(DriveAuto.MoveDirection.FORWARD, 0.6, 2);
    }

    void grabFoundation() {
        Kevin.foundationGrabber.grab();
    }

    void moveFoundationBack() {
        drivetrain.move(DriveAuto.MoveDirection.BACKWARD, 0.6, 2.4);
    }

    void releaseFoundation() {
        Kevin.foundationGrabber.release();
    }

    void parkOnLine(LinePosition position) {
        switch (position) {
            case CENTER_SKYBRIDGE:
                drivetrain.move(DriveAuto.MoveDirection.FORWARD, 0.3, 2);
            case WALL:
                switch (startLocation) {
                    case FOUNDATION:
                        drivetrain.move(teamColor == TeamColor.BLUE ? DriveAuto.MoveDirection.RIGHT : DriveAuto.MoveDirection.LEFT,
                                0.7, 3.4);
                        break;
                    case DEPOT:
                    case PARKONLY:
                        drivetrain.move(teamColor == TeamColor.BLUE ? DriveAuto.MoveDirection.LEFT : DriveAuto.MoveDirection.RIGHT,
                                0.7, 3.4);
                }
        }
    }

    enum LinePosition {
        WALL,
        CENTER_SKYBRIDGE
    }

    enum StartLocation {
        DEPOT,
        FOUNDATION,
        PARKONLY,
        SKYSTONE
    }

    enum TeamColor {
        RED,
        BLUE
    }
}